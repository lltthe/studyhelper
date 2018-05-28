package com.example.lamlethanhthe.studyhelper.DataModules;

import java.io.Serializable;

public class Host implements Serializable {
    private String name;
    private int unit;
    private String website;
    private String phone;
    private String address;

    public Host(String name, int unit, String website, String phone, String address) {
        this.name = name;
        this.unit = unit;
        this.website = website;
        this.phone = phone;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public int getUnit() {
        return unit;
    }

    public String getWebsite() {
        return website;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }
}
