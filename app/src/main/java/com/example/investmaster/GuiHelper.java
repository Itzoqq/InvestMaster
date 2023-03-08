package com.example.investmaster;

import android.icu.text.DecimalFormat;
import android.icu.text.DecimalFormatSymbols;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class GuiHelper {

    static DecimalFormat formattedDisplay = new DecimalFormat("#,##0.00");

    public static void updateBalanceDisplay(Float balance, TextView balanceDisplay) {
        String balanceString = formattedDisplay.format(balance);
        balanceDisplay.setText("$" + balanceString);
    }

    public static void updateGainPerClickDisplay(Float gainPerClick, Button clickToGainButton) {
        String gainPerClickDisplay = String.format("%.2f", gainPerClick);
        clickToGainButton.setText("Click to gain\n" + "$" + gainPerClickDisplay);
    }

    public static void updateUpgradeCostDisplay(Float upgradeCost, Button gainUpgradeButton) {
        String upgradeCostDisplay = String.format("%.2f", upgradeCost);
        gainUpgradeButton.setText("Upgrade cost: $" + upgradeCostDisplay);
    }

    public static void updateIncomeDisplay(Float income, TextView incomeDisplay) {
        String incomeString = formattedDisplay.format(income);
        incomeDisplay.setText("Income: $" + incomeString);
    }

    public static void swapUpgradeButtonAndIncomeDisplay(Integer upgradeLevelCounter, Pair<Float, Float>[] pairsGainAndUpgradeCost, Button gainUpgradeButton, TextView incomeDisplay) {
        if (upgradeLevelCounter == pairsGainAndUpgradeCost.length - 1) {
            gainUpgradeButton.setVisibility(View.GONE);
            incomeDisplay.setVisibility(View.VISIBLE);
        }
    }

    public static void checkToDisableGainUpgradeButton(Float balance, Float upgradeCost, Button gainUpgradeButton) {
        if (balance >= upgradeCost) {
            gainUpgradeButton.setEnabled(true);
        } else {
            gainUpgradeButton.setEnabled(false);
        }
    }

    public static void enableButtonIfEnoughMoney(Float balance, Float priceOfBusiness, Button button) {
        if(balance >= priceOfBusiness) {
            button.setEnabled(true);
        } else {
            button.setEnabled(false);
        }
    }
}
