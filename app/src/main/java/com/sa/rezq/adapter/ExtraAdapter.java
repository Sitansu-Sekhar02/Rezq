package com.sa.rezq.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sa.rezq.Models.CategoryModelClass;
import com.sa.rezq.R;

import java.util.List;

public class ExtraAdapter extends RecyclerView.Adapter<ExtraAdapter.MyViewHolder> {

    private List<CategoryModelClass> moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tvFood);
        }
    }


    public ExtraAdapter(List<CategoryModelClass> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.all_category_view_data, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CategoryModelClass movie = moviesList.get(position);
        holder.title.setText(movie.getCategory_name());
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
