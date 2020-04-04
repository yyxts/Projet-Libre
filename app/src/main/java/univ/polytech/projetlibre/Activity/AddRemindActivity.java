package univ.polytech.projetlibre.Activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.AlarmClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import univ.polytech.projetlibre.Database.DatabaseHelper;
import univ.polytech.projetlibre.Entity.Medicine;
import univ.polytech.projetlibre.Entity.Reminder;
import univ.polytech.projetlibre.Entity.User;
import univ.polytech.projetlibre.R;
import univ.polytech.projetlibre.Tools.AlertReceiver;

public class AddRemindActivity extends AppCompatActivity{

    private NumberPicker numberPickerhour;
    private NumberPicker numberPickerminute;
    private NumberPicker numberPickerdosagenumber;
    private Spinner spinnerdosageunit;
    private List<String> dataList;
    private List<String> medicinenameList;
    private ArrayAdapter<String> adapter;
    private Spinner medicinespinner;
    private int remindernumber = 0;
    private int selectmedicineID = 0;
    private int selectremindtime;
    private int selectuserID;
    private EditText newmedname;
    private EditText newmedexpiretime;
    private Button addremind;

    private LinkedHashSet<Medicine> medicine_set;
    private LinkedHashSet<Reminder> reminder_set;
    private SimpleAdapter list_adapter;
    private List<Map<String, Object>> datalistmedicine;
    private String newmedicinename;
    private int newmedicineexpiretime;
    private int newreminderID;
    private String newreminderdosage;
    private String alarmmedicinename;
    private String alarmusername;
    private String alarmmessage;

