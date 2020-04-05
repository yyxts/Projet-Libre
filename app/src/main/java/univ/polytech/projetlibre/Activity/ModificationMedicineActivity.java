package univ.polytech.projetlibre.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import univ.polytech.projetlibre.Database.DatabaseHelper;
import univ.polytech.projetlibre.Entity.Medicine;
import univ.polytech.projetlibre.R;

//Activity for modification medicine informations

public class ModificationMedicineActivity extends AppCompatActivity {

    private TextView tv_medID;
    private EditText et_medname;
    private EditText et_medexpiretime;
    private Button button_finish;
    private Button button_cancel;

    private LinkedHashSet<Medicine> medicine_set;
    private SimpleAdapter list_adapter;
    private List<Map<String, Object>> datalist;
    private int clickmedicineID;
    private List<Medicine> medicineList = new ArrayList<>();

    private String selectmedicinename;
    private int selectmedicineexpiredate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modification_medicine);

        tv_medID = findViewById(R.id.Textview_medicineID);
        et_medname = findViewById(R.id.editText_medicinename);
        et_medexpiretime = findViewById(R.id.editText_medicineexpiredate);
        button_finish = findViewById(R.id.button_finish);
        button_cancel = findViewById(R.id.button_cancel);

        clickmedicineID = (int) getIntent().getSerializableExtra("medID");

        queryData();



        tv_medID.setText(String.valueOf(clickmedicineID));
        et_medname.setText(selectmedicinename);
        et_medexpiretime.setText(dateFormat(selectmedicineexpiredate));

        button_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newmedicinename = et_medname.getText().toString();
                int newmedicineexpiretime = dateTransfer(et_medexpiretime.getText().toString());
                DatabaseHelper dbHelper = new DatabaseHelper(ModificationMedicineActivity.this, "database");
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                ContentValues medicine = new ContentValues();

                medicine.put("medname",newmedicinename);
                medicine.put("medexpiredate",newmedicineexpiretime);

                db.update("medicine",medicine,"medid=?", new String[]{String.valueOf(clickmedicineID)});

                db.close();

                Intent intent = new Intent(ModificationMedicineActivity.this,MedicineListActivity.class);
                startActivity(intent);
                finish();


            }
        });

        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ModificationMedicineActivity.this,MedicineListActivity.class);
                startActivity(intent);
                finish();
            }
        });




    }

    //Transfer YYYY/MM/DD formate to int
    private int dateTransfer(String date){
        String str=date.replaceAll("\\s*","");
        str = str.replace("/","");
        int newdate = Integer.parseInt(str);
        return newdate;
    }

    //Transfer date format to YYYY/MM/DD
    private String dateFormat(int date){
        int year = date/10000;
        int month = ( date - year * 10000 ) / 100 ;
        int day = date - year * 10000 - month * 100;
        String dateYMD = year + "/" + month + "/" + day;
        return dateYMD;
    }

    private void queryData() {
        medicine_set = new LinkedHashSet<Medicine>();
        DatabaseHelper Helper = new DatabaseHelper(ModificationMedicineActivity.this,"database");
        SQLiteDatabase db = Helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from medicine where medid ="+clickmedicineID,null);
        if(cursor != null){
            while(cursor.moveToNext()){
                // public Medicine(int medID, String medname, int medexpiredate)
                //     String sql1 = "create table if not exists medicine(" +
                //                "medid integer NOT NULL PRIMARY KEY, " +
                //                "medname varchar(20), " +
                //                "medexpiredate integer)";
                int medID = cursor.getInt(cursor.getColumnIndex("medid"));
                String medname = cursor.getString(cursor.getColumnIndex("medname"));
                int medexpiredate = cursor.getInt(cursor.getColumnIndex("medexpiredate"));

                selectmedicinename = medname;
                selectmedicineexpiredate = medexpiredate;

                medicine_set.add(new Medicine(medID,medname,medexpiredate));
            }

        }
    }

    private List<Map<String, Object>> getData(){
        if(medicine_set != null) {
            for(Medicine m:medicine_set){
                Map<String,Object> map = new LinkedHashMap<>();

                map.put("medname",m.getMedname());
                int year = m.getMedexpiredate() / 10000;
                int month = (m.getMedexpiredate() - year*10000) / 100;
                int day = (m.getMedexpiredate() - year *10000 - month*100);
                String expiredate = year + "/" + month + "/" + day;
                map.put("medexpiredate",expiredate);

                datalist.add(map);

            }
        }
        return datalist;
    }



}
