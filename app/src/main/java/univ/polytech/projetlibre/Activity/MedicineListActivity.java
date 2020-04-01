package univ.polytech.projetlibre.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import univ.polytech.projetlibre.Database.DatabaseHelper;
import univ.polytech.projetlibre.Entity.Medicine;
import univ.polytech.projetlibre.Entity.User;
import univ.polytech.projetlibre.R;

public class MedicineListActivity extends AppCompatActivity {

    private LinkedHashSet<Medicine> medicine_set;
    private SimpleAdapter list_adapter;
    private List<Map<String, Object>> datalist;
    private ListView mlv;
    private int clickmedicineID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_list);


        mlv = findViewById(R.id.medicinelistview);

        queryData();

        datalist = new ArrayList<>();
        list_adapter = new SimpleAdapter(MedicineListActivity.this, getData(), R.layout.medicinelistitems,
                new String[]{"medname", "medexpiredate"}, new int[]{R.id.medicinename,R.id.medicineexpiredate});
        mlv.setAdapter(list_adapter);

        mlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickmedicineID = position+1;

                Intent intent = new Intent(MedicineListActivity.this,ModificationMedicineActivity.class);

                intent.putExtra("medID",clickmedicineID);
                startActivity(intent);
            }
        });







    }

    private void queryData() {
        medicine_set = new LinkedHashSet<Medicine>();
        DatabaseHelper Helper = new DatabaseHelper(MedicineListActivity.this,"database");
        SQLiteDatabase db = Helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from medicine",null);
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
