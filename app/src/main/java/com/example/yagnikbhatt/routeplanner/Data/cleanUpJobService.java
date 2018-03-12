package com.example.yagnikbhatt.routeplanner.Data;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by hemaladani on 2/12/18.
 */

public class cleanUpJobService extends JobService {

    public static String TAG=cleanUpJobService.class.getSimpleName();

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i(TAG,"Job service started");
      //  jobFinished(params,false);
        new cleanUpTasks().execute(params);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    class cleanUpTasks extends AsyncTask<JobParameters,Void,JobParameters>{
        @Override
        protected void onPostExecute(JobParameters jobParameters) {
            super.onPostExecute(jobParameters);
            jobFinished(jobParameters,true);
        }

        @Override
        protected JobParameters doInBackground(JobParameters... jobParameters) {
            String where=DatabaseContract.columns.isCompleted+"=?";
            String []selectionArgs=new String[]{""+1};
            int count = getApplicationContext().getContentResolver().delete(DatabaseContract.content_URI,where,selectionArgs);
            Log.i(TAG,"Deleted Job "+count);


            return jobParameters[0];
        }
    }

}
