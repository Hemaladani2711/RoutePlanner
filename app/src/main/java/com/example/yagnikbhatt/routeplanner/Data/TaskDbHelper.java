package com.example.yagnikbhatt.routeplanner.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.yagnikbhatt.routeplanner.Data.DatabaseContract.columns;
import static com.example.yagnikbhatt.routeplanner.Data.DatabaseContract.databaseName;
import static com.example.yagnikbhatt.routeplanner.Data.DatabaseContract.tableName;

/**
 * Created by hemaladani on 1/23/18.
 */

public class TaskDbHelper extends SQLiteOpenHelper {

    Context mContext;
    public TaskDbHelper(Context mContext) {
        super(mContext, databaseName, null, DatabaseContract.version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        String createTable="create table "+ tableName+"("+ columns._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+ columns.description+" text, "+columns.priority+" Integer,"+columns.isCompleted+" Integer, "+columns.date+" long, "+columns.location+" text,"+columns.longitude+" REAL, "+columns.latitude+" REAL)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.delete(tableName,null,null);
        onCreate(db);

    }
   /* public Cursor getData(){
        SQLiteDatabase db=getWritableDatabase();
        Cursor cursor=db.query(tableName,cols,null,null,null,null,null);
        return cursor;
    }*/
    public void insertValues(Task task){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(columns.description,task.getDescription());
        contentValues.put(columns.priority,task.getPriority());
        contentValues.put(columns.isCompleted,0);
        contentValues.put(columns.date,task.getDate());
        db.insert(tableName,null,contentValues);

    }
}
