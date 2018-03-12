package com.example.yagnikbhatt.routeplanner.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.yagnikbhatt.routeplanner.Data.TaskAdapter;
import com.example.yagnikbhatt.routeplanner.R;

public class MainActivity extends AppCompatActivity implements TaskAdapter.onListItemClickListener{

    RecyclerView itemRecycler;
    //LinearLayoutManager linearLayoutManager;
    TaskAdapter adapter;
    public static String TAG=MainActivity.class.getSimpleName();
    public static String value="id";

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(adapter!=null){
            adapter=null;
        }
        setAdapterView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(adapter!=null){
            adapter=null;
        }
        setAdapterView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar mytoolbar=findViewById(R.id.toolbar);


        mytoolbar.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
        setSupportActionBar(mytoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mytoolbar.setTitle("Notes");
        setSupportActionBar(mytoolbar);


        setAdapterView();

        FloatingActionButton fab=findViewById(R.id.floatingActionButton);
        fab.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark,getTheme()));
        

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // RelativeLayout relativeLayout=(RelativeLayout) findViewById(R.id.layoutMain);
                Intent intent=new Intent(MainActivity.this,AddTask.class);
                startActivity(intent);
            }
        });






    }
    public void setAdapterView()
    {
        itemRecycler=findViewById(R.id.itemRecyclerView);
        adapter=new TaskAdapter(getApplicationContext());
        itemRecycler.setAdapter(adapter);
        //linearLayoutManager=new LinearLayoutManager(this);
        //recyclerView.setHasFixedSize(true);
        itemRecycler.setLayoutManager(new LinearLayoutManager(this));
        itemRecycler.addItemDecoration(new DividerItemDecoration(itemRecycler.getContext(),new LinearLayoutManager(this).getOrientation()));
        adapter.setOnListItemClickListener(this);


    }

    @Override
    public void onItemToggled(int id) {
        //Log.i(TAG,"Checkbox Clicked");
     //  Toast.makeText(getApplicationContext(),"Checkbox: "+id+" Clicked",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onDescriptionClicked(int id) {
        Log.i(TAG,"description Clicked");
       // Toast.makeText(getApplicationContext(),"Description: "+id+" Clicked",Toast.LENGTH_LONG).show();
        Intent intent=new Intent(this,EditTaskActivity.class);
        intent.putExtra(value,id);
        startActivity(intent);


    }
}
