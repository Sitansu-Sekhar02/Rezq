package com.sa.rezq.membership;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sa.rezq.Activity.AppController;
import com.sa.rezq.R;
import com.sa.rezq.global.GlobalFunctions;
import com.sa.rezq.global.GlobalVariables;
import com.sa.rezq.services.ServerResponseInterface;
import com.sa.rezq.services.ServicesMethodsManager;
import com.sa.rezq.services.model.HomePageMainModel;
import com.sa.rezq.services.model.HomePageModel;
import com.sa.rezq.services.model.MembershipDetailsMainModel;
import com.sa.rezq.services.model.MembershipDetailsModel;
import com.sa.rezq.services.model.ProfileMembershipModel;
import com.sa.rezq.services.model.ProfileModel;
import com.sa.rezq.services.model.StatusMainModel;
import com.sa.rezq.services.model.TrendingListModel;
import com.sa.rezq.services.model.TrendingModel;
import com.squareup.picasso.Picasso;

public class MembershipDetailsActivity extends AppCompatActivity {

    public static final String TAG = "MembershipDetailsActivity";


    RecyclerView recyclerView;
    View view;
    TextView AllLockedOffer;
    Context context = null;
    static Activity activity = null;

    private TextView tv_membership_name,tv_validity,tv_valid_from,tv_valid_till;
    private ImageView iv_membership_image;
    public View mainView;

    static Toolbar toolbar;
    static ActionBar actionBar;
    static String mTitle;
    static int mResourceID;
    static int titleResourseID;
    static TextView toolbar_title;
    static ImageView toolbar_logo, tool_bar_back_icon;


    private boolean shouldRefreshOnResume = false;

    GlobalVariables globalVariables;
    GlobalFunctions globalFunctions;
    Window window = null;
    TextView tv_upgrade;

    String membership_id=null;

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership_details);

        context = this;
        activity = this;
        window = getWindow();

        globalFunctions = AppController.getInstance().getGlobalFunctions();
        globalVariables = AppController.getInstance().getGlobalVariables();

        tv_membership_name = findViewById(R.id.tv_membership_name);
        tv_validity = findViewById(R.id.tv_validity);
        tv_valid_from = findViewById(R.id.tv_valid_from);
        tv_valid_till = findViewById(R.id.tv_valid_till);
        iv_membership_image = findViewById(R.id.iv_membership_image);

        tv_upgrade = findViewById(R.id.Tv_upgradeTo_Prime);
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.black_trans));
        }

        loadMembershipDetails();

        ProfileMembershipModel profileMembershipModel=GlobalFunctions.getProfileMembership(context);
        if (profileMembershipModel!=null){
            if (GlobalFunctions.isNotNullValue(profileMembershipModel.getUpgrade_id())){
                membership_id=profileMembershipModel.getUpgrade_id();
            }
            tv_upgrade.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = UpgradeParticularMembershipActivity.newInstance( activity, membership_id);
                    startActivity( intent);
                }
            });
        }


        setTitle(getString(R.string.my_membership), 0, 0);


    }

    private void loadMembershipDetails() {
        globalFunctions.showProgress(activity, getString(R.string.loading));
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.getMembershipDetails(context, new ServerResponseInterface() {
            @SuppressLint("LongLogTag")
            @Override
            public void OnSuccessFromServer(Object arg0) {
                if (context != null) {
                    globalFunctions.hideProgress();
                    Log.d(TAG, "Response: " + arg0.toString());
                    MembershipDetailsMainModel membershipDetailsMainModel=(MembershipDetailsMainModel) arg0;
                    MembershipDetailsModel membershipDetailsModel=membershipDetailsMainModel.getMembershipDetailsModel();
                    setUpThisPage(membershipDetailsModel);
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void OnFailureFromServer(String msg) {
                if (context != null) {
                    globalFunctions.hideProgress();
                    Log.d(TAG, "Failure : " + msg);
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void OnError(String msg) {
                if (context != null) {
                     globalFunctions.hideProgress();
                    Log.d(TAG, "Error : " + msg);
                }
            }
        }, " list");
    }

    private void setUpThisPage(MembershipDetailsModel membershipDetailsModel) {
        if (membershipDetailsModel != null) {

            if (GlobalFunctions.isNotNullValue(membershipDetailsModel.getMembership_name())) {
                tv_membership_name.setText(membershipDetailsModel.getMembership_name());

            }if (GlobalFunctions.isNotNullValue(membershipDetailsModel.getImage())) {
                Picasso.with(activity).load(membershipDetailsModel.getImage()).placeholder(R.drawable.ic_lazy_load).into(iv_membership_image);

            }
            if (GlobalFunctions.isNotNullValue(membershipDetailsModel.getValid_from())) {
                tv_valid_from.setText(GlobalFunctions.getDateFormatTillDate(membershipDetailsModel.getValid_from()));

            }if (GlobalFunctions.isNotNullValue(membershipDetailsModel.getValid_till())) {
                tv_valid_till.setText(GlobalFunctions.getDateFormatTillDate(membershipDetailsModel.getValid_till()));

            }if (GlobalFunctions.isNotNullValue(membershipDetailsModel.getValidity())) {
                tv_validity.setText(membershipDetailsModel.getValidity()+" "+activity.getString(R.string.days));

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
