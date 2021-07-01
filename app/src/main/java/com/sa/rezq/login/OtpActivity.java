package com.sa.rezq.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.mukesh.OtpView;
import com.sa.rezq.Activity.AppController;
import com.sa.rezq.Activity.MainActivity;
import com.sa.rezq.R;
import com.sa.rezq.account.AccountActivity;
import com.sa.rezq.global.GlobalFunctions;
import com.sa.rezq.global.GlobalVariables;
import com.sa.rezq.registration.RegisterActivity;
import com.sa.rezq.services.ServerResponseInterface;
import com.sa.rezq.services.ServicesMethodsManager;
import com.sa.rezq.services.model.LoginModel;
import com.sa.rezq.services.model.ProfileMainModel;
import com.sa.rezq.services.model.ProfileModel;
import com.sa.rezq.services.model.RegisterModel;
import com.sa.rezq.services.model.StatusMainModel;
import com.sa.rezq.services.model.StatusModel;
import com.sa.rezq.services.model.SubProfileModel;
import com.sa.rezq.view.AlertDialog;


import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class OtpActivity extends AppCompatActivity {

    public static final String TAG = "OtpActivity";

    public static final String BUNDLE_OTP_ACTIVITY_REGISTER_MODEL = "BundleOtpActivityRegisterModel";
    public static final String BUNDLE_OTP_ACTIVITY_PHN_NUMBER = "BundleOtpActivityPhoneNumber";
    public static final String BUNDLE_OTP_ACTIVITY_PAGE_TYPE = "BundleOtpActivityPageType";

    private static int RESEND_OTP_TOTAL_TIME_OUT = 60000;
    private static int RESEND_OTP_TIME_INTERVAL = 1000;
    private static int OTP_ATTEMPT_COUNT = 0;
    private static int OTP_MAX_ATTEMPT_COUNT = 6;

    Context context;
    private static Activity activity;
    View mainView;

    private FirebaseAuth mAuth;

    RegisterModel registerModel = null;
    String phoneNumber = "";
    String pageType = null;
    String forgotPassword = "";
    private String verificationId;

    GlobalVariables globalVariables;
    GlobalFunctions globalFunctions;
    Button btn_verify;

    private TextView continue_tv, otp_timer_tv, resend_tv, mobile_number_tv, login_with_password_tv, edit_tv;
    private OtpView otpView;
    CountDownTimer countDownTimer;

    public static Intent newInstance(Context context, RegisterModel registerModel, String phoneNumber, String pageType) {
        Intent intent = new Intent(context, OtpActivity.class);
        Bundle args = new Bundle();
        args.putSerializable(BUNDLE_OTP_ACTIVITY_REGISTER_MODEL, registerModel);
        args.putString(BUNDLE_OTP_ACTIVITY_PHN_NUMBER, phoneNumber);
        args.putString(BUNDLE_OTP_ACTIVITY_PAGE_TYPE, pageType);
        intent.putExtras(args);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.otpverification_activity);

        mAuth = FirebaseAuth.getInstance();

        context = this;
        activity = this;

        globalFunctions = AppController.getInstance().getGlobalFunctions();
        globalVariables = AppController.getInstance().getGlobalVariables();

    /*    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.ColorStatusBar));
        }*/

        otpView = (OtpView) findViewById(R.id.otp_view);
       // mobile_number_tv = (OtpView) findViewById(R.id.otp_view);

        btn_verify =  findViewById(R.id.btnVerify);
        otp_timer_tv = (TextView) findViewById(R.id.otp_timer_tv);
        resend_tv = (TextView) findViewById(R.id.resend_code_tv);
        //mobile_number_tv = (TextView) findViewById(R.id.mobile_number_tv);
        //login_with_password_tv = (TextView) findViewById(R.id.login_with_password_tv);
      //  edit_tv = (TextView) findViewById(R.id.edit_tv);

        mainView = btn_verify;

        if (getIntent().hasExtra(BUNDLE_OTP_ACTIVITY_REGISTER_MODEL)) {
            registerModel = (RegisterModel) getIntent().getSerializableExtra(BUNDLE_OTP_ACTIVITY_REGISTER_MODEL);
        } else {
            registerModel = null;
        }

        if (getIntent().hasExtra(BUNDLE_OTP_ACTIVITY_PHN_NUMBER)) {
            phoneNumber = (String) getIntent().getStringExtra(BUNDLE_OTP_ACTIVITY_PHN_NUMBER);
        } else {
            phoneNumber = null;
        }

        if (getIntent().hasExtra(BUNDLE_OTP_ACTIVITY_PAGE_TYPE)) {
            pageType = (String) getIntent().getStringExtra(BUNDLE_OTP_ACTIVITY_PAGE_TYPE);
        } else {
            pageType = null;
        }
/*
        if (registerModel != null) {
            if (globalFunctions.isNotNullValue(registerModel.getMobileNumber()) && globalFunctions.isNotNullValue(registerModel.getCountryCode())) {
                mobile_number_tv.setText(activity.getString(R.string.verification_code_sent_text) + " " + registerModel.getCountryCode() + registerModel.getMobileNumber());
            }
        }*/
/*

        if (phoneNumber != null) {
            //once got client firebase id...add setup firebase and uncomment below 2 lines...
           sendVerificationCode(phoneNumber);
           startCountDownTimer();
        }
*/

        otpView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String digits = otpView.getText().toString().trim();
                if (digits.length() >= 6) {
                    globalFunctions.closeKeyboard(activity);
                }
            }
        });

      /*  resend_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (phoneNumber != null && OTP_ATTEMPT_COUNT < OTP_MAX_ATTEMPT_COUNT) {
                    sendVerificationCode(phoneNumber);
                    startCountDownTimer();
                } else {
                    openMaxAttemptOtpDialog(context, true);
                }
            }
        });
*/
        btn_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code = otpView.getText().toString().trim();

                if (code.isEmpty() || code.length() < 6) {

                    otpView.setError(getString(R.string.please_enter_valid_otp));
                    otpView.requestFocus();
                    return;
                }

              /*  if (OTP_ATTEMPT_COUNT < OTP_MAX_ATTEMPT_COUNT) {
                    globalFunctions.showProgress(context, activity.getString(R.string.loading));
                    verifyCode(code);
                } else {
                    openMaxAttemptOtpDialog(context, true);
                }*/

                //once got client firebase id...add setup firebase and comment below lines...
                //below lines for static otp...any otp it will take...
                if (pageType != null) {
                    if (pageType.equalsIgnoreCase(globalVariables.PAGE_FROM_REGISTRATION)) {
//
                        Intent intent = RegisterActivity.newInstance(context, registerModel);
                        startActivity(intent);

                    } else if (pageType.equalsIgnoreCase(globalVariables.PAGE_FROM_LOGIN)) {
                        LoginModel model = new LoginModel();
                        model.setMobile_number(registerModel.getMobileNumber());
                        model.setCountryCode(registerModel.getCountryCode());
                        model.setEmail_id(registerModel.getEmailId());
                        registerModel.setEmailId(registerModel.getMobileNumber());

                        loginUser(context,model, registerModel);
                    } else if (pageType.equalsIgnoreCase(globalVariables.PAGE_TYPE_FORGOT_PASSWORD)) {
                        // go to forgotPassword activity and pass number
                        // phoneNumberWithoutCountryCode
                        if (registerModel != null) {
//                            Intent intent = ResetPasswordActivity.newInstance(context, registerModel);
//                            startActivity(intent);
                        }
                    }
                }
            }
        });

    }
    private void startCountDownTimer() {
        countDownTimer = new CountDownTimer(RESEND_OTP_TOTAL_TIME_OUT, RESEND_OTP_TIME_INTERVAL) {

            public void onTick(long millisUntilFinished) {
                resend_tv.setEnabled(false);
                resend_tv.setClickable(false);
                if (getApplicationContext() != null) {
                    String text = String.format(Locale.getDefault(), "%02d : %02d seconds",
                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60);
                    otp_timer_tv.setText(getString(R.string.in) + " " + text);
                    // resend_tv.setTextColor(globalFunctions.getColor(R.color.app_fontColor_hint));
                }
            }

            @Override
            public void onFinish() {
                otp_timer_tv.setText("");
                resend_tv.setEnabled(true);
                resend_tv.setClickable(true);
                // resend_tv.setTextColor(globalFunctions.getColor(R.color.blue));
            }
        };
        countDownTimer.start();
    }


    private void openMaxAttemptOtpDialog(Context context, final boolean isMaxAttempt) {
        final AlertDialog alertDialog = new AlertDialog(context);
        alertDialog.setCancelable(false);
        alertDialog.setIcon(R.drawable.rezq_logo);
        alertDialog.setTitle(getString(R.string.app_name));
        if (isMaxAttempt) {
            alertDialog.setMessage(getString(R.string.otp_max_attempt));
        } else {
            alertDialog.setMessage(getString(R.string.invalid_otp) + "! " + getString(R.string.please_enter_valid_otp));
        }
        alertDialog.setPositiveButton(getString(R.string.ok), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                if (isMaxAttempt) {
                    closeThisActivity();
                }
            }
        });

        alertDialog.show();
    }


    private void sendVerificationCode(String number) {
      /*  GlobalFunctions.showProgress(activity, context.getString(R.string.sending_otp));
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                 TaskExecutors.MAIN_THREAD,
                mCallBack
        );*/

        GlobalFunctions.showProgress(activity, context.getString(R.string.sending_otp));
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(number)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallBack)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            GlobalFunctions.hideProgress();
            verificationId = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            GlobalFunctions.hideProgress();
            if (code != null) {
                otpView.setText(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            GlobalFunctions.hideProgress();
            Toast.makeText(OtpActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    private void verifyCode(String code) {
        try {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
            signInWithCredential(credential);
        } catch (Exception Exccc) {
            OTP_ATTEMPT_COUNT++;
            openMaxAttemptOtpDialog(context, false);
            //otp incorrect...
            // GlobalFunctions.hideProgress();
//            GlobalFunctions.displayMessaage(activity, mainView, activity.getString(R.string.please_enter_valid_otp));
        }
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        OTP_ATTEMPT_COUNT++;
                        GlobalFunctions.hideProgress();
                        if (task != null) {
                            if (task.isSuccessful()) {
                                if (pageType != null) {
                                    if (pageType.equalsIgnoreCase(globalVariables.PAGE_FROM_REGISTRATION)) {
                                        //registerUser(context, registerModel);
                                        //check mobile number..
                                       /* LoginModel model = new LoginModel();
                                        model.setMobile(registerModel.getMobileNumber());
                                        model.setCountryCode(registerModel.getCountryCode());
                                        checkMobileNumber(context, model, registerModel);*/

                                        Intent intent = RegisterActivity.newInstance(context, registerModel);
                                        startActivity(intent);


                                    } else if (pageType.equalsIgnoreCase(globalVariables.PAGE_FROM_LOGIN)) {
                                        LoginModel model = new LoginModel();
                                        model.setMobile_number(registerModel.getMobileNumber());
                                        model.setCountryCode(registerModel.getCountryCode());
                                        model.setEmail_id(registerModel.getEmailId());
                                        registerModel.setEmailId(registerModel.getMobileNumber());
                                        loginUser(context,model, registerModel);
                                    } else if (pageType.equalsIgnoreCase(globalVariables.PAGE_TYPE_FORGOT_PASSWORD)) {
                                        // go to forgotPassword activity and pass number
                                        // phoneNumberWithoutCountryCode
                                       /* if (registerModel != null) {
                                            Intent intent = ResetPasswordActivity.newInstance(context, registerModel);
                                            startActivity(intent);
                                        }*/
                                    }
                                }
                            } else {
                                Toast.makeText(OtpActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                otpView.setText("");
                            }
                        }
                    }
                });
    }

    private void checkMobileNumber(final Context context, final LoginModel loginModel, final RegisterModel registerModel) {
        GlobalFunctions.showProgress(activity, getString(R.string.loading));
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.checkMobileNumber(context, loginModel, new ServerResponseInterface() {
            @Override
            public void OnSuccessFromServer(Object arg0) {
                GlobalFunctions.hideProgress();
                Log.d(TAG, "Response : " + arg0.toString());
                if (arg0 instanceof StatusModel) {
                    validateOutputAfterCheckingMobileNumber((StatusModel) arg0, registerModel);
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

    private void validateOutputAfterCheckingMobileNumber(StatusModel statusModel, RegisterModel registerModel) {
        if (!statusModel.isStatus()) {
            //new user....go for registration..
            Intent intent = RegisterActivity.newInstance(context, registerModel);
            startActivity(intent);
        } else {
            //registered user..
            globalFunctions.displayMessaage(activity, mainView, statusModel.getMessage());
        }
    }

    private void loginUser(final Context context, final  LoginModel loginModel,final RegisterModel model) {
        globalFunctions.showProgress(activity, getString(R.string.loading));
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.loginUser(context, loginModel, new ServerResponseInterface() {
            @Override
            public void OnSuccessFromServer(Object arg0) {
                globalFunctions.hideProgress();
                Log.d(TAG, "Response : " + arg0.toString());
                //StatusModel model = (StatusModel) arg0;
                validateOutputAfterLogin(arg0, model);
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

    private void validateOutputAfterLogin(Object model, RegisterModel registerModel1) {
        if (model instanceof StatusMainModel) {
            StatusMainModel statusMainModel = (StatusMainModel) model;
            StatusModel statusModel = statusMainModel.getStatusModel();
            if (!statusMainModel.isStatusLogin()) {
                globalFunctions.displayMessaage(activity, mainView, statusModel.getMessage());
            }else {
                GlobalFunctions.setSharedPreferenceString(context, GlobalVariables.SHARED_PREFERENCE_TOKEN, statusModel.getToken());
              // GlobalFunctions.setSharedPreferenceString(context, GlobalVariables.SHARED_PREFERENCE_ACCOUNT_ID, statusModel.get());
//                getProfile();
//                closeThisActivity();
//                Intent intent = new Intent(activity, MainActivity.class);
//                startActivity(intent);

                closeThisActivity();
                Intent intent = new Intent(activity, AccountActivity.class);
                startActivity(intent);
            }
        }
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

        final AlertDialog alertDialog = new AlertDialog(context);
        alertDialog.setCancelable(false);
        alertDialog.setIcon(R.drawable.rezq_logo);
        alertDialog.setTitle(getString(R.string.app_name));
        alertDialog.setMessage(message);
        alertDialog.setPositiveButton(getString(R.string.ok), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                //go to registration page...
                // Intent intent = new Intent(activity, RegisterActivity.class);
                Intent intent = RegisterActivity.newInstance(context, registerModel1);
                startActivity(intent);
            }
        });

        alertDialog.setNegativeButton(getString(R.string.cancel), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

   /* private void startCountDownTimer() {
        countDownTimer = new CountDownTimer(RESEND_OTP_TOTAL_TIME_OUT, RESEND_OTP_TIME_INTERVAL) {

            public void onTick(long millisUntilFinished) {
                resend_tv.setEnabled(false);
                resend_tv.setClickable(false);
                if (getApplicationContext() != null) {
                    String text = String.format(Locale.getDefault(), "%02d : %02d seconds",
                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60);
                    otp_timer_tv.setText(getString(R.string.in) + " " + text);
                    // resend_tv.setTextColor(globalFunctions.getColor(R.color.app_fontColor_hint));
                }
            }

            @Override
            public void onFinish() {
                otp_timer_tv.setText("");
                resend_tv.setEnabled(true);
                resend_tv.setClickable(true);
                // resend_tv.setTextColor(globalFunctions.getColor(R.color.blue));
            }
        };
        countDownTimer.start();
    }*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        closeThisActivity();
    }

    public static void closeThisActivity() {
        if (activity != null) {
            activity.finish();
        }
    }

    @Override
    protected void onDestroy() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        if (activity != null) activity = null;
        super.onDestroy();
    }

}