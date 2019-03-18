package com.hmdapp.finaltailor.Activity.Order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.hmdapp.finaltailor.Activity.MainActivity;
import com.hmdapp.finaltailor.Adapter.AdapterList_Tasks;
import com.hmdapp.finaltailor.Models.Order;
import com.hmdapp.finaltailor.R;
import com.hmdapp.finaltailor.Utlity.SpacingItemDecoration;
import com.hmdapp.finaltailor.Utlity.Tools;
import com.hmdapp.finaltailor.database.DB_Acsess;

import java.util.List;

public class Tasks_Activity extends AppCompatActivity {
    RecyclerView recyclerView;
    AdapterList_Tasks adapterList_tasks;
    private View parent_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_);
        parent_view = findViewById(android.R.id.content);
        initToolbar();
        initComponent();
    }
    private void initComponent() {
        recyclerView = findViewById(R.id.task_recy);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        DB_Acsess db_acsess = DB_Acsess.getInstans(this);
        db_acsess.open();
        List<Order> items = db_acsess.getAllTask();

        Log.d("order_siz_task_activity",items.size()+"");

        adapterList_tasks = new AdapterList_Tasks(this, items);
        recyclerView.setAdapter(adapterList_tasks);
       // Toast.makeText(this, "size" + items.size(), Toast.LENGTH_SHORT).show();

        // on item list clicked
        adapterList_tasks.setOnItemClickListener(new AdapterList_Tasks.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Order obj, int position) {
                Intent intent = new Intent(getApplicationContext(), show_Order_Info.class);
                intent.putExtra("id", obj.getId());
                intent.putExtra("phone",obj.getCloth().getCustomer().getPhone());
                startActivity(intent);
                finish();
               // Snackbar.make(parent_view, "Item " + obj.getCustomer().getPhone() + " clicked", Snackbar.LENGTH_SHORT).show();
            }
        });

    }
    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("لیست کار ها ");
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

            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

    }
}
