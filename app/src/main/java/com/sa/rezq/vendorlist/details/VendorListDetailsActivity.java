package com.sa.rezq.vendorlist.details;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.sa.rezq.Activity.AppController;
import com.sa.rezq.R;
import com.sa.rezq.adapter.OfferListAdapter;
import com.sa.rezq.adapter.ReviewListAdapter;
import com.sa.rezq.global.GlobalFunctions;
import com.sa.rezq.global.GlobalVariables;
import com.sa.rezq.membership.FreeMembershipActivity;
import com.sa.rezq.services.ServerResponseInterface;
import com.sa.rezq.services.ServicesMethodsManager;
import com.sa.rezq.services.model.BannerModel;
import com.sa.rezq.services.model.NearbyModel;
import com.sa.rezq.services.model.OfferListModel;
import com.sa.rezq.services.model.OfferModel;
import com.sa.rezq.services.model.RecentCouponModel;
import com.sa.rezq.services.model.ReviewListModel;
import com.sa.rezq.services.model.ReviewModel;
import com.sa.rezq.services.model.StatusMainModel;
import com.sa.rezq.services.model.StatusModel;
import com.sa.rezq.services.model.TrendingModel;
import com.sa.rezq.services.model.VendorDetailsMainModel;
import com.sa.rezq.services.model.VendorModel;
import com.sa.rezq.services.model.VendorStoreModel;
import com.sa.rezq.services.model.WishModel;
import com.squareup.picasso.Picasso;
import com.vlonjatg.progressactivity.ProgressLinearLayout;

import java.util.ArrayList;
import java.util.List;

public class VendorListDetailsActivity extends AppCompatActivity {

    public static final String TAG = "VendorListDetailsActivity",
            BUNDLE_VENDOR_LIST_DETAILS = "VendorListDetailsActivity",
            BUNDLE_BANNER_CLICK_DETAILS = "BannerClickDetails",
            BUNDLE_NEARBY_PLACES = "NearByPlaces",
            BUNDLE_VENDOR_MODEL = "VendorModel",
            BUNDLE_WISHLIST_MODEL = "WishListModel",
            BUNDLE_RECENT_COUPON = "RecentCopuonList",
            BUNDLE_VENDOR_STORE_LIST_DETAILS = "VendorStoreListDetailsActivity";

    View view;
    TextView AllLockedOffer;
    Context context = null;
    static Activity activity = null;

    ImageView vendor_list_image;
    ImageView iv_favourite;
    TextView tv_vendor_name, tvRating, tv_address, tv_open_map, tv_rating_count, Tv_allLockedOffers,empty_offer;
    public  static RelativeLayout upgrade_prime_rl;

    public View mainView;

    static Toolbar toolbar;
    static ActionBar actionBar;
    static String mTitle;
    static int mResourceID;
    static int titleResourseID;
    static TextView toolbar_title;
    static ImageView toolbar_logo, tool_bar_back_icon;
    BannerModel bannerModel = null;
    NearbyModel nearbyModel = null;
    RecentCouponModel recentCouponModel=null;
    LinearLayout hotel_ll;

    //int id = 100;
    boolean isWishlisted=false;
    String id = null;
    LayoutInflater layoutInflater;

    String latitude=null;
    String longitude=null;


    OfferListAdapter offerListAdapter;
    List<OfferModel> offerListModels = new ArrayList<>();
    List<OfferModel> offerListlocked = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    ProgressLinearLayout offerprogressActivity;
    RecyclerView offer_list_recyclerview;
    SwipeRefreshLayout swipe_container;
    OfferModel offerModel = null;
    List<OfferListModel> listModels = new ArrayList<>();


    ReviewListAdapter reviewListAdapter;
    List<ReviewModel> reviewModels = new ArrayList<>();
    LinearLayoutManager reviewLinear;
    ProgressLinearLayout reviewprogressActivity;
    RecyclerView review_list_recyclerview;
    SwipeRefreshLayout review_swipe_container;

    private boolean shouldRefreshOnResume = false;
    VendorModel vendorModel = null;
    ProgressLinearLayout AllLockedprogressActivity;

    GlobalVariables globalVariables;
    GlobalFunctions globalFunctions;
    Window window = null;

    String vendor_id = null;
    TrendingModel trendingModel = null;
    WishModel wishModel=null;
    VendorStoreModel vendorStoreModel = null;

