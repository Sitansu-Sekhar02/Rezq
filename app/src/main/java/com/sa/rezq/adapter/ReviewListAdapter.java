package com.sa.rezq.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.sa.rezq.R;
import com.sa.rezq.global.GlobalFunctions;
import com.sa.rezq.global.GlobalVariables;
import com.sa.rezq.services.model.NearbyModel;
import com.sa.rezq.services.model.ReviewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.viewHolder>{


    public static final String TAG = "ReviewListAdapter";

    private final List<ReviewModel> list;
    private final Activity activity;
    private GlobalVariables globalVariables;
    private GlobalFunctions globalFunctions;
    String minimumQuantity = "0";
    boolean isProductVertical = false;

    public ReviewListAdapter(Activity activity, List<ReviewModel> list) {
        this.activity = activity;
        this.list = list;

    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new viewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rating_and_review_activity, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ReviewListAdapter.viewHolder holder, int position) {
        final ReviewModel model = list.get(position);

        String
                fullName = model.getFirst_name() + " " + model.getLast_name();
                holder.user_name.setText( fullName );

        if (model.getRating() != null) {
            holder.tv_rating.setText((model.getRating()));
        }if (model.getCreated_on() != null) {
            holder.created_on.setText((model.getCreated_on()));
        }if (model.getComment() != null) {
            holder.tv_comment.setText((model.getComment()));
        }


        //get first letter of each String item
        String firstLetter= String.valueOf(model.getFirst_name().charAt(0));

        //String firstLetter = String.valueOf(personNames.get(position).charAt(0));

        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        // generate random color
        // int color = generator.getColor(personNames.get(position));
        int color = generator.getRandomColor();

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(firstLetter, color); // radius in px

        holder.product_image.setImageDrawable(drawable);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        CircleImageView product_image;
        TextView user_name;
        TextView tv_rating;
        TextView created_on;
        TextView tv_comment;
        RelativeLayout click_item;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            product_image = itemView.findViewById(R.id.profile_image);
            user_name = (TextView) itemView.findViewById(R.id.Tv_user_name);
            tv_rating = (TextView) itemView.findViewById(R.id.tv_rating);
            created_on = (TextView) itemView.findViewById(R.id.Tv_date_time);
            tv_comment = (TextView) itemView.findViewById(R.id.tv_comment);
            //click_item = itemView.findViewById(R.id.click_item);
        }
    }
}
