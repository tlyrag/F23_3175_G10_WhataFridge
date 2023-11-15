package com.douglas.whatafridge.Controller.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.douglas.whatafridge.Model.ObjectModels.Recipe;
import com.douglas.whatafridge.Model.ObjectModels.User;

public class UserDBController extends SQLiteOpenHelper {

    private final String TAG = "WTF App";
    private static final String DB_NAME = "WTFDB";
    private static final int DB_VERSION = 2;
    private static final String TABLE_NAME = "UserInfo";

    private static final String USERID_COL = "userid";
    private static final String PASSWORD_COL = "password";

    private static final String USERNAME_COL = "username";
    private static final String AGE_COL = "age";
    private static final String WEIGHT_COL = "weight";
    private static final String HEIGHT_COL = "height";
    private static final String GLUTEN_COL = "gluten";
    private static final String VEGETYPE_COL = "vegetype";

    public UserDBController(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void createTable(SQLiteDatabase db) {
        try{
            String query = "CREATE TABLE " + TABLE_NAME + " ("
                    + USERID_COL + " TEXT PRIMARY KEY AUTOINCREMENT, "
                    + PASSWORD_COL + " TEXT,"
                    + USERNAME_COL + " TEXT,"
                    + AGE_COL + " INTEGER,"
                    + WEIGHT_COL + " REAL,"
                    + HEIGHT_COL + " REAL,"
                    + GLUTEN_COL + " TEXT,"
                    + VEGETYPE_COL + " INTEGER)";

            db.execSQL(query);

        } catch (Exception err) {
            Log.d(TAG, "createTable: ");
        }
    }

    public void insertUserPass(String userId, String userPassword) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(USERID_COL, userId);
            values.put(PASSWORD_COL, userPassword);

            db.insert(TABLE_NAME, null, values);

            db.close();

        } catch (Exception err) {
            Log.d(TAG, "addNewRecipe: Failed to read Database"+ err.getMessage() );
        }

    }

    @SuppressLint("Range")
    public User selectUserInfo(String userId) {
        User user = null;

        try{
            SQLiteDatabase db = this.getReadableDatabase();
            String query = "Select * From " + TABLE_NAME
                    + " Where " + USERID_COL +" = '" + userId + "'";

            Cursor c = db.rawQuery(query,null);

            while(c.moveToNext()){
                user = new User();
                user.setUserId(c.getString(c.getColumnIndex(USERID_COL)));
                user.setPassword(c.getString(c.getColumnIndex(PASSWORD_COL)));

                user.setUserName(c.getString(c.getColumnIndex(USERNAME_COL)));
                user.setAge(c.getString(c.getColumnIndex(AGE_COL)));
                user.setWeight(c.getString(c.getColumnIndex(WEIGHT_COL)));
                user.setHeight(c.getString(c.getColumnIndex(HEIGHT_COL)));
                user.setGluten(c.getString(c.getColumnIndex(GLUTEN_COL)));
                user.setVegeType(c.getInt(c.getColumnIndex(VEGETYPE_COL)));
            }

            c.close();
        } catch (Exception err) {
            err.printStackTrace();
            Log.d(TAG, "selectUserInfo: " +err.getMessage());
        }

        return user;
    }

    public void updateUserInfo(User user){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(PASSWORD_COL, user.getPassword());
            values.put(USERNAME_COL, user.getUserName());
            values.put(AGE_COL, user.getAge());
            values.put(HEIGHT_COL, user.getHeight());
            values.put(WEIGHT_COL, user.getWeight());
            values.put(GLUTEN_COL, user.getGluten());
            values.put(VEGETYPE_COL, user.getVegeType());

            db.update(TABLE_NAME, values, USERID_COL+"=?", new String[]{user.getUserId()});

            db.close();

        } catch (Exception err) {
            Log.d(TAG, "addNewRecipe: Failed to read Database"+ err.getMessage() );
        }


    }


}