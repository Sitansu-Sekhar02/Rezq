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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sa.rezq.Activity.AppController;
import com.sa.rezq.R;
import com.sa.rezq.adapter.OfferListAdapter;
import com.sa.rezq.adapter.ReviewListAdapter;
import com.sa.rezq.adapter.VendorListAdapter;
import com.sa.rezq.global.GlobalFunctions;
import com.sa.rezq.global.GlobalVariables;
import com.sa.rezq.services.ServerResponseInterface;
import com.sa.rezq.services.ServicesMethodsManager;
import com.sa.rezq.services.model.OfferListModel;
import com.sa.rezq.services.model.OfferModel;
import com.sa.rezq.services.model.ReviewListModel;
import com.sa.rezq.services.model.ReviewModel;
import com.sa.rezq.services.model.TrendingModel;
import com.sa.rezq.services.model.VendorDetailsMainModel;
import com.sa.rezq.services.model.VendorModel;
import com.sa.rezq.services.model.VendorStoreListModel;
import com.sa.rezq.services.model.VendorStoreMainModel;
import com.squareup.picasso.Picasso;
import com.vlonjatg.progressactivity.ProgressLinearLayout;

import java.util.ArrayList;
import java.util.List;

public class VendorStoreListActivity extends AppCompatActivity {

    public static final String TAG = "VendorStoreListActivity",
            BUNDLE_VENDOR_STORE_LIST_DETAILS = "VendorStoreListActivity";

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


    VendorListAdapter vendorListAdapter;
    List<VendorStoreListModel> vendorStoreListModels = new ArrayList<>();
    LinearLayoutManager storelistLinear;
    ProgressLinearLayout reviewprogressActivity;
    RecyclerView vendor_store_list_recyclerview;
    SwipeRefreshLayout review_swipe_container;

    private boolean shouldRefreshOnResume = false;
    VendorModel vendorModel = null;

    GlobalVariables globalVariables;
    GlobalFunctions globalFunctions;
    Window window = null;

    String vendor_id = null;
    TrendingModel trendingModel = null;

    FirebaseStorage storage;
    StorageReference storageReference;

    public static Intent newInstance(Activity activity, TrendingModel model) {
        Intent intent = new Intent(activity, VendorStoreListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_VENDOR_STORE_LIST_DETAILS, model);
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


        if (getIntent().hasExtra(BUNDLE_VENDOR_STORE_LIST_DETAILS)) {
            trendingModel = (TrendingModel) getIntent().getSerializableExtra(BUNDLE_VENDOR_STORE_LIST_DETAILS);
        } else {
            trendingModel = null;
        }

        if (trendingModel != null) {
            if (GlobalFunctions.isNotNullValue(trendingModel.getId())) {
                Log.d(TAG,trendingModel.getId());
                vendor_id = trendingModel.getId();
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

        setTitle(getString(R.string.offers), 0, 0);



        getOfferList();


    }


    private void getOfferList() {
        GlobalFunctions.showProgress(context, getString(R.string.loading));
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.getVendorStoreList(context, String.valueOf(2), String.valueOf(108), new ServerResponseInterface() {
            @SuppressLint("LongLogTag")
            @Override
            public void OnSuccessFromServer(Object arg0) {
                GlobalFunctions.hideProgress();
                Log.d(TAG, "Response : " + arg0.toString());
                VendorStoreMainModel vendorStoreMainModel = (VendorStoreMainModel) arg0;
                VendorStoreListModel vendorStoreListModel = vendorStoreMainModel.getVendorModel();

                if (vendorStoreListModel != null) {
                    setThisPage(vendorStoreListModel);
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
        }, "Store List");
    }

    private void setThisPage(VendorStoreListModel vendorStoreListModel) {
        if (vendorStoreListModel != null && vendorStoreListModels != null) {
            vendorStoreListModels.clear();
            vendorStoreListModels.addAll( vendorStoreListModel.getVendorStoreListModels() );
            if (vendorListAdapter != null) {
                synchronized (vendorListAdapter) {
                    vendorListAdapter.notifyDataSetChanged();
                }
            }
            if (vendorStoreListModels.size() <= 0) {
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
        vendor_store_list_recyclerview.setLayoutManager(new GridLayoutManager(this, 2));
        vendor_store_list_recyclerview.setHasFixedSize(true);
        vendorListAdapter = new VendorListAdapter(activity, vendorStoreListModels);
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

//
//        //*Recyclerview Adapter*//
//        public class LockedOfferAdapter extends RecyclerView.Adapter<LockedOfferAdapter.MyViewHolder> {
//
//            private List<CategoryModelClass> moviesList;
//
//            public class MyViewHolder extends RecyclerView.ViewHolder {
//                TextView title;
//                RelativeLayout lockedOffer;
//
//                public MyViewHolder(View view) {
//                    super(view);
//                    title = (TextView) view.findViewById(R.id.tvFood);
//                    lockedOffer = view.findViewById(R.id.lockedOffer);
//                }
//            }
//
//
//            public LockedOfferAdapter(List<CategoryModelClass> moviesList) {
//                this.moviesList = moviesList;
//            }
//
//            @Override
//            public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                View itemView = LayoutInflater.from(parent.getContext())
//                        .inflate(R.layout.rezq_plus_offer_dataview, parent, false);
//
//                return new MyViewHolder(itemView);
//            }
//
//            @Override
//            public void onBindViewHolder(MyViewHolder holder, int position) {
//                CategoryModelClass movie = moviesList.get(position);
//                holder.lockedOffer.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        //  replaceFragmentWithAnimation(new OfferDetailsFragment());
//
//                    }
//                });
//
//            }
//
//            @Override
//            public int getItemCount() {
//                return moviesList.size();
//            }
//
//        /*public void replaceFragmentWithAnimation(Fragment fragment) {
//            FragmentTransaction transaction =getActivity().getSupportFragmentManager().beginTransaction();
//            transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
//            transaction.replace(R.id.fragment_container, fragment);
//            transaction.commit();
//        }*/
//        }


//        //*Recyclerview All locked offer list*//
//
//        public class AllLockedList extends RecyclerView.Adapter<AllLockedList.MyViewHolder> {
//
//            private List<CategoryModelClass> moviesList;
//
//            public class MyViewHolder extends RecyclerView.ViewHolder {
//                TextView title;
//                RelativeLayout lockedOffer;
//
//                public MyViewHolder(View view) {
//                    super(view);
//                    title = (TextView) view.findViewById(R.id.tvFood);
//                    lockedOffer = view.findViewById(R.id.lockedOffer);
//                }
//            }
//
//
//            public AllLockedList(List<CategoryModelClass> moviesList) {
//                this.moviesList = moviesList;
//            }
//
//            @Override
//            public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                View itemView = LayoutInflater.from(parent.getContext())
//                        .inflate(R.layout.rezq_plus_offer_dataview, parent, false);
//
//                return new MyViewHolder(itemView);
//            }
//
//            @Override
//            public void onBindViewHolder(MyViewHolder holder, int position) {
//
//            }
//
//            @Override
//            public int getItemCount() {
//                return moviesList.size();
//            }
//
//        }


    }
