package com.belac.ines.foodie.profile;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.belac.ines.foodie.R;
import com.belac.ines.foodie.api.RestaurantMenusResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileRestoranAdapter extends RecyclerView.Adapter<ProfileRestoranAdapter.ViewHolder> {

    private List<RestaurantMenusResponse.Result> items;
    private Context context;
    private RestaurantProfileAdapterListener listener;

    public ProfileRestoranAdapter(List<RestaurantMenusResponse.Result> items, Context context, RestaurantProfileAdapterListener listener) {
        this.items = items;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public ProfileRestoranAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_profile_menu_item, parent, false);
        return new ProfileRestoranAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileRestoranAdapter.ViewHolder holder, int position) {
        final RestaurantMenusResponse.Result item = items.get(position);

        holder.firstMeal.setText(String.format("Appetizer: %s", item.getFirstMeal()));
        holder.secondMeal.setText(String.format("Main course: %s", item.getSecondMeal()));
        holder.thirdMeal.setText(String.format("Dessert: %s", item.getThirdMeal()));

        holder.delete_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {listener.onClickDelete(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void removeItem(RestaurantMenusResponse.Result item) {
        items.remove(item);
        notifyDataSetChanged();
    }

    public interface RestaurantProfileAdapterListener {

        void onClickDelete(RestaurantMenusResponse.Result item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.firstMeal)
        public TextView firstMeal;
        @BindView(R.id.secondMeal)
        public TextView secondMeal;
        @BindView(R.id.thirdMeal)
        public TextView thirdMeal;
        @BindView(R.id.delete_icon)
        public ImageView delete_icon;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}