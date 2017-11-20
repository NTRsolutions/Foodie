package com.belac.ines.foodie.helper;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.belac.ines.foodie.R;

import java.util.List;

/**
 * Created by Ines on 20.11.2017..
 */

public class RestoranAdapter extends RecyclerView.Adapter<RestoranAdapter.MyViewHolder> {

    private List<Restoran> restoranList;

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView name, address;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.restoranName);
            address = (TextView) itemView.findViewById(R.id.restoranAddress);
        }
    }

    public RestoranAdapter(List<Restoran> restoranList) {
        this.restoranList = restoranList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restoran_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Restoran restoran = restoranList.get(position);
        holder.name.setText(restoran.getName());
        holder.address.setText(restoran.getAdress());
    }

    @Override
    public int getItemCount() {
        return restoranList.size();
    }


}
