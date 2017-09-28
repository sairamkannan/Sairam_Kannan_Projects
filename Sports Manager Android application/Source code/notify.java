package com.cs442.sairamkannan.sportsmgr;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;

public class notify extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    public int onStartCommand(Intent intent, int flags, int startId)
    {

        System.out.println("reached notify");
        NotificationCompat.Builder builder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(notify.this)
                        .setSmallIcon(R.drawable.icon)
                        .setContentTitle("You have one New Notification")
                                //.setContentText("Counter"+counter);
                        .setContentText("The counter in hr:mn:ss => ");//Displaying time in hour:min:sec


        Intent notificationIntent = new Intent(notify.this, MainActivity.class);

        PendingIntent contentIntent = PendingIntent.getActivity(notify.this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(contentIntent);
        builder.setAutoCancel(true);
        long[] pattern = {500,500,500,500,500};//vibrate pattern is 500milliseconds apart
        builder.setVibrate(pattern);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);//uses the default ringtone
        builder.setSound(alarmSound);
        builder.setStyle(new NotificationCompat.InboxStyle());
// Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1, builder.build());
        return START_STICKY;
    }

    public void onDestroy() {
        //This Method is Called when Stop is Pressed
        System.out.println("reached destroy");
        super.onDestroy();
    }
    }
