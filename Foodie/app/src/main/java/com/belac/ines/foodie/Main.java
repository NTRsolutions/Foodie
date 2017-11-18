package com.belac.ines.foodie;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.belac.ines.foodie.helper.SQLiteHandler;
import com.belac.ines.foodie.helper.SessionManager;

import java.util.HashMap;

public class Main extends AppCompatActivity {

    private Button btLogout;
    private SQLiteHandler db;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btLogout = (Button) findViewById(R.id.btnLogout);

        session = new SessionManager(getApplicationContext());
        Toast.makeText(Main.this, "Session: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();
        session.setLogin(false);
        db = new SQLiteHandler(getApplicationContext());
        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        String surname = user.get("surname");
        String email = user.get("email");

        Toast.makeText(Main.this, "SQLite: " + name + ", " + surname + ", " + email, Toast.LENGTH_LONG).show();

        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutUser();
            }
        });
    }

    private void logoutUser() {
        session.setLogin(false); //delete session
        db.deleteUsers(); //delete user from SQLite baze
        Intent intent = new Intent(Main.this, Login.class);
        startActivity(intent);
        finish();
    }
}
