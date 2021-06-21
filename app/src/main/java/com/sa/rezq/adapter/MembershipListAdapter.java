package com.sa.rezq.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    public static final String TAG = "AccountListAdapter";

    private final List<MembershipModel> list;
    private final Activity activity;


    List<String> profileImageList;
    List<Uri> uriProfileImageList;
    List<String> downloadProfileImageList;
    String imagePath = "";
    InsertAccountModel insertAccountModel;
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

        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.membership_list_recyclerview_data, parent, false));


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (holder instanceof MembershipListAdapter.ViewHolder) {

            final MembershipModel model = list.get(position);

            if (GlobalFunctions.isNotNullValue(model.getName())) {
                    holder.membership_title.setText(model.getName());

            }

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