    FirebaseStorage storage;
    StorageReference storageReference;

    public static Intent newInstance(Activity activity, TrendingModel model) {
        Intent intent = new Intent(activity, VendorListDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_VENDOR_LIST_DETAILS, model);
        intent.putExtras(bundle);
        return intent;
    }

    public static Intent newInstance(Activity activity, VendorStoreModel vendorStoreModel, VendorModel vendorModel) {
        Intent intent = new Intent(activity, VendorListDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_VENDOR_STORE_LIST_DETAILS, vendorStoreModel);
        bundle.putSerializable(BUNDLE_VENDOR_MODEL, vendorModel);
        intent.putExtras(bundle);
        return intent;
    }

    public static Intent newInstance(Activity activity, BannerModel bannerClickedModel) {
        Intent intent = new Intent(activity, VendorListDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_BANNER_CLICK_DETAILS, bannerClickedModel);
        intent.putExtras(bundle);
        return intent;
    }

    public static Intent newInstance(Activity activity, NearbyModel model) {
        Intent intent = new Intent(activity, VendorListDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_NEARBY_PLACES, model);
        intent.putExtras(bundle);
        return intent;
    }

    public static Intent newInstance(Activity activity, WishModel wishModel) {
        Intent intent = new Intent(activity, VendorListDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_WISHLIST_MODEL, wishModel);
        intent.putExtras(bundle);
        return intent;
    }

