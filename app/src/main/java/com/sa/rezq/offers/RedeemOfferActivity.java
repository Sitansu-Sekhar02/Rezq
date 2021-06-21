package com.sa.rezq.offers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mukesh.OtpView;
import com.sa.rezq.Activity.AppController;
import com.sa.rezq.R;
import com.sa.rezq.adapter.AllCategoryListAdapter;
import com.sa.rezq.coupons.RecentCouponActivity;
import com.sa.rezq.global.GlobalFunctions;
import com.sa.rezq.global.GlobalVariables;
import com.sa.rezq.services.ServerResponseInterface;
import com.sa.rezq.services.ServicesMethodsManager;
import com.sa.rezq.services.model.InsertAccountModel;
import com.sa.rezq.services.model.InsertRecentCouponModel;
import com.sa.rezq.services.model.OfferModel;
import com.sa.rezq.services.model.SeeAllCategoryModel;
import com.sa.rezq.services.model.StatusMainModel;
import com.sa.rezq.services.model.StatusModel;
import com.sa.rezq.services.model.VendorModel;
import com.squareup.picasso.Picasso;
import com.vlonjatg.progressactivity.ProgressLinearLayout;

import java.util.ArrayList;
import java.util.List;

public class RedeemOfferActivity extends AppCompatActivity {

    public static final String TAG = "RedeemOfferActivity",

            BUNDLE_VENDOR_DETAILS = "VendorDetailsId",
            BUNDLE_VENDOR_MODEL = "VendorModel",
            BUNDLE_REDEEM_DETAILS = "RedeemOfferActivity";


    Context context = null;
    static Activity activity = null;

    private EditText public_name_etv, cr_number_etv, vat_number_etv, m_first_name_etv, m_last_name_etv, m_email_etv, shop_url_etv, head_office_address_etv, mobile_number_etv;
    private TextView offer_title,offer_description,restro_title,restro_avg_rating,restro_rating_count,tv_save_in_recent;
    private ImageView offer_image,restro_image;
    private Button btn_redeemOffer;
    public View mainView;
    public  String code;
   // public   OtpView redeem_insert;

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


    AllCategoryListAdapter categoryListAdapter;
    List<SeeAllCategoryModel> listModelList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    ProgressLinearLayout progressActivity;
    RecyclerView seeAllCategoryRecyclerview;
    SwipeRefreshLayout swipe_container;

    InsertRecentCouponModel insertRecentCouponModel=null;

    private boolean shouldRefreshOnResume = false;

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

    public static Intent newInstance(Activity activity, OfferModel model,VendorModel vendorModel ) {
        Intent intent = new Intent(activity, RedeemOfferActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_REDEEM_DETAILS, model);
        bundle.putSerializable(BUNDLE_VENDOR_MODEL, vendorModel);
        intent.putExtras(bundle);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.redeem_offer_activity);

        context = this;
        activity = this;
        window = getWindow();

        globalFunctions = AppController.getInstance().getGlobalFunctions();
        globalVariables = AppController.getInstance().getGlobalVariables();

        offer_title=findViewById(R.id.offer_title);
        offer_image=findViewById(R.id.image_offer);
        offer_description=findViewById(R.id.offer_description);
        btn_redeemOffer=findViewById(R.id.btn_RedeemOffer);
        tv_save_in_recent=findViewById(R.id.tv_save_in_recent);

        restro_image=findViewById(R.id.iv_restro_image);
        restro_title=findViewById(R.id.tv_restro_title);
        restro_avg_rating=findViewById(R.id.restro_avg_rating);
        restro_rating_count=findViewById(R.id.restro_rating_count);


        if (getIntent().hasExtra(BUNDLE_REDEEM_DETAILS)) {
            offerModel = (OfferModel) getIntent().getSerializableExtra(BUNDLE_REDEEM_DETAILS);
        } else {
            offerModel = null;
        }

