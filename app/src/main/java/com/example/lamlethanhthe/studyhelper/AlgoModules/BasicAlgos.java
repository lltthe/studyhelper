package com.example.lamlethanhthe.studyhelper.AlgoModules;

import android.content.res.Resources;

import com.example.lamlethanhthe.studyhelper.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by Lam Le Thanh The on 10/9/2017.
 */

public class BasicAlgos {

    public static boolean willSwapCalendar(Calendar c1, Calendar c2, String order) {
        if (c1 == null)
            return true;
        if (c2 == null)
            return false;

        int cmp = c1.compareTo(c2);
        if (cmp == 0)
            return false;

        boolean ans = cmp < 0;
        return order.equals("asc") != ans;
    }

    public static String CalendartoString(Resources res, Calendar cal) {
        if (cal == null || res == null)
            return "";
        SimpleDateFormat pattern = new SimpleDateFormat(res.getString(R.string.format_date) + " H:m");
        return pattern.format(cal.getTime());
    }

    public static String DatetoString(Resources res, Calendar cal) {
        if (cal == null || res == null)
            return "";
        SimpleDateFormat pattern = new SimpleDateFormat(res.getString(R.string.format_date));
        return pattern.format(cal.getTime());
    }

    public static int randomInt(int max, int min) {
        Random r = new Random();
        return r.nextInt(max - min + 1) + min;
    }
}
