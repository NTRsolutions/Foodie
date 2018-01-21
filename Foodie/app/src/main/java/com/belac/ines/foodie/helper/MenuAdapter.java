package com.belac.ines.foodie.helper;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.belac.ines.foodie.MainActivity;
import com.belac.ines.foodie.R;
import com.belac.ines.foodie.classes.Restoran;
import com.belac.ines.foodie.profile.ProfileRestoranFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ines on 17.1.2018..
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyViewHolder>
        implements Filterable {

    private List<Restoran> menuList;
    private List<Restoran> filteredList;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView name, firstMeal, secondMeal, thirdMeal;
        public RelativeLayout viewBackground, viewForeground;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.restoranName);
            firstMeal = (TextView) itemView.findViewById(R.id.firstMeal);
            secondMeal = (TextView) itemView.findViewById(R.id.secondMeal);
            thirdMeal = (TextView) itemView.findViewById(R.id.thirdMeal);
            viewBackground = (RelativeLayout) itemView.findViewById(R.id.view_background);
            viewForeground = (RelativeLayout) itemView.findViewById(R.id.view_foreground);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = filteredList.get(getPosition()).getID();
                    Fragment fragment = new ProfileRestoranFragment();
                    Bundle args = new Bundle();
                    args.putInt("id", id);
                    fragment.setArguments(args);
                    FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();

                }
            });
        }
    }

    public MenuAdapter(List<Restoran> restoranList, Context context) {
        this.menuList = restoranList;
        this.filteredList = restoranList;
        this.context = context;
    }

    @Override
    public MenuAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_menu, parent, false);
        return new MenuAdapter.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MenuAdapter.MyViewHolder holder, int position) {
        Restoran restoran = filteredList.get(position);
        holder.name.setText(restoran.getName());
        holder.firstMeal.setText("Appetizer: " + restoran.getMeni().getFirstMeal());
        holder.secondMeal.setText("Main course: " + restoran.getMeni().getSecondMeal());
        holder.thirdMeal.setText("Dessert: " + restoran.getMeni().getThirdMeal());
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
                    List<Restoran> filter = new ArrayList<>();
                    for (Restoran row : menuList){
                        if(row.getName().toLowerCase().contains(charString.toLowerCase())
                                || row.getMeni().getFirstMeal().toLowerCase().contains(charString.toLowerCase())
                                || row.getMeni().getSecondMeal().toLowerCase().contains(charString.toLowerCase())
                                || row.getMeni().getThirdMeal().toLowerCase().contains(charString.toLowerCase())){
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
