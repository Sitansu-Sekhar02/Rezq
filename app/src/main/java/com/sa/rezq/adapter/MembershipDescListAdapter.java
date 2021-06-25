package com.sa.rezq.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sa.rezq.Activity.AppController;
import com.sa.rezq.R;
import com.sa.rezq.global.GlobalFunctions;
import com.sa.rezq.global.GlobalVariables;

import java.util.List;

public class MembershipDescListAdapter extends RecyclerView.Adapter<MembershipDescListAdapter.ViewHolder> {

    public static final String TAG = "MembershipDescListAdapter";

    private final List<String> list;
    private final Activity activity;

    GlobalVariables globalVariables;
    GlobalFunctions globalFunctions;

    public MembershipDescListAdapter(Activity activity, List<String> list) {
        this.activity = activity;
        this.list = list;
        globalVariables = AppController.getInstance().getGlobalVariables();
        globalFunctions = AppController.getInstance().getGlobalFunctions();

    }

    public void updatePlanList(List<String> updatelist) {
        list.clear();
        list.addAll(updatelist);
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.membership_list_recyclerview_data, parent, false));


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            final String title = list.get(position);

            if (GlobalFunctions.isNotNullValue(title)) {
                    holder.membership_title.setText(title);

            }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView membership_title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            membership_title = itemView.findViewById(R.id.tv_list_title);


        }
    }
}
