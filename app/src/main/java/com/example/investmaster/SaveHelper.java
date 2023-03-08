package com.example.investmaster;

import android.content.Context;
import android.content.SharedPreferences;


public class SaveHelper {

    public static void saveBalance(Context context, Float balance) {
        SharedPreferences prefs = context.getSharedPreferences(FragmentHelper.SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor save = prefs.edit();

        save.putFloat("balance", balance);
        save.apply();
    }

    public static void saveIncome(Context context, Float income) {
        SharedPreferences prefs = context.getSharedPreferences(FragmentHelper.SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor save = prefs.edit();

        save.putFloat("income", income);
        save.apply();
    }

    public static void saveUpgradeLevelCounter(Context context, Integer upgradeLevelCounter) {
        SharedPreferences prefs = context.getSharedPreferences(FragmentHelper.SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor save = prefs.edit();

        save.putInt("upgradeLevelCounter", upgradeLevelCounter);
        save.apply();
    }

    public static void saveTimeOfKill(Context context, long timeOfKill) {
        SharedPreferences prefs = context.getSharedPreferences(FragmentHelper.SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor save = prefs.edit();

        save.putLong("timeOfKill", timeOfKill);
        save.apply();
    }

    public static void saveTimeOfAlive(Context context, long timeOfAlive){
        SharedPreferences prefs = context.getSharedPreferences(FragmentHelper.SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor save = prefs.edit();

        save.putLong("timeOfAlive", timeOfAlive);
        save.apply();
    }

    public static void resetProgress(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(FragmentHelper.SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor save = prefs.edit();


        save.clear();
        save.putFloat("balance", 100000.00F);
        save.apply();
    }

}
