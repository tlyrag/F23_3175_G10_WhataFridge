package com.douglas.whatafridge.Views;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.douglas.whatafridge.Controller.AddRecipeRecyclerViewController;
import com.douglas.whatafridge.Controller.DBController;
import com.douglas.whatafridge.Model.ObjectModels.Ingredients;
import com.douglas.whatafridge.Model.ObjectModels.Recipe;
import com.douglas.whatafridge.R;

import java.util.ArrayList;
import java.util.List;

public class AddRecipes extends WTFemplate {
    public final String TAG = "WTF APP";
    EditText editTextRecipeName;
    EditText editTextRecipeSummary;
    EditText editTextRecipeInstruction;
    EditText editTextRecipeNumOfIngredients;
    AddRecipeRecyclerViewController adapter;
    RecyclerView ingredientRecyclerView;
    Button btnAddIngreds;
    Button btnaddRecipe;
    Recipe myRecipe;
    DBController db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        getItemView();
        createRecyclerView();
        db = new DBController(AddRecipes.this);

        btnaddRecipe.setOnClickListener(view -> {
            if(createRecipeObj()) {
                addDataToDB(myRecipe);
            };

            //Toast.makeText(this, myRecipe+"", Toast.LENGTH_SHORT).show();

        });

        btnAddIngreds.setOnClickListener(view -> {
            try {
                int ingrdQtd = Integer.parseInt(editTextRecipeNumOfIngredients.getText().toString());
                adapter.setIngredientsCount(ingrdQtd);
                //Toast.makeText(this, "Ingedients Count:" +adapter.getItemCount(), Toast.LENGTH_SHORT).show();
            } catch (Exception err) {
                Log.d(TAG, "onCreate: Invalid Format" + err.getMessage());
            }

        });


    }
    public void getItemView() {
        try {
            editTextRecipeName = findViewById(R.id.editTextMyRecipeName);
            editTextRecipeSummary = findViewById(R.id.editTextMyRecipeSummary);
            editTextRecipeInstruction = findViewById(R.id.editTextMyRecipeInstruction);
            editTextRecipeNumOfIngredients = findViewById(R.id.editTextNumberOfIngred);
            ingredientRecyclerView = findViewById(R.id.ingredientRecyclerView);
            btnAddIngreds = findViewById(R.id.btnAddIngreds);
            btnaddRecipe = findViewById(R.id.btnAddRecipe);
        } catch (Exception err) {
            Log.d(TAG, "getItemView: ");
        }
    }
    public void createRecyclerView() {
        try{
            adapter = new AddRecipeRecyclerViewController(1);
            LinearLayoutManager lm  = new LinearLayoutManager(this);
            ingredientRecyclerView.setLayoutManager(lm);
            ingredientRecyclerView.setAdapter(adapter);
        } catch (Exception err) {

        }
    }
    public boolean createRecipeObj() {
        String recipeName = editTextRecipeName.getText().toString();
        String recipeSummary = editTextRecipeSummary.getText().toString();
        String recipeInstructions = editTextRecipeInstruction.getText().toString();

        if(recipeName.isEmpty() || recipeSummary.isEmpty()  || recipeInstructions.isEmpty()) {
            Toast.makeText(this, "Please enter all Data", Toast.LENGTH_LONG).show();
            return false;
        }

        ArrayList<Ingredients> ingredientsList = new ArrayList<>();

        for(int i=0;i<ingredientRecyclerView.getChildCount();i++) {
            View itemView = ingredientRecyclerView.getChildAt(i);
            RecyclerView.ViewHolder viewHolder = ingredientRecyclerView.getChildViewHolder(itemView);
            AddRecipeRecyclerViewController.ImageViewerHolder IngredientViewHolder = (AddRecipeRecyclerViewController.ImageViewerHolder) viewHolder;
            String ingredientName =IngredientViewHolder.getIngredient();
            Ingredients newIngredient = new Ingredients();
            newIngredient.name = ingredientName;
            ingredientsList.add(newIngredient);
        }
        myRecipe = new Recipe(recipeName,ingredientsList,recipeSummary,recipeInstructions);
        return true;
    }
    public void addDataToDB(Recipe recipe) {
        StringBuilder RecipeIngredients = new StringBuilder();

        for(int i =0;i<recipe.usedIngredients.size();i++) {
            RecipeIngredients.append(recipe.usedIngredients.get(i).name);

            if(i!= recipe.usedIngredients.size()-1) {
                RecipeIngredients.append(",");
            }
        }
        Toast.makeText(this, recipe.title+" Successfully Added to DB", Toast.LENGTH_SHORT).show();
        db.addNewCourse(recipe.title,recipe.summary,recipe.instructions,RecipeIngredients.toString());
    }
}
