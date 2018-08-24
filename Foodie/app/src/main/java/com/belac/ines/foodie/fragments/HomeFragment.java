package com.belac.ines.foodie.fragments;

import android.Manifest;
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
import android.widget.Toast;

import com.belac.ines.foodie.R;
import com.belac.ines.foodie.api.APIService;
import com.belac.ines.foodie.api.RestaurantResponse;
import com.belac.ines.foodie.api.RetrofitClient;
import com.belac.ines.foodie.helper.HomeAdapter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment implements HomeAdapter.HomeListener {

    private GoogleMap googleMap;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.progress_bar)  ProgressBar mProgressBar;
    private SupportMapFragment mapFragment;
    private FusedLocationProviderClient mFusedLocationClient;
    private final LatLng mDefaultLocation = new LatLng(51.509865, -0.118092);

    private static final String TAG = HomeFragment.class.getSimpleName();

    public HomeFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        getData("Porto");

        return view;
    }

    private void getData(String place) {
        mProgressBar.setVisibility(View.VISIBLE);
        RetrofitClient.instance()
                .create(APIService.class)
                .getTop3(place)
                .enqueue(new Callback<List<RestaurantResponse>>() {
                    @Override public void onResponse(@NonNull Call<List<RestaurantResponse>> call, @NonNull Response<List<RestaurantResponse>> response) {
                        Log.d(TAG, "onResponse");
                        mProgressBar.setVisibility(View.GONE);

                        showData(response);
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<RestaurantResponse>> call, @NonNull Throwable t) {
                        Log.d(TAG, "onResponse");
                        mProgressBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showData(@NonNull Response<List<RestaurantResponse>> response) {

        recyclerView.setAdapter(new HomeAdapter(response.body(), getContext(), this));
    }

    private void checkLocationPermission() {
        Dexter.withActivity(getActivity())
                .withPermissions(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override public void onPermissionsChecked(MultiplePermissionsReport report) {
                        Log.d(TAG, "onPermissionsChecked");
                    }

                    @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions,
                                                                             PermissionToken token) {
                        Log.d(TAG, "onPermissionRationaleShouldBeShown");
                        token.continuePermissionRequest();
                    }
                })
                .check();
    }

    @Override
    public void onClickLocation(LatLng latLng) {

    }

    @Override
    public void onClickItem(RestaurantResponse item) {

    }
}
