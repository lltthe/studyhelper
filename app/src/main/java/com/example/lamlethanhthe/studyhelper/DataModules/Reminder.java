package com.example.lamlethanhthe.studyhelper.DataModules;

import com.example.lamlethanhthe.studyhelper.Global.TimeInterval;

import java.util.Calendar;

/**
 * Created by Lam Le Thanh The on 9/9/2017.
 */

public class Reminder {
    public Calendar fireAt;
    public TimeInterval repeatInterval;

    public Reminder() {
        fireAt = Calendar.getInstance();
        repeatInterval = TimeInterval.none;
    }

    public Reminder(Calendar fireAt, TimeInterval repeatInterval) {
        this.fireAt = fireAt;
        this.repeatInterval = repeatInterval;
    }
}
