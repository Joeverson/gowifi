package br.com.dup.services.notification;

import android.app.PendingIntent;
import android.content.Context;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;

import br.edu.ifpb.dup.imwifi.R;

/**
 * Created by root on 1/19/16.
 */
public class Notification {

    private static String ticker = "GoWifi";
    private static int notificationID = 001;

    public static void create(Context context, String title, String contextText, PendingIntent pendingIntent){

        //notificacao para o usuario de novas atualizacoes.
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setTicker(ticker);
        builder.setContentText(contextText);
        builder.setContentTitle(title);
        builder.setSmallIcon(R.mipmap.ic_directions_boat_white_24dp);

        // deixando opcional a implementacao dessa parada.
        if(pendingIntent != null)
            builder.setContentIntent(pendingIntent);


        NotificationManagerCompat nmc = NotificationManagerCompat.from(context);
        nmc.notify(notificationID, builder.build());
    }
}
