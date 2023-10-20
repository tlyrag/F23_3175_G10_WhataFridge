package com.douglas.whatafridge.Views;

import com.douglas.whatafridge.Controller.*;
import com.douglas.whatafridge.Model.ObjectModels.Recipe;
import com.douglas.whatafridge.Model.SpoonApiModels.GenericAPIResponse;
import com.douglas.whatafridge.R;
//import com.douglas.whatafridge.Model.*;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
        setContentView(R.layout.activity_search_recipe);

        // Getting View Elements by ID

        btnFindRecipe = findViewById(R.id.btnFindRecipe);
        editTextFindIngred = findViewById(R.id.editTextFindRecipe);
        listViewRecipes = findViewById(R.id.ListViewRecipe);


        /**
         * @author: Thiago
         *
         * FindRecipeBy Ingredient Button Handler
         * Call Get Recipe By Ingredients Method
         */

        try{
            btnFindRecipe.setOnClickListener(view -> {
                String ingredients = getEditText();
                getRecipes(this,ingredients);
            });
        } catch(Exception err) {
            Log.d(TAG, "Api error"+ err.getMessage());
        }
        /**
         * @author: Thiago
         * ListView Recipes Handler
         * Creates a bundle with the ID of the selected Item
         * Add ID to next Intent
         * Open Recipe Detail Activity
         */

        try {
            listViewRecipes.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) ->{
                int recipeID = recipes.get(position).id;
                Bundle bundle = new Bundle();
                bundle.putInt("recipeID",recipeID);
                Intent intent = new Intent(SearchRecipeActivity.this,RecipeDetailActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            });
        } catch (Exception err) {
            Log.d(TAG, "onCreate: ");
        }
    }

    ////////////////////////////////View Methods/////////////////////////////

    /**
     * @author: Thiago
     * Method will get what is written in Edit Text and return the ingredients
     * Pop up a toast in case its empty and button was pressed
     */
    public  String getEditText() {
        try {
            String ingredients = editTextFindIngred.getText().toString().trim();

            if(ingredients.isEmpty() || ingredients.equals("")) {
                throw new Exception();
            }
            return ingredients;
        } catch (Exception err) {
            Toast.makeText(this, "Ingredients cannot be Empty"+err.getMessage(), Toast.LENGTH_SHORT).show();
            editTextFindIngred.setText("");
            return "";
        }
    }



    /**
     * @author: Thiago
     * Calls Get Recipe by Ingredients API using the ingredient list in the Edit Text
     * If no recipe is find will result in a blank List
     *
     * Once API is successfull will call the Get Recipe by ID API
     */
    public void getRecipes(Context context, String ingredients) {
        try {
            api.getRecipeByIngredient(context, ingredients, 3, new GenericAPIResponse() {
                @Override
                public void onSuccess(ArrayList ListObject) {


                    if(ListObject.size()==0 || ListObject.isEmpty() || ListObject ==null) {
                        Recipe dummyRecipe = new Recipe();
                        dummyRecipe.title = "No Recipe was Found";
                        recipes.add(dummyRecipe);
                    } else {
                        recipes = ListObject;
                    }

                    String ids = getRecipeIds();

                    getRecipeDetails(context,ids);

                }

                @Override
                public void onFail(String ErrorMessage) {

                }
            });
        } catch (Exception err) {
            Log.d(TAG,"Fail on getRecipesMethod:" + err.getMessage());
        }

    }
    /**
     * @author: Thiago
     * Creates a String with all the ids in the Recipe List separated by ,
     */
    public String getRecipeIds() {
        StringBuilder recipeIds = new StringBuilder();

        if (recipes!=null&& !recipes.isEmpty()) {
            for (int i =0;i<recipes.size();i++) {
                recipeIds.append(recipes.get(i).id);
                //Checking if it is not the last recipe
                if(i!=recipes.size()-1) {
                    recipeIds.append(",");
                }

            }
        }

        return  recipeIds.toString();
    }
    /**
     * @author: Thiago
     * Call api Get RecipeBy ID API to grab the detail of each Recipe
     * Populates the Adapter with the correct information about each recipe
     */
    public void getRecipeDetails(Context context,String ids) {

        api.getRecipeByID(context,ids,new GenericAPIResponse() {
                    @Override
                    public void onSuccess(ArrayList ListObject) {
                        if(ListObject.size()==0 || ListObject.isEmpty() || ListObject ==null) {
                            Recipe dummyRecipe = new Recipe();
                            dummyRecipe.title = "No Recipe was Found";
                            recipes.add(dummyRecipe);
                        } else {
                            recipes = ListObject;
                            RecipeListViewAdapterController recipeAdapter = new RecipeListViewAdapterController(recipes);
                            listViewRecipes.setAdapter(recipeAdapter);
                        }
                    }

                    @Override
                    public void onFail(String ErrorMessage) {

                    }
                }
        );
    }


}