package com.example.yagnikbhatt.routeplanner.reminders;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.yagnikbhatt.routeplanner.Data.DatabaseContract;
import com.example.yagnikbhatt.routeplanner.Data.TaskUpdateService;
import com.example.yagnikbhatt.routeplanner.view.MainActivity;

/**
 * Created by hemaladani on 1/28/18.
 */

public class ReminderAlarmService extends IntentService {


    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public static String TAG="Intent Service";//ReminderAlarmService.class.getSimpleName();
    public static String CONTENTVALUES="ContentValues";
    int notificationId=001;
    public ReminderAlarmService() {
        super(TAG);
        
    }



    static PendingIntent getReminderPendingIntent(Context context, ContentValues contentValues){
        Intent intent=new Intent(context, ReminderAlarmService.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(CONTENTVALUES,contentValues);
        Log.i(TAG,"Description: "+contentValues.getAsString(DatabaseContract.columns.description));
        PendingIntent pendingIntent=PendingIntent.getService(context,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);
        //
        //context.startService(intent);

        return pendingIntent;

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"Destroying");
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {


        Intent intent1=new Intent(ReminderAlarmService.this, MainActivity.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent2=PendingIntent.getActivity(ReminderAlarmService.this,0,intent1,0);
        ContentValues contentValues2=intent.getParcelableExtra(CONTENTVALUES);
       // notificationId=contentValues2.getAsInteger(DatabaseContract.columns._ID);
        //Log.i(TAG,"Description: "+contentValues2.getAsString(DatabaseContract.columns.description)+" | Date: "+contentValues2.getAsLong(DatabaseContract.columns.date));
//        Log.i(TAG,"Description: "+contentValues2.getAsString(DatabaseContract.columns.description));

        NotificationCompat.Builder  builder=new NotificationCompat.Builder(ReminderAlarmService.this,"none").setSmallIcon(android.support.v4.R.drawable.notification_icon_background).setAutoCancel(true).setContentTitle("Task Update").setContentText(contentValues2.getAsString(DatabaseContract.columns.description)).setContentIntent(pendingIntent2);
        NotificationManager manager=(NotificationManager)this.getSystemService(NOTIFICATION_SERVICE);
        manager.notify(notificationId,builder.build());
        contentValues2.put(DatabaseContract.columns.isCompleted,1);
        Log.i(TAG,contentValues2.toString());
        TaskUpdateService.updateTasks(getApplicationContext(),contentValues2);


    }

}
