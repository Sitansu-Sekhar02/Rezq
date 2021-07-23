package com.sa.rezq.map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Address;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.sa.rezq.Activity.AppController;
import com.sa.rezq.R;
import com.sa.rezq.global.GlobalFunctions;
import com.sa.rezq.global.GlobalVariables;
import com.sa.rezq.location.LocationListener;
import com.sa.rezq.location.LocationServices;
import com.sa.rezq.services.model.AddressModel;
import com.sa.rezq.services.model.MyCityListModel;
import com.sa.rezq.services.model.MyCityModel;
import com.sa.rezq.services.model.ProfileModel;
import com.sa.rezq.services.model.UserAddressModel;
import com.sa.rezq.widget.TouchableWrapper;

import java.util.ArrayList;
import java.util.List;


public class SearchPlaceOnMapActivity extends AppCompatActivity implements MapWrapperLayout.OnDragListener, OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleMap.OnMyLocationButtonClickListener, ActivityCompat.OnRequestPermissionsResultCallback,
        GoogleApiClient.OnConnectionFailedListener,
        TouchableWrapper.UpdateMapAfterUserInterection, LocationListener {


    private static final int CAMERA_ZOOM_LEVEL_FOR_MAP = 16;

    public static final String
            TAG = "SrcPlaceOnMapActivity",
            BUNDLE_SEARCH_PLACE_ON_MAP_ACTIVITY_ADDRESS_MODEL = "BundleSearchPlaceOnMapActivityAddressModel",
            BUNDLE_SEARCH_PLACE_ON_MAP_ACTIVITY_LOCATION_TYPE = "BundleSearchPlaceOnMapActivityLocationType",
            BUNDLE_SEARCH_PLACE_ON_MAP_ACTIVITY_USER_REQ_MODEL = "BundleSearchPlaceOnMapActivityUserReqModel",
            BUNDLE_SEARCH_PLACE_ON_MAP_ACTIVITY_PAGE_TYPE = "BundleSearchPlaceOnMapActivityPageType",
            BUNDLE_SEARCH_PLACE_ON_MAP_ACTIVITY_FROM_PAGE = "BundleSearchPlaceOnMapActivityFromPage",
            BUNDLE_SEARCH_PLACE_ON_MAP_ACTIVITY_ADDRESS_COUNT = "BundleSearchPlaceOnMapActivityAddressCount",
            BUNDLE_MY_CITY_MODE = "BundleMyCityModel";

    private static final int
            LOCATION_PERMISSION_REQUEST_CODE = 132,
            PERMISSION_ACCESS_COARSE_LOCATION = 156;

    private PlacesClient placesClient;

    String selectedCityId = null;

    Context context = null;
    static Activity activity = null;
    Window window = null;
    View mainView;

    static Toolbar toolbar;
    static ActionBar actionBar;
    static String mTitle;
    static int mResourceID, titleResourseID;
    static Menu menu;
    static TextView toolbar_title;
    static ImageView toolbar_logo;

    String addressType = GlobalVariables.ADDRESS_TYPE_HOME;
    UserAddressModel userAddressModel = null;

    //This is for restriciting search only to india, for other countries you can specify lat lng by changing the below values.
    private static final LatLngBounds BOUNDS_GREATER_INDIA = new LatLngBounds(
            new LatLng(18.9096, 24.774265), new LatLng(20.9096, 34.774265));

    private PlaceAutocompleteAdapter mAdapter;
    private AutoCompleteTextView mAutocompleteView;

    private AddressModel mAddress;
    boolean miSConnectedDone = false;

    String selectedAddressId = null;

    private View mMarkerParentView;
    private ImageView mMarkerImageView;
    static ImageView search_clear_iv;

    private int
            imageParentWidth = -1,
            imageParentHeight = -1,
            imageHeight = -1,
            centerX = -1,
            centerY = -1;

    private CustomMapFragment
            mCustomMapFragment;

    private GoogleMap
            mMap;

    private TextView
            save_tv;
    private EditText first_name_etv, last_name_etv, phone_number_etv, pincode_etv, address_etv, landmark_etv, city_town_etv, state_etv, select_city_etv;

    private TextView home_tv, work_tv, others_tv, select_city_tv;
    private AppCompatCheckBox default_address_cb;
    private RelativeLayout city_rl;

    private GoogleApiClient
            googleApiClient;

    private GlobalFunctions
            globalFunctions;

    private GlobalVariables
            globalVariables;

    MyCityListModel cityListModel = null;

    UserAddressModel model = null;
    MyCityModel myCityModel = null;

    String fromPage = null;
    String addressCount = "0";

    ImageView back_iv, cart_iv, search_iv;
    RelativeLayout cart_notification_layout;

    private GlobalVariables.LOCATION_TYPE locationType = GlobalVariables.LOCATION_TYPE.NONE;

    static Intent locationintent;
    LocationServices locationService;

    private OnSuccessListener fetchPlaceResponseHandler = new OnSuccessListener() {
        @Override
        public void onSuccess(Object object) {
            if (object instanceof FetchPlaceResponse) {
                FetchPlaceResponse fetchPlaceResponse = (FetchPlaceResponse) object;
                if (fetchPlaceResponse != null) {
                    fetchPlaceResponse.getPlace();
                    hideKeyboard();
                    Place place = fetchPlaceResponse.getPlace();
                    String address = place.getAddress();
                    if (mAddress == null) {
                        mAddress = new AddressModel();
                    }
                    mAddress.setAddress(address);
                    mAddress.setLatitude(place.getLatLng().latitude);
                    mAddress.setLongitude(place.getLatLng().longitude);

                    LatLng newLatLngTemp = new LatLng(place.getLatLng().latitude, place.getLatLng().longitude);
                    updateLocation(newLatLngTemp, true);

                }
            }
        }
    };

    private AdapterView.OnItemClickListener
            mAutocompleteClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            final PlaceAutocompleteAdapter.PlaceAutocomplete item = mAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);


            final List<Place.Field> fieldList = new ArrayList<>();
            fieldList.add(Place.Field.NAME);
            fieldList.add(Place.Field.ADDRESS);
            fieldList.add(Place.Field.LAT_LNG);

            FetchPlaceRequest fetchPlaceRequest =
                    FetchPlaceRequest.builder(placeId, fieldList)
                            .build();


            Task<FetchPlaceResponse> fetchPlaceRequestTask = placesClient.fetchPlace(fetchPlaceRequest);

            fetchPlaceRequestTask.addOnSuccessListener(fetchPlaceResponseHandler);

        }
    };

    public static Intent newInstance(Context context, UserAddressModel model, AddressModel addressModel, GlobalVariables.LOCATION_TYPE locationType, String fromPage, String addressCount) {
        Intent intent = new Intent(context,SearchPlaceOnMapActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_SEARCH_PLACE_ON_MAP_ACTIVITY_ADDRESS_MODEL, addressModel);
        bundle.putSerializable(BUNDLE_SEARCH_PLACE_ON_MAP_ACTIVITY_USER_REQ_MODEL, model);
        bundle.putSerializable(BUNDLE_SEARCH_PLACE_ON_MAP_ACTIVITY_LOCATION_TYPE, locationType);
        bundle.putString(BUNDLE_SEARCH_PLACE_ON_MAP_ACTIVITY_FROM_PAGE, fromPage);
        bundle.putString(BUNDLE_SEARCH_PLACE_ON_MAP_ACTIVITY_ADDRESS_COUNT, addressCount);
        intent.putExtras(bundle);
        return intent;
    }

    public static Intent newInstance(Context context, UserAddressModel addressModel, String fromPage) {
        Intent intent = new Intent(context, SearchPlaceOnMapActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_SEARCH_PLACE_ON_MAP_ACTIVITY_USER_REQ_MODEL, addressModel);
        bundle.putString(BUNDLE_SEARCH_PLACE_ON_MAP_ACTIVITY_FROM_PAGE, fromPage);
        intent.putExtras(bundle);
        return intent;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_place_on_map);
        activity = this;
        context = this;

       /* Drawable navIconDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_back, getTheme());

        toolbar = (Toolbar) findViewById(R.id.toolbar); // Attaching the layout to the toolbar object
        //toolbar.setPadding(0, globalFunctions.getStatusBarHeight(this), 0, 0);
        toolbar.setContentInsetsAbsolute(0, 0);
        toolbar.setNavigationIcon(navIconDrawable);
        toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbar_logo = (ImageView) toolbar.findViewById(R.id.tool_bar_logo);

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(navIconDrawable);
        setOptionsMenuVisiblity(false);*/

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        //toolbar.setPadding(0, GlobalFunctions.getStatusBarHeight(context), 0, 0);
        //toolbar.setNavigationIcon(R.drawable.ic_back_draw);
        //toolbar.setContentInsetsAbsolute(0,0);

        toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        back_iv = (ImageView) findViewById(R.id.tool_bar_back_icon);

