package com.sa.rezq.membership;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sa.rezq.Activity.AppController;
import com.sa.rezq.Models.CategoryModelClass;
import com.sa.rezq.R;
import com.sa.rezq.global.GlobalFunctions;
import com.sa.rezq.global.GlobalVariables;
import com.sa.rezq.services.model.TrendingModel;

import java.util.ArrayList;
import java.util.List;

public class MembershipActivity extends AppCompatActivity {

    public static final String TAG = "MembershipActivity";


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
    Menu menu;
    String account = String.valueOf(1);


    private boolean shouldRefreshOnResume = false;

    GlobalVariables globalVariables;
    GlobalFunctions globalFunctions;
    Window window = null;
    ImageView vendor_list_image,iv_favourite;
    TextView tv_vendor_name,tvRating,tv_address,tv_open_map,tv_rating_count;

    String vendor_id = null;
    TrendingModel trendingModel = null;

    FirebaseStorage storage;
    StorageReference storageReference;


    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_membership);

        context = this;
        activity = this;
        window = getWindow();

        globalFunctions = AppController.getInstance().getGlobalFunctions();
        globalVariables = AppController.getInstance().getGlobalVariables();
        //linearLayoutManager = new LinearLayoutManager(activity);
       // progressActivity = findViewById(R.id.details_progressActivity);
       // swipe_container = findViewById(R.id.swipe_container);
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

        setTitle(getString(R.string.my_membership), 0, 0);


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

   /* public void replaceFragmentWithAnimation(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }*/


    //*Recyclerview Adapter*//


    public class RecentCouponAdapter extends RecyclerView.Adapter<RecentCouponAdapter.MyViewHolder> {

        private List<CategoryModelClass> moviesList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView title;

            public MyViewHolder(View view) {
                super(view);
                title = (TextView) view.findViewById(R.id.tvFood);
            }
        }


        public RecentCouponAdapter(List<CategoryModelClass> moviesList) {
            this.moviesList = moviesList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recent_coupon_dataview, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            CategoryModelClass movie = moviesList.get(position);
            //holder.title.setText(movie.getCategory_name());

        }

        @Override
        public int getItemCount() {
            return moviesList.size();
        }

        /*public void replaceFragmentWithAnimation(Fragment fragment) {
            FragmentTransaction transaction =getActivity().getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
            transaction.replace(R.id.fragment_container, fragment);
            transaction.commit();
        }*/
    }
}