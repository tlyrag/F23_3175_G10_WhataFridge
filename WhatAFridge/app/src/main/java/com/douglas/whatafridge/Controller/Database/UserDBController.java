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

public class UserDBController  {

    private final String TAG = "WTF App";
    private static final String TABLE_NAME = "UserInfo";

    private static final String USERID_COL = "userid";
    private static final String PASSWORD_COL = "password";

    private static final String USERNAME_COL = "username";
    private static final String AGE_COL = "age";
    private static final String WEIGHT_COL = "weight";
    private static final String HEIGHT_COL = "height";
    private static final String GLUTEN_COL = "gluten";
    private static final String VEGETYPE_COL = "vegetype";
    private static final String DAIRY_COL = "dairy";
    private static final String EGG_COL = "egg";
    private static final String POULTRY_COL = "poultry";
    private static final String FISH_COL = "fish";
    private static final String PORK_COL = "pork";
    private static final String BEEF_COL = "beef";


    MyDBHelper myDBHelper;

    public UserDBController(MyDBHelper myDBHelper) {
        this.myDBHelper = myDBHelper;
    }

    public void insertUserPass(String userId, String userPassword) {
        try {
            SQLiteDatabase db = myDBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(USERID_COL, userId);
            values.put(PASSWORD_COL, userPassword);

            db.insert(TABLE_NAME, null, values);

            db.close();

        } catch (Exception err) {
            Log.d(TAG, "insertUserPass: "+ err.getMessage() );
        }

    }

    @SuppressLint("Range")
    public User selectUserInfo(String userId) {
        User user = null;

        try{
            SQLiteDatabase db = myDBHelper.getReadableDatabase();
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
                user.setDairy(c.getString(c.getColumnIndex(DAIRY_COL)));
                user.setEgg(c.getString(c.getColumnIndex(EGG_COL)));
                user.setPoultry(c.getString(c.getColumnIndex(POULTRY_COL)));
                user.setFish(c.getString(c.getColumnIndex(FISH_COL)));
                user.setPork(c.getString(c.getColumnIndex(PORK_COL)));
                user.setBeef(c.getString(c.getColumnIndex(BEEF_COL)));


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
            SQLiteDatabase db = myDBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(PASSWORD_COL, user.getPassword());
            values.put(USERNAME_COL, user.getUserName());
            values.put(AGE_COL, user.getAge());
            values.put(HEIGHT_COL, user.getHeight());
            values.put(WEIGHT_COL, user.getWeight());
            values.put(GLUTEN_COL, user.getGluten());
            values.put(VEGETYPE_COL, user.getVegeType());

            values.put(EGG_COL, user.getEgg());
            values.put(DAIRY_COL, user.getDairy());
            values.put(POULTRY_COL, user.getPoultry());
            values.put(FISH_COL, user.getFish());
            values.put(PORK_COL, user.getPork());
            values.put(BEEF_COL, user.getBeef());

            db.update(TABLE_NAME, values, USERID_COL+"=?", new String[]{user.getUserId()});

            db.close();

        } catch (Exception err) {
            Log.d(TAG, "updateUserInfo: "+ err.getMessage() );
        }


    }

    public void insertVerifyCode(String email, String code) {
        try {

            SQLiteDatabase db = myDBHelper.getWritableDatabase();
            //check
            String query = "SELECT code FROM VerifyCode"
                         + " WHERE email = '" + email + "' AND status = 'Y'";
            Cursor c = db.rawQuery(query,null);

            if(c.getCount() != 0){
                query = "DELETE FROM VerifyCode WHERE email = '" + email + "'";
                db.execSQL(query);

            }
            query = "INSERT INTO VerifyCode (email, code, status) VALUES ('" + email +"','" + code +"','Y' )";
            db.execSQL(query);


            db.close();

        } catch (Exception err) {
            Log.d(TAG, "insertVerifyCode: "+ err.getMessage() );
        }

    }


    public String selectVerifyCode(String email) {
        String code = "";

        try{
            SQLiteDatabase db = myDBHelper.getReadableDatabase();
            String query = "SELECT code FROM VerifyCode"
                    + " WHERE email = '" + email + "'";

            Cursor c = db.rawQuery(query,null);
            c.moveToNext();
            code = c.getString(0);

            c.close();
        } catch (Exception err) {
            err.printStackTrace();
            Log.d(TAG, "selectUserInfo: " +err.getMessage());
        }

        return code;
    }

    public void deleteVerifyCode(String email) {
        try {

            SQLiteDatabase db = myDBHelper.getWritableDatabase();
            //check
            String query = "SELECT code FROM VerifyCode"
                    + " WHERE email = '" + email + "' AND status = 'Y'";
            Cursor c = db.rawQuery(query,null);

            if(c.getCount() != 0){
                query = "DELETE FROM VerifyCode WHERE email = '" + email + "'";
                db.execSQL(query);

            }

            db.close();

        } catch (Exception err) {
            Log.d(TAG, "deleteVerifyCode: "+ err.getMessage() );
        }

    }




}