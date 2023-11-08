package com.douglas.whatafridge.Controller.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.douglas.whatafridge.Model.ObjectModels.Recipe;
import com.douglas.whatafridge.R;

import java.util.List;

public class MyRecipeRecyclerViewAdapter extends RecyclerView.Adapter<MyRecipeRecyclerViewAdapter.ItemViewHolder>{
    List<Recipe> recipeList;

    public MyRecipeRecyclerViewAdapter(List<Recipe> recipeList) {
        this.recipeList = recipeList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View ItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.myrecie_recycler_layout,parent,false);
        ItemViewHolder holder = new ItemViewHolder(ItemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Recipe currRecipe = recipeList.get(position);
        holder.myRecipeTitle.setText(currRecipe.title);
        holder.myRecipeSummary.setText(currRecipe.summary);
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView myRecipeTitle;
        TextView myRecipeSummary;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            getViewItem(itemView);
        }
        public void getViewItem(View itemView) {
            myRecipeTitle = itemView.findViewById(R.id.textViewMyRecipeName);
            myRecipeSummary = itemView.findViewById(R.id.textViewMyRecipeSummary);
        }

    }
}
