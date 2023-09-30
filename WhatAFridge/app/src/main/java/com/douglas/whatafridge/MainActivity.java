package com.douglas.whatafridge;

import androidx.appcompat.app.AppCompatActivity;
import com.douglas.whatafridge.Controller.*;
//import com.douglas.whatafridge.Model.*;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    SpoonacularController api = new SpoonacularController();
    TextView txtTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{
        api.getRecipeByIngredient(this,"salmon",1);
        txtTitle = findViewById(R.id.TextViewTitle);
        String RecipeTitle = api.recipesByIngredientList.get(0).title;
        txtTitle.setText(RecipeTitle);

        } catch(Exception err) {
            Log.d("WTF App", "Api error"+ err.getMessage());
        }



    }
}