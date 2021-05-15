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
import com.sa.rezq.Models.CategoryModelClass;
import com.sa.rezq.Models.PopularPlacesModelClass;
import com.sa.rezq.R;

import java.util.ArrayList;

public class PlacesNearyouAdapter extends RecyclerView.Adapter<PlacesNearyouAdapter.viewHolder>{

    Context context;
    ArrayList<PopularPlacesModelClass> arrayList;

    public PlacesNearyouAdapter(Context context, ArrayList<PopularPlacesModelClass> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public PlacesNearyouAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.places_nearyou_listdata, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlacesNearyouAdapter.viewHolder holder, int position) {
        holder.percentOff.setText(arrayList.get(position).getCategory_percent_off());
        holder.mainlnr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView percentOff;
        RecyclerView mainlnr;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            percentOff = (TextView) itemView.findViewById(R.id.tvoff);
            mainlnr = itemView.findViewById(R.id.mainlnr);

        }
    }

}
