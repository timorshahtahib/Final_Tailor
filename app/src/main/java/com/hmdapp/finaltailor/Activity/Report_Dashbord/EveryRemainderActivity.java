package com.hmdapp.finaltailor.Activity.Report_Dashbord;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.hmdapp.finaltailor.Adapter.AdapterListPayment;
import com.hmdapp.finaltailor.Models.Payment;
import com.hmdapp.finaltailor.R;
import com.hmdapp.finaltailor.Utlity.SpacingItemDecoration;
import com.hmdapp.finaltailor.Utlity.Tools;
import com.hmdapp.finaltailor.database.DB_Acsess;

import java.util.List;

public class EveryRemainderActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    AdapterListPayment adapterListPayment;
    private View parent_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_every_remainder);
        parent_view = findViewById(android.R.id.content);
        initComponent();
        initToolbar();
    }

    private void initComponent() {


        int Id = getIntent().getIntExtra("id", 1);
        recyclerView = findViewById(R.id.every_remainder_report_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new SpacingItemDecoration(3, Tools.dpToPx(this, 3), true));

        //Toast.makeText(this, "Customer id  : " + customerId, Toast.LENGTH_LONG).show();


        DB_Acsess db_acsess = DB_Acsess.getInstans(this);
        db_acsess.open();
        List<Payment> items = db_acsess.getListOfRemainderReport(this,Id);


        adapterListPayment = new AdapterListPayment(this, items);
        recyclerView.setAdapter(adapterListPayment);
        // Toast.makeText(this, "size" + items.size(), Toast.LENGTH_SHORT).show();
        // on item list clicked

        adapterListPayment.setOnItemClickListener(new AdapterListPayment.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Payment obj, int position) {
                Intent intent = new Intent(getApplicationContext(), TotalReportPaymentCustomerActivity.class);
                intent.putExtra("id", obj.getId());

                intent.putExtra("task_id", obj.getOrder().getId());

                intent.putExtra("payment", obj.getAmount());


                startActivity(intent);
                //Snackbar.make(parent_view, "Item " + obj.getCustomer().getPhone() + " clicked", Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("لیست قرض های فردی ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_setting, menu);
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
}