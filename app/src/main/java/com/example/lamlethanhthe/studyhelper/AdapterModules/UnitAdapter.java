package com.example.lamlethanhthe.studyhelper.AdapterModules;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lamlethanhthe.studyhelper.DataModules.Unit;
import com.example.lamlethanhthe.studyhelper.R;

import java.util.ArrayList;

public class UnitAdapter extends ArrayAdapter<Unit> {
    private Context context;
    private ArrayList<Unit> reserve;

    public UnitAdapter(Context context, ArrayList<Unit> units) {
        super(context, 0, units);
        this.context = context;
        reserve = new ArrayList<>();
        reserve.addAll(units);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_unit, null);
        }

        Unit curUnit = getItem(position);

        try {
            ((TextView)convertView.findViewById(R.id.txtUnitID)).setText(Integer.toString(curUnit.getId()));
            ((TextView)convertView.findViewById(R.id.txtUnitName)).setText(curUnit.getName());
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
                for (Unit tmp : reserve) {
                    if (tmp.getName().toLowerCase().contains(s))
                        add(tmp);
                }
            } else
                addAll(reserve);
            notifyDataSetChanged();
        }
    }
}
