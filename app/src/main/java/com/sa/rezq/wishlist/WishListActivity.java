package com.sa.rezq.wishlist;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.sa.rezq.Activity.AppController;
import com.sa.rezq.R;
import com.sa.rezq.adapter.AccountListAdapter;
import com.sa.rezq.adapter.WishListAdapter;
import com.sa.rezq.adapter.interfaces.OpenInsertAccountInvoke;
import com.sa.rezq.global.GlobalFunctions;
import com.sa.rezq.global.GlobalVariables;
import com.sa.rezq.services.ServerResponseInterface;
import com.sa.rezq.services.ServicesMethodsManager;
import com.sa.rezq.services.model.AccountListModel;
import com.sa.rezq.services.model.AccountMainModel;
import com.sa.rezq.services.model.AccountModel;
import com.sa.rezq.services.model.InsertAccountModel;
import com.sa.rezq.services.model.StatusMainModel;
import com.sa.rezq.services.model.StatusModel;
import com.sa.rezq.services.model.WishListMainModel;
import com.sa.rezq.services.model.WishListModel;
import com.sa.rezq.services.model.WishListSubMainModel;
import com.sa.rezq.services.model.WishModel;
import com.vlonjatg.progressactivity.ProgressLinearLayout;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class WishListActivity extends AppCompatActivity {
    public static final String TAG = "WishListActivity";


    Context context;
    private static Activity activity;
    View mainView;



    static Toolbar toolbar;
    static ActionBar actionBar;
    static String mTitle;
    static int mResourceID;
    static int titleResourseID;
    static TextView toolbar_title;
    static ImageView toolbar_logo, tool_bar_back_icon;
    GlobalVariables globalVariables;
    GlobalFunctions globalFunctions;

    WishListAdapter wishListAdapter;
    List<WishModel> wishModels = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    GridLayoutManager gridLayoutManager;
    ProgressLinearLayout progressActivity;
    RecyclerView wishListRecyclerview;
    SwipeRefreshLayout swipe_container;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wishlist_activity);

        context = this;
        activity = this;

        globalFunctions = AppController.getInstance().getGlobalFunctions();
        globalVariables = AppController.getInstance().getGlobalVariables();

        linearLayoutManager = new LinearLayoutManager(activity);
        gridLayoutManager=new GridLayoutManager(activity,2);
        progressActivity = findViewById(R.id.favourite_progressActivity);
        wishListRecyclerview = findViewById(R.id.wishlist_recyclerview);
        swipe_container = findViewById(R.id.swipe_container);


        mainView = wishListRecyclerview;

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

        swipe_container.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getWishList();
            }
        });
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        setTitle(getString(R.string.favourites), 0, 0);

        getWishList();


    }

    private void getWishList() {
        globalFunctions.showProgress(activity, getString(R.string.loading));
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.getWishListes(context, new ServerResponseInterface() {
            @SuppressLint("LongLogTag")
            @Override
            public void OnSuccessFromServer(Object arg0) {
                globalFunctions.hideProgress();
                if (swipe_container.isRefreshing()) {
                    swipe_container.setRefreshing( false );
                }
                Log.d(TAG, "Response : " + arg0.toString());
                WishListMainModel wishListMainModel = (WishListMainModel) arg0;
                if (wishListMainModel!=null && wishListMainModel.getWishListSubMainModel()!=null){
                    WishListSubMainModel listModel = wishListMainModel.getWishListSubMainModel();
                    WishListModel wishListModel=listModel.getWishListModel();
                    setThisPage(wishListModel);
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

    private void setThisPage(WishListModel wishListModel) {
        if (wishListModel != null && wishModels != null) {
            wishModels.clear();
            wishModels.addAll(wishListModel.getWishList());
            if (wishListAdapter != null) {
                synchronized (wishListAdapter) {
                    wishListAdapter.notifyDataSetChanged();
                }
            }

            if (wishModels.size() <= 0) {
                showEmptyPage();
            } else {
                showContent();
                initRecyclerView();
            }

            /*if (wishModels.size() > 0) {
                showContent();
                initRecyclerView();
            }*/
        }
    }
    private void showEmptyPage() {
        if (progressActivity != null) {
            progressActivity.showEmpty(getResources().getDrawable(R.drawable.rezq_logo), getString(R.string.emptyList),
                    getString(R.string.no_data_show));
        }
    }


    private void showContent() {
        if (progressActivity != null) {
            progressActivity.showContent();
        }
    }

    private void initRecyclerView() {
        wishListRecyclerview.setLayoutManager(gridLayoutManager);
        wishListRecyclerview.setHasFixedSize(true);
        wishListAdapter = new WishListAdapter(activity, wishModels);
        wishListRecyclerview.setAdapter(wishListAdapter);
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
