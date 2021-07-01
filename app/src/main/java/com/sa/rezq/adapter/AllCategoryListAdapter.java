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
import com.sa.rezq.vendorlist.details.VendorStoreListActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AllCategoryListAdapter extends RecyclerView.Adapter<AllCategoryListAdapter.ViewHolder> {

    public static final String TAG = "SeeAllCategoryListAdapter";

    private final List<SeeAllCategoryModel> list;
    private final Activity activity;
    private GlobalVariables globalVariables;
    private GlobalFunctions globalFunctions;
    String minimumQuantity = "0";
    boolean isProductVertical = false;

    public AllCategoryListAdapter(Activity activity, List<SeeAllCategoryModel> list) {
        this.activity = activity;
        this.list = list;

    }

   @NonNull
   @Override
   public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_allpopular_category_view_data, parent, false));

   }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final SeeAllCategoryModel model = list.get(position);

        if (GlobalFunctions.isNotNullValue(model.getName())) {
            holder.title_tv.setText(model.getName());
        } if(GlobalFunctions.isNotNullValue (model.getVendor_count())) {
            holder.vendor_count.setText(model.getVendor_count());
        }if (GlobalFunctions.isNotNullValue(model.getIcon() )) {
            Picasso.with(activity).load(model.getIcon()).placeholder(R.drawable.rezq_logo).into(holder.item_image);
        }


        holder.click_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = VendorStoreListActivity.newInstance( activity, model );
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
         TextView title_tv, vendor_count;
         RelativeLayout click_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_image = itemView.findViewById(R.id.category_image);
            vendor_count = itemView.findViewById(R.id.vendor_count);
            title_tv = itemView.findViewById(R.id.tv_category_title);
            click_item = itemView.findViewById(R.id.category_click_item);

        }
    }
}
