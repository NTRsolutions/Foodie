package com.belac.ines.foodie.registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.belac.ines.foodie.Login;
import com.belac.ines.foodie.Main;
import com.belac.ines.foodie.R;

/**
 * Created by Ines on 13.11.2017..
 */

public class UserFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_fragment, container, false);


    }


}
