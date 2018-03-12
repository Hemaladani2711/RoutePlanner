package com.example.yagnikbhatt.routeplanner.Data;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yagnikbhatt.routeplanner.R;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by hemaladani on 1/18/18.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {


    Context mContext;
    public onListItemClickListener monListItemClickListener;
    Cursor cursor;
    public String TAG=TaskAdapter.class.getSimpleName();

    public interface onListItemClickListener{
         void onItemToggled(int id);
        void onDescriptionClicked(int id);
        //void onPriorityPictClicked();
    }


    public class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private String TAG=TaskViewHolder.class.getSimpleName();
        CheckBox checkBox;
        TextView descriptionText,dateText;
        ImageView priorityView;
        LinearLayout layoutDescription;
        //Context mContext;


        public TaskViewHolder(View itemView) {
            super(itemView);
            layoutDescription=itemView.findViewById(R.id.layoutDescription);
            checkBox=itemView.findViewById(R.id.chkbox);
            descriptionText=itemView.findViewById(R.id.txtDescription);
            dateText=itemView.findViewById(R.id.txtDate);
            priorityView=itemView.findViewById(R.id.imgPriority);
            checkBox.setOnClickListener(this);
            descriptionText.setOnClickListener(this);
            priorityView.setOnClickListener(this);
            layoutDescription.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v==checkBox){

                setCheckBoxToggled(this);
            //    Log.i(TAG,"SetCheckBox");
            }else if(v==layoutDescription||v==descriptionText){
                setOnDescriptionClicked(this);
            //    Log.i(TAG,"SetDescription");
            }/*else if(v==priorityView){
                setOnPriorityPictClick();

            }*/

        }
    }
    public void setOnListItemClickListener(onListItemClickListener onListItemClickListener){
        monListItemClickListener=onListItemClickListener;
    }
    public void setCheckBoxToggled(TaskViewHolder holder){
        if(monListItemClickListener!=null){
           // Log.i(TAG,"Checkbox Clicked");
            cursor.moveToFirst();
            cursor.move(holder.getAdapterPosition());
            int id=cursor.getInt(0);
            monListItemClickListener.onItemToggled(id);
        }
    }

   /* public void setOnPriorityPictClick(){
        if(monListItemClickListener!=null){
            monListItemClickListener.onPriorityPictClicked();
        }
    }*/
    public void setOnDescriptionClicked(TaskViewHolder holder){
        if(monListItemClickListener!=null){
         //   Log.i(TAG,"Description Clicked");
            cursor.moveToFirst();
            cursor.move(holder.getAdapterPosition());
            int id=cursor.getInt(0);
            monListItemClickListener.onDescriptionClicked(id);
        }
    }

    public TaskAdapter(Context context){
        mContext=context;
        Uri uri=Uri.parse(TaskProvider.Url);
        Log.i("uriAd",""+uri);
        cursor=mContext.getContentResolver().query(uri,null,null,null,null,null);


    }



    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext=parent.getContext();
        View view= LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        cursor.moveToFirst();
        cursor.move(position);
        holder.descriptionText.setText(""+cursor.getString(1));
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(cursor.getLong(4));
        holder.dateText.setText("Due by "+calendar.getDisplayName(Calendar.MONTH,Calendar.SHORT, Locale.getDefault())+" "+calendar.get(Calendar.DATE));

    }

    @Override
    public int getItemCount() {
        if(cursor!=null)
        return cursor.getCount();
        else
            return 0;
    }
}
