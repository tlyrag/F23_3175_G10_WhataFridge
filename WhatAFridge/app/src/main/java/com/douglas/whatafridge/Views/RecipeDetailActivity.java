package com.douglas.whatafridge.Views;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.douglas.whatafridge.Controller.SpoonacularController;
import com.douglas.whatafridge.Model.ObjectModels.Ingredients;
import com.douglas.whatafridge.Model.ObjectModels.Recipe;
import com.douglas.whatafridge.Model.SpoonApiModels.GenericAPIResponse;
import com.douglas.whatafridge.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetailActivity extends WTFemplate {
    SpoonacularController api = new SpoonacularController();
    TextView textViewRecipeTitle;
    TextView textViewIngredientsTitle;
    TextView textViewSummaryTitle;
    TextView textViewRecipeInstructionsTitle;
    TextView textViewIngredientList;
    TextView textViewSummary;
    TextView textViewRecipeInstructions;

    ImageView imageViewRecipe;
    String recipeID;
    List<Recipe> recipes = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        getExtraData();
        getViewItems();
        getRecipeDetails();
    }
    public void getExtraData() {
        Bundle bundle = getIntent().getExtras();
        recipeID =  Integer.toString(bundle.getInt("recipeID"))  ;
    }
    public void getViewItems() {
        textViewRecipeTitle = findViewById(R.id.textViewRecipeDetailTitle);
        textViewIngredientList = findViewById(R.id.textViewIngredientList);
        textViewSummary = findViewById(R.id.textViewRecipeDetailsSummary);
        textViewIngredientsTitle = findViewById(R.id.textViewIngredientsTitle);
        imageViewRecipe = findViewById(R.id.imageViewRecipeDetailImage);
        textViewSummaryTitle = findViewById(R.id.textViewRecipeDetailSummaryTitle);
        textViewRecipeInstructionsTitle = findViewById(R.id.textViewRecipeInstructionTitle);
        textViewRecipeInstructions=findViewById(R.id.textViewRecipeInstruction);
    }
    public void setViewItemInfo(String recipeName, String ingredients, String summary, String imgUrl,String instruction) {
        textViewIngredientsTitle.setText("Ingredients");
        textViewSummaryTitle.setText("Summary");
        textViewRecipeInstructionsTitle.setText("Instruction");
        textViewRecipeTitle.setText(recipeName);
        textViewIngredientList.setText(ingredients);
        textViewSummary.setText(Html.fromHtml(summary));
        textViewRecipeInstructions.setText(Html.fromHtml(instruction));
        Picasso.get().load(imgUrl).into(imageViewRecipe);

    }
    public String createIngredintText(ArrayList<Ingredients> ingredientsList) {
        StringBuilder ingredient = new StringBuilder();

        for(int i =0; i<ingredientsList.size();i++) {
            ingredient.append( i+1+" : " + ingredientsList.get(i).amount +" " + ingredientsList.get(i).unit +" of "+ingredientsList.get(i).name +" "  + "\n");
        }
        return ingredient.toString();

    }
    public void getRecipeDetails() {
        try {
            api.getRecipeByID(this, recipeID, new GenericAPIResponse() {
                @Override
                public void onSuccess(ArrayList ListObject) {
                    recipes = ListObject;
                    Recipe currentRecipe = recipes.get(0);
                    String ingredients = createIngredintText(currentRecipe.extendedIngredients);
                    setViewItemInfo(currentRecipe.title,ingredients,currentRecipe.summary, currentRecipe.image,currentRecipe.instructions);
                }

                @Override
                public void onFail(String ErrorMessage) {

                }
            });
        } catch (Exception err) {
            Log.d(TAG, "getRecipeDetails: " + err.getMessage());
        }
    }
}