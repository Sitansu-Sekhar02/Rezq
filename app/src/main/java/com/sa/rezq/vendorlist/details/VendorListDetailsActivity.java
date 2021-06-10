package com.sa.rezq.vendorlist.details;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sa.rezq.Activity.AppController;
import com.sa.rezq.R;
import com.sa.rezq.adapter.AllCategoryListAdapter;
import com.sa.rezq.adapter.OfferListAdapter;
import com.sa.rezq.adapter.ReviewListAdapter;
import com.sa.rezq.adapter.TrendingListAdapter;
import com.sa.rezq.global.GlobalFunctions;
import com.sa.rezq.global.GlobalVariables;
import com.sa.rezq.services.ServerResponseInterface;
import com.sa.rezq.services.ServicesMethodsManager;
import com.sa.rezq.services.model.OfferListModel;
import com.sa.rezq.services.model.OfferModel;
import com.sa.rezq.services.model.ReviewListModel;
import com.sa.rezq.services.model.ReviewModel;
import com.sa.rezq.services.model.SeeAllCategoryModel;
import com.sa.rezq.services.model.TrendingModel;
import com.sa.rezq.services.model.VendorDetailsMainModel;
import com.sa.rezq.services.model.VendorModel;
import com.sa.rezq.services.model.VendorStoreListModel;
import com.squareup.picasso.Picasso;
import com.vlonjatg.progressactivity.ProgressLinearLayout;

import java.util.ArrayList;
import java.util.List;

public class VendorListDetailsActivity extends AppCompatActivity {

    public static final String TAG = "VendorListDetailsActivity",
            BUNDLE_VENDOR_LIST_DETAILS = "VendorListDetailsActivity",
            BUNDLE_VENDOR_STORE_LIST_DETAILS = "VendorStoreListDetailsActivity";


    View view;
    TextView AllLockedOffer;
    Context context = null;
    static Activity activity = null;

    ImageView vendor_list_image,iv_favourite;
    TextView tv_vendor_name,tvRating,tv_address,tv_open_map,tv_rating_count,Tv_allLockedOffers;

    public View mainView;

    static Toolbar toolbar;
    static ActionBar actionBar;
    static String mTitle;
    static int mResourceID;
    static int titleResourseID;
    static TextView toolbar_title;
    static ImageView toolbar_logo, tool_bar_back_icon;

    int id=100;


    OfferListAdapter offerListAdapter;
    List<OfferModel> offerListModels = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    ProgressLinearLayout offerprogressActivity;
    RecyclerView offer_list_recyclerview;
    SwipeRefreshLayout swipe_container;
    OfferModel offerModel;


    ReviewListAdapter reviewListAdapter;
    List<ReviewModel> reviewModels = new ArrayList<>();
    LinearLayoutManager reviewLinear;
    ProgressLinearLayout reviewprogressActivity;
    RecyclerView review_list_recyclerview;
    SwipeRefreshLayout review_swipe_container;

    private boolean shouldRefreshOnResume = false;
    VendorModel vendorModel = null;

    GlobalVariables globalVariables;
    GlobalFunctions globalFunctions;
    Window window = null;

    String vendor_id = null;
    TrendingModel trendingModel = null;
    VendorStoreListModel vendorStoreListModel = null;

    FirebaseStorage storage;
    StorageReference storageReference;

    public static Intent newInstance(Activity activity, TrendingModel model) {
        Intent intent = new Intent(activity, VendorListDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_VENDOR_LIST_DETAILS, model);
        intent.putExtras(bundle);
        return intent;
    }

