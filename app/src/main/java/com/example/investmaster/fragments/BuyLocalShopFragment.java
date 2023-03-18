package com.example.investmaster.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.investmaster.FragmentHelper;
import com.example.investmaster.GuiHelper;
import com.example.investmaster.LoadHelper;
import com.example.investmaster.MainActivity;
import com.example.investmaster.R;
import com.example.investmaster.SaveHelper;

public class BuyLocalShopFragment extends Fragment {

    Button buyLocalShopButton;
    TextView balanceDisplay;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    Float balance;
    Float income;

    Float localShopCost = 10000.00F;
    Float localShopIncome = 500.00F;

    public BuyLocalShopFragment() {
        // Required empty public constructor
    }

    public static BuyLocalShopFragment newInstance(String param1, String param2) {
        BuyLocalShopFragment fragment = new BuyLocalShopFragment();
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
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_buy_local_shop, container, false);

        buyLocalShopButton = rootView.findViewById(R.id.buyLocalShopButton);
        balanceDisplay = rootView.findViewById(R.id.balanceDisplay);

        loadProgress();
        GuiHelper.updateBalanceDisplay(balance, balanceDisplay);

        buyLocalShopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                balance -= localShopCost;
                income += localShopIncome;
                SaveHelper.saveBalance(getActivity(), balance);
                SaveHelper.saveIncome(getActivity(), income);
                FragmentHelper.loadOwnedBusinessFragment(getParentFragmentManager());
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadProgress();
    }

    @Override
    public void onPause() {
        super.onPause();
        saveProgress();
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