package com.belac.ines.foodie.helper;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.belac.ines.foodie.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ines on 20.11.2017..
 */

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.MyViewHolder>
        implements Filterable{

    private List<Restoran> restoranList;
    private List<Restoran> filteredList;

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView name, address;
        public RelativeLayout viewBackground, viewForeground;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            address = (TextView) itemView.findViewById(R.id.address);
            viewBackground = (RelativeLayout) itemView.findViewById(R.id.view_background);
            viewForeground = (RelativeLayout) itemView.findViewById(R.id.view_foreground);
        }
    }

    public WishlistAdapter(List<Restoran> restoranList) {
        this.restoranList = restoranList;
        this.filteredList = restoranList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wishlist_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Restoran restoran = filteredList.get(position);
        holder.name.setText(restoran.getName());
        holder.address.setText(restoran.getAdress());
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if(charString.isEmpty()){
                    filteredList = restoranList;
                }else {
                    List<Restoran> filter = new ArrayList<>();
                    for (Restoran row : restoranList){
                        if(row.getName().toLowerCase().contains(charString.toLowerCase())
                                || row.getAdress().toLowerCase().contains(charString.toLowerCase())){
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
                filteredList = (ArrayList<Restoran>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public void removeItem(int position){
        filteredList.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Restoran restoran, int position){
        filteredList.add(position, restoran);
        notifyItemInserted(position);

    }

}
