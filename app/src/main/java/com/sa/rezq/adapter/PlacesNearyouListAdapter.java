package com.sa.rezq.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sa.rezq.R;
import com.sa.rezq.global.GlobalFunctions;
import com.sa.rezq.global.GlobalVariables;
import com.sa.rezq.services.model.NearbyModel;
import com.sa.rezq.vendorlist.details.VendorListDetailsActivity;
import com.sa.rezq.vendorlist.details.VendorStoreListActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PlacesNearyouListAdapter extends RecyclerView.Adapter<PlacesNearyouListAdapter.viewHolder>{


    public static final String TAG = "PlacesNearyouListAdapter";

    private final List<NearbyModel> list;
    private final Activity activity;
    private GlobalVariables globalVariables;
    private GlobalFunctions globalFunctions;
    String minimumQuantity = "0";
    boolean isProductVertical = false;

    public PlacesNearyouListAdapter( Activity activity,List<NearbyModel> list) {
        this.activity = activity;
        this.list = list;

    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new viewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_places_recyclerview_data, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull PlacesNearyouListAdapter.viewHolder holder, int position) {
        final NearbyModel model = list.get(position);

        if (model.getName() != null) {
            holder.product_title.setText(model.getName());
        }
        if (model.getDistance() != null) {
            holder.product_distance.setText((model.getDistance()));
        }  if (model.getIcon() != null) {
            Picasso.with(activity).load(model.getIcon()).placeholder(R.drawable.rezq_logo).into(holder.product_image);
        }


        holder.click_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = VendorListDetailsActivity.newInstance( activity, model );
                activity.startActivity( intent );

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView product_image;
        TextView product_title;
        TextView product_distance;
        RelativeLayout click_item;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            product_image = (ImageView) itemView.findViewById(R.id.product_image);
            product_title = (TextView) itemView.findViewById(R.id.product_title);
            product_distance = (TextView) itemView.findViewById(R.id.tv_distance);
            click_item = itemView.findViewById(R.id.click_item);
        }
    }
}
