package com.hmdapp.finaltailor.Activity.Report_Dashbord;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.hmdapp.finaltailor.Activity.Order.show_Order_Info;
import com.hmdapp.finaltailor.Adapter.AdapterListPayment;
import com.hmdapp.finaltailor.Models.Payment;
import com.hmdapp.finaltailor.R;
import com.hmdapp.finaltailor.Utlity.SpacingItemDecoration;
import com.hmdapp.finaltailor.Utlity.Tools;
import com.hmdapp.finaltailor.database.DB_Acsess;

import java.util.List;

public class ReportActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AdapterListPayment adapterListPayment;
    private View parent_view;
    String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        parent_view = findViewById(android.R.id.content);
        initComponent();
    }

    private void initComponent() {
        value = getIntent().getStringExtra("myKey");
        initToolbar(value);
        recyclerView = findViewById(R.id.report_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new SpacingItemDecoration(3, Tools.dpToPx(this, 3), true));

        //Toast.makeText(this,"Value : "+value,Toast.LENGTH_LONG).show();


        DB_Acsess db_acsess = DB_Acsess.getInstans(this);
        db_acsess.open();
        List<Payment> items = db_acsess.getAllPaymentReport(value, this);


        adapterListPayment = new AdapterListPayment(this, items);
        recyclerView.setAdapter(adapterListPayment);
        // Toast.makeText(this, "size" + items.size(), Toast.LENGTH_SHORT).show();
        // on item list clicked

        adapterListPayment.setOnItemClickListener(new AdapterListPayment.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Payment obj, int position) {
                Intent intent = new Intent(getApplicationContext(), show_Order_Info.class);

                intent.putExtra("id", obj.getOrder().getId());



                startActivity(intent);
                // Snackbar.make(parent_view, "Item " + obj.getCustomer().getPhone() + " clicked", Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    private void initToolbar(String type) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        switch (type) {
            case "Daily":
                getSupportActionBar().setTitle("روزانه ");
                break;
            case "Weekly":
                getSupportActionBar().setTitle("هفتانه ");
                break;
            case "Monthly":
                getSupportActionBar().setTitle("ماهانه ");
                break;
            case "Yearly":
                getSupportActionBar().setTitle("سالانه ");
                break;
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_report, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {

            startActivity(new Intent(this,DashboradReportActivity.class));
            finish();
        } else if (item.getItemId() == R.id.app_bar_delete_all) {
            //  Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
            showAlertDialog();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAlertDialog() {
        // Toast.makeText(ReportActivity.this, "Value : "+value, Toast.LENGTH_SHORT).show();
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("آیا میخواهید گزارش ها را پاک کنید ؟");
        builder.setPositiveButton("بلی", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DB_Acsess db_acsess = DB_Acsess.getInstans(ReportActivity.this);
                db_acsess.open();

                boolean result = db_acsess.deletePaymentByType(value);
                // Toast.makeText(ReportActivity.this, "Result : "+result, Toast.LENGTH_SHORT).show();
                if (result == true) {
                    Toast.makeText(ReportActivity.this, "گزارش ها موفقانه حذف شد", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ReportActivity.this, DashboradReportActivity.class));
                    finish();
                } else {
                    Toast.makeText(ReportActivity.this, "گزارش ها حذف نشد", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("نخیر", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });
        builder.show();
    }

    @Override
    public void onBackPressed() {

    }
}
