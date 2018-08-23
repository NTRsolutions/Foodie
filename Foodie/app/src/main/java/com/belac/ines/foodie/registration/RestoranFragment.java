package com.belac.ines.foodie.registration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.belac.ines.foodie.Login;
import com.belac.ines.foodie.MainActivity;
import com.belac.ines.foodie.R;
import com.belac.ines.foodie.api.APIService;
import com.belac.ines.foodie.api.AppConfig;
import com.belac.ines.foodie.api.RetrofitClient;

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
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ines on 13.11.2017..
 */

public class RestoranFragment extends Fragment{

    @BindView(R.id.etName) EditText etName;
    @BindView(R.id.etSurname) EditText etSurname;
    @BindView(R.id.etEmail) EditText etEmail;
    @BindView(R.id.etTelephone) EditText etTelephone;
    @BindView(R.id.etLocation) EditText etLocation;
    @BindView(R.id.etPassword) EditText etPassword;
    @BindView(R.id.etRestoran) EditText etRestoran;
    @BindView(R.id.btnRegister) Button btRegister;

    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_reg_restoran, container, false);

        ButterKnife.bind(this,v);
        progressDialog = new ProgressDialog(getActivity());


        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = etName.getText().toString().trim();
                final String surname = etSurname.getText().toString().trim();
                final String email = etEmail.getText().toString().trim();
                final String telephone = etTelephone.getText().toString().trim();
                final String location = etLocation.getText().toString().trim();
                final String restoran = etRestoran.getText().toString().trim();
                final String password = etPassword.getText().toString().trim();

                if(!email.isEmpty() && !password.isEmpty() && !name.isEmpty() && !surname.isEmpty() ) {

                    //registracija
                    progressDialog.setMessage("Loading...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    RetrofitClient.instance()
                            .create(APIService.class)
                            .register(name, surname, email, telephone, location, restoran, password)
                            .enqueue(new Callback<ResponseBody>() {
                                @Override public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                                    progressDialog.hide();
                                    Intent intent = new Intent(getActivity(), Login.class);
                                    startActivity(intent);
                                    getActivity().finish();
                                }

                                @Override
                                public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                                    progressDialog.hide();
                                    Toast.makeText(getActivity(), "OOPs! Something went wrong. Connection Problem.",
                                            Toast.LENGTH_LONG).show();
                                }
                            });
                }else {
                    Toast.makeText(getActivity(), "OOPs! Please enter all informations.", Toast.LENGTH_LONG).show();
                }
            }
        });

        return v;
    }

}
