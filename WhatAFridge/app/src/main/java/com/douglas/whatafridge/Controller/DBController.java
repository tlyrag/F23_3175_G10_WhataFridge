package com.douglas.whatafridge.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DBController extends SQLiteOpenHelper {

    private final String TAG = "WTF App";
    private static final String DB_NAME = "WTFDB";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "myRecipes";
    private static final String ID_COL = "id";

    private static final String NAME_COL = "name";

    private static final String SUMMARY_COL = "summary";

    private static final String INSTRUCTION_COL = "instruction";
    private static final String INGREDIENTS_COL = "ingredients";

    public DBController(Context context) {
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
                    + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + NAME_COL + " TEXT,"
                    + SUMMARY_COL + " TEXT,"
                    + INSTRUCTION_COL + " TEXT,"
                    + INGREDIENTS_COL + " TEXT)";

            db.execSQL(query);

        } catch (Exception err) {
            Log.d(TAG, "createTable: ");
        }
    }

    public void addNewCourse(String recipeName, String recipeSummary, String recipeInstruction, String recipeIngredients) {


        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();

        values.put(NAME_COL, recipeName);
        values.put(SUMMARY_COL, recipeSummary);
        values.put(INSTRUCTION_COL, recipeInstruction);
        values.put(INGREDIENTS_COL, recipeIngredients);

        db.insert(TABLE_NAME, null, values);


        db.close();
    }
}
