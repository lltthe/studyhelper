package com.example.lamlethanhthe.studyhelper.AdapterModules;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lamlethanhthe.studyhelper.AlgoModules.BasicAlgos;
import com.example.lamlethanhthe.studyhelper.DataModules.Reminder;
import com.example.lamlethanhthe.studyhelper.Global.TimeInterval;
import com.example.lamlethanhthe.studyhelper.R;

import java.util.ArrayList;

/**
 * Created by Lam Le Thanh The on 11/9/2017.
 */

public class RemAdapter extends RecyclerView.Adapter<RemAdapter.ViewHolder> {
    Context context;
    ArrayList<Reminder> rems;

    public RemAdapter(Context context, ArrayList<Reminder> rems) {
        this.context = context;
        this.rems = rems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Resources res = context.getResources();
        Reminder rem = rems.get(position);
        String str = BasicAlgos.CalendartoString(res, rem.fireAt);
        if (rem.repeatInterval != TimeInterval.none)
            str += " " + res.getString(R.string.hint_set_alarm_repeat) + " " + (rem.repeatInterval == TimeInterval.day ? res.getString(R.string.info_day) : res.getString(R.string.info_week));
        holder.txt.setText(str);
    }

    @Override
    public int getItemCount() {
        return rems == null ? 0 : rems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt;

        public ViewHolder(View itemView) {
            super(itemView);
            txt = (TextView)itemView.findViewById(R.id.txtRem);
        }
    }
}
