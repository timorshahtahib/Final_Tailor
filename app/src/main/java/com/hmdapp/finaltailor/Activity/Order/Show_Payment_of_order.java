package com.hmdapp.finaltailor.Activity.Order;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.hmdapp.finaltailor.Activity.MainActivity;
import com.hmdapp.finaltailor.Adapter.Adaptar_show_payes;
import com.hmdapp.finaltailor.Adapter.AdapterGridSingleLine;
import com.hmdapp.finaltailor.Models.Order;
import com.hmdapp.finaltailor.Models.Payment;
import com.hmdapp.finaltailor.R;
import com.hmdapp.finaltailor.Utlity.Tools;
import com.hmdapp.finaltailor.database.DB_Acsess;

import java.util.ArrayList;

public class Show_Payment_of_order extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__payment_of_order);
        initToolbar();
        intitcomponent();
    }

    private void intitcomponent() {

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        DB_Acsess db_acsess = DB_Acsess.getInstans(this);
        db_acsess.open();
        int order_id = getIntent().getIntExtra("order_id", 0);
        ArrayList<Payment> payments = db_acsess.getPayment_of_roder(order_id);

        Log.d("Order_id and size",order_id+"   size"+payments.size());
        Adaptar_show_payes adaptar_show_payes = new Adaptar_show_payes(this, payments);

        recyclerView.setAdapter(adaptar_show_payes);

    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("لیست پرداخت ها ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_payment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
Intent intent=new Intent(this, show_Order_Info.class);
            int order_id = getIntent().getIntExtra("order_id", 0);
           intent.putExtra("id",order_id);
          startActivity(intent);

            finish();

        } else if (item.getItemId() == R.id.action_pay) {
            customerPayment();
            //   Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }


    private void customerPayment() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_add_payment);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        final EditText edPayment;

        edPayment = dialog.findViewById(R.id.txt_payment_after_work);


        dialog.findViewById(R.id.bt_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.findViewById(R.id.bt_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DB_Acsess db_acsess = DB_Acsess.getInstans(getApplicationContext());
                db_acsess.open();

                // String cuName = getIntent().getStringExtra("date");

                float remainder = getIntent().getIntExtra("reminder", 0);


                int value = Integer.parseInt(edPayment.getText().toString());


                //Toast.makeText(getApplicationContext(), "Price : "+price+" , Payment : "+payment +" , Value : "+value, Toast.LENGTH_SHORT).show();
                if (value - remainder >= 0) {

                    int taskId = getIntent().getIntExtra("id", 1);
                    Order order = new Order();
                    Payment payment_ = new Payment();

                    order.setId(taskId);

                    payment_.setOrder(order);
                    payment_.setAmount(value);

                    long time = System.currentTimeMillis();
                    String regDate = Tools.getFormattedDateSimple(time);
                    payment_.setDate(regDate);
                    db_acsess.Add_Rasid(payment_);

                    // Toast.makeText(getApplicationContext(), "Hi "+regDate, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();

                    startActivity(new Intent(getApplicationContext(), Tasks_Activity.class));
                    finish();
                } else if (remainder == 0) {
                    Toast.makeText(getApplicationContext(), "پرداخت از قبل تکمیل شده است " + "\n   دکمه لغو را فشار دهید   ", Toast.LENGTH_SHORT).show();
                } else {

                    //Toast.makeText(show_Order_Info.this,"Remainder : "+tvRemainder.getText().toString() + " Price : "+tvPrice.getText().toString(),Toast.LENGTH_LONG).show();

                    // float price = Float.parseFloat(tvPrice.getText().toString());
                    Toast.makeText(getApplicationContext(), "مقدار پول باقی مانده را اشتباه وارد نمودید ", Toast.LENGTH_SHORT).show();

                }

            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }


}
