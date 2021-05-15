package com.sa.rezq.Fragemts;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sa.rezq.Activity.MainActivity;
import com.sa.rezq.Models.PopularPlacesModelClass;
import com.sa.rezq.R;
import com.sa.rezq.adapter.PopularPlacesAdapter;

import java.util.ArrayList;

public class FoodAndOffersFragments extends Fragment {

    RecyclerView RlPlaces;
    RecyclerView Rlfoods;
    RecyclerView Rlvisited;
    ArrayList<PopularPlacesModelClass> popularList;



    String iconsName[] = {"50% off", "15% off", "Buy 1 get 1",};


    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_food_n_offers, container, false);


        RlPlaces=view.findViewById(R.id.recyclerPlaces);
        Rlfoods=view.findViewById(R.id.recyclerPopularFoods);
        Rlvisited=view.findViewById(R.id.recyclerMostVisited);

        popularList = new ArrayList<>();

        PopularPlaces();
        PopularFoods();
        MostVisited();






        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        replaceFragmentWithAnimation(new AllCategoryFragment());


                        return true;
                    }
                }
                return false;
            }
        });

        MainActivity.ivHome.setVisibility(View.VISIBLE);
        MainActivity.ivHome.setText(getString(R.string.food));
        MainActivity.iv_menu.setImageResource(R.drawable.ic_group_back);
        MainActivity.searchView.setVisibility(View.GONE);
        MainActivity.tvLocation.setVisibility(View.GONE);
        MainActivity.Crprofile.setVisibility(View.GONE);
        MainActivity.tvHeaderText.setVisibility(View.GONE);

        MainActivity.iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragmentWithAnimation(new AllCategoryFragment());
            }
        });



        return  view;
    }

    private void MostVisited() {
        LinearLayoutManager linearLayoutManager
                = new LinearLayoutManager(getActivity());
        Rlvisited.setLayoutManager(linearLayoutManager);
        for (int i = 0; i < iconsName.length; i++) {

            PopularPlacesModelClass itemModel = new PopularPlacesModelClass();
            itemModel.setCategory_place_name(iconsName[i]);
            popularList.add(itemModel);
        }
        PlacesNearyouAdapter adapter = new PlacesNearyouAdapter(getActivity(), popularList);
        Rlvisited.setAdapter(adapter);
    }

    private void PopularFoods() {
        LinearLayoutManager linearLayoutManager
                = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false);
        Rlfoods.setLayoutManager(linearLayoutManager);
        for (int i = 0; i < iconsName.length; i++) {

            PopularPlacesModelClass itemModel = new PopularPlacesModelClass();
            itemModel.setCategory_place_name(iconsName[i]);
            popularList.add(itemModel);
        }
        PopularPlacesAdapter adapter = new PopularPlacesAdapter(getActivity(), popularList);
        Rlfoods.setAdapter(adapter);

    }

    private void PopularPlaces() {

        LinearLayoutManager linearLayoutManager
                = new LinearLayoutManager(getActivity());
        RlPlaces.setLayoutManager(linearLayoutManager);
        for (int i = 0; i < iconsName.length; i++) {

            PopularPlacesModelClass itemModel = new PopularPlacesModelClass();
            itemModel.setCategory_place_name(iconsName[i]);
            popularList.add(itemModel);
        }
        PlacesNearyouAdapter adapter = new PlacesNearyouAdapter(getActivity(), popularList);
        RlPlaces.setAdapter(adapter);

    }

    public void replaceFragmentWithAnimation(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }


    //*RecyclerView Adapter*//
    public class PlacesNearyouAdapter extends RecyclerView.Adapter<PlacesNearyouAdapter.viewHolder> {

        Context context;
        ArrayList<PopularPlacesModelClass> arrayList;

        public PlacesNearyouAdapter(Context context, ArrayList<PopularPlacesModelClass> arrayList) {
            this.context = context;
            this.arrayList = arrayList;
        }


        @NonNull
        @Override
        public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.places_nearyou_listdata, parent, false);
            return new PlacesNearyouAdapter.viewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull viewHolder holder, int position) {
            holder.percentOff.setText(arrayList.get(position).getCategory_percent_off());
            holder.mainlnr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // replaceFragmentWithAnimation(new FoodDetailsFragment());

                }
            });

        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public class viewHolder extends RecyclerView.ViewHolder {
            TextView percentOff;
            RelativeLayout mainlnr;

            public viewHolder(@NonNull View itemView) {
                super(itemView);
                percentOff = (TextView) itemView.findViewById(R.id.tvoff);
                mainlnr = itemView.findViewById(R.id.mainlnr);

            }
        }
    }
}
