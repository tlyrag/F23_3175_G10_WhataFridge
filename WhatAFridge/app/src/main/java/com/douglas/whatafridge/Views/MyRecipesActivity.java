package com.douglas.whatafridge.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.douglas.whatafridge.Controller.DBController;
import com.douglas.whatafridge.Controller.MyRecipeRecyclerViewController;
import com.douglas.whatafridge.Model.ObjectModels.Recipe;
import com.douglas.whatafridge.R;

import java.util.List;

public class MyRecipesActivity extends WTFemplate {
    RecyclerView recyclerViewRecipe;
    List<Recipe> recipeList;
    DBController db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recipes);
        getItemView();
        getRecipesFromDB();
        setRecyclerViewRecipe();
    }
    public void getItemView() {
        try {
            recyclerViewRecipe = findViewById(R.id.recipeRecyclerView);
        } catch (Exception err) {
            Log.d(TAG, "getItemView: Unable to get Item view from My Recipes "+ err.getMessage());
        }
    }
    public void getRecipesFromDB() {
        try {
            db = new DBController(this);
            recipeList =db.getAllRecipes();    
        } catch (Exception err) {
            Log.d(TAG, "getRecipesFromDB: Failed to create list of Recipes");
        }
        
    }
    public void setRecyclerViewRecipe() {
        MyRecipeRecyclerViewController recipeAdapter = new MyRecipeRecyclerViewController(recipeList);
        recyclerViewRecipe.setAdapter(recipeAdapter);
        LinearLayoutManager lm  = new LinearLayoutManager(this);
        recyclerViewRecipe.setLayoutManager(lm);
    }

}