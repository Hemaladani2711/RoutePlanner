package com.example.yagnikbhatt.routeplanner;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.yagnikbhatt.routeplanner.Data.DatabaseContract;
import com.example.yagnikbhatt.routeplanner.reminders.AlarmScheduler;
import com.example.yagnikbhatt.routeplanner.view.MainActivity;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by hemaladani on 2/4/18.
 */

public class ShowNotificationBroadcast extends BroadcastReceiver {
    public static String TAG=ShowNotificationBroadcast.class.getSimpleName();
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1=new Intent(context, MainActivity.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,0,intent1,0);
        ContentValues contentValues=intent.getParcelableExtra(AlarmScheduler.Content);
        Log.i(TAG,"Content Values:" +contentValues.getAsString(DatabaseContract.columns.description));
        NotificationCompat.Builder  builder=new NotificationCompat.Builder(context,"none").setSmallIcon(android.support.v4.R.drawable.notification_icon_background).setAutoCancel(true).setContentTitle("Task Update").setContentText(contentValues.getAsString(DatabaseContract.columns.description)).setContentIntent(pendingIntent);
        NotificationManager manager=(NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
        manager.notify(001,builder.build());

    }
}
