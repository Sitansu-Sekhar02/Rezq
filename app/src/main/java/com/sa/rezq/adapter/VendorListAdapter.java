package com.sa.rezq.adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sa.rezq.R;
import com.sa.rezq.global.GlobalFunctions;
import com.sa.rezq.global.GlobalVariables;
import com.sa.rezq.services.model.VendorModel;
import com.sa.rezq.services.model.VendorStoreModel;
import com.sa.rezq.vendorlist.details.VendorListDetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VendorListAdapter extends RecyclerView.Adapter<VendorListAdapter.viewHolder>{


    public static final String TAG = "TrendingListAdapter";

    private final List<VendorStoreModel> list;
    private final Activity activity;
    private GlobalVariables globalVariables;
    private GlobalFunctions globalFunctions;
    String minimumQuantity = "0";
    boolean isProductVertical = false;
    VendorModel vendorModel=null;

    public VendorListAdapter(Activity activity, VendorModel vendorModel ,List<VendorStoreModel> list) {
        this.activity = activity;
        this.list = list;
        this.vendorModel = vendorModel;

    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new viewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.vendor_store_listdata, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull VendorListAdapter.viewHolder holder, int position) {
        final VendorStoreModel model = list.get(position);

        if (GlobalFunctions.isNotNullValue(model.getName())) {
            holder.tv_vendor_name.setText(model.getName());
        }
        if (GlobalFunctions.isNotNullValue(model.getRating_count())) {
            holder.store_rating.setRating(Float.parseFloat((model.getRating_count())));
        }
        if (GlobalFunctions.isNotNullValue(model.getOffers())) {
            holder.offer_count.setText((model.getOffers()));
        }  if (GlobalFunctions.isNotNullValue(model.getFacilities() )) {
            holder.tv_facilities.setText((model.getFacilities()));
        }   if (GlobalFunctions.isNotNullValue(model.getLogo())) {

            Picasso.with(activity).load(model.getLogo()).placeholder(R.drawable.ic_lazy_load).into(holder.iv_vendor_logo);

        }

        holder.vendor_store_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (vendorModel==null){vendorModel=new VendorModel();}

                if (GlobalFunctions.isNotNullValue(model.getId())) {
                    vendorModel.setId(model.getId());
                }

                if (GlobalFunctions.isNotNullValue(model.getName())) {
                    vendorModel.setName(model.getName());
                }
                if (GlobalFunctions.isNotNullValue(model.getRating_count())) {
                    vendorModel.setRating_count(model.getRating_count());
                }

                if (GlobalFunctions.isNotNullValue(model.getAvg_rating())) {
                    vendorModel.setAvg_rating(model.getAvg_rating());
                }

                if (GlobalFunctions.isNotNullValue(model.getLogo())) {
                    vendorModel.setImage(model.getLogo());
                }

                Log.d("vendorModel00","=="+vendorModel);

                Intent intent = VendorListDetailsActivity.newInstance( activity, model ,vendorModel);
                activity.startActivity( intent );
            }
        });

    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView iv_vendor_logo;
        TextView tv_vendor_name,offer_count,product_discount,product_discount_title,tv_facilities;
        RatingBar store_rating;
        LinearLayout vendor_store_list,single_imageview_main_rl,double_imageview_main_rl;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            iv_vendor_logo = (ImageView) itemView.findViewById(R.id.iv_vendor_logo);
            tv_vendor_name = (TextView) itemView.findViewById(R.id.tv_vendor_name);
            tv_facilities = (TextView) itemView.findViewById(R.id.tv_facilities);
            offer_count = (TextView) itemView.findViewById(R.id.tv_offer_count);
            product_discount_title = (TextView) itemView.findViewById(R.id.product_discount_title);
            store_rating = (RatingBar) itemView.findViewById(R.id.tv_rating);

            vendor_store_list = itemView.findViewById(R.id.vendor_store_list);


        }
    }
}
