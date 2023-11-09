package com.douglas.whatafridge.Controller.API;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.douglas.whatafridge.Model.ObjectModels.Recipe;
import com.douglas.whatafridge.Model.SpoonApiModels.*;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpoonacularController  {
    public  String TAG = "WTFApp";
    GetRecipeByIngredients getRecipe;
    GetRecipeByID recipeByID;
    RetrofitClient retrofitClient = new RetrofitClient();

    /**
     * @author: Thiago
     *  Creates a retrofit object with the interface of the API endpoint as a class
     *  Call the API and uses the enqeue method to handle the response.
     *
     * @param context is used in case a Toast to be displayed when the metod is called
     * @param  number is used to determine the number of Recipes to be returned by the api call
     * @param ApiResponse Method is using the Generic API response to return a generic <T> ArrayList in a Success or Error method
     *                    should be called using new ApiResponse and implement methods
     *
     */
        public void getRecipeByIngredient(Context context,String ingredients,int number, GenericAPIResponse ApiResponse) {
            try {
                getRecipe = retrofitClient.retrofit.create(GetRecipeByIngredients.class);
                Call<ArrayList<Recipe>> callRecipeByIngredient = getRecipe.getRecipesByIngredients(ingredients,number,true,2,true);
                callRecipeByIngredient.enqueue(new Callback<ArrayList<Recipe>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                        if(!response.isSuccessful()){
                            Toast.makeText(context, "Error Fetching API", Toast.LENGTH_SHORT).show();
                            ApiResponse.onFail("Error Fetching the API:"+ response.message());
                            Log.d(TAG,"Error Fetching API:"+ response.message());
                        } else {
                            //Log.d(TAG,response.body().get(0).title);
                            ApiResponse.onSuccess(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                        Toast.makeText(context, "API FAIled" + t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d(TAG,"Error Fetching API"+t.getMessage());
                    }
                });
            } catch (Exception err) {
                Log.d(TAG,"Error in Spoonacular Controller"+ err.getMessage());
            }

        }


    /**
     * @author: Thiago
     *  Creates a retrofit object with the interface of the API endpoint as a class
     *  Call the API and uses the enqeue method to handle the response.
     *
     * @param context is used in case a Toast to be displayed when the metod is called
     * @param  ids should use the ids to be used in the Query should be separated by , ex: 6451,32123
     * @param ApiResponse Method is using the Generic API response to return a generic <T> ArrayList in a Success or Error method
     *                    should be called using new ApiResponse and implement methods
     *
     */
        public void getRecipeByID (Context context,String ids, GenericAPIResponse ApiResponse) {
            recipeByID = retrofitClient.retrofit.create(GetRecipeByID.class);
            Call<ArrayList<Recipe>> callRecipeByID = recipeByID.getRecipesByID(ids,true);
            callRecipeByID.enqueue(new Callback<ArrayList<Recipe>>() {
                @Override
                public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                    if(!response.isSuccessful()) {
                        Toast.makeText(context, "Error Fetching API", Toast.LENGTH_SHORT).show();
                        ApiResponse.onFail("Error Fetching the API:"+ response.message());
                        Log.d(TAG,"Error Fetching API:"+ response.message());
                    } else {
                        ApiResponse.onSuccess(response.body());
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                    Toast.makeText(context, "API FAIled" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG,"Error Fetching API"+t.getMessage());
                }
            });
        }
}
