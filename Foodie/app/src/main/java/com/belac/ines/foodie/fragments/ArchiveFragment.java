package com.belac.ines.foodie.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.belac.ines.foodie.R;
import com.belac.ines.foodie.api.APIService;
import com.belac.ines.foodie.api.OrderResponse;
import com.belac.ines.foodie.api.RetrofitClient;
import com.belac.ines.foodie.helper.ArchiveAdapter;
import com.belac.ines.foodie.helper.SessionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Korisnik on 19.01.2018..
 */

public class ArchiveFragment extends Fragment {

    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    public ArchiveFragment() {
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_archive, container, false);

        ButterKnife.bind(this, view);

        initRecyclerView();

        if (SessionManager.getType(getContext()) == 1) {
            getUserOrders();
        } else {
            getRestaurantOrders();
        }

        return view;
    }

    private void getRestaurantOrders() {
        progressBar.setVisibility(View.VISIBLE);
        RetrofitClient.instance()
                .create(APIService.class)
                .getRestaurantOrders(Integer.valueOf(SessionManager.getId(getContext())))
                .enqueue(new Callback<OrderResponse>() {
                    @Override public void onResponse(@NonNull Call<OrderResponse> call,
                                                     @NonNull Response<OrderResponse> response) {
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setAdapter(new ArchiveAdapter(response.body().getResults(), getContext()));
                    }

                    @Override public void onFailure(@NonNull Call<OrderResponse> call, @NonNull Throwable t) {
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    private void getUserOrders() {
        progressBar.setVisibility(View.VISIBLE);
        RetrofitClient.instance()
                .create(APIService.class)
                .getOrders(Integer.valueOf(SessionManager.getId(getContext())))
                .enqueue(new Callback<OrderResponse>() {
                    @Override public void onResponse(@NonNull Call<OrderResponse> call,
                                                     @NonNull Response<OrderResponse> response) {
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setAdapter(new ArchiveAdapter(response.body().getResults(), getContext()));
                    }

                    @Override public void onFailure(@NonNull Call<OrderResponse> call, @NonNull Throwable t) {
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
    }
}
