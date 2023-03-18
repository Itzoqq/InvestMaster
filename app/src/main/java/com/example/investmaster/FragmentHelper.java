package com.example.investmaster;

import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.investmaster.fragments.BuyLocalShopFragment;
import com.example.investmaster.fragments.EarnFragment;
import com.example.investmaster.fragments.OwnedBusinessFragment;
import com.example.investmaster.fragments.SettingsFragment;
import com.example.investmaster.fragments.StartBusinessFragment;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class FragmentHelper {

    public final static String SHARED_PREFS = "sharedPrefs";

    public static void loadEarnFragment(FragmentManager fragmentManager) {
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if (!(fragment instanceof EarnFragment)) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_container, new EarnFragment());
            transaction.commit();
        }
    }

    public static void loadSettingsFragment(FragmentManager fragmentManager) {
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if (!(fragment instanceof SettingsFragment)) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_container, new SettingsFragment());
            transaction.commit();
        }
    }

    public static void loadOwnedBusinessFragment(FragmentManager fragmentManager) {
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if (!(fragment instanceof OwnedBusinessFragment)) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_container, new OwnedBusinessFragment());
            transaction.commit();
        }
    }

    public static void loadStartBusinessFragment(FragmentManager fragmentManager) {
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if (!(fragment instanceof StartBusinessFragment)) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_container, new StartBusinessFragment());
            transaction.commit();
        }
    }

    public static void loadBuyLocalShopFragment(FragmentManager fragmentManager) {
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if (!(fragment instanceof BuyLocalShopFragment)) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_container, new BuyLocalShopFragment());
            transaction.commit();
        }
    }

    public static void fetchFragmentAndMethod(FragmentManager fragmentManager) {
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment != null && fragment.getView() != null) {
                    TextView balanceDisplay = fragment.getView().findViewById(R.id.balanceDisplay);
                    if (balanceDisplay != null) {
                        GuiHelper.updateBalanceDisplay(LoadHelper.loadBalance(fragment.getActivity()), balanceDisplay);
                    }
                    try {
                        Method method = fragment.getClass().getMethod("loadProgress");
                        method.invoke(fragment);
                    } catch (NoSuchMethodException | IllegalAccessException |
                             InvocationTargetException e) {
                        // do nothing if the fragment does not have the method
                    }
                }
            }
        }
    }
}