package com.example.investmaster.fragments;

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
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class OwnedBusinessFragment extends Fragment {

    TextView balanceDisplay, incomeDisplay;
    Button startBusinessButton;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    Float balance;
    Float income;

    public OwnedBusinessFragment() {
    }

    public static OwnedBusinessFragment newInstance(String param1, String param2) {
        OwnedBusinessFragment fragment = new OwnedBusinessFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_owned_business, container, false);
        balanceDisplay = rootView.findViewById(R.id.balanceDisplay);
        incomeDisplay = rootView.findViewById(R.id.incomeDisplay);
        startBusinessButton = rootView.findViewById(R.id.startBusinessButton);

        loadProgress();
        updateUI();

        startBusinessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentHelper.loadStartBusinessFragment(getParentFragmentManager());
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
        bottomNavigationView.setSelectedItemId(R.id.navigation_ownedBusiness);
    }

    @Override
    public void onPause() {
        super.onPause();
        saveProgress();
    }

    public void updateUI() {
        GuiHelper.updateIncomeDisplay(income, incomeDisplay);
        GuiHelper.updateBalanceDisplay(balance, balanceDisplay);
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