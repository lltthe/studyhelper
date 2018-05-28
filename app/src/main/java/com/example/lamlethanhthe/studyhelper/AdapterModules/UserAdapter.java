package com.example.lamlethanhthe.studyhelper.AdapterModules;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lamlethanhthe.studyhelper.DataModules.DataManager;
import com.example.lamlethanhthe.studyhelper.LaunchActivity;
import com.example.lamlethanhthe.studyhelper.MainMenuActivity;
import com.example.lamlethanhthe.studyhelper.R;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {
    private ArrayList<UserTab> users;
    private Context context;
    private DataManager dat;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final String text = users.get(position).getUsername();
        holder.username.setText(text);

        holder.username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dat != null) {
                    boolean t = dat.Signin(text);

                    if (t) {
                        Intent intent = new Intent(context, MainMenuActivity.class);
                        context.startActivity(intent);
                    }
                }
            }
        });

        holder.avatar.setImageBitmap(users.get(position).getAvatar());
        holder.avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LaunchActivity)context).onAvatarClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView username;
        public ImageView avatar;

        public MyViewHolder(View view) {
            super(view);

            username = (TextView) view.findViewById(R.id.userName);
            avatar = (ImageView) view.findViewById(R.id.avatar);
        }
    }

    public UserAdapter(Context context, ArrayList<UserTab> users) {
        this.context = context;
        this.users = users;
        dat = null;

        if (context != null)
            dat = new DataManager(context);
    }

}
