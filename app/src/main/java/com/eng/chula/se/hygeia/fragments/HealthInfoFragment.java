package com.eng.chula.se.hygeia.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eng.chula.se.hygeia.R;

/*
 * Health Info Fragment
 * Suttipong.k 17/04/2019
 * */

public class HealthInfoFragment extends Fragment {

    private TextView textViewWeight, textViewHeight, textViewDiseaseList, textViewAllergicList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.health_info_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textViewWeight = (TextView) view.findViewById(R.id.textViewWeight);
        textViewHeight = (TextView) view.findViewById(R.id.textViewHeight);
        textViewDiseaseList = (TextView) view.findViewById(R.id.textViewDiseaseList);
        textViewAllergicList = (TextView) view.findViewById(R.id.textViewAllergicList);

        //textViewEmail.setText(SharedPrefManager.getInstance(getActivity()).getUser().getEmail());
        //textViewName.setText(SharedPrefManager.getInstance(getActivity()).getUser().getName());
        //textViewSchool.setText(SharedPrefManager.getInstance(getActivity()).getUser().getSchool());
    }
}
