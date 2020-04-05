package univ.polytech.projetlibre.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import pl.com.salsoft.sqlitestudioremote.SQLiteStudioService;
import univ.polytech.projetlibre.Database.DatabaseHelper;
import univ.polytech.projetlibre.Entity.Record;
import univ.polytech.projetlibre.Entity.Reminder;
import univ.polytech.projetlibre.R;

//Main Activity

public class MainActivity extends AppCompatActivity {

    private Button buttonReminder;
    private Button buttonMedicine;
    private Button buttonRecord;
    private Button buttonQuit;
    private Button buttonRecordList;
    private Button buttonUserList;
    private int reminderID;
    private LinkedHashSet<Reminder> reminder_set;
    private SimpleAdapter list_adapter;
    private List<Map<String, Object>> datalist;
    private List<String> medicinenameList = new ArrayList<>();
    private List<Integer> reminderIDList = new ArrayList<>();
    private List<Integer> medicineIDList = new ArrayList<>();
    private List<Integer> recordtimeList = new ArrayList<>();
    private List<Integer> userIDList = new ArrayList<>();
    private List<String> recorddosageList = new ArrayList<>();
    private List<String> usernameList = new ArrayList<>();
    private int recorduserID;
    private int recordreminderID;
    private int recordmedicienID;
    private String recordusername;
    private String recorddosage;
    private String recordmedicienname;
    private int recordtime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = this.getIntent();

        buttonReminder = findViewById(R.id.button1);
        buttonMedicine = findViewById(R.id.button2);
        buttonQuit = findViewById(R.id.button3);
        buttonRecord = findViewById(R.id.button4);
        buttonRecordList = findViewById(R.id.button5);
        buttonUserList = findViewById(R.id.button6);

        if(intent.getSerializableExtra("remid")!=null){
            reminderID = (int)intent.getSerializableExtra("remid");
            Log.i("ID",reminderID+"");
            queryData();
            Intent intent1 = new Intent(MainActivity.this, AddRecordActivity.class);
            recorduserID = userIDList.get(0);
            recordreminderID = reminderID;
            recordmedicienID = medicineIDList.get(0);
            recordusername = usernameList.get(0);
            recorddosage = recorddosageList.get(0);
            recordtime = recordtimeList.get(0);
            recordmedicienname = medicinenameList.get(0);
            //        recorduserID = (int)intent.getSerializableExtra("userID");
            //        recordreminderID = (int)intent.getSerializableExtra("reminderID");
            //        recordmedicienID = (int)intent.getSerializableExtra("medicineID");
            //        recordusername = (String)intent.getSerializableExtra("userName");
            //        recorddosage = (String)intent.getSerializableExtra("recorddosage");
            //        recordtime = (int)intent.getSerializableExtra("recordtime");
            //recordmedicinename = (String)intent.getSerializableExtra("medicinename");

            intent1.putExtra("userID",recorduserID);
            intent1.putExtra("reminderID",recordreminderID);
            intent1.putExtra("medicineID",recordreminderID);
            intent1.putExtra("userName",recordusername);
            intent1.putExtra("recorddosage",recorddosage);
            intent1.putExtra("recordtime",recordtime);
            intent1.putExtra("medicinename",recordmedicienname);
            startActivity(intent1);

        }


