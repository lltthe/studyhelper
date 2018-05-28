package com.example.lamlethanhthe.studyhelper.AdapterModules;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.lamlethanhthe.studyhelper.ConfigActivity;
import com.example.lamlethanhthe.studyhelper.DataModules.DataManager;
import com.example.lamlethanhthe.studyhelper.DataModules.Host;
import com.example.lamlethanhthe.studyhelper.HostDetails;
import com.example.lamlethanhthe.studyhelper.R;

import java.util.ArrayList;

public class ConfigTabHost extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_config_host, container, false);

        final DataManager dat = new DataManager(inflater.getContext());
        ArrayList<Host> hosts = dat.getHosts();

        final HostAdapter hostAdapter = new HostAdapter(inflater.getContext(), hosts);
        final ListView lv = (ListView)view.findViewById(R.id.listHost);
        lv.setAdapter(hostAdapter);

        EditText search = (EditText)view.findViewById(R.id.edtSearchHost);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                hostAdapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Host tmp = (Host)lv.getItemAtPosition(position);
                ((ConfigActivity)getActivity()).updateHostChoice(tmp.getName());
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Host tmp = (Host)parent.getItemAtPosition(position);
                Intent intent = new Intent(getActivity(), HostDetails.class);
                intent.putExtra("host", tmp);
                startActivity(intent);
                return true;
            }
        });

        return view;
    }
}
