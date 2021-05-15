package com.sa.rezq.Fragemts;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.sa.rezq.Models.CategoryModelClass;
import com.sa.rezq.Models.PopularPlacesModelClass;
import com.sa.rezq.Models.TrendingModelClass;
import com.sa.rezq.R;
import com.sa.rezq.adapter.CardPagerAdapter;
import com.sa.rezq.adapter.CategoryAdapter;
import com.sa.rezq.adapter.PopularPlacesAdapter;
import com.sa.rezq.extra.AppSettings;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class DashboardFragment extends Fragment {
    ViewPager mViewPager;
    Timer timer;
    TabLayout tabLayout;
    int currentPage = 0;
    TextView seeAll;
    CardView cardViewItem;


    //SearchView searchView;
    Dialog dialog;
    View view;

    //long
    final long DELAY_MS = 1000;
    final long PERIOD_MS = 2000;

    //Images for slider
    private static final int[] mResources = {
            R.drawable.slider_1,
            R.drawable.slider_1,
            R.drawable.slider_1,

    };


    ArrayList<CategoryModelClass> categoryList;
    ArrayList<TrendingModelClass> trendingList;
    ArrayList<PopularPlacesModelClass> popularList;


    RecyclerView CategoryRecyclerView, TrendingRecyclerView,PopularplacesRecyclerview;

    int[] icons = {R.drawable.food,R.drawable.ic_bar,R.drawable.flight,R.drawable.ic_cinema};
    String iconsName[] = {"FOOD", "BAR", "TRAVEL", "CINEMA"};



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home, container, false);

        mViewPager = view.findViewById(R.id.myviewpager);
        tabLayout = view.findViewById(R.id.tabDots);

        CategoryRecyclerView = view.findViewById(R.id.Rvcategory);
        //TrendingRecyclerView = view.findViewById(R.id.Rvtrending);
        PopularplacesRecyclerview =view.findViewById(R.id.Rvplaces);
        seeAll=view.findViewById(R.id.TvseeAllCategory);
        cardViewItem=view.findViewById(R.id.cardItem);

        cardViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragmentWithAnimation(new RestaurantAndOfferDetailsFragment());

            }
        });



        seeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragmentWithAnimation(new AllCategoryFragment());

            }
        });

        categoryList = new ArrayList<>();
        trendingList = new ArrayList<>();
        popularList = new ArrayList<>();


        //set the adapter and recyclerview items
        CategoryItems();
        TrendingItems();
        PopularPlaces();



        //Page
        AppSettings.fromPage = "1";
        //setTablayout
        tabLayout.setupWithViewPager(mViewPager, true);
        final int NUM_PAGES = 4;
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES - 1) {
                    currentPage = 0;
                }
                mViewPager.setCurrentItem(currentPage++, true);
            }
        };
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);

        CardPagerAdapter mCardPagerAdapter = new CardPagerAdapter(getActivity(), mResources) {
            @Override
            protected void onCategoryClick(View view, String str) {

            }
        };
        mViewPager.setAdapter(mCardPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setClipToPadding(false);
        mViewPager.setCurrentItem(1, true);
        mViewPager.setPageMargin(10);

        return view;
    }

    private void PopularPlaces() {
        LinearLayoutManager linearLayoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        PopularplacesRecyclerview.setLayoutManager(linearLayoutManager);
        for (int i = 0; i < icons.length; i++) {
            PopularPlacesModelClass itemModel = new PopularPlacesModelClass();
            itemModel.setCategory_place_name(iconsName[i]);
            popularList.add(itemModel);
        }

        PopularPlacesAdapter adapter = new PopularPlacesAdapter(getActivity(), popularList);
        PopularplacesRecyclerview.setAdapter(adapter);

    }

    private void TrendingItems() {
        /*LinearLayoutManager linearLayoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        TrendingRecyclerView.setLayoutManager(linearLayoutManager);
        for (int i = 0; i < icons.length; i++) {
            TrendingModelClass itemModel = new TrendingModelClass();
            itemModel.setCategory_image(trending_images[i]);
            itemModel.setCategory_place_name(image_names[i]);
            trendingList.add(itemModel);
        }

        CategoryAdapter adapter = new CategoryAdapter(getActivity(), categoryList);
        CategoryRecyclerView.setAdapter(adapter);*/
    }

    private void CategoryItems() {

        LinearLayoutManager linearLayoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        CategoryRecyclerView.setLayoutManager(linearLayoutManager);
        for (int i = 0; i < icons.length; i++) {
            CategoryModelClass itemModel = new CategoryModelClass();
            itemModel.setCategory_image(icons[i]);
            itemModel.setCategory_name(iconsName[i]);
            categoryList.add(itemModel);
        }

        CategoryAdapter adapter = new CategoryAdapter(getActivity(), categoryList);
        CategoryRecyclerView.setAdapter(adapter);
    }
    public void replaceFragmentWithAnimation(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }


}
