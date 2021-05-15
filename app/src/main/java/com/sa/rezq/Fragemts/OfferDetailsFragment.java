package com.sa.rezq.Fragemts;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sa.rezq.Activity.MainActivity;
import com.sa.rezq.Models.CategoryModelClass;
import com.sa.rezq.R;
import com.sa.rezq.adapter.CategoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class OfferDetailsFragment extends Fragment {

    View view;
    Button btnUpgrade;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.offers_upgrade_coupon_reedem, container, false);

        btnUpgrade=view.findViewById(R.id.btnUpgrade);



        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        Intent i = new Intent(getActivity(), MainActivity.class);
                        startActivity(i);
                        getActivity().overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
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
                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
            }
        });

        btnUpgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               replaceFragmentWithAnimation(new ReddemOfferFragment());

            }
        });





        return  view;
    }
    public void replaceFragmentWithAnimation(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }



}
