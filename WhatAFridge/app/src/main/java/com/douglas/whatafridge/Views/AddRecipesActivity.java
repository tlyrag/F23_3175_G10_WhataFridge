package com.douglas.whatafridge.Views;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.douglas.whatafridge.Controller.Adapters.AddRecipeRecyclerViewAdapter;
import com.douglas.whatafridge.Controller.Database.RecipeDBController;
import com.douglas.whatafridge.Model.ObjectModels.Ingredients;
import com.douglas.whatafridge.Model.ObjectModels.Recipe;
import com.douglas.whatafridge.R;

import java.util.ArrayList;

public class AddRecipesActivity extends WFTemplate {
    public final String TAG = "WTF APP";
    EditText editTextRecipeName;
    EditText editTextRecipeSummary;
    EditText editTextRecipeInstruction;
    EditText editTextRecipeNumOfIngredients;
    AddRecipeRecyclerViewAdapter adapter;
    RecyclerView ingredientRecyclerView;
    Button btnAddIngreds;
    Button btnaddRecipe;
    Recipe myRecipe;
    RecipeDBController db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        getItemView();
        createRecyclerView();
        db = new RecipeDBController(AddRecipesActivity.this);

        btnaddRecipe.setOnClickListener(view -> {
            if(createRecipeObj()) {
                long id = addDataToDB(myRecipe);
                Intent recipeDetail = createBundle(id);
                startActivity(recipeDetail);
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
            editTextRecipeName = findViewById(R.id.editTextRecipeName);
            editTextRecipeSummary = findViewById(R.id.editTextRecipeSummary);
            editTextRecipeInstruction = findViewById(R.id.editTextRecipeInstructions);
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
            adapter = new AddRecipeRecyclerViewAdapter(1);
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
            AddRecipeRecyclerViewAdapter.ImageViewerHolder IngredientViewHolder = (AddRecipeRecyclerViewAdapter.ImageViewerHolder) viewHolder;
            String ingredientName =IngredientViewHolder.getIngredient();
            Ingredients newIngredient = new Ingredients();
            newIngredient.name = ingredientName;
            ingredientsList.add(newIngredient);
        }
        myRecipe = new Recipe(recipeName,ingredientsList,recipeSummary,recipeInstructions);
        return true;
    }
    public long addDataToDB(Recipe recipe) {
        StringBuilder RecipeIngredients = new StringBuilder();

        for(int i =0;i<recipe.usedIngredients.size();i++) {
            RecipeIngredients.append(recipe.usedIngredients.get(i).name);

            if(i!= recipe.usedIngredients.size()-1) {
                RecipeIngredients.append(",");
            }
        }
        //Toast.makeText(this, recipe.title+" Successfully Added to DB", Toast.LENGTH_SHORT).show();
        long id = db.addNewRecipe(recipe.title,recipe.summary,recipe.instructions,RecipeIngredients.toString());
        return id;
    }
    public Intent createBundle(long id) {
        Intent recipeDetail = new Intent(this, RecipeDetailActivity.class);
        Bundle recipeBundle = new Bundle();
        recipeBundle.putLong("id",id);
        recipeBundle.putBoolean("isMyRecipe",true);
        recipeDetail.putExtras(recipeBundle);
        return recipeDetail;
    }
}
