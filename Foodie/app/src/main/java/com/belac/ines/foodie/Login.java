package com.belac.ines.foodie;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.belac.ines.foodie.app.AppConfig;

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

public class Login extends AppCompatActivity {

    // CONNECTION_TIMEOUT and READ_TIMEOUT are in milliseconds

    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;
    private EditText etEmail;
    private EditText etPassword;
    private Button btLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Get Reference to variables
        etEmail = (EditText) findViewById(R.id.email);
        etPassword = (EditText) findViewById(R.id.lozinka);
        btLogin = (Button) findViewById(R.id.btnLogin);

    }

    // Triggers when LOGIN Button clicked
    public void checkLogin(View arg0) {

        // Get text from email and passord field
        final String email = etEmail.getText().toString().trim();
        final String password = etPassword.getText().toString().trim();
        if(!email.isEmpty() && !password.isEmpty() ) {
            // Initialize  AsyncLogin() class with email and password
            new AsyncLogin().execute(email,password);
        }else{
            Toast.makeText(Login.this, "OOPs! Please enter your email and password.", Toast.LENGTH_LONG).show();
        }
    }

    private class AsyncLogin extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(Login.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your php file resides
                url = new URL(AppConfig.URL_LOGIN);

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "exception";
            }
            try {

                    // Setup HttpURLConnection class to send and receive data from php and mysql
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(READ_TIMEOUT);
                    conn.setConnectTimeout(CONNECTION_TIMEOUT);
                    conn.setRequestMethod("POST");

                    // setDoInput and setDoOutput method depict handling of both send and receive
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

                    // Append parameters to URL
                    Uri.Builder builder = new Uri.Builder()
                            .appendQueryParameter("email", params[0])
                            .appendQueryParameter("password", params[1]);
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

                // Check if successful connection made
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

                } else {

                    return ("unsuccessful");
                }

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
                    String name = jObj.getString("name");
                    String surname = jObj.getString("surname");
                    Toast.makeText(getApplicationContext(), name + " " + surname, Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(Login.this, Main.class);
                    startActivity(intent);
                    Login.this.finish();

                } else if (error) {
                    String errorMsg = jObj.getString("error_msg");
                    Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();

                } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {
                    Toast.makeText(Login.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Wrong user or password.", Toast.LENGTH_LONG).show();
            }

            pdLoading.dismiss();

        }
    }
}
