package univ.polytech.projetlibre.Tools;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

//This class is for receive the notification(Alarm)

public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String message = (String)intent.getSerializableExtra("message");
        int remid = (int)intent.getSerializableExtra("remid");

        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification(message,remid);




        int randomnumber = (int)Math.random() * 100;
        notificationHelper.getManager().notify(randomnumber, nb.build());
        Log.i("setAlarm","Notify ID:"+randomnumber);
    }
}
