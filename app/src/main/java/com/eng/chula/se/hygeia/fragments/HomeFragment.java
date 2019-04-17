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
import com.eng.chula.se.hygeia.storage.SharedPrefManager;

public class HomeFragment extends Fragment {

    private TextView textViewEmail, textViewName, textViewSchool;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textViewEmail = (TextView) view.findViewById(R.id.textViewEmail);
        textViewName = (TextView) view.findViewById(R.id.textViewName);
        textViewSchool = (TextView) view.findViewById(R.id.textViewSchool);

        textViewEmail.setText(SharedPrefManager.getInstance(getActivity()).getUser().getEmail());
        textViewName.setText(SharedPrefManager.getInstance(getActivity()).getUser().getName());
        textViewSchool.setText(SharedPrefManager.getInstance(getActivity()).getUser().getSchool());
    }
}
