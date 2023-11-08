package com.douglas.whatafridge.Controller.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.douglas.whatafridge.Model.ObjectModels.Recipe;
import com.douglas.whatafridge.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeListViewAdapter extends BaseAdapter {
    final String TAG ="WTF App";
    List<Recipe> recipeList;
    TextView txtRecipeTime;
    TextView txtRecipeName;
    TextView txtRecipeLike;
    TextView txtRecipePrice;
    TextView txtRecipeHealthScore;
    ImageView imgRecipe;

    public RecipeListViewAdapter(List<Recipe> recipeList) {
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
            txtRecipeTime = view.findViewById(R.id.textViewRecipeTime);
            txtRecipeName =view.findViewById(R.id.textViewRecipeName);
            txtRecipeLike =view.findViewById(R.id.textViewRecipeLikes);
            txtRecipeHealthScore=view.findViewById(R.id.textViewRecipeHealthScore);
            txtRecipePrice = view.findViewById(R.id.textViewRecipePrice);
            imgRecipe= view.findViewById(R.id.imageViewRecipeImg);


        } catch (Exception err) {
            Log.d(TAG, "getIteminView: " +err.getMessage());

        }

    }

    public  void setItemView(int i ) {
        try{
            txtRecipeTime.setText("Ready In:" +recipeList.get(i).readyInMinutes + " Minutes");
            txtRecipeName.setText(recipeList.get(i).title);
            txtRecipeLike.setText("Likes:"+recipeList.get(i).aggregateLikes + "");
            txtRecipePrice.setText("Price: $" + recipeList.get(i).pricePerServing);
            txtRecipeHealthScore.setText("HealthScore:"+ recipeList.get(i).healthScore);
            Picasso.get().load(recipeList.get(i).image).into(imgRecipe);

        } catch (Exception err) {
            Log.d(TAG, "setItemView: " + err.getMessage());
        }

    }
}
