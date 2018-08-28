package com.belac.ines.foodie;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.belac.ines.foodie.api.APIService;
import com.belac.ines.foodie.api.RetrofitClient;
import com.belac.ines.foodie.api.models.LoginResponse;
import com.belac.ines.foodie.helper.SessionManager;
import com.belac.ines.foodie.registration.Register;

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

        // Check if user is already logged in or not
        if (SessionManager.isUserLoggedIn(getApplicationContext())) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    // Triggers when LOGIN Button clicked
    @OnClick({R.id.btnLogin}) void onClickLogin() {

        final String email = etEmail.getText().toString().trim();
        final String password = etPassword.getText().toString().trim();

        int check = checkCredentials(email, password);
        if(check == 3){
            Toast.makeText(Login.this, "Please enter your email.", Toast.LENGTH_SHORT).show();
        }else if (check == 2){
            Toast.makeText(Login.this, "Please enter your password.", Toast.LENGTH_SHORT).show();
        }else if(check == 1){
            Toast.makeText(Login.this, "Please enter your credentials.", Toast.LENGTH_SHORT).show();
        }else if (check == 0){
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

                                SessionManager.setLoggIn(getApplicationContext(), loginResponse.getName(),
                                        loginResponse.getSurname(), loginResponse.getEmail(),
                                        loginResponse.getTelephone(), loginResponse.getAddress(),
                                        Integer.valueOf(loginResponse.getType()), id);

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

    public int checkCredentials(String email, String password) {
        if(email.isEmpty() && password.isEmpty()){
            return 1;
        }else if(password.isEmpty()){
            return 2;
        }else if (email.isEmpty()){
            return 3;
        }else{
            return 0;
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

