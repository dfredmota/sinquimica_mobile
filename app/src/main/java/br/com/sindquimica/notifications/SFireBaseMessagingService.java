package br.com.sindquimica.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import br.com.sindquimica.R;
import br.com.sindquimica.activity.HomeAct;

/**
 * Created by fred on 12/01/17.
 */

public class SFireBaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String from = remoteMessage.getFrom();
        Log.d("Notificações","Mensagem recebida de:"+from);

        if(remoteMessage.getNotification() != null){
            Log.d("Notificações","Mensagem:"+remoteMessage.getNotification().getBody());

            showNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
        }
    }

    private void showNotification(String title, String body) {

        Intent intent = new Intent(this, HomeAct.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);

        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder n2 = new NotificationCompat.Builder(this);

        n2.setSmallIcon(R.drawable.ic_person_white_24dp);
        n2.setContentTitle(title);
        n2.setContentText(body);
        n2.setAutoCancel(true);
        n2.setSound(sound);
        n2.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,n2.build());

    }
}
