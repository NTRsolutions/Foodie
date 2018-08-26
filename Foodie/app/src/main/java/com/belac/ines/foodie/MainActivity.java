package com.belac.ines.foodie;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.belac.ines.foodie.fragments.ArchiveFragment;
import com.belac.ines.foodie.fragments.FavoritesFragment;
import com.belac.ines.foodie.fragments.HomeFragment;
import com.belac.ines.foodie.fragments.MenuFragment;
import com.belac.ines.foodie.fragments.AllRestaurantsFragment;
import com.belac.ines.foodie.helper.SessionManager;
import com.belac.ines.foodie.profile.ProfilKorisnikFragment;
import com.belac.ines.foodie.profile.ProfileRestoranFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.fragment_container) FrameLayout fragmentContainer;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.nav_view) NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        TextView name = (TextView)header.findViewById(R.id.header_text);
        name.setText(String.format("%s %s", SessionManager.getName(getApplicationContext()),
                SessionManager.getSurname(getApplicationContext())));

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new HomeFragment()).commit();

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            logoutUser();
            return true;
        } else if (id == android.R.id.home) {
            drawer.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment profile;
        if (SessionManager.getType(getApplicationContext()) == 1) {
            profile = new ProfilKorisnikFragment();
        } else {
            profile = new ProfileRestoranFragment();
        }

        Fragment fragment = null;
        if (id == R.id.nav_profile) {
            fragment = new ProfilKorisnikFragment();
        } else if (id == R.id.nav_menu) {
            fragment = new MenuFragment();
        } else if (id == R.id.nav_wishlist) {
            fragment = new FavoritesFragment();
        } else if (id == R.id.nav_restoran) {
            fragment = new AllRestaurantsFragment();
        } else if (id == R.id.nav_home) {
            fragment = new HomeFragment();
        } else if (id == R.id.nav_arhiva){
            fragment = new ArchiveFragment();
        }
        if(fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }
        drawer.closeDrawers();
        return true;
    }

    private void logoutUser() {
        SessionManager.logout(getApplicationContext());
        Intent intent = new Intent(MainActivity.this, Login.class);
        startActivity(intent);
        finish();
    }
}
