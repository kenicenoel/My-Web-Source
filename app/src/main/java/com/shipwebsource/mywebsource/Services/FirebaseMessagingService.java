package com.shipwebsource.mywebsource.Services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;
import com.shipwebsource.mywebsource.MainActivity;
import com.shipwebsource.mywebsource.R;

/**
 * Created by Software Developer on 1/17/2017.
 */


public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService
{
    private final String CONTENT_TITLE  = "My Web Source";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage)
    {
        showNotification(remoteMessage.getData().get("message"));


    }

    private void showNotification(String message)
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle(CONTENT_TITLE)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}
