package com.douglas.whatafridge.Controller.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.douglas.whatafridge.Model.ObjectModels.Recipe;
import com.douglas.whatafridge.R;

import java.util.List;

public class MyRecipeRecyclerViewAdapter extends RecyclerView.Adapter<MyRecipeRecyclerViewAdapter.ItemViewHolder>{
    List<Recipe> recipeList;
    int SelectedID;
    OnItemClickListner onItemClickListner;
    String TAG = "WTF APP";

    public MyRecipeRecyclerViewAdapter(List<Recipe> recipeList,OnItemClickListner onItemClickListner) {

        this.recipeList = recipeList;
        this.onItemClickListner = onItemClickListner;

    }

    public MyRecipeRecyclerViewAdapter(List<Recipe> recipeList) {
        this.recipeList = recipeList;
    }

    public int getSelectedID() {
        return SelectedID;
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

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition(); // Use a local variable for clarity
                if(position != RecyclerView.NO_POSITION) { // Check if position is valid
                    onItemClickListner.onItemCLick(position);
                    SelectedID = position; // Store the selected id if needed for later
                    Log.d(TAG, "ItemViewHolder: SelectedID " + SelectedID);
                }
                notifyDataSetChanged();
                //Toast.makeText(v.getContext(), "Clicked on item: " + recipeList.get(position), Toast.LENGTH_SHORT).show();
            });

        }
        public void getViewItem(View itemView) {
            myRecipeTitle = itemView.findViewById(R.id.textViewMyRecipeName);
            myRecipeSummary = itemView.findViewById(R.id.textViewMyRecipeSummary);
        }

    }
    public interface OnItemClickListner {
        public void onItemCLick(int i);
    }
}
