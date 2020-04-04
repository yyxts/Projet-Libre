package univ.polytech.projetlibre.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.io.Serializable;

import pl.com.salsoft.sqlitestudioremote.SQLiteStudioService;
import univ.polytech.projetlibre.Database.DatabaseHelper;
import univ.polytech.projetlibre.R;

public class MainActivity extends AppCompatActivity {

    private Button buttonReminder;
    private Button buttonMedicine;
    private Button buttonRecord;
    private Button buttonQuit;
    private Button buttonRecordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonReminder = findViewById(R.id.button1);
        buttonMedicine = findViewById(R.id.button2);
        buttonQuit = findViewById(R.id.button3);
        buttonRecord = findViewById(R.id.button4);
        buttonRecordList = findViewById(R.id.button5);


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


        //创建存放数据的ContentValues对象
       ContentValues values = new ContentValues();
        ContentValues values1 = new ContentValues();
        //像ContentValues中存放数据
        values.put("userid", 1);
        values.put("lastname","zhang");
        values.put("firstname","bolun");
        values.put("address","6RUEDELABONDONNIERE");
        values.put("birthday","19950910");
        values.put("sex","1");
        values.put("phone","602153726");

        values1.put("userid", 2);
        values1.put("lastname","liu");
        values1.put("firstname","yan");
        values1.put("address","6RUEDELABONDONNIERE");
        values1.put("birthday","19940511");
        values1.put("sex","0");
        values1.put("phone","770019925");






        //数据库执行插入命令
        db2.insert("medicine", null, medicine1);
        db2.insert("medicine",null, medicine2);

        //数据库执行插入命令
        db2.insert("user", null, values);
        db2.insert("user",null, values1);

        //数据库执行插入命令
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


}
