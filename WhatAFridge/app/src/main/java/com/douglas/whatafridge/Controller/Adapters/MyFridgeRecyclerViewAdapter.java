package com.douglas.whatafridge.Controller.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.douglas.whatafridge.Model.ObjectModels.Ingredients;
import com.douglas.whatafridge.Model.ObjectModels.Recipe;
import com.douglas.whatafridge.R;

import java.util.List;

public class MyFridgeRecyclerViewAdapter extends RecyclerView.Adapter<MyFridgeRecyclerViewAdapter.ItemViewHolder>{
    List<Ingredients> ingredientsList;
    int SelectedID;
    OnItemClickListner onItemClickListner;
    String TAG = "WTF APP";

    public MyFridgeRecyclerViewAdapter(List<Ingredients> ingredientsList, OnItemClickListner onItemClickListner) {

        this.ingredientsList = ingredientsList;
        this.onItemClickListner = onItemClickListner;

    }

    public MyFridgeRecyclerViewAdapter(List<Ingredients> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    public int getSelectedID() {
        return SelectedID;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View ItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.myfridge_recycler_layout,parent,false);
        ItemViewHolder holder = new ItemViewHolder(ItemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Ingredients currIngred = ingredientsList.get(position);
        holder.myIngredientName.setText(currIngred.name);
        holder.myIngredBBefore.setText("Best Before: " + currIngred.BestBefore);
        holder.myIngredQuantity.setText("Current Amount Available:" + Double.toString(currIngred.amount));
    }

    @Override
    public int getItemCount() {
        return ingredientsList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView myIngredientName;
        TextView myIngredQuantity;
        TextView myIngredBBefore;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            getViewItem(itemView);
            try{
                itemView.setOnClickListener(v -> {
                    int position = getAdapterPosition();

                    if(position != RecyclerView.NO_POSITION) {
                        onItemClickListner.onItemCLick(position);
                        SelectedID = position;
                    }
                    notifyDataSetChanged();
                    //Toast.makeText(v.getContext(), "Clicked on item: " + recipeList.get(position), Toast.LENGTH_SHORT).show();
                });
            } catch (Exception err) {
                Log.d(TAG, "ItemViewHolder: " + err.getMessage());
            }


        }
        public void getViewItem(View itemView) {
            myIngredientName = itemView.findViewById(R.id.textViewMyIngredNames);
            myIngredQuantity = itemView.findViewById(R.id.textViewMyIngredQuantity);
            myIngredBBefore = itemView.findViewById(R.id.textViewMyIngredBBefore);
        }

    }
    public interface OnItemClickListner {
        public void onItemCLick(int i);
    }
}
