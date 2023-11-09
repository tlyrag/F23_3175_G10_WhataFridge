package com.douglas.whatafridge.Views;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.douglas.whatafridge.Controller.Database.RecipeDBController;
import com.douglas.whatafridge.Controller.SpoonacularController;
import com.douglas.whatafridge.Model.ObjectModels.Ingredients;
import com.douglas.whatafridge.Model.ObjectModels.Recipe;
import com.douglas.whatafridge.Model.SpoonApiModels.GenericAPIResponse;
import com.douglas.whatafridge.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetailActivity extends WFTemplate {
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
    long myRecipeID;
    boolean isMyRecipe;
    RecipeDBController db;
    List<Recipe> recipes = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
         db = new RecipeDBController(this);
        getExtraData();
        getViewItems();
        Log.d(TAG, "onCreate: isMyRecipe is:" + isMyRecipe);
        if(isMyRecipe) {
            Log.d(TAG, "onCreate: Getting my RecipeID");
            getMyRecipe(myRecipeID);
        } else {
            getRecipeDetailsApi();
        }

    }
    public void getExtraData() {
        Bundle bundle = getIntent().getExtras();
        recipeID =  Integer.toString(bundle.getInt("recipeID", Integer.parseInt("0"))) ;
        myRecipeID = bundle.getLong("id",0);
        isMyRecipe = bundle.getBoolean("isMyRecipe",false);


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

    /**
     * @author Thiago
     *
     * Overloaded mehtod without image
     */
    public void setViewItemInfo(String recipeName, String ingredients, String summary,String instruction) {
        textViewIngredientsTitle.setText("Ingredients");
        textViewSummaryTitle.setText("Summary");
        textViewRecipeInstructionsTitle.setText("Instruction");
        textViewRecipeTitle.setText(recipeName);
        textViewIngredientList.setText(ingredients);
        textViewSummary.setText(Html.fromHtml(summary));
        textViewRecipeInstructions.setText(Html.fromHtml(instruction));

    }


    public String createIngredintText(ArrayList<Ingredients> ingredientsList, boolean isMyRecipe) {
        StringBuilder ingredient = new StringBuilder();
        if(isMyRecipe) {
            for(int i =0; i<ingredientsList.size();i++) {
                ingredient.append( i+1+" : " +ingredientsList.get(i).name +" "  + "\n");
            }
        } else {
            for(int i =0; i<ingredientsList.size();i++) {
                ingredient.append( i+1+" : " + ingredientsList.get(i).amount +" " + ingredientsList.get(i).unit +" of "+ingredientsList.get(i).name +" "  + "\n");
            }
        }

        return ingredient.toString();

    }
    public void getRecipeDetailsApi() {
        try {
            api.getRecipeByID(this, recipeID, new GenericAPIResponse() {
                @Override
                public void onSuccess(ArrayList ListObject) {
                    recipes = ListObject;
                    Recipe currentRecipe = recipes.get(0);
                    String ingredients = createIngredintText(currentRecipe.extendedIngredients,false);
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
    public void getMyRecipe(long id) {
        try {
            Recipe myRecipe = db.getRecipeByID(id);
            String Ingredients = createIngredintText(myRecipe.usedIngredients,true);
            setViewItemInfo(myRecipe.title,Ingredients,myRecipe.summary, myRecipe.instructions);

        } catch (Exception err) {
            Log.d(TAG, "getMyRecipe: failed to get my recipe data " + err.getMessage());
        }
    }
}