package com.belac.ines.foodie.wishlist;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;

import com.belac.ines.foodie.app.AppConfig;
import com.belac.ines.foodie.classes.Menu;
import com.belac.ines.foodie.classes.Restoran;

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

/**
 * Created by Ines on 17.1.2018..
 */

public class DeleteItem {

    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;

    HttpURLConnection conn;
    URL url = null;
    private String name;
    private int ID;
    boolean error;

    public DeleteItem(String name, int id) {
        this.name  = name;
        this.ID = id;
    }

    public boolean isError() {
        new AsyncWishlist().execute();
        return error;
    }

    private class AsyncWishlist extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {}

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
                        .appendQueryParameter("user", name)
                        .appendQueryParameter("restoranID", String.valueOf(ID))
                        .appendQueryParameter("action", "delete");
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
                error = jObj.getBoolean("error");

            } catch (JSONException e) {
                e.printStackTrace();
                error = true;
            }
        }
    }
}
