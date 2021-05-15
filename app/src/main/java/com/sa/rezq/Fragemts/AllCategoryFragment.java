package com.sa.rezq.Fragemts;

import android.content.Intent;
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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sa.rezq.Activity.MainActivity;
import com.sa.rezq.Models.CategoryModelClass;
import com.sa.rezq.R;
import com.sa.rezq.adapter.CategoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class AllCategoryFragment extends Fragment {
    View view;
    ArrayList<String> categoryList;
    private List<CategoryModelClass> movieList = new ArrayList<>();
    private ExtraAdapter mAdapter;
    CategoryAdapter adapter;
    RecyclerView recyclerView;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_recyclerview_category, container, false);

        categoryList = new ArrayList<>();

        CategoryModelClass movie = new CategoryModelClass("FOOD");
        movieList.add(movie);

        movie = new CategoryModelClass("BAR");
        movieList.add(movie);


        // set up the RecyclerView
        recyclerView = view.findViewById(R.id.categoryRecyclerview);

        mAdapter = new ExtraAdapter(movieList);
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
        MainActivity.ivHome.setText(getString(R.string.popular_category));
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


    //*Recyclerview Adapter*//


    public class ExtraAdapter extends RecyclerView.Adapter<ExtraAdapter.MyViewHolder> {

        private List<CategoryModelClass> moviesList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView title;
            RelativeLayout mainItemClick;

            public MyViewHolder(View view) {
                super(view);
                title = (TextView) view.findViewById(R.id.tvFood);
                mainItemClick = view.findViewById(R.id.mainlnr);
            }
        }


        public ExtraAdapter(List<CategoryModelClass> moviesList) {
            this.moviesList = moviesList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_allpopular_category_view_data, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            CategoryModelClass movie = moviesList.get(position);
          //  holder.title.setText(movie.getCategory_name());
            holder.mainItemClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replaceFragmentWithAnimation(new FoodAndOffersFragments());


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
}
