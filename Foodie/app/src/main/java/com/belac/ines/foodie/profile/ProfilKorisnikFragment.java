package com.belac.ines.foodie.profile;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.belac.ines.foodie.R;
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

        SessionManager session = new SessionManager(getActivity());
        if(session.isLoggedIn()){
            SQLiteHandler db = new SQLiteHandler(getActivity());
            HashMap<String, String> user = db.getUserDetails();
            name.setText( user.get("name") + " " + user.get("surname"));

        }else{
            Toast.makeText(getActivity(), "User is not logged in", Toast.LENGTH_LONG);
        }

        return view;
    }
}
