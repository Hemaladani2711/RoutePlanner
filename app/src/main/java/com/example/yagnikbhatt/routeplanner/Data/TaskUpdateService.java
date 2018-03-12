package com.example.yagnikbhatt.routeplanner.Data;

import android.app.IntentService;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.yagnikbhatt.routeplanner.reminders.AlarmScheduler;

/**
 * Created by hemaladani on 1/23/18.
 */

public class TaskUpdateService extends IntentService {
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"Service is being Destroyed");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     **/

    public static String TAG=TaskUpdateService.class.getSimpleName();

    public static String Insert="Insert";
    public static String Update="Update";
    public static String Delete="Delete";
    public static String mContentValues="contentValues";
    public static Context context;

    public TaskUpdateService() {
        super(TAG);
        Log.i(TAG,"Task Update Started");
    }
    public static void addTask(Context mContext,ContentValues contentValues){
        Log.i(TAG,"Add Task");

        try{
        context=mContext;
        Intent intent=new Intent(mContext,TaskUpdateService.class);
        intent.setAction(Insert);
        intent.putExtra(mContentValues,contentValues);
        context.startService(intent);}
        catch (Exception e){
            Log.i(TAG,e.getMessage());
        }

    }
    public static void deleteTask(Context mContext, Uri uri){
        try{
        Intent intent=new Intent(mContext,TaskUpdateService.class);
                intent.setAction(Delete);
                intent.setData(uri);
                mContext.startService(intent);}
                catch (Exception e){
                    Log.i(TAG,e.getMessage());
                }

    }

    public static void updateTasks(Context mContext, ContentValues contentValues){
        try{

            Intent intent=new Intent(mContext,TaskUpdateService.class);
            intent.setAction(Update);
            intent.putExtra(mContentValues,contentValues);
            mContext.startService(intent);


        }catch (Exception e){
            Log.i(TAG,e.getMessage());
        }
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
       // intent.get
        Log.i(TAG,"OnHandleIntent");
        if(intent.getAction().matches(Insert)){
            ContentValues contentValues=intent.getParcelableExtra(mContentValues);
            performInsert(contentValues);

        }else if(intent.getAction().matches(Update)){
            ContentValues contentValues=intent.getParcelableExtra(mContentValues);
            Log.i(TAG,"Action Update: "+""+contentValues.toString());
            performUpdate(contentValues);


        }else if(intent.getAction().matches(Delete)){
            Uri uri=intent.getData();
            performDelete(uri);

        }

    }
    private void performDelete(Uri uri){
      //  Uri uri= ContentUris.withAppendedId(DatabaseContract.content_URI,contentValues.getAsInteger(DatabaseContract.columns._ID));
        int no=context.getContentResolver().delete(uri,null,null);
        Log.i(TAG,""+no+" rows deleted");
    }

    private void performInsert(ContentValues contentValues){
       Uri uri=context.getContentResolver().insert(DatabaseContract.content_URI,contentValues);
       Long nu=ContentUris.parseId(uri);
       int id=Integer.parseInt(nu.toString());
       contentValues.put(DatabaseContract.columns._ID,id);
       Log.i(TAG,"Inserted Successful");
        AlarmScheduler alarmScheduler=new AlarmScheduler();
        alarmScheduler.createAlarm(this,contentValues);

    }
    private void performUpdate(ContentValues contentValues){
        String id=DatabaseContract.columns._ID;
        String where=id+"=?";
        String []selectionArgs=new String[]{""+contentValues.getAsString(id)};
        Log.i(TAG,""+contentValues.toString());
        Uri uri= ContentUris.withAppendedId(DatabaseContract.content_URI,contentValues.getAsInteger(id));
        long update=context.getContentResolver().update(uri,contentValues,where,selectionArgs);
        if(update<=0){
            Log.i(TAG,"Failed to update");
        }else{
            Log.i(TAG,"Updated: "+update);
        }
    }


}
