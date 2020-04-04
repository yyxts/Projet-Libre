package univ.polytech.projetlibre.Tools;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String message = (String)intent.getSerializableExtra("message");

        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification(message);
        int randomnumber = (int)Math.random() * 100;
        notificationHelper.getManager().notify(randomnumber, nb.build());
        Log.i("setAlarm","Notify ID:"+randomnumber);
    }
}
