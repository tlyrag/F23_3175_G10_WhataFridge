package com.douglas.whatafridge.Model.SpoonApiModels;
import  android.content.Context;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public  final String BASE_URL = GetRecipeByIngredients.BASE_URL;
    public Context context;
    public Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    public RetrofitClient() {
    //Context context
        //this.context = context;
    }

}
