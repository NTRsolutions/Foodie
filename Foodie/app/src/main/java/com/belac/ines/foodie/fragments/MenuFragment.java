package com.belac.ines.foodie.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.belac.ines.foodie.R;
import com.belac.ines.foodie.api.APIService;
import com.belac.ines.foodie.api.Interactor;
import com.belac.ines.foodie.api.MenuResponse;
import com.belac.ines.foodie.api.RetrofitClient;
import com.belac.ines.foodie.helper.MenuAdapter;
import com.belac.ines.foodie.helper.OrderInteractor;
import com.belac.ines.foodie.profile.ProfileRestoranFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuFragment extends Fragment implements MenuAdapter.MenuListener {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.root)
    LinearLayout root;

    private List<MenuResponse.Result> menuList = new ArrayList<>();
    private MenuAdapter menuAdapter;
    private static int id=0101;

    public MenuFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lists, container, false);

        ButterKnife.bind(this, view);

        menuAdapter = new MenuAdapter(menuList, getContext(), this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(menuAdapter);

        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        RetrofitClient.instance().create(APIService.class).menu().enqueue(new Callback<MenuResponse>() {
            @Override
            public void onResponse(@NonNull Call<MenuResponse> call, @NonNull Response<MenuResponse> response) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                if (!response.body().getError()) {

                    menuList.clear();
                    menuList.addAll(response.body().getResults());
                    menuAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MenuResponse> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @OnTextChanged(R.id.search)
    void onTextChange(CharSequence query) {
        menuAdapter.getFilter().filter(query);
    }

    @Override
    public void onClickMenu(MenuResponse.Result item) {
        Fragment fragment = new ProfileRestoranFragment();
        Bundle args = new Bundle();
        args.putInt("id", Integer.valueOf(item.getId()));
        fragment.setArguments(args);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
    }

    @Override
    public void onClickOrder(int menuId) {
        OrderInteractor.orderMenu(getContext(), menuId, new Interactor() {
            @Override
            public void onSuccess() {
                Snackbar.make(root, "The order was sent.", Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onError() {
                Snackbar.make(root, "Something went wrong!", Snackbar.LENGTH_SHORT).show();
            }

        });
    }
}
