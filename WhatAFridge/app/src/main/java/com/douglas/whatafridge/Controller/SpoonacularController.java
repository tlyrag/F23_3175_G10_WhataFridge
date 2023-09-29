package com.douglas.whatafridge.Controller;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import com.douglas.whatafridge.BuildConfig;
import com.douglas.whatafridge.Model.*;
public interface SpoonacularController {
     static final String apiKey = BuildConfig.API_KEY;
    static final String BASE_URL = "https://api.spoonacular.com";
    @Headers("X-API-KEY: " + apiKey)
    @GET("recipes/findByIngredients")
    Call<Recipe> getRecipesByIngredients(
            @Query("ingredients") String ingredients,
            @Query("number") int number,
            @Query("limitLicense") boolean limitLicense,
            @Query("ranking") int ranking,
            @Query("ignorePantry") boolean ignorePantry
    );
}
