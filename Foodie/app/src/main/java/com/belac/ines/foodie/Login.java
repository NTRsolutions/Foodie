package com.belac.ines.foodie;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.belac.ines.foodie.api.APIService;
import com.belac.ines.foodie.api.AppConfig;
import com.belac.ines.foodie.api.RetrofitClient;
import com.belac.ines.foodie.api.models.LoginResponse;
import com.belac.ines.foodie.helper.SQLiteHandler;
import com.belac.ines.foodie.helper.SessionManager;
import com.belac.ines.foodie.registration.Register;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    @BindView(R.id.email) EditText etEmail;
    @BindView(R.id.lozinka) EditText etPassword;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);

         /*   // User is already logged in. Take him to main activity
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
            finish(); */
    }

    // Triggers when LOGIN Button clicked
    @OnClick({R.id.btnLogin}) void onClickLogin() {

        final String email = etEmail.getText().toString().trim();
        final String password = etPassword.getText().toString().trim();

        if(!email.isEmpty() && !password.isEmpty() ) {
            showProgressDialog();

            RetrofitClient.instance()
                    .create(APIService.class)
                    .login(email, password)
                    .enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<com.belac.ines.foodie.api.models.LoginResponse> call,
                                               @NonNull Response<com.belac.ines.foodie.api.models.LoginResponse> response) {
                            hideProgressDialog();

                            if (!response.body().getError()) {

                                LoginResponse.Results loginResponse = response.body().getResults();

                                String id;
                                if (Integer.valueOf(response.body().getResults().getType()) == 1) {
                                    id = String.valueOf(loginResponse.getIdUser());
                                } else {
                                    id = String.valueOf(loginResponse.getIdRestaurant());
                                }

                            Intent intent = new Intent(Login.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                            }else{
                                Toast.makeText(Login.this, "OOPs! Please check your email and password.", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            hideProgressDialog();
                            Toast.makeText(Login.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(Login.this, "OOPs! Please enter your email and password.", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public void createAccount(View arg0){
        Intent intent = new Intent(Login.this, Register.class);
        startActivity(intent);
        //Login.this.finish();
    }
    private void showProgressDialog() {
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
    private void hideProgressDialog() { progressDialog.hide(); }
}

