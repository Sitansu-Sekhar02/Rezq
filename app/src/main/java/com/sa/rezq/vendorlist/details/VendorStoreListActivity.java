package com.sa.rezq.vendorlist.details;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sa.rezq.Activity.AppController;
import com.sa.rezq.R;
import com.sa.rezq.adapter.VendorListAdapter;
import com.sa.rezq.global.GlobalFunctions;
import com.sa.rezq.global.GlobalVariables;
import com.sa.rezq.services.ServerResponseInterface;
import com.sa.rezq.services.ServicesMethodsManager;
import com.sa.rezq.services.model.BannerModel;
import com.sa.rezq.services.model.CategoryModel;
import com.sa.rezq.services.model.NearbyModel;
import com.sa.rezq.services.model.SeeAllCategoryModel;
import com.sa.rezq.services.model.TrendingModel;
import com.sa.rezq.services.model.VendorModel;
import com.sa.rezq.services.model.VendorRTModel;
import com.sa.rezq.services.model.VendorStoreModel;
import com.sa.rezq.services.model.VendorStoreMainModel;
import com.vlonjatg.progressactivity.ProgressLinearLayout;

import java.util.ArrayList;
import java.util.List;

public class VendorStoreListActivity extends AppCompatActivity {

    public static final String TAG = "VendorStoreListActivity",
            BUNDLE_VENDOR_STORE_LIST_DETAILS = "VendorStoreListActivity",
            BUNDLE_TRENDING_DETAILS = "TrendingDetails",
            BUNDLE_BANNER_LIST = "BannerList",
            BUNDLE_ALL_CATEGORY_LIST = "AllCategoryList",
            BUNDLE_SHOW_TRENDING_LIST = "ShowTrendingList",
            BUNDLE_NEARBY_PLACES = "NearByPlaces",
          BUNDLE_CATEGORY_LIST = "CategoryList";

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

    String vendor_id = null;
    String category_id = null;
    NearbyModel nearbyModel=null;



    VendorListAdapter vendorListAdapter;
    List<VendorStoreModel> vendorStoreModels = new ArrayList<>();
    LinearLayoutManager storelistLinear;
    ProgressLinearLayout reviewprogressActivity;
    RecyclerView vendor_store_list_recyclerview;
    SwipeRefreshLayout review_swipe_container;

    private boolean shouldRefreshOnResume = false;
    VendorModel vendorModel = null;

    GlobalVariables globalVariables;
    GlobalFunctions globalFunctions;
    Window window = null;

    TrendingModel trendingModel = null;
    CategoryModel categoryModel=null;
    BannerModel bannerModel=null;
    SeeAllCategoryModel allCategoryModel=null;

    FirebaseStorage storage;
    StorageReference storageReference;

    String sort=null;


    public static Intent newInstance(Activity activity, TrendingModel model) {
        Intent intent = new Intent(activity, VendorStoreListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_TRENDING_DETAILS, model);
        intent.putExtras(bundle);
        return intent;
    }

    /*public static Intent newInstance(Activity activity,VendorStoreListModel vendorStoreListModel) {
        Intent intent = new Intent(activity, VendorStoreListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_VENDOR_STORE_LIST_DETAILS, vendorStoreListModel);
        intent.putExtras(bundle);
        return intent;
    }*/
    public static Intent newInstance(Activity activity, CategoryModel categoryModel) {
        Intent intent = new Intent(activity, VendorStoreListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_CATEGORY_LIST, categoryModel);
        intent.putExtras(bundle);
        return intent;
    }

    public static Intent newInstance(Activity activity, BannerModel bannerClickedModel) {
        Intent intent = new Intent(activity, VendorStoreListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_BANNER_LIST, bannerClickedModel);
        intent.putExtras(bundle);
        return intent;
    }

    public static Intent newInstance(Activity activity, SeeAllCategoryModel model) {
        Intent intent = new Intent(activity, VendorStoreListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_ALL_CATEGORY_LIST, model);
        intent.putExtras(bundle);
        return intent;
    }

