package com.sa.rezq.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.sa.rezq.Fragemts.DashboardFragment;
import com.sa.rezq.Fragemts.FavouriteFragment;
import com.sa.rezq.Fragemts.MembershipFragment;
import com.sa.rezq.Fragemts.RecentCouponFragment;
import com.sa.rezq.R;
import com.sa.rezq.extra.Preferences;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, LocationListener,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {


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
    public  static CircleImageView Crprofile;
    public  static SearchView EtsearchRecent;



    public static NavigationView navigationView;

    Preferences preferences;

    //Langauge Textview
    TextView tvArabic,tvEnglish;

    //other
    public static int backPressed = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        //initialize findviewbyid
        Intialize();

        searchView .clearFocus();
        searchView.setFocusable(false);


        iv_menu.setOnClickListener(this);

        //replace dashboard fragment
        replaceFragmentWithAnimation(new DashboardFragment());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        //for current location
        GPSTracker mGPS = new GPSTracker(this);
        if (mGPS.canGetLocation()) {
            mGPS.getLocation();
            tvHeaderText.setText("Lat" + mGPS.getLatitude() + "Lon" + mGPS.getLongitude());
            latitute = mGPS.getLatitude();
            longitute = mGPS.getLongitude();
            tvHeaderText.setText(getCompleteAddressString());

        } else {
            tvHeaderText.setText("Unable to Find Location");
            System.out.println("Unable");
            mGPS.showSettingsAlert();

        }

        //Change language
        tvEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocaleHelper.setLocale(MainActivity.this,"en"); //for english;
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                /*Intent refresh = new Intent(this, MainActivity.class);
                finish();
                startActivity(refresh);*/
            }
        });
        tvArabic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocaleHelper.setLocale(MainActivity.this,"ar-rSA"); //for arabic;
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);

            }
        });





        // setvalue
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        if (navigationView != null) {
            Menu menu = navigationView.getMenu();
            /*if (preferences.get("login").equalsIgnoreCase("yes")) {

                menu.findItem(R.id.nav_logout).setTitle("Login");
            } else {
                menu.findItem(R.id.nav_logout).setTitle("Logout");
            }*/
            navigationView.setNavigationItemSelectedListener(this);

        }
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

                Log.e("rrrrr", "" + subLocality+","+state+","+country);
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
        Crprofile = findViewById(R.id.userProf);
        tvArabic=findViewById(R.id.Tvlang_arabic);
        tvEnglish=findViewById(R.id.Tvlang_english);
        ivHome=findViewById(R.id.ivHomeText);
        EtsearchRecent=findViewById(R.id.Etsearch);


        //imageview
        iv_menu = findViewById(R.id.iv_menu);
        preferences=new Preferences(this);

        //navigationview
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);

    }
    public void replaceFragmentWithAnimation(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_menu:
                drawer.openDrawer(Gravity.LEFT);
                v.setFocusableInTouchMode(true);
                v.requestFocus();
                v.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (event.getAction() == KeyEvent.ACTION_DOWN) {
                            if (keyCode == KeyEvent.KEYCODE_BACK) {
                                drawer.closeDrawer(Gravity.LEFT);

                                return true;
                            }
                        }
                        return false;
                    }
                });
                break;

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            replaceFragmentWithAnimation( new DashboardFragment());
        } else if (id == R.id.nav_recentCoupon) {
            replaceFragmentWithAnimation(new RecentCouponFragment());

        }
        else if (id == R.id.nav_fav) {
            replaceFragmentWithAnimation(new FavouriteFragment());

        }
        else if (id == R.id.nav_membership) {
            replaceFragmentWithAnimation(new MembershipFragment());

        }
        else if (id == R.id.nav_account) {
        }else if (id == R.id.nav_logout) {
            logout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;    }

    private void logout() {

    }

    @Override
    public void onBackPressed() {
        backPressed = backPressed + 1;
        if (backPressed == 1) {
            Toast.makeText(MainActivity.this, "Press back again to exit", Toast.LENGTH_SHORT).show();
            new CountDownTimer(5000, 1000) { // adjust the milli seconds here
                public void onTick(long millisUntilFinished) {
                }
                public void onFinish() { backPressed = 0;
                }
            }.start();
        }
        if (backPressed == 2) {
            backPressed = 0;
            finishAffinity();
            android.os.Process.killProcess(android.os.Process.myPid());
        }
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
    public static class LocaleHelper {

        private static final String SELECTED_LANGUAGE = "Locale.Helper.Selected.Language";

        public static void onCreate(Context context) {

            String lang;
            if(getLanguage(context).isEmpty()){
                lang = getPersistedData(context, Locale.getDefault().getLanguage());
            }else {
                lang = getLanguage(context);
            }

            setLocale(context, lang);
        }

        public static void onCreate(Context context, String defaultLanguage) {
            String lang = getPersistedData(context, defaultLanguage);
            setLocale(context, lang);
        }

        public static String getLanguage(Context context) {
            return getPersistedData(context, Locale.getDefault().getLanguage());
        }

        public static void setLocale(Context context, String language) {
            persist(context, language);
            updateResources(context, language);
        }

        private static String getPersistedData(Context context, String defaultLanguage) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            return preferences.getString(SELECTED_LANGUAGE, defaultLanguage);
        }

        private static void persist(Context context, String language) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = preferences.edit();

            editor.putString(SELECTED_LANGUAGE, language);
            editor.apply();
        }

        private static void updateResources(Context context, String language) {
            Locale locale = new Locale(language);
            Locale.setDefault(locale);

            Resources resources = context.getResources();

            Configuration configuration = resources.getConfiguration();
            configuration.locale = locale;

            resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        }
    }
}