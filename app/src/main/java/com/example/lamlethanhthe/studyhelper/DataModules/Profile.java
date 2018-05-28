package com.example.lamlethanhthe.studyhelper.DataModules;

import java.util.ArrayList;

public class Profile {
    private String username;
    private boolean highschool;
    private String host;
    private ArrayList<String> subjects;
    private ArrayList<Location> locations;

    public Profile(String username, boolean highschool, String host) {
        this.username = username;
        this.highschool = highschool;
        this.host = host;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isHighschool() {
        return highschool;
    }

    public void setHighschool(boolean highschool) {
        this.highschool = highschool;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
