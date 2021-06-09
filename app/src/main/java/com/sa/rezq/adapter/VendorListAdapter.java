package com.sa.rezq.adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.sa.rezq.R;
import com.sa.rezq.global.GlobalFunctions;
import com.sa.rezq.global.GlobalVariables;
import com.sa.rezq.services.model.TrendingModel;
import com.sa.rezq.services.model.VendorModel;
import com.sa.rezq.services.model.VendorStoreListModel;
import com.sa.rezq.vendorlist.details.VendorListDetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VendorListAdapter extends RecyclerView.Adapter<VendorListAdapter.viewHolder>{


    public static final String TAG = "TrendingListAdapter";

    private final List<VendorStoreListModel> list;
    private final Activity activity;
    private GlobalVariables globalVariables;
    private GlobalFunctions globalFunctions;
    String minimumQuantity = "0";
    boolean isProductVertical = false;

    public VendorListAdapter(Activity activity, List<VendorStoreListModel> list) {
        this.activity = activity;
        this.list = list;

    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        return new viewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.vendor_store_list_recyclerview_data, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull VendorListAdapter.viewHolder holder, int position) {
        final VendorStoreListModel model = list.get(position);

        if (model.getName() != null) {
            holder.product_title.setText(model.getName());
        }
        if (model.getDistance() != null) {
            holder.product_discount.setText((model.getDistance()));
        }  if (model.getLogo() != null) {
            Picasso.with(activity).load(model.getLogo()).placeholder(R.drawable.rezq_logo).into(holder.product_image);
        }


        holder.cardItem_main_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  Intent intent = VendorListDetailsActivity.newInstance( activity, model );
                //activity.startActivity( intent );
            }
        });



    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView product_image;
        TextView product_title,title_description,product_discount,product_discount_title;
        RelativeLayout click_item,single_imageview_main_rl,double_imageview_main_rl;
        CardView cardItem_main_item;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            product_image = (ImageView) itemView.findViewById(R.id.product_image);
            product_title = (TextView) itemView.findViewById(R.id.product_title);
            product_discount = (TextView) itemView.findViewById(R.id.product_discount);
            product_discount_title = (TextView) itemView.findViewById(R.id.product_discount_title);


            cardItem_main_item = itemView.findViewById(R.id.cardItem_main_item);


        }
    }
}
