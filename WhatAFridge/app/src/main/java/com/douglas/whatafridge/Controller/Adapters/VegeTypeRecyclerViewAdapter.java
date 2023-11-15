package com.douglas.whatafridge.Controller.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.douglas.whatafridge.Model.ObjectModels.VegeTypeImage;
import com.douglas.whatafridge.R;

import java.util.List;

public class VegeTypeRecyclerViewAdapter extends RecyclerView.Adapter<VegeTypeRecyclerViewAdapter.ImageViewHolder>{

    List<VegeTypeImage> AdapterImages;
    List<VegeTypeImage> oppAdapterImages;


    public VegeTypeRecyclerViewAdapter(List<VegeTypeImage> adapterImages, List<VegeTypeImage> oppAdapterImages) {
        AdapterImages = adapterImages;
        this.oppAdapterImages = oppAdapterImages;
    }


    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.vege_type_layout,parent,false);
        ImageViewHolder holder = new ImageViewHolder(itemView);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        holder.imgViewItem.setImageResource(AdapterImages.get(position).getImgPic());

        holder.imgViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeView(holder.getAdapterPosition());
            }
        });
    }

    public void changeView(int position){
        VegeTypeImage vegeTypeImage = new VegeTypeImage(AdapterImages.get(position).getImgId(), AdapterImages.get(position).getImgName(), AdapterImages.get(position).getImgPic());
        VegeTypeImage oppvegeTypeImage = new VegeTypeImage(AdapterImages.get(position).getImgId(), AdapterImages.get(position).getImgName(), oppAdapterImages.get(position).getImgPic());

        AdapterImages.set(position, oppvegeTypeImage);
        oppAdapterImages.set(position, vegeTypeImage);

        notifyItemChanged(position);
    }

    @Override
    public int getItemCount() {
        return AdapterImages.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{
        ImageView imgViewItem;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imgViewItem = itemView.findViewById(R.id.imgViewVegeType);
        }
    }

}
