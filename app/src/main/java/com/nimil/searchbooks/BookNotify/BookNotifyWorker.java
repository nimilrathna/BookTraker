package com.nimil.searchbooks.BookNotify;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.UserDictionary;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.nimil.searchbooks.MainActivity;
import com.nimil.searchbooks.R;

public class BookNotifyWorker extends Worker {
    private static final String ACTION_UPDATE_NOTIFICATION = "com.nimil.searchbooks.ACTION_UPDATE_NOTIFICATION";
    private static final String NOTIFICATION_DELETE_ACTION = "com.nimil.searchbooks.NOTIFICATION_DELETE_ACTION";
    Context mContext;
    // Notification channel ID.
    private static final String CHANNEL_ID = "primary_notification_channel";
    // Notification ID.
    private static final int NOTIFICATION_ID = 0;
    public BookNotifyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        mContext=context;
    }

    @NonNull
    @Override
    public Result doWork() {
        makeStatusNotification("Abegail's Journey",mContext);
        return Result.success();
    }

    static void makeStatusNotification(String message,Context context) {

        // Make a channel if necessary
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            CharSequence name = "bookNotificationChannel";
            String description = "Notification channel for sending book title weekly";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel =
                    new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            // Add the channel
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        // Create the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Book of the Week")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(new long[0]);

        // Show the notification
        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder.build());
    }

}
