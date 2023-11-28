package com.douglas.whatafridge.Views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.douglas.whatafridge.Controller.Adapters.MyFridgeRecyclerViewAdapter;
import com.douglas.whatafridge.Controller.Database.FridgeDBController;
import com.douglas.whatafridge.Model.ObjectModels.Ingredients;
import com.douglas.whatafridge.R;

import java.util.List;

public class MyFridgeActivity extends WFTemplate implements MyFridgeRecyclerViewAdapter.OnItemClickListner{
    RecyclerView recyclerViewFridge;
    List<Ingredients> IngredientList;
    FridgeDBController db;
    MyFridgeRecyclerViewAdapter fridgeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_fridge);
        getItemView();
        getFridgeFromDB();
        setRecyclerViewRecipe();



    }
    public void getItemView() {
        try {
            recyclerViewFridge = findViewById(R.id.FridgeRecyclerView);
        } catch (Exception err) {
            Log.d(TAG, "getItemView: Unable to get Item view from My Recipes "+ err.getMessage());
        }
    }
    public void getFridgeFromDB() {
        try {
            db = new FridgeDBController(this);
            IngredientList =db.getAllIngredients();


        } catch (Exception err) {
            Log.d(TAG, "getFridgeFromDB: Failed to create list of Ingredients");
        }
        
    }
    public void setRecyclerViewRecipe() {
        fridgeAdapter = new MyFridgeRecyclerViewAdapter(IngredientList, this);
        recyclerViewFridge.setAdapter(fridgeAdapter);
        LinearLayoutManager lm  = new LinearLayoutManager(this);
        recyclerViewFridge.setLayoutManager(lm);
    }

    @Override
    public void onItemCLick(int i) {
        try{

            Intent nextIntent = createBundle(IngredientList.get(i).id);
            startActivity(nextIntent);

        }
        catch (Exception err) {
            Log.d(TAG, "onItemCLick: "+ err.getMessage());
        }

        //Intent nextIntent = createBundle(recipeList.get(i).id);
        //startActivity(nextIntent);
    }

    public Intent createBundle(long id) {
        Intent AddFridge = new Intent(this, AddFridgeActivity.class);
        Bundle recipeBundle = new Bundle();
        recipeBundle.putLong("id",id);
        recipeBundle.putBoolean("isMyFridge",true);
        AddFridge.putExtras(recipeBundle);
        return AddFridge;
    }
}