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
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.belac.ines.foodie.MainActivity;
import com.belac.ines.foodie.R;
import com.belac.ines.foodie.api.MenuResponse;
import com.belac.ines.foodie.classes.Restoran;
import com.belac.ines.foodie.profile.ProfileRestoranFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ines on 17.1.2018..
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyViewHolder>
        implements Filterable {

    private List<MenuResponse.Result> menuList;
    private List<MenuResponse.Result> filteredList;
    Context context;
    private MenuListener listener;

    public MenuAdapter(List<MenuResponse.Result> restoranList, Context context, MenuListener listener) {
        this.menuList = restoranList;
        this.filteredList = restoranList;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public MenuAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_menu, parent, false);
        return new MenuAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuAdapter.MyViewHolder holder, int position) {
        final MenuResponse.Result restoran = filteredList.get(position);
        holder.name.setText(restoran.getName());
        holder.firstMeal.setText("Appetizer: " + restoran.getFirst());
        holder.secondMeal.setText("Main course: " + restoran.getSecond());
        holder.thirdMeal.setText("Dessert: " + restoran.getThird());
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.restoranName) public TextView name;
        @BindView(R.id.firstMeal) public TextView firstMeal;
        @BindView(R.id.secondMeal) public TextView secondMeal;
        @BindView(R.id.thirdMeal) public TextView thirdMeal;

        public MyViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public int getItemCount() { return filteredList.size(); }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if(charString.isEmpty()){
                    filteredList = menuList;
                }else {
                    List<MenuResponse.Result> filter = new ArrayList<>();
                    for (MenuResponse.Result row : menuList){
                        if(row.getName().toLowerCase().contains(charString.toLowerCase())
                                || row.getFirst().toLowerCase().contains(charString.toLowerCase())
                                || row.getSecond().toLowerCase().contains(charString.toLowerCase())
                                || row.getThird().toLowerCase().contains(charString.toLowerCase())){
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
                filteredList = (ArrayList<MenuResponse.Result>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface MenuListener {

        void onClickMenu(MenuResponse.Result item);

    }
}
