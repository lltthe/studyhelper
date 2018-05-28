package com.example.lamlethanhthe.studyhelper;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.lamlethanhthe.studyhelper.DataModules.DataManager;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        initComponents();
    }

    private void initComponents() {
    }

    @Override
    public void onBackPressed() {
        DataManager.Logout();
        super.onBackPressed();
    }

    public void invokeMap(View view) {
        Intent intent = new Intent(MainMenuActivity.this, MapsActivity.class);
        startActivity(intent);
    }

    public void invokeConfig(View view) {
        Intent intent = new Intent(MainMenuActivity.this, ConfigActivity.class);
        startActivity(intent);
    }

    public void invokeWeb(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://thisinh.thithptquocgia.edu.vn"));
        startActivity(intent);
    }

    public void invokeSchedules(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://vietnamnet.vn/vn/giao-duc/tuyen-sinh/lich-thi-chinh-thuc-ky-thi-thpt-quoc-gia-nam-2017-356203.html"));
        startActivity(intent);
    }

    public void invokeNote(View view) {
        Intent intent = new Intent(this, NoteActivity.class);
        startActivity(intent);
    }

    public void invokeAbout(View view) {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }
}
