package com.douglas.whatafridge.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.douglas.whatafridge.R;



public class MainPageActivity extends WFTemplate {
    CardView cardViewProfile;
    CardView cardViewMyFridge;
    CardView cardViewAddItems;
    CardView cardViewAddRecipe;
    CardView cardViewMyRecipe;
    CardView cardViewSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        getItemViews();

        cardViewSearch.setOnClickListener(view -> {
            Intent newIntent = new Intent(this, SearchRecipeActivity.class);
            startActivity(newIntent);
        });

        cardViewMyRecipe.setOnClickListener(view -> {
            Intent newIntent = new Intent(this, MyRecipesActivity.class);
            startActivity(newIntent);
        });
        cardViewAddRecipe.setOnClickListener(view -> {
            Intent newIntent = new Intent(this, AddRecipesActivity.class);
            startActivity(newIntent);
        });

        cardViewProfile.setOnClickListener(view -> {
            Intent newIntent = new Intent(this, ProfileActivity.class);
            startActivity(newIntent);
        });

        cardViewAddItems.setOnClickListener(view-> {
            Intent newIntent = new Intent(this, AddFridgeActivity.class);
            startActivity(newIntent);
        });

        cardViewMyFridge.setOnClickListener(view-> {
            Intent newIntent = new Intent(this, MyFridgeActivity.class);
            startActivity(newIntent);
        });
    }

    public void getItemViews() {
        cardViewProfile = findViewById(R.id.cardViewProfile);
        cardViewMyFridge = findViewById(R.id.cardViewMyFridge);
        cardViewAddItems= findViewById(R.id.cardViewAddItems);
        cardViewAddRecipe= findViewById(R.id.cardViewAddRecipe);
        cardViewMyRecipe= findViewById(R.id.cardViewMyRecipe);
        cardViewSearch= findViewById(R.id.cardViewSearch);

    }
}