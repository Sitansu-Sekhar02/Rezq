package com.sa.rezq.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easywaylocation.EasyWayLocation;
import com.example.easywaylocation.GetLocationDetail;
import com.example.easywaylocation.Listener;
import com.example.easywaylocation.LocationData;
import com.github.siyamed.shapeimageview.ShapeImageView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.sa.rezq.account.AccountActivity;
import com.sa.rezq.account.SwitchAccountActivity;
import com.sa.rezq.coupons.RecentCouponActivity;
import com.sa.rezq.home.HomeFragment;
import com.sa.rezq.R;
import com.sa.rezq.fcm.analytics.AnalyticsReport;
import com.sa.rezq.global.GlobalFunctions;
import com.sa.rezq.global.GlobalVariables;
import com.sa.rezq.membership.FreeMembershipActivity;
import com.sa.rezq.membership.MembershipDetailsActivity;
import com.sa.rezq.membership.UpgradeMembershipListActivity;
import com.sa.rezq.profile.ProfileMainActivity;
import com.sa.rezq.services.ServerResponseInterface;
import com.sa.rezq.services.ServicesMethodsManager;
import com.sa.rezq.services.model.BannerListModel;
import com.sa.rezq.services.model.CountryModel;
import com.sa.rezq.services.model.HomePageMainModel;
import com.sa.rezq.services.model.HomePageModel;
import com.sa.rezq.services.model.KeyValueModel;
import com.sa.rezq.services.model.MembershipDetailsModel;
import com.sa.rezq.services.model.MembershipModel;
import com.sa.rezq.services.model.NotificationModel;
import com.sa.rezq.services.model.ProfileMainModel;
import com.sa.rezq.services.model.ProfileMembershipModel;
import com.sa.rezq.services.model.ProfileModel;
import com.sa.rezq.services.model.PushNotificationModel;
import com.sa.rezq.services.model.StatusModel;
import com.sa.rezq.services.model.UpdateLanguageModel;
import com.sa.rezq.vendorlist.details.VendorListDetailsActivity;
import com.sa.rezq.view.AlertDialog;
import com.sa.rezq.wishlist.WishListActivity;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.easywaylocation.EasyWayLocation.LOCATION_SETTING_REQUEST_CODE;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, LocationListener,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener , Listener, LocationData.AddressCallBack {


    EasyWayLocation easyWayLocation;
    GetLocationDetail getLocationDetail;


    public static ImageView iv_menu;

    DrawerLayout drawer;

    public static LinearLayout llmain;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    Double latitute, longitute;

    public static TextView tvHeaderText;

    public  static SearchView searchView;
    public  static TextView tvLocation,ivHome;
    public  static CircleImageView crop_profile;
    public  static SearchView EtsearchRecent;



    public static NavigationView navigationView;


    //Langauge Textview
    static  TextView arabic_language_tv, english_language_tv;

    static View arabic_language_iv, english_language_iv;



    public static final String BUNDLE_DEEPLINK_URL = "BundleDeepLinkURL";
    public static final String BUNDLE_MAIN_NOTIFICATION_MODEL = "BundleMainModelNotificationModel";
    public static Context mainContext = null;
    static Activity activity = null;
    public static RelativeLayout cart_notification_layout, notification_layout;

    public static TextView tvCount;
    LinearLayout nav_header;
    public static TextView iv_Notification;
    TextView Logout;
    private LayoutInflater layoutInflater;
    public static String TAG = "MainActivity";
    public static LocationServices locServices = null;
    static Toolbar toolbar;
    static ActionBar actionBar;

    static String mTitle;
    static int mResourceID;
    static int titleResourseID;
    static GlobalFunctions globalFunctions;

    static Intent locationintent;
    public Menu menu;
    FragmentManager mainActivityFM = null;
    Window mainWindow = null;
    KeyValueModel keyValueModel;
    View mainView;
    GlobalVariables globalVariables;
    View navigationHeaderView;

    int gravity = 0;
    private NotificationModel notificationModel = null;

    ValueAnimator mVaActionBar;
    AnalyticsReport analyticsReport;

    CountryModel countryModel;
    private List<CountryModel> countryList = new ArrayList();
    private List <String> countryStringList = new ArrayList();
    private String[] countryArr;

    ProfileMembershipModel profileMembershipModel=null;

    public static Intent newInstance(Context context, String url) {
        Intent intent = new Intent( context, MainActivity.class );
        intent.putExtra( BUNDLE_DEEPLINK_URL, url );
        return intent;
    }

    public static Intent newInstance(Context context, NotificationModel notificationModel) {
        Intent intent = new Intent( context, MainActivity.class );
        intent.putExtra( BUNDLE_MAIN_NOTIFICATION_MODEL, notificationModel );
        return intent;
    }

    public static void setTitle(String title, int titleImageID, int backgroundResourceID) {
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
        //restoreToolbar();
    }


    public static void setMyTitle(String title, int titleImageID, int backgroundResourceID) {
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
        //restoreToolbar();
    }


    public static void closeThisActivity() {
        if (activity != null) {
            activity.finish();
        }
    }
    public static void startLocationService() {
        activity.startService( locationintent );
    }

    public static void stopLocationService() {

        activity.stopService( locationintent );
    }


    public static void setNotification(boolean isNotify) {
        if (notification_layout != null) {
            /*final ImageView tv = (ImageView) notification_layout.findViewById(R.id.actionbar_badge_notification_textview);
            if(!isNotify){
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv.setVisibility(View.GONE);
                    }
                });

            }else{
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv.setVisibility(View.VISIBLE);
                    }
                });
            }*/
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        activity = this;
        mainContext = this;
        mainWindow = getWindow();



        mainActivityFM = getSupportFragmentManager();
        layoutInflater = activity.getLayoutInflater();
        globalFunctions = AppController.getInstance().getGlobalFunctions();
        globalVariables = AppController.getInstance().getGlobalVariables();
        analyticsReport = new AnalyticsReport();
        //initialize findviewbyid

        arabic_language_tv=findViewById(R.id.arabic_language_tv);
        english_language_tv=findViewById(R.id.english_language_tv);
        arabic_language_iv=findViewById(R.id.arabic_language_iv);
        english_language_iv=findViewById(R.id.english_language_iv);
        Intialize();

        toolbar = ( Toolbar ) findViewById( R.id.tool_bar ); // Attaching the layout to the toolbar object

        mainView=toolbar;
        setSupportActionBar( toolbar );
        actionBar = getSupportActionBar();
        // actionBar.setHomeAsUpIndicator( navIconDrawable );
        setOptionsMenuVisiblity( false );

        Drawable navIconDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_group_menu, getTheme());
        gravity = globalFunctions.getLanguage( mainContext ) == GlobalVariables.LANGUAGE.ARABIC ? GravityCompat.START : GravityCompat.START;
        drawer = ( DrawerLayout ) findViewById( R.id.drawer_layout );
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        toggle.setDrawerIndicatorEnabled( false );
        toggle.setHomeAsUpIndicator(navIconDrawable);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerVisible(gravity)) {
                    drawer.closeDrawer(gravity);
                } else {
                    drawer.openDrawer(gravity);
                }
            }
        });
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        String token = FirebaseInstanceId.getInstance().getToken();
        if (token != null) {
            PushNotificationModel pushNotificationModel = new PushNotificationModel();
            pushNotificationModel.setRegistration_id( token );
            sendPushNotificationID( mainContext, pushNotificationModel );
        }
        if (getIntent().hasExtra( BUNDLE_MAIN_NOTIFICATION_MODEL )) {
            notificationModel = ( NotificationModel ) getIntent().getSerializableExtra( BUNDLE_MAIN_NOTIFICATION_MODEL );
        } else {
            notificationModel = null;
        }
        accessPermissions( this );

        gravity = globalFunctions.getLanguage( mainContext ) == GlobalVariables.LANGUAGE.ARABIC ? GravityCompat.START : GravityCompat.START;

        navigationView.setNavigationItemSelectedListener( this );
        navigationHeaderView = navigationView.getHeaderView( 0 );


        mainActivityFM.addOnBackStackChangedListener( new FragmentManager.OnBackStackChangedListener() {

            @Override
            public void onBackStackChanged() {
                if (mainActivityFM != null) {
                    Fragment currentFragment = mainActivityFM.findFragmentById( R.id.container );
                    if (currentFragment != null) {
                        currentFragment.onResume();
                    }
                }
            }
        } );


        Fragment dashboardFragment=new HomeFragment();
        replaceFragment( dashboardFragment, HomeFragment.TAG, getString( R.string.app_name ), 0, 0 );


        getLocationDetail = new GetLocationDetail(this, this);
        easyWayLocation = new EasyWayLocation(this, false,true,this);
        if (permissionIsGranted()) {
            doLocationWork();
        } else {
            checkLocationPermission();

        }

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            checkLocationPermission();
//        }else {

            //for current location
            /*GPSTracker mGPS = new GPSTracker(this);
            if (mGPS.canGetLocation()) {
                mGPS.getLocation();
                // tvHeaderText.setText("Lat" + mGPS.getLatitude() + "Lon" + mGPS.getLongitude());
                latitute = mGPS.getLatitude();
                longitute = mGPS.getLongitude();
                tvHeaderText.setText(getCompleteAddressString());

            } else {
                tvHeaderText.setText("Unable to Find Location");
                System.out.println("Unable");
                mGPS.showSettingsAlert();

            }*/
