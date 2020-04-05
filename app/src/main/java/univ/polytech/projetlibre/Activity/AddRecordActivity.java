package univ.polytech.projetlibre.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;

import univ.polytech.projetlibre.Database.DatabaseHelper;
import univ.polytech.projetlibre.Entity.Medicine;
import univ.polytech.projetlibre.Entity.Record;
import univ.polytech.projetlibre.Entity.Reminder;
import univ.polytech.projetlibre.R;

//Activity for adding a record

public class AddRecordActivity extends AppCompatActivity {

    private int recorduserID;
    private int recordreminderID;
    private int recordmedicienID;
    private String recordmedicinename;
    private String recordusername;
    private String recorddosage;
    private int recordtime;
    private int recorddate;
    private TextView tv_recordusername;
    private TextView tv_recordmedicinename;
    private TextView tv_recorddate;
    private TextView tv_recordtime;
    private TextView tv_recordmedicinedosage;
    private EditText et_recordcomment;
    private LinkedHashSet<Record> record_set;

    private Button button_finish;
    private Button button_cancel;
    private int recordcount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
        Intent intent = this.getIntent();

        tv_recordusername = findViewById(R.id.textView_RecordUserName);
        tv_recordmedicinename = findViewById(R.id.textView_RecordMedicineName);
        tv_recorddate = findViewById(R.id.textView_RecordDate);
        tv_recordtime = findViewById(R.id.textView_RecordTime);
        tv_recordmedicinedosage = findViewById(R.id.textView_RecordMedicineDosage);
        et_recordcomment = findViewById(R.id.editText_comment);
        button_finish = findViewById(R.id.button_recordfinish);
        button_cancel = findViewById(R.id.button_recordcancel);


        recorduserID = (int)intent.getSerializableExtra("userID");
        recordreminderID = (int)intent.getSerializableExtra("reminderID");
        recordmedicienID = (int)intent.getSerializableExtra("medicineID");
        recordusername = (String)intent.getSerializableExtra("userName");
        recorddosage = (String)intent.getSerializableExtra("recorddosage");
        recordtime = (int)intent.getSerializableExtra("recordtime");

        recordmedicinename = (String)intent.getSerializableExtra("medicinename");

        Calendar cal=Calendar.getInstance();
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH);
        int day=cal.get(Calendar.DATE);

        Date date=new Date();
        SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd");//设置当前时间的格式，为年-月-日
        String recorddateString = dateFormat.format(date);
        recorddate = Integer.parseInt(recorddateString.replace("-",""));

        tv_recordusername.setText(recordusername);
        tv_recordmedicinename.setText(recordmedicinename);
        tv_recorddate.setText(recorddateString);
        tv_recordtime.setText(timeFormat(recordtime));
        tv_recordmedicinedosage.setText(recorddosage);

        queryData();


        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddRecordActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        button_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseHelper Helper = new DatabaseHelper(AddRecordActivity.this,"database");
                SQLiteDatabase db1 = Helper.getWritableDatabase();


                ContentValues newrecord = new ContentValues();
                newrecord.put("recid",recordcount+1);
                newrecord.put("recdate",recorddate);
                newrecord.put("rectime",recordtime);
                newrecord.put("recdosage",recorddosage);
                newrecord.put("reccomment",et_recordcomment.getText().toString());
                newrecord.put("recuserid",recorduserID);
                newrecord.put("recmedid",recordmedicienID);

                db1.insert("record", null, newrecord);

                Log.i("insert record"," recid:" + (recordcount+1) + " recdate:" + recorddate + " rectime:" + recordtime + " recdosage:" + recorddosage + " reccomment:" + et_recordcomment.getText().toString() + " recuserid:" + recorduserID + " recmedid:" + recordmedicienID);



                Intent intent = new Intent(AddRecordActivity.this,MainActivity.class);
                startActivity(intent);
                finish();

            }
        });


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

    private int dateTransfer(String date){
        date.replace("/","");
        int newdate = Integer.parseInt(date);
        return newdate;
    }

    private String datenewFormat(int date){
        int year = date / 10000;
        int month = (date - year*10000) / 100;
        int day = (date - year *10000 - month*100);
        String newdate = year + "/" + month + "/" + day;
        return newdate;
    }

    private void queryData() {
        record_set = new LinkedHashSet<Record>();

        DatabaseHelper Helper = new DatabaseHelper(AddRecordActivity.this,"database");
        SQLiteDatabase db = Helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from record",null);
        if(cursor != null){
            while(cursor.moveToNext()){
                int recID = cursor.getInt(cursor.getColumnIndex("recid"));
                int recdate = cursor.getInt(cursor.getColumnIndex("recdate"));
                int rectime = cursor.getInt(cursor.getColumnIndex("rectime"));
                String recdosage = cursor.getString(cursor.getColumnIndex("recdosage"));
                String reccomment = cursor.getString(cursor.getColumnIndex("reccomment"));
                int recuserid = cursor.getInt(cursor.getColumnIndex("recuserid"));
                int recmedid = cursor.getInt(cursor.getColumnIndex("recmedid"));
                recordcount = recordcount + 1;
                record_set.add(new Record(recID,recdate,rectime,recdosage,reccomment,recuserid,recmedid));


            }
        }
    }


}
