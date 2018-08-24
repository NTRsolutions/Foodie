package com.belac.ines.foodie.profile;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.belac.ines.foodie.R;
import com.belac.ines.foodie.api.APIService;
import com.belac.ines.foodie.api.AppConfig;
import com.belac.ines.foodie.api.RestaurantDetailsResponse;
import com.belac.ines.foodie.api.RestaurantMenusResponse;
import com.belac.ines.foodie.api.RetrofitClient;
import com.belac.ines.foodie.fragments.NewMenuFragment;
import com.belac.ines.foodie.helper.SQLiteHandler;
import com.belac.ines.foodie.helper.SessionManager;
import com.belac.ines.foodie.wishlist.DeleteItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileRestoranFragment extends Fragment implements ProfileRestoranAdapter.RestaurantProfileAdapterListener {

    private static final String TAG = ProfileFragment.class.getSimpleName();

    @BindView(R.id.name) TextView name;
    @BindView(R.id.telephone) TextView telephone;
    @BindView(R.id.location) TextView location;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.root)
    RelativeLayout root;

    public ProfileRestoranFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restauran_profile, container, false);

        ButterKnife.bind(this, view);

        initRecyclerView();

        RetrofitClient.instance()
                .create(APIService.class)
                .restaurantDetails(Integer.valueOf(SessionManager.getId(getContext())))
                .enqueue(new Callback<RestaurantDetailsResponse>() {
                    @Override public void onResponse(@NonNull Call<RestaurantDetailsResponse> call,
                                                     @NonNull Response<RestaurantDetailsResponse> response) {

                        name.setText(response.body().getResults().get(0).getName());
                        location.setText(
                                String.format("Location: %s", response.body().getResults().get(0).getLocation()));
                        telephone.setText(
                                String.format("Telephone: %s", response.body().getResults().get(0).getTelephone()));
                    }

                    @Override
                    public void onFailure(@NonNull Call<RestaurantDetailsResponse> call, @NonNull Throwable t) {
                        Log.e(TAG, t.getLocalizedMessage());
                    }
                });

        progressBar.setVisibility(View.VISIBLE);
        RetrofitClient.instance()
                .create(APIService.class)
                .restaurantMenus(Integer.valueOf(SessionManager.getId(getContext())))
                .enqueue(new Callback<RestaurantMenusResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<RestaurantMenusResponse> call, @NonNull Response<RestaurantMenusResponse> response) {
                        progressBar.setVisibility(View.GONE);

                        initAdapter(response);
                    }

                    @Override
                    public void onFailure(@NonNull Call<RestaurantMenusResponse> call, @NonNull Throwable t) {

                    }
                });

        return view;
    }

    private void initAdapter(@NonNull Response<RestaurantMenusResponse> response) {
        recyclerView.setAdapter(new ProfileRestoranAdapter(response.body().getResults(), getContext(), this));
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
    }

    @OnClick(R.id.add_new_menu) void onClickAddNewMenu() {
        Fragment fragment = new NewMenuFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
    }

    @Override public void onClickDelete(final RestaurantMenusResponse.Result item) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Do you want delete menu?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
                RetrofitClient.instance()
                        .create(APIService.class)
                        .deleteMenu(Integer.valueOf(item.getId()))
                        .enqueue(new Callback<ResponseBody>() {
                            @Override public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                ((ProfileRestoranAdapter) recyclerView.getAdapter()).removeItem(item);
                            }

                            @Override public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }
}