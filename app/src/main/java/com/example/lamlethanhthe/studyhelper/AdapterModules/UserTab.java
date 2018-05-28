package com.example.lamlethanhthe.studyhelper.AdapterModules;

import android.graphics.Bitmap;

public class UserTab {
    private String username;
    private Bitmap avatar;

    public UserTab(String username, Bitmap avatar) {
        this.username = username;
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public Bitmap getAvatar() {
        return avatar;
    }

    public void setAvatar(Bitmap avatar) {
        this.avatar = avatar;
    }
}
