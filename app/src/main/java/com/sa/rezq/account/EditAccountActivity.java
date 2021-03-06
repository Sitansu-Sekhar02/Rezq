package com.sa.rezq.account;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
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

import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.sa.rezq.Activity.AppController;
import com.sa.rezq.Activity.MainActivity;
import com.sa.rezq.R;
import com.sa.rezq.global.GlobalFunctions;
import com.sa.rezq.global.GlobalVariables;
import com.sa.rezq.image_picker.ImagePickerActivity;
import com.sa.rezq.services.ServerResponseInterface;
import com.sa.rezq.services.ServicesMethodsManager;
import com.sa.rezq.services.model.ProfileMainModel;
import com.sa.rezq.services.model.ProfileModel;
import com.sa.rezq.upload.UploadImage;
import com.sa.rezq.upload.UploadListener;
import com.sa.rezq.view.AlertDialog;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class EditAccountActivity extends AppCompatActivity implements UploadListener {
    public static final String TAG ="EditAccountActivity" ;
    public static final String BUNDLE_EDIT_MODEL= "BundleEditProfile";



    private static final int PERMISSION_REQUEST_CODE = 200;
    public static final int REQUEST_IMAGE = 100;

    Context context;
    static Window mainWindow = null;

    GlobalFunctions globalFunctions;
    GlobalVariables globalVariables;
    View mainView;
    TextView name_tv, therapist_tv,tv_edit;
    EditText first_name_etv, last_name_etv, mobile_etv, email_etv, confirm_password_etv;
    ImageView profile_image;
    Button update_profile;
    private CountryCodePicker country_code_picker;

    private static Activity activity;
    String selected_country_code = "";


    List<String> profileImageList;
    List<Uri> uriProfileImageList;
    List<String> downloadProfileImageList;
    String imagePath = "";

    ProfileModel profileModel = null;

    static Toolbar toolbar;
    static ActionBar actionBar;
    static String mTitle;
    static int mResourceID;
    static int titleResourseID;
    public Menu menu;
    static TextView toolbar_title;
    static ImageView toolbar_logo, tool_bar_back_icon;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_activity);

        context = this;
        activity = this;
        mainWindow = getWindow();


        globalFunctions = AppController.getInstance().getGlobalFunctions();
        globalVariables = AppController.getInstance().getGlobalVariables();

        // name_tv = ( TextView ) view.findViewById( R.id.name_tv );
       // country_code_picker = (CountryCodePicker) findViewById(R.id.country_code_picker);

        first_name_etv = findViewById( R.id.et_firstname );
        last_name_etv =  findViewById( R.id.et_lastname );
      //  mobile_etv =  findViewById( R.id.et_mobile_no );
      //  email_etv = findViewById( R.id.et_email_id );
        tv_edit = findViewById( R.id.tv_edit );
        update_profile =findViewById( R.id.continue_btn );
        profile_image = ( ImageView ) findViewById( R.id.ivProfimeImage );

        TextInputLayout user_firstname_TextObj = (TextInputLayout)    findViewById(R.id.ti_first_name);
        TextInputLayout user_last_TextObj = (TextInputLayout)    findViewById(R.id.ti_last_name);
        Typeface font_yekan  = Typeface.createFromAsset(getAssets(), "regular.otf");
        user_firstname_TextObj .setTypeface(font_yekan);
        user_last_TextObj .setTypeface(font_yekan);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.black_trans));
        }

       /* country_code_picker.setCountryForPhoneCode(+91);
        country_code_picker.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                selected_country_code = country_code_picker.getSelectedCountryCodeWithPlus();
                mobile_etv.setText("");
            }
        });*/

        /*country_code_picker.registerCarrierNumberEditText(mobile_etv);

        country_code_picker.setPhoneNumberValidityChangeListener(new CountryCodePicker.PhoneNumberValidityChangeListener() {
            @Override
            public void onValidityChanged(boolean isValidNumber) {
            }
        });

        selected_country_code = country_code_picker.getSelectedCountryCodeWithPlus();*/

        if (getIntent().hasExtra(BUNDLE_EDIT_MODEL)) {
            profileModel = (ProfileModel) getIntent().getSerializableExtra(BUNDLE_EDIT_MODEL);
        } else {
            profileModel = null;
        }


        update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateProfile();
                // updateProfile(context, profileModel);
            }
        });

        tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCropFuctionalImage();

            }
        });

        downloadProfileImageList = new ArrayList<>();
        uriProfileImageList = new ArrayList<>();
        profileImageList = new ArrayList<>();

        profileImageList.clear();
        uriProfileImageList.clear();
        downloadProfileImageList.clear();



        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        //toolbar.setPadding(0, GlobalFunctions.getStatusBarHeight(mainContext), 0, 0);
        toolbar.setContentInsetsAbsolute(0, 0);
        // toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        // toolbar_logo = (ImageView) toolbar.findViewById(R.id.tool_bar_logo);
        tool_bar_back_icon = (ImageView) toolbar.findViewById(R.id.tool_bar_back_icon);

        tool_bar_back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mainView = toolbar;

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        setTitle(getString(R.string.edit_profile), 0, 0);

        //editProfile();
        ProfileModel profileModel=GlobalFunctions.getProfile(activity);
        if (profileModel!=null) {
            setThisPage(profileModel);
        }


    }

    private void setThisPage(ProfileModel profileModel) {

        if (profileModel != null) {
            if (GlobalFunctions.isNotNullValue(profileModel.getProfileImg())) {
                Picasso.with(context).load(profileModel.getProfileImg()).placeholder(R.drawable.layout_bg).into(profile_image);

            }
            if (GlobalFunctions.isNotNullValue(profileModel.getFirstName())) {
                first_name_etv.setText(profileModel.getFirstName());
            }
            if (GlobalFunctions.isNotNullValue(profileModel.getLastName())) {
                last_name_etv.setText(profileModel.getLastName());
            }
           /* if (GlobalFunctions.isNotNullValue(profileModel.getEmail())) {
                email_etv.setText(profileModel.getEmail());
            }
            if (GlobalFunctions.isNotNullValue(profileModel.getPhone())) {
                mobile_etv.setText(profileModel.getPhone());
            }*/
        }
    }

    private void validateProfile() {
        if (first_name_etv != null || last_name_etv != null ) {
            String
                    firstname = first_name_etv.getText().toString().trim(),
                    lastname = last_name_etv.getText().toString().trim();
                  //  mobileNo = mobile_etv.getText().toString().trim(),
                 //   email = email_etv.getText().toString().trim();

            if (firstname.isEmpty()) {
                first_name_etv.setError( getString( R.string.enter_mendatory_field) );
                first_name_etv.setFocusableInTouchMode( true );
                first_name_etv.requestFocus();
            } else if (lastname.isEmpty()) {
                last_name_etv.setError( getString( R.string.enter_mendatory_field ) );
                last_name_etv.setFocusableInTouchMode( true );
                last_name_etv.requestFocus();
            } /*else if (email.isEmpty()) {
                email_etv.setError(getString(R.string.pleaseFillMandatoryDetails));
                email_etv.setFocusableInTouchMode(true);
                email_etv.requestFocus();
            } else if (!globalFunctions.isEmailValid(email)) {
                globalFunctions.displayMessaage(activity, mainView, getString(R.string.emailNotValid));
                email_etv.setFocusableInTouchMode(true);
                email_etv.requestFocus();
            }else if (!globalFunctions.isPhoneNumberValid( mobileNo )) {
                mobile_etv.setError( getString( R.string.mobileNoNotValid ) );
                mobile_etv.setFocusableInTouchMode( true);
                mobile_etv.requestFocus();
                // GlobalFunctions.displayMessaage(context, mainView, getString(R.string.mobileNumberNotValid));
            } else if (selected_country_code.isEmpty()) {
                globalFunctions.displayMessaage( activity, mainView, getString( R.string.countryCodeNONotValid ) );
            }else if (!country_code_picker.isValidFullNumber()) {
                globalFunctions.displayMessaage(activity, mainView, getString(R.string.please_enetr_valid_number));
                mobile_etv.setSelection(mobile_etv.getText().length());
                mobile_etv.setFocusableInTouchMode(true);
                mobile_etv.requestFocus();
            }*/ else {

                if (profileModel == null) {
                    profileModel = new ProfileModel();
                }
                profileModel=GlobalFunctions.getProfile(activity);

                profileModel.setFirstName( firstname );
                profileModel.setLastName( lastname );
               // profileModel.setEmail( email );
              //  profileModel.setPhone( mobileNo );
              //  profileModel.setCountry_code( selected_country_code );
                if (profileImageList.size()>0){
                    uploadImage(GlobalVariables.UPLOAD_PROFILE_PHOTO_PATH_CODE);

                }else {
                    updateProfile(context, profileModel);
                }

            }
        }
    }

    private void updateProfile(Context context, ProfileModel profileModel) {

        GlobalFunctions.showProgress(context, getString( R.string.updating_profile ) );
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.updateSubscriberInfo(context,profileModel, new ServerResponseInterface() {
            @Override
            public void OnSuccessFromServer(Object arg0) {
                GlobalFunctions.hideProgress();
                Log.d( TAG, "Response : " + arg0.toString() );
                outputAfterUpdate( arg0);
            }

            @Override
            public void OnFailureFromServer(String msg) {
                GlobalFunctions.hideProgress();
                GlobalFunctions.displayMessaage(EditAccountActivity.this.context, mainView, msg );
                Log.d( TAG, "Failure : " + msg );
            }

            @Override
            public void OnError(String msg) {
                GlobalFunctions.hideProgress();
                GlobalFunctions.displayMessaage(EditAccountActivity.this.context, mainView, msg );
                Log.d( TAG, "Error : " + msg );
            }
        }, "Get Profile" );
    }

    private void outputAfterUpdate(Object arg0) {
        if (arg0 instanceof ProfileMainModel) {
            ProfileMainModel profileMainModel = (ProfileMainModel) arg0;
            ProfileModel profileModel = profileMainModel.getProfileModel();
            GlobalFunctions.setProfile(activity,profileModel);
            if (profileMainModel.isStatus()) {
                showAlertDialog(profileMainModel);
            }else {
                ///
            }
//            setThisPage(profileModel);

        }
    }

    private void showAlertDialog(ProfileMainModel profileMainModel) {
        final AlertDialog alertDialog = new AlertDialog(context);
        alertDialog.setCancelable(false);
        alertDialog.setIcon(R.drawable.rezq_logo);
        alertDialog.setTitle(getString(R.string.app_name));
        alertDialog.setMessage(activity.getString(R.string.profile_updated_success));
        alertDialog.setPositiveButton(getString(R.string.ok), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                closeThisActivity();
               /* if (profileMainModel.isStatus()){
                    setThisPage(profileMainModel.getProfileModel());
                }*/
            }
        });

        alertDialog.show();

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
    private void goToMainActivity() {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        closeThisActivity();
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
        profile_image.setImageBitmap(bitmap);
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
                    updateProfile(activity,profileModel);
                }
            } else {
                GlobalFunctions.hideProgress();
                updateProfile(activity,profileModel);
            }
        }
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
                if (profileModel == null) {
                    profileModel = new ProfileModel();
                }
                profileModel.setProfileImg(imagePath);
                GlobalFunctions.hideProgress();
                updateProfile(activity,profileModel);
            }
        }
    }

    @Override
    public void OnFailure() {
        GlobalFunctions.hideProgress();
        globalFunctions.displayErrorDialog(activity, context.getString(R.string.failed_upload_image));
    }
}
