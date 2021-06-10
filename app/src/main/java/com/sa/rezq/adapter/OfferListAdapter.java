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
import com.sa.rezq.services.model.OfferModel;
import com.sa.rezq.services.model.TrendingModel;
import com.sa.rezq.vendorlist.details.VendorListDetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OfferListAdapter extends RecyclerView.Adapter<OfferListAdapter.viewHolder>{


    public static final String TAG = "OfferListAdapter";


    private final List<OfferModel> list;
    private final Activity activity;
    private GlobalVariables globalVariables;
    private GlobalFunctions globalFunctions;
    String minimumQuantity = "0";
    boolean isProductVertical = false;

    public OfferListAdapter(Activity activity, List<OfferModel> list) {
        this.activity = activity;
        this.list = list;

    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new viewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_details_dataview, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull OfferListAdapter.viewHolder holder, int position) {
        final OfferModel model = list.get(position);


            if (model.getTitle() != null) {
                holder.product_title.setText(model.getTitle());
            }
            if (model.getOffer_applicable() != null) {
                holder.product_applicable.setText((model.getOffer_applicable()));
            }   if (model.getOffer_image() != null) {
               // Picasso.with(activity).load(model.getOffer_image()).placeholder(R.drawable.rezq_logo).into(holder.offer_image);
            }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView offer_image,first_product_image,second_product_image;
        TextView product_title,tv_offer_title,second_product_title;
        TextView product_applicable,tv_applicable,second_product_discount;
        TextView product_discount_title,first_product_discount_title,second_product_discount_title;
        RelativeLayout click_item,single_imageview_main_rl,double_imageview_main_rl;
        CardView first_card_item_click,second_card_item_click;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            offer_image = (ImageView) itemView.findViewById(R.id.iv_offer_image);
            product_title = (TextView) itemView.findViewById(R.id.tv_first_offer_title);
            product_applicable = (TextView) itemView.findViewById(R.id.tv_first_offer_applicable);

            tv_offer_title = (TextView) itemView.findViewById(R.id.tv_offer_title);

            tv_applicable = (TextView) itemView.findViewById(R.id.tv_applicable);

        }
    }
}
