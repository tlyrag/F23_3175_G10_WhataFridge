package com.douglas.whatafridge.Controller.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDBHelper extends SQLiteOpenHelper {
    private final String TAG = "MyDBHelper";
    private static final String DB_NAME = "test.db";
    private static final int DB_VERSION = 1;
    public MyDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        //context.deleteDatabase(DB_NAME);
        //getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createUserTable(db);
    }

    public void createUserTable(SQLiteDatabase db) {
        try{
            String query = "CREATE TABLE UserInfo ("
                    + "userid TEXT PRIMARY KEY, "
                    + "password TEXT,"
                    + "username TEXT,"
                    + "age INTEGER,"
                    + "weight REAL,"
                    + "height REAL,"
                    + "gluten TEXT,"
                    + "vegetype INTEGER,"
                    + "dairy TEXT,"
                    + "egg TEXT,"
                    + "poultry TEXT,"
                    + "fish TEXT,"
                    + "pork TEXT,"
                    + "beef TEXT)";

            db.execSQL(query);

        } catch (Exception err) {
            err.printStackTrace();
            Log.d(TAG, "createTable: ");
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS UserInfo");
        onCreate(db);
    }
}