        //You can use the following example data to initialize
/*
        //Database helper
        DatabaseHelper dbHelper = new DatabaseHelper(MainActivity.this, "database");
        //Create Database
        SQLiteDatabase db1 = dbHelper.getReadableDatabase();

        SQLiteStudioService.instance().start(this);

        //public User(String lastname, String firstname, int userID, String address, int birthday, int sex, String phone)
        //public Medicine(String medname, int medID, int medexpiredate)
        //Reminder(int remID, int userID, String remdosage, int medID, int remtime)

        DatabaseHelper dbHelper3 = new DatabaseHelper(MainActivity.this, "database");
        SQLiteDatabase db2 = dbHelper3.getWritableDatabase();

        ContentValues reminder1 = new ContentValues();
        ContentValues reminder2 = new ContentValues();

        reminder1.put("remid",1);
        reminder1.put("remtime",800);
        reminder1.put("remuserid",1);
        reminder1.put("remdosage","1cap");
        reminder1.put("remmedid",1);

        reminder2.put("remid",2);
        reminder2.put("remtime",1200);
        reminder2.put("remuserid",2);
        reminder2.put("remdosage","2cap");
        reminder2.put("remmedid",2);


        ContentValues medicine1 = new ContentValues();
        ContentValues medicine2 = new ContentValues();

        medicine1.put("medid",1);
        medicine1.put("medname","doliprane");
        medicine1.put("medexpiredate","20201231");


        medicine2.put("medid",2);
        medicine2.put("medname","spasmag");
        medicine2.put("medexpiredate","20200630");



        ContentValues values = new ContentValues();
        ContentValues values1 = new ContentValues();

        values.put("userid", 1);
        values.put("lastname","zhang");
        values.put("firstname","bolun");
        values.put("address","1RUETOURS");
        values.put("birthday","19950910");
        values.put("sex","1");
        values.put("phone","1234567");

        values1.put("userid", 2);
        values1.put("lastname","liu");
        values1.put("firstname","yan");
        values1.put("address","2RUETOURS");
        values1.put("birthday","19940511");
        values1.put("sex","0");
        values1.put("phone","23456789");



        //Insert example data to database
        db2.insert("medicine", null, medicine1);
        db2.insert("medicine",null, medicine2);

        db2.insert("user", null, values);
        db2.insert("user",null, values1);

        db2.insert("reminder", null, reminder1);
        db2.insert("reminder",null, reminder2);

*/

        //flag = 0 reminder  flag=1 record flag=2 recordlist
        buttonReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,UserListActivity.class);
                intent.putExtra("flag",0);
                startActivity(intent);
            }
        });

        buttonMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MedicineListActivity.class);
                startActivity(intent);

            }
        });

        buttonRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,UserListActivity.class);
                intent.putExtra("flag",1);
                startActivity(intent);
            }
        });

        buttonRecordList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,UserListActivity.class);
                intent.putExtra("flag",2);
                startActivity(intent);
            }
        });

        buttonUserList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,UserListActivity.class);
                intent.putExtra("flag",3);
                startActivity(intent);
            }
        });

        buttonQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                System.exit(0);
            }
        });

    }

    private void queryData() {
        reminder_set = new LinkedHashSet<Reminder>();
        DatabaseHelper Helper = new DatabaseHelper(MainActivity.this,"database");
        SQLiteDatabase db = Helper.getWritableDatabase();
        int medicineID;
        //Cursor cursor = db.rawQuery("select * from reminder where remuserid = " + getuserID,null);
        Cursor cursor = db.rawQuery("select * from reminder where remid =" + reminderID,null);
        if(cursor != null){
            while(cursor.moveToNext()){
                //   public Reminder(int remID, int remtime, String remdosage, int userID, int medID)
                int remID = cursor.getInt(cursor.getColumnIndex("remid"));
                int remtime = cursor.getInt(cursor.getColumnIndex("remtime"));
                String remdosage = cursor.getString(cursor.getColumnIndex("remdosage"));
                int remuserID = cursor.getInt(cursor.getColumnIndex("remuserid"));
                int remmedID = cursor.getInt(cursor.getColumnIndex("remmedid"));
                //取药名
                Cursor cursor1 = db.rawQuery("select * from medicine where medid =" + remmedID,null);
                if(cursor1.moveToFirst()){
                    String medicinename = cursor1.getString(cursor1.getColumnIndex("medname"));
                    medicinenameList.add(medicinename);
                }
                Cursor cursor2 = db.rawQuery("select * from user where userid =" + remuserID, null);
                if(cursor2.moveToFirst()){
                    String userlastname = cursor2.getString(cursor2.getColumnIndex("lastname"));
                    String userfirstname = cursor2.getString(cursor2.getColumnIndex("firstname"));
                    String username = userlastname+userfirstname;
                    usernameList.add(username);
                }

                cursor1.close();
                reminder_set.add(new Reminder(remID, remtime, remdosage, remuserID, remmedID));
                userIDList.add(remuserID);
                reminderIDList.add(remID);
                medicineIDList.add(remmedID);
                recordtimeList.add(remtime);
                recorddosageList.add(remdosage);
            }
        }




    }

}
