package com.hmdapp.finaltailor.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ApplicationInfo;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.hmdapp.finaltailor.R;

import java.io.File;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Locale locale = new Locale("En");
        Locale.setDefault(locale);

        Configuration configuration = new Configuration();
        configuration.setLocale(locale);
        getBaseContext().getResources().updateConfiguration(configuration,getBaseContext().getResources().getDisplayMetrics());


       setUpToolbar();
       setUpNavigationView();
    }

    public void person_Action(View view) {
        startActivity(new Intent(this, Person_Activity.class));

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_simp, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void task_action(View view) {
        startActivity(new Intent(this, Tasks_Activity.class));

    }

    public void onReportClick(View view) {
        startActivity(new Intent(this, DashboradReportActivity.class));

    }

    private void setUpNavigationView() {
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {


                    case R.id.contact_us:
                        startActivity(new Intent(MainActivity.this, ContactActivity.class));
                        break;
                    case R.id.close_the_app:
                        Toast.makeText(MainActivity.this, menuItem.getTitle(), Toast.LENGTH_LONG).show();
                        finish();
                        break;
                    case R.id.nav_share:

                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, "Afghan Tailor App is coming online ");
                        sendIntent.setType("text/plain");
                        startActivity(sendIntent);
                        break;

                    case R.id.nav_send:
                        ApplicationInfo app = getApplicationContext().getApplicationInfo();
                        String filePath = app.sourceDir;

                        Intent intent = new Intent(Intent.ACTION_SEND);

                        intent.setType("*/*");

                        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(filePath)));
                        startActivity(Intent.createChooser(intent, "Afghan Tailor App Shared"));

                        break;
                    case R.id.nav_about:
                        Toast.makeText(MainActivity.this,menuItem.getTitle(),Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_facebook:
                        Toast.makeText(MainActivity.this,menuItem.getTitle(),Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_gmail:
                        Toast.makeText(MainActivity.this,menuItem.getTitle(),Toast.LENGTH_LONG).show();
                        break;
                    case R.id.customer_item:
                        startActivity(new Intent(MainActivity.this,Person_Activity.class));
                        break;
                    case R.id.task_item:
                        startActivity(new Intent(MainActivity.this,Tasks_Activity.class));
                        break;
                    case R.id.report_item:
                        startActivity(new Intent(MainActivity.this,DashboradReportActivity.class));
                        break;

                }

                return true;
            }
        });
    }


    private void setUpToolbar() {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white_color));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("خیاط");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, 0, 0);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }


}
