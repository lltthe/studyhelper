package com.example.lamlethanhthe.studyhelper;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lamlethanhthe.studyhelper.AdapterModules.UserAdapter;
import com.example.lamlethanhthe.studyhelper.AdapterModules.UserTab;
import com.example.lamlethanhthe.studyhelper.DataModules.DataManager;
import com.example.lamlethanhthe.studyhelper.DataModules.ImageManager;

import java.util.ArrayList;

public class LaunchActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<UserTab> users;
    private UserAdapter userAdapter;

    DataManager dat;

    private Button add;
    private EditText edt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        dat = new DataManager(this);
        dat.initLocalData();

        recyclerView = (RecyclerView)findViewById(R.id.recView);
        ArrayList<String> usernames = dat.getAllUsernames();

        users = new ArrayList<>();

        Bitmap def = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        for (String i : usernames) {
            Bitmap bmp = (new ImageManager("images", i + ".png", this, false)).load();
            users.add(new UserTab(i, bmp == null ? def : bmp));
        }

        users.get(0).setAvatar(BitmapFactory.decodeResource(getResources(), R.drawable.oggy));

        userAdapter = new UserAdapter(LaunchActivity.this, users);

        LinearLayoutManager layoutManager = new LinearLayoutManager(LaunchActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(userAdapter);

        userAdapter.notifyDataSetChanged();

        initAddUserComponents();

        preExec();
    }

    private void initAddUserComponents() {
        add = (Button)findViewById(R.id.btnAddUser);
        edt = (EditText)findViewById(R.id.edtNewUser);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = edt.getText().toString();
                boolean t = dat.Signup(s);

                if (t) {
                    users.add(new UserTab(s,BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)));
                    Toast.makeText(LaunchActivity.this, getResources().getString(R.string.launch_add_succ), Toast.LENGTH_SHORT).show();
                    edt.setText("");
                    userAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(LaunchActivity.this, getResources().getString(R.string.launch_add_fail), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private static final int CAMERA_CODE = 0;
    private int avaPos;

    private void preExec() {
        clearObsoleteReminder();
    }

    private void clearObsoleteReminder() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_CODE) {
                Bitmap bmp = (Bitmap)data.getExtras().get("data");

                users.get(avaPos).setAvatar(bmp);
                userAdapter.notifyDataSetChanged();

                new ImageManager("images", users.get(avaPos).getUsername() + ".png", this, false).save(bmp);
            }
        }
    }

    public void onAvatarClick(int pos) {
        avaPos = pos;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_CODE);
    }
}
