package univ.polytech.projetlibre.Tools;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.LinkedHashSet;

import univ.polytech.projetlibre.Activity.AddRecordActivity;
import univ.polytech.projetlibre.Activity.MainActivity;
import univ.polytech.projetlibre.Activity.RemindListActivity;
import univ.polytech.projetlibre.Database.DatabaseHelper;
import univ.polytech.projetlibre.Entity.Reminder;

public class NotificationClickReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("TAG", "userClick notification");
        int reminderID = (int)intent.getSerializableExtra("remid");
        Intent newIntent = new Intent(context, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        newIntent.putExtra("remid",reminderID);
        context.startActivity(newIntent);
    }
}
