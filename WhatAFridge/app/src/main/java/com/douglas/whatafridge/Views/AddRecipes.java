package com.douglas.whatafridge.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.douglas.whatafridge.Controller.AddRecipeRecyclerViewController;
import com.douglas.whatafridge.Controller.RecipeListViewAdapterController;
import com.douglas.whatafridge.R;

public class AddRecipes extends WTFemplate {
    public final String TAG = "WTF APP";
    EditText editTextRecipeName;
    EditText editTextRecipeSummary;
    EditText editTextRecipeInstruction;
    EditText editTextRecipeNumOfIngredients;
    AddRecipeRecyclerViewController adapter;
    RecyclerView ingredientRecyclerView;
    Button btnAddIngreds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recipe);
        getItemView();
        createRecyclerView();


        btnAddIngreds.setOnClickListener(view -> {
            try {
                int ingrdQtd = Integer.parseInt(editTextRecipeNumOfIngredients.getText().toString());
                adapter.setIngredientsCount(ingrdQtd);
                Toast.makeText(this, "Ingedients Count:" +adapter.getItemCount(), Toast.LENGTH_SHORT).show();
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
}
