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
import com.sa.rezq.services.model.NearbyModel;
import com.sa.rezq.services.model.TrendingModel;
import com.sa.rezq.vendorlist.details.VendorListDetailsActivity;
import com.sa.rezq.vendorlist.details.VendorStoreListActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TrendingListAdapter extends RecyclerView.Adapter<TrendingListAdapter.viewHolder>{


    public static final String TAG = "TrendingListAdapter";



    private final List<TrendingModel> list;
    private final Activity activity;
    private GlobalVariables globalVariables;
    private GlobalFunctions globalFunctions;
    String minimumQuantity = "0";
    boolean isProductVertical = false;

    public TrendingListAdapter(Activity activity, List<TrendingModel> list) {
        this.activity = activity;
        this.list = list;

    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        return new viewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.trending_recyclerview_data, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull TrendingListAdapter.viewHolder holder, int position) {
        final TrendingModel model = list.get(position);


        if (position%2==0){
            holder.single_imageview_main_rl.setVisibility(View.VISIBLE);
            holder.double_imageview_main_rl.setVisibility(View.GONE);
        }else {

            holder.single_imageview_main_rl.setVisibility(View.GONE);
            holder.double_imageview_main_rl.setVisibility(View.VISIBLE);
        }


        /*if (model.getVendor_name() != null) {
            holder.product_title.setText(model.getVendor_name());
        }
        if (model.getOffer_discount() != null) {
            holder.product_discount.setText((model.getOffer_discount()));
        }  if (model.getOffer_title() != null) {
            holder.product_discount_title.setText((model.getOffer_title()));
        }*/ if (model.getIcon() != null) {
            Picasso.with(activity).load(model.getIcon()).placeholder(R.drawable.rezq_logo).into(holder.product_image);

        }

       /* if (model.getVendor_name() != null) {
            holder.first_product_title.setText(model.getVendor_name());
        }
        if (model.getOffer_title() != null) {
            holder.first_product_discount_title.setText((model.getOffer_title()));
        }
        if (model.getOffer_discount() != null) {
            holder.first_product_discount.setText((model.getOffer_discount()));
        }*/  if (model.getIcon() != null) {
            Picasso.with(activity).load(model.getIcon()).placeholder(R.drawable.rezq_logo).into(holder.first_product_image);
        }


       /* if (model.getVendor_name() != null) {
            holder.second_product_title.setText(model.getVendor_name());
        }

        if (model.getOffer_discount() != null) {
            holder.second_product_discount.setText((model.getOffer_discount()));
        }  if (model.getOffer_title() != null) {
            holder.second_product_discount_title.setText((model.getOffer_title()));
        }*/if (model.getIcon() != null) {
            Picasso.with(activity).load(model.getIcon()).placeholder(R.drawable.rezq_logo).into(holder.second_product_image);

        }

        holder.single_imageview_main_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(model.getCount().equalsIgnoreCase("1")){
                    Intent intent = VendorListDetailsActivity.newInstance( activity, model );
                    activity.startActivity( intent );
                }else {
                    Intent intent = VendorStoreListActivity.newInstance( activity, model );
                    activity.startActivity( intent );
                }
            }
        });
        holder.first_card_item_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(model.getCount().equalsIgnoreCase("1")){
                    Intent intent = VendorListDetailsActivity.newInstance( activity, model );
                    activity.startActivity( intent );
                }else {
                    Intent intent = VendorStoreListActivity.newInstance( activity, model );
                    activity.startActivity( intent );
                }

            }
        });
        holder.second_card_item_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(model.getCount().equalsIgnoreCase("1")){
                    Intent intent = VendorListDetailsActivity.newInstance( activity, model );
                    activity.startActivity( intent );
                }else {
                    Intent intent = VendorStoreListActivity.newInstance( activity, model );
                    activity.startActivity( intent );
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView product_image,first_product_image,second_product_image;
        TextView product_title,first_product_title,second_product_title;
        TextView product_discount,first_product_discount,second_product_discount;
        TextView product_discount_title,first_product_discount_title,second_product_discount_title;
        RelativeLayout click_item,single_imageview_main_rl,double_imageview_main_rl;
        RelativeLayout first_card_item_click,second_card_item_click;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            product_image = (ImageView) itemView.findViewById(R.id.product_image);
            product_title = (TextView) itemView.findViewById(R.id.product_title);
            product_discount = (TextView) itemView.findViewById(R.id.product_discount);
            first_product_image = (ImageView) itemView.findViewById(R.id.first_product_image);
            second_product_image = (ImageView) itemView.findViewById(R.id.second_product_image);

            first_product_title = (TextView) itemView.findViewById(R.id.first_product_title);
            second_product_title = (TextView) itemView.findViewById(R.id.second_product_title);

            product_discount_title = (TextView) itemView.findViewById(R.id.product_discount_title);
            first_product_discount_title = (TextView) itemView.findViewById(R.id.first_product_discount_title);
            second_product_discount_title = (TextView) itemView.findViewById(R.id.second_product_discount_title);

            first_product_discount = (TextView) itemView.findViewById(R.id.first_product_discount);
            second_product_discount = (TextView) itemView.findViewById(R.id.second_product_discount);

            single_imageview_main_rl = itemView.findViewById(R.id.single_imageview_main_rl);
            double_imageview_main_rl = itemView.findViewById(R.id.double_imageview_main_rl);

            first_card_item_click = itemView.findViewById(R.id.first_card_item);
            second_card_item_click = itemView.findViewById(R.id.second_card_item);

        }
    }
}
