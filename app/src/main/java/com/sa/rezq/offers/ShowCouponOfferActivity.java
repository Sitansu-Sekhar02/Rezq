package com.sa.rezq.offers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mukesh.OtpView;
import com.sa.rezq.Activity.AppController;
import com.sa.rezq.Activity.MainActivity;
import com.sa.rezq.R;
import com.sa.rezq.adapter.AllCategoryListAdapter;
import com.sa.rezq.global.GlobalFunctions;
import com.sa.rezq.global.GlobalVariables;
import com.sa.rezq.membership.FreeMembershipActivity;
import com.sa.rezq.services.ServerResponseInterface;
import com.sa.rezq.services.ServicesMethodsManager;
import com.sa.rezq.services.model.FeedbackNRatingMainModel;
import com.sa.rezq.services.model.InsertAccountModel;
import com.sa.rezq.services.model.OfferModel;
import com.sa.rezq.services.model.RatingNFeedbackModel;
import com.sa.rezq.services.model.SeeAllCategoryModel;
import com.sa.rezq.services.model.StatusMainModel;
import com.sa.rezq.services.model.StatusModel;
import com.sa.rezq.services.model.VendorModel;
import com.sa.rezq.vendorlist.details.VendorListDetailsActivity;
import com.squareup.picasso.Picasso;
import com.vlonjatg.progressactivity.ProgressLinearLayout;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class ShowCouponOfferActivity extends AppCompatActivity {

    public static final String TAG = "ShowCouponOfferActivity",

            BUNDLE_VENDOR_DETAILS = "VendorDetailsId",
            BUNDLE_REDEEM_DETAILS = "RedeemOfferActivity";


    Context context = null;
    static Activity activity = null;

    private EditText public_name_etv, cr_number_etv, vat_number_etv, m_first_name_etv, m_last_name_etv, m_email_etv, shop_url_etv, head_office_address_etv, mobile_number_etv;
    private TextView offer_title,offer_description,restro_title,restro_avg_rating,restro_rating_count,tv_show_coupon,tv_copy_code;
    private ImageView offer_image,restro_image;
    private ImageView iv_feedback_image;
    private RatingBar rt_rating;
    private TextView tv_feedback_title;
    private  EditText et_feedback_comment;
    private Button btn_redeemOffer;
    private Button btn_submit;
    public View mainView;
    public   OtpView redeem_insert;

    static Toolbar toolbar;
    static ActionBar actionBar;
    static String mTitle;
    static int mResourceID;
    static int titleResourseID;
    static TextView toolbar_title;
    static ImageView toolbar_logo, tool_bar_back_icon;
    Menu menu;
    String account = String.valueOf(1);
    String offer_store_id=null;
    String store_id=null;

    LayoutInflater layoutInflater;
    RatingNFeedbackModel ratingNFeedbackModel;

    String rating = "1";



    AllCategoryListAdapter categoryListAdapter;
    List<SeeAllCategoryModel> listModelList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    ProgressLinearLayout progressActivity;
    RecyclerView seeAllCategoryRecyclerview;
    SwipeRefreshLayout swipe_container;

    private boolean shouldRefreshOnResume = false;

    private ClipboardManager myClipboard;
    private ClipData myClip;

    GlobalVariables globalVariables;
    GlobalFunctions globalFunctions;
    Window window = null;
    OfferModel offerModel=null;
    VendorModel storeModel=null;

    String imagePath = "";

    FirebaseStorage storage;
    StorageReference storageReference;

  /*  public static Intent newInstance(Activity activity, OfferModel listModel) {
        Intent intent = new Intent(activity, RedeemOfferActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_REDEEM_DETAILS, listModel);
        intent.putExtras(bundle);
        return intent;
    }*/

    public static Intent newInstance(Activity activity, OfferModel model, VendorModel vendorStoreListModel) {
        Intent intent = new Intent(activity, ShowCouponOfferActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_REDEEM_DETAILS, model);
        bundle.putSerializable(BUNDLE_VENDOR_DETAILS, vendorStoreListModel);
        intent.putExtras(bundle);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_offers_coupon);

        context = this;
        activity = this;
        window = getWindow();

        layoutInflater = activity.getLayoutInflater();


        globalFunctions = AppController.getInstance().getGlobalFunctions();
        globalVariables = AppController.getInstance().getGlobalVariables();

        offer_title=findViewById(R.id.offer_title);
        tv_copy_code=findViewById(R.id.tv_copy_couponcode);
        offer_image=findViewById(R.id.image_offer);
        offer_description=findViewById(R.id.offer_description);
        btn_redeemOffer=findViewById(R.id.btn_RedeemOffer);

        restro_image=findViewById(R.id.iv_restro_image);
        restro_title=findViewById(R.id.tv_restro_title);
        restro_avg_rating=findViewById(R.id.restro_avg_rating);
        restro_rating_count=findViewById(R.id.restro_rating_count);
        tv_show_coupon=findViewById(R.id.tv_show_coupon);


        if (getIntent().hasExtra(BUNDLE_REDEEM_DETAILS)) {
            offerModel = (OfferModel) getIntent().getSerializableExtra(BUNDLE_REDEEM_DETAILS);
        } else {
            offerModel = null;
        }

        if (getIntent().hasExtra(BUNDLE_VENDOR_DETAILS)) {
            storeModel = (VendorModel) getIntent().getSerializableExtra(BUNDLE_VENDOR_DETAILS);
        } else {
            storeModel = null;
        }

        if (offerModel != null) {
            if (GlobalFunctions.isNotNullValue(offerModel.getOffer_image())) {
                Picasso.with(context).load(offerModel.getOffer_image()).placeholder(R.drawable.rezq_logo).into(offer_image);

            }
            if (GlobalFunctions.isNotNullValue(offerModel.getTitle())) {
                offer_title.setText(offerModel.getTitle());

            }
            if (GlobalFunctions.isNotNullValue(offerModel.getOffer_image())) {
                offer_description.setText(offerModel.getOffer_applicable());

            }if (GlobalFunctions.isNotNullValue(offerModel.getId())) {
                offer_store_id = offerModel.getId();
            }
            if (GlobalFunctions.isNotNullValue(offerModel.getCoupon_code())) {
                tv_show_coupon .setText(offerModel.getCoupon_code());
            }

            if (GlobalFunctions.isNotNullValue(offerModel.getStoreId())) {
                store_id=offerModel.getStoreId();
            }


        }


        if (storeModel!=null){
            if (GlobalFunctions.isNotNullValue(storeModel.getImage())) {
                Picasso.with(context).load(storeModel.getImage()).placeholder(R.drawable.rezq_logo).into(restro_image);
            }

            if (GlobalFunctions.isNotNullValue(storeModel.getName())) {
                restro_title.setText(storeModel.getName());
            }

            if (GlobalFunctions.isNotNullValue(storeModel.getAvg_rating())) {
                restro_avg_rating.setText(storeModel.getAvg_rating());
            }

            if (GlobalFunctions.isNotNullValue(storeModel.getRating_count())) {
                restro_rating_count.setText(storeModel.getRating_count());
            }

            if (GlobalFunctions.isNotNullValue(storeModel.getId())) {
                store_id = storeModel.getId();
            }
        }
        //to copy data to clip board
        tv_copy_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                String text;
                text = tv_show_coupon.getText().toString();

                myClip = ClipData.newPlainText("text", text);
                myClipboard.setPrimaryClip(myClip);

                Toasty.success(getApplicationContext(), getString(R.string.copy_coupon_code), Toast.LENGTH_SHORT).show();
            }
        });


        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        //toolbar.setPadding(0, GlobalFunctions.getStatusBarHeight(context), 0, 0);
        //toolbar.setNavigationIcon(R.drawable.ic_back_draw);
        //toolbar.setContentInsetsAbsolute(0,0);
        toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        tool_bar_back_icon = (ImageView) toolbar.findViewById(R.id.tool_bar_back_icon);
        tool_bar_back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // onBackPressed();
                Intent intent = new Intent(activity, MainActivity.class);
                startActivity(intent);
                closeThisActivity();
            }
        });
        mainView = toolbar;

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        setTitle(getString(R.string.offers), 0, 0);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                // Show your dialog here
                openFeedbackPopup();

            }
        }, 1500);

    }

    private void openFeedbackPopup() {
        final Dialog dialog = new Dialog(activity, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.feed_back_n_rating_allert);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.show();
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        iv_feedback_image=dialog.findViewById(R.id.iv_product_image);
        tv_feedback_title=dialog.findViewById(R.id.tv_product_title);
        rt_rating=dialog.findViewById(R.id.rt_rating);
        et_feedback_comment=dialog.findViewById(R.id.et_Comment);
        btn_submit=dialog.findViewById(R.id.btn_submit);
        et_feedback_comment.clearFocus();

        final ImageView rating_iv1, rating_iv2, rating_iv3, rating_iv4, rating_iv5;

        rating_iv1 = dialog.findViewById(R.id.rating_iv1);
        rating_iv2 = dialog.findViewById(R.id.rating_iv2);
        rating_iv3 = dialog.findViewById(R.id.rating_iv3);
        rating_iv4 = dialog.findViewById(R.id.rating_iv4);
        rating_iv5 = dialog.findViewById(R.id.rating_iv5);

        setImageView(5, rating_iv1, rating_iv2, rating_iv3, rating_iv4, rating_iv5);


        rating_iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setImageView(1, rating_iv1, rating_iv2, rating_iv3, rating_iv4, rating_iv5);
            }

        });

        rating_iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setImageView(2, rating_iv1, rating_iv2, rating_iv3, rating_iv4, rating_iv5);
            }

        });

        rating_iv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setImageView(3, rating_iv1, rating_iv2, rating_iv3, rating_iv4, rating_iv5);
            }

        });

        rating_iv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setImageView(4, rating_iv1, rating_iv2, rating_iv3, rating_iv4, rating_iv5);
            }

        });

        rating_iv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setImageView(5, rating_iv1, rating_iv2, rating_iv3, rating_iv4, rating_iv5);
            }

        });



        if (storeModel != null) {
            if (GlobalFunctions.isNotNullValue(storeModel.getImage())) {
                Picasso.with(context).load(storeModel.getImage()).placeholder(R.drawable.rezq_logo).into(iv_feedback_image);

            }
            if (GlobalFunctions.isNotNullValue(storeModel.getName())) {
                tv_feedback_title.setText(storeModel.getName());
            }
        }




             btn_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (et_feedback_comment != null) {
                        String
                                //rating = String.valueOf(rt_rating.getRating()),
                                comment = et_feedback_comment.getText().toString().trim();
                       /* if (rating.isEmpty()) {
                            rt_rating.setError(getString(R.string.pleaseFillMandatoryDetails));
                            rt_rating.setFocusableInTouchMode(true);
                            rt_rating.requestFocus();
                        }*//* if (comment.isEmpty()) {
                            et_feedback_comment.setError(getString(R.string.please_give_a_comment));
                            et_feedback_comment.setFocusableInTouchMode(true);
                            et_feedback_comment.requestFocus();
                        } else {*/
                            if (ratingNFeedbackModel == null) {
                                ratingNFeedbackModel = new RatingNFeedbackModel();
                            }
                            ratingNFeedbackModel.setStore_id(store_id);
                            ratingNFeedbackModel.setRating(rating);
                            ratingNFeedbackModel.setComment(comment);
                            dialog.dismiss();

                             insertFeedback(activity, ratingNFeedbackModel);

                        }
                }

            });

    }

    private void setImageView(int position, ImageView rating_iv1, ImageView rating_iv2, ImageView rating_iv3, ImageView rating_iv4, ImageView rating_iv5) {
        switch (position) {
            case 1:
                rating_iv1.setImageResource(R.drawable.ic_star_selected_large);
                rating_iv2.setImageResource(R.drawable.ic_star_unselected_large);
                rating_iv3.setImageResource(R.drawable.ic_star_unselected_large);
                rating_iv4.setImageResource(R.drawable.ic_star_unselected_large);
                rating_iv5.setImageResource(R.drawable.ic_star_unselected_large);
                rating = position + "";
                break;

            case 2:
                rating_iv1.setImageResource(R.drawable.ic_star_selected_large);
                rating_iv2.setImageResource(R.drawable.ic_star_selected_large);
                rating_iv3.setImageResource(R.drawable.ic_star_unselected_large);
                rating_iv4.setImageResource(R.drawable.ic_star_unselected_large);
                rating_iv5.setImageResource(R.drawable.ic_star_unselected_large);
                rating = position + "";
                break;

            case 3:
                rating_iv1.setImageResource(R.drawable.ic_star_selected_large);
                rating_iv2.setImageResource(R.drawable.ic_star_selected_large);
                rating_iv3.setImageResource(R.drawable.ic_star_selected_large);
                rating_iv4.setImageResource(R.drawable.ic_star_unselected_large);
                rating_iv5.setImageResource(R.drawable.ic_star_unselected_large);
                rating = position + "";
                break;

            case 4:
                rating_iv1.setImageResource(R.drawable.ic_star_selected_large);
                rating_iv2.setImageResource(R.drawable.ic_star_selected_large);
                rating_iv3.setImageResource(R.drawable.ic_star_selected_large);
                rating_iv4.setImageResource(R.drawable.ic_star_selected_large);
                rating_iv5.setImageResource(R.drawable.ic_star_unselected_large);
                rating = position + "";
                break;

            case 5:
                rating_iv1.setImageResource(R.drawable.ic_star_selected_large);
                rating_iv2.setImageResource(R.drawable.ic_star_selected_large);
                rating_iv3.setImageResource(R.drawable.ic_star_selected_large);
                rating_iv4.setImageResource(R.drawable.ic_star_selected_large);
                rating_iv5.setImageResource(R.drawable.ic_star_selected_large);
                rating = position + "";
                break;

            default:
                rating_iv1.setImageResource(R.drawable.ic_star_unselected_large);
                rating_iv2.setImageResource(R.drawable.ic_star_unselected_large);
                rating_iv3.setImageResource(R.drawable.ic_star_unselected_large);
                rating_iv4.setImageResource(R.drawable.ic_star_unselected_large);
                rating_iv5.setImageResource(R.drawable.ic_star_unselected_large);
                rating = "1";
        }
    }

    private void insertFeedback(Activity activity, RatingNFeedbackModel ratingNFeedbackModel) {
        globalFunctions.showProgress(activity, activity.getString(R.string.loading));
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.giveFeedback(context, ratingNFeedbackModel, new ServerResponseInterface() {
            @Override
            public void OnSuccessFromServer(Object arg0) {
                globalFunctions.hideProgress();
                Log.d(TAG, "Response : " + arg0.toString());
                //StatusModel model = (StatusModel) arg0;
                validateOutputAfterFeedback(arg0);
            }

            @Override
            public void OnFailureFromServer(String msg) {
                  globalFunctions.hideProgress();
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Failure : " + msg);
            }

            @Override
            public void OnError(String msg) {
                globalFunctions.hideProgress();
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Error : " + msg);
            }
        }, "feedback");
    }
    private void validateOutputAfterFeedback(Object arg0) {
        if (arg0 instanceof StatusMainModel) {
            StatusMainModel feedbackNRatingMainModel = (StatusMainModel) arg0;
            StatusModel statusModel = feedbackNRatingMainModel.getStatusModel();
            if (!feedbackNRatingMainModel.isStatusLogin()) {
                globalFunctions.displayMessaage(activity, mainView, statusModel.getMessage());

            } else {
                globalFunctions.displayMessaage(activity, mainView, statusModel.getMessage());
                //goToMainActivity();

            }
        }
    }
    private void goToMainActivity() {
        Intent intent = new Intent(activity, MainActivity.class);
        startActivity(intent);
        closeThisActivity();
    }



    public static void setTitle (String title,int titleImageID, int backgroundResourceID){
        mTitle = title;
        if (backgroundResourceID != 0) {
            mResourceID = backgroundResourceID;
        } else {
            mResourceID = 0;
        }
        if (titleImageID != 0) {
            titleResourseID = titleImageID;
        } else {
            titleResourseID = 0;
        }
        restoreToolbar();
    }

    @SuppressLint("LongLogTag")
    private static void restoreToolbar () {
        //toolbar = (Toolbar) findViewById(R.id.tool_bar);
        Log.d(TAG, "Restore Tool Bar");
        if (actionBar != null) {
            Log.d(TAG, "Restore Action Bar not Null");
            Log.d(TAG, "Title : " + mTitle);
            toolbar_title.setText(mTitle);
            if (mResourceID != 0) toolbar.setBackgroundResource(mResourceID);
            //actionBar.setTitle("");
            // actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    public void onBackPressed () {
        Intent intent = new Intent(activity, MainActivity.class);
        startActivity( intent );
        closeThisActivity();
        super.onBackPressed();

    }
    public static void closeThisActivity () {
        if (activity != null) {
            activity.finish();
            //activity.overridePendingTransition(R.anim.zoom_in,R.anim.zoom_out);
        }
    }
    @Override
    public void onPause () {
        super.onPause();
        if (getFragmentManager().findFragmentByTag(TAG) != null)
            getFragmentManager().findFragmentByTag(TAG).setRetainInstance(true);
    }

    @Override
    public void onStart () {

       /* if(hint != null) {
            hint.launchAutomaticHintForCall(activity.findViewById(R.id.action_call));
        }*/
//       globalFunctions.launchAutomaticHintForSearch(mainView, getString(R.string.search_title),  getString(R.string.search_description));
        super.onStart();
    }

    @Override
    public void onDestroy () {
        super.onDestroy();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

}
