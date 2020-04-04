package univ.polytech.projetlibre.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import pl.com.salsoft.sqlitestudioremote.SQLiteStudioService;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    public DatabaseHelper(Context context,String name){
        this(context,name,VERSION);
    }

    public DatabaseHelper(Context context,String name,int version) {
        this(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create database tables

        //public User(String lastname, String firstname, int userID, String address, int birthday, int sex, String phone)
        String sql = "create table if not exists user(" +
                "userid integer NOT NULL PRIMARY KEY, " +
                "lastname varchar(20), " +
                "firstname varchar(20), " +
                "address varchar(20), " +
                "birthday integer, " +
                "sex integer, " +
                "phone varchar(20))";
        db.execSQL(sql);
        //public Medicine(String medname, int medID, int medexpiredate)
        String sql1 = "create table if not exists medicine(" +
                "medid integer NOT NULL PRIMARY KEY, " +
                "medname varchar(20), " +
                "medexpiredate integer)";
        db.execSQL(sql1);
        Log.i("Database","Database create successful!");


        String sql2 = "create table if not exists reminder(" +
                "remid integer NOT NULL PRIMARY KEY,  " +
                "remtime integer, " +
                "remdosage text, " +
                "remuserid integer, " +
                "remmedid integer, " +
                "FOREIGN KEY(remuserid) REFERENCES user(userid), " +
                "FOREIGN KEY(remmedid) REFERENCES medicine(medid))";
        db.execSQL(sql2);

        String sql3 = "create table if not exists record(" +
                "recid integer NOT NULL PRIMARY KEY, " +
                "recdate integer, " +
                "rectime integer, " +
                "recdosage text, " +
                "reccomment text, " +
                "recuserid integer, " +
                "recmedid integer, " +
                "FOREIGN KEY(recuserid) REFERENCES user(userid), " +
                "FOREIGN KEY(recmedid) REFERENCES medicine(medid))";
        db.execSQL(sql3);

        Log.i("Database","Database with Foreign KEY create successful!");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if(!db.isReadOnly()) {
            //Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys = ON;");
        }
    }


}
