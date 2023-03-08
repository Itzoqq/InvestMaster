package com.example.investmaster.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.investmaster.FragmentHelper;
import com.example.investmaster.GuiHelper;
import com.example.investmaster.LoadHelper;
import com.example.investmaster.SaveHelper;
import com.example.investmaster.R;

public class StartBusinessFragment extends Fragment {

    TextView balanceDisplay, incomeDisplay;
    Button openShopsButton;
    Button openLocalShopButton, openChainOfShopsButton;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    Float balance;
    Float income;

    Float localShopCost = 10000.00F;
    Float localShopIncome = 500.00F;
    Float chainOfShopsCost = 150000.00F;

    public StartBusinessFragment() {
    }


    public static StartBusinessFragment newInstance(String param1, String param2) {
        StartBusinessFragment fragment = new StartBusinessFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_start_business, container, false);

        balanceDisplay = rootView.findViewById(R.id.balanceDisplay);
        incomeDisplay = rootView.findViewById(R.id.incomeDisplay);
        openShopsButton = rootView.findViewById(R.id.openShopsButton);
        openLocalShopButton = rootView.findViewById(R.id.openLocalShopButton);
        openChainOfShopsButton = rootView.findViewById(R.id.openChainOfShopsButton);

        loadProgress();
        updateUI();


        openLocalShopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                balance -= localShopCost;
                income += localShopIncome;
                SaveHelper.saveBalance(getActivity(), balance);
                SaveHelper.saveIncome(getActivity(), income);
                updateUI();
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadProgress();
        updateUI();
    }

    @Override
    public void onPause() {
        super.onPause();
        saveProgress();
    }

    public void updateUI() {
        GuiHelper.updateBalanceDisplay(balance, balanceDisplay);
        GuiHelper.updateIncomeDisplay(income, incomeDisplay);
        GuiHelper.enableButtonIfEnoughMoney(balance,localShopCost, openLocalShopButton);
        GuiHelper.enableButtonIfEnoughMoney(balance,chainOfShopsCost, openChainOfShopsButton);
    }

    public void loadProgress() {
        balance = LoadHelper.loadBalance(getActivity());
        income = LoadHelper.loadIncome(getActivity());
    }

    public void saveProgress() {
        SaveHelper.saveBalance(getActivity(), balance);
        SaveHelper.saveIncome(getActivity(), income);
    }

}