    public static Intent newInstance(Activity activity, VendorStoreListModel vendorStoreListModel) {
        Intent intent = new Intent(activity, VendorListDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_VENDOR_STORE_LIST_DETAILS, vendorStoreListModel);
        intent.putExtras(bundle);
        return intent;
    }

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_list_details);

        context = this;
        activity = this;
        window = getWindow();

        globalFunctions = AppController.getInstance().getGlobalFunctions();
        globalVariables = AppController.getInstance().getGlobalVariables();

        linearLayoutManager = new LinearLayoutManager(activity);
        offerprogressActivity = findViewById(R.id.details_progressActivity_reviews);

        reviewLinear = new LinearLayoutManager(activity);
        reviewprogressActivity = findViewById(R.id.details_progressActivity);
        //review_swipe_container = findViewById(R.id.swipe_container);
        review_list_recyclerview =findViewById(R.id.raecyclerview_review_rating);
        review_list_recyclerview.setAdapter(reviewListAdapter);


        vendor_list_image=findViewById(R.id.vendor_list_image);
       // vendor_list_image.setAlpha(0.75f);

        iv_favourite=findViewById(R.id.iv_favourite);

        tv_vendor_name=findViewById(R.id.tv_vendor_name);
        tv_rating_count=findViewById(R.id.tv_count_rating);
        tvRating=findViewById(R.id.tvRating);
        tv_address=findViewById(R.id.tv_address);
        tv_open_map=findViewById(R.id.tv_open_map);
        Tv_allLockedOffers=findViewById(R.id.Tv_allLockedOffers);


        offer_list_recyclerview =findViewById(R.id.recyclerview_locked_offer);
        offer_list_recyclerview.setAdapter(offerListAdapter);

        mainView=vendor_list_image;


        if (getIntent().hasExtra(BUNDLE_VENDOR_LIST_DETAILS)) {
            trendingModel = (TrendingModel) getIntent().getSerializableExtra(BUNDLE_VENDOR_LIST_DETAILS);
        } else {
            trendingModel = null;
        }

        if (trendingModel != null) {
            if (GlobalFunctions.isNotNullValue(trendingModel.getId())) {
                Log.d(TAG,trendingModel.getId());
                vendor_id = trendingModel.getId();
            }
        }

        if (getIntent().hasExtra(BUNDLE_VENDOR_STORE_LIST_DETAILS)) {
            vendorStoreListModel = (VendorStoreListModel) getIntent().getSerializableExtra(BUNDLE_VENDOR_STORE_LIST_DETAILS);
        } else {
            vendorStoreListModel = null;
        }

        if (vendorStoreListModel != null) {
            if (GlobalFunctions.isNotNullValue(vendorStoreListModel.getId())) {
                Log.d(TAG,vendorStoreListModel.getId());
                vendor_id = vendorStoreListModel.getId();
            }
        }
       /* if (trendingModel != null) {
            if (GlobalFunctions.isNotNullValue(orderStatus.getId())) {
                status = orderStatus.getId();
            }
        }*/
        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        tool_bar_back_icon = (ImageView) toolbar.findViewById(R.id.tool_bar_back_icon);
        toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title);

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        tool_bar_back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Tv_allLockedOffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

       /* tv_open_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tackAddress(vendorModel);

            }
        });
*/
        setTitle(getString(R.string.offers), 0, 0);


        loadVendorlistDetails();

      /*  if (offerModel.getAllow().equalsIgnoreCase("1")) {
            getOfferList();
        }
*/

        getOfferList();

        getReviews();


    }

    private void getReviews() {
        //GlobalFunctions.showProgress(context, getString(R.string.loading));
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.getVendorDetails(context, String.valueOf(100), new ServerResponseInterface() {
            @SuppressLint("LongLogTag")
            @Override
            public void OnSuccessFromServer(Object arg0) {
                //GlobalFunctions.hideProgress();
                Log.d(TAG, "Response : " + arg0.toString());
                VendorDetailsMainModel vendorMainModel = (VendorDetailsMainModel) arg0;
                VendorModel vendorModel = vendorMainModel.getVendorModel();
                ReviewListModel reviewListModel=vendorModel.getReviewListModel();

                if (reviewListModel != null) {
                    setReviewPage(reviewListModel);
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void OnFailureFromServer(String msg) {
                //GlobalFunctions.hideProgress();

                Log.d(TAG, "Failure : " + msg);
                GlobalFunctions.displayMessaage(context, mainView, msg);
            }

            @SuppressLint("LongLogTag")
            @Override
            public void OnError(String msg) {
               // GlobalFunctions.hideProgress();
                Log.d(TAG, "Error : " + msg);
                GlobalFunctions.displayMessaage(context, mainView, msg);
            }
        }, "Review List");

    }

    private void setReviewPage(ReviewListModel reviewListModel) {
        if (reviewListModel != null && reviewModels != null) {
            reviewModels.clear();
            reviewModels.addAll( reviewListModel.getReviewModels() );
            if (reviewListAdapter != null) {
                synchronized (reviewListAdapter) {
                    reviewListAdapter.notifyDataSetChanged();
                }
            }
            if (reviewModels.size() <= 0) {
                showReviewEmptyPage();
            } else {
                showReviewContent();
                reviewRecyclerView();
            }
        }
    }

    private void showReviewEmptyPage() {
        if (offerprogressActivity != null) {
            offerprogressActivity.showEmpty(getResources().getDrawable(R.drawable.rezq_logo), getString(R.string.emptyList),
                    getString(R.string.no_reviews));
        }
    }

    private void showReviewContent() {
        if (reviewprogressActivity != null) {
            reviewprogressActivity.showContent();
        }
    }
    private void reviewRecyclerView() {
        review_list_recyclerview.setLayoutManager(reviewLinear);
        review_list_recyclerview.setHasFixedSize(true);
        reviewListAdapter = new ReviewListAdapter(activity, reviewModels);
        review_list_recyclerview.setAdapter(reviewListAdapter);
    }


    private void getOfferList() {
       // GlobalFunctions.showProgress(context, getString(R.string.loading));
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.getVendorDetails(context, String.valueOf(100), new ServerResponseInterface() {
            @SuppressLint("LongLogTag")
            @Override
            public void OnSuccessFromServer(Object arg0) {
                GlobalFunctions.hideProgress();
                Log.d(TAG, "Response : " + arg0.toString());
                VendorDetailsMainModel vendorMainModel = (VendorDetailsMainModel) arg0;
                VendorModel vendorModel = vendorMainModel.getVendorModel();
                OfferListModel offerModel=vendorModel.getOfferListModel();

                if (offerModel != null) {
                    setThisPage(offerModel);
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void OnFailureFromServer(String msg) {
               // GlobalFunctions.hideProgress();

                Log.d(TAG, "Failure : " + msg);
                GlobalFunctions.displayMessaage(context, mainView, msg);
            }

            @SuppressLint("LongLogTag")
            @Override
            public void OnError(String msg) {
              //  GlobalFunctions.hideProgress();
                Log.d(TAG, "Error : " + msg);
                GlobalFunctions.displayMessaage(context, mainView, msg);
            }
        }, "Offer List");
    }

    private void setThisPage(OfferListModel offerModel) {
        if (offerModel != null && offerListModels != null) {
            offerListModels.clear();
            offerListModels.addAll( offerModel.getOfferModels() );
            if (offerListAdapter != null) {
                synchronized (offerListAdapter) {
                    offerListAdapter.notifyDataSetChanged();
                }
            }
            if (offerListModels.size() <= 0) {
                showOfferEmptyPage();
            } else {
                showOfferContent();
                offerRecyclerView();
            }
        }
    }

    private void showOfferEmptyPage() {
        if (offerprogressActivity != null) {
            offerprogressActivity.showEmpty(getResources().getDrawable(R.drawable.rezq_logo), getString(R.string.emptyList),
                    getString(R.string.not_available));
        }
    }

    private void offerRecyclerView() {
        offer_list_recyclerview.setLayoutManager(linearLayoutManager);
        offer_list_recyclerview.setHasFixedSize(true);
        offerListAdapter = new OfferListAdapter(activity, offerListModels);
        offer_list_recyclerview.setAdapter(offerListAdapter);
    }

    private void showOfferContent() {
        if (offerprogressActivity != null) {
            offerprogressActivity.showContent();
        }
    }


    private void tackAddress(VendorModel vendorModel) {


          try {
              Uri uri = Uri.parse("https://www.google.co.in/maps/dir/" + "" + "/" + vendorModel.getAddress());
              Intent intent = new Intent(Intent.ACTION_VIEW, uri);
              intent.setPackage("com.google.android.apps.maps");
              intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              startActivity(intent);
          } catch (ActivityNotFoundException e) {
              Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps,maps");
              Intent intent = new Intent(Intent.ACTION_VIEW, uri);
              intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              startActivity(intent);

          }

    }


    private void loadVendorlistDetails() {
        GlobalFunctions.showProgress(context, getString(R.string.loading));
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.getVendorDetails(context, String.valueOf(100), new ServerResponseInterface() {
            @SuppressLint("LongLogTag")
            @Override
            public void OnSuccessFromServer(Object arg0) {
                GlobalFunctions.hideProgress();
                Log.d(TAG, "Response : " + arg0.toString());
                VendorDetailsMainModel vendorMainModel = (VendorDetailsMainModel) arg0;
                VendorModel vendorModel = vendorMainModel.getVendorModel();


                if (vendorModel != null) {
                    setVendorDetails(vendorModel);
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void OnFailureFromServer(String msg) {
                GlobalFunctions.hideProgress();

                Log.d(TAG, "Failure : " + msg);
                GlobalFunctions.displayMessaage(context, mainView, msg);
            }

            @SuppressLint("LongLogTag")
            @Override
            public void OnError(String msg) {
                GlobalFunctions.hideProgress();
                Log.d(TAG, "Error : " + msg);
                GlobalFunctions.displayMessaage(context, mainView, msg);
            }
        }, "Order List");
    }

    private void setVendorDetails(VendorModel vendorModel) {

        if (vendorModel != null && context != null) {

            if (GlobalFunctions.isNotNullValue(vendorModel.getImage())) {
                Picasso.with(context).load(vendorModel.getImage()).placeholder(R.drawable.rezq_logo).into(vendor_list_image);

            }
            if (GlobalFunctions.isNotNullValue(vendorModel.getName())) {
                tv_vendor_name.setText(vendorModel.getName());

            }  if (GlobalFunctions.isNotNullValue(vendorModel.getAddress())) {
                tv_address.setText(vendorModel.getAddress());
              /*  tv_open_map.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tackAddress(vendorModel);

                    }
                });*/
            }
            if (GlobalFunctions.isNotNullValue(vendorModel.getRating_count())) {
                tvRating.setText(vendorModel.getAvg_rating());

            }if (GlobalFunctions.isNotNullValue(vendorModel.getRating_count())) {
                tv_rating_count.setText(vendorModel.getRating_count());

            }
        }
    }

    /*private void SeeAllLockedPopup() {
        final Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.all_locked_offer_alert);
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
        RecyclerView rezqPlus=dialog.findViewById(R.id.recyclerRezqplus);
        RecyclerView rezqPrime=dialog.findViewById(R.id.recyclerPrimeOffer);

        mAdapter = new LockedOfferAdapter(movieList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rezqPlus.setLayoutManager(mLayoutManager);
        rezqPlus.setItemAnimator(new DefaultItemAnimator());
        rezqPlus.setAdapter(mAdapter);


    }*/

        @Override
        public void onStop () {
            super.onStop();
            shouldRefreshOnResume = true;
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




    }
