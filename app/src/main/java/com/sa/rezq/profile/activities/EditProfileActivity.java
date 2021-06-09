/*
package com.sa.rezq.profile.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import com.sa.rezq.R;
import com.sa.rezq.global.GlobalFunctions;
import com.sa.rezq.global.GlobalVariables;
import com.sa.rezq.services.ServerResponseInterface;
import com.sa.rezq.services.ServicesMethodsManager;
import com.sa.rezq.services.model.ProfileMainModel;
import com.sa.rezq.services.model.ProfileModel;
import com.sa.rezq.services.model.StatusResponseModel;
import com.sa.rezq.view.AlertDialog;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EditProfileActivity extends AppCompatActivity implements  {

    public static final String TAG = "EditProfileActivity";

    Context context = null;
    static Activity activity = null;

    private EditText public_name_etv, cr_number_etv,vat_number_etv,m_first_name_etv,m_last_name_etv, m_email_etv,shop_url_etv, head_office_address_etv,mobile_number_etv;
    private TextView continue_tv;
    private ImageView edit_profile_image_iv;
    public View mainView;

    Toolbar toolbar;
    ActionBar actionBar;
    String mTitle;
    int mResourceID, titleResourseID;
    TextView toolbar_title;
    ImageView toolbar_icon;
    Menu menu;

    GlobalVariables globalVariables;
    GlobalFunctions globalFunctions;
    Window window = null;

    ProfileModel profileModel = null;
    String imagePath = "";

    FirebaseStorage storage;
    StorageReference storageReference;

    List<String> profileImageList;
    List<Uri> uriProfileImageList;
    List<Bitmap> bitmapProfileImageList;
    List<String> downloadProfileImageList;

    private boolean
            isLocalProfileImageRemoved = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.edit_profile_activity);

        context = this;
        activity = this;
        window = getWindow();

        globalFunctions = new GlobalFunctions();
        globalVariables = new GlobalVariables();

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        //toolbar.setPadding(0, GlobalFunctions.getStatusBarHeight(context), 0, 0);
        //toolbar.setNavigationIcon(R.drawable.ic_back_draw);
        //toolbar.setContentInsetsAbsolute(0,0);
        toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbar_icon = (ImageView) toolbar.findViewById(R.id.tool_bar_back_icon);
        toolbar_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        setOptionsMenuVisiblity(false);

       */
/* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }*//*


        public_name_etv = findViewById(R.id.public_name_etv);
        cr_number_etv = (EditText) findViewById(R.id.cr_number_etv);
        vat_number_etv = (EditText) findViewById(R.id.vat_number_etv);
        m_first_name_etv = (EditText) findViewById(R.id.m_first_name_etv);
        m_last_name_etv = (EditText) findViewById(R.id.m_last_name_etv);
        m_email_etv = (EditText) findViewById(R.id.m_email_etv);
        shop_url_etv = (EditText) findViewById(R.id.shop_url_etv);
        mobile_number_etv = (EditText) findViewById(R.id.phone_number_etv);
        head_office_address_etv = (EditText) findViewById(R.id.head_office_address_etv);

        edit_profile_image_iv = (ImageView) findViewById(R.id.edit_profile_image_iv);
        continue_tv = (TextView) findViewById(R.id.continue_tv);

        downloadProfileImageList = new ArrayList<>();
        uriProfileImageList = new ArrayList<>();
        profileImageList = new ArrayList<>();
        bitmapProfileImageList = new ArrayList<>();

        profileImageList.clear();
        uriProfileImageList.clear();
        downloadProfileImageList.clear();
        bitmapProfileImageList.clear();

//        storage = FirebaseStorage.getInstance();
//        storageReference = storage.getReference();

        mainView = toolbar;

        getProfile();
        //setThisPage();

    }

    private void getProfile() {
        globalFunctions.showProgress(activity, getString(R.string.loading));
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.getProfile(context, new ServerResponseInterface() {
            @Override
            public void OnSuccessFromServer(Object arg0) {
                Log.d(TAG, "Response : " + arg0.toString());
                globalFunctions.hideProgress();
                if (arg0 instanceof ProfileMainModel) {
                    ProfileMainModel profileMainModel = (ProfileMainModel) arg0;
                    ProfileModel profileModel = profileMainModel.getProfileModel();
                    globalFunctions.setProfile(context, profileModel);
                    setThisPage();
                }
            }

            @Override
            public void OnFailureFromServer(String msg) {
                globalFunctions.hideProgress();
                globalFunctions.displayMessaage(context, mainView, msg);
                Log.d(TAG, "Failure : " + msg);
            }

            @Override
            public void OnError(String msg) {
                globalFunctions.hideProgress();
                globalFunctions.displayMessaage(context, mainView, msg);
                Log.d(TAG, "Error : " + msg);
            }
        }, "Get_Profile");
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new android.app.AlertDialog.Builder(activity)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
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
        CropImage.activity()
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .setAspectRatio(2, 2)
                .start(activity);
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
        }
    }

    private void setProfileImageToModel(Bitmap bitmap, Uri selectUri) {
        profileImageList.clear();
        bitmapProfileImageList.clear();
        uriProfileImageList.clear();
//        user_profile_iv.setImageBitmap(bitmap);
        profileImageList.add("image_one_iv");
        uriProfileImageList.add(selectUri);
        bitmapProfileImageList.add(bitmap);
    }

    private void confirmProfileImageDeleteAlertDialog() {
        final AlertDialog alertDialog = new AlertDialog(context);
        alertDialog.setCancelable(false);
        alertDialog.setIcon(R.drawable.rezq_logo);
        alertDialog.setTitle(getString(R.string.app_name));
       // alertDialog.setMessage(getResources().getString(R.string.confirm_delete));
        alertDialog.setPositiveButton(getString(R.string.yes), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileModel profileModel = globalFunctions.getProfile(activity);
               // profileModel.setProfileImage("");
                updateProfile(context, profileModel);
                alertDialog.dismiss();
//                user_profile_iv.setImageResource(R.drawable.ic_default_user);
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

    private void validateInput() {
        */
