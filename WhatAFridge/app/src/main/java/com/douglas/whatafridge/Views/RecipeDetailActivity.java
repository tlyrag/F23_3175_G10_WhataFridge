package com.douglas.whatafridge.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.douglas.whatafridge.Controller.SpoonacularController;
import com.douglas.whatafridge.Model.ObjectModels.Ingredients;
import com.douglas.whatafridge.Model.ObjectModels.Recipe;
import com.douglas.whatafridge.Model.SpoonApiModels.GenericAPIResponse;
import com.douglas.whatafridge.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetailActivity extends WFTemplate {
    SpoonacularController api = new SpoonacularController();
    TextView textViewRecipeTitle;
    TextView textViewIngredientList;
    TextView textViewPreparation;
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
        textViewPreparation = findViewById(R.id.textViewRecipeDetailsPreparation);
        imageViewRecipe = findViewById(R.id.imageViewRecipeDetailImage);
    }
    public void setViewItemInfo(String recipeName, String ingredients, String preparation, String imgUrl) {
        textViewRecipeTitle.setText(recipeName);

        textViewIngredientList.setText(ingredients);
        textViewPreparation.setText(preparation);
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
                    setViewItemInfo(currentRecipe.title,ingredients,currentRecipe.summary, currentRecipe.image);
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