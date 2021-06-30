package com.sa.rezq.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sa.rezq.R;
import com.sa.rezq.adapter.interfaces.ClickListener;
import com.sa.rezq.global.GlobalFunctions;
import com.sa.rezq.global.GlobalVariables;
import com.sa.rezq.services.model.WishlistCategoryModel;

import java.util.List;

public class WishListCategoryAdapter extends RecyclerView.Adapter<WishListCategoryAdapter.ViewHolder> {

    public static final String TAG = "WishListAdapter";

    private final List<WishlistCategoryModel> list;
    private final Activity activity;
    private GlobalVariables globalVariables;
    private GlobalFunctions globalFunctions;
    private int selectedPosition=-1;
    private ClickListener clicklistener;



    public WishListCategoryAdapter(Activity activity, List<WishlistCategoryModel> list, ClickListener clicklistener) {
        this.activity = activity;
        this.list = list;
        this.clicklistener = clicklistener;
    }

   @NonNull
   @Override
   public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.favourite_list_single_data, parent, false));

   }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final WishlistCategoryModel model = list.get(position);

        if (model.getName() != null) {
            holder.tv_fav_title.setText(model.getName());
        }

        if (selectedPosition == position) {
            holder.line_view.setVisibility(View.VISIBLE);
            holder.tv_fav_title.setTextColor(activity.getResources().getColor(R.color.black));
        } else {
            holder.line_view.setVisibility(View.GONE);
            holder.tv_fav_title.setTextColor(activity.getResources().getColor(R.color.app_grey_text));
        }

        if (selectedPosition==-1){
            selectedPosition=0;
            holder.line_view.setVisibility(View.VISIBLE);
            holder.tv_fav_title.setTextColor(activity.getResources().getColor(R.color.black));
            WishlistCategoryModel tmpModel = list.get(selectedPosition);
            clicklistener.OnItemClickListener(tmpModel);

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition=position;
                notifyDataSetChanged();
                clicklistener.OnItemClickListener(model);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
         TextView tv_fav_title;
         View line_view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_fav_title = itemView.findViewById(R.id.tv_fav_title);
            line_view = itemView.findViewById(R.id.line_view);


        }
    }
}
