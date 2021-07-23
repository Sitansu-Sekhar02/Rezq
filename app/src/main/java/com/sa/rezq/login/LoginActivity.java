package com.sa.rezq.login;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.hbb20.CountryCodePicker;
import com.sa.rezq.Activity.AppController;
import com.sa.rezq.R;
import com.sa.rezq.account.AccountActivity;
import com.sa.rezq.global.GlobalFunctions;
import com.sa.rezq.global.GlobalVariables;
import com.sa.rezq.services.ServerResponseInterface;
import com.sa.rezq.services.ServicesMethodsManager;
import com.sa.rezq.services.model.LoginModel;
import com.sa.rezq.services.model.ProfileMainModel;
import com.sa.rezq.services.model.ProfileModel;
import com.sa.rezq.services.model.RegisterModel;
import com.sa.rezq.services.model.StatusMainModel;
import com.sa.rezq.services.model.StatusModel;
import com.sa.rezq.view.AlertDialog;

public class LoginActivity  extends AppCompatActivity {

    public static final String TAG = "LoginActivity";

    Context context;
    private static Activity activity;
    View mainView;

    Toolbar toolbar;
    ActionBar actionBar;

    String selected_country_code = "";

    String pageType = null;
    RegisterModel myRegisterModel = null;

    LoginModel loginModel = null;
    RegisterModel registerModel = null;

    private EditText phone_number_etv;
    private TextView continue_tv, skip_login, register_tv, login_with_otp_tv,login_with_password_tv;
    private CountryCodePicker country_code_picker;
    private EditText password_etv;

    boolean isLoginBtnEnabled = false;

