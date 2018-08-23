package com.belac.ines.foodie.profile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.belac.ines.foodie.R;

/**
 * Created by Ines on 21.1.2018..
 */

public class ProfilKorisnikFragment extends Fragment {

    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;

    private TextView name, address, city, telephone, firstMeal, secondMeal, dessert;

    public ProfilKorisnikFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_korisnik, container, false);

        name = (TextView) view.findViewById(R.id.userName);
        address = (TextView) view.findViewById(R.id.userAddress);
        telephone = (TextView) view.findViewById(R.id.userTelephone);


        return view;
    }
}
