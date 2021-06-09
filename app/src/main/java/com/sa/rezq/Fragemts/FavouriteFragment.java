package com.sa.rezq.Fragemts;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sa.rezq.Activity.MainActivity;
import com.sa.rezq.Models.FavouriteCategoryModel;
import com.sa.rezq.R;

import java.util.ArrayList;
import java.util.List;

public class FavouriteFragment extends Fragment {
    ArrayList<String> categoryList;
    private List<FavouriteCategoryModel> movieList = new ArrayList<>();
    private FavouriteAdapter mAdapter;
    RecyclerView recyclerView;


    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.favourite_fragment, container, false);

        categoryList = new ArrayList<>();


        FavouriteCategoryModel movie = new FavouriteCategoryModel("All");
        movieList.add(movie);

        movie = new FavouriteCategoryModel("Food");
        movieList.add(movie);


        // set up the RecyclerView
        recyclerView = view.findViewById(R.id.favouriteTitle);

        mAdapter = new FavouriteAdapter(movieList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false);
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
        MainActivity.ivHome.setText(getString(R.string.favourites));
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



        return  view;
    }
    /*public void replaceFragmentWithAnimation(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
*/
    //*RecyclerView Adapter*//
    public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.viewHolder> {

        private List<FavouriteCategoryModel> moviesList;

        public FavouriteAdapter(List<FavouriteCategoryModel> moviesList) {
            this.moviesList = moviesList;
        }

        @NonNull
        @Override
        public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favourite_single_datatitle, parent, false);
            return new viewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull viewHolder holder, int position) {


        }

        @Override
        public int getItemCount() {
            return moviesList.size();
        }

        public class viewHolder extends RecyclerView.ViewHolder {

            public viewHolder(@NonNull View itemView) {
                super(itemView);


            }
        }
    }
}
