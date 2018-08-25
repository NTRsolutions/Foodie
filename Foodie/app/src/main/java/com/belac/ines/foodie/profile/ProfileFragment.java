package com.belac.ines.foodie.profile;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.belac.ines.foodie.R;
import com.belac.ines.foodie.helper.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileFragment extends Fragment {

    private static final String TAG = ProfileFragment.class.getSimpleName();

    @BindView(R.id.user) TextView name;
    @BindView(R.id.address) TextView address;
    @BindView(R.id.email) TextView email;
    @BindView(R.id.telephone) TextView telephone;

    public ProfileFragment() {
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ButterKnife.bind(this, view);

        name.setText(
                String.format("%s %s", SessionManager.getName(getContext()), SessionManager.getSurname(getContext())));
        email.setText(String.format("Email: %s", SessionManager.getEmail(getContext())));
        address.setText(String.format("Address: %s", SessionManager.getAddress(getContext())));
        telephone.setText(String.format("Telephone: %s", SessionManager.getTelephone(getContext())));

        return view;
    }
}