    private static final int INTERVAL = 1000 * 60 * 60 * 24;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_remind);
        Intent intent = this.getIntent();



        numberPickerhour = findViewById(R.id.NumberPickerHour);
        numberPickerminute = findViewById(R.id.NumberPickerMinute);
        numberPickerdosagenumber = findViewById(R.id.NumberPickerDosageNumber);
        spinnerdosageunit = findViewById(R.id.spinnerDosageUnit);
        medicinespinner = findViewById(R.id.spinnermedicine);
        newmedname = findViewById(R.id.editText);
        newmedexpiretime = findViewById(R.id.editText2);
        addremind = findViewById(R.id.buttonfinish);
        selectuserID = (int)intent.getSerializableExtra("userID");
        alarmusername = (String)intent.getSerializableExtra("userName");

        numberPickerhour.setMaxValue(23);
        numberPickerhour.setMinValue(0);
        numberPickerhour.setValue(0);

        numberPickerminute.setMaxValue(59);
        numberPickerminute.setMinValue(0);
        numberPickerminute.setValue(0);

        numberPickerdosagenumber.setMaxValue(1000);
        numberPickerdosagenumber.setMinValue(0);
        numberPickerdosagenumber.setValue(0);

        dataList = new ArrayList<String>();
        dataList.add("cup");
        dataList.add("g");
        dataList.add("mg");
        dataList.add("tablet");
        dataList.add("ml");
        dataList.add("IU");




        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,dataList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerdosageunit.setAdapter(adapter);

        datalistmedicine = new ArrayList<>();

        medicinenameList = new ArrayList<>();

        queryData();

        ArrayAdapter medicineadapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line,medicinenameList);

        medicinespinner.setAdapter(medicineadapter);

        newreminderID = remindernumber+1;




        for(int i=0;i<datalistmedicine.size();i++){

            Log.i("List:",datalistmedicine.get(i).toString());
        }




        //Select medicine

        medicinespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int total = medicine_set.size();
                if(position < total){
                    selectmedicineID = position+1;
                    //ArrayList<Medicine> listmedicine = new ArrayList<Medicine>(medicine_set);
                    //String selectmedicinename = listmedicine.get(selectmedicineID-1).getMedname();
                    //Toast.makeText(AddRemindActivity.this, "medID is : "+ selectmedicinename, Toast.LENGTH_SHORT).show();
                    Toast.makeText(AddRemindActivity.this, "medID is : "+ selectmedicineID, Toast.LENGTH_SHORT).show();
                }else if(position == total){
                    selectmedicineID = 0;
                    Toast.makeText(AddRemindActivity.this, "medID is : "+ 0, Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        //Select remind time

        numberPickerhour.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                final int hour = newVal;
                numberPickerminute.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        int minute = newVal;
                        //new remindtime
                        selectremindtime = hour*100+minute;

                        Toast.makeText(AddRemindActivity.this, "Clock is : "+ hour + ":" + minute, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        //Select remind dosage

        numberPickerdosagenumber.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                final int dosagenumber = newVal;
                spinnerdosageunit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        newreminderdosage = dosagenumber + dataList.get(position);
                        Toast.makeText(AddRemindActivity.this, "Dosage is : "+ newreminderdosage , Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });






        //Button Click Listener for addRemind
        addremind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DatabaseHelper Helper = new DatabaseHelper(AddRemindActivity.this,"database");
                SQLiteDatabase db1 = Helper.getWritableDatabase();


                //if user want to add new medicine
                if(selectmedicineID == 0){
                    newmedicinename = newmedname.getText().toString();
                    newmedicineexpiretime = Integer.parseInt(newmedexpiretime.getText().toString());
                    ContentValues newmedicine = new ContentValues();
                    int total = medicine_set.size();

                    newmedicine.put("medid",total+1);
                    newmedicine.put("medname",newmedicinename);
                    newmedicine.put("medexpiredate",newmedicineexpiretime);

                    db1.insert("medicine", null, newmedicine);

                    alarmmedicinename = newmedicinename;

                    ContentValues newreminder = new ContentValues();
                    newreminder.put("remid", newreminderID);
                    newreminder.put("remtime",selectremindtime);
                    newreminder.put("remdosage",newreminderdosage);
                    newreminder.put("remuserid",selectuserID);
                    newreminder.put("remmedid",total+1);
                    db1.insert("reminder",null, newreminder);
                }else{
                    ContentValues newreminder = new ContentValues();
                    newreminder.put("remid", newreminderID);
                    newreminder.put("remtime",selectremindtime);
                    newreminder.put("remdosage",newreminderdosage);
                    newreminder.put("remuserid",selectuserID);
                    newreminder.put("remmedid",selectmedicineID);
                    db1.insert("reminder",null, newreminder);
                    ArrayList<Medicine> listmedicine = new ArrayList<Medicine>(medicine_set);


                    alarmmedicinename = listmedicine.get(selectmedicineID-1).getMedname();;
                }

                alarmmessage = alarmusername + " please take " + newreminderdosage + " " + alarmmedicinename;
                int alarmhour = selectremindtime / 100;
                int alarmminute = selectremindtime - ((selectremindtime/100) * 100);

                //createAlarm(alarmmessage,alarmhour,alarmminute);

                Calendar c = Calendar.getInstance();
                c.set(Calendar.HOUR_OF_DAY, alarmhour);
                c.set(Calendar.MINUTE, alarmminute);
                c.set(Calendar.SECOND, 0);

                Log.i("alarmmessage",alarmmessage);

                startAlarm(c, alarmmessage);


                Intent intent = new Intent(AddRemindActivity.this,MainActivity.class);
                startActivity(intent);



            }
        });

    }


    private void startAlarm(Calendar c, String message) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        intent.putExtra("message", message);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }

        //Repeat Alarm
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(), INTERVAL, pendingIntent);
        //alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }


    private void queryData() {
        medicine_set = new LinkedHashSet<Medicine>();
        reminder_set = new LinkedHashSet<Reminder>();
        DatabaseHelper Helper = new DatabaseHelper(AddRemindActivity.this,"database");
        SQLiteDatabase db = Helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from medicine",null);
        if(cursor != null){
            while(cursor.moveToNext()){
                //public Medicine(String medname, int medID, int medexpiredate)
                int medID = cursor.getInt(cursor.getColumnIndex("medid"));
                int medexpiredate = cursor.getInt(cursor.getColumnIndex("medexpiredate"));
                String medname = cursor.getString(cursor.getColumnIndex("medname"));


                medicine_set.add(new Medicine(medID, medname, medexpiredate));
                medicinenameList.add(medname);
            }
        }
        medicinenameList.add("I want to add a new medicine");

        Cursor cursor2 = db.rawQuery("select * from reminder",null);
        if(cursor2 != null){
            while(cursor2.moveToNext()){
                int remID = cursor2.getInt(cursor2.getColumnIndex("remid"));
                int remtime = cursor2.getInt(cursor2.getColumnIndex("remtime"));
                String remdosage = cursor2.getString(cursor2.getColumnIndex("remdosage"));
                int remuserID = cursor2.getInt(cursor2.getColumnIndex("remuserid"));
                int remmedID = cursor2.getInt(cursor2.getColumnIndex("remmedid"));
                remindernumber++;
                reminder_set.add(new Reminder(remID, remtime, remdosage, remuserID, remmedID));
            }
        }
    }

    private List<Map<String, Object>> getData(){
        if(medicine_set != null) {
            for(Medicine m:medicine_set){
                Map<String,Object> map = new LinkedHashMap<>();
                map.put("medicine",m.getMedname());

                datalistmedicine.add(map);
            }
            String notfound = "I want add a new medicine";
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("",notfound);
            datalistmedicine.add(map);



        }
        return datalistmedicine;
    }



}
