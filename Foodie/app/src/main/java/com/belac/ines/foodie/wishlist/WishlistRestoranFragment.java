package com.belac.ines.foodie.wishlist;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.belac.ines.foodie.R;
import com.belac.ines.foodie.api.AppConfig;
import com.belac.ines.foodie.classes.Restoran;
import com.belac.ines.foodie.helper.WishlistAdapter;
import com.belac.ines.foodie.helper.WishlistTouchHelper;

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
import java.util.ArrayList;
import java.util.List;

public class WishlistRestoranFragment extends Fragment implements WishlistTouchHelper.WishlistTouchHelperListener {
    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;

    private List<Restoran> restoranList = new ArrayList<>();
    private RecyclerView recyclerView;
    private WishlistAdapter wAdapter;
    private EditText search;
    private FrameLayout frameLayout;
    private String userEmail;

    public WishlistRestoranFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restorant, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        frameLayout = (FrameLayout) view.findViewById(R.id.frame_layout);
        wAdapter = new WishlistAdapter(restoranList, getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(wAdapter);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new WishlistTouchHelper(0, ItemTouchHelper.LEFT, this, false);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

        search = (EditText) view.findViewById(R.id.search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence query, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence query, int i, int i1, int i2) {
                wAdapter.getFilter().filter(query);
            }

            @Override
            public void afterTextChanged(Editable query) {}
        });
        return view;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof WishlistAdapter.MyViewHolder) {
            // get the removed item name to display it in snack bar
            String name = restoranList.get(viewHolder.getAdapterPosition()).getName();
            int id = restoranList.get(viewHolder.getAdapterPosition()).getID();

            // backup of removed item for undo purpose
            final Restoran deletedItem = restoranList.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            wAdapter.removeItem(viewHolder.getAdapterPosition());
            boolean ups = new DeleteItem(userEmail, id).isError();
            //TODO osvjezit prikaz

            // showing snack bar with Undo option
            Snackbar snackbar;
            if(!ups){
                snackbar = Snackbar
                        .make(frameLayout, name + " removed from wishlist!", Snackbar.LENGTH_LONG)
                        .setDuration(5000);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        // undo is selected, restore the deleted item
                        wAdapter.restoreItem(deletedItem, deletedIndex);
                    }
                });
                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();
            }else{
                snackbar = Snackbar
                        .make(frameLayout, "Mistake: " + name + " hasn't been removed!", Snackbar.LENGTH_LONG)
                        .setDuration(5000);
            }
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }


    private class AsyncWishlist extends AsyncTask<String, String, String> {
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
                url = new URL(AppConfig.URL_WISHLIST);

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
                        .appendQueryParameter("user",params[0])
                        .appendQueryParameter("action", "restorants");
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
                    JSONArray json = jObj.getJSONArray("results");
                    int i;
                    for(i=0; i < json.length(); i++) {

                        JSONObject jObject = json.getJSONObject(i);
                        Restoran res = new Restoran(jObject.getInt("id"), jObject.getString("name"), jObject.getString("address"));
                        restoranList.add(res);
                    }
                    wAdapter.notifyDataSetChanged();
                } else if (error) {
                    Toast.makeText(getActivity(), "Wishlist is empty", Toast.LENGTH_LONG).show();

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