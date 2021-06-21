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
import com.sa.rezq.services.model.SeeAllCategoryModel;
import com.sa.rezq.services.model.WishListModel;
import com.sa.rezq.services.model.WishModel;
import com.sa.rezq.vendorlist.details.VendorListDetailsActivity;
import com.sa.rezq.vendorlist.details.VendorStoreListActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.ViewHolder> {

    public static final String TAG = "WishListAdapter";

    private final List<WishModel> list;
    private final Activity activity;
    private GlobalVariables globalVariables;
    private GlobalFunctions globalFunctions;


    public WishListAdapter(Activity activity, List<WishModel> list) {
        this.activity = activity;
        this.list = list;

    }

   @NonNull
   @Override
   public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_wishlist_single_data, parent, false));

   }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final WishModel model = list.get(position);

        if (model.getName() != null) {
            holder.product_title_tv.setText(model.getName());
        } if (model.getFacilities() != null) {
            holder.product_sub_title.setText(model.getFacilities());
        }if (model.getLogo() != null) {
            Picasso.with(activity).load(model.getLogo()).placeholder(R.drawable.rezq_logo).into(holder.item_image);
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

    public class ViewHolder extends RecyclerView.ViewHolder {
         ImageView item_image, item_viv;
         TextView product_title_tv, product_sub_title;
         RelativeLayout click_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_image = itemView.findViewById(R.id.product_image);
            product_title_tv = itemView.findViewById(R.id.product_title);
            product_sub_title = itemView.findViewById(R.id.tv_product_sub_title);
            click_item = itemView.findViewById(R.id.fav_click_item);

        }
    }
}
