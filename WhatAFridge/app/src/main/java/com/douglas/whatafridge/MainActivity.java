package com.douglas.whatafridge;

import androidx.appcompat.app.AppCompatActivity;
import com.douglas.whatafridge.Controller.*;
import com.douglas.whatafridge.Model.ObjectModels.Recipe;
import com.douglas.whatafridge.Model.SpoonApiModels.GenericAPIResponse;
//import com.douglas.whatafridge.Model.*;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    SpoonacularController api = new SpoonacularController();
    TextView txtTitle;
    ArrayList<TextView> txtRecipes = new ArrayList<>();

    Button btnFindRecipe;
    EditText editTextFindIngred;

    /// View Actions and Handlings
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Getting View Elements by ID
        txtTitle = findViewById(R.id.TextViewTitle);
        txtRecipes.add(findViewById(R.id.textViewRecipe0));
        txtRecipes.add(findViewById(R.id.textViewRecipe1));
        txtRecipes.add(findViewById(R.id.textViewRecipe2));
        btnFindRecipe = findViewById(R.id.btnFindRecipe);
        editTextFindIngred = findViewById(R.id.editTextFindRecipe);

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
            Toast.makeText(this, err.getMessage(), Toast.LENGTH_SHORT).show();
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
                    ArrayList<Recipe> recipes = ListObject;

                    for(int i =0; i<recipes.size();i++) {
                        updateRecipe(txtRecipes.get(i),recipes.get(i));
                    }

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