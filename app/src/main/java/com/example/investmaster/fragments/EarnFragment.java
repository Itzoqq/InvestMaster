package com.example.investmaster.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.core.util.Pair;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.investmaster.GuiHelper;
import com.example.investmaster.LoadHelper;
import com.example.investmaster.SaveHelper;
import com.example.investmaster.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class EarnFragment extends Fragment {

    TextView balanceDisplay, incomeDisplay;
    Button clickToGainButton, gainUpgradeButton;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;


    Pair<Float, Float>[] pairsGainAndUpgradeCost = new Pair[]{
            Pair.create(1.00F, 10.00F),
            Pair.create(2.00F, 50.00F),
            Pair.create(5.00F, 150.00F),
            Pair.create(10.00F, 500.00F),
            Pair.create(20.00F, 2000.00F),
            Pair.create(50.00F, 5500.00F)
    };

    Integer upgradeLevelCounter;
    Float balance;
    Float income;
    Float gainPerClick;
    Float upgradeCost;

    public EarnFragment() {
    }

    public static EarnFragment newInstance(String param1, String param2) {
        EarnFragment fragment = new EarnFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_earn, container, false);
        balanceDisplay = rootView.findViewById(R.id.balanceDisplay);
        incomeDisplay = rootView.findViewById(R.id.incomeDisplay);
        clickToGainButton = rootView.findViewById(R.id.clickToGainButton);
        gainUpgradeButton = rootView.findViewById(R.id.gainUpgradeButton);

        loadProgress();
        updateUI();

        clickToGainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                balance += gainPerClick;
                GuiHelper.updateBalanceDisplay(balance, balanceDisplay);

                if (balance >= upgradeCost) {
                    gainUpgradeButton.setEnabled(true);
                }

                SaveHelper.saveBalance(getActivity(), balance);
            }
        });

        gainUpgradeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                balance -= pairsGainAndUpgradeCost[upgradeLevelCounter].second;
                upgradeLevelCounter++;
                gainPerClick = pairsGainAndUpgradeCost[upgradeLevelCounter].first;
                upgradeCost = pairsGainAndUpgradeCost[upgradeLevelCounter].second;

                updateUI();
                saveProgress();
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadProgress();
        updateUI();

        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.navigationBar);
        bottomNavigationView.setSelectedItemId(R.id.navigation_earn);
    }

    @Override
    public void onPause() {
        super.onPause();
        saveProgress();
    }

    public void updateUI() {
        GuiHelper.updateBalanceDisplay(balance, balanceDisplay);
        GuiHelper.updateGainPerClickDisplay(gainPerClick, clickToGainButton);
        GuiHelper.updateUpgradeCostDisplay(upgradeCost, gainUpgradeButton);
        GuiHelper.updateIncomeDisplay(income, incomeDisplay);

        GuiHelper.enableButtonIfEnoughMoney(balance, upgradeCost, gainUpgradeButton);
        GuiHelper.swapUpgradeButtonAndIncomeDisplay(upgradeLevelCounter, pairsGainAndUpgradeCost, gainUpgradeButton, incomeDisplay);
    }

    public void loadProgress() {
        balance = LoadHelper.loadBalance(getActivity());
        upgradeLevelCounter = LoadHelper.loadUpgradeLevelCounter(getActivity());
        income = LoadHelper.loadIncome(getActivity());
        gainPerClick = pairsGainAndUpgradeCost[upgradeLevelCounter].first;
        upgradeCost = pairsGainAndUpgradeCost[upgradeLevelCounter].second;
    }

    public void saveProgress() {
        SaveHelper.saveBalance(getActivity(), balance);
        SaveHelper.saveUpgradeLevelCounter(getActivity(), upgradeLevelCounter);
        SaveHelper.saveIncome(getActivity(), income);
    }
    
}