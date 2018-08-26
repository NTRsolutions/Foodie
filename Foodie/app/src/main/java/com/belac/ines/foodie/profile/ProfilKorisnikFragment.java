package com.belac.ines.foodie.profile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.belac.ines.foodie.R;

import com.belac.ines.foodie.helper.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ines on 21.1.2018..
 */

public class ProfilKorisnikFragment extends Fragment {

    private static final String TAG = ProfilKorisnikFragment.class.getSimpleName();

    @BindView(R.id.userName) TextView name;
    @BindView(R.id.userAddress) TextView address;
    @BindView(R.id.userEmail) TextView email;
    @BindView(R.id.userTelephone) TextView telephone;

    public ProfilKorisnikFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_korisnik, container, false);

        ButterKnife.bind(this, view);

        name.setText(
                String.format("%s %s", SessionManager.getName(getContext()), SessionManager.getSurname(getContext())));
        email.setText(String.format("Email: %s", SessionManager.getEmail(getContext())));
        address.setText(String.format("Address: %s", SessionManager.getAddress(getContext())));
        telephone.setText(String.format("Telephone: %s", SessionManager.getTelephone(getContext())));


        return view;
    }
}
