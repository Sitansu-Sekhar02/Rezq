package com.sa.rezq.account;

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

import com.sa.rezq.Activity.AppController;
import com.sa.rezq.R;
import com.sa.rezq.adapter.AccountListAdapter;
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
import com.vlonjatg.progressactivity.ProgressLinearLayout;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SwitchAccountActivity extends AppCompatActivity implements OpenInsertAccountInvoke {
    public static final String TAG = "SwtchAccountActivity";


    Context context;
    private static Activity activity;
    View mainView;
    CountDownTimer countDownTimer;



    static Toolbar toolbar;
    static ActionBar actionBar;
    static String mTitle;
    static int mResourceID;
    static int titleResourseID;
    static TextView toolbar_title;
    static ImageView toolbar_logo, tool_bar_back_icon;
    GlobalVariables globalVariables;
    GlobalFunctions globalFunctions;

    AccountListAdapter accountListAdapter;
    List<AccountModel> accountModels = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    GridLayoutManager gridLayoutManager;
    ProgressLinearLayout progressActivity;
    RecyclerView accountListRecyclerview;
    InsertAccountModel insertAccountModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_switch_account);

        context = this;
        activity = this;

        globalFunctions = AppController.getInstance().getGlobalFunctions();
        globalVariables = AppController.getInstance().getGlobalVariables();

        linearLayoutManager = new LinearLayoutManager(activity);
        gridLayoutManager=new GridLayoutManager(activity,3);
        progressActivity = findViewById(R.id.account_switch_progressActivity);
        accountListRecyclerview = findViewById(R.id.recyclerview_switch_account_list);


        mainView = accountListRecyclerview;

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

        setTitle(getString(R.string.accounts), 0, 0);

        getUserList();


    }

    private void getUserList() {
        globalFunctions.showProgress(activity, getString(R.string.loading));
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.getAccountList(context, new ServerResponseInterface() {
            @SuppressLint("LongLogTag")
            @Override
            public void OnSuccessFromServer(Object arg0) {
                globalFunctions.hideProgress();
                Log.d(TAG, "Response : " + arg0.toString());
                AccountMainModel accountMainModel = (AccountMainModel) arg0;
                if (accountMainModel!=null && accountMainModel.getAccountListModel()!=null){
                    AccountListModel listModel = accountMainModel.getAccountListModel();
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

    private void setThisPage(AccountListModel accountListModel) {
        if (accountListModel != null && accountModels != null) {
            accountModels.clear();
            accountModels.addAll(accountListModel.getAccountModels());
           // setStaticAccount();
            if (accountListAdapter != null) {
                synchronized (accountListAdapter) {
                    accountListAdapter.notifyDataSetChanged();
                }
            }

            if (accountModels.size() > 0) {
                showContent();
                initRecyclerView();
            }
        }
    }

    private void setStaticAccount() {
        if (accountModels==null){accountModels=new ArrayList<>();}
        AccountModel accountModel=new AccountModel();
        accountModel.setFirst_name("Add");
        accountModel.setId("0");
        accountModels.add(accountModel);
    }

    private void showContent() {
        if (progressActivity != null) {
            progressActivity.showContent();
        }
    }

    private void initRecyclerView() {
        accountListRecyclerview.setLayoutManager(gridLayoutManager);
        accountListRecyclerview.setHasFixedSize(true);
        accountListAdapter = new AccountListAdapter(activity, accountModels,this::OnItemClickListener);
        accountListRecyclerview.setAdapter(accountListAdapter);
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
    public void OnItemClickListener(AccountModel position) {
      //  openAccountPopup(position);
    }

    private void openAccountPopup(AccountModel position) {
        final Dialog dialog = new Dialog(activity, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_new_account_activity);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.show();
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        final EditText  et_UserfistName = dialog.findViewById(R.id.et_UserfirstName);
        final EditText  et_UserLastName = dialog.findViewById(R.id.et_UserlastName);
        final CircleImageView iv_profile_image = dialog.findViewById(R.id.profile_image);
        final Button  btn_Continue = dialog.findViewById(R.id.btn_Continue);

        btn_Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_UserfistName != null && et_UserLastName != null) {
                    String
                            firstName = et_UserfistName.getText().toString().trim(),
                            lastName = et_UserLastName.getText().toString().trim();
                    if (firstName.isEmpty()) {
                        et_UserfistName.setError(getString(R.string.pleaseFillMandatoryDetails));
                        et_UserfistName.setFocusableInTouchMode(true);
                        et_UserfistName.requestFocus();
                    } else if (lastName.isEmpty()) {
                        et_UserLastName.setError(getString(R.string.pleaseFillMandatoryDetails));
                        et_UserLastName.setFocusableInTouchMode(true);
                        et_UserLastName.requestFocus();
                    } else {
                        if (insertAccountModel == null) {
                            insertAccountModel = new InsertAccountModel();
                        }
                        insertAccountModel.setFirst_name(firstName);
                        insertAccountModel.setLast_name(lastName);


                        insertNewUser(activity, insertAccountModel);

                           /* if (profileImageList.size() > 0) {
                                //uploadImage(GlobalVariables.UPLOAD_PROFILE_PHOTO_PATH_CODE);

                            } else {
                                insertNewUser(activity, insertAccountModel,holder);
                            }*/

                    }
                }
            }
        });
    }

    private void insertNewUser(Activity activity, InsertAccountModel insertAccountModel) {
        // globalFunctions.showProgress(activity, activity.getString(R.string.loading));
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.insertUser(context, insertAccountModel, new ServerResponseInterface() {
            @Override
            public void OnSuccessFromServer(Object arg0) {
                //globalFunctions.hideProgress();
                Log.d(TAG, "Response : " + arg0.toString());
                //StatusModel model = (StatusModel) arg0;
                validateOutputAfterInsertUser(arg0);
            }

            @Override
            public void OnFailureFromServer(String msg) {
                //  globalFunctions.hideProgress();
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Failure : " + msg);
            }

            @Override
            public void OnError(String msg) {
                // globalFunctions.hideProgress();
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Error : " + msg);
            }
        }, "Register_User");
    }

    private void validateOutputAfterInsertUser(Object arg0) {
        if (arg0 instanceof StatusMainModel) {
            StatusMainModel statusMainModel = (StatusMainModel) arg0;
            StatusModel statusModel = statusMainModel.getStatusModel();
            if (!statusMainModel.isStatusLogin()) {
                globalFunctions.displayMessaage(activity, mainView, statusModel.getMessage());
            } else {
                GlobalFunctions.setSharedPreferenceString(context, GlobalVariables.SHARED_PREFERENCE_TOKEN, statusModel.getToken());
               // getProfile();

            }
        }
    }
}
