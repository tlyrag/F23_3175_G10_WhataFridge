package com.douglas.whatafridge.Controller;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.douglas.whatafridge.Model.ObjectModels.Ingredients;
import com.douglas.whatafridge.R;

import java.util.List;

public class AddRecipeRecyclerViewController extends RecyclerView.Adapter<AddRecipeRecyclerViewController.ImageViewerHolder>{
    public final String TAG = "WTF APP";
    List<Ingredients> ingredientsList;

    public int getIngredientsCount() {
        return ingredientsCount;
    }

    public void setIngredientsCount(int ingredientsCount) {
        this.ingredientsCount = ingredientsCount;
        notifyDataSetChanged();
    }

    public AddRecipeRecyclerViewController(int ingredientsCount) {
        this.ingredientsCount = ingredientsCount;
        notifyDataSetChanged();
    }

    int ingredientsCount;
    @NonNull
    @Override
    public ImageViewerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_adder_layout,parent,false);
        ImageViewerHolder holder = new ImageViewerHolder(itemView);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewerHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return ingredientsCount;
    }

    public class ImageViewerHolder extends RecyclerView.ViewHolder {
        EditText editTextIngredientName;

        public ImageViewerHolder(@NonNull View itemView) {
            super(itemView);
            getItemView(itemView);
        }

        public void getItemView(View itemView) {
            try {
                editTextIngredientName = itemView.findViewById(R.id.editTextIngredientName);

            } catch (Exception err) {
                Log.d(TAG, "getItemView: ");
            }
        }
        public String getIngredient() {
            String ingredient = editTextIngredientName.getText().toString();
            return ingredient;
        }

    }
}
