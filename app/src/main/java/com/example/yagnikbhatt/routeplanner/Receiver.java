package com.example.yagnikbhatt.routeplanner;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;

import com.example.yagnikbhatt.routeplanner.Data.DatabaseContract;
import com.example.yagnikbhatt.routeplanner.reminders.AlarmScheduler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hemaladani on 1/28/18.
 */

public class Receiver extends BroadcastReceiver {
    public static String TAG=BroadcastReceiver.class.getSimpleName();
    @Override
    public void onReceive(Context context, Intent intent) {
        List<ContentValues> myTasks=new ArrayList<>();

            Log.i(Receiver.class.getSimpleName(),"Boot Completed");
           Cursor cursor=context.getContentResolver().query(DatabaseContract.content_URI,null,null,null,null);
            if(cursor.getCount()>0)
            {
                cursor.moveToFirst();
                do{

                    ContentValues contentValues=new ContentValues();
                    contentValues.put(DatabaseContract.columns._ID,cursor.getInt(0));
                    contentValues.put(DatabaseContract.columns.description,cursor.getString(1));
                    contentValues.put(DatabaseContract.columns.isCompleted,cursor.getInt(2));
                    contentValues.put(DatabaseContract.columns.priority,cursor.getInt(3));
                    contentValues.put(DatabaseContract.columns.date,cursor.getLong(4));


                   Log.i(Receiver.class.getSimpleName(),"Content Values: "+contentValues.getAsString(DatabaseContract.columns.description));
                    AlarmScheduler alarmScheduler=new AlarmScheduler();
                    alarmScheduler.createAlarm(context,contentValues);


                    //myTasks.add(contentValues);



                }while ( cursor.moveToNext());
               // AlarmManager alarmManager=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
                Log.i(TAG,"Alarm reset after boot:" );
                cursor.close();
                /*AlarmScheduler alarmScheduler=new AlarmScheduler();
                alarmScheduler.setRepeatingAlarm(context,myTasks);*/




            }

           /* Intent intent1=new Intent(context, MainActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent1);*/

    }
}
