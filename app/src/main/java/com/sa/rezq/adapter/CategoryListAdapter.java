package com.sa.rezq.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sa.rezq.Activity.AppController;
import com.sa.rezq.R;
import com.sa.rezq.global.GlobalFunctions;
import com.sa.rezq.global.GlobalVariables;
import com.sa.rezq.home.ProductDetailsActivity;
import com.sa.rezq.services.model.CategoryModel;

import com.sa.rezq.vendorlist.details.VendorStoreListActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> {

    public static final String TAG = "ProductListAdapter";

    private final List<CategoryModel> list;
    private final Activity activity;
    private GlobalVariables globalVariables;
    private GlobalFunctions globalFunctions;
    String minimumQuantity = "0";
    boolean isProductVertical = false;

    public CategoryListAdapter(Activity activity, List<CategoryModel> list) {
        this.activity = activity;
        this.list = list;

    }

   @NonNull
   @Override
   public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_category_recyclerview_data, parent, false));

   }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final CategoryModel model = list.get(position);

        if (model.getName() != null) {
            holder.title_tv.setText(model.getName());
        }
        if (model.getCount() != null) {
            holder.product_count.setText((model.getCount()));
        }  if (model.getIcon() != null) {
            Picasso.with(activity).load(model.getIcon()).placeholder(R.drawable.rezq_logo).into(holder.item_iv);
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
         ImageView item_iv, item_viv;
         TextView title_tv, product_count;
         LinearLayout click_item,price_vll,product_list_grid_ll, product_list_vertical_ll;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_iv = itemView.findViewById(R.id.categoryImage);
            product_count = itemView.findViewById(R.id.tv_total__places);
            title_tv = itemView.findViewById(R.id.TvCategoryName);
            click_item = itemView.findViewById(R.id.click_item);

        }
    }
}
