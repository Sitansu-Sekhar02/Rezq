package com.sa.rezq.coupons;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sa.rezq.Activity.AppController;
import com.sa.rezq.Activity.MainActivity;
import com.sa.rezq.R;
import com.sa.rezq.adapter.AccountListAdapter;
import com.sa.rezq.adapter.AllCategoryListAdapter;
import com.sa.rezq.adapter.CouponListAdapter;
import com.sa.rezq.adapter.interfaces.OnOfferClickInvoke;
import com.sa.rezq.global.GlobalFunctions;
import com.sa.rezq.global.GlobalVariables;
import com.sa.rezq.offers.RedeemOfferActivity;
import com.sa.rezq.services.ServerResponseInterface;
import com.sa.rezq.services.ServicesMethodsManager;
import com.sa.rezq.services.model.AccountListModel;
import com.sa.rezq.services.model.AccountMainModel;
import com.sa.rezq.services.model.AccountModel;
import com.sa.rezq.services.model.InsertAccountModel;
import com.sa.rezq.services.model.OfferModel;
import com.sa.rezq.services.model.RecentCouponListModel;
import com.sa.rezq.services.model.RecentCouponMainModel;
import com.sa.rezq.services.model.RecentCouponModel;
import com.sa.rezq.services.model.SeeAllCategoryModel;
import com.sa.rezq.services.model.TrendingModel;
import com.sa.rezq.services.model.VendorDetailsMainModel;
import com.sa.rezq.services.model.VendorModel;
import com.squareup.picasso.Picasso;
import com.vlonjatg.progressactivity.ProgressLinearLayout;

import java.util.ArrayList;
import java.util.List;

public class RecentCouponActivity extends AppCompatActivity implements OnOfferClickInvoke {

    public static final String TAG = "RecentCouponActivity";


   // private RecentCouponAdapter mAdapter;
    //CategoryAdapter adapter;
    RecyclerView recyclerView;
    View view;
    TextView AllLockedOffer;
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

    ProgressLinearLayout progressActivity;
    GlobalFunctions globalFunctions;
    GlobalVariables globalVariables;
    List <RecentCouponModel> recentCouponModels = new ArrayList <>();
    LinearLayoutManager linearLayoutManager;
    CouponListAdapter listAdapter;


    Window window = null;


    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recent_coupons_activity);

        context = this;
        activity = this;
        window = getWindow();

        globalFunctions = AppController.getInstance().getGlobalFunctions();
        globalVariables = AppController.getInstance().getGlobalVariables();

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        tool_bar_back_icon = (ImageView) toolbar.findViewById(R.id.tool_bar_back_icon);
        tool_bar_back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        recyclerView = findViewById(R.id.recent_list_recyclerview);

        linearLayoutManager = new LinearLayoutManager( activity );
        progressActivity = findViewById( R.id.recent_progressActivity );

        mainView = recyclerView;
        loadRecentList();




        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        setTitle(getString(R.string.recent), 0, 0);


    }

    private void loadRecentList() {
       // GlobalFunctions.showProgress( context, getString( R.string.loading ));
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.getRecentCouponList( context, new ServerResponseInterface() {
            @Override
            public void OnSuccessFromServer(Object arg0) {
              //  GlobalFunctions.hideProgress();

                Log.d( TAG, "Response : " + arg0.toString());
                RecentCouponMainModel recentCouponMainModel = (RecentCouponMainModel) arg0;
                if (recentCouponMainModel!=null && recentCouponMainModel.getRecentCouponListModel()!=null){
                    RecentCouponListModel listModel = recentCouponMainModel.getRecentCouponListModel();
                    setThisPage(listModel);
                }
            }

            @Override
            public void OnFailureFromServer(String msg) {
               // GlobalFunctions.hideProgress();

                Log.d( TAG, "Failure : " + msg );
                GlobalFunctions.displayMessaage( context, mainView, msg );
            }

            @Override
            public void OnError(String msg) {
                //GlobalFunctions.hideProgress();

                Log.d( TAG, "Error : " + msg );
                GlobalFunctions.displayMessaage( context, mainView, msg );
            }
        }, "Recent List" );
    }

    private void setThisPage(RecentCouponListModel listModel) {

        if (listModel != null && recentCouponModels != null) {
            recentCouponModels.clear();
            recentCouponModels.addAll(listModel.getRecentCouponModels());
            if (listAdapter != null) {
                synchronized (listAdapter) {
                    listAdapter.notifyDataSetChanged();
                }
            }

            if (recentCouponModels.size() <= 0) {
                showEmptyPage();
            } else {
                showContent();
                initRecyclerView();
            }

        }
    }

    private void showEmptyPage() {
        if (progressActivity != null) {
            progressActivity.showEmpty(getResources().getDrawable(R.drawable.rezq_logo), getString(R.string.emptyList),
                    getString(R.string.no_recent_coupons));
        }
    }

    private void showContent() {
        if (progressActivity != null) {
            progressActivity.showContent();
        }
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        listAdapter = new CouponListAdapter(activity, recentCouponModels,this);
        recyclerView.setAdapter(listAdapter);
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
                listAdapter.getFilter().filter(newText);
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



    @Override
    public void onStop () {
        super.onStop();
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
    public void OnClickInvoke(RecentCouponModel recentCouponModel) {
        loadVendorlistDetails(recentCouponModel.getId(),recentCouponModel.getOffer_id());
    }


    private void loadVendorlistDetails(String vendor_id, String offer_id) {
        GlobalFunctions.showProgress(context, getString(R.string.loading));
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.getVendorDetails(context, vendor_id, new ServerResponseInterface() {
            @SuppressLint("LongLogTag")
            @Override
            public void OnSuccessFromServer(Object arg0) {
                GlobalFunctions.hideProgress();
                Log.d(TAG, "Response : " + arg0.toString());
                VendorDetailsMainModel vendorMainModel = (VendorDetailsMainModel) arg0;
                VendorModel vendorModel = vendorMainModel.getVendorModel();

                if (vendorModel != null) {
                    setVendorDetails(vendorModel,offer_id);
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

    private void setVendorDetails(VendorModel vendorModel,String offer_id ) {

        if (vendorModel != null && context != null) {
            OfferModel offerModel=GlobalFunctions.getSelectedModelFromList(vendorModel.getOfferListModel().getOfferModels(),offer_id);
            Intent intent = RedeemOfferActivity.newInstance( activity, offerModel,vendorModel);
            activity.startActivity( intent );

        }
    }

}
