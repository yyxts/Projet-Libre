package univ.polytech.projetlibre.Activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import univ.polytech.projetlibre.Database.DatabaseHelper;
import univ.polytech.projetlibre.Entity.Medicine;
import univ.polytech.projetlibre.Entity.Reminder;
import univ.polytech.projetlibre.Entity.User;
import univ.polytech.projetlibre.R;
import univ.polytech.projetlibre.Tools.AlertReceiver;

public class ModificationRemindActivity extends AppCompatActivity {

    private TextView tv_reminduser;
    private TextView tv_remindmedicine;
    private EditText et_reminddosage;
    private EditText et_remindtime;
    private Button button_finish;
    private Button button_cancel;

    private LinkedHashSet<Medicine> medicine_set;
    private LinkedHashSet<Reminder> reminder_set;
    private LinkedHashSet<User> user_set;
    private SimpleAdapter list_adapter;
    private List<Map<String, Object>> datalist;
    private List<String> medicinenameList = new ArrayList<>();
    private List<String> usernameList = new ArrayList<>();

    private int selectmedicineID;
    private int selectreminderID;
    private int selectuserID;
    private int selectremindertime;
    private String selectreminderdosage;
    private int newremindertime;
    private String newreminderdosage;
    private String alarmmessage;

    private static final int INTERVAL = 1000 * 60 * 60 * 24;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modification_remind);

        Intent intent = this.getIntent();

        tv_reminduser = findViewById(R.id.textView_remindUser);
        tv_remindmedicine = findViewById(R.id.textView_remindMedicine);
        et_reminddosage = findViewById(R.id.editText_reminderDosage);
        et_remindtime = findViewById(R.id.editText_reminderTime);
        button_finish = findViewById(R.id.button_reminderfinish);
        button_cancel = findViewById(R.id.button_remindercancel);

        selectuserID =  (int)getIntent().getSerializableExtra("userID");
        selectmedicineID = (int)getIntent().getSerializableExtra("medicineID");
        selectreminderID = (int)getIntent().getSerializableExtra("reminderID");

        queryData();

        tv_reminduser.setText(usernameList.get(0));
        tv_remindmedicine.setText(medicinenameList.get(0));
        et_reminddosage.setText(selectreminderdosage);
        et_remindtime.setText(timeFormat(selectremindertime));



        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ModificationRemindActivity.this,RemindListActivity.class);
                startActivity(intent);
                finish();
            }
        });

        button_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newreminderdosage = et_reminddosage.getText().toString();
                int newremindertime = timeTransfer(et_remindtime.getText().toString());
                DatabaseHelper dbHelper = new DatabaseHelper(ModificationRemindActivity.this, "database");
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                ContentValues reminder = new ContentValues();

                reminder.put("remdosage",newreminderdosage);
                reminder.put("remtime",newremindertime);

                db.update("reminder",reminder,"remid=?",new String[]{String.valueOf(selectreminderID)});
                db.close();

                alarmmessage = usernameList.get(0) + " please take " + newreminderdosage + " " + medicinenameList.get(0) + newremindertime;

                int alarmhour = newremindertime / 100;
                int alarmminute = newremindertime - (alarmhour * 100);

                Calendar c = Calendar.getInstance();
                c.set(Calendar.HOUR_OF_DAY, alarmhour);
                c.set(Calendar.MINUTE, alarmminute);
                c.set(Calendar.SECOND, 0);

                Log.i("alarmmessage",alarmmessage);

                cancelAlarm();

                startnewAlarm(c, alarmmessage);


                Intent intent1 = new Intent(ModificationRemindActivity.this,RemindListActivity.class);
                intent1.putExtra("userID",selectuserID);
                intent1.putExtra("userName",usernameList.get(0));
                startActivity(intent1);
                finish();
            }
        });




    }

    private void cancelAlarm(){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, selectreminderID, intent, 0);
        //Cancel Old alarm
        alarmManager.cancel(pendingIntent);
    }

    private void startnewAlarm(Calendar c, String message) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        intent.putExtra("message", message);
        //FLAG_UPDATE_CURRENT means update Intent
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, selectreminderID, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }

        //Repeat Alarm
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(), INTERVAL, pendingIntent);
    }

    private int timeTransfer(String time){
        int newtime = Integer.parseInt(time.replace(":",""));
        return newtime;
    }

    private String timeFormat(int time){
        int hour = time / 100;
        int minute = time - hour*100;
        String newtime;
        if(minute<10){
            newtime = hour + ":0" + minute;
        }else{
            newtime = hour + ":" + minute;
        }

        return newtime;
    }

    private void queryData() {
        reminder_set = new LinkedHashSet<Reminder>();
        DatabaseHelper Helper = new DatabaseHelper(ModificationRemindActivity.this,"database");
        SQLiteDatabase db = Helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from reminder where remid =" + selectreminderID,null);
        if(cursor != null){
            while(cursor.moveToNext()){

                int remID = cursor.getInt(cursor.getColumnIndex("remid"));
                int remtime = cursor.getInt(cursor.getColumnIndex("remtime"));
                int remuserid = cursor.getInt(cursor.getColumnIndex("remuserid"));
                String remdosage = cursor.getString(cursor.getColumnIndex("remdosage"));
                int remmedid = cursor.getInt(cursor.getColumnIndex("remmedid"));

                Cursor cursor1 = db.rawQuery("select * from user where userid =" + selectuserID,null);
                if(cursor1.moveToFirst()){
                    String lastname = cursor1.getString(cursor1.getColumnIndex("lastname"));
                    String firstname = cursor1.getString(cursor1.getColumnIndex("firstname"));
                    usernameList.add(lastname+firstname);
                }
                cursor1.close();

                Cursor cursor2 = db.rawQuery("select * from medicine where medid =" + selectmedicineID,null);
                if(cursor2.moveToFirst()){
                    String medicinename = cursor2.getString(cursor2.getColumnIndex("medname"));
                    medicinenameList.add(medicinename);
                }
                cursor2.close();

                selectremindertime = remtime;
                selectreminderdosage = remdosage;

                reminder_set.add(new Reminder(remID, remtime, remdosage, remuserid, remmedid));
            }

        }
    }



}
