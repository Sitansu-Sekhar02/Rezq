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

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

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
import com.sa.rezq.adapter.AllCategoryListAdapter;
import com.sa.rezq.adapter.MembershipDescriptionListAdapter;
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

public class MembershipListActivity extends AppCompatActivity {

    public static final String TAG = "MembershipActivity";

    public static final int SLIDER_LOAD_TIME_IN_MILLI_SEC=2000;

    RecyclerView recyclerView;
    View view;
    TextView AllLockedOffer;
    Context context = null;
    static Activity activity = null;

    private EditText public_name_etv, cr_number_etv, vat_number_etv, m_first_name_etv, m_last_name_etv, m_email_etv, shop_url_etv, head_office_address_etv, mobile_number_etv;
    private TextView price;
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
    SliderLayout mProductSlider;
    PagerIndicator mPagerIndicator;

    private boolean shouldRefreshOnResume = false;

    GlobalVariables globalVariables;
    GlobalFunctions globalFunctions;
    Window window = null;


    MembershipListAdapter membershipListAdapter;
    MembershipDescriptionListAdapter descriptionListAdapter;
    List<MembershipModel> membershipModels = new ArrayList<>();
    LinearLayoutManager linearHorizontalLayoutManager;
    LinearLayoutManager linearLayoutManager;
    RecyclerView membership_child_list_recyclerview,membership_banner_recyclerview;


    int review_position = 0;
    MembershipListModel membershipListModel=null;

    List<String> serviceNameList=new ArrayList<>();


    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upgrade_membership);

        context = this;
        activity = this;

        globalFunctions = AppController.getInstance().getGlobalFunctions();
        globalVariables = AppController.getInstance().getGlobalVariables();

        linearHorizontalLayoutManager = new LinearLayoutManager(activity,linearLayoutManager.HORIZONTAL, false);
        linearLayoutManager = new LinearLayoutManager(activity);

        membership_child_list_recyclerview = findViewById(R.id.membership_child_list_recyclerview);
        membership_banner_recyclerview = findViewById(R.id.membership_banner_recyclerview);


        btnSubscribe = findViewById(R.id.btnSubscribe);
        price = findViewById(R.id.price);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
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

        loadListBanner(context);


        mainView = membership_banner_recyclerview;


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

       // getMembershipList();

        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(membership_banner_recyclerview);

        membership_banner_recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                review_position = linearHorizontalLayoutManager.findFirstVisibleItemPosition();

                if (membershipListModel.getMembershipModels() != null && membershipListModel.getMembershipModels().get(review_position) != null) {
                    MembershipModel membershipModel = membershipListModel.getMembershipModels().get(review_position);

                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setThisPage(membershipModel);
                        }
                    });
                }
            }
        });

        serviceNameList.clear();

        descriptionListAdapter = new MembershipDescriptionListAdapter(activity, serviceNameList);
        membership_child_list_recyclerview.setLayoutManager(linearLayoutManager);
        membership_child_list_recyclerview.setAdapter(descriptionListAdapter);


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
                globalFunctions.displayMessaage(activity, mainView, membershipMainModel.getMessage());

            } else {

            }
        }
    }

    private void loadListBanner(Context context) {
        globalFunctions.showProgress(activity, getString(R.string.loading));
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.getMembershipList(context, new ServerResponseInterface() {
            @Override
            public void OnSuccessFromServer(Object arg0) {
                 globalFunctions.hideProgress();
                Log.d(TAG, "Response : " + arg0.toString());
                MembershipMainModel membershipMainModel = (MembershipMainModel) arg0;
                if (membershipMainModel!=null && membershipMainModel.getMembershipListModel()!=null){
                    membershipListModel = membershipMainModel.getMembershipListModel();
                    setListBanners(membershipListModel);

                    if (membershipListModel.getMembershipModels() != null && membershipListModel.getMembershipModels().get(review_position) != null) {
                        MembershipModel membershipModel = membershipListModel.getMembershipModels().get(review_position);

                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setThisPage(membershipModel);
                            }
                        });
                    }
                }
            }

            @Override
            public void OnFailureFromServer(String msg) {
                globalFunctions.hideProgress();
                globalFunctions.displayMessaage(context, mainView, msg);
                Log.d(TAG, "Failure : " + msg);
            }

            @Override
            public void OnError(String msg) {
                 globalFunctions.hideProgress();
                globalFunctions.displayMessaage(context, mainView, msg);
                Log.d(TAG, "Error : " + msg);
            }
        }, "Banners");
    }


    private void setThisPage(MembershipModel membershipModel) {
        if (membershipModel != null) {

                if (globalFunctions.isNotNullValue(membershipModel.getPrice())) {
                    price.setText(activity.getString(R.string.sar)+membershipModel.getPrice()+" /"+activity.getString(R.string.month));

                }

            if (GlobalFunctions.isNotNullValue(membershipModel.getDescription())) {
                List<String> serviceNameList = new ArrayList<>();
                serviceNameList.clear();
//                service_description_tv.setText(globalFunctions.getHTMLString(membershipModel.getDescription()));
//                service_description_tv.setText(globalFunctions.html2text(service_description_tv.getText().toString().trim()));

                String[] serviceList = membershipModel.getDescription().split("</div>");

                for (int i = 0; i < serviceList.length; i++) {
                    String value = serviceList[i];
                    serviceNameList.add(value);
                }

                if (serviceNameList.size() > 0) {
                    descriptionListAdapter.updatePlanList(serviceNameList);
                }

            }

            review_position = linearHorizontalLayoutManager.findFirstVisibleItemPosition();

        }
    }

    private void setListBanners(MembershipListModel membershipListModel) {
        if (membershipListModel != null && membershipModels != null) {
            membershipModels.clear();
            membershipModels.addAll(membershipListModel.getMembershipModels());
            if (membershipListAdapter != null) {
                synchronized (membershipListAdapter) {
                    membershipListAdapter.notifyDataSetChanged();
                }
            }

            if (membershipModels.size() > 0) {
                initBannerRecyclerView();
            }
        }
    }

    private void initBannerRecyclerView() {

        membershipListAdapter = new MembershipListAdapter(activity, membershipModels);
        membership_banner_recyclerview.setLayoutManager(linearHorizontalLayoutManager);
        membership_banner_recyclerview.setHasFixedSize(true);
        membership_banner_recyclerview.setAdapter(membershipListAdapter);

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
