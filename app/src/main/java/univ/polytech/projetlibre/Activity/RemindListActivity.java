package univ.polytech.projetlibre.Activity;

import android.app.AlarmManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.support.design.widget.FloatingActionButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import univ.polytech.projetlibre.Database.DatabaseHelper;
import univ.polytech.projetlibre.Entity.Reminder;
import univ.polytech.projetlibre.Entity.User;
import univ.polytech.projetlibre.R;

public class RemindListActivity extends Activity {

    private FloatingActionButton FAB;
    private ListView rlv;
    private int getuserID;
    private int flag;
    private String getuserName;
    private LinkedHashSet<Reminder> reminder_set;
    private SimpleAdapter list_adapter;
    private List<Map<String, Object>> datalist;
    private List<String> medicinenameList = new ArrayList<>();
    private List<Integer> reminderIDList = new ArrayList<>();
    private List<Integer> medicineIDList = new ArrayList<>();
    private List<Integer> recordtimeList = new ArrayList<>();
    private List<String> recorddosageList = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind_list);
        Intent intent = this.getIntent();

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        FAB = findViewById(R.id.floatingActionButton);
        rlv = findViewById(R.id.reminderlistview);

        getuserID = (int)intent.getSerializableExtra("userID");
        getuserName = (String)intent.getSerializableExtra("userName");
        flag = (int)intent.getSerializableExtra("flag");

        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RemindListActivity.this,AddRemindActivity.class);
                intent.putExtra("userID",getuserID);
                intent.putExtra("userName",getuserName);
                startActivity(intent);
                finish();
            }
        });



        queryData();


        datalist = new ArrayList<>();
        list_adapter = new SimpleAdapter(RemindListActivity.this, getData(), R.layout.remindlistitems,
                new String[]{"remindertime", "remindermedicinename","reminderdosage"}, new int[]{R.id.remindertime, R.id.remindermedicinename, R.id.reminderdosage});
        rlv.setAdapter(list_adapter);

        rlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(flag == 0){
                    Intent intent = new Intent(RemindListActivity.this,ModificationRemindActivity.class);
                    intent.putExtra("userID",getuserID);
                    intent.putExtra("reminderID",reminderIDList.get(position));
                    intent.putExtra("medicineID",medicineIDList.get(position));
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(RemindListActivity.this,AddRecordActivity.class);
                    //        String sql3 = "create table if not exists record(" +
                    //                "recid integer NOT NULL PRIMARY KEY, " +
                    //                "recdate integer, " +
                    //                "rectime integer, " +
                    //                "recuserid integer, " +
                    //                "recmedid integer, " +
                    //                "FOREIGN KEY(recuserid) REFERENCES user(userid), " +
                    //                "FOREIGN KEY(recmedid) REFERENCES medicine(medid))";
                    //        db.execSQL(sql3);
                    intent.putExtra("userID",getuserID);
                    intent.putExtra("reminderID",reminderIDList.get(position));
                    intent.putExtra("medicineID",medicineIDList.get(position));
                    intent.putExtra("userName",getuserName);
                    intent.putExtra("recorddosage",recorddosageList.get(position));
                    intent.putExtra("recordtime",recordtimeList.get(position));
                    intent.putExtra("medicinename",medicinenameList.get(position));


                    startActivity(intent);
                    finish();
                }

            }
        });







    }





    private void queryData() {
        reminder_set = new LinkedHashSet<Reminder>();
        DatabaseHelper Helper = new DatabaseHelper(RemindListActivity.this,"database");
        SQLiteDatabase db = Helper.getWritableDatabase();
        int medicineID;
        //Cursor cursor = db.rawQuery("select * from reminder where remuserid = " + getuserID,null);
        Cursor cursor = db.rawQuery("select * from reminder where remuserid =" + getuserID,null);
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
                cursor1.close();
                reminder_set.add(new Reminder(remID, remtime, remdosage, remuserID, remmedID));
                reminderIDList.add(remID);
                medicineIDList.add(remmedID);
                recordtimeList.add(remtime);
                recorddosageList.add(remdosage);
            }
        }




    }

    private List<Map<String, Object>> getData(){
        int i = 0;
        if(reminder_set != null) {
            for(Reminder r:reminder_set){
                Map<String,Object> map = new LinkedHashMap<>();
                int time = r.getRemtime();
                int reminderhour = (time/100);
                int reminderminute = (time - (time/100)*100);
                String remindertimeformula;
                if(reminderminute<10){
                    remindertimeformula = reminderhour + ":0" + reminderminute;
                }else{
                    remindertimeformula = reminderhour + ":" + reminderminute;
                }
                map.put("remindertime",remindertimeformula);
                map.put("remindermedicinename",medicinenameList.get(i));
                map.put("reminderdosage",r.getRemdosage());
                i++;
                datalist.add(map);
            }
        }
        return datalist;
    }



}
