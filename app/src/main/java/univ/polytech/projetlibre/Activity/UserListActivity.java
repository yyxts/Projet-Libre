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
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import univ.polytech.projetlibre.Database.DatabaseHelper;
import univ.polytech.projetlibre.Entity.Medicine;
import univ.polytech.projetlibre.Entity.User;
import univ.polytech.projetlibre.R;

public class UserListActivity extends AppCompatActivity {

    private ListView lv;

    private LinkedHashSet<User> user_set;
    private SimpleAdapter list_adapter;
    private List<Map<String, Object>> datalist;
    private String selectusername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        lv = findViewById(R.id.ListViewUser);

        DatabaseHelper Helper = new DatabaseHelper(UserListActivity.this,"database");
        SQLiteDatabase db = Helper.getWritableDatabase();

        queryData();

        datalist = new ArrayList<>();
        list_adapter = new SimpleAdapter(UserListActivity.this, getData(), R.layout.items,
                new String[]{"lastname", "firstname"}, new int[]{R.id.lastname, R.id.firstname});
        lv.setAdapter(list_adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int total = user_set.size();
                int userclickID = position+1;

                ArrayList<User> listuser = new ArrayList<User>(user_set);
                selectusername = listuser.get(position).getLastname() + listuser.get(position).getFirstname();
                Toast.makeText(UserListActivity.this, "userID is : "+ (position+1), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UserListActivity.this,RemindListActivity.class);
                intent.putExtra("userID",userclickID);
                intent.putExtra("userName",selectusername);
                startActivity(intent);
                finish();
            }
        });
    }

    private void queryData() {
        user_set = new LinkedHashSet<User>();
        DatabaseHelper Helper = new DatabaseHelper(UserListActivity.this,"database");
        SQLiteDatabase db = Helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from user",null);
        if(cursor != null){
            while(cursor.moveToNext()){

                int userID = cursor.getInt(cursor.getColumnIndex("userid"));
                String lastname = cursor.getString(cursor.getColumnIndex("lastname"));
                String firstname = cursor.getString(cursor.getColumnIndex("firstname"));
                String address = cursor.getString(cursor.getColumnIndex("address"));
                int birthday = cursor.getInt(cursor.getColumnIndex("birthday"));
                int sex = cursor.getInt(cursor.getColumnIndex("sex"));
                String phone = cursor.getString(cursor.getColumnIndex("phone"));

                user_set.add(new User(userID, lastname, firstname, address, birthday,  sex, phone));
            }
        }
    }

    private List<Map<String, Object>> getData(){
        if(user_set != null) {
            for(User u:user_set){
                Map<String,Object> map = new LinkedHashMap<>();
                map.put("lastname",u.getLastname());
                map.put("firstname",u.getFirstname());
                datalist.add(map);
            }
        }
        return datalist;
    }

}
