package com.belac.ines.foodie.helper;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;


import com.belac.ines.foodie.MainActivity;
import com.belac.ines.foodie.R;
import com.belac.ines.foodie.api.AllRestaurantsResponse;
import com.belac.ines.foodie.classes.Restoran;
import com.belac.ines.foodie.profile.ProfileRestoranFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ines on 20.11.2017..
 */

public class AllRestaurantsAdapter extends RecyclerView.Adapter<AllRestaurantsAdapter.MyViewHolder>{

    private List<AllRestaurantsResponse.Result> restoranList;
    private RestaurantsListener listener;
    Context context;

    public AllRestaurantsAdapter(List<AllRestaurantsResponse.Result> restoranList, Context context, RestaurantsListener listener) {
        this.restoranList = restoranList;
        this.listener = listener;
        this.context = context;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.restoranName) TextView name;
        @BindView(R.id.restoranAddress) TextView address;

        public MyViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    @NonNull @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_restoran, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final AllRestaurantsResponse.Result restoran = restoranList.get(position);
        holder.name.setText(restoran.getName());
        holder.address.setText(restoran.getAddress());
    }

    @Override
    public int getItemCount() {
        return restoranList.size();
    }

    public interface RestaurantsListener {

        void onClickItem( AllRestaurantsResponse.Result item);
    }
}
