package com.sa.rezq.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.sa.rezq.Fragemts.DashboardFragment;
import com.sa.rezq.R;
import com.sa.rezq.extra.Preferences;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {


    public static ImageView iv_menu, ivHome;

    DrawerLayout drawer;
    Dialog dialog;

    public static LinearLayout llmain;
    public static RelativeLayout rlsearchview;


    public static TextView tvHeaderText;

    MenuItem my_account;

    public static TextView marquee;

    public static NavigationView navigationView;
    public static BottomNavigationView bottom_nav;
    TextView logout;

    String status;

    Preferences preferences;

    public static ImageView ivCart;

    //other
    public static int backPressed = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Intialize();


        iv_menu.setOnClickListener(this);
        replaceFragmentWithAnimation(new DashboardFragment());


        // setvalue

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        if (navigationView != null) {
            Menu menu = navigationView.getMenu();
            if (preferences.get("login").equalsIgnoreCase("yes")) {

                menu.findItem(R.id.nav_logout).setTitle("Login");
            } else {
                menu.findItem(R.id.nav_logout).setTitle("Logout");
            }
            navigationView.setNavigationItemSelectedListener(this);

        }
    }

    private void Intialize() {
        llmain = findViewById(R.id.llmain);

        //textview
        tvHeaderText = findViewById(R.id.tvHeaderText);

        //imageview
        iv_menu = findViewById(R.id.iv_menu);
        preferences=new Preferences(this);

        //navigationview
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);

    }
    public void replaceFragmentWithAnimation(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_menu:
                drawer.openDrawer(Gravity.LEFT);
                v.setFocusableInTouchMode(true);
                v.requestFocus();
                v.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (event.getAction() == KeyEvent.ACTION_DOWN) {
                            if (keyCode == KeyEvent.KEYCODE_BACK) {
                                drawer.closeDrawer(Gravity.LEFT);

                                return true;
                            }
                        }
                        return false;
                    }
                });
                break;

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            replaceFragmentWithAnimation( new DashboardFragment());
        } else if (id == R.id.nav_recentCoupon) {

        }
        else if (id == R.id.nav_fav) {

        }
        else if (id == R.id.nav_membership) {

        }
        else if (id == R.id.nav_account) {
        }else if (id == R.id.nav_logout) {
            logout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;    }

    private void logout() {

    }

    @Override
    public void onBackPressed() {
        backPressed = backPressed + 1;
        if (backPressed == 1) {
            Toast.makeText(MainActivity.this, "Press back again to exit", Toast.LENGTH_SHORT).show();
            new CountDownTimer(5000, 1000) { // adjust the milli seconds here
                public void onTick(long millisUntilFinished) {
                }
                public void onFinish() { backPressed = 0;
                }
            }.start();
        }
        if (backPressed == 2) {
            backPressed = 0;
            finishAffinity();
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
}