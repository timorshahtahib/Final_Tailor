package com.hmdapp.finaltailor.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;



import com.hmdapp.finaltailor.Adapter.AdapterGridSingleLine;
import com.hmdapp.finaltailor.Adapter.AdapterList_Tasks;
import com.hmdapp.finaltailor.Models.Cloth;
import com.hmdapp.finaltailor.Models.Customer;
import com.hmdapp.finaltailor.Models.Payment;
import com.hmdapp.finaltailor.Models.Task;
import com.hmdapp.finaltailor.R;
import com.hmdapp.finaltailor.Utlity.SpacingItemDecoration;
import com.hmdapp.finaltailor.Utlity.Tools;
import com.hmdapp.finaltailor.database.DB_Acsess;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
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
        recyclerView.addItemDecoration(new SpacingItemDecoration(3, Tools.dpToPx(this, 3), true));


        DB_Acsess db_acsess = DB_Acsess.getInstans(this);
        db_acsess.open();
        List<Task> items = db_acsess.getAllTask();

        adapterList_tasks = new AdapterList_Tasks(this, items);
        recyclerView.setAdapter(adapterList_tasks);
       // Toast.makeText(this, "size" + items.size(), Toast.LENGTH_SHORT).show();

        // on item list clicked
        adapterList_tasks.setOnItemClickListener(new AdapterList_Tasks.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Task obj, int position) {
                Intent intent = new Intent(getApplicationContext(),PaymentActivity.class);
                intent.putExtra("id", obj.getId());
                intent.putExtra("cu_id", obj.getCustomerId());
                intent.putExtra("state",obj.getState());
                intent.putExtra("is_exist",obj.getIsExist());
                intent.putExtra("deliver_date", obj.getDeliverDate());
                intent.putExtra("color", obj.getColor());
                intent.putExtra("count", obj.getCount());
                intent.putExtra("price", obj.getPrice());
                intent.putExtra("payment", obj.getPayment());
                intent.putExtra("remainder", obj.getRemainder());
                intent.putExtra("reg_date", obj.getRegDate());



                startActivity(intent);
               // Snackbar.make(parent_view, "Item " + obj.getCustomer().getPhone() + " clicked", Snackbar.LENGTH_SHORT).show();
            }
        });

    }
    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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
            finish();
            // startActivity(new Intent(this,MainActivity.class));

        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }


}
