package com.sa.rezq.adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.sa.rezq.R;
import com.sa.rezq.global.GlobalFunctions;
import com.sa.rezq.global.GlobalVariables;
import com.sa.rezq.offers.RedeemOfferActivity;
import com.sa.rezq.services.model.InsertRecentCouponModel;
import com.sa.rezq.services.model.OfferModel;
import com.sa.rezq.services.model.RecentCouponModel;
import com.sa.rezq.services.model.TrendingModel;
import com.sa.rezq.services.model.VendorStoreModel;
import com.sa.rezq.vendorlist.details.VendorListDetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CouponListAdapter extends RecyclerView.Adapter<CouponListAdapter.viewHolder> implements Filterable {


    public static final String TAG = "CouponListAdapter";


    private final List<RecentCouponModel> list;
    private List<RecentCouponModel> searchList;

    private final Activity activity;
    private GlobalVariables globalVariables;
    private GlobalFunctions globalFunctions;
    String minimumQuantity = "0";
    boolean isProductVertical = false;

    public CouponListAdapter(Activity activity, List<RecentCouponModel> list) {
        this.activity = activity;
        this.list = list;
        searchList = new ArrayList<>(list);

    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        return new viewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_details_dataview, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull CouponListAdapter.viewHolder holder, int position) {
        final RecentCouponModel model = list.get(position);


        if (GlobalFunctions.isNotNullValue(model.getVendor_name() )) {
            holder.product_title.setText(model.getVendor_name());
        }
        if (GlobalFunctions.isNotNullValue(model.getOffer_title() )) {
            holder.product_applicable.setText((model.getOffer_title()));
        } if (GlobalFunctions.isNotNullValue(model.getLogo() )) {
            Picasso.with(activity).load(model.getLogo()).placeholder(R.drawable.ic_lazy_load).into(holder.offer_image);
        }

        holder.relative_unlocked_offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = VendorListDetailsActivity.newInstance( activity, model);
                activity.startActivity( intent );
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        return searchFilter;
    }

    private Filter searchFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<RecentCouponModel> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(searchList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (RecentCouponModel item : searchList) {
                    if (item.getVendor_name().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView offer_image,first_product_image,second_product_image;
        TextView product_title,tv_offer_title,second_product_title;
        TextView product_applicable,tv_applicable,second_product_discount;
        TextView product_discount_title,first_product_discount_title,second_product_discount_title;
        RelativeLayout relative_unlocked_offer,single_imageview_main_rl,double_imageview_main_rl;
        CardView first_card_item_click,second_card_item_click;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            offer_image = (ImageView) itemView.findViewById(R.id.iv_offer_image);
            product_title = (TextView) itemView.findViewById(R.id.tv_first_offer_title);
            product_applicable = (TextView) itemView.findViewById(R.id.tv_first_offer_applicable);

            tv_offer_title = (TextView) itemView.findViewById(R.id.tv_offer_title);
            tv_applicable = (TextView) itemView.findViewById(R.id.tv_applicable);
            relative_unlocked_offer = (RelativeLayout) itemView.findViewById(R.id.relative_unlocked_offer);

        }
    }
}
