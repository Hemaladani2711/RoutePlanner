package com.example.yagnikbhatt.routeplanner.reminders;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.example.yagnikbhatt.routeplanner.Data.DatabaseContract;

import java.util.Calendar;

/**
 * Created by hemaladani on 1/23/18.
 */

public class AlarmScheduler {
    public static String TAG="Scheduler";//AlarmScheduler.class.getSimpleName();
    public static String Content="Content";

  public  void createAlarm(Context mContext, ContentValues contentValues){

       AlarmManager alarmManager=(AlarmManager) mContext.getSystemService(mContext.ALARM_SERVICE);

      //NotificationManager manager=mContext.get

      PendingIntent pendingIntent=ReminderAlarmService.getReminderPendingIntent(mContext,contentValues);
      Log.i(TAG,"Description: "+contentValues.getAsString(DatabaseContract.columns.description)+" | Date: "+contentValues.getAsLong(DatabaseContract.columns.date));
      alarmManager.setExact(AlarmManager.RTC_WAKEUP,contentValues.getAsLong(DatabaseContract.columns.date),pendingIntent);
      Calendar calendar=Calendar.getInstance();
      calendar.setTimeInMillis(contentValues.getAsLong(DatabaseContract.columns.date));
      Log.i(AlarmScheduler.class.getSimpleName(),"Alarm set at"+ calendar.getTime());

  }
  public int createquery(){
      return 0;
  }
/*
  public void setRepeatingAlarm(Context mContext, List<ContentValues> myTasks){

      AlarmManager alarmManager=(AlarmManager) mContext.getSystemService(mContext.ALARM_SERVICE);

      //NotificationManager manager=mContext.get
      for(int i=0;i<myTasks.size();i++) {
          ContentValues contentValues=myTasks.get(i);
          Calendar calendar = Calendar.getInstance();
          calendar.setTimeInMillis(contentValues.getAsLong(DatabaseContract.columns.date));
          Intent intent1=new Intent(mContext, ShowNotificationBroadcast.class);
          intent1.putExtra(Content,contentValues);

          //intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          PendingIntent pendingIntent=PendingIntent.getBroadcast(mContext,0,intent1,PendingIntent.FLAG_CANCEL_CURRENT);
          Log.i(AlarmScheduler.class.getSimpleName(), "Alarm set at" + calendar.getTime()+" for :"+contentValues.getAsString(DatabaseContract.columns.description));
          alarmManager.setExact(AlarmManager.RTC_WAKEUP, contentValues.getAsLong(DatabaseContract.columns.date), pendingIntent);
          contentValues.clear();
      }




  }*/
}
