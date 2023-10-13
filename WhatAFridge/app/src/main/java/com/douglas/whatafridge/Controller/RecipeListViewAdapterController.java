package com.douglas.whatafridge.Controller;

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

import java.util.List;

public class RecipeListViewAdapterController extends BaseAdapter {
    List<Recipe> recipeList;

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
        TextView txtRecipeID = view.findViewById(R.id.textViewRecipeID);
        TextView txtRecipeName = view.findViewById(R.id.textViewRecipeName);
        TextView txtRecipeLike = view.findViewById(R.id.txtViewRecipeLikes);
        Button btnGetRecipeInfo = view.findViewById(R.id.btnGetRecipeInfo);
        Log.d("WTFApp","Recipe Title is:" +recipeList.get(i).title);
        //ImageView imgRecipe = view.findViewById(R.id.imageViewRecipeImg);

        try{
            txtRecipeID.setText(recipeList.get(i).id + "");
            txtRecipeName.setText(recipeList.get(i).title);
            txtRecipeLike.setText(recipeList.get(i).likes + "");
            //btnGetRecipeInfo.setText("Get Recipe Info");
        }
        catch (Exception err) {
            Log.d("WTFApp","Failed to load data into ListView:"+err.getMessage());

        }

        return view;
    }
}
