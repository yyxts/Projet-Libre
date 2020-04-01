package univ.polytech.projetlibre.Tools;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import univ.polytech.projetlibre.Activity.AddRecordActivity;

public class NotificationClickReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("TAG", "userClick notification");
        Intent newIntent = new Intent(context, AddRecordActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(newIntent);
    }



}
