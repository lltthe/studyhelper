package com.example.lamlethanhthe.studyhelper.AdapterModules;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lamlethanhthe.studyhelper.DataModules.Location;
import com.example.lamlethanhthe.studyhelper.R;

import java.util.ArrayList;

public class LocationAdapter extends ArrayAdapter<Location> {
    private Context context;
    private ArrayList<Location> reserve;

    public LocationAdapter(Context context, ArrayList<Location> locations) {
        super(context, 0, locations);
        this.context = context;
        reserve = new ArrayList<>();
        reserve.addAll(locations);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_location, null);
        }

        Location curLocation = getItem(position);

        if (curLocation != null) {
            try {
                ((TextView) convertView.findViewById(R.id.txtLocName)).setText(curLocation.getName());
                ((TextView) convertView.findViewById(R.id.txtLocAddress)).setText(curLocation.getAddress());
                ImageView img = (ImageView)convertView.findViewById(R.id.img);
                Location.LocType type = curLocation.getType();
                if (type == Location.LocType.overnightSite)
                    img.setImageBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.home));
                else if (type == Location.LocType.testSite)
                    img.setImageBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.test));
                else
                    img.setImageBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.smile));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return convertView;
    }

    public void filter(String s) {
        if (s != null) {
            clear();
            if (s.length() > 0) {
                s = s.toLowerCase();
                for (Location tmp : reserve) {
                    String str = tmp.getAddress() + " " + tmp.getName();
                    if (str.toLowerCase().contains(s))
                        add(tmp);
                }
            } else
                addAll(reserve);
            notifyDataSetChanged();
        }
    }
}
