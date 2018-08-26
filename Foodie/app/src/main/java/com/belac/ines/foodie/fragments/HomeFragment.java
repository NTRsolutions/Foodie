package com.belac.ines.foodie.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
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
import com.belac.ines.foodie.profile.ProfileRestoranFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment implements OnMapReadyCallback, HomeAdapter.HomeListener {

    private GoogleMap googleMap;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.progress_bar)  ProgressBar mProgressBar;
    private SupportMapFragment mapFragment;
    private FusedLocationProviderClient mFusedLocationClient;

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


        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_view);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mapFragment.getMapAsync(this);

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
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        for (RestaurantResponse restaurants : response.body()) {
            LatLng loc = new LatLng(Double.parseDouble(restaurants.getLatitude()),
                    Double.parseDouble(restaurants.getLongitude()));

            googleMap.addMarker(new MarkerOptions().position(loc).title(restaurants.getName()));
            builder.include(loc);
        }

        LatLngBounds bounds = builder.build();
        int width = getResources().getDisplayMetrics().widthPixels;
        CameraUpdate center = CameraUpdateFactory.newLatLngBounds(bounds, width,300,0);
        googleMap.moveCamera(center);

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
        CameraUpdate center = CameraUpdateFactory.newLatLng(latLng);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(13);
        googleMap.moveCamera(center);
        googleMap.animateCamera(zoom);
    }

    @Override
    public void onClickItem(RestaurantResponse item) {
        Fragment fragment = new MenuDetailFragment();
        Bundle args = new Bundle();
        args.putInt("id", Integer.valueOf(item.getId()));
        fragment.setArguments(args);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            showLastLocation();
        } else {
            checkLocationPermission();
            CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(0,0));
            CameraUpdate zoom = CameraUpdateFactory.zoomTo(2);
            googleMap.moveCamera(center);
            googleMap.animateCamera(zoom);
        }
    }

    @SuppressLint("MissingPermission")
    private void showLastLocation() {
        googleMap.setMyLocationEnabled(true);

        mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override public void onSuccess(Location location) {

                if (location == null) {
                    getData("Pazin");
                    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(0,0));
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(2);
                    googleMap.moveCamera(center);
                    googleMap.animateCamera(zoom);

                    return;
                }

                CameraUpdate center =
                        CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude()));
                try {
                    Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                    String place = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1).get(0).getLocality();
                    getData(place);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                CameraUpdate zoom = CameraUpdateFactory.zoomTo(10);
                googleMap.moveCamera(center);
                googleMap.animateCamera(zoom);

            }
        });
    }
}
