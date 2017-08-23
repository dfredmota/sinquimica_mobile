package br.com.sindquimicace.notifications;

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

import br.com.sindquimicace.R;
import br.com.sindquimicace.activity.EventosAct;
import br.com.sindquimicace.activity.HomeAct;

/**
 * Created by fred on 12/01/17.
 */

public class SFireBaseMessagingService extends FirebaseMessagingService {


    int notificationId = 1 ;

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


        Intent intent2 = new Intent(this, EventosAct.class);

        intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent2 = PendingIntent.getActivity(this,0,intent2,PendingIntent.FLAG_ONE_SHOT);

        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder n2 = new NotificationCompat.Builder(this);

        n2.setSmallIcon(R.drawable.logo);
        n2.setContentTitle(title);
        n2.setContentText(body);
        n2.setAutoCancel(true);
        n2.setSound(sound);

        if(body.equalsIgnoreCase("Você tem uma Nova Mensagem!"))
        n2.setContentIntent(pendingIntent);
        else if(body.equalsIgnoreCase("Você tem um Novo Evento!") || body.equalsIgnoreCase("Cancelamento de Evento!"))
        n2.setContentIntent(pendingIntent2);

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId,n2.build());

    }
}
