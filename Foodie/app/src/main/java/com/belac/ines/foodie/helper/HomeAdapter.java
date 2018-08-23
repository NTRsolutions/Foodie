package com.belac.ines.foodie.helper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.belac.ines.foodie.R;
import com.belac.ines.foodie.api.RestaurantResponse;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder>{

    private List<RestaurantResponse> elements;
    private Context context;
    private HomeListener listener;

    public HomeAdapter(List<RestaurantResponse> elements, Context context, HomeListener listener) {
        this.elements = elements;
        this.context = context;
        this.listener = listener;
    }

    @Override public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeAdapter.ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item_view, parent, false));
    }

    @Override public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final RestaurantResponse item = elements.get(position);

        holder.name.setText(item.getName());
        holder.address.setText(item.getAddress());

        holder.location.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onClickLocation(
                        new LatLng(Double.parseDouble(item.getLatitude()), Double.parseDouble(item.getLongitude())));
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onClickItem(item);
            }
        });
    }

    @Override public int getItemCount() {
        return elements.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.restoranName)
        TextView name;
        @BindView(R.id.restoranAddress) TextView address;
        @BindView(R.id.location) ImageView location;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface HomeListener {

        void onClickLocation(LatLng latLng);
        void onClickItem(RestaurantResponse item);
    }
}
