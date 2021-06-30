package com.sa.rezq.membership;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.sa.rezq.Activity.AppController;
import com.sa.rezq.Activity.MainActivity;
import com.sa.rezq.R;
import com.sa.rezq.adapter.MembershipDescListAdapter;
import com.sa.rezq.adapter.MembershipListAdapter;
import com.sa.rezq.global.GlobalFunctions;
import com.sa.rezq.global.GlobalVariables;
import com.sa.rezq.services.ServerResponseInterface;
import com.sa.rezq.services.ServicesMethodsManager;
import com.sa.rezq.services.model.MembershipListModel;
import com.sa.rezq.services.model.MembershipModel;
import com.sa.rezq.services.model.UpgradeMembershipMainModel;
import com.sa.rezq.services.model.UpgradeMembershipModel;
import com.sa.rezq.view.AlertDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class UpgradeParticularMembershipActivity extends AppCompatActivity {

    public static final String TAG = "UpgradeGoldMembershipActivity",
                                BUNDLE_MEMBERSHIP_ID = "MembershipId";


    public static final int SLIDER_LOAD_TIME_IN_MILLI_SEC=2000;

    RecyclerView recyclerView;
    View view;
    TextView AllLockedOffer;
    Context context = null;
    static Activity activity = null;

    private EditText public_name_etv, cr_number_etv, vat_number_etv, m_first_name_etv, m_last_name_etv, m_email_etv, shop_url_etv, head_office_address_etv, mobile_number_etv;
    private TextView price,tv_desc_type,tv_no_ofDays,tv_plan_title;
    private ImageView membership_image;
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
    MembershipDescListAdapter descriptionListAdapter;
    List<MembershipModel> membershipModels = new ArrayList<>();
    MembershipModel membershipModel=null;
    UpgradeMembershipModel upgradeMembershipModel=null;
    String membership_id=null;


    int review_position = 0;
    MembershipListModel membershipListModel=null;

    List<String> serviceNameList=new ArrayList<>();

    public static Intent newInstance(Activity activity, String membership_id) {
        Intent intent = new Intent(activity, UpgradeParticularMembershipActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_MEMBERSHIP_ID, membership_id);
        intent.putExtras(bundle);
        return intent;
    }


    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gold_membership_activity);

        context = this;
        activity = this;
        window = getWindow();


        globalFunctions = AppController.getInstance().getGlobalFunctions();
        globalVariables = AppController.getInstance().getGlobalVariables();



        tv_desc_type = findViewById(R.id.tv_desc_type);
        tv_no_ofDays = findViewById(R.id.tv_no_ofDays);
        tv_plan_title = findViewById(R.id.tv_plan_title);
        membership_image = findViewById(R.id.iv_gold_member);

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

        if (getIntent().hasExtra(BUNDLE_MEMBERSHIP_ID)) {

            membership_id = String.valueOf(getIntent().getSerializableExtra(BUNDLE_MEMBERSHIP_ID));
           // Log.e("memmmm",""+membership_id);

        } else {
            membership_id = null;
        }
        if (upgradeMembershipModel != null) {
            if (GlobalFunctions.isNotNullValue(upgradeMembershipModel.getId())) {
                Log.d(TAG,upgradeMembershipModel.getId());
                membership_id = upgradeMembershipModel.getId();
            }
        }

        loadListBanner(membership_id);

        mainView = toolbar;


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.black_trans));
        }


        setTitle(getString(R.string.upgrade_membership), 0, 0);

    }

    private void insertMembership(UpgradeMembershipModel membershipModel) {
        //globalFunctions.showProgress(activity, getString(R.string.loading));
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.insertParticularMembership(context,membershipModel, new ServerResponseInterface() {
            @SuppressLint("LongLogTag")
            @Override
            public void OnSuccessFromServer(Object arg0) {
                // globalFunctions.hideProgress();
                Log.d(TAG, "Response : " + arg0.toString());
                validOutputAfterClick(arg0);

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
        }, "Click ");
    }

    private void validOutputAfterClick(Object arg0) {
        if (arg0 instanceof UpgradeMembershipMainModel) {
            UpgradeMembershipMainModel membershipMainModel = (UpgradeMembershipMainModel) arg0;
           // UpgradeMembershipModel upgradeMembershipModel = membershipMainModel.getMessage();
            if (membershipMainModel.isStatus()) {
                openSuccessDialog(membershipMainModel.getMessage());
            } else {

                globalFunctions.displayMessaage(activity, mainView, membershipMainModel.getMessage());

            }
        }
    }

    private void openSuccessDialog(String message) {
        final AlertDialog alertDialog = new AlertDialog( activity );
        alertDialog.setCancelable( false );
        alertDialog.setIcon( R.drawable.rezq_logo );
        alertDialog.setTitle( activity.getString( R.string.app_name ) );
        alertDialog.setMessage( message);
        alertDialog.setPositiveButton( activity.getString( R.string.ok ), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity, MainActivity.class);
                startActivity(intent);
                alertDialog.dismiss();

            }
        } );

        alertDialog.show();
    }

    private void loadListBanner(String membership_id) {
        globalFunctions.showProgress(activity, getString(R.string.loading));
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.upgradeParticularMembership(context,membership_id, new ServerResponseInterface() {
            @SuppressLint("LongLogTag")
            @Override
            public void OnSuccessFromServer(Object arg0) {
                 globalFunctions.hideProgress();
                Log.d(TAG, "Response : " + arg0.toString());
                UpgradeMembershipMainModel upgradeMembershipMainModel = (UpgradeMembershipMainModel) arg0;
                UpgradeMembershipModel upgradeMembershipModel=upgradeMembershipMainModel.getUpgradeMembershipModel();
                setThisPage(upgradeMembershipModel);


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
        }, "Banners");
    }




    private void setThisPage(UpgradeMembershipModel membershipModel) {
        if (membershipModel != null) {
                if (globalFunctions.isNotNullValue(membershipModel.getImage())) {
                    Picasso.with(context).load(membershipModel.getImage()).placeholder(R.drawable.rezq_logo).into(membership_image);

                }
                if (globalFunctions.isNotNullValue(membershipModel.getPrice())) {
                    price.setText(activity.getString(R.string.sar)+" "+membershipModel.getPrice()+" /");

                }
                if (globalFunctions.isNotNullValue(membershipModel.getNo_of_days())) {
                    tv_no_ofDays.setText(membershipModel.getNo_of_days()+activity.getString(R.string.days));

                 }
                if (globalFunctions.isNotNullValue(membershipModel.getName())) {
                        tv_plan_title.setText(membershipModel.getName());

                }


            if (GlobalFunctions.isNotNullValue(membershipModel.getDescription())) {
//                List<String> serviceNameList = new ArrayList<>();
//                serviceNameList.clear();
                tv_desc_type.setText(globalFunctions.getHTMLString(membershipModel.getDescription()));
//                tv_desc_type.setText(globalFunctions.html2text(tv_desc_type.getText().toString().trim()));

//                String[] serviceList = membershipModel.getDescription().split("</div>");

               /* for (int i = 0; i < serviceList.length; i++) {
                    String value = serviceList[i];
                    serviceNameList.add(value);
                }

                if (serviceNameList.size() > 0) {
                    descriptionListAdapter.updatePlanList(serviceNameList);
                }*/

            }
            btnSubscribe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (GlobalFunctions.isNotNullValue(membershipModel.getId())) {
                        membership_id=membershipModel.getId();
                      //  Log.e("membershipid",""+membership_id);

                    }
                    membershipModel.setMembership_id(membership_id);
                    insertMembership(membershipModel);

                }
            });




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
