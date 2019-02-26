package com.headspire.firebasetest;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFireBaseMessage extends FirebaseMessagingService {
    @Override
    public void onNewToken(String s) {
        Log.e("Tagg",s);
    }

    @Override
    public void onDeletedMessages() {
        Log.e("Tagg","onDeletedMessages");
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e("Tagg","onMessageReceived");
        sendNotification(remoteMessage.getData().get("key"));
    }
    private void sendNotification(String body) {
        Intent intent=new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent= (PendingIntent) PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
        Uri notificationSound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        String chanelId ="tagg";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "BrusselsAir";
            String description = "BrusselsAir App";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(chanelId, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
        NotificationCompat.Builder notificationBuilder=new NotificationCompat.Builder(this,chanelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("notification")
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(notificationSound)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,notificationBuilder.build());
    }
}