/*if (context != null) {

            String
                    fullName = first_name_etv.getText().toString().trim(),
                    lastName = last_name_etv.getText().toString().trim(),
                    emailId = email_etv.getText().toString().trim(),
                    companyName = company_name_etv.getText().toString().trim(),
                    mobile = mobile_number_etv.getText().toString();

            if (fullName.isEmpty()) {
                first_name_etv.setError(getString(R.string.pleaseFillMandatoryDetails));
                first_name_etv.setFocusableInTouchMode(true);
                first_name_etv.requestFocus();
            } else if (lastName.isEmpty()) {
                last_name_etv.setError(getString(R.string.pleaseFillMandatoryDetails));
                last_name_etv.setFocusableInTouchMode(true);
                last_name_etv.requestFocus();
            } else if (emailId.isEmpty()) {
                email_etv.setError(getString(R.string.pleaseFillMandatoryDetails));
                email_etv.setFocusableInTouchMode(true);
                email_etv.requestFocus();
            } else if (!globalFunctions.isEmailValid(emailId)) {
                globalFunctions.displayMessaage(activity, mainView, getString(R.string.emailNotValid));
                email_etv.setFocusableInTouchMode(true);
                email_etv.requestFocus();
            } else if (mobile.isEmpty()) {
                mobile_number_etv.setError(getString(R.string.pleaseFillMandatoryDetails));
                mobile_number_etv.setFocusableInTouchMode(true);
                mobile_number_etv.requestFocus();
            } else {
                if (profileModel == null) {
                    profileModel = new ProfileModel();
                }
                profileModel = globalFunctions.getProfile(activity);
                profileModel.setFirstName(fullName);
                profileModel.setLastName(lastName);
                profileModel.setEmailId(emailId);
                profileModel.setCompanyName(companyName);

                if (male_rb.isChecked()) {
                    profileModel.setGender("1");
                } else {
                    profileModel.setGender("2");
                }

                if (profileImageList.size() > 0) {
                    uploadImage(GlobalVariables.UPLOAD_PROFILE_PHOTO_PATH_CODE);
                } else {
                    updateProfile(context, profileModel);
                }

            }
        }*//*


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
                    updateProfile(context, profileModel);
                }
            } else {
                GlobalFunctions.hideProgress();
                updateProfile(context, profileModel);
            }
        }
    }

    private void updateProfile(final Context context, ProfileModel profileModel) {
        globalFunctions.showProgress(activity, getString(R.string.updatingProfile));
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.updateUser(context, profileModel, new ServerResponseInterface() {
            @Override
            public void OnSuccessFromServer(Object arg0) {
                globalFunctions.hideProgress();
                Log.d(TAG, "Response : " + arg0.toString());
                checkUpdateAfter(arg0);
            }

            @Override
            public void OnFailureFromServer(String msg) {
                globalFunctions.hideProgress();
                globalFunctions.displayMessaage(activity, mainView, msg);
                Log.d(TAG, "Failure : " + msg);
            }

            @Override
            public void OnError(String msg) {
                globalFunctions.hideProgress();
                globalFunctions.displayMessaage(activity, mainView, msg);
                Log.d(TAG, "Error : " + msg);
            }
        }, "Edit_User");
    }


    private void checkUpdateAfter(Object model) {
        if (model instanceof StatusResponseModel) {
            StatusResponseModel profileMainModel = (StatusResponseModel) model;
            globalFunctions.displayMessaage(activity, mainView, profileMainModel.getResponse());
            if (profileMainModel.isStatus()) {
                getProfile();
            }
        }
    }

    private void clearAllImageList() {
        profileImageList.clear();
        bitmapProfileImageList.clear();
        uriProfileImageList.clear();
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
                // closeThisActivity();
                ProfileActivity.closeThisActivity();
                //setThisPage();
                Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                activity.startActivity(intent);
                closeThisActivity();
            }
        });
        alertDialog.show();
    }

    private void setThisPage() {

        isLocalProfileImageRemoved = false;

        final ProfileModel myProfileModel = globalFunctions.getProfile(activity);
        if (myProfileModel != null) {

            if (globalFunctions.isNotNullValue(myProfileModel.getProfileImage())) {
//                Picasso.with(activity).load(myProfileModel.getProfileImage()).placeholder(R.drawable.app_icon).into(user_profile_iv);
            }

            if (globalFunctions.isNotNullValue(myProfileModel.getFirstName())) {
//                first_name_etv.setText(myProfileModel.getFirstName());
//                user_name_tv.setText(myProfileModel.getFirstName());
            }

            if (globalFunctions.isNotNullValue(myProfileModel.getLastName())) {
//                last_name_etv.setText(myProfileModel.getLastName());
//                user_name_tv.setText(myProfileModel.getFirstName() + " " + myProfileModel.getLastName());
            }

            if (globalFunctions.isNotNullValue(myProfileModel.getEmailId())) {
//                email_etv.setText(myProfileModel.getEmailId());
            }

            if (globalFunctions.isNotNullValue(myProfileModel.getMobileNumber())) {
                mobile_number_etv.setText(myProfileModel.getCountryCode() + "" + myProfileModel.getMobileNumber());
            }
            if (globalFunctions.isNotNullValue(myProfileModel.getCompanyName())) {
//                company_name_etv.setText(myProfileModel.getCompanyName());
            }


            edit_profile_image_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkPermission()) {
                        //write your main code to execute, It will execute if the permission is already given.
                        openCropFuctionalImage();
                    } else {
//                        requestPermission();
                    }
                }
            });

           */
