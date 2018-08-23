package com.belac.ines.foodie.profile;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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
import com.belac.ines.foodie.api.AppConfig;
import com.belac.ines.foodie.classes.Menu;
import com.belac.ines.foodie.classes.Restoran;
import com.belac.ines.foodie.helper.SQLiteHandler;
import com.belac.ines.foodie.helper.SessionManager;
import com.belac.ines.foodie.wishlist.DeleteItem;
import com.belac.ines.foodie.wishlist.WishlistMenuFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProfileRestoranFragment extends Fragment implements View.OnClickListener {

    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;

    private TextView name, address, city, telephone, firstMeal, secondMeal, dessert;
    int restoranID;
    private Switch wishlist;
    private String userEmail;
    private Button btnOrder;
    public String action;

    public ProfileRestoranFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        name = (TextView) view.findViewById(R.id.restoranName);
        address = (TextView) view.findViewById(R.id.restoranAddress);
        telephone = (TextView) view.findViewById(R.id.restoranTelephone);
        firstMeal = (TextView) view.findViewById(R.id.restoranFirstMeal);
        secondMeal = (TextView) view.findViewById(R.id.restoranSecondMeal);
        dessert = (TextView) view.findViewById(R.id.restoranDessert);
        wishlist = (Switch) view.findViewById(R.id.switchWishlist);
        btnOrder = (Button) view.findViewById(R.id.btnOrder);

        SessionManager session = new SessionManager(getActivity());
        if(session.isLoggedIn()){
            SQLiteHandler db = new SQLiteHandler(getActivity());
            HashMap<String, String> user = db.getUserDetails();
            userEmail = user.get("email");
            Bundle args = getArguments();
            int index = args.getInt("id", 0);
            restoranID = index;
            action = "getData";
            new AsyncProfil().execute(AppConfig.URL_PROFIL, "get");
        }else{
            Toast.makeText(getActivity(), "User is not logged in", Toast.LENGTH_LONG);
        }

        wishlist.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    action = "insert";
                    new AsyncProfil().execute(AppConfig.URL_WISHLIST, "add");
                }else {
                    boolean error = new DeleteItem(userEmail, 10).isError();
                }
            }
        });

        btnOrder.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        action = "insert";
        new AsyncProfil().execute(AppConfig.URL_ORDER, "add");
    }

    private class AsyncProfil extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(getActivity());
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                url = new URL(params[0]);

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "exception";
            }
            try {
                // Setup HttpURLConnection class
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("user", userEmail)
                        .appendQueryParameter("restoran", Integer.toString(restoranID))
                        .appendQueryParameter("action", params[1]);
                String query = builder.build().getEncodedQuery();

                // Open connection for sending data
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();

            } catch (IOException e1) {
                e1.printStackTrace();
                return "exception";
            }
            try {
                int response_code = conn.getResponseCode();
                if (response_code == HttpURLConnection.HTTP_OK) {
                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    // Pass data to onPostExecute method
                    return (result.toString());

                } else { return ("unsuccessful"); }

            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            } finally {
                conn.disconnect();
            }
        }

        @Override
        protected void onPostExecute(String result) {

            try {
                JSONObject jObj = new JSONObject(result);
                boolean error = jObj.getBoolean("error");
                if (!error) {
                    if(action.equals("getData")){
                        JSONArray json = jObj.getJSONArray("results");
                        int i;
                        for(i=0; i < json.length(); i++) {

                            JSONObject jObject = json.getJSONObject(i);
                            name.setText(jObject.getString("name"));
                            address.setText(jObject.getString("address"));
                            telephone.setText(jObject.getString("telephone"));
                            firstMeal.setText(jObject.getString("first"));
                            secondMeal.setText(jObject.getString("second"));
                            dessert.setText(jObject.getString("third"));
                            wishlist.setChecked(Boolean.parseBoolean(jObject.getString("wishlist")));
                        }
                    }
                } else if (error) {
                    Toast.makeText(getActivity(), jObj.getString("error_msg"), Toast.LENGTH_LONG).show();
                } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {
                    Toast.makeText(getActivity(), "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "JSON problem: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
            pdLoading.dismiss();
        }
        }

}
