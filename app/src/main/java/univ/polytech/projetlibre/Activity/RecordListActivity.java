package univ.polytech.projetlibre.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import univ.polytech.projetlibre.Database.DatabaseHelper;
import univ.polytech.projetlibre.Entity.Medicine;
import univ.polytech.projetlibre.Entity.Record;
import univ.polytech.projetlibre.Entity.Reminder;
import univ.polytech.projetlibre.Entity.User;
import univ.polytech.projetlibre.R;

public class RecordListActivity extends AppCompatActivity {

    private ListView listView_record;
    private FloatingActionButton FAB_email;
    private LinkedHashSet<Record> record_set;
    private LinkedHashSet<Medicine> medicine_set;
    private List<String> medicinenameList = new ArrayList<>();
    private List<String> medicinedosageList = new ArrayList<>();
    private List<Integer> recorddateList = new ArrayList<>();
    private List<Integer> recordtimeList = new ArrayList<>();
    private List<String> recordcommentList = new ArrayList<>();
    private List<Map<String, Object>> datalist;

    private int recordlistuserID;
    private String recordlistuserName;

    private SimpleAdapter list_adapter;

    private EditText et_email;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_list);
        Intent intent = this.getIntent();

        listView_record = findViewById(R.id.ListView_Record);
        FAB_email = findViewById(R.id.floatingActionButton2);
        et_email = findViewById(R.id.editText_email);

        recordlistuserID = (int)intent.getSerializableExtra("userID");
        recordlistuserName = (String)intent.getSerializableExtra("userName");

        queryData();


        datalist = new ArrayList<>();

        //                map.put("recorddate",datenewFormat(date));
        //                map.put("recordtime",timeFormat(time));
        //                map.put("recordmedicine",medicinenameList.get(i));
        //                map.put("reminderdosage",r.getRecdosage());
        //                map.put("recordcomment",r.getReccomment());

        list_adapter = new SimpleAdapter(RecordListActivity.this, getData(), R.layout.recordlistitems,
                new String[]{"recorddate", "recordtime","recordmedicine","reminderdosage","recordcomment"}, new int[]{R.id.recorddate, R.id.recordtime, R.id.recordmedicine, R.id.recorddosage, R.id.recordcomment});
        listView_record.setAdapter(list_adapter);

        FAB_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data=new Intent(Intent.ACTION_SENDTO);

                data.setData(Uri.parse("mailto:"+et_email.getText().toString()));

                data.putExtra(Intent.EXTRA_SUBJECT, "Medicine Record: " + recordlistuserName);

                String EmailText = "The following records are Medication Record for " + recordlistuserName + ":\n\n";

                String recordDetail;

                //循环输出
                for(int i=0;i<record_set.size();i++){
                    int number = i+1;
                    recordDetail = "Record #" + number + ":\n" +
                            "Date: " +  datenewFormat(recorddateList.get(i)) + " Time: " + timeFormat(recordtimeList.get(i)) + "\n"
                            + " Medicine Name: " + medicinenameList.get(i) + " Dosage: " + medicinedosageList.get(i) + " \nComment: "
                            + recordcommentList.get(i) + "\n\n";
                    EmailText = EmailText + recordDetail;
                }
                //这句话是把EmailText作为正文
                data.putExtra(Intent.EXTRA_TEXT, EmailText);
                startActivity(data);
            }
        });

    }

    private void queryData() {
        record_set = new LinkedHashSet<Record>();

        DatabaseHelper Helper = new DatabaseHelper(RecordListActivity.this,"database");
        SQLiteDatabase db = Helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from record where recuserid ="+ recordlistuserID,null);
        if(cursor != null){
            while(cursor.moveToNext()){
                int recID = cursor.getInt(cursor.getColumnIndex("recid"));
                int recdate = cursor.getInt(cursor.getColumnIndex("recdate"));
                int rectime = cursor.getInt(cursor.getColumnIndex("rectime"));
                String recdosage = cursor.getString(cursor.getColumnIndex("recdosage"));
                String reccomment = cursor.getString(cursor.getColumnIndex("reccomment"));
                int recuserid = cursor.getInt(cursor.getColumnIndex("recuserid"));
                int recmedid = cursor.getInt(cursor.getColumnIndex("recmedid"));

                Cursor cursor1 = db.rawQuery("select * from medicine where medid ="+ recmedid,null);
                if(cursor1.moveToNext()){
                    String medname = cursor1.getString(cursor1.getColumnIndex("medname"));
                    medicinenameList.add(medname);
                }
                cursor1.close();
                medicinedosageList.add(recdosage);
                recorddateList.add(recdate);
                recordtimeList.add(rectime);
                recordcommentList.add(reccomment);

                record_set.add(new Record(recID,recdate,rectime,recdosage,reccomment,recuserid,recmedid));
            }
        }
    }

    private List<Map<String, Object>> getData(){
        int i = 0;
        if(record_set != null) {
            for(Record r:record_set){
                Map<String,Object> map = new LinkedHashMap<>();
                int time = r.getRectime();
                int date = r.getRecdate();

                map.put("recorddate",datenewFormat(date));
                map.put("recordtime",timeFormat(time));
                map.put("recordmedicine",medicinenameList.get(i));
                map.put("reminderdosage",r.getRecdosage());
                map.put("recordcomment",r.getReccomment());
                i++;
                datalist.add(map);
            }
        }
        return datalist;
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




}
