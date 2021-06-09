package com.sa.rezq.Fragemts;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.sa.rezq.Activity.MainActivity;
import com.sa.rezq.R;

public class CopyCouponFragment extends Fragment {

    View view;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.copy_offers_coupon, container, false);




        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                  //  replaceFragmentWithAnimation(new ReddemOfferFragment());
                        return true;
                    }
                }
                return false;
            }
        });



        MainActivity.ivHome.setVisibility(View.VISIBLE);
        MainActivity.ivHome.setText(getString(R.string.offers));
        MainActivity.iv_menu.setImageResource(R.drawable.ic_group_back);
        MainActivity.searchView.setVisibility(View.GONE);
        MainActivity.tvLocation.setVisibility(View.GONE);
        MainActivity.Crprofile.setVisibility(View.GONE);
        MainActivity.tvHeaderText.setVisibility(View.GONE);

        MainActivity.iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // replaceFragmentWithAnimation(new ReddemOfferFragment());

            }
        });







        return  view;
    }


    /*public void replaceFragmentWithAnimation(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }*/



}
