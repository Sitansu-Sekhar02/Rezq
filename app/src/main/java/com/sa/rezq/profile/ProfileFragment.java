package com.sa.rezq.profile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.sa.rezq.R;
import com.sa.rezq.global.GlobalFunctions;
import com.sa.rezq.profile.activities.EditProfileActivity;
import com.sa.rezq.services.ServerResponseInterface;
import com.sa.rezq.services.ServicesMethodsManager;
import com.sa.rezq.services.model.ProfileMainModel;
import com.sa.rezq.services.model.ProfileMembershipModel;
import com.sa.rezq.services.model.ProfileModel;
import com.sa.rezq.services.model.StatusModel;
import com.sa.rezq.view.AlertDialog;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment {
    public static final String TAG ="ProfileFragment" ;
    public static final String BUNDLE_KEY_CITY_HOUSE_TYPE_FRAGMENT_ORDER_SUBMIT_MODEL = "BundleKeyCreateRideFragmentOrderSubmit";

    Activity activity;
    Context context;
    View mainView;
    TextView tv_userfullName, therapist_tv, update_button, email_ev,tv_membership_title;
    TextView first_name_etv, last_name_etv, mobile_etv, email_etv, password_etv, confirm_password_etv;
    ImageView profile_image;
    Button btn_update_profile;

    View view;
    ProfileModel detail = null;
    ProfileMembershipModel profileMembershipModel=null;

    public ProfileFragment() {
        setHasOptionsMenu( true );
    }

    public static Fragment newInstance() {
        Fragment fragment = new ProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putString( BUNDLE_KEY_CITY_HOUSE_TYPE_FRAGMENT_ORDER_SUBMIT_MODEL, null );
        fragment.setArguments( bundle );
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu( true );
        super.onCreate( savedInstanceState );
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView( inflater, container, savedInstanceState );
        view = inflater.inflate(R.layout.activity_my_profile, container, false);

        activity = getActivity();
        context = getActivity();

       // name_tv = ( TextView ) view.findViewById( R.id.name_tv );

        first_name_etv = view.findViewById( R.id.tv_firstname );
        last_name_etv =  view.findViewById( R.id.tv_lastName );
        mobile_etv =  view.findViewById( R.id.tv_mobileNumber );
        email_etv = view.findViewById( R.id.tv_emailId );
        tv_userfullName = view.findViewById( R.id.tv_userfullName );
        btn_update_profile = view.findViewById( R.id.btn_update_profile );
        tv_membership_title = view.findViewById( R.id.tv_membership_title );
        //email_ev = view.findViewById( R.id.email_ev );
     /*   password_etv = ( EditText ) view.findViewById( R.id.password_etv );
        confirm_password_etv = ( EditText ) view.findViewById( R.id.confirm_password_etv );*/

        profile_image = view.findViewById( R.id.ivProfimeImage );
        btn_update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                activity.startActivity(new Intent(activity, EditProfileActivity.class));

            }
        });


        getProfile();
        mainView=first_name_etv;



        return view;
    }

    private void getProfile() {

        GlobalFunctions.showProgress( context, getString( R.string.getting_profile ) );
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.getProfile( context, new ServerResponseInterface() {
            @Override
            public void OnSuccessFromServer(Object arg0) {
                GlobalFunctions.hideProgress();
                Log.d( TAG, "Response : " + arg0.toString() );
                setDetails( arg0);
            }

            @Override
            public void OnFailureFromServer(String msg) {
                GlobalFunctions.hideProgress();
                GlobalFunctions.displayMessaage( context, mainView, msg );
                Log.d( TAG, "Failure : " + msg );
                setDetails();
            }

            @Override
            public void OnError(String msg) {
                GlobalFunctions.hideProgress();
                GlobalFunctions.displayMessaage( context, mainView, msg );
                Log.d( TAG, "Error : " + msg );
                setDetails();
            }
        }, "Get Profile" );

    }

    private void setDetails() {
        if (context != null && isAdded()) {
            detail = GlobalFunctions.getProfile( context );
            setThisPage();
        }
    }

    private void setDetails(Object arg0) {
        ProfileMainModel profileMainModel = ( ProfileMainModel ) arg0;
        ProfileModel profileModel = profileMainModel.getProfileModel();
        profileMembershipModel=profileModel.getProfileMembershipModel();

        GlobalFunctions.setProfile( context, profileModel );
       // GlobalFunctions.setProfile( context, profileMembershipModel );
        if (context != null && isAdded()) {
            detail = GlobalFunctions.getProfile( context );
            setThisPage();
        }
       // ProfileModel model = ( ProfileModel ) profile;
    }

    private void setThisPage() {
        if (detail != null) {
            if (isAdded()) {
                //  setEditPage(false);

                Log.d( "heckDetail",""+ detail );

                String fullName = detail.getFirstName() + " " + detail.getLastName();
                tv_userfullName.setText( fullName );
                first_name_etv.setText( detail.getFirstName());
                last_name_etv.setText( detail.getLastName());
                mobile_etv.setText( detail.getPhone());
                email_etv.setText( detail.getEmail() );
                //tv_membership_title.setText(detail.);
                if (profileMembershipModel!=null){
                    if (GlobalFunctions.isNotNullValue(profileMembershipModel.getMembership_name())){
                        tv_membership_title.setText(profileMembershipModel.getMembership_name());
                    }
                }

                try {
                    if (detail.getProfileImg() != null || !detail.getProfileImg().equals( "null" ) || !detail.getProfileImg().equalsIgnoreCase( "" )) {
                        Picasso.with( context ).load( detail.getProfileImg() ).placeholder( R.drawable.ic_baseline_person_24 ).into( profile_image);
                    }
                } catch (Exception e) {
                }

            }
        }
    }

    @Override
    public void onResume() {
        /* getProfile();*/
        // MainActivity.setTitleResourseID(0);
        (( ProfileMainActivity ) activity).setTitle( getString( R.string.my_profile ), R.drawable.rezq_logo, 0 );
        super.onResume();
    }

    private void checkChangePasswordAfter(Object model) {
        if (model instanceof StatusModel) {
            StatusModel statusModel = ( StatusModel ) model;
//            GlobalFunctions.displayMessaage(context, mainView, statusModel.getMessage());
            if (statusModel.isStatus()) {
                showAlertDialog( context, statusModel.getMessage() );
            }
        }
    }

    private void showAlertDialog(final Context context, final String message) {
        final AlertDialog alertDialog = new AlertDialog( context );
        alertDialog.setCancelable( false );
        alertDialog.setIcon( R.drawable.rezq_logo );
        alertDialog.setTitle( getString( R.string.app_name ) );
        alertDialog.setMessage( message );
        alertDialog.setPositiveButton( getString( R.string.ok ), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                //logoutUser( context );
            }
        } );

        alertDialog.show();
    }

