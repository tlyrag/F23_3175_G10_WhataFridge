package com.douglas.whatafridge;

import androidx.appcompat.app.AppCompatActivity;
import com.douglas.whatafridge.Controller.*;
import com.douglas.whatafridge.Model.*;
import android.os.Bundle;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{
            SpoonacularController api = RetrofitClient.getInstance().getSpoonacularController();
            Call<Recipe> call = api.getRecipesByIngredients("chicken", 5, true, 1, true);

            call.enqueue(new Callback<Recipe>() {
                @Override
                public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                    //Log.d("WTF App",response.body().toString());
                }

                @Override
                public void onFailure(Call<Recipe> call, Throwable t) {

                }
            });



        } catch(Exception err) {
            Log.d("WTF App", "Api error");
        }



    }
}