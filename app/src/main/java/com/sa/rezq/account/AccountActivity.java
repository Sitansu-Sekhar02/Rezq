package com.sa.rezq.account;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.provider.Settings;
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
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.hbb20.CountryCodePicker;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.sa.rezq.Activity.AppController;
import com.sa.rezq.Activity.MainActivity;
import com.sa.rezq.R;
import com.sa.rezq.adapter.AccountListAdapter;
import com.sa.rezq.adapter.AllCategoryListAdapter;
import com.sa.rezq.adapter.VendorListAdapter;
import com.sa.rezq.adapter.interfaces.OpenInsertAccountInvoke;
import com.sa.rezq.global.GlobalFunctions;
import com.sa.rezq.global.GlobalVariables;
import com.sa.rezq.image_picker.ImagePickerActivity;
import com.sa.rezq.services.ServerResponseInterface;
import com.sa.rezq.services.ServicesMethodsManager;
import com.sa.rezq.services.model.AccountListModel;
import com.sa.rezq.services.model.AccountMainModel;
import com.sa.rezq.services.model.AccountModel;
import com.sa.rezq.services.model.InsertAccountModel;
import com.sa.rezq.services.model.LoginModel;
import com.sa.rezq.services.model.RegisterModel;
import com.sa.rezq.services.model.SeeAllCategoryListMainModel;
import com.sa.rezq.services.model.SeeAllCategoryListModel;
import com.sa.rezq.services.model.SeeAllCategoryModel;
import com.sa.rezq.services.model.StatusMainModel;
import com.sa.rezq.services.model.StatusModel;
import com.sa.rezq.upload.UploadImage;
import com.sa.rezq.upload.UploadListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.vlonjatg.progressactivity.ProgressLinearLayout;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.sa.rezq.profile.ProfileMainActivity.context;

public class AccountActivity  extends AppCompatActivity implements OpenInsertAccountInvoke, UploadListener {
    public static final String TAG = "AccountActivity";


    private static final int PERMISSION_REQUEST_CODE = 200;
    public static final int REQUEST_IMAGE = 100;


    Context context;
    private static Activity activity;
    View mainView;
    CountDownTimer countDownTimer;
    public CircleImageView iv_profile_image;

    List<String> profileImageList;
    List<Uri> uriProfileImageList;
    List<String> downloadProfileImageList;
    String imagePath = "";

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
        setContentView(R.layout.activity_add_account);

        context = this;
        activity = this;

        globalFunctions = AppController.getInstance().getGlobalFunctions();
        globalVariables = AppController.getInstance().getGlobalVariables();

        linearLayoutManager = new LinearLayoutManager(activity);
        gridLayoutManager=new GridLayoutManager(activity,3);
        progressActivity = findViewById(R.id.account_progressActivity);
        accountListRecyclerview = findViewById(R.id.recyclerview_account_list);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.black_trans));
        }

        downloadProfileImageList = new ArrayList<>();
        uriProfileImageList = new ArrayList<>();
        profileImageList = new ArrayList<>();

        profileImageList.clear();
        uriProfileImageList.clear();
        downloadProfileImageList.clear();


        mainView = accountListRecyclerview;

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
            setStaticAccount();
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

    public void onBackPressed () {

       // closeThisActivity();
        super.onBackPressed();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        closeThisActivity();
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
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        if (activity != null) activity = null;
        super.onDestroy();
    }


    @Override
    public void OnItemClickListener(AccountModel position) {
        openAccountPopup(position);
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
        final ImageView edit_profile_image_iv = dialog.findViewById(R.id.edit_profile_image_iv);
        iv_profile_image = dialog.findViewById(R.id.profile_image);
        final Button  btn_Continue = dialog.findViewById(R.id.btn_Continue);

        edit_profile_image_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCropFuctionalImage();

            }
        });

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
                        dialog.dismiss();


                        // insertNewUser(activity, insertAccountModel);

                            if (profileImageList.size() > 0) {
                                uploadImage(GlobalVariables.UPLOAD_PROFILE_PHOTO_PATH_CODE);

                            } else {
                                insertNewUser(activity, insertAccountModel);
                            }

                    }
                }
            }
        });
    }

    private void insertNewUser(Activity activity, InsertAccountModel insertAccountModel) {
         globalFunctions.showProgress(activity, activity.getString(R.string.loading));
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

    private void validateOutputAfterInsertUser(Object arg0) {
        if (arg0 instanceof StatusMainModel) {
            StatusMainModel statusMainModel = (StatusMainModel) arg0;
            StatusModel statusModel = statusMainModel.getStatusModel();
            if (!statusMainModel.isStatusLogin()) {
                globalFunctions.displayMessaage(activity, mainView, statusModel.getMessage());

            } else {
                GlobalFunctions.setSharedPreferenceString(context, GlobalVariables.SHARED_PREFERENCE_ACCOUNT_ID, statusModel.getExtra());
                goToMainActivity();

            }
        }
    }

    private void goToMainActivity() {
        Intent intent = new Intent(activity, MainActivity.class);
        startActivity(intent);
        closeThisActivity();
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
                    insertNewUser(activity,insertAccountModel);
                }
            } else {
                GlobalFunctions.hideProgress();
                insertNewUser(activity,insertAccountModel);
            }
        }
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
        iv_profile_image.setImageBitmap(bitmap);
        profileImageList.add("image_one_iv");
        uriProfileImageList.add(selectUri);
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
                if (insertAccountModel == null) {
                    insertAccountModel = new InsertAccountModel();
                }
                insertAccountModel.setProfile_image(imagePath);
                GlobalFunctions.hideProgress();
                insertNewUser(activity,insertAccountModel);
            }
        }
    }


    @Override
    public void OnFailure() {
        GlobalFunctions.hideProgress();
        globalFunctions.displayErrorDialog(activity, context.getString(R.string.failed_upload_image));
    }
}
