package com.example.lamlethanhthe.studyhelper.AdapterModules;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lamlethanhthe.studyhelper.DataModules.Host;
import com.example.lamlethanhthe.studyhelper.R;

import java.util.ArrayList;

public class HostAdapter extends ArrayAdapter<Host> {
    private Context context;
    private ArrayList<Host> reserve;

    public HostAdapter(Context context, ArrayList<Host> hosts) {
        super(context, 0, hosts);
        this.context = context;
        reserve = new ArrayList<>();
        reserve.addAll(hosts);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_host, null);
        }

       Host curHost = getItem(position);

        try {
            ((TextView)convertView.findViewById(R.id.txtHostName)).setText(curHost.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;
    }

    public void filter(String s) {
        if (s != null) {
            clear();
            if (s.length() > 0) {
                s = s.toLowerCase();
                for (Host tmp : reserve) {
                    if (tmp.getName().toLowerCase().contains(s))
                        add(tmp);
                }
            } else
                addAll(reserve);
            notifyDataSetChanged();
        }
    }
}
