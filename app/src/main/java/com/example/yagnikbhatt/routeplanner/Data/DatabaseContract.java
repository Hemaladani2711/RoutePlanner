package com.example.yagnikbhatt.routeplanner.Data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by hemaladani on 1/23/18.
 */

public  class DatabaseContract {

    public static String databaseName="sampleTask.db";
    public static int version=1;
    public static String tableName="task";
    public static Uri content_URI=Uri.parse(TaskProvider.Url);
    public static String[] cols=new String[]{columns._ID,columns.description,columns.priority,columns.isCompleted,columns.date};
    public  static class columns implements BaseColumns {
        public  static String description="Description";
        public static String date="DueDate";
        public static String priority="isPriority";
        public static String isCompleted="isCompleted";
        public static String location="location";
        public static String longitude="longitude";
        public static String latitude="latitude";

    }

}
