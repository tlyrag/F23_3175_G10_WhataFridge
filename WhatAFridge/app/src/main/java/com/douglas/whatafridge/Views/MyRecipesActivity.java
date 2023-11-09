package com.douglas.whatafridge.Views;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.douglas.whatafridge.Controller.Database.RecipeDBController;
import com.douglas.whatafridge.Controller.Adapters.MyRecipeRecyclerViewAdapter;
import com.douglas.whatafridge.Model.ObjectModels.Recipe;
import com.douglas.whatafridge.R;

import java.util.List;

public class MyRecipesActivity extends WFTemplate implements MyRecipeRecyclerViewAdapter.OnItemClickListner{
    RecyclerView recyclerViewRecipe;
    List<Recipe> recipeList;
    RecipeDBController db;
    MyRecipeRecyclerViewAdapter recipeAdapter;

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
         recipeAdapter = new MyRecipeRecyclerViewAdapter(recipeList,this);
        recyclerViewRecipe.setAdapter(recipeAdapter);
        LinearLayoutManager lm  = new LinearLayoutManager(this);
        recyclerViewRecipe.setLayoutManager(lm);
    }

    @Override
    public void onItemCLick(int i) {

        Intent nextIntent = createBundle(recipeList.get(i).id);
        startActivity(nextIntent);
    }

    public Intent createBundle(long id) {
        Intent recipeDetail = new Intent(this, RecipeDetailActivity.class);
        Bundle recipeBundle = new Bundle();
        recipeBundle.putLong("id",id);
        recipeBundle.putBoolean("isMyRecipe",true);
        recipeDetail.putExtras(recipeBundle);
        return recipeDetail;
    }
}