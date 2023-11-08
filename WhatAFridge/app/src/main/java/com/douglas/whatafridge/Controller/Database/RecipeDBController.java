package com.douglas.whatafridge.Controller.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.douglas.whatafridge.Model.ObjectModels.Ingredients;
import com.douglas.whatafridge.Model.ObjectModels.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeDBController extends SQLiteOpenHelper {

    private final String TAG = "WTF App";
    private static final String DB_NAME = "WTFDB";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "myRecipes";
    private static final String ID_COL = "id";

    private static final String NAME_COL = "name";

    private static final String SUMMARY_COL = "summary";

    private static final String INSTRUCTION_COL = "instruction";
    private static final String INGREDIENTS_COL = "ingredients";


    public RecipeDBController(Context context) {
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

    public long addNewRecipe(String recipeName, String recipeSummary, String recipeInstruction, String recipeIngredients) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(NAME_COL, recipeName);
            values.put(SUMMARY_COL, recipeSummary);
            values.put(INSTRUCTION_COL, recipeInstruction);
            values.put(INGREDIENTS_COL, recipeIngredients);
            long id = db.insert(TABLE_NAME, null, values);
            db.close();
            return id;
        } catch (Exception err) {
            Log.d(TAG, "addNewRecipe: Failed to read Database"+ err.getMessage() );
            return 0;
        }

    }
    public Recipe getRecipeByID(long id) {
        try{
            Log.d(TAG, "getRecipeByID: Searching for recipe Index:" + id);
            SQLiteDatabase db = this.getReadableDatabase();
            String query = "Select * From " + TABLE_NAME
                    + " Where " + ID_COL +" = " + id;
            Cursor cursorRecipe = db.rawQuery(query,null);

            if(cursorRecipe!=null && cursorRecipe.moveToFirst()) {
                Recipe myRecipe = createRecipe(cursorRecipe);
                return myRecipe;
            } else {
                Recipe dummyRecipe = new Recipe();
                dummyRecipe.title = "Unable to find recipe";
                return  dummyRecipe;
            }

            
        } catch (Exception err) {
            Log.d(TAG, "getRecipeByID:Failed to get Recipe from database" +err.getMessage());
            Recipe dummyRecipe = new Recipe();
            dummyRecipe.title = "Unable to find recipe";
            return  dummyRecipe;
        }
 
    }

    public List<Recipe> getAllRecipes() {
        try{
            //Log.d(TAG, "getRecipeByID: Searching for recipe Index:" + id);
            // Intanciating variables and database
            SQLiteDatabase db = this.getReadableDatabase();
            String query = "Select * From " + TABLE_NAME;
            Cursor cursorRecipe = db.rawQuery(query,null);
            List<Recipe> recipeList = new ArrayList<>();

            // Checking and treating first line of response
            if(cursorRecipe!=null && cursorRecipe.moveToFirst()) {
                Recipe myRecipe = createRecipe(cursorRecipe);
                recipeList.add(myRecipe);

            } else {
                Recipe dummyRecipe = new Recipe();
                dummyRecipe.title = "Unable to find recipes";
                recipeList.add(dummyRecipe);
                return recipeList;
            }
            // Loping through database and grabbing the rest of the data
            while (cursorRecipe.moveToNext()) {
                recipeList.add(createRecipe(cursorRecipe));
            }
            cursorRecipe.close();
            return recipeList;

        } catch (Exception err) {
            Log.d(TAG, "getRecipeByID:Failed to get Recipe from database" +err.getMessage());
            Recipe dummyRecipe = new Recipe();
            List<Recipe> recipeList = new ArrayList<>();
            dummyRecipe.title = "Unable to find recipe";
            recipeList.add(dummyRecipe);
            return recipeList;
        }
    }
    public Recipe createRecipe(Cursor cursor) {
        String recipeName = cursor.getString(1);
        String recipeSummary = cursor.getString(2);
        String recipeInstruction = cursor.getString(3);
        String recipeIngredients = cursor.getString(4);
        ArrayList<Ingredients> myIngredList = createIngredientList(recipeIngredients);
        Recipe myRecipe = new Recipe(recipeName,myIngredList,recipeSummary,recipeInstruction);
        return myRecipe;
    }
    public ArrayList<Ingredients> createIngredientList(String ingredients) {
        String[] ingredArr =ingredients.split(",");
        ArrayList<Ingredients> myIngredList = new ArrayList<>();

        for(int i =0 ;i<ingredArr.length;i++) {
            Ingredients myIngred = new Ingredients();
            myIngred.name = ingredArr[i];
            myIngredList.add(myIngred);
        }
        return myIngredList;
    }
}
