package com.sa.rezq.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sa.rezq.Models.PopularPlacesModelClass;
import com.sa.rezq.Models.TrendingModelClass;
import com.sa.rezq.R;

import java.util.ArrayList;

public class PopularPlacesAdapter extends RecyclerView.Adapter<PopularPlacesAdapter.viewHolder>{

    Context context;
    ArrayList<PopularPlacesModelClass> arrayList;

    public PopularPlacesAdapter(Context context, ArrayList<PopularPlacesModelClass> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public PopularPlacesAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.popular_places_recyclerview_data, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularPlacesAdapter.viewHolder holder, int position) {
        holder.iconName.setText(arrayList.get(position).getCategory_place_name());
        Glide.with(context)
                .load(arrayList.get(position).getCategory_image())
                .into(holder.icon);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView iconName;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.categoryImage);
            iconName = (TextView) itemView.findViewById(R.id.TvCategoryName);
        }
    }
}
