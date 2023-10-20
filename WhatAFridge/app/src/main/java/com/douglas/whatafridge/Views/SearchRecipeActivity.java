package com.douglas.whatafridge.Views;

import com.douglas.whatafridge.Controller.*;
import com.douglas.whatafridge.Model.ObjectModels.Recipe;
import com.douglas.whatafridge.Model.SpoonApiModels.GenericAPIResponse;
import com.douglas.whatafridge.R;
//import com.douglas.whatafridge.Model.*;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SearchRecipeActivity extends WFTemplate {
    SpoonacularController api = new SpoonacularController();
    TextView txtTitle;
    ArrayList<TextView> txtRecipes = new ArrayList<>();
    List<Recipe> recipes = new ArrayList<>();
    Button btnFindRecipe;
    EditText editTextFindIngred;
    ListView listViewRecipes;

    /// View Actions and Handlings
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Getting View Elements by ID

        btnFindRecipe = findViewById(R.id.btnFindRecipe);
        editTextFindIngred = findViewById(R.id.editTextFindRecipe);
        listViewRecipes = findViewById(R.id.ListViewRecipe);


        try{
        btnFindRecipe.setOnClickListener(view -> {
            String ingredients = getEditText();

            getRecipes(this,ingredients);
        });


        } catch(Exception err) {
            Log.d("WTF App", "Api error"+ err.getMessage());
        }
    }
    ////////////////////////////////View Methods/////////////////////////////

    /// Get Text in Edit Text and return as a String
    public  String getEditText() {
        try {
            String ingredients = editTextFindIngred.getText().toString();
            return ingredients;
        } catch (Exception err) {
            Toast.makeText(this, "Error in getEditText Method"+err.getMessage(), Toast.LENGTH_SHORT).show();
            editTextFindIngred.setText("");
            return "";
        }
    }

    /// Update a TextView based on Recipes
    public void updateRecipe(TextView txt,Recipe recipe) {
        txt.setText(recipe.title);
    }


    /////// Call Get Recipe API and return the Promisse
    public void getRecipes(Context context, String ingredients) {
        try {
            api.getRecipeByIngredient(context, ingredients, 3, new GenericAPIResponse() {
                @Override
                public void onSuccess(ArrayList ListObject) {
                    recipes = ListObject;

                    if(recipes.size()==0 || recipes.isEmpty() || recipes ==null) {
                        Recipe dummyRecipe = new Recipe();
                        dummyRecipe.title = "No Recipe was FOund";
                        recipes.add(dummyRecipe);
                    }
                    RecipeListViewAdapterController recipeAdapter = new RecipeListViewAdapterController(recipes);
                    listViewRecipes.setAdapter(recipeAdapter);
                }

                @Override
                public void onFail(String ErrorMessage) {

                }
            });
        } catch (Exception err) {
            Log.d("WTF App","Fail on getRecipesMethod:" + err.getMessage());
        }

    }

}