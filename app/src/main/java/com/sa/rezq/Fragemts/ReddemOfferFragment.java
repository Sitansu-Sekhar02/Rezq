package com.sa.rezq.Fragemts;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.sa.rezq.Activity.MainActivity;
import com.sa.rezq.R;

public class ReddemOfferFragment extends Fragment {

    View view;
    TextView redeemOffer;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.redeem_offer_coupon, container, false);

        redeemOffer=view.findViewById(R.id.tvRedeemOffer);



        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                    // replaceFragmentWithAnimation(new OfferDetailsFragment());
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
              //  replaceFragmentWithAnimation(new OfferDetailsFragment());

            }
        });

        redeemOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RedeemOfferPopup();

            }
        });





        return  view;
    }

    private void RedeemOfferPopup() {
        final Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.reedem_offer_alert);
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

        Button btRedeem=dialog.findViewById(R.id.btnReedem);

        btRedeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                //replaceFragmentWithAnimation(new CopyCouponFragment() );
            }
        });


    }

   /* public void replaceFragmentWithAnimation(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }*/



}
