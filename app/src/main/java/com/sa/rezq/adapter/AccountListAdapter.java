package com.sa.rezq.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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
import com.sa.rezq.services.ServerResponseInterface;
import com.sa.rezq.services.ServicesMethodsManager;
import com.sa.rezq.services.model.AccountModel;
import com.sa.rezq.services.model.CategoryModel;
import com.sa.rezq.services.model.InsertAccountModel;
import com.sa.rezq.services.model.RegisterModel;
import com.sa.rezq.services.model.StatusMainModel;
import com.sa.rezq.services.model.StatusProfileModel;
import com.sa.rezq.services.model.SubProfileModel;
import com.sa.rezq.vendorlist.details.VendorStoreListActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

import static com.sa.rezq.profile.ProfileMainActivity.context;

public class AccountListAdapter extends RecyclerView.Adapter<AccountListAdapter.ViewHolder> {

    public static final String TAG = "AccountListAdapter";

    private final List<AccountModel> list;
    private final Activity activity;
    private OpenInsertAccountInvoke openInsertAccountInvoke;


    List<String> profileImageList;
    List<Uri> uriProfileImageList;
    List<String> downloadProfileImageList;
    String imagePath = "";
    InsertAccountModel insertAccountModel;
    GlobalVariables globalVariables;
    GlobalFunctions globalFunctions;

    public AccountListAdapter(Activity activity, List<AccountModel> list,OpenInsertAccountInvoke openInsertAccountInvoke) {
        this.activity = activity;
        this.list = list;
        this.openInsertAccountInvoke = openInsertAccountInvoke;
        globalVariables = AppController.getInstance().getGlobalVariables();
        globalFunctions = AppController.getInstance().getGlobalFunctions();

    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.accountlist_recyclerview_data, parent, false));


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (holder instanceof AccountListAdapter.ViewHolder) {

            final AccountModel model = list.get(position);

            if (GlobalFunctions.isNotNullValue(model.getFirst_name())) {
                    holder.account_title.setText(model.getFirst_name());

            }

            if (GlobalFunctions.isNotNullValue(model.getProfile_image())) {
                    Picasso.with(activity).load(model.getProfile_image()).placeholder(R.drawable.layout_bg).into(holder.profile_image);

            }else if (GlobalFunctions.isNotNullValue(model.getId()) && model.getId().equalsIgnoreCase("0")){
                holder.profile_image.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_baseline_add_24));
            }


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (GlobalFunctions.isNotNullValue(model.getId()) && model.getId().equalsIgnoreCase("0")){

                        if (list.size()<= 4) {
                            openInsertAccountInvoke.OnItemClickListener(model);
                        }else{
                            Toasty.warning(activity,activity.getString(R.string.show_warning),Toast.LENGTH_LONG).show();
                        }
                      //  openInsertAccountInvoke.OnItemClickListener(model);


                    }else {
                         GlobalFunctions.setSharedPreferenceString(activity,GlobalVariables.SHARED_PREFERENCE_ACCOUNT_ID,model.getId());
                         Intent intent = new Intent(activity, MainActivity.class);
                         activity.startActivity(intent);
                    }

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView profile_image;
        TextView account_title;
        LinearLayout linear_profile, linear_add_account;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profile_image = itemView.findViewById(R.id.profile_image);
            account_title = itemView.findViewById(R.id.tv_profile_name);
            linear_profile = itemView.findViewById(R.id.Linear_UserView);
            linear_add_account = itemView.findViewById(R.id.Linear_AddView);

        }
    }
}
