package com.example.lamlethanhthe.studyhelper.AdapterModules;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.lamlethanhthe.studyhelper.ConfigActivity;
import com.example.lamlethanhthe.studyhelper.R;

public class ConfigTabOthers extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_config_others, container, false);

        final Switch swHS = (Switch)view.findViewById(R.id.switchHS);
        final CheckBox ckF = (CheckBox)view.findViewById(R.id.chkForeign);
        final CheckBox ckN = (CheckBox)view.findViewById(R.id.chkNatural);
        final CheckBox ckS = (CheckBox)view.findViewById(R.id.chkSocial);
        Button save = (Button)view.findViewById(R.id.saveConfig);

        swHS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked && !ckF.isChecked())
                        ckF.setChecked(true);
            }
        });

        ckF.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked && swHS.isChecked())
                    swHS.setChecked(false);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean[] args = new boolean[] {swHS.isChecked(), ckF.isChecked(), ckN.isChecked(), ckS.isChecked()};

                ((ConfigActivity)getActivity()).finalize(args);
            }
        });

        return view;
    }
}
