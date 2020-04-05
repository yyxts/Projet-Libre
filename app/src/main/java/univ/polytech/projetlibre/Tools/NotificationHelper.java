package univ.polytech.projetlibre.Tools;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import univ.polytech.projetlibre.R;

//This class is for receive the notification(Alarm)
public class NotificationHelper extends ContextWrapper {
    public static final String channelID = "channelID";
    public static final String channelName = "Channel Name";

    private NotificationManager mManager;

    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);

        getManager().createNotificationChannel(channel);
    }

    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return mManager;
    }

    public NotificationCompat.Builder getChannelNotification(String message, int id) {
        Intent clickIntent = new Intent(getApplicationContext(),NotificationClickReceiver.class);
        clickIntent.putExtra("remid",id);
        int id1 = (int) (System.currentTimeMillis() / 1000);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), id1, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);

      return new NotificationCompat.Builder(getApplicationContext(), channelID)
        .setContentTitle("Health Care Reminder")
        .setContentText(message)
        .setSmallIcon(R.mipmap.ic_healthcare)
        .setContentIntent(pendingIntent)
        .setDefaults(Notification.DEFAULT_ALL)
        .setAutoCancel(true);
        /*NotificationCompat.Builder builder1 = new NotificationCompat.Builder(getApplicationContext(), channelID);
        builder1.setContentTitle("Health Care Reminder");
        builder1.setContentText(message);
        builder1.setSmallIcon(R.mipmap.ic_healthcare);
        builder1.setDefaults(Notification.DEFAULT_ALL);
        builder1.setAutoCancel(true);
        Intent intent =new Intent (this,NotificationClickReceiver.class);
        PendingIntent pendingIntent =PendingIntent.getBroadcast(this, 0, intent, 0);
        builder1.setContentIntent(pendingIntent);
        return builder1;*/


    }
}
