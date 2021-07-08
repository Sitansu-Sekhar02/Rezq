package com.sa.rezq.registration;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;

import com.facebook.FacebookSdk;
import com.google.android.material.textfield.TextInputLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.sa.rezq.Activity.AppController;
import com.sa.rezq.Activity.MainActivity;
import com.sa.rezq.R;
import com.sa.rezq.account.AccountActivity;
import com.sa.rezq.global.GlobalFunctions;
import com.sa.rezq.global.GlobalVariables;
import com.sa.rezq.image_picker.ImagePickerActivity;
import com.sa.rezq.login.LoginActivity;
import com.sa.rezq.services.ServerResponseInterface;
import com.sa.rezq.services.ServicesMethodsManager;
import com.sa.rezq.services.model.AddressModel;
import com.sa.rezq.services.model.CityModel;
import com.sa.rezq.services.model.ProfileMainModel;
import com.sa.rezq.services.model.ProfileModel;
import com.sa.rezq.services.model.RegisterModel;
import com.sa.rezq.services.model.StatusMainModel;
import com.sa.rezq.services.model.StatusModel;
import com.sa.rezq.services.model.StatusProfileModel;
import com.sa.rezq.services.model.StatusResponseModel;
import com.sa.rezq.services.model.SubProfileModel;
import com.sa.rezq.upload.UploadImage;
import com.sa.rezq.upload.UploadListener;
import com.sa.rezq.usersActivity.UserActivity;
import com.sa.rezq.view.AlertDialog;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class RegisterActivity extends AppCompatActivity implements UploadListener {

    public static final String TAG = "RegisterActivity";

    public static final String BUNDLE_REGISTER_ACTIVITY_REGISTER_MODEL = "BundleRegisterActivityRegisterModel";
    public static final String BUNDLE_CITY_MODEL = "BundleCityModel";

    private static final int PERMISSION_REQUEST_CODE = 200;
    public static final int REQUEST_IMAGE = 100;

    Context context;
    private static Activity activity;
    View mainView;

    String
            mobileNumber = "",
            countryCode = "";

    GlobalVariables globalVariables;
    GlobalFunctions globalFunctions;
    private LayoutInflater layoutInflater;

    private CircleImageView user_iv;
    private TextView register_tv, login_tv, gender_tv;
    private EditText first_name_etv, last_name_etv, email_etv, phone_number_etv, company_name_etv, password_etv, confirm_password_etv;
    private RadioGroup gender_rg;
    private ImageView edit_profile_image_iv;
    private  CircleImageView profile_iamge;

    private RadioButton male_rb, female_rb;
    Button register_btn;

    AddressModel addressModel = new AddressModel();

    RegisterModel myRegisterModel = null;
    RegisterModel registerModel = null;
    CityModel cityModel = null;

    //String selectedGender = GlobalVariables.GENDER_MALE;

    List<String> profileImageList;
    List<Uri> uriProfileImageList;
    List<String> downloadProfileImageList;
    String imagePath = "";


    public static Intent newInstance(Context context, RegisterModel model) {
        Intent intent = new Intent(context, RegisterActivity.class);
        Bundle args = new Bundle();
        args.putSerializable(BUNDLE_REGISTER_ACTIVITY_REGISTER_MODEL, model);
        intent.putExtras(args);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.register_activity);

        context = this;
        activity = this;

        this.layoutInflater = activity.getLayoutInflater();

        globalFunctions = AppController.getInstance().getGlobalFunctions();
        globalVariables = AppController.getInstance().getGlobalVariables();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.black_trans));
        }


        register_btn =findViewById(R.id.tvRegisterButton);

        first_name_etv = (EditText) findViewById(R.id.etFirstName);
        last_name_etv = (EditText) findViewById(R.id.etLastName);
        email_etv = (EditText) findViewById(R.id.etEmail);
        phone_number_etv = (EditText) findViewById(R.id.etContact);
        edit_profile_image_iv = (ImageView) findViewById(R.id.edit_profile_image_iv);
        profile_iamge = (CircleImageView) findViewById(R.id.ivProfimeImage);


        TextInputLayout user_firstname_TextObj = (TextInputLayout)    findViewById(R.id.outlined_text_input_layout);
        TextInputLayout user_lastnameTextObj = (TextInputLayout)    findViewById(R.id.ti_last_name_input);
        TextInputLayout user_contactTextObj = (TextInputLayout)    findViewById(R.id.ti_contact_input);
        TextInputLayout user_emailTextObj = (TextInputLayout)    findViewById(R.id.ti_email_input);
        Typeface font_yekan  = Typeface.createFromAsset(getAssets(), "regular.otf");
        user_firstname_TextObj .setTypeface(font_yekan);
        user_lastnameTextObj .setTypeface(font_yekan);
        user_contactTextObj .setTypeface(font_yekan);
        user_emailTextObj .setTypeface(font_yekan);


        mainView = first_name_etv;

        if (getIntent().hasExtra(BUNDLE_REGISTER_ACTIVITY_REGISTER_MODEL)) {
            myRegisterModel = (RegisterModel) getIntent().getSerializableExtra(BUNDLE_REGISTER_ACTIVITY_REGISTER_MODEL);
        }

        if (myRegisterModel != null) {
            if (myRegisterModel.getCountryCode() != null && myRegisterModel.getMobileNumber() != null) {
                countryCode = myRegisterModel.getCountryCode();
                //country_code_picker.setCountryForPhoneCode(Integer.parseInt(myRegisterModel.getCountryCode()));
                mobileNumber = myRegisterModel.getMobileNumber();
                phone_number_etv.setText(countryCode +" "+ myRegisterModel.getMobileNumber());
            }
        }

        downloadProfileImageList = new ArrayList<>();
        uriProfileImageList = new ArrayList<>();
        profileImageList = new ArrayList<>();

        profileImageList.clear();
        uriProfileImageList.clear();
        downloadProfileImageList.clear();



        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  startActivity(new Intent(activity, UserActivity.class));
             validateInput();
            }
        });
        edit_profile_image_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    openCropFuctionalImage();
            }
        });

      /*  gender_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                openGenderPopupMenu();
            }
        });*/

    }


    private void validateInput() {
        if (first_name_etv != null && last_name_etv != null && phone_number_etv != null && email_etv != null ) {
            String
                    firstName = first_name_etv.getText().toString().trim(),
                    lastName = last_name_etv.getText().toString().trim(),
                    mobile = phone_number_etv.getText().toString().trim(),
                    email = email_etv.getText().toString().trim();


            if (firstName.isEmpty()) {
                first_name_etv.setError(getString(R.string.pleaseFillMandatoryDetails));
                first_name_etv.setFocusableInTouchMode(true);
                first_name_etv.requestFocus();
            } else if (lastName.isEmpty()) {
                last_name_etv.setError(getString(R.string.pleaseFillMandatoryDetails));
                last_name_etv.setFocusableInTouchMode(true);
                last_name_etv.requestFocus();
            }/*else if (mobile.isEmpty()) {
                phone_number_etv.setError(getString(R.string.pleaseFillMandatoryDetails));
                phone_number_etv.setFocusableInTouchMode(true);
                phone_number_etv.requestFocus();
            }else if (!globalFunctions.isPhoneNumberValid(mobile)) {
                globalFunctions.displayMessaage(activity, mainView, getString(R.string.phoneNONotValid));
                phone_number_etv.setFocusableInTouchMode(true);
                phone_number_etv.requestFocus();

            }*/else if (email.isEmpty()) {
                email_etv.setError(getString(R.string.pleaseFillMandatoryDetails));
                email_etv.setFocusableInTouchMode(true);
                email_etv.requestFocus();
            } else if (!globalFunctions.isEmailValid(email)) {
                globalFunctions.displayMessaage(activity, mainView, getString(R.string.emailNotValid));
                email_etv.setFocusableInTouchMode(true);
                email_etv.requestFocus();

            } else {
                if (registerModel == null) {
                    registerModel = new RegisterModel();
                }
                registerModel.setFirstName(firstName);
                registerModel.setLastName(lastName);
                registerModel.setMobileNumber(mobileNumber);
                registerModel.setEmailId(email);
                registerModel.setCountryCode(countryCode);

                if (profileImageList.size()>0){
                    uploadImage(GlobalVariables.UPLOAD_PROFILE_PHOTO_PATH_CODE);

                }else {
                    registerUser(context, registerModel);
                }

            }
        }
    }

    private void registerUser(final Context context, final RegisterModel model) {
        globalFunctions.showProgress(activity, getString(R.string.loading));
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.registerUser(context, model, new ServerResponseInterface() {
            @Override
            public void OnSuccessFromServer(Object arg0) {
                globalFunctions.hideProgress();
                Log.d(TAG, "Response : " + arg0.toString());
                //StatusModel model = (StatusModel) arg0;
                validateOutputAfterRegistration(arg0);
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
        }, "Register_User");
    }

    private void validateOutputAfterRegistration(Object model) {
        if (model instanceof StatusProfileModel) {
            StatusProfileModel statusProfileModel = (StatusProfileModel) model;
            SubProfileModel subProfileModel = statusProfileModel.getSubProfileModel();
            if (statusProfileModel.isStatus()) {
                GlobalFunctions.setSharedPreferenceString(context, GlobalVariables.SHARED_PREFERENCE_TOKEN, subProfileModel.getToken());
//                showAlertMessage(subProfileModel.getMessage());
                getProfile();
            } else {
                globalFunctions.displayMessaage(activity, mainView, subProfileModel.getMessage());

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
                closeThisActivity();
                Intent intent = new Intent(activity, LoginActivity.class);
                startActivity(intent);
            }
        });

        alertDialog.show();
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
                    ProfileMainModel profileMainModel = (ProfileMainModel) arg0;
                    ProfileModel profileModel = profileMainModel.getProfileModel();
                    globalFunctions.setProfile(context, profileModel);
                    goToMainActivity();
                }
            }

            @Override
            public void OnFailureFromServer(String msg) {
                // globalFunctions.hideProgress();
                globalFunctions.displayMessaage(context, mainView, msg);
                Log.d(TAG, "Failure : " + msg);
                goToMainActivity();
            }

            @Override
            public void OnError(String msg) {
                // globalFunctions.hideProgress();
                globalFunctions.displayMessaage(context, mainView, msg);
                Log.d(TAG, "Error : " + msg);
                goToMainActivity();
            }
        }, "Get_Profile");
    }

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        return true;
    }

    private void openCropFuctionalImage() {
     /*   CropImage.activity()
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .setAspectRatio(2, 2)
                .start(activity);*/

        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            showImagePickerOptions();
                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }


    private void showImagePickerOptions() {
        ImagePickerActivity.showImagePickerOptions(this, new ImagePickerActivity.PickerOptionListener() {
            @Override
            public void onTakeCameraSelected() {
                launchCameraIntent();
            }

            @Override
            public void onChooseGallerySelected() {
                launchGalleryIntent();
            }
        });
    }

    private void showSettingsDialog() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(activity);
        builder.setTitle(getString(R.string.dialog_permission_title));
        builder.setMessage(getString(R.string.dialog_permission_message));
        builder.setPositiveButton(getString(R.string.go_to_settings), (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton(getString(android.R.string.cancel), (dialog, which) -> dialog.cancel());
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    private void launchCameraIntent() {
        Intent intent = new Intent(activity, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);

        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void launchGalleryIntent() {
        Intent intent = new Intent(activity, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = null;
        Uri selectUri = null;
        GlobalFunctions.hideProgress();
        if (resultCode == RESULT_OK && requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            try {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                Uri selectedImageUri = result.getUri();//data.getData();
                String selectedImagePath = globalFunctions.getRealPathFromURI(context, selectedImageUri);
                Log.d(TAG, "Path = " + selectedImagePath);
                File imagePath = new File(selectedImagePath);
                if (imagePath.exists()) {
                    Bitmap bmp = result.getBitmap();
                    if (bmp == null) {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inJustDecodeBounds = true;
                        BitmapFactory.decodeFile(selectedImagePath, options);
                        final int REQUIRED_SIZE = 200;
                        int scale = 1;
                        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                            scale *= 2;
                        options.inSampleSize = scale;
                        options.inJustDecodeBounds = false;
                        bmp = BitmapFactory.decodeFile(selectedImagePath, options);
                        bitmap = bmp;
                    }
                }
                selectUri = selectedImageUri;
                this.imagePath = selectedImagePath;
                setProfileImageToModel(bitmap, selectUri);

            } catch (Exception exccc) {
                globalFunctions.displayMessaage(context, mainView, getString(R.string.something_went_wrong_message));
            }
        }else if (resultCode == RESULT_OK && requestCode == REQUEST_IMAGE) {
            Uri uri = data.getParcelableExtra("path");
            try {
                // You can update this bitmap to your server
                 bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                selectUri = uri;
                setProfileImageToModel(bitmap,selectUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setProfileImageToModel(Bitmap bitmap, Uri selectUri) {
        profileImageList.clear();
        uriProfileImageList.clear();
        profile_iamge.setImageBitmap(bitmap);
        profileImageList.add("image_one_iv");
        uriProfileImageList.add(selectUri);
    }
    private void uploadImage(String type) {
        if (type.equalsIgnoreCase(GlobalVariables.UPLOAD_PROFILE_PHOTO_PATH_CODE)) {
            if (uriProfileImageList != null) {
                if (uriProfileImageList.size() != 0) {
                    globalFunctions.showProgress(activity, context.getString(R.string.uploading_image));
                    for (int j = 0; j < uriProfileImageList.size(); j++) {
                        Uri uri = (uriProfileImageList.get(j));
                        UploadImage uploadImage = new UploadImage(context);
                        uploadImage.startUploading(uri, type, "image", this);
                    }
                } else {
                    GlobalFunctions.hideProgress();
                   registerUser(activity,registerModel);
                }
            } else {
                GlobalFunctions.hideProgress();
                registerUser(activity,registerModel);
            }
        }
    }



    private void goToMainActivity() {
        Intent intent = new Intent(activity, AccountActivity.class);
        activity.startActivity(intent);
        closeThisActivity();
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

    @Override
    public void OnSuccess(String fileName, String type, String uploadingFile) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                globalFunctions.displayMessaage(activity, mainView, "Uploaded");
                // GlobalFunctions.hideProgress();
                if (uploadingFile.equalsIgnoreCase("image")) {
                    setUploadedImageToModel(type, fileName);
                }
            }
        });
    }
    private void setUploadedImageToModel(String type, String imagePath) {
        if (type != null) {
            if (type.equalsIgnoreCase(GlobalVariables.UPLOAD_PROFILE_PHOTO_PATH_CODE)) {
                if (registerModel == null) {
                    registerModel = new RegisterModel();
                }
                registerModel.setProfileImage(imagePath);
                GlobalFunctions.hideProgress();
                registerUser(activity,registerModel);
            }
        }
    }


    @Override
    public void OnFailure() {
        GlobalFunctions.hideProgress();
        globalFunctions.displayErrorDialog(activity, context.getString(R.string.failed_upload_image));
    }
}