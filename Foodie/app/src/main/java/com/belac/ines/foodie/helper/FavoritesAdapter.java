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
import com.belac.ines.foodie.api.AllRestaurantsResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

	private List<AllRestaurantsResponse.Result> items;
	private Context context;
	private FavoritesListener listener;

	public FavoritesAdapter(List<AllRestaurantsResponse.Result> results, Context context, FavoritesListener listener) {
		this.context = context;
		this.items = results;
		this.listener = listener;
	}

	@NonNull @Override public FavoritesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return new FavoritesAdapter.ViewHolder(
				LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_favorites, parent, false));
	}

	@Override public void onBindViewHolder(@NonNull FavoritesAdapter.ViewHolder holder, int position) {
		final AllRestaurantsResponse.Result item = items.get(position);

		holder.name.setText(item.getName());
		holder.address.setText(item.getAddress());

		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View v) {
				listener.onClickItem(item);
			}
		});

		holder.remove.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View v) {
				listener.onClickRemove(item);
			}
		});
	}

	@Override public int getItemCount() {
		return items.size();
	}

	public void removeItem(AllRestaurantsResponse.Result item) {
		items.remove(item);
		notifyDataSetChanged();
	}

	public class ViewHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.restoranName) TextView name;
		@BindView(R.id.restoranAddress) TextView address;
		@BindView(R.id.remove) ImageView remove;

		public ViewHolder(View itemView) {
			super(itemView);

			ButterKnife.bind(this, itemView);
		}
	}

	public interface FavoritesListener {

		void onClickItem(AllRestaurantsResponse.Result item);

		void onClickRemove(AllRestaurantsResponse.Result item);
	}
}
