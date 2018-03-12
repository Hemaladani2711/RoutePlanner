package com.example.yagnikbhatt.routeplanner.view;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.TextView;

import com.example.yagnikbhatt.routeplanner.Data.DatabaseContract;
import com.example.yagnikbhatt.routeplanner.Data.Task;
import com.example.yagnikbhatt.routeplanner.Data.TaskUpdateService;
import com.example.yagnikbhatt.routeplanner.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Locale;

public class AddTask extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    long date;
    TextInputEditText addDescription;

    Switch prioritySwitch;
    TextView dateText,txtAddress;
    String location;
    double latitude,longitude;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private String userID;
    private FirebaseAuth mAuth;
    Uri uri;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_task_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_save:
                saveValues();

                break;

        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        txtAddress=findViewById(R.id.txtAddress);
        android.support.v7.widget.Toolbar toolbar=(android.support.v7.widget.Toolbar) findViewById(R.id.addToolbar);
        PlaceAutocompleteFragment autocompleteFragment=(PlaceAutocompleteFragment)getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                //Log.i("Place",place.getName().toString());
                //Toast.makeText(getApplicationContext(),""+place.getAddress(),Toast.LENGTH_LONG).show();
                location=place.getName()+"";
                txtAddress.setText(location);
                LatLng latLng=place.getLatLng();
                longitude=latLng.longitude;
                latitude=latLng.latitude;
                Log.i("Add Task",""+latLng.toString());

            }

            @Override
            public void onError(Status status) {

            }
        });
        toolbar.setTitle("Add Task");
        toolbar.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        addDescription=findViewById(R.id.addDescription);
        prioritySwitch=findViewById(R.id.switch1);
        dateText=(TextView)findViewById(R.id.txtSetDate);
        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(getApplicationContext(),"Clicked",Toast.LENGTH_SHORT).show();
                datePickerFrag datePickerFrag=new datePickerFrag();
                datePickerFrag.show(getSupportFragmentManager(),"datefrag");



            }
        });

    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        //Toast.makeText(getApplicationContext(),""+year,Toast.LENGTH_SHORT).show();
        Calendar calendar=Calendar.getInstance();
        // Date and time when event needs to show.
        calendar.set(year,month,dayOfMonth,22,29,30);
        dateText.setText("Due by "+calendar.getDisplayName(Calendar.MONTH,Calendar.SHORT, Locale.getDefault())+" "+calendar.get(Calendar.DATE));
        date =calendar.getTimeInMillis();

    }

    /*public int[] settime(){
        Calendar calendar=Calendar.getInstance();
        int[] dates=new int[]{calendar.get(Calendar.HOUR),calendar.get(Calendar.MINUTE)+1,calendar.get(Calendar.SECOND)};
        return dates;

    }*/
    public void saveValues(){
        Task task=new Task();
        if(date!=0){
         task.setDate(date);
        }
        task.setDescription(addDescription.getText().toString());
        if(prioritySwitch.isChecked()){
            task.setPriority(1);
        }else {
            task.setPriority(0);
        }
        task.setCompleted(false);
        /*TaskDbHelper helper=new TaskDbHelper(getApplicationContext());
        helper.insertValues(task);*/
        //Uri uri= Uri.parse(TaskProvider.Url);
        ContentValues contentValues=new ContentValues();
        contentValues.put(DatabaseContract.columns.description,task.getDescription());
        contentValues.put(DatabaseContract.columns.priority,task.getPriority());
        contentValues.put(DatabaseContract.columns.date,task.getDate());
        contentValues.put(DatabaseContract.columns.isCompleted,0);
        contentValues.put(DatabaseContract.columns.location,location);
        contentValues.put(DatabaseContract.columns.longitude,longitude);
        contentValues.put(DatabaseContract.columns.latitude,latitude);


        mAuth=FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userID=user.getUid();

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef= mFirebaseDatabase.getReference();

        Task userInformation = new Task(task.getDate(),task.getDescription(),location,0,0,false,longitude,latitude);
        myRef.child("user").child(userID).setValue(userInformation);

        TaskUpdateService.addTask(this,contentValues);




       //getApplicationContext().getContentResolver().insert(uri,contentValues);


        //Log.i("Save Values","Successful");
        finish();
    }
}
