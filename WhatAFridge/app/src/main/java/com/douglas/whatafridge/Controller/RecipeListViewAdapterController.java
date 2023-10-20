package com.douglas.whatafridge.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.douglas.whatafridge.Model.ObjectModels.Recipe;
import com.douglas.whatafridge.R;
import com.douglas.whatafridge.Views.RecipeDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeListViewAdapterController extends BaseAdapter {
    final String TAG ="WTF App";
    List<Recipe> recipeList;
    TextView txtRecipeID;
    TextView txtRecipeName;
    TextView txtRecipeLike;

    ImageView imgRecipe;

    public RecipeListViewAdapterController(List<Recipe> recipeList) {
        this.recipeList = recipeList;
    }

    @Override
    public int getCount() {
        return recipeList.size();
    }

    @Override
    public Object getItem(int i) {
        return recipeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return recipeList.get(i).id;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recipe_displayer_layout,viewGroup,false);
        }

        getIteminView(view);
        setItemView(i);

        return view;
    }

    public void getIteminView(View view) {
        try {
            txtRecipeID = view.findViewById(R.id.textViewRecipeID);
            txtRecipeName =view.findViewById(R.id.textViewRecipeName);
            txtRecipeLike =view.findViewById(R.id.textViewRecipeLikes);
            imgRecipe= view.findViewById(R.id.imageViewRecipeImg);


        } catch (Exception err) {
            Log.d(TAG, "getIteminView: " +err.getMessage());

        }

    }

    public  void setItemView(int i ) {
        try{
            txtRecipeID.setText(recipeList.get(i).id + "");
            txtRecipeName.setText(recipeList.get(i).title);
            txtRecipeLike.setText(recipeList.get(i).likes + "");
            Picasso.get().load(recipeList.get(i).image).into(imgRecipe);

        } catch (Exception err) {
            Log.d(TAG, "setItemView: " + err.getMessage());
        }

    }
}
