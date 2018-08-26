package com.belac.ines.foodie.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.belac.ines.foodie.R;
import com.belac.ines.foodie.api.APIService;
import com.belac.ines.foodie.api.AllRestaurantsResponse;
import com.belac.ines.foodie.api.RetrofitClient;
import com.belac.ines.foodie.helper.FavoritesAdapter;
import com.belac.ines.foodie.helper.FavoritesManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoritesFragment extends Fragment implements FavoritesAdapter.FavoritesListener {

	@BindView(R.id.recycler_view) RecyclerView recyclerView;
	@BindView(R.id.progress_bar) ProgressBar progressBar;

	public FavoritesFragment() {
	}

	@Override public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_favorites, container, false);

		ButterKnife.bind(this, view);
		initRecyclerView();

		RetrofitClient.instance().create(APIService.class).restaurants().enqueue(new Callback<AllRestaurantsResponse>() {
			@Override
			public void onResponse( @NonNull Call<AllRestaurantsResponse> call, @NonNull Response<AllRestaurantsResponse> response) {
				progressBar.setVisibility(View.GONE);
				showData(response);
			}

			@Override
			public void onFailure(@NonNull Call<AllRestaurantsResponse> call, Throwable t) {
				progressBar.setVisibility(View.GONE);
			}
		});

		return view;
	}

	private void initRecyclerView() {
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		recyclerView.setItemAnimator(new DefaultItemAnimator());
		recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
	}

	private void showData(Response<AllRestaurantsResponse> response) {
		List<AllRestaurantsResponse.Result> filtered = new ArrayList<>();

		for (AllRestaurantsResponse.Result result : response.body().getResults()) {
			if (FavoritesManager.isFavorite(getContext(), Integer.valueOf(result.getId()))) {
				filtered.add(result);
			}
		}

		recyclerView.setAdapter(new FavoritesAdapter(filtered, getContext(), this));
	}

	@Override public void onClickItem(AllRestaurantsResponse.Result item) {
		Fragment fragment = new MenuDetailFragment();
		Bundle args = new Bundle();
		args.putInt("id", Integer.valueOf(item.getId()));
		fragment.setArguments(args);
		FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
	}

	@Override public void onClickRemove(AllRestaurantsResponse.Result item) {
		FavoritesManager.removeFavorite(getContext(), Integer.valueOf(item.getId()));
		((FavoritesAdapter) recyclerView.getAdapter()).removeItem(item);
	}
}
