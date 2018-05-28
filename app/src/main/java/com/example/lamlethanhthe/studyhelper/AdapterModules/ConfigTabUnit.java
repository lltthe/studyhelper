package com.example.lamlethanhthe.studyhelper.AdapterModules;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lamlethanhthe.studyhelper.ConfigActivity;
import com.example.lamlethanhthe.studyhelper.DataModules.DataManager;
import com.example.lamlethanhthe.studyhelper.DataModules.Unit;
import com.example.lamlethanhthe.studyhelper.R;

import java.util.ArrayList;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class ConfigTabUnit extends Fragment {
    EditText search;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_config_unit, container, false);

        final DataManager dat = new DataManager(inflater.getContext());
        ArrayList<Unit> units = dat.getUnits();

        final UnitAdapter unitAdapter = new UnitAdapter(inflater.getContext(), units);
        final ListView lv = (ListView)view.findViewById(R.id.listUnit);
        lv.setAdapter(unitAdapter);

        search = (EditText)view.findViewById(R.id.edtSearchUnit);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                unitAdapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Unit tmp = (Unit) lv.getItemAtPosition(position);
                if (tmp.getId() < 3) {
                    dat.setUnitChoice(tmp.getId());
                    ((ConfigActivity) getActivity()).updateHosts(tmp.getId());
                } else {
                    Toast.makeText(parent.getContext(), "Chưa hỗ trợ đủ thông tin của 63 cụm thi\nXin thành thật xin lỗi vì sự bất tiện này", Toast.LENGTH_LONG).show();
                }
            }
        });

        Button speech = (Button)view.findViewById(R.id.btnSpeech);
        speech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "vi-VN");
                try {
                    startActivityForResult(intent, 101);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null && resultCode == RESULT_OK)
            if (requestCode == 101) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                Toast.makeText(getContext(), result.get(0), Toast.LENGTH_SHORT).show();
                search.setText(result.get(0));
            }
    }
}
