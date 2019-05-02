package com.eng.chula.se.hygeia.activities.DrugRequest;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.eng.chula.se.hygeia.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {

    private Button.OnClickListener onClickListener;

    public BlankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stepper_blank, container, false);

        Button button = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(onClickListener);
        return view;
    }

    public void setOnClickListener(Button.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
