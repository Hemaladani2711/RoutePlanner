package com.example.yagnikbhatt.routeplanner.view;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;

import com.example.yagnikbhatt.routeplanner.Data.DatabaseContract;
import com.example.yagnikbhatt.routeplanner.R;

import java.util.Calendar;
import java.util.Locale;

/**
 * Implementation of App Widget functionality.
 */
public class UpcomingTask extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        Cursor cursor=context.getContentResolver().query(DatabaseContract.content_URI,null,null,null,DatabaseContract.columns.date);
        cursor.moveToFirst();
        if(cursor.getCount()>0){


        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(cursor.getLong(4));
        CharSequence widgetText = cursor.getString(1);
        CharSequence dateText="Due By: "+calendar.getDisplayName(Calendar.MONTH,Calendar.SHORT, Locale.getDefault())+" "+calendar.get(Calendar.DATE);

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.upcoming_task);
        Intent intent=new Intent(context,EditTaskActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(MainActivity.value,cursor.getInt(0));
        PendingIntent pendingIntent=PendingIntent.getActivity(context,0,intent,0);
        views.setOnClickPendingIntent(R.id.widget_TaskDescription,pendingIntent);
        views.setTextViewText(R.id.widget_TaskDescription,widgetText);
        views.setTextViewText(R.id.Widget_TaskDate,dateText);
        cursor.close();
        //Toast.makeText(context,""+widgetText,Toast.LENGTH_SHORT).show();

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);}
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
      for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

       /* RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.upcoming_task);

        Intent intent=new Intent(context,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,0,intent,0);
        views.setOnClickPendingIntent(R.id.widget_TaskDescription,pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetIds, views);*/


    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

