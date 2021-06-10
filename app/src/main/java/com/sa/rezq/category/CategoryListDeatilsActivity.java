package com.sa.rezq.category;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
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
import com.sa.rezq.adapter.AllCategoryListAdapter;
import com.sa.rezq.global.GlobalFunctions;
import com.sa.rezq.global.GlobalVariables;
import com.sa.rezq.services.ServerResponseInterface;
import com.sa.rezq.services.ServicesMethodsManager;
import com.sa.rezq.services.model.SeeAllCategoryListMainModel;
import com.sa.rezq.services.model.SeeAllCategoryListModel;
import com.sa.rezq.services.model.SeeAllCategoryModel;
import com.vlonjatg.progressactivity.ProgressLinearLayout;

import java.util.ArrayList;
import java.util.List;

public class CategoryListDeatilsActivity extends AppCompatActivity {

    public static final String TAG = "CategoryListDeatilsActivity",
            BUNDLE_CATEGORY_LIST_DETAILS = "CategoryListDeatilsActivity";


    Context context = null;
    static Activity activity = null;

    private EditText public_name_etv, cr_number_etv, vat_number_etv, m_first_name_etv, m_last_name_etv, m_email_etv, shop_url_etv, head_office_address_etv, mobile_number_etv;
    private TextView continue_tv;
    private ImageView edit_profile_image_iv;
    public View mainView;

    static Toolbar toolbar;
    static ActionBar actionBar;
    static String mTitle;
    static int mResourceID;
    static int titleResourseID;
    static TextView toolbar_title;
    static ImageView toolbar_logo, tool_bar_back_icon;
    Menu menu;
    String account = String.valueOf(1);


    AllCategoryListAdapter categoryListAdapter;
    List<SeeAllCategoryModel> listModelList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    ProgressLinearLayout progressActivity;
    RecyclerView seeAllCategoryRecyclerview;
    SwipeRefreshLayout swipe_container;

    private boolean shouldRefreshOnResume = false;

    GlobalVariables globalVariables;
    GlobalFunctions globalFunctions;
    Window window = null;

    String imagePath = "";

    FirebaseStorage storage;
    StorageReference storageReference;

    public static Intent newInstance(Activity activity, SeeAllCategoryModel listModel) {
        Intent intent = new Intent(activity, CategoryListDeatilsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_CATEGORY_LIST_DETAILS, listModel);
        intent.putExtras(bundle);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list_details);

        context = this;
        activity = this;
        window = getWindow();

        globalFunctions = AppController.getInstance().getGlobalFunctions();
        globalVariables = AppController.getInstance().getGlobalVariables();
        linearLayoutManager = new LinearLayoutManager(activity);
        progressActivity = findViewById(R.id.details_progressActivity);
        swipe_container = findViewById(R.id.swipe_container);

        // set up the RecyclerView
        seeAllCategoryRecyclerview = findViewById(R.id.categoryRecyclerview);

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
        mainView = seeAllCategoryRecyclerview;

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        swipe_container.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
        setTitle(getString(R.string.popular_category), 0, 0);


        loadData();
        seeAllCategoryRecyclerview.setAdapter(categoryListAdapter);


    }

    private void loadData() {
        globalFunctions.showProgress(activity, getString(R.string.loading));
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.getSeeAllCategoryList(context, new ServerResponseInterface() {
            @SuppressLint("LongLogTag")
            @Override
            public void OnSuccessFromServer(Object arg0) {
                globalFunctions.hideProgress();
                if (swipe_container.isRefreshing()) {
                    swipe_container.setRefreshing( false );
                }
                Log.d(TAG, "Response : " + arg0.toString());
                SeeAllCategoryListMainModel seeAllCategoryListMainModel = (SeeAllCategoryListMainModel) arg0;
                if (seeAllCategoryListMainModel!=null && seeAllCategoryListMainModel.getSeeAllCategoryListModel()!=null){
                    SeeAllCategoryListModel listModel = seeAllCategoryListMainModel.getSeeAllCategoryListModel();
                    setThisPage(listModel);
                }


            }

            @SuppressLint("LongLogTag")
            @Override
            public void OnFailureFromServer(String msg) {
                globalFunctions.hideProgress();
                if (swipe_container.isRefreshing()) {
                    swipe_container.setRefreshing( false );
                }
                globalFunctions.displayMessaage(context, mainView, msg);
                Log.d(TAG, "Failure : " + msg);
            }

            @SuppressLint("LongLogTag")
            @Override
            public void OnError(String msg) {
                globalFunctions.hideProgress();
                if (swipe_container.isRefreshing()) {
                    swipe_container.setRefreshing( false );
                }
                globalFunctions.displayMessaage(context, mainView, msg);
                Log.d(TAG, "Error : " + msg);
            }
        }, "list");
    }

    private void setThisPage(SeeAllCategoryListModel seeAllCategoryListModel) {
        if (seeAllCategoryListModel != null && listModelList != null) {
            listModelList.clear();
            listModelList.addAll(seeAllCategoryListModel.getAllCategoryList());
            if (categoryListAdapter != null) {
                synchronized (categoryListAdapter) {
                    categoryListAdapter.notifyDataSetChanged();
                }
            }
            if (listModelList.size() <= 0) {
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
                    getString(R.string.no_data_show));
        }
    }

    private void initRecyclerView() {
        categoryListAdapter = new AllCategoryListAdapter(activity, listModelList);
        seeAllCategoryRecyclerview.setLayoutManager(linearLayoutManager);
        seeAllCategoryRecyclerview.setHasFixedSize(true);
        seeAllCategoryRecyclerview.setAdapter(categoryListAdapter);
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
        shouldRefreshOnResume = true;
    }

}
