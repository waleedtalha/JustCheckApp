package com.cwise.justcheck;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private static final String MY_PREF = "my preff";
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    boolean dark = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = getSharedPreferences(MY_PREF, MODE_PRIVATE);
        String abc =preferences.getString("themekey","a");
        if (abc.equals("dark")) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigation);
        drawer = findViewById(R.id.drawer);

        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        getSupportFragmentManager().beginTransaction().replace(R.id.frame, new OneFragment()).commit();
        navigationView.setCheckedItem(R.id.first);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            Fragment fragment;

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawer.closeDrawer(GravityCompat.START);
                switch (item.getItemId()) {
                    case R.id.first:
                        fragment = new OneFragment();
                        break;
                    case R.id.two:
                        Notify.Toast("as");
                        fragment = new TwoFragment();
                        break;
                    case R.id.fourth:
                        fragment = new OneFragment();
                        SharedPreferences.Editor editor = getSharedPreferences(MY_PREF, MODE_PRIVATE).edit();
                        int nightModeFlags =getResources().getConfiguration().uiMode &
                                        Configuration.UI_MODE_NIGHT_MASK;
                        if (nightModeFlags==Configuration.UI_MODE_NIGHT_NO) {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                            editor.clear();
                            editor.putString("themekey", "dark");
                            editor.apply();
                            dark = false;
                        } else if (nightModeFlags==Configuration.UI_MODE_NIGHT_YES){
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                            editor.clear();
                            editor.putString("themekey", "white");
                            editor.apply();
                            dark = true;
                        }
                        drawer.openDrawer(GravityCompat.START);

                        break;
                    case R.id.share:
                        fragment = new OneFragment();
                        Intent share_intent = new Intent();
                        share_intent.setAction(Intent.ACTION_SEND);
                        share_intent.putExtra(Intent.EXTRA_TEXT, "Check this app via\n" +
                                "https://play.google.com/store/apps/details?id="
                                + getApplicationContext().getPackageName());
                        share_intent.setType("text/plain");
                        startActivity(Intent.createChooser(share_intent, "Share app via"));
                        break;
                    case R.id.rateus:
                        fragment = new OneFragment();
                        Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName());
                        Intent i = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(i);
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment).commit();
                return true;
            }
        });
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
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        int NightMode = AppCompatDelegate.getDefaultNightMode();

        SharedPreferences sharedPreferences = getSharedPreferences("SharedPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("NightModeInt", NightMode);
        editor.apply();
    }
}