package com.hmdapp.finaltailor.Activity.Customer;

import android.app.SearchManager;
import android.content.Context;
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
import android.widget.SearchView;
import android.widget.Toast;


import com.hmdapp.finaltailor.Activity.Customer.Profile_Activity;
import com.hmdapp.finaltailor.Activity.Customer.Regester_Customer;
import com.hmdapp.finaltailor.Activity.MainActivity;
import com.hmdapp.finaltailor.Adapter.AdapterListBasic;
import com.hmdapp.finaltailor.Models.Customer;
import com.hmdapp.finaltailor.R;
import com.hmdapp.finaltailor.database.DB_Acsess;

import java.util.List;

public class Person_Activity extends AppCompatActivity {

 private SearchView searchView;
    private View parent_view;

    private RecyclerView recyclerView;
    private AdapterListBasic mAdapter;

    List<Customer> items;

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
        Toolbar toolbar = findViewById(R.id.toolbar);
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
        items = db_acsess.get_All_Customer();

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
                intent.putExtra("state", 0);




                startActivity(intent);
                //Snackbar.make(parent_view, "Item " + obj.getName() + " clicked", Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_setting, menu);


        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.app_bar_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchAction(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {

                DB_Acsess db_acsess = DB_Acsess.getInstans(getApplicationContext());
                db_acsess.open();
                items = db_acsess.get_All_Customer();

                //set data and list adapter
                mAdapter = new AdapterListBasic(getApplicationContext(), items);
                recyclerView.setAdapter(mAdapter);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {

            startActivity(new Intent(this, MainActivity.class));
            finish();

        } else if (item.getItemId() == R.id.app_bar_search) {
            Toast.makeText(getApplicationContext(), "Search clicked!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);


    }

    private void searchAction(String query) {
        items.clear();
        recyclerView.setAdapter(null);


        DB_Acsess db_acsess = DB_Acsess.getInstans(this);
        db_acsess.open();


        if (!query.trim().equals("")) {

            items.addAll(db_acsess.searchCustomer(query));
            if (items.size() > 0) {
                //  lyt_result.setVisibility(View.VISIBLE);
                recyclerView.setAdapter(mAdapter);
            }

        } else {
            Toast.makeText(this, "Please fill search input", Toast.LENGTH_SHORT).show();
        }
    }


}