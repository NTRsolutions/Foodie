package com.belac.ines.foodie.helper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.belac.ines.foodie.R;
import com.belac.ines.foodie.api.RestaurantMenusResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestaurantMenusAdapter extends RecyclerView.Adapter<RestaurantMenusAdapter.ViewHolder> {

	private List<RestaurantMenusResponse.Result> items;
	private Context context;
	private RestaurantMenusAdapterListener listener;
	private boolean user = true;

	public RestaurantMenusAdapter(List<RestaurantMenusResponse.Result> items, Context context,
                                  RestaurantMenusAdapterListener listener) {
		this.items = items;
		this.context = context;
		this.listener = listener;
	}

	@Override public RestaurantMenusAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View itemView =
				LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_menu_details_item, parent, false);
		if (SessionManager.getType(parent.getContext()) == 2) {
			user = false;
		}
		return new RestaurantMenusAdapter.ViewHolder(itemView);
	}

	@Override public void onBindViewHolder(@NonNull RestaurantMenusAdapter.ViewHolder holder, int position) {
		final RestaurantMenusResponse.Result item = items.get(position);

		holder.firstMeal.setText(String.format("Appetizer: %s", item.getFirstMeal()));
		holder.secondMeal.setText(String.format("Main course: %s", item.getSecondMeal()));
		holder.thirdMeal.setText(String.format("Dessert: %s", item.getThirdMeal()));

		if(!user){
			holder.order.setVisibility(View.GONE);
		}

		holder.order.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View v) {
				listener.onClickOrder(Integer.valueOf(item.getId()));
			}
		});
	}

	@Override public int getItemCount() {
		return items.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.firstMeal) public TextView firstMeal;
		@BindView(R.id.secondMeal) public TextView secondMeal;
		@BindView(R.id.thirdMeal) public TextView thirdMeal;
		@BindView(R.id.order) public Button order;

		public ViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}

	public interface RestaurantMenusAdapterListener {

		void onClickOrder(int id);
	}
}
