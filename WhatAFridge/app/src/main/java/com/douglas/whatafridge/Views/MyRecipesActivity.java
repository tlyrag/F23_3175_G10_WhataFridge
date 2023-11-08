package com.douglas.whatafridge.Views;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.douglas.whatafridge.Controller.Database.RecipeDBController;
import com.douglas.whatafridge.Controller.Adapters.MyRecipeRecyclerViewAdapter;
import com.douglas.whatafridge.Model.ObjectModels.Recipe;
import com.douglas.whatafridge.R;

import java.util.List;

public class MyRecipesActivity extends WFTemplate {
    RecyclerView recyclerViewRecipe;
    List<Recipe> recipeList;
    RecipeDBController db;

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
            db = new RecipeDBController(this);
            recipeList =db.getAllRecipes();    
        } catch (Exception err) {
            Log.d(TAG, "getRecipesFromDB: Failed to create list of Recipes");
        }
        
    }
    public void setRecyclerViewRecipe() {
        MyRecipeRecyclerViewAdapter recipeAdapter = new MyRecipeRecyclerViewAdapter(recipeList);
        recyclerViewRecipe.setAdapter(recipeAdapter);
        LinearLayoutManager lm  = new LinearLayoutManager(this);
        recyclerViewRecipe.setLayoutManager(lm);
    }

}