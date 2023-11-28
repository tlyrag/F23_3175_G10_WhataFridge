package com.douglas.whatafridge.Views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import com.douglas.whatafridge.R;

public class HomePageActivity extends WFTemplate {
    ImageButton btnMyRecipe;
    ImageButton btnSearchRecipe;
    ImageButton btnAddRecipe;
    ImageButton btnAddFridge;
    ImageButton btnProfile;
    ImageButton btnMyFridge;
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

        btnAddFridge.setOnClickListener(view-> {
            Intent newIntent = new Intent(this, AddFridgeActivity.class);
            startActivity(newIntent);
        });

        btnMyFridge.setOnClickListener(view-> {
            Intent newIntent = new Intent(this, MyFridgeActivity.class);
            startActivity(newIntent);
        });
    }

    public void getItemViews() {
        btnMyRecipe = findViewById(R.id.btnMyRecipe);
        btnSearchRecipe= findViewById(R.id.btnSearchRecipe);
        btnAddRecipe = findViewById(R.id.btnAddRecipeAct);
        btnProfile = findViewById(R.id.btnProfile);
        btnAddFridge =findViewById(R.id.btnAddFridge);
        btnMyFridge = findViewById(R.id.btnMyFridge);
    }

}