/*
    private void logoutUser(Context context) {
        GlobalFunctions.showProgress( context, getString( R.string.logingout ) );
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.logout( context, new ServerResponseInterface() {
            @Override
            public void OnSuccessFromServer(Object arg0) {
                GlobalFunctions.hideProgress();
                Log.d( TAG, "Response : " + arg0.toString() );
                validateOutput( arg0 );
            }

            @Override
            public void OnFailureFromServer(String msg) {
                GlobalFunctions.hideProgress();
                Toast.makeText( context, msg, Toast.LENGTH_SHORT ).show();
                //GlobalFunctions.displayMessaage(context, mainView, msg);
                Log.d( TAG, "Failure : " + msg );
            }

            @Override
            public void OnError(String msg) {
                GlobalFunctions.hideProgress();
                Toast.makeText( context, msg, Toast.LENGTH_SHORT ).show();
                //GlobalFunctions.displayMessaage(context, mainView, msg);
                Log.d( TAG, "Error : " + msg );
            }
        }, "Logout_User" );
    }
*/

    private void validateOutput(Object arg0) {
        if (arg0 instanceof StatusModel) {
            StatusModel statusModel = ( StatusModel ) arg0;
            GlobalFunctions.displayMessaage( context, mainView, statusModel.getMessage() );
            if (statusModel.isStatus()) {
                /*Logout success, Clear all cache and reload the home page*/

            }
//            GlobalFunctions.logoutApplication( context );
            // MainActivity.RestartEntireApp(context);
        }
    }


}
