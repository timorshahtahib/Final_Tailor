package com.hmdapp.finaltailor.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.hmdapp.finaltailor.Activity.Customer.Profile_Activity;
import com.hmdapp.finaltailor.Activity.Customer.Regester_Customer;
import com.hmdapp.finaltailor.Adapter.AdapterListBasic;
import com.hmdapp.finaltailor.Models.Customer;
import com.hmdapp.finaltailor.R;
import com.hmdapp.finaltailor.database.DB_Acsess;

import java.util.List;

public class Person_Activity extends AppCompatActivity {


    private View parent_view;

    private RecyclerView recyclerView;
    private AdapterListBasic mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_);
        parent_view = findViewById(android.R.id.content);

        FloatingActionButton fb = findViewById(R.id.fab_add);

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Regester_Customer.class));
        }
        });


        initToolbar();
        initComponent();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("لیست مشتریان");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void initComponent() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        DB_Acsess db_acsess = DB_Acsess.getInstans(this);
        db_acsess.open();
        List<Customer> items = db_acsess.get_All_Customer();

        //set data and list adapter
        mAdapter = new AdapterListBasic(this, items);
        recyclerView.setAdapter(mAdapter);

        // on item list clicked
        mAdapter.setOnItemClickListener(new AdapterListBasic.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Customer obj, int position) {
                Intent intent = new Intent(getApplicationContext(), Profile_Activity.class);

                intent.putExtra("id_cu", obj.getId());
                intent.putExtra("date", obj.getName());
                intent.putExtra("job", obj.getJob());
                intent.putExtra("phone", obj.getPhone());
                intent.putExtra("state",0);

                startActivity(intent);
                //Snackbar.make(parent_view, "Item " + obj.getName() + " clicked", Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {

            startActivity(new Intent(this,MainActivity.class));
            finish();

        }else if(item.getItemId() ==  R.id.app_bar_search){
            Toast.makeText(getApplicationContext(), "Search clicked!", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}