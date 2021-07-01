package com.sa.rezq.wishlist;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.sa.rezq.Activity.AppController;
import com.sa.rezq.R;
import com.sa.rezq.adapter.AllCategoryListAdapter;
import com.sa.rezq.adapter.WishListAdapter;
import com.sa.rezq.adapter.WishListCategoryAdapter;
import com.sa.rezq.adapter.interfaces.ClickListener;
import com.sa.rezq.global.GlobalFunctions;
import com.sa.rezq.global.GlobalVariables;
import com.sa.rezq.services.ServerResponseInterface;
import com.sa.rezq.services.ServicesMethodsManager;
import com.sa.rezq.services.model.SeeAllCategoryListMainModel;
import com.sa.rezq.services.model.SeeAllCategoryListModel;
import com.sa.rezq.services.model.SeeAllCategoryModel;
import com.sa.rezq.services.model.WishListMainModel;
import com.sa.rezq.services.model.WishListModel;
import com.sa.rezq.services.model.WishModel;
import com.sa.rezq.services.model.WishlistCategoryModel;
import com.vlonjatg.progressactivity.ProgressLinearLayout;

import java.util.ArrayList;
import java.util.List;

public class WishListActivity extends AppCompatActivity implements ClickListener {
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
    LinearLayoutManager gridLayoutManager;
    ProgressLinearLayout progressActivity;
    RecyclerView wishListRecyclerview;
    SwipeRefreshLayout swipe_container;

    String category_id=null;


    WishListCategoryAdapter wishListCategoryAdapter;
    List<WishlistCategoryModel> wishlistCategoryModels = new ArrayList<>();
    LinearLayoutManager horizontalLinear;
    RecyclerView wishlist_category_recyclerview;

