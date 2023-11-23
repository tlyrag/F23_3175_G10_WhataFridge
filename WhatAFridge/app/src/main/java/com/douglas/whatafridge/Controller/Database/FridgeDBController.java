package com.douglas.whatafridge.Controller.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.douglas.whatafridge.Model.ObjectModels.Ingredients;

import java.util.ArrayList;
import java.util.List;

public class FridgeDBController extends SQLiteOpenHelper {
    private static final String ID_COL = "id";
    private final String TAG = "WTF App";
    private static final String DB_NAME = "FridgeDB";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "myFridge";


    private static final String NAME_COL = "name";

    private static final String BESTBEFORE_COL = "bbefore";

    private static final String QUANTITY_COL = "quantity";

    public FridgeDBController(Context context) {
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
                    + BESTBEFORE_COL + " TEXT,"
                    + QUANTITY_COL + " REAL)";

            db.execSQL(query);

        } catch (Exception err) {
            Log.d(TAG, "createTable: ");
        }
    }

    public long addNewIngredient(String IngredName, String IngredBB, double IngredQtd) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(NAME_COL, IngredName);
            values.put(BESTBEFORE_COL, IngredBB);
            values.put(QUANTITY_COL, IngredQtd);
            long id = db.insert(TABLE_NAME, null, values);
            db.close();
            return id;
        } catch (Exception err) {
            Log.d(TAG, "addNewIngredient: Failed to read Database" + err.getMessage());
            return 0;
        }
    }
    public List<Ingredients> getAllIngredients() {
        try{

            SQLiteDatabase db = this.getReadableDatabase();
            String query = "Select * From " + TABLE_NAME;
            Cursor cursorIngred = db.rawQuery(query,null);
            List<Ingredients> ingredientsList = new ArrayList<>();

            // Checking and treating first line of response
            if(cursorIngred!=null && cursorIngred.moveToFirst()) {
                int ingredId = cursorIngred.getInt(0);
                String ingredName = cursorIngred.getString(1);
                String ingredBestBefore = cursorIngred.getString(2);
                Double ingredQuantity = cursorIngred.getDouble(3);

                Ingredients ingred = new Ingredients(ingredId,ingredQuantity,ingredBestBefore,ingredName);
                ingredientsList.add(ingred);

            } else {
                Ingredients dummyIngred = new Ingredients();
                dummyIngred.name = "Unable to find Ingredients";
                //WingredientsList.add(dummyIngred);
                return ingredientsList;
            }
            // Loping through database and grabbing the rest of the data
            while (cursorIngred.moveToNext()) {
                int ingredId = cursorIngred.getInt(0);
                String ingredName = cursorIngred.getString(1);
                String ingredBestBefore = cursorIngred.getString(2);
                Double ingredQuantity = cursorIngred.getDouble(3);

                Ingredients ingred = new Ingredients(ingredId,ingredQuantity,ingredBestBefore,ingredName);
                ingredientsList.add(ingred);
            }
            cursorIngred.close();
            return ingredientsList;

        } catch (Exception err) {
            Log.d(TAG, "getIngredByID:Failed to get Ingredient from database " +err.getMessage());
            Ingredients dummyIngred = new Ingredients();
            List<Ingredients> ingredientsList = new ArrayList<>();
            dummyIngred.name = "Unable to find Ingredient";
            //ingredientsList.add(dummyIngred);
            return ingredientsList;
        }
    }

    public Ingredients getIngredientByID(long id) {
        try{

            SQLiteDatabase db = this.getReadableDatabase();
            String query = "Select * From " + TABLE_NAME
                    + " Where " + ID_COL +" = " + id;
            Cursor cursorIngred = db.rawQuery(query,null);

            if(cursorIngred!=null && cursorIngred.moveToFirst()) {
                int ingredientID =cursorIngred.getInt(0);
                String ingredName = cursorIngred.getString(1);
                String ingredBestBefore = cursorIngred.getString(2);
                Double ingredQuantity = cursorIngred.getDouble(3);

                Ingredients ingred = new Ingredients(ingredQuantity,ingredBestBefore,ingredName);
                return ingred;
            } else {

                Ingredients dummyIngred = new Ingredients();
                dummyIngred.name = "Unable to find Ingredient";
                return dummyIngred;
            }


        } catch (Exception err) {
            Log.d(TAG, "getIngredByID:Failed to get Ingred from database " +err.getMessage());
            Ingredients dummyIngred = new Ingredients();
            dummyIngred.name = "Unable to find Ingredient";
          return dummyIngred;
        }

    }

    public void updateIngredientData(Ingredients ingredient) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "updateIngredientData: "+ ingredient);
        ContentValues values = new ContentValues();
        values.put(NAME_COL, ingredient.name);
        values.put(BESTBEFORE_COL, ingredient.BestBefore);
        values.put(QUANTITY_COL, ingredient.amount);

        db.update(TABLE_NAME,values,"id=?",new String[] {ingredient.id+""});
        db.close();
    }
}
