package com.example.lamlethanhthe.studyhelper;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.lamlethanhthe.studyhelper.AlgoModules.BasicAlgos;
import com.example.lamlethanhthe.studyhelper.Global.Flags;

/**
 * Created by Lam Le Thanh The on 11/9/2017.
 */

public class alarmReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = intent.getParcelableExtra(Flags.alarmNotif);
        manager.notify(BasicAlgos.randomInt(200, 1), notification);
    }
}
