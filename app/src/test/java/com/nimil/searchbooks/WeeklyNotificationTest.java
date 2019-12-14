package com.nimil.searchbooks;



import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;
import androidx.work.Configuration;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import androidx.work.impl.utils.SynchronousExecutor;
import androidx.work.testing.TestDriver;
import androidx.work.testing.WorkManagerTestInitHelper;
//import androidx.work.


import com.nimil.searchbooks.BookNotify.BookNotifyWorker;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
@Config(manifest= Config.NONE)
public class WeeklyNotificationTest {
    public Context mContext;
    @Before
    public void setUp(){
        mContext= InstrumentationRegistry.getInstrumentation().getContext();
        Configuration config = new Configuration.Builder()
                // Set log level to Log.DEBUG to
                // make it easier to see why tests failed
                .setMinimumLoggingLevel(Log.DEBUG)
                // Use a SynchronousExecutor to make it easier to write tests
                //.setExecutor(new SynchronousExecutor())
                .build();

        // Initialize WorkManager for instrumentation tests.
        WorkManagerTestInitHelper.initializeTestWorkManager(
                mContext, config);
    }

    @Test
    public void testNotificationWorkManager(){
        // Create request
        PeriodicWorkRequest request =
                new PeriodicWorkRequest.Builder(BookNotifyWorker.class, 7, TimeUnit.DAYS)
                        .build();

        WorkManager workManager = WorkManager.getInstance(mContext);
        TestDriver testDriver = WorkManagerTestInitHelper.getTestDriver();
        WorkInfo workInfo = null;
        try {
        // Enqueue
        workManager.enqueue(request).getResult().get();
        // Tells the testing framework the period delay is met
        testDriver.setPeriodDelayMet(request.getId());
        // Get WorkInfo and outputData


            workInfo = workManager.getWorkInfoById(request.getId()).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Assert
        assertThat(workInfo.getState(), is(WorkInfo.State.ENQUEUED));

       /* OneTimeWorkRequest request =
                new OneTimeWorkRequest.Builder(BookNotifyWorker.class)
                        .build();

        WorkManager workManager = WorkManager.getInstance(mContext);
        WorkInfo workInfo =null;
        // Enqueue and wait for result. This also runs the Worker synchronously
        // because we are using a SynchronousExecutor.
        try {
            workManager.enqueue(request).getResult().get();

        // Get WorkInfo and outputData
        workInfo = workManager.getWorkInfoById(request.getId()).get();
        //Data outputData = workInfo.getOutputData();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Assert
        assertThat(workInfo.getState(), is(WorkInfo.State.SUCCEEDED));*/
    }

}