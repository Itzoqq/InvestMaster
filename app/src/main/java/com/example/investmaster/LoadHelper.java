package com.example.investmaster;

import android.content.Context;
import android.content.SharedPreferences;

public class LoadHelper {


    public static Float loadBalance(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(FragmentHelper.SHARED_PREFS, Context.MODE_PRIVATE);
        return prefs.getFloat("balance", 0.00F);
    }

    public static Integer loadUpgradeLevelCounter(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(FragmentHelper.SHARED_PREFS, Context.MODE_PRIVATE);
        return prefs.getInt("upgradeLevelCounter", 0);
    }

    public static Float loadIncome(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(FragmentHelper.SHARED_PREFS, Context.MODE_PRIVATE);
        return prefs.getFloat("income", 0.00F);
    }

    public static Long loadTimeOfKill(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(FragmentHelper.SHARED_PREFS, Context.MODE_PRIVATE);
        return prefs.getLong("timeOfKill", 0);
    }

    public static Long loadTimeOfAlive(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(FragmentHelper.SHARED_PREFS, Context.MODE_PRIVATE);
        return prefs.getLong("timeOfAlive", 0);
    }
}