    public static Intent newInstance(Activity activity, NearbyModel model) {
        Intent intent = new Intent(activity, VendorStoreListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_NEARBY_PLACES, model);
        intent.putExtras(bundle);
        return intent;
    }
    public static Intent newInstance(Activity activity, String sort) {
        Intent intent = new Intent(activity, VendorStoreListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_SHOW_TRENDING_LIST, sort);
        intent.putExtras(bundle);
        return intent;
    }


    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_list_recyclerview);

        context = this;
        activity = this;
        window = getWindow();

        globalFunctions = AppController.getInstance().getGlobalFunctions();
        globalVariables = AppController.getInstance().getGlobalVariables();

        storelistLinear = new LinearLayoutManager(activity);
        reviewprogressActivity = findViewById(R.id.details_progressActivity);

        review_swipe_container = findViewById(R.id.swipe_container);
        vendor_store_list_recyclerview =findViewById(R.id.vendor_list_recyclerview);
        vendor_store_list_recyclerview.setAdapter(vendorListAdapter);



        mainView=vendor_store_list_recyclerview;


        //trending list
        if (getIntent().hasExtra(BUNDLE_TRENDING_DETAILS)) {

            trendingModel = (TrendingModel) getIntent().getSerializableExtra(BUNDLE_TRENDING_DETAILS);
        } else {
            trendingModel = null;
        }

        if (trendingModel != null) {
            if (GlobalFunctions.isNotNullValue(trendingModel.getId())) {
                Log.d(TAG,trendingModel.getId());
                vendor_id = trendingModel.getId();
            }
        }
        //bnner list
        if (getIntent().hasExtra(BUNDLE_BANNER_LIST)) {

            bannerModel = (BannerModel) getIntent().getSerializableExtra(BUNDLE_BANNER_LIST);
        } else {
            bannerModel = null;
        }

        if (bannerModel != null) {
            if (GlobalFunctions.isNotNullValue(bannerModel.getVendor_id())) {
                Log.d(TAG,bannerModel.getVendor_id());
                vendor_id = bannerModel.getVendor_id();
            }
        }

        if (getIntent().hasExtra(BUNDLE_ALL_CATEGORY_LIST)) {

            allCategoryModel = (SeeAllCategoryModel) getIntent().getSerializableExtra(BUNDLE_ALL_CATEGORY_LIST);
        } else {
            allCategoryModel = null;
        }
        if (allCategoryModel != null) {
            if (GlobalFunctions.isNotNullValue(allCategoryModel.getCategori_id())) {
                category_id = allCategoryModel.getCategori_id();
            }
        }

        if (getIntent().hasExtra(BUNDLE_NEARBY_PLACES)) {

            nearbyModel = (NearbyModel) getIntent().getSerializableExtra(BUNDLE_NEARBY_PLACES);
        } else {
            nearbyModel = null;
        }

        if (nearbyModel != null) {
            if (GlobalFunctions.isNotNullValue(nearbyModel.getId())) {
                Log.d(TAG,nearbyModel.getId());
                vendor_id = nearbyModel.getId();
            }
        }

        //category list
        if (getIntent().hasExtra(BUNDLE_CATEGORY_LIST)) {

            categoryModel = (CategoryModel) getIntent().getSerializableExtra(BUNDLE_CATEGORY_LIST);
        } else {
            categoryModel = null;
        }
        if (categoryModel != null) {
            if (GlobalFunctions.isNotNullValue(categoryModel.getId())) {
                Log.d(TAG,categoryModel.getId());
                category_id = categoryModel.getId();
            }
        }

        if (getIntent().hasExtra(BUNDLE_SHOW_TRENDING_LIST)) {

            sort = (String) getIntent().getStringExtra(BUNDLE_SHOW_TRENDING_LIST);
        } else {
            sort= null;
        }


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

        setTitle(getString(R.string.offers), 0, 0);


        getOfferList();

        review_swipe_container.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getOfferList();
            }
        } );

    }
    private void getOfferList() {
        GlobalFunctions.showProgress(context, getString(R.string.loading));
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.getVendorStoreList(context, vendor_id,sort,category_id, new ServerResponseInterface() {
            @SuppressLint("LongLogTag")
            @Override
            public void OnSuccessFromServer(Object arg0) {
                GlobalFunctions.hideProgress();
                if (review_swipe_container.isRefreshing()) {
                    review_swipe_container.setRefreshing( false );
                }
                Log.d(TAG, "Response : " + arg0.toString());

                VendorStoreMainModel vendorStoreMainModel = (VendorStoreMainModel) arg0;
                if (vendorStoreMainModel!=null && vendorStoreMainModel.getVendorModel()!=null){
                    VendorRTModel vendorModel = vendorStoreMainModel.getVendorModel();
                    setThisPage(vendorModel);
                }

            }

            @SuppressLint("LongLogTag")
            @Override
            public void OnFailureFromServer(String msg) {
                GlobalFunctions.hideProgress();
                if (review_swipe_container.isRefreshing()) {
                    review_swipe_container.setRefreshing( false );
                }

                Log.d(TAG, "Failure : " + msg);
                GlobalFunctions.displayMessaage(context, mainView, msg);
            }

            @SuppressLint("LongLogTag")
            @Override
            public void OnError(String msg) {
                GlobalFunctions.hideProgress();
                if (review_swipe_container.isRefreshing()) {
                    review_swipe_container.setRefreshing( false );
                }
                Log.d(TAG, "Error : " + msg);
                GlobalFunctions.displayMessaage(context, mainView, msg);
            }
        }, "Store List");
    }

    private void setThisPage(VendorRTModel vendorStoreListModel) {
        if (vendorStoreListModel != null && vendorStoreModels != null) {
            vendorStoreModels.clear();
            vendorStoreModels.addAll( vendorStoreListModel.getVendorListModels() );
            if (vendorListAdapter != null) {
                synchronized (vendorListAdapter) {
                    vendorListAdapter.notifyDataSetChanged();
                }
            }
            if (vendorStoreModels.size() <= 0) {
                showOfferEmptyPage();
            } else {
                showOfferContent();
                offerRecyclerView();
            }
        }
    }

    private void showOfferEmptyPage() {
        if (reviewprogressActivity != null) {
            reviewprogressActivity.showEmpty(getResources().getDrawable(R.drawable.rezq_logo), getString(R.string.emptyList),
                    getString(R.string.no_data_show));
        }
    }

    private void offerRecyclerView() {
        vendor_store_list_recyclerview.setLayoutManager(storelistLinear);
        vendor_store_list_recyclerview.setHasFixedSize(true);
        vendorListAdapter = new VendorListAdapter(activity, vendorModel, vendorStoreModels);
        vendor_store_list_recyclerview.setAdapter(vendorListAdapter);
    }

    private void showOfferContent() {
        if (reviewprogressActivity != null) {
            reviewprogressActivity.showContent();
        }
    }


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

    @Override
    public void onResume() {
       // ((VendorStoreListActivity) activity).setTitle( getString( R.string.app_name ), R.drawable.rezq_logo, 0 );
        // ((MainActivity) activity).setTitle("", 0, 0);
        super.onResume();
        if (shouldRefreshOnResume) {
            getOfferList();
        }
     /*   if (getFragmentManager().findFragmentByTag( TAG ) != null)
            getFragmentManager().findFragmentByTag( TAG ).getRetainInstance();*/
    }


}
