package com.example.yagnikbhatt.routeplanner.Data;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by hemaladani on 1/23/18.
 */

public class TaskProvider extends ContentProvider {
    TaskDbHelper dbhelper;
    public static String TAG=TaskProvider.class.getSimpleName();
    public   static String ProviderName="com.example.yagnikbhatt.routeplanner.TaskProvider";
    public static String Url="content://"+ProviderName+"/Tasks";
    public static UriMatcher uriMatcher;
    public static final int Tasks=1,Task=2;
    public static final int CLEANUP_JOB_ID=10;


    static {
        uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(ProviderName,"Tasks/", Tasks);
        uriMatcher.addURI(ProviderName,"Tasks/#",Task);
    }

    @Override
    public boolean onCreate() {
        dbhelper=new TaskDbHelper(getContext());
        manageCleanUp();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db=dbhelper.getReadableDatabase();
        Cursor cursor=null;

        switch (uriMatcher.match(uri)){
            case Tasks:
                cursor=db.query(DatabaseContract.tableName,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case Task:
                selection=DatabaseContract.columns._ID+"=?";
                selectionArgs=new String[]{uri.getLastPathSegment().toString()};
              //  Log.i(TAG,"uri: "+uri);
                //Log.i(TAG,"Selection: "+selection);
                //Log.i(TAG,"Cursor size: "+selectionArgs);
                cursor=db.query(DatabaseContract.tableName,projection,selection,selectionArgs,null,null,sortOrder);
                //Log.i(TAG,"Cursor size: "+cursor.getCount());
                break;
                default:
                    throw new IllegalArgumentException("Uri:"+uri+"- is not recognized");

        }
        cursor.setNotificationUri(getContext().getContentResolver(),uri);

        return cursor;




    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)){
            case Tasks:
                return "vnd.android.cursor.dir/com.example.yagnikbhatt.routeplanner";
            case Task:
                return "vnd.android.cursor.item/com.example.yagnikbhatt.routeplanner";
        }


        throw new IllegalArgumentException("Illegal argument Exception");
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Uri uri1;
        switch (uriMatcher.match(uri))
        {
            case Task:
                throw new IllegalArgumentException(""+uri+": Wrong URI");

            case Tasks:

                SQLiteDatabase db=dbhelper.getWritableDatabase();
                long no=db.insert(DatabaseContract.tableName,null,values);
                if (no>0){
                    Log.i(TAG,"uri :"+no);


                    uri1= ContentUris.withAppendedId(DatabaseContract.content_URI,no);
                    getContext().getContentResolver().notifyChange(uri1,null);
                    return uri1;

                }else {
                   // throw new SQLException("Failed to insert");
                }


        }

        return null;


    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database=dbhelper.getWritableDatabase();
        switch (uriMatcher.match(uri)){
            case Task:
                selection=DatabaseContract.columns._ID+"=?";
                long id=ContentUris.parseId(uri);
                selectionArgs=new String[]{""+id};
                break;
            case Tasks:
                break;

        }


        int no= database.delete(DatabaseContract.tableName,selection,selectionArgs);
        getContext().getContentResolver().notifyChange(uri,null);
        return no;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database=dbhelper.getWritableDatabase();
        int no=database.update(DatabaseContract.tableName,values,selection,selectionArgs);
        getContext().getContentResolver().notifyChange(uri,null);
        return no;
    }


    public void manageCleanUp(){

        ComponentName componentName=new ComponentName(getContext(),cleanUpJobService.class);
        //fire job every 15 seconds
        long periodic=5000;

        JobInfo jobInfo=new JobInfo.Builder(CLEANUP_JOB_ID,componentName).setPersisted(true).setMinimumLatency(periodic).build();
        JobScheduler scheduler=(JobScheduler)getContext().getSystemService(Context.JOB_SCHEDULER_SERVICE);

        if(scheduler.schedule(jobInfo)!=JobScheduler.RESULT_SUCCESS){
            Log.i(TAG,"Failed to schedule the job");
        }else
        {
            Log.i(TAG,""+scheduler.getPendingJob(CLEANUP_JOB_ID)+" is scheduled");
        }


    }
}
