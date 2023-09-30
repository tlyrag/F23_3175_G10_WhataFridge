package com.douglas.whatafridge.Controller;
import android.content.Context;
import android.nfc.Tag;
import android.util.Log;
import android.widget.Toast;

import com.douglas.whatafridge.Model.ObjectModels.Recipe;
import com.douglas.whatafridge.Model.SpoonApiModels.*;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpoonacularController  {
    public  String tag = "WTFApp";
    GetRecipeByIngredients getRecipe;
    public ArrayList<Recipe> recipesByIngredientList;
    RetrofitClient retrofitClient = new RetrofitClient();
        public void getRecipeByIngredient(Context context,String ingredients,int number) {
            try {
                getRecipe = retrofitClient.retrofit.create(GetRecipeByIngredients.class);
                Call<ArrayList<Recipe>> callRecipeByIngredient = getRecipe.getRecipesByIngredients(ingredients,number,true,2,true);
                callRecipeByIngredient.enqueue(new Callback<ArrayList<Recipe>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                        if(!response.isSuccessful()){
                            Toast.makeText(context, "Error Fetching API", Toast.LENGTH_SHORT).show();
                            Log.d(tag,"Error Fetching API");
                        } else {
                            Toast.makeText(context, "API WORKED" + response.message(), Toast.LENGTH_SHORT).show();
                            Log.d(tag,response.body().get(0).title);
                            recipesByIngredientList= response.body();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                        Toast.makeText(context, "API FAIled" + t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d(tag,"Error Fetching API"+t.getMessage());
                    }
                });
            } catch (Exception err) {
                Log.d(tag,"Error in Spoonacular Controller"+ err.getMessage());
            }

        }

}
