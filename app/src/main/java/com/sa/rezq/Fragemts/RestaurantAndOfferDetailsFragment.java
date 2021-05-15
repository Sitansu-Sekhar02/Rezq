package com.sa.rezq.Fragemts;

import android.app.Dialog;
import android.content.Intent;
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

public class RestaurantAndOfferDetailsFragment extends Fragment {
    ArrayList<String> categoryList;
    private List<CategoryModelClass> movieList = new ArrayList<>();
    private LockedOfferAdapter mAdapter;
    CategoryAdapter adapter;
    RecyclerView recyclerView;
    View view;
    TextView AllLockedOffer;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_restaurant_and_offers, container, false);

        categoryList = new ArrayList<>();
        CategoryModelClass movie = new CategoryModelClass("FOOD");
        movieList.add(movie);

        movie = new CategoryModelClass("BAR");
        movieList.add(movie);
// set up the RecyclerView
        recyclerView = view.findViewById(R.id.Lockedofferrecylerview);

        mAdapter = new LockedOfferAdapter(movieList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


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

        AllLockedOffer=view.findViewById(R.id.TvseeLockedOffers);

        AllLockedOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // replaceFragmentWithAnimation(new OfferDetailsFragment());
                SeeAllLockedPopup();


            }
        });




        return  view;
    }

    private void SeeAllLockedPopup() {
        final Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.all_locked_offer_alert);
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
        RecyclerView rezqPlus=dialog.findViewById(R.id.recyclerRezqplus);
        RecyclerView rezqPrime=dialog.findViewById(R.id.recyclerPrimeOffer);

        mAdapter = new LockedOfferAdapter(movieList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rezqPlus.setLayoutManager(mLayoutManager);
        rezqPlus.setItemAnimator(new DefaultItemAnimator());
        rezqPlus.setAdapter(mAdapter);


    }

    public void replaceFragmentWithAnimation(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }


    //*Recyclerview Adapter*//
    public class LockedOfferAdapter extends RecyclerView.Adapter<LockedOfferAdapter.MyViewHolder> {

        private List<CategoryModelClass> moviesList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView title;
            RelativeLayout lockedOffer;

            public MyViewHolder(View view) {
                super(view);
                title = (TextView) view.findViewById(R.id.tvFood);
                lockedOffer=view.findViewById(R.id.lockedOffer);
            }
        }


        public LockedOfferAdapter(List<CategoryModelClass> moviesList) {
            this.moviesList = moviesList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.rezq_plus_offer_dataview, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            CategoryModelClass movie = moviesList.get(position);
            holder.lockedOffer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replaceFragmentWithAnimation(new OfferDetailsFragment());

                }
            });

        }

        @Override
        public int getItemCount() {
            return moviesList.size();
        }

        public void replaceFragmentWithAnimation(Fragment fragment) {
            FragmentTransaction transaction =getActivity().getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
            transaction.replace(R.id.fragment_container, fragment);
            transaction.commit();
        }
    }


    //*Recyclerview All locked offer list*//

    public class AllLockedList extends RecyclerView.Adapter<AllLockedList.MyViewHolder> {

        private List<CategoryModelClass> moviesList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView title;
            RelativeLayout lockedOffer;

            public MyViewHolder(View view) {
                super(view);
                title = (TextView) view.findViewById(R.id.tvFood);
                lockedOffer=view.findViewById(R.id.lockedOffer);
            }
        }


        public AllLockedList(List<CategoryModelClass> moviesList) {
            this.moviesList = moviesList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.rezq_plus_offer_dataview, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return moviesList.size();
        }

    }


}