    public static Intent newInstance(Activity activity, RecentCouponModel model) {
        Intent intent = new Intent(activity, VendorListDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_RECENT_COUPON, model);
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

        layoutInflater = activity.getLayoutInflater();


        globalFunctions = AppController.getInstance().getGlobalFunctions();
        globalVariables = AppController.getInstance().getGlobalVariables();

        linearLayoutManager = new LinearLayoutManager(activity);
        offerprogressActivity = findViewById(R.id.details_progressActivity_reviews);
        AllLockedprogressActivity = findViewById(R.id.locked_progressActivity);
        empty_offer = findViewById(R.id.empty_offer);
        hotel_ll = findViewById(R.id.hotel_ll);
        upgrade_prime_rl = findViewById(R.id.upgrade_prime_rl);


        reviewLinear = new LinearLayoutManager(activity);
        reviewprogressActivity = findViewById(R.id.details_progressActivity);
        //review_swipe_container = findViewById(R.id.swipe_container);
        review_list_recyclerview = findViewById(R.id.raecyclerview_review_rating);
        review_list_recyclerview.setAdapter(reviewListAdapter);


        vendor_list_image = findViewById(R.id.vendor_list_image);
        // vendor_list_image.setAlpha(0.75f);

        iv_favourite = findViewById(R.id.iv_favourite);

        tv_vendor_name = findViewById(R.id.tv_vendor_name);
        tv_rating_count = findViewById(R.id.tv_count_rating);
        tvRating = findViewById(R.id.tvRating);
        tv_address = findViewById(R.id.tv_address);
        tv_open_map = findViewById(R.id.tv_open_map);
        Tv_allLockedOffers = findViewById(R.id.Tv_allLockedOffers);


        offer_list_recyclerview = findViewById(R.id.recyclerview_locked_offer);
        offer_list_recyclerview.setAdapter(offerListAdapter);

        mainView = vendor_list_image;


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


        if (getIntent().hasExtra(BUNDLE_VENDOR_LIST_DETAILS)) {
            trendingModel = (TrendingModel) getIntent().getSerializableExtra(BUNDLE_VENDOR_LIST_DETAILS);
        } else {
            trendingModel = null;
        }

        if (getIntent().hasExtra(BUNDLE_WISHLIST_MODEL)) {
            wishModel = (WishModel) getIntent().getSerializableExtra(BUNDLE_WISHLIST_MODEL);
        } else {
            wishModel = null;
        }
        if (wishModel != null) {
            if (GlobalFunctions.isNotNullValue(wishModel.getId())) {
                Log.d(TAG, wishModel.getId());
                vendor_id = wishModel.getId();
            }
        }


        if (trendingModel != null) {
            if (GlobalFunctions.isNotNullValue(trendingModel.getId())) {
                Log.d(TAG, trendingModel.getId());
                vendor_id = trendingModel.getId();
            }
        }

        if (getIntent().hasExtra(BUNDLE_VENDOR_STORE_LIST_DETAILS)) {
            vendorStoreModel = (VendorStoreModel) getIntent().getSerializableExtra(BUNDLE_VENDOR_STORE_LIST_DETAILS);

        } else {
            vendorStoreModel = null;
        }

        if (getIntent().hasExtra(BUNDLE_VENDOR_MODEL)) {

            vendorModel = (VendorModel) getIntent().getSerializableExtra(BUNDLE_VENDOR_MODEL);
        } else {
            vendorModel = null;
        }

        if (vendorStoreModel != null) {
            if (GlobalFunctions.isNotNullValue(vendorStoreModel.getId())) {
                Log.d(TAG, vendorStoreModel.getId());
                vendor_id = vendorStoreModel.getId();
            }
        }

        if (getIntent().hasExtra(BUNDLE_BANNER_CLICK_DETAILS)) {

            bannerModel = (BannerModel) getIntent().getSerializableExtra(BUNDLE_BANNER_CLICK_DETAILS);
        } else {
            bannerModel = null;
        }

        if (bannerModel != null) {
            if (GlobalFunctions.isNotNullValue(bannerModel.getVendor_id())) {
                Log.d(TAG, bannerModel.getVendor_id());
                vendor_id = bannerModel.getVendor_id();
            }
        }

        if (getIntent().hasExtra(BUNDLE_NEARBY_PLACES)) {

            nearbyModel = (NearbyModel) getIntent().getSerializableExtra(BUNDLE_NEARBY_PLACES);
        } else {
            nearbyModel = null;
        }

        if (nearbyModel != null) {
            if (GlobalFunctions.isNotNullValue(nearbyModel.getId())) {
                Log.d(TAG, nearbyModel.getId());
                vendor_id = nearbyModel.getId();
            }
        }


        if (getIntent().hasExtra(BUNDLE_RECENT_COUPON)) {

            recentCouponModel = (RecentCouponModel) getIntent().getSerializableExtra(BUNDLE_RECENT_COUPON);
        } else {
            recentCouponModel = null;
        }
        if (recentCouponModel != null) {
            if (GlobalFunctions.isNotNullValue(recentCouponModel.getId())) {
                Log.d(TAG, recentCouponModel.getId());
                vendor_id = recentCouponModel.getId();
            }
        }





        Tv_allLockedOffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOrderStatusDialog();

            }
        });


        setTitle(getString(R.string.offers), 0, 0);

        loadVendorlistDetails();

         iv_favourite.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 checkWishlist();


             }
         });


        getOfferList();

        getReviews();

    }

    private void checkWishlist() {
        // globalFunctions.showProgress(activity, activity.getString(R.string.loading));
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.getCheckWishList(context, vendor_id, new ServerResponseInterface() {
            @SuppressLint("LongLogTag")
            @Override
            public void OnSuccessFromServer(Object arg0) {
                //globalFunctions.hideProgress();
                Log.d(TAG, "Response : " + arg0.toString());
                //StatusModel model = (StatusModel) arg0;
                validateOutputAfterWishList(arg0);
            }

            @SuppressLint("LongLogTag")
            @Override
            public void OnFailureFromServer(String msg) {
                //  globalFunctions.hideProgress();
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Failure : " + msg);
            }

            @SuppressLint("LongLogTag")
            @Override
            public void OnError(String msg) {
                // globalFunctions.hideProgress();
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Error : " + msg);
            }
        }, "Register_User");
    }

    private void validateOutputAfterWishList(Object arg0) {
        if (arg0 instanceof StatusMainModel) {
            StatusMainModel statusMainModel = (StatusMainModel) arg0;
            StatusModel statusModel = statusMainModel.getStatusModel();
            globalFunctions.displayMessaage(activity, mainView, statusModel.getMessage());
            if (statusMainModel.isStatusLogin()) {
                if (isWishlisted){
                    //not wishlist icon
                    iv_favourite.setImageResource(R.drawable.ic_like);
                    isWishlisted=false;

                }else {
                    //wishlist icon
                    isWishlisted=true;
                    iv_favourite.setImageResource(R.drawable.ic_favorite);

                }

            }
        }
    }

    private void openOrderStatusDialog() {
        View view = layoutInflater.inflate(R.layout.all_locked_offer_alert, null, false);
        BottomSheetDialog alertView = new BottomSheetDialog(activity);
        alertView.setContentView(view);
        alertView.setCancelable(true);
        RecyclerView prime_offer = alertView.findViewById(R.id.recyclerview_Rezqplus_offer);
        Button btn_upgrade_prime_offer=alertView.findViewById(R.id.btn_upgrade_prime_offer);

        btn_upgrade_prime_offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(VendorListDetailsActivity.this, FreeMembershipActivity.class);
                startActivity(intent);
            }
        });



        if (offerListlocked.size() <= 0) {
            showEmptyPage();
        } else {
            showContent();
            setStatusRecyclerview(prime_offer);
        }
        /*if (offerListlocked.size()>0) {
            setStatusRecyclerview(prime_offer);
        }
*/
        alertView.show();
    }

    private void showContent() {
        if (AllLockedprogressActivity != null) {
            AllLockedprogressActivity.showContent();
        }
    }

    private void showEmptyPage() {
        if (AllLockedprogressActivity != null) {
            AllLockedprogressActivity.showEmpty(getResources().getDrawable(R.drawable.rezq_logo), getString(R.string.emptyList),
                    getString(R.string.no_offers));
        }
    }

    private void setStatusRecyclerview(RecyclerView prime_offer) {
        linearLayoutManager = new LinearLayoutManager(activity);
        offerListAdapter = new OfferListAdapter(activity, vendor_id, vendorModel, offerListlocked);
        prime_offer.setLayoutManager(linearLayoutManager);
        prime_offer.setAdapter(offerListAdapter);
        prime_offer.setHasFixedSize(true);

    }

    private void getReviews() {
        //GlobalFunctions.showProgress(context, getString(R.string.loading));
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.getVendorDetails(context, vendor_id, new ServerResponseInterface() {
            @SuppressLint("LongLogTag")
            @Override
            public void OnSuccessFromServer(Object arg0) {
                // GlobalFunctions.hideProgress();
                Log.d(TAG, "Response : " + arg0.toString());
                VendorDetailsMainModel vendorMainModel = (VendorDetailsMainModel) arg0;
                VendorModel vendorModel = vendorMainModel.getVendorModel();
                ReviewListModel reviewListModel = vendorModel.getReviewListModel();

                if (reviewListModel != null) {
                    setReviewPage(reviewListModel);
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
                //GlobalFunctions.hideProgress();
                Log.d(TAG, "Error : " + msg);
                GlobalFunctions.displayMessaage(context, mainView, msg);
            }
        }, "Review List");

    }



    private void setReviewPage(ReviewListModel reviewListModel) {
        if (reviewListModel != null && reviewModels != null) {
            reviewModels.clear();
            reviewModels.addAll(reviewListModel.getReviewModels());
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
        servicesMethodsManager.getVendorDetails(context, vendor_id, new ServerResponseInterface() {
            @SuppressLint("LongLogTag")
            @Override
            public void OnSuccessFromServer(Object arg0) {
                // GlobalFunctions.hideProgress();
                Log.d(TAG, "Response : " + arg0.toString());
                VendorDetailsMainModel vendorMainModel = (VendorDetailsMainModel) arg0;
                VendorModel vendorModel = vendorMainModel.getVendorModel();
                OfferListModel offerModel = vendorModel.getOfferListModel();

                if (offerModel != null) {
                    setThisPage(offerModel);
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
                //GlobalFunctions.hideProgress();
                Log.d(TAG, "Error : " + msg);
                GlobalFunctions.displayMessaage(context, mainView, msg);
            }
        }, "Offer List");
    }

    private void setThisPage(OfferListModel offerListModel) {
        if (offerListModel != null && offerListModels != null) {
            offerListModels.clear();
//            offerListModels.addAll(offerListModel.getOfferModels());
           /* if (offerListAdapter != null) {
                synchronized (offerListAdapter) {
                    offerListAdapter.notifyDataSetChanged();
                }
            }*/

            offerListModels.addAll(GlobalFunctions.getOfferList(offerListModel.getOfferModels(), true));
            offerListlocked.addAll(GlobalFunctions.getOfferList(offerListModel.getOfferModels(), false));


            if (offerListAdapter != null) {
                synchronized (offerListAdapter) {
                    offerListAdapter.notifyDataSetChanged();
                }
            }

            if (offerListModels.size() <= 0) {
                showOfferEmptyPage();
            } else {
                empty_offer.setVisibility(View.GONE);
                hotel_ll.setVisibility(View.VISIBLE);
                showOfferContent();
                offerRecyclerView();
            }
        }
    }


    private void showOfferEmptyPage() {
       empty_offer.setVisibility(View.VISIBLE);
        hotel_ll.setVisibility(View.GONE);
    }

    private void offerRecyclerView() {
        offer_list_recyclerview.setLayoutManager(linearLayoutManager);
        offer_list_recyclerview.setHasFixedSize(true);
        offerListAdapter = new OfferListAdapter(activity, vendor_id, vendorModel, offerListModels);
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
        servicesMethodsManager.getVendorDetails(context, vendor_id, new ServerResponseInterface() {
            @SuppressLint("LongLogTag")
            @Override
            public void OnSuccessFromServer(Object arg0) {
                GlobalFunctions.hideProgress();
                Log.d(TAG, "Response : " + arg0.toString());
                VendorDetailsMainModel vendorMainModel = (VendorDetailsMainModel) arg0;
                vendorModel = vendorMainModel.getVendorModel();


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
            if (GlobalFunctions.isNotNullValue(vendorModel.getWishlist())) {
                if (vendorModel.getWishlist().equalsIgnoreCase("0")) {
                    iv_favourite.setImageResource(R.drawable.ic_like);
                    isWishlisted=false;
                } else {
                    iv_favourite.setImageResource(R.drawable.ic_favorite);
                    isWishlisted=true;
                }

                // Picasso.with(context).load(vendorModel.getImage()).placeholder(R.drawable.rezq_logo).into(vendor_list_image);

            }
            /*if (GlobalFunctions.isNotNullValue(vendorModel.getImage())) {
                Picasso.with(context).load(vendorModel.getImage()).placeholder(R.drawable.rezq_logo).into(vendor_list_image);

            }*/
            if (GlobalFunctions.isNotNullValue(vendorModel.getLogo())) {
                Picasso.with(context).load(vendorModel.getLogo()).placeholder(R.drawable.rezq_logo).into(vendor_list_image);

            }
            if (GlobalFunctions.isNotNullValue(vendorModel.getName())) {
                tv_vendor_name.setText(vendorModel.getName());

            }
            if (GlobalFunctions.isNotNullValue(vendorModel.getAddress())) {
                tv_address.setText(vendorModel.getAddress());

            }
            if (GlobalFunctions.isNotNullValue(vendorModel.getRating_count())) {
                tvRating.setText(vendorModel.getAvg_rating());

            }
            if (GlobalFunctions.isNotNullValue(vendorModel.getRating_count())) {
                tv_rating_count.setText(vendorModel.getRating_count());

            }
            if (vendorModel != null) {

                if (GlobalFunctions.isNotNullValue(vendorModel.getLatitude())) {
                    latitude = vendorModel.getLatitude();
                }
                if (GlobalFunctions.isNotNullValue(vendorModel.getLongitude())) {
                    longitude = vendorModel.getLongitude();
                }
            }

            tv_open_map.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (vendorModel!=null){
                        if (GlobalFunctions.isNotNullValue(vendorModel.getLatitude()) || GlobalFunctions.isNotNullValue( vendorModel.getLongitude())) {

                            try {
                                String strUri = "http://maps.google.com/maps?q=loc:" + latitude + "," + longitude ;
                                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));
                                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                                startActivity(intent);
                            }catch (ActivityNotFoundException e){
                                Uri uri=Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps,maps");
                                Intent intent=new Intent(Intent.ACTION_VIEW,uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);

                            }
                        }
                    }
                }
            });


        }
    }


    @Override
    public void onStop() {
        super.onStop();
        shouldRefreshOnResume = true;
    }

    public static void setTitle(String title, int titleImageID, int backgroundResourceID) {
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
    private static void restoreToolbar() {
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

    public void onBackPressed() {

        closeThisActivity();
        super.onBackPressed();
    }

    public static void closeThisActivity() {
        if (activity != null) {
            activity.finish();
            //activity.overridePendingTransition(R.anim.zoom_in,R.anim.zoom_out);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getFragmentManager().findFragmentByTag(TAG) != null)
            getFragmentManager().findFragmentByTag(TAG).setRetainInstance(true);
    }

    @Override
    public void onStart() {

       /* if(hint != null) {
            hint.launchAutomaticHintForCall(activity.findViewById(R.id.action_call));
        }*/
//       globalFunctions.launchAutomaticHintForSearch(mainView, getString(R.string.search_title),  getString(R.string.search_description));
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
