package com.example.yagnikbhatt.routeplanner.view;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.example.yagnikbhatt.routeplanner.Data.DatabaseContract;
import com.example.yagnikbhatt.routeplanner.R;
import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by hemaladani on 2/4/18.
 */

public class EditTaskActivity extends AppCompatActivity {
public static String TAG=EditTaskActivity.class.getSimpleName();
        int id;
TextInputEditText Edit_Description;
TextView edit_DateText,txtEditAddress;
Switch edit_Switch;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_task);
        Edit_Description=findViewById(R.id.editDescription);
        edit_Switch=findViewById(R.id.editSwitch);
        edit_DateText=findViewById(R.id.editDateText);
        txtEditAddress=findViewById(R.id.txt_edit_Address);
        android.support.v7.widget.Toolbar toolbar=(android.support.v7.widget.Toolbar) findViewById(R.id.addToolbar1);
        toolbar.setTitle("Edit Task");
        toolbar.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setValues();






        //Toast.makeText(this,"id:"+cursor.getString(1),Toast.LENGTH_SHORT).show();
    }

    public void setValues(){
        final Intent intent=getIntent();
        id=intent.getIntExtra(MainActivity.value,0);
        Uri uri= ContentUris.withAppendedId(DatabaseContract.content_URI,id);
        Cursor cursor=getContentResolver().query(uri,null,null,null,null);
        cursor.moveToFirst();
        Edit_Description.setText(cursor.getString(1));
        int i=cursor.getInt(2);
        if(i==1){
            edit_Switch.setChecked(true);
        }else{
            edit_Switch.setChecked(false);
        }
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(cursor.getLong(4));
        edit_DateText.setText("Due by:"+calendar.getDisplayName(Calendar.MONTH,Calendar.SHORT, Locale.getDefault())+"  "+calendar.get(Calendar.DATE));
        txtEditAddress.setText(cursor.getString(5));
        final LatLng destLatlng=new LatLng(cursor.getDouble(7),cursor.getDouble(6));
        Log.i(TAG,"Destlatlng: "+destLatlng.toString());
        txtEditAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(EditTaskActivity.this,MapsActivity.class);
                /*intent1.putExtra(MapsActivity.DEST_LAT,destLatlng.latitude);
                intent1.putExtra(MapsActivity.DEST_LONG,destLatlng.longitude);*/
                double[]array={destLatlng.latitude,destLatlng.longitude};
                //Bundle bundle=new Bundle();
                Log.i(TAG,"Bundle:"+destLatlng.latitude);
                //bundle.putDouble(MapsActivity.DEST_LAT,destLatlng.latitude);
                //bundle.putDouble(MapsActivity.DEST_LONG,destLatlng.longitude);
                intent1.putExtra(MapsActivity.DEST_LONG,array);
                startActivity(intent1);
            }
        });

    }
}
