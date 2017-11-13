package com.belac.ines.foodie.registration;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.belac.ines.foodie.R;

/**
 * Created by Ines on 13.11.2017..
 */

public class RestoranFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.restoran_fragment, container, false);
    }

}
