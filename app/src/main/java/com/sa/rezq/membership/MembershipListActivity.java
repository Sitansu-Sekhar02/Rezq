package com.sa.rezq.membership;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sa.rezq.Activity.AppController;
import com.sa.rezq.R;
import com.sa.rezq.adapter.AccountListAdapter;
import com.sa.rezq.adapter.MembershipListAdapter;
import com.sa.rezq.global.GlobalFunctions;
import com.sa.rezq.global.GlobalVariables;
import com.sa.rezq.home.HomeFragment;
import com.sa.rezq.services.ServerResponseInterface;
import com.sa.rezq.services.ServicesMethodsManager;
import com.sa.rezq.services.model.AccountListModel;
import com.sa.rezq.services.model.AccountMainModel;
import com.sa.rezq.services.model.AccountModel;
import com.sa.rezq.services.model.BannerListModel;
import com.sa.rezq.services.model.BannerModel;
import com.sa.rezq.services.model.HomePageMainModel;
import com.sa.rezq.services.model.HomePageModel;
import com.sa.rezq.services.model.InsertAccountModel;
import com.sa.rezq.services.model.MembershipListModel;
import com.sa.rezq.services.model.MembershipMainModel;
import com.sa.rezq.services.model.MembershipModel;
import com.sa.rezq.services.model.StatusMainModel;
import com.sa.rezq.services.model.StatusModel;
import com.sa.rezq.services.model.TrendingModel;
import com.sa.rezq.vendorlist.details.VendorListDetailsActivity;
import com.sa.rezq.vendorlist.details.VendorStoreListActivity;
import com.sa.rezq.view.CustomSliderTextView;
import com.vlonjatg.progressactivity.ProgressLinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MembershipListActivity extends AppCompatActivity  implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    public static final String TAG = "MembershipActivity";

    public static final int SLIDER_LOAD_TIME_IN_MILLI_SEC=2000;

    RecyclerView recyclerView;
    View view;
    TextView AllLockedOffer;
    Context context = null;
    static Activity activity = null;

    private EditText public_name_etv, cr_number_etv, vat_number_etv, m_first_name_etv, m_last_name_etv, m_email_etv, shop_url_etv, head_office_address_etv, mobile_number_etv;
    private TextView continue_tv;
    private ImageView edit_profile_image_iv;
    public View mainView;
    Button btnSubscribe;

    static Toolbar toolbar;
    static ActionBar actionBar;
    static String mTitle;
    static int mResourceID;
    static int titleResourseID;
    static TextView toolbar_title;
    static ImageView toolbar_logo, tool_bar_back_icon;
    Menu menu;
    String account = String.valueOf(1);
    SliderLayout mProductSlider;
    PagerIndicator mPagerIndicator;

    private boolean shouldRefreshOnResume = false;

    GlobalVariables globalVariables;
    GlobalFunctions globalFunctions;
    Window window = null;
    ImageView vendor_list_image,iv_favourite;
    TextView tv_vendor_name,tvRating,tv_address,tv_open_map,tv_rating_count;

    MembershipListAdapter membershipListAdapter;
    List<MembershipModel> membershipModels = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    ProgressLinearLayout progressActivity;
    RecyclerView MembershipListRecyclerview;

    MembershipListModel model;


    String vendor_id = null;
    TrendingModel trendingModel = null;

    FirebaseStorage storage;
    StorageReference storageReference;


    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upgrade_membership);

        context = this;
        activity = this;
        window = getWindow();

        globalFunctions = AppController.getInstance().getGlobalFunctions();
        globalVariables = AppController.getInstance().getGlobalVariables();

        linearLayoutManager = new LinearLayoutManager(activity);
        progressActivity = findViewById(R.id.details_progressActivity);
        MembershipListRecyclerview = findViewById(R.id.membership_list_recyclerview);


        mProductSlider = (SliderLayout) view.findViewById(R.id.shop_main_fragment_top_slider);
        mPagerIndicator = (PagerIndicator) view.findViewById(R.id.shop_main_fragment_top_slider_custom_indicator);
        btnSubscribe = findViewById(R.id.btnSubscribe);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        //toolbar.setPadding(0, GlobalFunctions.getStatusBarHeight(context), 0, 0);
        //toolbar.setNavigationIcon(R.drawable.ic_back_draw);
        //toolbar.setContentInsetsAbsolute(0,0);
        toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        tool_bar_back_icon = (ImageView) toolbar.findViewById(R.id.tool_bar_back_icon);
        tool_bar_back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        loadBanner(context);


        mainView = MembershipListRecyclerview;


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.black_trans));
        }

        btnSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertMembership();

            }
        });

        setTitle(getString(R.string.upgrade_membership), 0, 0);

        getMembershipList();


    }

    private void insertMembership() {
        //globalFunctions.showProgress(activity, getString(R.string.loading));
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.insertMembership(context, new ServerResponseInterface() {
            @Override
            public void OnSuccessFromServer(Object arg0) {
                // globalFunctions.hideProgress();
                Log.d(TAG, "Response : " + arg0.toString());

                validOutputAfterClick(arg0);
               /* MembershipMainModel membershipModel = (MembershipMainModel) arg0;
                if (membershipModel!=null && membershipModel.getMembershipListModel()!=null){
                    MembershipListModel listModel = membershipModel.getMembershipListModel();
                    outpotAfterClick(listModel);
                }*/
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
        }, "Click ");
    }

    private void validOutputAfterClick(Object arg0) {
        if (arg0 instanceof MembershipMainModel) {
            MembershipMainModel membershipMainModel = (MembershipMainModel) arg0;
            MembershipListModel listModel = membershipMainModel.getMembershipListModel();
            if (!membershipMainModel.isStatus()) {
               // globalFunctions.displayMessaage(activity, mainView, listModel.getMessage());

            } else {
              //  GlobalFunctions.setSharedPreferenceString(context, GlobalVariables.SHARED_PREFERENCE_TOKEN, statusModel.getToken());

            }
        }
    }

    private void loadBanner(Context context) {
        //globalFunctions.showProgress(activity, getString(R.string.loading));
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.getHomeData(context, new ServerResponseInterface() {
            @Override
            public void OnSuccessFromServer(Object arg0) {
                // globalFunctions.hideProgress();
                Log.d(TAG, "Response : " + arg0.toString());
                if (arg0 instanceof MembershipMainModel) {
                    MembershipMainModel membershipMainModel= (MembershipMainModel) arg0;
                    //MembershipListModel membershipListModel=membershipMainModel.getMembershipListModel();
                    setBannerPage(membershipMainModel.getMembershipListModel());
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

    private void setBannerPage(final MembershipListModel membershipListModel) {
        if (membershipListModel != null) {

            if (membershipListModel.getMembershipModels() != null) {
                MembershipModel section2 = membershipListModel.getMembershipModels();
                if (section2 != null) setBanners(section2);
            }

        }
    }

    private void setBanners(MembershipListModel bannerListModel) {
        if (bannerListModel != null) {
            if (model == null) {
                 BannerTask bannerTask = new BannerTask();
                bannerTask.execute(bannerListModel);
            } else if (model.getMembershipModels().size() != bannerListModel.getMembershipModels().size()) {
                BannerTask bannerTask = new BannerTask();
                bannerTask.execute(bannerListModel);
            }
        }
    }





    private void getMembershipList() {
        globalFunctions.showProgress(activity, getString(R.string.loading));
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.getMembershipList(context, new ServerResponseInterface() {
            @SuppressLint("LongLogTag")
            @Override
            public void OnSuccessFromServer(Object arg0) {
                globalFunctions.hideProgress();
                Log.d(TAG, "Response : " + arg0.toString());
                MembershipMainModel membershipMainModel = (MembershipMainModel) arg0;
                if (membershipMainModel!=null && membershipMainModel.getMembershipListModel()!=null){
                    MembershipListModel listModel = membershipMainModel.getMembershipListModel();
                    setThisPage(listModel);
                }

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
        }, "list");
    }

    private void setThisPage(MembershipListModel membershipListModel) {
        if (membershipListModel != null && membershipModels != null) {
            membershipModels.clear();
            membershipModels.addAll(membershipListModel.getMembershipModels());
            // setStaticAccount();
            if (membershipListAdapter != null) {
                synchronized (membershipListAdapter) {
                    membershipListAdapter.notifyDataSetChanged();
                }
            }

            if (membershipModels.size() > 0) {
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

    private void initRecyclerView() {
        MembershipListRecyclerview.setLayoutManager(linearLayoutManager);
        MembershipListRecyclerview.setHasFixedSize(true);
        membershipListAdapter = new MembershipListAdapter(activity, membershipModels);
        MembershipListRecyclerview.setAdapter(membershipListAdapter);
    }


    @Override
    public void onSliderClick(BaseSliderView slider) {
        MembershipModel bannerClickedModel = (MembershipModel) slider.getBundle().getSerializable("data");
        if (bannerClickedModel != null) {
            if (bannerClickedModel.getId() != null) {

           /*     if(bannerClickedModel.getCount().equalsIgnoreCase("1")){
                    Intent intent = VendorListDetailsActivity.newInstance( activity, bannerClickedModel );
                    activity.startActivity( intent );
                }else {
                    Intent intent = VendorStoreListActivity.newInstance( activity, bannerClickedModel );
                    activity.startActivity( intent );
                }*/
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
    public class BannerTask extends AsyncTask<MembershipListModel, Void, List<String>> {

        @Override
        protected List<String> doInBackground(MembershipListModel... membershipListModels) {
            MembershipListModel membershipListModel = membershipListModels[0];
            if (membershipListModel != null) model = membershipListModel;
            List<String> bannerStrings = new ArrayList<>();
            if (model != null) {
                for (int i = 0; i < model.getMembershipModels().size(); i++) {
                    String imageModel = model.getMembershipModels().get(i).getImage();
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
                            .putString("extra", model.getMembershipModels().get(i).getId() + "");
                    textSliderView.getBundle()
                            .putSerializable("data", model.getMembershipModels().get(i));

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
