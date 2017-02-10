package com.unrestrained.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

import com.unrestrained.activity.BaiduMapActivity;

public class TimeService extends Service {
    public TimeService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, BaiduMapActivity.class), 0);

//      am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 2 * 1000, pendingIntent);//重复

        am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 5000, pendingIntent);//一次

        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 启动服务
     *
     * @param context
     */
    public static void startService(Context context) {
        Intent intent = new Intent(context, TimeService.class);
        context.startService(intent);
    }

}