    GlobalVariables globalVariables;
    GlobalFunctions globalFunctions;

    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);



        context = this;
        activity = this;

        globalFunctions = AppController.getInstance().getGlobalFunctions();
        globalVariables = AppController.getInstance().getGlobalVariables();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.app_background_color));
        }


        phone_number_etv = (EditText) findViewById(R.id.etNumber);
        country_code_picker = (CountryCodePicker) findViewById(R.id.country_code_picker);

        login=findViewById(R.id.btnLogin);
        country_code_picker.setCountryForPhoneCode(+91);

        mainView = phone_number_etv;



        country_code_picker.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                selected_country_code = country_code_picker.getSelectedCountryCodeWithPlus();
                phone_number_etv.setText("");
            }
        });

        country_code_picker.registerCarrierNumberEditText(phone_number_etv);

        country_code_picker.setPhoneNumberValidityChangeListener(new CountryCodePicker.PhoneNumberValidityChangeListener() {
            @Override
            public void onValidityChanged(boolean isValidNumber) {
            }
        });

        selected_country_code = country_code_picker.getSelectedCountryCodeWithPlus();


         phone_number_etv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().startsWith("0")) {
                    s.clear();
                }
                String digits = phone_number_etv.getText().toString().trim();
                if (selected_country_code.equalsIgnoreCase("+91")) {
                    if (digits.length() >= 10) {
                        globalFunctions.closeKeyboard(activity);
                        showSubmitButton(true);
                    } else {
                        showSubmitButton(false);
                    }
                } else if (digits.length() >= getResources().getInteger(R.integer.mobile_max_length)) {
                    globalFunctions.closeKeyboard(activity);
                    showSubmitButton(true);
                } else {
                    showSubmitButton(false);
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validateInput(globalVariables.PAGE_FROM_LOGIN);

               /* if (isLoginBtnEnabled) {
                    validateInput(globalVariables.PAGE_FROM_LOGIN);
                } else {

                }*/
               // Intent intent=new Intent(LoginActivity.this, ActivityOtpVerification.class);
              //  startActivity(intent);
            }
        });


    }
    private void showSubmitButton(boolean isShown) {

//        String pwd = password_etv.getText().toString().trim();

        if (isShown) {
            login.setTextColor(activity.getResources().getColor(R.color.colorPrimaryDark));
            login.setBackground(activity.getResources().getDrawable(R.drawable.bg_button_yellow_half_curved_filled));
            isLoginBtnEnabled = true;
        } else {
            isLoginBtnEnabled = false;
            login.setTextColor(activity.getResources().getColor(R.color.colorPrimaryDark));
            login.setBackground(activity.getResources().getDrawable(R.drawable.bg_button_color_accent_curved_filled));
        }

    }
    private void validateInput(String pageType) {
        if (phone_number_etv != null) {
            String
                    mobileNumber = phone_number_etv.getText().toString().trim();

            if (mobileNumber.isEmpty()) {
                phone_number_etv.setError(getString(R.string.enter_mobileno));
                phone_number_etv.setFocusableInTouchMode(true);
                phone_number_etv.requestFocus();
            }else if (selected_country_code.isEmpty()) {
                globalFunctions.displayMessaage(activity, mainView, getString(R.string.countryCodeNONotValid));
            } else if (!country_code_picker.isValidFullNumber()) {
//                mobile_number_etv.setText("");
                globalFunctions.displayMessaage(activity, mainView, getString(R.string.please_enetr_valid_number));
                phone_number_etv.setSelection(phone_number_etv.getText().length());
                phone_number_etv.setFocusableInTouchMode(true);
                phone_number_etv.requestFocus();
            } else {


                if (pageType != null) {
                    //go to otp page....
                    if (registerModel == null) {
                        registerModel = new RegisterModel();
                    }
                    registerModel.setCountryCode(selected_country_code);
                    registerModel.setMobileNumber(mobileNumber);
                    // registerModel.setPassword(GlobalFunctions.md5(password));

                    if (pageType.equalsIgnoreCase(globalVariables.PAGE_FROM_REGISTRATION)) {

                        LoginModel model = new LoginModel();
                        model.setMobile_number(registerModel.getMobileNumber());
                        model.setCountryCode(registerModel.getCountryCode());
                        checkMobileNumber(context, model, registerModel, pageType);

                    } else if (pageType.equalsIgnoreCase(globalVariables.PAGE_FROM_LOGIN)) {

                        LoginModel model = new LoginModel();
                        model.setMobile_number(registerModel.getMobileNumber());
                        model.setCountryCode(registerModel.getCountryCode());
                        checkMobileNumber(context, model, registerModel, pageType);

                    } else {
                        LoginModel model = new LoginModel();
                        model.setMobile_number(registerModel.getMobileNumber());
                        model.setCountryCode(registerModel.getCountryCode());
                        model.setEmail_id(mobileNumber);
                        registerModel.setEmailId(mobileNumber);
                        loginUser(context, model,registerModel);
                    }
                }
            }
        }
    }
    private void checkMobileNumber(final Context context, final LoginModel loginModel, final RegisterModel registerModel, String pageType) {
        GlobalFunctions.showProgress(activity, getString(R.string.loading));
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.checkMobileNumber(context, loginModel, new ServerResponseInterface() {
            @Override
            public void OnSuccessFromServer(Object arg0) {
                GlobalFunctions.hideProgress();
                Log.d(TAG, "Response : " + arg0.toString());
                if (arg0 instanceof StatusMainModel) {
                    StatusMainModel statusMainModel = (StatusMainModel) arg0;
                    validateOutputAfterCheckingMobileNumber(statusMainModel, registerModel, pageType);
                }
            }

            @Override
            public void OnFailureFromServer(String msg) {
                GlobalFunctions.hideProgress();
                GlobalFunctions.displayMessaage(activity, mainView, msg);
                Log.d(TAG, "Failure : " + msg);
            }

            @Override
            public void OnError(String msg) {
                GlobalFunctions.hideProgress();
                GlobalFunctions.displayMessaage(activity, mainView, msg);
                Log.d(TAG, "Error : " + msg);
            }
        }, "CheckMobileNumber");
    }
    private void loginUser(final Context context, final LoginModel model,RegisterModel registerModel) {
        globalFunctions.showProgress(activity, getString(R.string.loading));
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.loginUser(context, model, new ServerResponseInterface() {
            @Override
            public void OnSuccessFromServer(Object arg0) {
                globalFunctions.hideProgress();
                Log.d(TAG, "Response : " + arg0.toString());
                //StatusModel model = (StatusModel) arg0;
                validateOutputAfterLogin(arg0, registerModel);
            }

            @Override
            public void OnFailureFromServer(String msg) {
                globalFunctions.hideProgress();
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Failure : " + msg);
            }

            @Override
            public void OnError(String msg) {
                globalFunctions.hideProgress();
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Error : " + msg);
            }
        }, "LoginUser");
    }

    private void validateOutputAfterLogin(Object arg0, RegisterModel model) {
        if (arg0 instanceof StatusMainModel) {
            StatusMainModel statusMainModel = (StatusMainModel) arg0;
            StatusModel statusModel = statusMainModel.getStatusModel();
            if (!statusMainModel.isStatusLogin()) {
                globalFunctions.displayMessaage(activity, mainView, statusModel.getMessage());
            } else {
                    GlobalFunctions.setSharedPreferenceString(context, GlobalVariables.SHARED_PREFERENCE_TOKEN, statusModel.getToken());
                    getProfile();

                }
        }

    }

    private void showAlertMessage(String message) {

        final AlertDialog alertDialog = new AlertDialog(context);
        alertDialog.setCancelable(false);
        alertDialog.setIcon(R.drawable.rezq_logo);
        alertDialog.setTitle(getString(R.string.app_name));
        alertDialog.setMessage(message);
        alertDialog.setPositiveButton(getString(R.string.ok), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                finishAffinity();
            }
        });

        alertDialog.show();
    }


    private void showAlertMessage(String message, final RegisterModel registerModel1) {

      /*  final AlertDialog alertDialog = new AlertDialog(context);
        alertDialog.setCancelable(false);
        alertDialog.setIcon(R.drawable.rezq_logo);
        alertDialog.setTitle(getString(R.string.app_name));
        alertDialog.setMessage(message);
        alertDialog.setPositiveButton(getString(R.string.please_continue), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                pageType=globalVariables.PAGE_FROM_REGISTRATION;
                sendOtpToMyMobileNumber(registerModel1, pageType);
            }
        });

        alertDialog.setNegativeButton(getString(R.string.ok), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();

*/

        final Dialog dialog = new Dialog(activity, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.registration_popup);
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
        final Button  btn_ok = dialog.findViewById(R.id.btn_ok);
        final TextView  tv_cancel = dialog.findViewById(R.id.tv_cancel);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                pageType=globalVariables.PAGE_FROM_REGISTRATION;
                sendOtpToMyMobileNumber(registerModel1, pageType);
            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


    }


    private void getProfile() {
        // globalFunctions.showProgress(activity, getString(R.string.loading));
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.getProfile(context, new ServerResponseInterface() {
            @Override
            public void OnSuccessFromServer(Object arg0) {
                Log.d(TAG, "Response : " + arg0.toString());
                // globalFunctions.hideProgress();
                if (arg0 instanceof ProfileMainModel) {
                    ProfileMainModel profileMainModel=(ProfileMainModel) arg0;
                    ProfileModel profileModel = profileMainModel.getProfileModel();
                    GlobalFunctions.setProfile(context, profileModel);
                    closeThisActivity();
                    Intent intent = new Intent(activity, AccountActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void OnFailureFromServer(String msg) {
                // globalFunctions.hideProgress();
                globalFunctions.displayMessaage(context, mainView, msg);
                Log.d(TAG, "Failure : " + msg);
            }

            @Override
            public void OnError(String msg) {
                // globalFunctions.hideProgress();
                globalFunctions.displayMessaage(context, mainView, msg);
                Log.d(TAG, "Error : " + msg);
            }
        }, "Get Profile");
    }

    private void validateOutputAfterCheckingMobileNumber(StatusMainModel statusMainModel, RegisterModel registerModel, String pageType) {
        StatusModel statusModel = statusMainModel.getStatusModel();

        if (pageType.equalsIgnoreCase(globalVariables.PAGE_FROM_REGISTRATION)) {
            if (!statusMainModel.isStatus()) {
                //new user....go for registration..
                sendOtpToMyMobileNumber(registerModel, pageType);
//                Intent intent = RegisterActivity.newInstance(context, registerModel);
//                startActivity(intent);
            } else {
                //registered user..
                globalFunctions.displayMessaage(activity, mainView, statusModel.getMessage());
            }
        } else if (pageType.equalsIgnoreCase(globalVariables.PAGE_FROM_LOGIN)) {
            if (statusMainModel.isStatus()) {
                sendOtpToMyMobileNumber(registerModel, pageType);
            } else {
                if (statusModel.getExtra().equalsIgnoreCase("1")){
                    showAlertMessage(statusModel.getMessage(),registerModel);
                } else  if (statusModel.getExtra().equalsIgnoreCase("2")){
                    sendOtpToMyMobileNumber(registerModel, pageType);
                }else {
                    //registered user..
                    globalFunctions.displayMessaage(activity, mainView, statusModel.getMessage());
                }
            }
        }

    }


    private void sendOtpToMyMobileNumber(RegisterModel model, String pageType) {
        String phoneNumber = selected_country_code + model.getMobileNumber();
        Intent intent = OtpActivity.newInstance(context, model, phoneNumber, pageType);
        activity.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        closeThisActivity();

    }

    public static void closeThisActivity() {
        if (activity != null) {
            activity.finish();
        }
    }

    @Override
    protected void onDestroy() {
        if (activity != null) activity = null;
        super.onDestroy();
    }



}