        if (getIntent().hasExtra(BUNDLE_VENDOR_MODEL)) {
            storeModel = (VendorModel) getIntent().getSerializableExtra(BUNDLE_VENDOR_MODEL);
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
               // code = offerModel.getCoupon_code();
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
        btn_redeemOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRedeemPopup();
            }
        });

        tv_save_in_recent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (insertRecentCouponModel == null) { insertRecentCouponModel = new InsertRecentCouponModel();}
                    insertRecentCouponModel.setOffers_id(offer_store_id);
                    insertRecentCouponModel.setStore_id(store_id);
                    saveInRecentCoupon(insertRecentCouponModel);

            }
        });


        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        tool_bar_back_icon = (ImageView) toolbar.findViewById(R.id.tool_bar_back_icon);
        tool_bar_back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mainView = offer_title;

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        setTitle(getString(R.string.offers), 0, 0);




    }

    private void saveInRecentCoupon(InsertRecentCouponModel recentCouponActivity) {

        // globalFunctions.showProgress(activity, activity.getString(R.string.loading));
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.insertRecentCoupon(context,recentCouponActivity, new ServerResponseInterface() {
            @Override
            public void OnSuccessFromServer(Object arg0) {
                //globalFunctions.hideProgress();
                Log.d(TAG, "Response : " + arg0.toString());
                //StatusModel model = (StatusModel) arg0;
                validateOutputAfterclickRecentCoupon(arg0);
            }

            @Override
            public void OnFailureFromServer(String msg) {
                //  globalFunctions.hideProgress();
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Failure : " + msg);
            }

            @Override
            public void OnError(String msg) {
                // globalFunctions.hideProgress();
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Error : " + msg);
            }
        }, "Register_User");
    }

    private void validateOutputAfterclickRecentCoupon(Object arg0) {
        if (arg0 instanceof StatusMainModel) {
            StatusMainModel statusMainModel = (StatusMainModel) arg0;
            StatusModel statusModel = statusMainModel.getStatusModel();
            if (!statusMainModel.isStatusLogin()) {
                globalFunctions.displayMessaage(activity, mainView, statusModel.getMessage());

            } else {
                GlobalFunctions.setSharedPreferenceString(context, GlobalVariables.SHARED_PREFERENCE_TOKEN, statusModel.getToken());

            }
        }
    }

    private void openRedeemPopup() {
        final Dialog dialog = new Dialog(activity, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.reedem_offer_alert);
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
        final OtpView redeem_insert = dialog.findViewById(R.id.redeem_offer_insert);
        final Button  btn_Continue = dialog.findViewById(R.id.btn_continue_redeem);

        redeem_insert.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String digits = redeem_insert.getText().toString().trim();
                if (digits.length() >= 6) {
                    globalFunctions.closeKeyboard(activity);
                }
            }
        });

        btn_Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 code = redeem_insert.getText().toString().trim();

                if (code.isEmpty() || code.length() < 6) {

                    redeem_insert.setError(getString(R.string.please_enter_valid_code));
                    redeem_insert.requestFocus();
                    return;
                }
                else {

                      verifyCode();

                    }

            }
        });
    }

    private void verifyCode() {
        globalFunctions.showProgress(activity, getString(R.string.loading));
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.checkRedeemCode(context,store_id,code,offer_store_id, new ServerResponseInterface() {
            @SuppressLint("LongLogTag")
            @Override
            public void OnSuccessFromServer(Object arg0) {
                globalFunctions.hideProgress();
                Log.d(TAG, "Response : " + arg0.toString());
                validateOutputAfterInsertCoupon(arg0);

            }

            @SuppressLint("LongLogTag")
            @Override
            public void OnFailureFromServer(String msg) {
                globalFunctions.hideProgress();

                globalFunctions.displayMessaage(context, mainView, msg);
                Log.d(TAG, "Failure : " + msg);
            }

            @SuppressLint("LongLogTag")
            @Override
            public void OnError(String msg) {
                globalFunctions.hideProgress();

                globalFunctions.displayMessaage(context, mainView, msg);
                Log.d(TAG, "Error : " + msg);
            }
        }, "Redeem Code");

    }

    private void validateOutputAfterInsertCoupon(Object arg0) {
        if (arg0 instanceof StatusMainModel) {
            StatusMainModel statusMainModel = (StatusMainModel) arg0;
            if (!statusMainModel.isStatusLogin()) {
                globalFunctions.displayMessaage(activity, mainView, statusMainModel.getMessage());

            } else {
                Intent intent = ShowCouponOfferActivity.newInstance( activity, offerModel,storeModel);
                activity.startActivity( intent );

            }
        }
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