//        setSupportActionBar(toolbar);
//        actionBar = getSupportActionBar();
//        setOptionsMenuVisiblity(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }

        Places.initialize(AppController.getInstance().getApplicationContext(), activity.getString(R.string.GoogleAPIKey));

        initializeMap();

        locationintent = new Intent(LocationServices.LOCATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            locationintent.setPackage(context.getPackageName());
        }

        locationService = new LocationServices();
        if (locationService != null) {
            startLocationService();
            //locationService.setListener(this);
        }

        globalFunctions = AppController.getInstance().getGlobalFunctions();
        globalVariables = AppController.getInstance().getGlobalVariables();

        mAutocompleteView = (AutoCompleteTextView) findViewById(R.id.googleplacesearch);
        mMarkerParentView = findViewById(R.id.marker_view_incl);
        mMarkerImageView = (ImageView) findViewById(R.id.marker_icon_view);

        save_tv = (TextView) findViewById(R.id.add_address_tv);


        mainView = mAutocompleteView;

        placesClient = Places.createClient(context);

        mAdapter = new PlaceAutocompleteAdapter(this, R.layout.google_places_search_items, placesClient, null);

        //TODO:In order to Restrict search to India uncomment this and comment the above line
        /*
        mAdapter = new PlaceAutocompleteAdapter(this, R.layout.google_places_search_items,
                mGoogleApiClient, BOUNDS_GREATER_INDIA, null);
         */

        mAutocompleteView.setAdapter(mAdapter);
        mAutocompleteView.setOnItemClickListener(mAutocompleteClickListener);

        /*search_clear_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAutocompleteView.setText("");
                address_etv.setText("");
            }
        });*/

        try {
            Intent intent = getIntent();
            locationType = (GlobalVariables.LOCATION_TYPE) intent.getSerializableExtra(BUNDLE_SEARCH_PLACE_ON_MAP_ACTIVITY_LOCATION_TYPE);
        } catch (Exception e) {
            locationType = null;
        }

        try {
            if (getIntent().hasExtra(BUNDLE_SEARCH_PLACE_ON_MAP_ACTIVITY_FROM_PAGE)) {
                fromPage = getIntent().getStringExtra(BUNDLE_SEARCH_PLACE_ON_MAP_ACTIVITY_FROM_PAGE);
            } else {
                fromPage = null;
            }
        } catch (Exception excc) {
            fromPage = null;
        }

        try {
            if (getIntent().hasExtra(BUNDLE_SEARCH_PLACE_ON_MAP_ACTIVITY_ADDRESS_COUNT)) {
                addressCount = getIntent().getStringExtra(BUNDLE_SEARCH_PLACE_ON_MAP_ACTIVITY_ADDRESS_COUNT);
            } else {
                addressCount = "0";
            }
        } catch (Exception excc) {
            addressCount = "0";
        }

        try {
            Intent intent = getIntent();
            model = (UserAddressModel) intent.getExtras().getSerializable(BUNDLE_SEARCH_PLACE_ON_MAP_ACTIVITY_USER_REQ_MODEL);
        } catch (Exception e) {
            model = null;
        }

        if (model != null) {
            //from edit address....
            setThisPage(model);
        } else {
            //add address...
            homeIsSelected();
        }

        try {
            Intent intent = getIntent();
            mAddress = (AddressModel) intent.getExtras().getSerializable(BUNDLE_SEARCH_PLACE_ON_MAP_ACTIVITY_ADDRESS_MODEL);

        } catch (Exception e) {
        }

        try {
            if (mAddress != null) {
                if (mAddress.getLatitude() == 0 || mAddress.getLongitude() == 0) {
                    GetLatLon();
                } else {
                    LatLng newLatLngTemp = new LatLng(mAddress.getLatitude(), mAddress.getLongitude());
                    updateLocation(newLatLngTemp, true);
                }
            } else {
                GetLatLon();
            }
        } catch (Exception exccc) {

        }

        try {
            if (model != null) {
                if (!GlobalFunctions.isNotNullValue(model.getLatitude())) {
                    GetLatLon();
                } else {
                    double latvalue= Double.parseDouble(model.getLatitude());
                    double longvalue= Double.parseDouble(model.getLongitude());
                    LatLng newLatLngTemp = new LatLng(latvalue, longvalue);
                    updateLocation(newLatLngTemp, true);
                }
            } else {
                GetLatLon();
            }
        } catch (Exception exccc) {

        }

     /*   select_city_etv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = CityListActivity.newInstance(activity, new MyCityListModel(), "1");
                startActivityForResult(intent, GlobalVariables.REQUEST_CODE_FOR_CITY);

            }
        });

        select_city_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = CityListActivity.newInstance(activity, new MyCityListModel(), "1");
                startActivityForResult(intent, GlobalVariables.REQUEST_CODE_FOR_CITY);

            }
        });*/

        save_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateInput();
            }
        });

        /*cancel_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });*/

        /*city_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = SelectCityListActivity.newInstance(activity, cityListModel, "1");
                startActivityForResult(intent, globalVariables.REQUEST_SELECT_CITY_CODE);
            }
        });*/

        home_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeIsSelected();
            }
        });

        work_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                workIsSelected();
            }
        });

        others_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                othersIsSelected();
            }
        });

        /*cart_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CartActivity.class);
                activity.startActivity(intent);
            }
        });

        search_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(activity, SearchActivity.class);
                startActivityForResult(myIntent, globalVariables.REQUEST_CODE_FOR_SEARCH);
            }
        });*/

        back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void setThisPage(UserAddressModel model) {
        if (model != null) {
            selectedAddressId = model.getId();

            if (GlobalFunctions.isNotNullValue(model.getFirstName())) {
                first_name_etv.setText(model.getFirstName());
            }

            if (GlobalFunctions.isNotNullValue(model.getLastName())) {
                last_name_etv.setText(model.getLastName());
            }

            if (GlobalFunctions.isNotNullValue(model.getNumber())) {
                phone_number_etv.setText(model.getNumber());
            }

            if (GlobalFunctions.isNotNullValue(model.getAddress())) {
                address_etv.setText(model.getAddress());
            }

            if (GlobalFunctions.isNotNullValue(model.getPincode())) {
                pincode_etv.setText(model.getPincode());
            }

            if (GlobalFunctions.isNotNullValue(model.getType()) && model.getType().equalsIgnoreCase("1")) {
                default_address_cb.setChecked(true);
            }

            if (model.getAddressType() != null) {
                if (model.getAddressType().equalsIgnoreCase(globalVariables.ADDRESS_TYPE_HOME)) {
                    homeIsSelected();
                } else if (model.getAddressType().equalsIgnoreCase(globalVariables.ADDRESS_TYPE_WORK)) {
                    workIsSelected();
                } else if (model.getAddressType().equalsIgnoreCase(globalVariables.ADDRESS_TYPE_OTHERS)) {
                    othersIsSelected();
                } else {
                    othersIsSelected();
                }
            }

            if (globalFunctions.isNotNullValue(model.getCityName())) {
                city_town_etv.setText(model.getCityName());
            }

            if (globalFunctions.isNotNullValue(model.getCityId())) {
                selectedCityId = model.getCityId();
            }

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        GlobalFunctions.hideProgress();
        /*if (resultCode == activity.RESULT_OK && requestCode == globalVariables.REQUEST_SELECT_CITY_CODE) {
            MyCityModel myCityModel1 = (MyCityModel) data.getExtras().getSerializable(SelectCityListActivity.BUNDLE_CITY_MODEL);
            if (myCityModel1 != null) {
                selectedCityId = myCityModel1.getId();
                city_town_etv.setText(myCityModel1.getName());
            }
        } else if (resultCode == activity.RESULT_OK && requestCode == globalVariables.REQUEST_CODE_FOR_CITY) {
            MyCityModel myCityModel = (MyCityModel) data.getExtras().getSerializable(CityListActivity.BUNDLE_CITY_MODEL);
            if (myCityModel != null) {
                selectedCityId = myCityModel.getId();
//                select_city_etv.setText(myCityModel.getName());
                select_city_tv.setText(myCityModel.getName());
            }
        }*/
    }

    private void homeIsSelected() {
        addressType = globalVariables.ADDRESS_TYPE_HOME;
      //  home_tv.setBackground(activity.getResources().getDrawable(R.drawable.bg_button_color_accent_filled_half_border_grey_curved));
      //  work_tv.setBackground(activity.getResources().getDrawable(R.drawable.bg_button_color_border_deep_grey_curved));
       // others_tv.setBackground(activity.getResources().getDrawable(R.drawable.bg_button_color_border_deep_grey_curved));

        home_tv.setTextColor(activity.getResources().getColor(R.color.app_fontBlackColor));
        work_tv.setTextColor(activity.getResources().getColor(R.color.app_fontBlackColor));
        others_tv.setTextColor(activity.getResources().getColor(R.color.app_fontBlackColor));
    }

    private void workIsSelected() {
        addressType = globalVariables.ADDRESS_TYPE_WORK;
       // home_tv.setBackground(activity.getResources().getDrawable(R.drawable.bg_button_color_border_deep_grey_curved));
       // work_tv.setBackground(activity.getResources().getDrawable(R.drawable.bg_button_color_accent_filled_half_border_grey_curved));
       // others_tv.setBackground(activity.getResources().getDrawable(R.drawable.bg_button_color_border_deep_grey_curved));

        home_tv.setTextColor(activity.getResources().getColor(R.color.app_fontBlackColor));
        work_tv.setTextColor(activity.getResources().getColor(R.color.app_fontBlackColor));
        others_tv.setTextColor(activity.getResources().getColor(R.color.app_fontBlackColor));
    }

    private void othersIsSelected() {
        addressType = globalVariables.ADDRESS_TYPE_OTHERS;
       // home_tv.setBackground(activity.getResources().getDrawable(R.drawable.bg_button_color_border_deep_grey_curved));
      //  work_tv.setBackground(activity.getResources().getDrawable(R.drawable.bg_button_color_border_deep_grey_curved));
       // others_tv.setBackground(activity.getResources().getDrawable(R.drawable.bg_button_color_accent_filled_half_border_grey_curved));

        home_tv.setTextColor(activity.getResources().getColor(R.color.app_fontBlackColor));
        work_tv.setTextColor(activity.getResources().getColor(R.color.app_fontBlackColor));
        others_tv.setTextColor(activity.getResources().getColor(R.color.app_fontBlackColor));
    }

    private void validateInput() {

/*
        if (context != null) {
           */
/* String
                    fName = first_name_etv.getText().toString().trim(),
                    lName = last_name_etv.getText().toString().trim(),
                    mobile = phone_number_etv.getText().toString().trim(),
                    pincode = pincode_etv.getText().toString().trim(),
                    landmark = landmark_etv.getText().toString().trim(),
                    city = city_town_etv.getText().toString().trim(),
                    state = state_etv.getText().toString().trim(),
                    mySelectedAddress = address_etv.getText().toString().trim();*//*


            if (mySelectedAddress.isEmpty()) {
                globalFunctions.displayMessaage(activity, mainView, getString(R.string.please_select_address));
            } */
/*else if (selectedCityId == null) {
                globalFunctions.displayMessaage(activity, mainView, getString(R.string.please_select_your_city));
            } *//*
 else {
                if (userAddressModel == null) {
                    userAddressModel = new UserAddressModel();
                }
                ProfileModel profileModel = globalFunctions.getProfile(context);
                if (profileModel != null) {
//                    userAddressModel.setPhone(profileModel.getMobileNumber());
                    userAddressModel.setEmail(profileModel.getEmailId());
//                    userAddressModel.setFullName(profileModel.getFirstName()+" "+profileModel.getLastName());
//                    userAddressModel.setCommNumber(profileModel.getCountryCode()+" "+profileModel.getMobileNumber());
//                    userAddressModel.setPhone(profileModel.getCountryCode()+""+profileModel.getMobileNumber());
                }
                if (fromPage != null) {
                    if (fromPage.equalsIgnoreCase(globalVariables.FROM_ADRRESS_LISTING_PAGE)) {
                        //add address...

//                        userAddressModel.setAddress(mySelectedAddress);
//                        userAddressModel.setLandmark(mySelectedAddress);
//                        userAddressModel.setLatitude("24.00");
//                        userAddressModel.setLongitude("84.45");

                        if (default_address_cb.isChecked()) {
                            userAddressModel.setType(globalVariables.ACTION_TYPE_INSERT_ADDRESS);
                        }

                        userAddressModel.setFirstName(fName);
                        userAddressModel.setLastName(lName);
                        userAddressModel.setAddress(mySelectedAddress);
                        userAddressModel.setNumber(mobile);
                        userAddressModel.setPincode(pincode);
                        userAddressModel.setCityId(selectedCityId);
                        userAddressModel.setAction(globalVariables.ACTION_TYPE_INSERT_ADDRESS);
                        userAddressModel.setAddressType(addressType);
                        if (selectedCityId != null) {
                            userAddressModel.setCityId(selectedCityId);
                        }
                        updateUserAddress(context, userAddressModel, true);
                    } else if (fromPage.equalsIgnoreCase(globalVariables.PROM_EDIT_ADDRESS_PAGE)) {
                        //update address....
                        if (default_address_cb.isChecked()) {
                            userAddressModel.setType(globalVariables.ACTION_TYPE_INSERT_ADDRESS);
                        }

                        userAddressModel.setFirstName(fName);
                        userAddressModel.setLastName(lName);
                        userAddressModel.setAddress(mySelectedAddress);
                        userAddressModel.setNumber(mobile);
                        userAddressModel.setPincode(pincode);
                        userAddressModel.setCityId(selectedCityId);
                        userAddressModel.setAction(globalVariables.ACTION_TYPE_UPDATE_ADDRESS);
                        userAddressModel.setAddressType(addressType);

                        userAddressModel.setId(selectedAddressId);
//                        userAddressModel.setCityId(selectedCityId);
                        updateUserAddress(context, userAddressModel, false);
                    }
                }
            }
        }
*/
    }

/*
    private void updateUserAddress(final Context context, final UserAddressModel userAddressModel, boolean isAddAddress) {
        GlobalFunctions.showProgress(activity, context.getString(R.string.loading));
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.addUserAddress(context, userAddressModel, new ServerResponseInterface() {
            @Override
            public void OnSuccessFromServer(Object arg0) {
                GlobalFunctions.hideProgress();
                Log.d(TAG, "Response : " + arg0.toString());
                StatusResponseModel model = (StatusResponseModel) arg0;
                validateOutput(model);
            }

            @Override
            public void OnFailureFromServer(String msg) {
                // hideLoading();
                GlobalFunctions.hideProgress();
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Failure : " + msg);
            }

            @Override
            public void OnError(String msg) {
                //hideLoading();
                GlobalFunctions.hideProgress();
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Error : " + msg);
            }
        }, "UpdateUserAddress");
    }
*/

/*
    private void validateOutput(Object model) {
        if (model instanceof StatusResponseModel) {
            StatusResponseModel statusModel = (StatusResponseModel) model;
            GlobalFunctions.displayMessaage(activity, mainView, statusModel.getResponse());
            if (statusModel.isStatus()) {
                */
/*closeThisActivity();
                if (fromPage != null) {
                    if (fromPage.equalsIgnoreCase(globalVariables.PROM_EDIT_ADDRESS_PAGE)) {

//                        setResult(true);
                        //update or add address....
                        *//*
*/
/*UserAddressListActivity.closeThisActivity();
                        Intent intent = new Intent(SearchPlaceOnMapActivity.this, UserAddressListActivity.class);
                        activity.startActivity(intent);*//*
*/
/*
//                        closeThisActivity();
                    }
                }*//*

            }
        }
    }
*/

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        imageParentWidth = mMarkerParentView.getWidth();
        imageParentHeight = mMarkerParentView.getHeight();
        imageHeight = mMarkerImageView.getHeight();

        centerX = imageParentWidth / 2;
        centerY = (imageParentHeight / 2) + (imageHeight / 2);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

        }
    }

    private void initializeMap() {
        if (mMap == null) {
            mCustomMapFragment = ((CustomMapFragment) getFragmentManager().findFragmentById(R.id.map));
            mCustomMapFragment.setOnDragListener(this);
            mCustomMapFragment.getMapAsync(this);
            if (mMap == null) {
                Log.d(TAG, "Sorry! unable to create maps");
            }
        }
    }

    public void setLatLng(double latitude, double longitude) {
        if (mAddress == null) {
            mAddress = new AddressModel();
        }
        mAddress.setLatitude(latitude);
        mAddress.setLongitude(longitude);
    }

    public LatLng getLatLng() {
        if (mAddress != null) {
            return new LatLng(mAddress.getLatitude(), mAddress.getLongitude());
        }
        return null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMyLocationButtonClickListener(this);
        enableMyLocation();
        updateLocation(getLatLng(), true);
    }

    @SuppressLint("MissingPermission")
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
//            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
//                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        setLatLng(mMap.getCameraPosition().target.latitude, mMap.getCameraPosition().target.longitude);
        updateLocation(getLatLng(), true);
        return false;
    }

    @Override
    protected void onResume() {
        setTitle(getString(R.string.add_address), 0, 0);
        super.onResume();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }


    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (!miSConnectedDone) {
            miSConnectedDone = true;
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
              /*  try {
                    @SuppressLint("MissingPermission") Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                    double lat = lastLocation.getLatitude(), lon = lastLocation.getLongitude();
                    setLatLng(lat, lon);
                    updateLocation(getLatLng(), true);
                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                }*/
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    public void GetLatLon() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSION_ACCESS_COARSE_LOCATION);
        } else {
           /* try {
                @SuppressLint("MissingPermission") Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                double lat = lastLocation.getLatitude(), lon = lastLocation.getLongitude();
                setLatLng(lat, lon);
            } catch (Exception e) {
            }
*/
            if (sLocationEnabled(this)) {
               // googleApiClient = new GoogleApiClient.Builder(this, this, this).addApi(LocationServices.API).build();
                googleApiClient.connect();
            } else {
                showSettingsAlert();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        if (googleApiClient != null) {
            googleApiClient.disconnect();
        }
        super.onStop();
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(SearchPlaceOnMapActivity.this);

        // Setting Dialog Title
        alertDialog.setTitle("GPS settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.delete);

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                SearchPlaceOnMapActivity.this.startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    public boolean sLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF;
        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }

    private void setResult(boolean isSuccess) {
        Intent intent = new Intent();
        intent.putExtra(BUNDLE_SEARCH_PLACE_ON_MAP_ACTIVITY_ADDRESS_MODEL, userAddressModel);
        if (isSuccess) setResult(RESULT_OK, intent);
        else setResult(RESULT_CANCELED, intent);
        closeThisActivity();
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
        restoreToolbar();
    }

    private static void restoreToolbar() {
        //toolbar = (Toolbar) findViewById(R.id.tool_bar);
        Log.d(TAG, "Restore Tool Bar");
        if (actionBar != null) {
            Log.d(TAG, "Restore Action Bar not Null");
            Log.d(TAG, "Title : " + mTitle);
            if (titleResourseID != 0) {
//                toolbar_logo.setVisibility(View.VISIBLE);
                toolbar_title.setVisibility(View.VISIBLE);
//                toolbar_logo.setImageResource(titleResourseID);
            } else {
//                toolbar_logo.setVisibility(View.GONE);
                toolbar_title.setVisibility(View.VISIBLE);
                toolbar_title.setText(mTitle);
            }
            if (mResourceID != 0) toolbar.setBackgroundResource(mResourceID);
            //actionBar.setTitle("");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       /* menu.clear();
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_nothing, menu);
        this.menu = menu;*/

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
       /* menu.clear();
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_nothing, menu);
        this.menu = menu;*/

        return true;
    }

    public void setOptionsMenuVisiblity(boolean showMenu) {
        if (menu == null)
            return;
        //menu.setGroupVisible(R.id.menu, showMenu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                break;
        }*/
        return false;
    }

    @Override
    public void onDrag(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            Projection projection = (mMap != null && mMap.getProjection() != null) ? mMap.getProjection() : null;
            if (projection != null) {
                LatLng centerLatLng = projection.fromScreenLocation(new Point(
                        centerX, centerY));
                updateLocation(centerLatLng, false);
            }
        }
    }

    private void updateLocation(LatLng centerLatLng, boolean isCameraZoom) {
        if (centerLatLng != null) {
            setLatLng(centerLatLng.latitude, centerLatLng.longitude);

            if (isCameraZoom) {
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(centerLatLng, CAMERA_ZOOM_LEVEL_FOR_MAP);
                if (mMap != null)
                    mMap.animateCamera(cameraUpdate);
                /*All Zooming camer is an showcase so we dont want to show the suggestion again to stop the loop of Functionality.*/
                //mAutocompleteView.setOnItemClickListener(null);
            }

            Address address = globalFunctions.getAddressfromLatLng(activity, centerLatLng.latitude, centerLatLng.longitude);

            if (address != null) {
                mAddress = globalFunctions.getAddressModelFromAddress(address);
            }
            String completeAddress = null;
            if (mAddress.getAddress() != null) {
                completeAddress = mAddress.getAddress();
            } else {
                completeAddress = globalFunctions.getAddressTextFromModelExcludingNameAndAddresswithComma(context, mAddress);
            }

            if (completeAddress != null) {
                //mAutocompleteView.setOnItemClickListener(null);

                mAutocompleteView.setText(completeAddress);
                address_etv.setText(completeAddress);
                pincode_etv.setText(mAddress.getPincode());
                landmark_etv.setText(mAddress.getLandmark());
                city_town_etv.setText(mAddress.getCity());
                state_etv.setText(mAddress.getState());

                mAutocompleteView.dismissDropDown();
                //mAutocompleteView.setOnItemClickListener(mAutocompleteClickListener);

                if (userAddressModel == null) {
                    userAddressModel = new UserAddressModel();
                }
                userAddressModel.setAddress(mAddress.getAddress());
                userAddressModel.setLatitude(mAddress.getLatitude() + "");
                userAddressModel.setLongitude(mAddress.getLongitude() + "");
                userAddressModel.setLandmark(mAddress.getLandmark());
                userAddressModel.setPincode(mAddress.getPincode());
                userAddressModel.setCityName(mAddress.getCity());
            }
        }
    }

    @Override
    public void onBackPressed() {
        setResult(false);
    }

    public static void closeThisActivity() {
        if (activity != null) {
            activity.finish();
        }
    }

    @Override
    public void onUpdateMapAfterUserInterection() {

    }

    public void startLocationService() {
        activity.startService(locationintent);
    }

    public void stopLocationService() {
        activity.stopService(locationintent);
    }

    @Override
    public void OnLocationFetch(Location location) {
//        setCurrentLatLng();
        setLatLng(location.getLatitude(), location.getLongitude());

    }

    @Override
    public void OnProviderDisabled(String provider) {

    }

    @Override
    public void OnProviderEnabled(String provider) {

    }

    @Override
    public void OnLocationFailure(String msg) {

    }
}