    AllCategoryListAdapter categoryListAdapter;
    List<SeeAllCategoryModel> listModelList = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wishlist_activity);

        context = this;
        activity = this;

        globalFunctions = AppController.getInstance().getGlobalFunctions();
        globalVariables = AppController.getInstance().getGlobalVariables();

        gridLayoutManager=new GridLayoutManager(activity,2);
        progressActivity = findViewById(R.id.favourite_progressActivity);
        wishListRecyclerview = findViewById(R.id.wishlist_recyclerview);
        swipe_container = findViewById(R.id.swipe_container);


        horizontalLinear = new LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL, false);
        wishlist_category_recyclerview = findViewById(R.id.favourite_list_recyclerview);

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


        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        setTitle(getString(R.string.favourites), 0, 0);

        getWishlistCategoryList();

        initRecyclerView();

    /*    swipe_container.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getWishList(category_id);
            }
        });
*/
    }

    private void getWishlistCategoryList() {
        //globalFunctions.showProgress(activity, getString(R.string.loading));
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.getSeeAllCategoryList(context, new ServerResponseInterface() {
            @SuppressLint("LongLogTag")
            @Override
            public void OnSuccessFromServer(Object arg0) {
               // globalFunctions.hideProgress();
                Log.d(TAG, "Response : " + arg0.toString());
               /* WishlistCategoryMainModel wishlistCategoryMainModel = (WishlistCategoryMainModel) arg0;
                if (wishlistCategoryMainModel!=null && wishlistCategoryMainModel.getWishlistCategoryListModel()!=null){
                    WishlistCategoryListModel listModel = wishlistCategoryMainModel.getWishlistCategoryListModel();
                 //   WishlistCategoryModel wishListModel=listModel.getWishlistCategoryModels();
                    setWishListPage(listModel);
                }*/
                SeeAllCategoryListMainModel seeAllCategoryListMainModel = (SeeAllCategoryListMainModel) arg0;
                if (seeAllCategoryListMainModel!=null && seeAllCategoryListMainModel.getSeeAllCategoryListModel()!=null){
                    SeeAllCategoryListModel listModel = seeAllCategoryListMainModel.getSeeAllCategoryListModel();
                    setWishListPage(listModel);
                }

            }

            @SuppressLint("LongLogTag")
            @Override
            public void OnFailureFromServer(String msg) {

                //globalFunctions.hideProgress();

                globalFunctions.displayMessaage(context, mainView, msg);
                Log.d(TAG, "Failure : " + msg);
            }

            @SuppressLint("LongLogTag")
            @Override
            public void OnError(String msg) {
               // globalFunctions.hideProgress();
                globalFunctions.displayMessaage(context, mainView, msg);
                Log.d(TAG, "Error : " + msg);
            }
        }, "list");
    }

    private void setWishListPage(SeeAllCategoryListModel listModel) {
        if (listModel != null && listModelList != null) {
            listModelList.clear();
            //static for all category
            SeeAllCategoryModel wishlistCategoryModel=new SeeAllCategoryModel();
            wishlistCategoryModel.setCategori_id("0");
            wishlistCategoryModel.setName("All");
            listModelList.add(wishlistCategoryModel);
            listModelList.addAll(listModel.getAllCategoryList());
            if (categoryListAdapter != null) {
                synchronized (categoryListAdapter) {
                    categoryListAdapter.notifyDataSetChanged();
                }
            }

            if (listModelList.size() > 0) {
                initCategoryRecyclerView();
            }
        }
    }

    private void initCategoryRecyclerView() {
        wishlist_category_recyclerview.setLayoutManager(horizontalLinear);
        wishlist_category_recyclerview.setHasFixedSize(true);
        wishListCategoryAdapter = new WishListCategoryAdapter(activity, listModelList, model -> OnItemClickListener(model));
        wishlist_category_recyclerview.setAdapter(wishListCategoryAdapter);
    }

    private void getWishList(String category_id) {
       // globalFunctions.showProgress(activity, getString(R.string.loading));
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.getWishListes(context,category_id, new ServerResponseInterface() {
            @SuppressLint("LongLogTag")
            @Override
            public void OnSuccessFromServer(Object arg0) {
               // globalFunctions.hideProgress();
               /* if (swipe_container.isRefreshing()) {
                    swipe_container.setRefreshing( false );
                }*/
                Log.d(TAG, "Response : " + arg0.toString());
                WishListMainModel wishListMainModel = (WishListMainModel) arg0;
                if (wishListMainModel!=null && wishListMainModel.getWishListModel()!=null){
                    WishListModel listModel = wishListMainModel.getWishListModel();
                    //WishListModel wishListModel=listModel.getWishList();
                    setThisPage(listModel);
                }

            }

            @SuppressLint("LongLogTag")
            @Override
            public void OnFailureFromServer(String msg) {

              /*  globalFunctions.hideProgress();
                if (swipe_container.isRefreshing()) {
                    swipe_container.setRefreshing( false );
                }*/
                globalFunctions.displayMessaage(context, mainView, msg);
                Log.d(TAG, "Failure : " + msg);
            }

            @SuppressLint("LongLogTag")
            @Override
            public void OnError(String msg) {
               /* globalFunctions.hideProgress();
                if (swipe_container.isRefreshing()) {
                    swipe_container.setRefreshing( false );
                }*/
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
                    getString(R.string.no_favourite_item));
        }
    }


    private void showContent() {
        if (progressActivity != null) {
            progressActivity.showContent();
        }
    }

    private void initRecyclerView() {
        wishListRecyclerview.setLayoutManager(gridLayoutManager);
        wishListAdapter = new WishListAdapter(activity, wishModels);
        wishListRecyclerview.setAdapter(wishListAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu_item, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                wishListAdapter.getFilter().filter(newText);
                return false;
            }
        });
        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Set styles for expanded state here
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.LTGRAY));
                }
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Set styles for collapsed state here
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                }
                return true;
            }
        });


        return true;
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
    public void OnItemClickListener(SeeAllCategoryModel model) {
        getWishList(model.getCategori_id());

    }
}
