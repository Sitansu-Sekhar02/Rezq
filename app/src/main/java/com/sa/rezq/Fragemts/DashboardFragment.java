package com.sa.rezq.Fragemts;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.sa.rezq.R;
import com.sa.rezq.adapter.CardPagerAdapter;
import com.sa.rezq.extra.AppSettings;

import java.util.Timer;
import java.util.TimerTask;

public class DashboardFragment extends Fragment {
    ViewPager mViewPager;
    Timer timer;
    TabLayout tabLayout;
    int currentPage = 0;


    //SearchView searchView;
    Dialog dialog;
    View view;

    //long
    final long DELAY_MS = 1000;
    final long PERIOD_MS = 2000;

    //Images
    private static final int[] mResources = {
            R.drawable.slider_1,
            R.drawable.slider_1,
            R.drawable.slider_1,

    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home, container, false);

        mViewPager = view.findViewById(R.id.myviewpager);
        tabLayout = view.findViewById(R.id.tabDots);


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
    }
