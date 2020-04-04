package univ.polytech.projetlibre.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import univ.polytech.projetlibre.Database.DatabaseHelper;
import univ.polytech.projetlibre.Entity.User;
import univ.polytech.projetlibre.R;

public class AddUserActivity extends AppCompatActivity {
    private Spinner sexspinner;
    private Button button_finish;
    private Button button_cancel;
    private EditText et_lastname;
    private EditText et_firstname;
    private EditText et_address;
    private EditText et_birthday;
    private EditText et_phonenumber;

    private List<String> sexList;

    private int newuserid;
    private String newuserlastname;
    private String newuserfirstname;
    private int newusersex;
    private String newuseraddress;
    private int newuserbirthday;
    private int newuserphonenumber;

    private LinkedHashSet<User> user_set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        sexspinner = findViewById(R.id.spinner_sex);
        button_finish = findViewById(R.id.button_finishadduser);
        button_cancel = findViewById(R.id.button_canceladduser);
        et_lastname = findViewById(R.id.editText_userlastname);
        et_firstname = findViewById(R.id.editText_userfirstname);
        et_address = findViewById(R.id.editText_address);
        et_birthday = findViewById(R.id.editText_birthday);
        et_phonenumber = findViewById(R.id.editText_phonenumber);

        sexList = new ArrayList<String>();
        sexList.add("Male");
        sexList.add("Female");
        ArrayAdapter sexadapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line,sexList);

        sexspinner.setAdapter(sexadapter);

        sexspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==2){
                    newusersex = 0;
                }else{
                    newusersex = 1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                newusersex = 1;
            }
        });

        queryData();


        button_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseHelper dbHelper = new DatabaseHelper(AddUserActivity.this, "database");
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                ContentValues newusers = new ContentValues();

                int total = user_set.size();

                newuserid = total+1;
                newuserlastname = et_lastname.getText().toString();
                newuserfirstname = et_firstname.getText().toString();
                newuseraddress = et_address.getText().toString();
                String birthday = et_birthday.getText().toString().replace("/","");

                newuserbirthday = Integer.parseInt(birthday);
                newuserphonenumber = Integer.parseInt(et_phonenumber.getText().toString());

                newusers.put("userid", newuserid);
                newusers.put("lastname",newuserlastname);
                newusers.put("firstname",newuserfirstname);
                newusers.put("address",newuseraddress);
                newusers.put("birthday",newuserbirthday);
                newusers.put("sex",newusersex);
                newusers.put("phone",newuserphonenumber);

                db.insert("user", null, newusers);

                Intent intent = new Intent(AddUserActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddUserActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });










    }

    private void queryData() {
        user_set = new LinkedHashSet<User>();
        DatabaseHelper Helper = new DatabaseHelper(AddUserActivity.this,"database");
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





}
