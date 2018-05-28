package com.example.lamlethanhthe.studyhelper.DataModules;

import com.google.android.gms.maps.model.LatLng;

public class Location {
    public enum LocType {testSite, overnightSite, restSite}

    private String user;
    private LatLng pos;
    private String name;
    private String address;
    private LocType type;

    public Location(String user, LatLng pos, String name, String address, LocType type) {
        this.user = user;
        this.pos = pos;
        this.name = name;
        this.address = address;
        this.type = type;
    }

    public String getUser() {
        return user;
    }

    public LatLng getPos() {
        return pos;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public LocType getType() {
        return type;
    }

    public static LocType fromString(String s) {
        if (s.equals("Địa điểm thi"))
            return LocType.testSite;
        if (s.equals("Nơi nghỉ"))
            return LocType.overnightSite;
        return LocType.restSite;
    }

    public static String toString(LocType t) {
        if (t == LocType.overnightSite)
            return "Nơi nghỉ";
        if (t == LocType.testSite)
            return "Địa điểm thi";
        return "Khác";
    }
}
