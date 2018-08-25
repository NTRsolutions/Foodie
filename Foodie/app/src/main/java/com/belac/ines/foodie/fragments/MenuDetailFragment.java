package com.belac.ines.foodie.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.belac.ines.foodie.R;
import com.belac.ines.foodie.api.APIService;
import com.belac.ines.foodie.api.RestaurantDetailsResponse;
import com.belac.ines.foodie.api.RestaurantMenusResponse;
import com.belac.ines.foodie.api.RetrofitClient;
import com.belac.ines.foodie.helper.FavoritesManager;
import com.belac.ines.foodie.helper.RestaurantMenusAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuDetailFragment extends Fragment implements RestaurantMenusAdapter.RestaurantMenusAdapterListener {

    private static final String TAG = MenuDetailFragment.class.getSimpleName();

    @BindView(R.id.name) TextView name;
    @BindView(R.id.telephone) TextView telephone;
    @BindView(R.id.location) TextView location;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.favorite_switch) Switch favoriteSwitch;
    @BindView(R.id.root) RelativeLayout root;

    int restaurantId;

    public MenuDetailFragment() {
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_detail, container, false);

        ButterKnife.bind(this, view);

        assert getArguments() != null;
        restaurantId = getArguments().getInt("id", -1);

        initRecyclerView();

        progressBar.setVisibility(View.VISIBLE);
        if (restaurantId != -1) {

            if (FavoritesManager.isFavorite(getContext(), restaurantId)) {
                favoriteSwitch.setChecked(true);
            } else {
                favoriteSwitch.setChecked(false);
            }

            getData();
        }

        return view;
    }

    private void getData() {
        RetrofitClient.instance()
                .create(APIService.class)
                .restaurantMenus(restaurantId)
                .enqueue(new Callback<RestaurantMenusResponse>() {
                    @Override public void onResponse(@NonNull Call<RestaurantMenusResponse> call,
                                                     @NonNull Response<RestaurantMenusResponse> response) {

                        progressBar.setVisibility(View.GONE);

                        setAdapter(response);
                    }

                    @Override public void onFailure(@NonNull Call<RestaurantMenusResponse> call, @NonNull Throwable t) {
                        progressBar.setVisibility(View.GONE);
                    }
                });

        RetrofitClient.instance()
                .create(APIService.class)
                .restaurantDetails(restaurantId)
                .enqueue(new Callback<RestaurantDetailsResponse>() {
                    @Override public void onResponse(@NonNull Call<RestaurantDetailsResponse> call,
                                                     @NonNull Response<RestaurantDetailsResponse> response) {

                        name.setText(response.body().getResults().get(0).getName());
                        telephone.setText(response.body().getResults().get(0).getTelephone());
                        location.setText(response.body().getResults().get(0).getLocation());
                    }

                    @Override
                    public void onFailure(@NonNull Call<RestaurantDetailsResponse> call, @NonNull Throwable t) {
                        Log.e(TAG, t.getLocalizedMessage());
                    }
                });
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
    }

    private void setAdapter(@NonNull Response<RestaurantMenusResponse> response) {
        recyclerView.setAdapter(new RestaurantMenusAdapter(response.body().getResults(), getContext(), this));
    }

    @OnCheckedChanged(R.id.favorite_switch) void onChecked(boolean checked) {
        if (restaurantId == -1) return;

        if (checked) {
            FavoritesManager.addFavorite(getContext(), restaurantId);
        } else {
            FavoritesManager.removeFavorite(getContext(), restaurantId);
        }
    }

    @Override
    public void onClickOrder(int id) {

    }
}
