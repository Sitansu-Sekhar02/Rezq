package com.sa.rezq.home;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.sa.rezq.Activity.AppController;
import com.sa.rezq.R;
import com.sa.rezq.adapter.CategoryListAdapter;
import com.sa.rezq.adapter.PlacesNearyouListAdapter;
import com.sa.rezq.adapter.TrendingListAdapter;
import com.sa.rezq.category.AllCategoryListActivity;
import com.sa.rezq.global.GlobalFunctions;
import com.sa.rezq.global.GlobalVariables;
import com.sa.rezq.location_service.LocationMonitoringService;
import com.sa.rezq.membership.UpgradeParticularMembershipActivity;
import com.sa.rezq.search.SearchActivity;
import com.sa.rezq.services.ServerResponseInterface;
import com.sa.rezq.services.ServicesMethodsManager;
import com.sa.rezq.services.model.BannerListModel;
import com.sa.rezq.services.model.BannerModel;
import com.sa.rezq.services.model.CategoryListModel;
import com.sa.rezq.services.model.CategoryModel;
import com.sa.rezq.services.model.HomePageMainModel;
import com.sa.rezq.services.model.HomePageModel;
import com.sa.rezq.services.model.LocationModel;
import com.sa.rezq.services.model.NearbyListModel;
import com.sa.rezq.services.model.NearbyModel;
import com.sa.rezq.services.model.ProfileMembershipModel;
import com.sa.rezq.services.model.SearchResponseModel;
import com.sa.rezq.services.model.SeeAllCategoryModel;
import com.sa.rezq.services.model.TrendingListModel;
import com.sa.rezq.services.model.TrendingModel;
import com.sa.rezq.services.model.VariantModel;
import com.sa.rezq.services.model.VendorModel;
import com.sa.rezq.services.model.VendorStoreModel;
import com.sa.rezq.vendorlist.details.VendorListDetailsActivity;
import com.sa.rezq.vendorlist.details.VendorStoreListActivity;
import com.sa.rezq.view.CustomSliderTextView;
import com.vlonjatg.progressactivity.ProgressLinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class HomeFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    public static final String TAG = "HomeFragment";
    public static final String TAG_Category = "categorylist";
    public static final String TAG_nearby = "Nearbyplace";
    public static final String TAG_trending = "Nearbyplace";
    public static final String BUNDLE_PRODUCT_MODEL = "BundleProductModel";

    public static final String BUNDLE_SEARCH_RESPONSE_MODEL = "Bundle_Search_Response_Model";

    public static final int SLIDER_LOAD_TIME_IN_MILLI_SEC=2000;

    Activity activity;
    Context context;

    View mainView;



    CategoryModel categoryModel=null;
    CategoryListAdapter categoryAdapter;
    List<CategoryModel> categoryList = new ArrayList<>();
    LinearLayoutManager reviewLayoutManager;
    ProgressLinearLayout progressActivity;
    RecyclerView CategoryRecyclerView;

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;

    NearbyModel nearbyModel=null;
    PlacesNearyouListAdapter placesNearyouListAdapter;
    List<NearbyModel> nearbyModelList = new ArrayList<>();
    LinearLayoutManager nearbyplaces;
    ProgressLinearLayout nearbyprogressLinearLayout;
    RecyclerView nearbyRecyclerView;



    TrendingModel trendingModel=null;
    TrendingListAdapter trendingListAdapter;
    List<TrendingModel> trendingModelList = new ArrayList<>();
    LinearLayoutManager trendinLinearLayout;
    ProgressLinearLayout trendinProgress;
    RecyclerView trending_RecyclerView;


    String productId = null, variationId = null;
    String selectedVariantId = null,
            selectedSubVariantId = null;
    VariantModel selectedSubVariantModel = null;

    String vendorId = null;
    GlobalFunctions globalFunctions;
    GlobalVariables globalVariables;

    SliderLayout mProductSlider;
    PagerIndicator mPagerIndicator;
    TextView create_RFP_now_tv;
    BannerListModel banners;
    RelativeLayout rl_prime_upgrade;


    ViewPager mViewPager;
    Timer timer;
    TabLayout tabLayout;
    int currentPage = 0;

    TextView seeAllPopular_category,tv_all_trending,tv_all_nearby;
    TextView upgrade_membership,tv_gold_membership;
    String membership_id=null;
    CardView cardViewItem;
    CardView search_card_view;

    SeeAllCategoryModel listModel;
    LocationModel locationModel;
   // Window window = null;

    static Intent locationintent;
    private boolean mAlreadyStartedService = false;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);


        activity = getActivity();
        context = getActivity();
       // window =getActivity().getWindow();


        globalFunctions = AppController.getInstance().getGlobalFunctions();
        globalVariables = AppController.getInstance().getGlobalVariables();

        mProductSlider = (SliderLayout) view.findViewById(R.id.shop_main_fragment_top_slider);
        mPagerIndicator = (PagerIndicator) view.findViewById(R.id.shop_main_fragment_top_slider_custom_indicator);
        reviewLayoutManager = new LinearLayoutManager(activity);



        progressActivity = view.findViewById( R.id.details_progressActivity );
        nearbyprogressLinearLayout = view.findViewById( R.id.details_progressActivity_nearyou );
        trendinProgress = view.findViewById( R.id.details_progressActivity_trending );


        mainView = mProductSlider;


        CategoryRecyclerView = view.findViewById(R.id.Rvcategory);
        trending_RecyclerView = view.findViewById(R.id.recycler_trending);
        nearbyRecyclerView =view.findViewById(R.id.recycler_places_near_you);
        seeAllPopular_category=view.findViewById(R.id.TvseeAllCategory);
        tv_all_trending=view.findViewById(R.id.tv_all_trending);
        tv_all_nearby=view.findViewById(R.id.tv_all_nearby);
        upgrade_membership=view.findViewById(R.id.tv_upgrade_membership);
        cardViewItem=view.findViewById(R.id.cardItem);
        search_card_view=view.findViewById(R.id.search_card_view);
        tv_gold_membership=view.findViewById(R.id.tv_gold_membership);
        rl_prime_upgrade=view.findViewById(R.id.rl_prime_upgrade);

        locationintent = new Intent(activity, LocationMonitoringService.class);

        loadMenu(context);

        search_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, SearchActivity.class);
                startActivityForResult(intent,GlobalVariables.REQUEST_CODE_FOR_SEARCH);
            }
        });




        if (!mAlreadyStartedService) {
            startStep1();
        } else {
            //Intent intent = new Intent(activity, LocationMonitoringService.class);
            // activity.stopService(locationintent);
            // stopService(new Intent(SummaryActivity.this, LocationMonitoringService.class));
            //mAlreadyStartedService = false;
        }

        seeAllPopular_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AllCategoryListActivity.newInstance( activity, listModel );
                activity.startActivity( intent );
            }
        });

        tv_all_trending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = VendorStoreListActivity.newInstance( activity, "1" );
                activity.startActivity( intent );
            }
        });
        tv_all_nearby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = VendorStoreListActivity.newInstance( activity, "2" );
               activity.startActivity( intent );
            }
        });


        loadCategory();

        loadNearbyPLaces();

        TrendingCategory();

        return view;
    }

    private void TrendingCategory() {
        //globalFunctions.showProgress(activity, getString(R.string.loading));
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.getTrendingList(context, new ServerResponseInterface() {
            @Override
            public void OnSuccessFromServer(Object arg0) {
                if (context != null) {
                    //globalFunctions.hideProgress();
                    Log.d(TAG_trending, "Response: " + arg0.toString());
                    HomePageMainModel homePageMainModel=(HomePageMainModel) arg0;
                    HomePageModel homePageModel = homePageMainModel.getHomePageModel();
                    TrendingListModel trendingListModel=homePageModel.getTrendingListModel();
                    setUpTrendingPage(trendingListModel);
                }
            }

            @Override
            public void OnFailureFromServer(String msg) {
                if (context != null) {
                   // globalFunctions.hideProgress();
                    Log.d(TAG_trending, "Failure : " + msg);
                }
            }

            @Override
            public void OnError(String msg) {
                if (context != null) {
                    // globalFunctions.hideProgress();
                    Log.d(TAG_trending, "Error : " + msg);
                }
            }
        }, "Trending list");

    }

    private void setUpTrendingPage(TrendingListModel trendingListModel) {
        if (trendingListModel != null && trendingModelList != null) {
            trendingModelList.clear();
//            trendingModelList.addAll( trendingListModel.getTrendingModels() );

            setUpdatedList(trendingListModel.getTrendingModels() );

            if (trendingListAdapter != null) {
                synchronized (trendingListAdapter) {
                    trendingListAdapter.notifyDataSetChanged();
                }
            }
            if (trendingModelList.size() <= 0) {
                showNearbyEmptyPage();
            } else {
                showTrendingContent();
                trendingRecyclerView();
            }
        }
    }

    private void setUpdatedList(List<TrendingModel> trendingListModel) {
        for (int i=0;i<trendingListModel.size();i++){
            TrendingModel trendingModel=trendingListModel.get(i);

            if (i==1 || i%3==1){
                trendingModelList.add(trendingModel);
            }else if (i==2 || i%3==2){
                trendingModelList.get(i-1).setTraTrendingModel(trendingModel);
            }else {
                trendingModelList.add(trendingModel);
            }

        }
    }

    private void showTrendingContent() {
        if (trendinProgress != null) {
            trendinProgress.showContent();
        }
    }

    private void trendingRecyclerView() {
        // nearbyRecyclerView.setLayoutManager(reviewLayoutManager);
        trending_RecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        trending_RecyclerView.setHasFixedSize(true);
        trendingListAdapter = new TrendingListAdapter(activity, trendingModelList);
        trending_RecyclerView.setAdapter(trendingListAdapter);
    }

    private void loadNearbyPLaces() {
        //globalFunctions.showProgress(activity, getString(R.string.loading));
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.getNearbyList(context, new ServerResponseInterface() {
            @Override
            public void OnSuccessFromServer(Object arg0) {
                if (context != null) {
                    //globalFunctions.hideProgress();
                    Log.d(TAG_nearby, "Response: " + arg0.toString());
                    HomePageMainModel homePageMainModel=(HomePageMainModel) arg0;
                    HomePageModel homePageModel = homePageMainModel.getHomePageModel();
                    NearbyListModel nearbyListModel=homePageModel.getNearbyListModel();
                    setUpNearbyPage(nearbyListModel);
                }
            }

            @Override
            public void OnFailureFromServer(String msg) {
                if (context != null) {
                    globalFunctions.hideProgress();
                    Log.d(TAG_nearby, "Failure : " + msg);
                }
            }

            @Override
            public void OnError(String msg) {
                if (context != null) {
                   // globalFunctions.hideProgress();
                    Log.d(TAG_nearby, "Error : " + msg);
                }
            }
        }, "Nearbylist");
    }

    private void setUpNearbyPage(NearbyListModel nearbyListModel) {
        if (nearbyListModel != null && nearbyModelList != null) {
            nearbyModelList.clear();
            nearbyModelList.addAll( nearbyListModel.getNearbyModels() );
            if (placesNearyouListAdapter != null) {
                synchronized (categoryAdapter) {
                    placesNearyouListAdapter.notifyDataSetChanged();
                }
            }

            if (nearbyModelList.size() <= 0) {
                showNearbyEmptyPage();
            } else {
                showNearbyContent();
               nearbyRecyclerView();
            }
        }
    }

    private void nearbyRecyclerView() {
        // nearbyRecyclerView.setLayoutManager(reviewLayoutManager);
        nearbyRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        nearbyRecyclerView.setHasFixedSize(true);
        placesNearyouListAdapter = new PlacesNearyouListAdapter(activity, nearbyModelList);
        nearbyRecyclerView.setAdapter(placesNearyouListAdapter);
    }

    private void showNearbyContent() {
        if (nearbyprogressLinearLayout != null) {
            nearbyprogressLinearLayout.showContent();
        }
    }

    private void showNearbyEmptyPage() {
        if (nearbyprogressLinearLayout != null) {
            nearbyprogressLinearLayout.showEmpty(getResources().getDrawable(R.drawable.rezq_logo), getString(R.string.emptyList),
                    getString(R.string.not_available));
        }
    }

    private void loadCategory() {

        globalFunctions.showProgress(activity, getString(R.string.loading));
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.getCategoryList(context, new ServerResponseInterface() {
            @Override
            public void OnSuccessFromServer(Object arg0) {
                if (context != null) {
                    globalFunctions.hideProgress();
                    Log.d(TAG_Category, "Response: " + arg0.toString());
                    HomePageMainModel homePageMainModel=(HomePageMainModel) arg0;
                    HomePageModel homePageModel = homePageMainModel.getHomePageModel();
                    CategoryListModel categoryModel=homePageModel.getCategoryList();
                    setUpPageCategoryplace(categoryModel);
                }
            }

            @Override
            public void OnFailureFromServer(String msg) {
                if (context != null) {
                    globalFunctions.hideProgress();
                    Log.d(TAG_Category, "Failure : " + msg);
                }
            }

            @Override
            public void OnError(String msg) {
                if (context != null) {
                    //globalFunctions.hideProgress();
                    Log.d(TAG_Category, "Error : " + msg);
                }
            }
        }, "Category list");


    }

    private void setUpPageCategoryplace(CategoryListModel categoryListModel) {

        if (categoryListModel != null && categoryList != null) {
            categoryList.clear();
            categoryList.addAll( categoryListModel.getCategoryList() );
            if (categoryAdapter != null) {
                synchronized (categoryAdapter) {
                    categoryAdapter.notifyDataSetChanged();
                }
            }
            if (categoryList.size() <= 0) {
                showEmptyPage();
            } else {
                showContent();
                initRecyclerView();
            }
        }
    }

    private void showContent() {
        if (progressActivity != null) {
            progressActivity.showContent();
        }
    }

    private void showEmptyPage() {
        if (progressActivity != null) {
            progressActivity.showEmpty(getResources().getDrawable(R.drawable.rezq_logo), getString(R.string.emptyList),
                    getString(R.string.not_available));
        }
    }

    private void initRecyclerView() {
       // CategoryRecyclerView.setLayoutManager(reviewLayoutManager);
        CategoryRecyclerView.setHasFixedSize(true);
        categoryAdapter = new CategoryListAdapter(activity, categoryList);
        CategoryRecyclerView.setAdapter(categoryAdapter);
    }





    private void loadMenu(final Context context) {
//        globalFunctions.showProgress(activity, getString(R.string.loading));
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.getHomeData(context, new ServerResponseInterface() {
            @Override
            public void OnSuccessFromServer(Object arg0) {
               // globalFunctions.hideProgress();
                Log.d(TAG, "Response : " + arg0.toString());
                if (arg0 instanceof HomePageMainModel) {
                    HomePageMainModel homePageMainModel= (HomePageMainModel) arg0;
                    setThisPage(homePageMainModel.getHomePageModel());

                }
            }

            @Override
            public void OnFailureFromServer(String msg) {
                //globalFunctions.hideProgress();
                globalFunctions.displayMessaage(context, mainView, msg);

                Log.d(TAG, "Failure : " + msg);
            }

            @Override
            public void OnError(String msg) {
               // globalFunctions.hideProgress();
                globalFunctions.displayMessaage(context, mainView, msg);
                Log.d(TAG, "Error : " + msg);
            }
        }, "Menu");
    }

    private void setThisPage(final HomePageModel homePageModel) {
        if (homePageModel != null) {
            if (homePageModel.getProfileMembershipModel()!=null) {
                ProfileMembershipModel profileMembershipModel = homePageModel.getProfileMembershipModel();
                if (profileMembershipModel!=null){
                    globalFunctions.setProfileMembership(context,profileMembershipModel);
                    if (GlobalFunctions.isNotNullValue(profileMembershipModel.getIs_premium())){
                            if(profileMembershipModel.getIs_premium()!=null){
                                rl_prime_upgrade.setVisibility(View.VISIBLE);
                                if (GlobalFunctions.isNotNullValue(profileMembershipModel.getUpgrade_id())){
                                    membership_id=profileMembershipModel.getUpgrade_id();
                                }
                                if (GlobalFunctions.isNotNullValue(profileMembershipModel.getUpgrade_title())){
                                    upgrade_membership.setText(profileMembershipModel.getUpgrade_title());
                                    upgrade_membership.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = UpgradeParticularMembershipActivity.newInstance( activity, membership_id);
                                            activity.startActivity( intent);

                                        }
                                    });
                                }else if (profileMembershipModel.getUpgrade_title().equalsIgnoreCase("")){
                                    rl_prime_upgrade.setVisibility(View.GONE);

                                }else{

                                }

                            }else if(profileMembershipModel.getIs_premium().equalsIgnoreCase("1")){
                                rl_prime_upgrade.setVisibility(View.GONE);
                                upgrade_membership.setVisibility(View.GONE);
                            }else{

                            }
                    }else {
                        rl_prime_upgrade.setVisibility(View.GONE);
                        upgrade_membership.setVisibility(View.GONE);

                    }
                   }
                }

            if (homePageModel.getBannerList() != null) {
                BannerListModel section2 = homePageModel.getBannerList();
                if (section2 != null) setBanners(section2);
            }

        }
    }

    private void setBanners(BannerListModel bannerListModel) {
        if (bannerListModel != null) {
            if (banners == null) {
                BannerTask bannerTask = new BannerTask();
                bannerTask.execute(bannerListModel);
            } else if (banners.getBannerList().size() != bannerListModel.getBannerList().size()) {
                BannerTask bannerTask = new BannerTask();
                bannerTask.execute(bannerListModel);
            }
        }
    }

    @Override
    public void onResume() {
//        MainActivity.setNavigationBottomIcon(TAG);
        super.onResume();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getFragmentManager().findFragmentByTag(TAG) != null)
            getFragmentManager().findFragmentByTag(TAG).setRetainInstance(true);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onSliderClick(BaseSliderView slider) {
        BannerModel bannerClickedModel = (BannerModel) slider.getBundle().getSerializable("data");
        if (bannerClickedModel != null) {
            if (bannerClickedModel.getVendor_id() != null) {

                if(bannerClickedModel.getCount().equalsIgnoreCase("1")){
                    Intent intent = VendorListDetailsActivity.newInstance( activity, bannerClickedModel );
                    activity.startActivity( intent );
                }else {
                    Intent intent = VendorStoreListActivity.newInstance( activity, bannerClickedModel );
                    activity.startActivity( intent );
                }
               /* if (bannerClickedModel.getType().equalsIgnoreCase(globalVariables.TYPE_CATEGORY)) {
                    Intent intent = VendorStoreListActivity.newInstance(activity, bannerClickedModel.getVendor_id(), null, null);
                    activity.startActivity(intent);
                }  else if (bannerClickedModel.getType().equalsIgnoreCase(globalVariables.TYPE_OFFER)) {
                    Intent intent = ProductListActivity.newInstance(activity, null, null, bannerClickedModel.getId());
                    activity.startActivity(intent);
                } else if (bannerClickedModel.getType().equalsIgnoreCase(globalVariables.TYPE_NORMAL)) {
                    if (bannerClickedModel.getUrl() != null) {
                        try {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(bannerClickedModel.getUrl()));
                            startActivity(browserIntent);
                        } catch (ActivityNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }*/

            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    public class BannerTask extends AsyncTask<BannerListModel, Void, List<String>> {

        @Override
        protected List<String> doInBackground(BannerListModel... bannerListModels) {
            BannerListModel bannerListModel = bannerListModels[0];
            if (bannerListModel != null) banners = bannerListModel;
            List<String> bannerStrings = new ArrayList<>();
            if (banners != null) {
                for (int i = 0; i < banners.getBannerList().size(); i++) {
                    String imageModel = banners.getBannerList().get(i).getImage();
                    //if url is not empty...add url...else don't add...
                    if (GlobalFunctions.isNotNullValue(imageModel)) {
                        bannerStrings.add(imageModel);
                    }
                }
            }
            if (mProductSlider != null) mProductSlider.removeAllSliders();
            return bannerStrings;
        }

        @Override
        protected void onPostExecute(List<String> bannerStrings) {
            super.onPostExecute(bannerStrings);
            setImageOnView(bannerStrings);
        }
    }

    public void setImageOnView(List<String> displayImageURL) {
        if (displayImageURL != null) {
            if (mProductSlider.getChildCount() <= 1) {
                Log.d(TAG, "ImageURL List size : " + displayImageURL.size());
                for (int i = 0; i < displayImageURL.size() && displayImageURL.get(i) != null; i++) {
                    CustomSliderTextView textSliderView = new CustomSliderTextView(context);
                    // initialize a SliderLayout
                    textSliderView
                            .description(1 + "")
                            .image(displayImageURL.get(i).toString())
                            .setScaleType(BaseSliderView.ScaleType.Fit)
                            .setOnSliderClickListener(this);
                    //add your extra information
                    textSliderView.bundle(new Bundle());
                    textSliderView.getBundle()
                            .putString("extra", banners.getBannerList().get(i).getId() + "");
                    textSliderView.getBundle()
                            .putSerializable("data", banners.getBannerList().get(i));

                    mProductSlider.addSlider(textSliderView);
                    if (displayImageURL.size() > 0) {
                        mProductSlider.setCurrentPosition(0);
                    }

                }

                if (displayImageURL.size() > 0) {
                    mProductSlider.setPresetTransformer(SliderLayout.Transformer.Stack);
                    mProductSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                    if (mPagerIndicator != null) {
                        mProductSlider.setCustomIndicator(mPagerIndicator);
                        mProductSlider.setCurrentPosition(0);
                    }
                    mProductSlider.setCustomAnimation(new DescriptionAnimation());
                    mProductSlider.setDuration(SLIDER_LOAD_TIME_IN_MILLI_SEC);
                    mProductSlider.addOnPageChangeListener(this);
                    mProductSlider.startAutoCycle();
                }
            }
        }
    }


    private void startStep1() {
        //Check whether this user has installed Google play service which is being used by Location updates.
        if (isGooglePlayServicesAvailable()) {
            //Passing null to indicate that it is executing for the first time.
            startStep2(null);
        } else {
            Toast.makeText(activity, R.string.no_google_playservice_available, Toast.LENGTH_LONG).show();
        }
    }

    public boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(context);
        if (status != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(status)) {
                googleApiAvailability.getErrorDialog(activity, status, 2404).show();
            }
            return false;
        }
        return true;
    }

    private Boolean startStep2(DialogInterface dialog) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            promptInternetConnect();
            return false;
        }

        if (dialog != null) {
            dialog.dismiss();
        }

        //Yes there is active internet connection. Next check Location is granted by user or not.

        if (checkPermissions()) { //Yes permissions are granted by the user. Go to the next step.
            startStep3();
        } else {  //No user has not granted the permissions yet. Request now.
            requestPermissions();
        }
        return true;
    }

    private void requestPermissions() {

        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(activity,
                        Manifest.permission.ACCESS_FINE_LOCATION);

        boolean shouldProvideRationale2 =
                ActivityCompat.shouldShowRequestPermissionRationale(activity,
                        Manifest.permission.ACCESS_COARSE_LOCATION);


        // Provide an additional rationale to the img_user. This would happen if the img_user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale || shouldProvideRationale2) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");
            showSnackbar(R.string.permission_rationale,
                    android.R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            ActivityCompat.requestPermissions(activity,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                                    REQUEST_PERMISSIONS_REQUEST_CODE);
                        }
                    });
        } else {
            Log.i(TAG, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the img_user denied the permission
            // previously and checked "Never ask again".
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(
                activity.findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }

    private void startStep3() {
        //And it will be keep running until you close the entire application from task manager.
        //This method will executed only once.

        if (!mAlreadyStartedService) {
            //Start location sharing service to app server.........
            // Intent intent = new Intent(activity, LocationMonitoringService.class);
           // GlobalFunctions.setSharedPreferenceString(context, "extra_order_Id", order_vendor_product_id);
            activity.startService(locationintent);

            mAlreadyStartedService = true;
            //Ends................................................
        }
    }

    private void promptInternetConnect() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);
        builder.setTitle(R.string.no_internet);
        builder.setMessage(R.string.no_internet);

        String positiveText = getString(R.string.ok);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Block the Application Execution until user grants the permissions
                        if (startStep2(dialog)) {

                            //Now make sure about location permission.
                            if (checkPermissions()) {

                                //Step 2: Start the Location Monitor Service
                                //Everything is there to start the service.
                                startStep3();
                            } else if (!checkPermissions()) {
                                requestPermissions();
                            }
                        }
                    }
                });

        android.app.AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void stopLocationService() {
        if (activity != null) {
            activity.stopService(locationintent);
        }
    }

    private boolean checkPermissions() {
        int permissionState1 = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION);

        int permissionState2 = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_COARSE_LOCATION);

        return permissionState1 == PackageManager.PERMISSION_GRANTED && permissionState2 == PackageManager.PERMISSION_GRANTED;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );

        if (resultCode == activity.RESULT_OK) {

            if (requestCode == globalVariables.REQUEST_CODE_FOR_SEARCH) {
                SearchResponseModel searchResponseModel = (SearchResponseModel) data.getExtras().getSerializable(SearchActivity.BUNDLE_SEARCH_RESPONSE_MODEL);
                if (searchResponseModel != null) {
                        if (searchResponseModel.getId() != null) {
                            VendorStoreModel vendorStoreModel = new VendorStoreModel();
                             vendorStoreModel.setId(searchResponseModel.getId());
                                Intent intent = VendorListDetailsActivity.newInstance(activity, vendorStoreModel,new VendorModel());
                                activity.startActivity(intent);
                        }
                }
            }
        }
    }


}
