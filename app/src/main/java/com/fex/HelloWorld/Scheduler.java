package com.fex.HelloWorld;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import androidx.annotation.RequiresApi;

public class Scheduler {
    private static final String TAG = "MainActivityUtil";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void scheduleJob(Context context) {

        ComponentName componentName = new ComponentName(context, ExampleJobService.class);

        JobInfo info = new JobInfo.Builder(123, componentName)
                .setPersisted(true)
                .setMinimumLatency(15 * 60 * 1000) // wait at least
                .setOverrideDeadline(15 * 60 * 1000) // maximum delay
                .build();

        JobScheduler scheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        int resultCode = scheduler.schedule(info);
        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d(TAG, "Job scheduled");
        } else {
            Log.d(TAG, "Job scheduling failed");
        }

    }
}