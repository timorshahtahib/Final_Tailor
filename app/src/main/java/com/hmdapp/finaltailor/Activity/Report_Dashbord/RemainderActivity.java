package com.hmdapp.finaltailor.Activity.Report_Dashbord;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.hmdapp.finaltailor.Adapter.AdapterListBasic;
import com.hmdapp.finaltailor.Adapter.AdapterListRemainder;
import com.hmdapp.finaltailor.Models.Payment;
import com.hmdapp.finaltailor.Models.payment_report;
import com.hmdapp.finaltailor.R;
import com.hmdapp.finaltailor.Utlity.SpacingItemDecoration;
import com.hmdapp.finaltailor.Utlity.Tools;
import com.hmdapp.finaltailor.database.DB_Acsess;

import java.util.List;

public class RemainderActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    AdapterListRemainder adapterListRemainder;
    private View parent_view;
    List<payment_report> items;
    DB_Acsess db_acsess;

    private SearchView searchView;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remainder);
        parent_view = findViewById(android.R.id.content);
        initToolbar();
        initComponent();
    }

    private void initComponent() {
        recyclerView = findViewById(R.id.remainder_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new SpacingItemDecoration(3, Tools.dpToPx(this, 3), true));


        db_acsess = DB_Acsess.getInstans(this);
        db_acsess.open();
        items = db_acsess.getreminder_customer( this);


        adapterListRemainder = new AdapterListRemainder(this, items);
        recyclerView.setAdapter(adapterListRemainder);


        adapterListRemainder.setOnItemClickListener(new AdapterListRemainder.OnItemClickListener() {
            @Override
            public void onItemClick(View view, payment_report obj, int position) {
                Intent intent = new Intent(getApplicationContext(), EveryRemainderActivity.class);
                intent.putExtra("id", obj.getId());



                startActivity(intent);
              //  Snackbar.make(parent_view, "Item " + obj.getCustomer().getPhone() + " clicked", Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("لیست تمام قرض ها ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

                items = db_acsess.getreminder_customer( getApplicationContext());

                //set data and list adapter
               adapterListRemainder = new AdapterListRemainder(getApplicationContext(), items);
                recyclerView.setAdapter( adapterListRemainder);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {

            startActivity(new Intent(this,DashboradReportActivity.class));
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

    }

    private void searchAction(String query) {
        items.clear();
        recyclerView.setAdapter(null);





        if (!query.trim().equals("")) {

            items.addAll(db_acsess.searchCustomer_reminder(query));
            if (items.size() > 0) {
                //  lyt_result.setVisibility(View.VISIBLE);
                recyclerView.setAdapter(adapterListRemainder);
            }

        } else {
            Toast.makeText(this, "Please fill search input", Toast.LENGTH_SHORT).show();
        }
    }
}