/* user_profile_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkPermission()) {
                        //write your main code to execute, It will execute if the permission is already given.
                        openCropFuctionalImage();
                    } else {
//                        requestPermission();
                    }
                }
            });

            save_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    validateInput();
                }
            });*//*


        }
    }

    private void removeProfileImage() {
        profileImageList.clear();
        uriProfileImageList.clear();
        bitmapProfileImageList.clear();
//        user_profile_iv.setImageResource(R.drawable.ic_default_user);
        isLocalProfileImageRemoved = true;
    }

    private boolean isProfileImageAdded() {
        boolean result = false;
        ProfileModel profileModel = globalFunctions.getProfile(activity);
        if (profileModel != null && profileModel.getProfileImage().contains("https")) {
            result = true;
        } else if (profileImageList.size() > 0) {
            result = true;
        }
        return result;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        setTitle(getString(R.string.edit_profile), 0, 0);
        super.onResume();
    }

    public void setTitle(String title, int titleImageID, int backgroundResourceID) {
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

    private void restoreToolbar() {
        //toolbar = (Toolbar) findViewById(R.id.tool_bar);
        Log.d(TAG, "Restore Tool Bar");
        if (actionBar != null) {
            Log.d(TAG, "Restore Action Bar not Null");
            Log.d(TAG, "Title : " + mTitle);
            if (titleResourseID != 0) {
                toolbar_title.setVisibility(View.GONE);
            } else {
                toolbar_title.setVisibility(View.VISIBLE);
                toolbar_title.setText(mTitle);
            }

            if (mResourceID != 0) toolbar.setBackgroundResource(mResourceID);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_nothing, menu);
        this.menu = menu;
        setOptionsMenuVisiblity(false);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_nothing, menu);
        this.menu = menu;
        setOptionsMenuVisiblity(false);
        return true;
    }


    public void setOptionsMenuVisiblity(boolean showMenu) {
        if (menu == null)
            return;
        //menu.setGroupVisible(R.id.menu, showMenu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        }
        return false;
    }

    public static void closeThisActivity() {
        if (activity != null) {
            activity.finish();
        }
    }

    @Override
    public void onBackPressed() {
        closeThisActivity();
    }

    @Override
    public void OnSuccess(final String imgUrl, final String type, final String uploadingFile) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                globalFunctions.displayMessaage(activity, mainView, "Uploaded");
                // GlobalFunctions.hideProgress();
                if (uploadingFile.equalsIgnoreCase("image")) {
                    setUploadedImageToModel(type, imgUrl);
                }
            }
        });
    }

    private void setUploadedImageToModel(String type, String imagePath) {
        if (type != null) {
            if (type.equalsIgnoreCase(GlobalVariables.UPLOAD_PROFILE_PHOTO_PATH_CODE)) {
                profileModel.setProfileImage(imagePath);
                GlobalFunctions.hideProgress();
                updateProfile(context, profileModel);
            }
        }
    }

    @Override
    public void OnFailure() {
        GlobalFunctions.hideProgress();
        globalFunctions.displayErrorDialog(activity, context.getString(R.string.failed_upload_image));
    }

}
*/
