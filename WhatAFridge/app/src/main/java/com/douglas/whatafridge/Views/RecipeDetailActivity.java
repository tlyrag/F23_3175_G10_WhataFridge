package com.douglas.whatafridge.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.douglas.whatafridge.R;

import org.w3c.dom.Text;

public class RecipeDetailActivity extends WFTemplate {
    TextView textViewRecipeTitle;
    int recipeID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        getExtraData();
        getViewItems();
        setViewItemInfo();
    }
    public void getExtraData() {
        Bundle bundle = getIntent().getExtras();
        recipeID = bundle.getInt("recipeID");
    }
    public void getViewItems() {
        textViewRecipeTitle = findViewById(R.id.textViewRecipeDetailTitle);
    }
    public void setViewItemInfo() {
        textViewRecipeTitle.setText("Recipe ID:" + recipeID);

    }
}