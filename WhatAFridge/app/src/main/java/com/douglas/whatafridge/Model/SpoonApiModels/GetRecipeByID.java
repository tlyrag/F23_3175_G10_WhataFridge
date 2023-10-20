package com.douglas.whatafridge.Model.SpoonApiModels;

import com.douglas.whatafridge.BuildConfig;
import com.douglas.whatafridge.Model.ObjectModels.Recipe;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface GetRecipeByID {

    static final String apiKey = BuildConfig.API_KEY;
    static final String BASE_URL = "https://api.spoonacular.com";
    @Headers("X-API-KEY: " + apiKey)
    @GET("/recipes/informationBulk")
    Call<ArrayList<Recipe>> getRecipesByID(
            @Query("ids") String ids,
            @Query("includeNutrition") boolean includeNutrition
    );
}
