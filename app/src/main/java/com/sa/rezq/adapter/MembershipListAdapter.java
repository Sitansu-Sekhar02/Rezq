package com.sa.rezq.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sa.rezq.Activity.AppController;
import com.sa.rezq.Activity.MainActivity;
import com.sa.rezq.R;
import com.sa.rezq.adapter.interfaces.OpenInsertAccountInvoke;
import com.sa.rezq.global.GlobalFunctions;
import com.sa.rezq.global.GlobalVariables;
import com.sa.rezq.services.model.AccountModel;
import com.sa.rezq.services.model.InsertAccountModel;
import com.sa.rezq.services.model.MembershipModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

public class MembershipListAdapter extends RecyclerView.Adapter<MembershipListAdapter.ViewHolder> {

    public static final String TAG = "MembershipListAdapter";

    private final List<MembershipModel> list;
    private final Activity activity;

    GlobalVariables globalVariables;
    GlobalFunctions globalFunctions;

    public MembershipListAdapter(Activity activity, List<MembershipModel> list) {
        this.activity = activity;
        this.list = list;
        globalVariables = AppController.getInstance().getGlobalVariables();
        globalFunctions = AppController.getInstance().getGlobalFunctions();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.membership_banner_dataview, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            final MembershipModel model = list.get(position);

           /* if (GlobalFunctions.isNotNullValue(model.getName())) {
                    holder.membership_title.setText(model.getName());

            }*/
            if (GlobalFunctions.isNotNullValue(model.getImage())) {
                Picasso.with(activity).load(model.getImage()).placeholder(R.drawable.ic_lazy_load).into(holder.iv_banner_image);

            }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //TextView membership_title;
        ImageView iv_banner_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_banner_image = itemView.findViewById(R.id.iv_banner_image);


        }
    }
}
