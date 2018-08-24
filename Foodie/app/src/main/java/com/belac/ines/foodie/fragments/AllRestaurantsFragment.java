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
import android.widget.Toast;

import com.belac.ines.foodie.R;
import com.belac.ines.foodie.api.APIService;
import com.belac.ines.foodie.api.AllRestaurantsResponse;
import com.belac.ines.foodie.api.RetrofitClient;
import com.belac.ines.foodie.helper.AllRestaurantsAdapter;
import com.belac.ines.foodie.profile.ProfileRestoranFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllRestaurantsFragment extends Fragment implements AllRestaurantsAdapter.RestaurantsListener {

    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.progress_bar) ProgressBar progressBar;

    private List<AllRestaurantsResponse.Result> restaurantList = new ArrayList<>();
    private AllRestaurantsAdapter restaurantsAdapter;

    public AllRestaurantsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lists, container, false);

        ButterKnife.bind(this, view);

        restaurantsAdapter = new AllRestaurantsAdapter(restaurantList, getContext(), this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(restaurantsAdapter);

        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        RetrofitClient.instance()
                .create(APIService.class)
                .restaurants()
                .enqueue(new Callback<AllRestaurantsResponse>() {
                    @Override
                    public void onResponse(Call<AllRestaurantsResponse> call, Response<AllRestaurantsResponse> response) {
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);

                        if (!response.body().getError()) {
                            restaurantList.clear();
                            restaurantList.addAll(response.body().getResults());
                            restaurantsAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<AllRestaurantsResponse> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
        });

        return view;
    }

    @OnTextChanged(R.id.search) void onTextChange(CharSequence query) {
        restaurantsAdapter.getFilter().filter(query);
    }

    @Override
    public void onClickRestaurant(AllRestaurantsResponse.Result item) {
        Fragment fragment = new ProfileRestoranFragment();
        Bundle args = new Bundle();
        args.putInt("id", Integer.valueOf(item.getId()));
        fragment.setArguments(args);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
    }
}
