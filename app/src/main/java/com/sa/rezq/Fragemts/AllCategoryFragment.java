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
import com.sa.rezq.Models.CategoryModelClass;
import com.sa.rezq.R;
import com.sa.rezq.adapter.CategoryAdapter;
import com.sa.rezq.adapter.ExtraAdapter;

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

        MainActivity.tvHeaderText.setText("Popular Categories");
        MainActivity.iv_menu.setImageResource(R.drawable.ic_back);
        MainActivity.searchView.setVisibility(View.GONE);
        MainActivity.tvLocation.setVisibility(View.GONE);
        MainActivity.Crprofile.setVisibility(View.GONE);
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
}
