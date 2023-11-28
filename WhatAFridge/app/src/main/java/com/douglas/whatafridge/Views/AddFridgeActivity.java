package com.douglas.whatafridge.Views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.douglas.whatafridge.Controller.Database.FridgeDBController;
import com.douglas.whatafridge.Model.ObjectModels.Ingredients;
import com.douglas.whatafridge.R;

public class AddFridgeActivity extends WFTemplate {
    public final String TAG = "WTF APP";
    EditText editTextItemName;
    EditText editTextItemBestBefore;
    EditText editTextItemQuantity;

    Button btnaddItem;
    Ingredients ingredient;
    FridgeDBController db;
    boolean isMyIngred = false;
    long ingredId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fridge);
        db = new FridgeDBController(AddFridgeActivity.this);
        getItemView();
        getExtraData();


        btnaddItem.setOnClickListener(view -> {
                if(isMyIngred) {
                    if(createIngredObj()) {
                        Log.d(TAG, "onCreate: " + ingredient);
                        db.updateIngredientData(ingredient);
                        Toast.makeText(this, ingredient.name+ "Updated in my Fridge", Toast.LENGTH_LONG).show();
                        clearFields();
                        isMyIngred=false;
                    }
                } else {
                    if(createIngredObj()) {
                        long id = addDataToDB(ingredient);
                        Toast.makeText(this, ingredient.name+ "Added to my Fridge", Toast.LENGTH_LONG).show();
                        clearFields();
                    }
                }

        });
    }


    public void getItemView() {
        try {
            editTextItemName = findViewById(R.id.editTextIngredientName);
            editTextItemBestBefore = findViewById(R.id.editTextIngredientBestBefore);
            editTextItemQuantity = findViewById(R.id.editTextIngredientQuantity);
            btnaddItem = findViewById(R.id.btnAddIngredient);
        } catch (Exception err) {
            Log.d(TAG, "getItemView: ");
        }
    }

    public boolean createIngredObj() {
        String ingredName = editTextItemName.getText().toString();

        String BestBefore = editTextItemBestBefore.getText().toString();

        try{
            double quantity = Double.parseDouble(editTextItemQuantity.getText().toString()) ;

            if(isMyIngred) {
                int itemID =Integer.parseInt(ingredId+"");
                ingredient = new Ingredients(itemID,quantity,BestBefore,ingredName);
            }
            else {
                ingredient = new Ingredients(quantity,BestBefore,ingredName);
            }

        }
        catch (Exception ex) {
            Toast.makeText(this, "Quantity must be a valid Number", Toast.LENGTH_SHORT).show();
            editTextItemQuantity.setText("");
            return  false;
        }

        return true;
    }
    public void clearFields() {
        editTextItemName.setText("");
        editTextItemBestBefore.setText("");
        editTextItemQuantity.setText("");
    }
    public long addDataToDB(Ingredients ingredient) {

        long id = db.addNewIngredient(ingredient.name,ingredient.BestBefore,ingredient.amount);
        return id;
    }
    public void getExtraData() {
        Bundle bundle = getIntent().getExtras();

        if(bundle!=null) {
            ingredId = bundle.getLong("id",0);
            isMyIngred = bundle.getBoolean("isMyFridge",false);
        }

        if(isMyIngred) {
            ingredient = db.getIngredientByID(ingredId);
            editTextItemName.setText(ingredient.name);
            editTextItemBestBefore.setText(ingredient.BestBefore);
            editTextItemQuantity.setText(ingredient.amount +"");
        }


    }
}
