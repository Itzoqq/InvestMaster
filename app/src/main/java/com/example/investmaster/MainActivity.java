package com.example.investmaster;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.investmaster.fragments.EarnFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView navigationBar;

    public FragmentManager fragmentManager;
    public long timeOfKill;
    public long offlineTimeMillis;
    public Float offlineTimeMinutes;

    Float balance;
    Float income;

    private Handler mHandler;
    private Runnable mRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_main);

        /* Define elements */
        navigationBar = findViewById(R.id.navigationBar);

        /* Initial functions */
        navigationBar.setSelectedItemId(R.id.navigation_earn);
        fragmentManager = getSupportFragmentManager();
        FragmentHelper.loadEarnFragment(fragmentManager);


        //job za online income
        initHandlerAndRunnable();



        /* Events */
        navigationBar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_settings:
                        FragmentHelper.loadSettingsFragment(fragmentManager);
                        return true;
                    case R.id.navigation_wealth:
                        //ide infalteanje fragmenta
                        return true;
                    case R.id.navigation_earn:
                        FragmentHelper.loadEarnFragment(fragmentManager);
                        return true;
                    case R.id.navigation_ownedBusiness:
                        FragmentHelper.loadOwnedBusinessFragment(fragmentManager);
                        return true;
                    case R.id.navigation_stockAndCrypto:
                        //ide infalteanje fragmenta
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (LoadHelper.loadTimeOfKill(this) != 0) {
            offlineIncomeGain();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        timeOfKill = System.currentTimeMillis();
        SaveHelper.saveTimeOfKill(this, timeOfKill);
    }


    public void onlineIncomeGain() {
        // Retrieve the current balance from SharedPreferences
        balance = LoadHelper.loadBalance(getApplicationContext());
        income = LoadHelper.loadIncome(getApplicationContext());

        // Add the income amount to the current balance
        SaveHelper.saveBalance(this, balance + income);

        // Get the currently displayed fragment
        FragmentHelper.fetchFragmentAndMethod(fragmentManager);
    }

    public void initHandlerAndRunnable() {
        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                // Call the onlineIncomeGain() method to add the income to the balance and update the balance display
                onlineIncomeGain();

                // Schedule the next execution of this Runnable after one minute
                mHandler.postDelayed(this, 20 * 1000); // 60 seconds = 1 minute
            }
        };

        // Start the Runnable execution every minute
        mHandler.postDelayed(mRunnable, 20 * 1000);
    }

    public void offlineIncomeGain() {
        // Retrieve the current balance from SharedPreferences
        balance = LoadHelper.loadBalance(getApplicationContext());
        income = LoadHelper.loadIncome(getApplicationContext());

        // Calculate the offline time in minutes
        offlineTimeMillis = System.currentTimeMillis() - LoadHelper.loadTimeOfKill(this);
        offlineTimeMinutes = Float.valueOf(offlineTimeMillis) / 60000;

        // Calculate the offline income gain
        Float offlineIncomeGain = offlineTimeMinutes * income;

        // Add the offline income gain to the current balance
        SaveHelper.saveBalance(this, balance + offlineIncomeGain);

        // Get the currently displayed fragment
        FragmentHelper.fetchFragmentAndMethod(fragmentManager);

        System.out.println("OfflineTimeMillis: " + String.format("%d", offlineTimeMillis));
        System.out.println("offlineTimeMinutes: " + String.format("%.2f", offlineTimeMinutes));
        System.out.println("offlineIncomeGain: " + String.format("%.2f", offlineIncomeGain));
    }
}