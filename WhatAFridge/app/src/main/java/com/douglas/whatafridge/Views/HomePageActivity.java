package com.douglas.whatafridge.Views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.douglas.whatafridge.R;

public class HomePageActivity extends WFTemplate {
    Button btnMyRecipe;
    Button btnSearchRecipe;
    Button btnAddRecipe;

    Button btnProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        getItemViews();

        btnSearchRecipe.setOnClickListener(view -> {
            Intent newIntent = new Intent(this, SearchRecipeActivity.class);
            startActivity(newIntent);
        });

        btnMyRecipe.setOnClickListener(view -> {
            Intent newIntent = new Intent(this, MyRecipesActivity.class);
            startActivity(newIntent);
        });
        btnAddRecipe.setOnClickListener(view -> {
            Intent newIntent = new Intent(this, AddRecipesActivity.class);
            startActivity(newIntent);
        });

        btnProfile.setOnClickListener(view -> {
            Intent newIntent = new Intent(this, ProfileActivity.class);
            startActivity(newIntent);
        });
    }

    public void getItemViews() {
        btnMyRecipe = findViewById(R.id.btnMyRecipe);
        btnSearchRecipe= findViewById(R.id.btnSearchRecipe);
        btnAddRecipe = findViewById(R.id.btnAddRecipeAct);
        btnProfile = findViewById(R.id.btnProfile);
    }

}