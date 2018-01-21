package com.belac.ines.foodie.helper;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.belac.ines.foodie.MainActivity;
import com.belac.ines.foodie.R;
import com.belac.ines.foodie.classes.Order;
import com.belac.ines.foodie.classes.Restoran;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Korisnik on 21.01.2018..
 */

public class ArchiveAdapter extends RecyclerView.Adapter<ArchiveAdapter.MyViewHolder>
        implements Filterable {

    private List<Order> ordersList;
    private List<Order> filteredList;

    //prima dohvaćenu listu narudzbi iz DB
    public ArchiveAdapter(List<Order> ordersList) {
        this.ordersList = ordersList;
        this.filteredList = ordersList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView userName, restoranName, orderDate, orderPrice;

        public MyViewHolder(View itemView) {
            super(itemView);
            userName = (TextView) itemView.findViewById(R.id.userName);
            restoranName = (TextView) itemView.findViewById(R.id.restoranName);
            orderDate = (TextView) itemView.findViewById(R.id.orderDate);
            orderPrice = (TextView) itemView.findViewById(R.id.orderPrice);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopup(v);

                }
            });
        }
    }
    @Override
    public ArchiveAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_archive, parent, false);
        return new ArchiveAdapter.MyViewHolder(itemView);
    }

    //punjenje view holdera ("kartica")
    @Override
    public void onBindViewHolder(ArchiveAdapter.MyViewHolder holder, int position) {
        Order order = filteredList.get(position);
        holder.userName.setText(order.getUserName());
        holder.restoranName.setText(order.getRestoranName());
        holder.orderDate.setText(order.getDatum());
        holder.orderPrice.setText(order.getPrice());
    }

        @Override
        public int getItemCount() { return filteredList.size(); }

        //search filtriranje
        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    String charString = charSequence.toString();
                    if(charString.isEmpty()){
                        filteredList = ordersList;
                    }else {
                        List<Order> filter = new ArrayList<>();
                        for (Order row : ordersList){
                            if(row.getUserName().toLowerCase().contains(charString.toLowerCase())
                                    || row.getRestoranName().toLowerCase().contains(charString.toLowerCase())
                                    || row.getDatum().toLowerCase().contains(charString.toLowerCase())){
                                filter.add(row);
                            }
                        }
                        filteredList = filter;
                    }

                    FilterResults filterResults = new FilterResults();
                    filterResults.values = filteredList;
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    filteredList = (ArrayList<Order>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
        }

    public void showPopup(View anchorView) {



    }
}