//        }
//
//
        //getProfile();
//        loadMenu(mainContext);


    }


    private void getProfile() {
        // globalFunctions.showProgress(activity, getString(R.string.loading));
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.getProfile(mainContext, new ServerResponseInterface() {
            @Override
            public void OnSuccessFromServer(Object arg0) {
                Log.d(TAG, "Response : " + arg0.toString());
                // globalFunctions.hideProgress();
                if (arg0 instanceof ProfileMainModel) {
                    ProfileMainModel profileMainModel=(ProfileMainModel) arg0;
                    ProfileModel profileModel = profileMainModel.getProfileModel();
                    globalFunctions.setProfile(mainContext, profileModel);
                    loadMenu(mainContext);
//                    setNavigationHeaders();
                }
            }

            @Override
            public void OnFailureFromServer(String msg) {
                // globalFunctions.hideProgress();
                globalFunctions.displayMessaage(mainContext, mainView, msg);
                Log.d(TAG, "Failure : " + msg);
            }

            @Override
            public void OnError(String msg) {
                // globalFunctions.hideProgress();
                globalFunctions.displayMessaage(mainContext, mainView, msg);
                Log.d(TAG, "Error : " + msg);
            }
        }, "Get Profile");
    }

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "regular.otf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    public boolean permissionIsGranted() {

        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void doLocationWork() {
        easyWayLocation.startLocation();
    }

    private void loadMenu(final Context context) {
//        globalFunctions.showProgress(activity, getString(R.string.loading));
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.getHomeData(context, new ServerResponseInterface() {
            @Override
            public void OnSuccessFromServer(Object arg0) {
                // globalFunctions.hideProgress();
                Log.d(TAG, "Response : " + arg0.toString());
                if (arg0 instanceof HomePageMainModel) {
                    HomePageMainModel homePageMainModel= (HomePageMainModel) arg0;
                    setThisPage(homePageMainModel.getHomePageModel());

                }
            }

            @Override
            public void OnFailureFromServer(String msg) {
                //globalFunctions.hideProgress();
                globalFunctions.displayMessaage(context, mainView, msg);

                Log.d(TAG, "Failure : " + msg);
            }

            @Override
            public void OnError(String msg) {
                // globalFunctions.hideProgress();
                globalFunctions.displayMessaage(context, mainView, msg);
                Log.d(TAG, "Error : " + msg);
            }
        }, "Menu");
    }

    private void setThisPage(final HomePageModel homePageModel) {
        if (homePageModel != null) {
            if (homePageModel.getProfileMembershipModel() != null) {
                profileMembershipModel = homePageModel.getProfileMembershipModel();
                Log.e("profile",""+homePageModel.getProfileMembershipModel());
                if (navigationHeaderView != null && mainContext != null) {

                    getMembershipDetails(profileMembershipModel);
                }

            }
        }
    }

    private void getMembershipDetails(ProfileMembershipModel profileMembershipModel) {

        if (navigationHeaderView != null && mainContext != null) {
            TextView
                    header_name_tv = (TextView) navigationHeaderView.findViewById(R.id.tv_nav_fullname),
                    // header_email_tv = ( TextView ) navigationHeaderView.findViewById( R.id.nav_tvEmail ),
                    header_tv_free_member = (TextView) navigationHeaderView.findViewById(R.id.tv_free_membership),
                    header_tv_validity_from = (TextView) navigationHeaderView.findViewById(R.id.tv_validity_from),
                    header_tv_validity_to = (TextView) navigationHeaderView.findViewById(R.id.tv_validity_to),
                    nav_header = navigationHeaderView.findViewById(R.id.TvseeProfile),
                    nav_tv_upgrade = navigationHeaderView.findViewById(R.id.tv_upgrade),
                    //nav_ll_validity = navigationHeaderView.findViewById(R.id.ll_validity),
                    header_tv_rezq_plus_member = (TextView) navigationHeaderView.findViewById(R.id.tv_rezq_plus_member);

            ImageView
                    header_app_iv = (ImageView) navigationHeaderView.findViewById(R.id.nav_profile_image);


            nav_header.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    drawer.closeDrawer(GravityCompat.START);

                    Intent intent = new Intent( mainContext, ProfileMainActivity.class );
                    startActivity( intent );

                }
            });
            nav_tv_upgrade.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    drawer.closeDrawer(GravityCompat.START);

                    Intent intent = new Intent( mainContext, UpgradeMembershipListActivity.class );
                    startActivity( intent );
                }
            });
            if (globalFunctions.getLanguage(mainContext) == GlobalVariables.LANGUAGE.ENGLISH) {
                english_language_iv.setVisibility(View.VISIBLE);
                arabic_language_iv.setVisibility(View.GONE);
            } else {
                english_language_iv.setVisibility(View.GONE);
                arabic_language_iv.setVisibility(View.VISIBLE);
            }

            arabic_language_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (globalFunctions.getLanguage(mainContext) == GlobalVariables.LANGUAGE.ENGLISH) {
                        LanguageChange(mainContext);
//                    RestartEntireApp(mainContext, true);
                    } else {
                        ///nothing...
                    }
                    drawer.closeDrawer(gravity);
                }
            });

            english_language_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (globalFunctions.getLanguage(mainContext) == GlobalVariables.LANGUAGE.ENGLISH) {
                        //nothing
                    } else {
                        LanguageChange(mainContext);
//                    RestartEntireApp(mainContext, true);
                    }
                    drawer.closeDrawer(gravity);
                }
            });

            ProfileModel profileModel=globalFunctions.getProfile(activity);
            if (profileModel!=null) {

                if (GlobalFunctions.isNotNullValue(profileModel.getFirstName())) {
                    String membership_fullName = profileModel.getFirstName();
                    if (GlobalFunctions.isNotNullValue(profileModel.getLastName())) {
                        header_name_tv.setText(membership_fullName + " " + profileModel.getLastName());
                    } else {
                        header_name_tv.setText(membership_fullName);
                    }

                }


                if (GlobalFunctions.isNotNullValue(profileModel.getImage())) {
                    Picasso.with(mainContext).load(profileModel.getImage()).placeholder(R.drawable.ic_guest_profile).into(header_app_iv);

                }

            }


            if (profileMembershipModel != null) {

                if (GlobalFunctions.isNotNullValue(profileMembershipModel.getMembership_id()) && ! profileMembershipModel.getMembership_id().equalsIgnoreCase("0")) {
                    String membershipName = profileMembershipModel.getMembership_name();
                    String valid_from = profileMembershipModel.getValid_from();
                    String valid_to = profileMembershipModel.getValid_till();

                    header_tv_free_member.setVisibility(View.GONE);
                    nav_tv_upgrade.setVisibility(View.GONE);
                    header_tv_rezq_plus_member.setVisibility(View.VISIBLE);
                    header_tv_rezq_plus_member.setText(membershipName);
                    header_tv_validity_from.setText(GlobalFunctions.getDateFormat(valid_from));
                    header_tv_validity_to.setText(" - "+GlobalFunctions.getDateFormatTillDate(valid_to));
                    header_tv_rezq_plus_member.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(activity, MembershipDetailsActivity.class);
                            activity.startActivity(intent);
                        }
                    });

                } else {

                    header_tv_free_member.setVisibility(View.VISIBLE);
                    nav_tv_upgrade.setVisibility(View.VISIBLE);
                }

                if (GlobalFunctions.isNotNullValue(profileMembershipModel.getSubscriber_name())) {
                    String membership_fullName =profileMembershipModel.getSubscriber_name();
                    header_name_tv.setText(membership_fullName);
                }

                if (GlobalFunctions.isNotNullValue(profileMembershipModel.getSubscriber_image())) {
                    Picasso.with( mainContext ).load(profileMembershipModel.getSubscriber_image() ).placeholder( R.drawable.ic_baseline_person_24 ).into(crop_profile);
                }

                crop_profile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent( mainContext, ProfileMainActivity.class );
                        startActivity( intent );
                    }
                });


                if (GlobalFunctions.isNotNullValue(profileMembershipModel.getSubscriber_image())) {
                    Picasso.with(mainContext).load(profileMembershipModel.getSubscriber_image()).placeholder(R.drawable.ic_baseline_person_24).into(header_app_iv);

                }


            } else {
                //empry wala
                header_tv_free_member.setVisibility(View.VISIBLE);
                nav_tv_upgrade.setVisibility(View.VISIBLE);

            }
        }
    }

    public void LanguageChange(Context context) {
        ShowPopUpLanguage(context);
    }

    public void ShowPopUpLanguage(final Context context) {
        final AlertDialog alertDialog = new AlertDialog(activity);
        alertDialog.setCancelable(false);
        alertDialog.setIcon(R.drawable.rezq_logo);
        alertDialog.setTitle(activity.getString(R.string.app_name));
        alertDialog.setMessage(activity.getString(R.string.lang_change));
        alertDialog.setPositiveButton(activity.getString(R.string.ok), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                RestartEntireAppForChange(context, true);
            }
        });

        alertDialog.setNegativeButton(activity.getString(R.string.cancel), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    public void RestartEntireAppForChange(Context context, boolean isLanguageChange) {
        if (isLanguageChange) {
            SharedPreferences shared_preference = PreferenceManager.getDefaultSharedPreferences(this
                    .getApplicationContext());

            String mCustomerLanguage = shared_preference.getString(
                    globalVariables.SHARED_PREFERENCE_SELECTED_LANGUAGE, "null");
            String mCurrentlanguage;
            if ((mCustomerLanguage.equalsIgnoreCase("en"))) {
                globalFunctions.setLanguage(context, GlobalVariables.LANGUAGE.ARABIC);

                mCurrentlanguage = "ar";
            } else {
                mCurrentlanguage = "en";
                globalFunctions.setLanguage(context, GlobalVariables.LANGUAGE.ENGLISH);

            }
            SharedPreferences.Editor editor = shared_preference.edit();
            editor.putString(globalVariables.SHARED_PREFERENCE_SELECTED_LANGUAGE, mCurrentlanguage);
            editor.commit();
        }
        globalFunctions.closeAllActivities();
        Intent i = new Intent(this, SplashActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        System.exit(0);
    }

    private boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }

                    }
                } else {
                    Toast.makeText(this, "permission denied",
                            Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }


    private String getCompleteAddressString() {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this,
                Locale.getDefault());
        try {
            List<Address> listAddresses = geocoder.getFromLocation(latitute,
                    longitute, 1);
            if (null != listAddresses && listAddresses.size() > 0) {
                String state = listAddresses.get(0).getAdminArea();
                String country = listAddresses.get(0).getCountryName();
                String subLocality = listAddresses.get(0).getSubLocality();
                strAdd = subLocality+","+state+","+country;

               // Log.e("rrrrr", "" + subLocality+","+state+","+country);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strAdd;
    }

    private void Intialize() {
        llmain = findViewById(R.id.llmain);
        searchView=findViewById(R.id.searchView);
        //textview
        tvHeaderText = findViewById(R.id.tvHeaderText);
        tvLocation = findViewById(R.id.tvlocation);
        crop_profile = findViewById(R.id.iv_userProfile);

        ivHome=findViewById(R.id.ivHomeText);


        iv_menu = findViewById(R.id.iv_menu);


        iv_menu.setOnClickListener(this);
        //drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = ( NavigationView ) findViewById( R.id.nav_view );

        Menu m = navigationView.getMenu();
        for (int i=0;i<m.size();i++) {
            MenuItem mi = m.getItem(i);

            //for aapplying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu!=null && subMenu.size() >0 ) {
                for (int j=0; j <subMenu.size();j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }

            //the method we have create in activity
            applyFontToMenuItem(mi);
        }

    }

    private void replaceFragment(@Nullable Fragment allFragment,@Nullable String tag,@Nullable String title, int titleImageID,@Nullable int bgResID) {
        if (allFragment != null && mainActivityFM != null) {
            Fragment tempFrag = mainActivityFM.findFragmentByTag( tag );
            if (tempFrag != null) {
//                mainActivityFM.beginTransaction().remove(tempFrag).commitAllowingStateLoss();
                mainActivityFM.beginTransaction().remove( tempFrag ).commit();
            }
            setTitle( title, titleImageID, bgResID );
            FragmentTransaction ft = mainActivityFM.beginTransaction();
            ft.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
            //ft.setCustomAnimations(R.anim.slide_out_left, R.anim.slide_out_right);
            ft.add( R.id.container, allFragment, tag ).addToBackStack( tag ).commitAllowingStateLoss();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );

        if (requestCode == LOCATION_SETTING_REQUEST_CODE) {
            easyWayLocation.onActivityResult(resultCode);
        }
    }

    private void accessPermissions(MainActivity mainActivity) {
        int permissionCheck_getAccounts = ContextCompat.checkSelfPermission( activity, android.Manifest.permission.GET_ACCOUNTS );
        int permissionCheck_callPhone = ContextCompat.checkSelfPermission( activity, android.Manifest.permission.CALL_PHONE );
        int permissionCheck_lockwake = ContextCompat.checkSelfPermission( activity, android.Manifest.permission.WAKE_LOCK );
        int permissionCheck_internet = ContextCompat.checkSelfPermission( activity, android.Manifest.permission.INTERNET );
        int permissionCheck_Access_internet = ContextCompat.checkSelfPermission( activity, android.Manifest.permission.ACCESS_NETWORK_STATE );
        int permissionCheck_Access_wifi = ContextCompat.checkSelfPermission( activity, android.Manifest.permission.ACCESS_WIFI_STATE );
        // int permissionCheck_External_storage = ContextCompat.checkSelfPermission(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        // int permissionCheck_cam = ContextCompat.checkSelfPermission(activity, android.Manifest.permission.CAMERA);

        if (permissionCheck_internet != PackageManager.PERMISSION_GRANTED || permissionCheck_Access_internet != PackageManager.PERMISSION_GRANTED || permissionCheck_callPhone != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale( activity, android.Manifest.permission.INTERNET ) && ActivityCompat.shouldShowRequestPermissionRationale( activity, android.Manifest.permission.ACCESS_NETWORK_STATE ) && ActivityCompat.shouldShowRequestPermissionRationale( activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE ) && ActivityCompat.shouldShowRequestPermissionRationale( activity, android.Manifest.permission.CAMERA ) || ActivityCompat.shouldShowRequestPermissionRationale( activity, android.Manifest.permission.CALL_PHONE )) {
                Fragment homeFragment = null;
                homeFragment = new HomeFragment();
                mainActivityFM.beginTransaction().replace( R.id.container, homeFragment, "" ).commitAllowingStateLoss();
            } else {
                ActivityCompat.requestPermissions( activity, new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.INTERNET, android.Manifest.permission.ACCESS_NETWORK_STATE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.CALL_PHONE}, globalVariables.PERMISSIONS_REQUEST_PRIMARY );
            }
        }
    }

    private void sendPushNotificationID(Context mainContext, PushNotificationModel pushNotificationModel) {
        if (globalFunctions.getSharedPreferenceString( globalVariables.SHARED_PREFERENCE_COOKIE ) != null) {
            ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
            servicesMethodsManager.sendPushNotificationID( mainContext, pushNotificationModel, new ServerResponseInterface() {
                @Override
                public void OnSuccessFromServer(Object arg0) {
                    Log.d( TAG, "Response : " + arg0.toString() );
                }

                @Override
                public void OnFailureFromServer(String msg) {
                    Log.d( TAG, "Failure : " + msg );
                }

                @Override
                public void OnError(String msg) {
                    Log.d( TAG, "Error : " + msg );
                }
            }, "Send_Push_Notification_ID" );
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        easyWayLocation.endUpdates();
    }
    @Override
    protected void onStart() {
      /*  if (upComingFragment != null) {
            Fragment fragment = upComingFragment.instantiate( activity, 0 );
            (( MyAppointmentsNewListFragment ) fragment).refreshList();
        }
        if (completedFragment != null) {
            Fragment fragment = completedFragment.instantiate( activity, 0 );
            (( MyAppointmentsConfirmedListFragment ) fragment).refreshList();
        }*/
        super.onStart();
    }

    @Override
    protected void onResume() {
        getProfile();
        super.onResume();
        easyWayLocation.startLocation();

    }



    public void setOptionsMenuVisiblity(boolean showMenu) {
        if (menu == null)
            return;
        //menu.setGroupVisible(R.id.menu, showMenu);
    }

    private void setNavigationHeaders() {
        if (navigationHeaderView != null && mainContext != null) {
            TextView
                    header_name_tv = ( TextView ) navigationHeaderView.findViewById( R.id.tv_nav_fullname ),
                   // header_email_tv = ( TextView ) navigationHeaderView.findViewById( R.id.nav_tvEmail ),
                    header_tv_free_member = ( TextView ) navigationHeaderView.findViewById( R.id.tv_free_membership ),
                    header_tv_validity_from = ( TextView ) navigationHeaderView.findViewById( R.id.tv_validity_from ),
                    header_tv_validity_to = ( TextView ) navigationHeaderView.findViewById( R.id.tv_validity_to ),
                    nav_header=navigationHeaderView.findViewById(R.id.TvseeProfile),
                    nav_tv_upgrade=navigationHeaderView.findViewById(R.id.tv_upgrade),
                    header_tv_rezq_plus_member= ( TextView ) navigationHeaderView.findViewById( R.id.tv_rezq_plus_member );

            ImageView
                    header_app_iv = ( ImageView ) navigationHeaderView.findViewById( R.id.nav_profile_image);

                    nav_header.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                            drawer.closeDrawer(GravityCompat.START);

                            Intent intent = new Intent( mainContext, ProfileMainActivity.class );
                            startActivity( intent );

                        }
                    });
                    nav_tv_upgrade.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                            drawer.closeDrawer(GravityCompat.START);

                            Intent intent = new Intent( mainContext, UpgradeMembershipListActivity.class );
                            startActivity( intent );
                        }
                    });


            ProfileModel profileModel = globalFunctions.getProfile( mainContext );
            if (profileModel != null && mainContext != null) {
                try {
                    String
                            fullName = profileModel.getFirstName() + " " + profileModel.getLastName();
                            header_name_tv.setText( fullName != null ? fullName : getString( R.string.guest ) );
                     String
                            user_full_name=profileModel.getFullname();
                          // header_name_tv.setText(user_full_name);

                    try {
                        if (profileModel.getProfileImg() != null || !profileModel.getProfileImg().equals( "null" ) || !profileModel.getProfileImg().equalsIgnoreCase( "" )) {
                            Picasso.with( mainContext ).load(profileModel.getProfileImg() ).placeholder( R.drawable.ic_baseline_person_24 ).into( header_app_iv );
                        }
                    } catch (Exception e) {
                    }

                } catch (Exception exxx) {
                    Log.e( TAG, exxx.getMessage() );
                }

                if (navigationHeaderView != null && mainContext != null) {
                   // getMembershipDetails();

                }


            } else {
                if (mainContext != null) {
                    try {
                        //header_email_tv.setVisibility( View.GONE );
                        header_name_tv.setText( mainContext.getString( R.string.guest ) );
                    } catch (Exception exxx) {
                        Log.e( TAG, exxx.getMessage() );
                    }
                }
            }


        }
    }
    public void RestartEntireApp(Context context, boolean isLanguageChange) {
        if (isLanguageChange) {
            SharedPreferences shared_preference = PreferenceManager.getDefaultSharedPreferences( this
                    .getApplicationContext() );

            String mCustomerLanguage = shared_preference.getString(
                    globalVariables.SHARED_PREFERENCE_SELECTED_LANGUAGE, "null" );
            String mCurrentlanguage;
            if ((mCustomerLanguage.equalsIgnoreCase( "en" ))) {
                globalFunctions.setLanguage( context, GlobalVariables.LANGUAGE.ARABIC );

                mCurrentlanguage = "ar";
            } else {
                mCurrentlanguage = "en";
                globalFunctions.setLanguage( context, GlobalVariables.LANGUAGE.ENGLISH );

            }
            SharedPreferences.Editor editor = shared_preference.edit();
            editor.putString( globalVariables.SHARED_PREFERENCE_SELECTED_LANGUAGE, mCurrentlanguage );
            editor.commit();
        }
        globalFunctions.closeAllActivities();
        Intent i = new Intent( this, SplashActivity.class );
        i.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK );
        startActivity( i );
        System.exit( 0 );
    }
    public void stimulateOnResumeFunction() {
        mainActivityFM.findFragmentById( R.id.container ).onResume();
    }

    public void releseFragments() {
        for (int i = 0; i < mainActivityFM.getBackStackEntryCount(); ++i) {
            mainActivityFM.popBackStack();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState( outState );
    }


    private void logoutUser(final  Context mainContext,final UpdateLanguageModel updateLanguageModel) {
        GlobalFunctions.showProgress( mainContext, getString( R.string.logingout ) );
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.logout( mainContext,updateLanguageModel, new ServerResponseInterface() {
            @Override
            public void OnSuccessFromServer(Object arg0) {
                GlobalFunctions.hideProgress();
                Log.d( TAG, "Response : " + arg0.toString() );
                validateOutput( arg0 );
            }

            @Override
            public void OnFailureFromServer(String msg) {
                GlobalFunctions.hideProgress();
                Toast.makeText( mainContext, msg, Toast.LENGTH_SHORT ).show();
                //GlobalFunctions.displayMessaage(context, mainView, msg);
                Log.d( TAG, "Failure : " + msg );
            }

            @Override
            public void OnError(String msg) {
                GlobalFunctions.hideProgress();
                Toast.makeText( mainContext, msg, Toast.LENGTH_SHORT ).show();
                //GlobalFunctions.displayMessaage(context, mainView, msg);
                Log.d( TAG, "Error : " + msg );
            }
        }, "Logout_User" );
    }

    private void validateOutput(Object arg0) {
        if (arg0 instanceof StatusModel) {
            StatusModel statusModel = ( StatusModel ) arg0;
            //globalFunctions.displayMessaage(activity, mainView, statusModel.getMessage());
            if (statusModel.isStatus()) {
                /*Logout success, Clear all cache and reload the home page*/

                globalFunctions.logoutApplication( mainContext ,false);
                GlobalFunctions.closeAllActivities();
                RestartEntireApp( mainContext, false );

            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
           /* case R.id.iv_menu:
                drawer.openDrawer(Gravity.LEFT);
                break;
*/
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {

        } else if (id == R.id.nav_recentCoupon) {
            Intent intent = new Intent(activity, RecentCouponActivity.class);
            activity.startActivity(intent);
        }
        else if (id == R.id.nav_fav) {
            Intent intent = new Intent(activity, WishListActivity.class);
            activity.startActivity(intent);
        }
        else if (id == R.id.nav_membership) {

            if (profileMembershipModel != null) {
                if (profileMembershipModel.getMembership_id() != null) {
                    Intent intent = new Intent(activity, MembershipDetailsActivity.class);
                    activity.startActivity(intent);
                }
            } else {
                Intent intent = new Intent(activity, FreeMembershipActivity.class);
                activity.startActivity(intent);
            }
        }
        else if (id == R.id.nav_account) {
            /*Intent intent = new Intent(activity, SwitchAccountActivity.class);
            activity.startActivity(intent);*/

             Intent intent = new Intent(activity, ProfileMainActivity.class);
             activity.startActivity(intent);

        }else if (id == R.id.nav_logout) {
            if (GlobalFunctions.isLoggedIn(activity)) {
                logout();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    private void logout() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        final AlertDialog alertDialog = new AlertDialog( activity );
        alertDialog.setCancelable( false );
        alertDialog.setIcon( R.drawable.rezq_logo );
        alertDialog.setTitle( activity.getString( R.string.app_name ) );
        alertDialog.setMessage( activity.getResources().getString( R.string.appLogoutText ));
        alertDialog.setPositiveButton( activity.getString( R.string.yes ), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                //Logout the Application
               /* LogoutModel logoutModel=new LogoutModel();
                logoutModel.setUuid(GlobalFunctions.getUniqueID(activity));
                logoutUser( mainContext ,logoutModel);*/

                UpdateLanguageModel updateLanguageModel = new UpdateLanguageModel();
                if (GlobalFunctions.isNotNullValue(GlobalFunctions.getSharedPreferenceString(mainContext, GlobalVariables.SHARED_PREFERENCE_TOKEN))) {
                    updateLanguageModel.setPushToken(GlobalFunctions.getSharedPreferenceString(mainContext, GlobalVariables.SHARED_PREFERENCE_TOKEN));
                    logoutUser(mainContext, updateLanguageModel);
                }
            }
        } );

        alertDialog.setNegativeButton( activity.getString( R.string.cancel ), new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();
            }
        } );

        alertDialog.show();

    }



    @Override
    public void onLocationChanged(@NonNull Location location) {

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        LocationManager locationManager = (LocationManager)
                this.getSystemService(Context.LOCATION_SERVICE);
        String provider = locationManager.getBestProvider(new Criteria(), true);
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location locations = locationManager.getLastKnownLocation(provider);
        List<String> providerList = locationManager.getAllProviders();
        if (null != locations && null != providerList && providerList.size() > 0) {
            longitute= locations.getLongitude();
            latitute = locations.getLatitude();
            Geocoder geocoder = new Geocoder(this,
                    Locale.getDefault());
            try {
                List<Address> listAddresses = geocoder.getFromLocation(latitute,
                        longitute, 1);
                if (null != listAddresses && listAddresses.size() > 0) {
                    String state = listAddresses.get(0).getAdminArea();
                    String country = listAddresses.get(0).getCountryName();
                    String subLocality = listAddresses.get(0).getSubLocality();
                    markerOptions.title("" + latLng + "," + subLocality + "," + state
                            + "," + country);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(MainActivity.this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                    mLocationRequest, (com.google.android.gms.location.LocationListener) this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    protected void onDestroy() {
        //deRegisterBroadcast();
        super.onDestroy();
    }



    @Override
    public void onBackPressed() {
        if (mainActivityFM != null) {
            String currentFragment = mainActivityFM.findFragmentById( R.id.container ).getClass().getName();
            String homeFragmentName = HomeFragment.class.getName();
            DrawerLayout drawer = ( DrawerLayout ) findViewById( R.id.drawer_layout );
            if (drawer.isDrawerOpen( GravityCompat.START )) {
                drawer.closeDrawers();
            } else if (!currentFragment.equalsIgnoreCase( homeFragmentName )) {
                Fragment homeFragment = homeFragment = new HomeFragment();
                replaceFragment( homeFragment, HomeFragment.TAG, getString( R.string.app_name ), 0, 0 );
            } else if (currentFragment.equalsIgnoreCase( homeFragmentName )) {
                /*Exit Alert Box*/
                final AlertDialog alertDialog = new AlertDialog( mainContext );
                alertDialog.setCancelable( false );
                alertDialog.setIcon( R.drawable.rezq_logo );
                alertDialog.setTitle( getString( R.string.app_name ) );
                alertDialog.setMessage( getResources().getString( R.string.appExitText ) );
                alertDialog.setPositiveButton( getString( R.string.yes ), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        GlobalFunctions.closeAllActivities();
                        finishAffinity();
                        System.exit( 0 );
                    }
                } );
                alertDialog.setNegativeButton( getString( R.string.cancel ), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                } );

                alertDialog.show();

            } else {
                super.onBackPressed();
                stimulateOnResumeFunction();
            }
        } else {
            super.onBackPressed();
            stimulateOnResumeFunction();

        }
        //stimulateOnResumeFunction();
    }

    @Override
    public void locationOn() {

    }

    @Override
    public void currentLocation(Location location) {
        getLocationDetail.getAddress(location.getLatitude(), location.getLongitude(), activity.getString(R.string.google_api_key));
    }

    @Override
    public void locationCancelled() {

    }

    @Override
    public void locationData(LocationData locationData) {
        tvHeaderText.setText(locationData.getFull_address());
        Log.d("address00","=="+locationData.getFull_address());

    }
}