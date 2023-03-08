package com.example.investmaster.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.investmaster.FragmentHelper;
import com.example.investmaster.SaveHelper;
import com.example.investmaster.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingsFragment extends Fragment {

    Button resetProgressButton;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public SettingsFragment() {
    }

    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        resetProgressButton = rootView.findViewById(R.id.resetProgressButton);

        resetProgressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showResetDialog();
            }
        });

        return rootView;
    }

    public void showResetDialog() {
        AlertDialog.Builder confirmation = new AlertDialog.Builder(getActivity());
        confirmation.setTitle("Reset application progress?");
        confirmation.setMessage("You won't be able to recover your data.\nAre you sure you want to proceed?");
        confirmation.setCancelable(true);

        confirmation.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SaveHelper.resetProgress(getActivity());
                FragmentHelper.loadEarnFragment(getParentFragmentManager());
            }
        });

        confirmation.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog dialog = confirmation.create();
        dialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.navigationBar);
        bottomNavigationView.setSelectedItemId(R.id.navigation_settings);
    }
}