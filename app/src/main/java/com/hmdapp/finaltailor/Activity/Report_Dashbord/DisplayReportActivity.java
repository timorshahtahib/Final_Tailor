package com.hmdapp.finaltailor.Activity.Report_Dashbord;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hmdapp.finaltailor.Models.Customer;
import com.hmdapp.finaltailor.Models.Payment;
import com.hmdapp.finaltailor.Models.Order;
import com.hmdapp.finaltailor.R;
import com.hmdapp.finaltailor.Utlity.Tools;
import com.hmdapp.finaltailor.database.DB_Acsess;


public class DisplayReportActivity extends AppCompatActivity {

    private TextView customerName, customerJob, deliveryDate,tvColor,
            tvCount,tvPrice,tvPayment,tvRemainder,tvRegDate;
    private CheckBox checkState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_report);
        initToolbar();
        setUpViews();
        initViews();
    }
    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_display_report);
        String name = getIntent().getStringExtra("date");

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void initViews(){
        DB_Acsess db_acsess = DB_Acsess.getInstans(this);
        db_acsess.open();
        int id = getIntent().getIntExtra("id", 1);
        int customerId = getIntent().getIntExtra("cu_id", 1);
        String deliverCuDate = getIntent().getStringExtra("deliver_date");
        int state = getIntent().getIntExtra("state",0);

        Customer customer = db_acsess.getCustomer(customerId);

        customerName.setText(customer.getName());
        customerJob.setText(customer.getJob());
        //Toast.makeText(show_Order_Info.this,""+customer.getPhone(),Toast.LENGTH_LONG).show();

        deliveryDate.setText(deliverCuDate);
        tvColor.setText(getIntent().getStringExtra("color"));
        tvCount.setText(getIntent().getIntExtra("count",1)+"");
        tvPrice.setText((Float) getIntent().getFloatExtra("price",1)+"");
        tvPayment.setText((Float) getIntent().getFloatExtra("payment", 1)+"");
        tvRemainder.setText((Float) getIntent().getFloatExtra("remainder", 1)+"");
        tvRegDate.setText(getIntent().getStringExtra("reg_date"));

        if (state == 0){
            checkState.setChecked(false);
        }else{
            checkState.setChecked(true);
        }
//        checkState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                DB_Acsess db_acsess = DB_Acsess.getInstans(DisplayReportActivity.this);
//                db_acsess.open();
//                int id = getIntent().getIntExtra("id", 1);
//                Order task = new Order();
//                Payment  payment = new Payment();
//
//                task.setId(id);
//                payment.setId(id);
//                if(isChecked){
//                    task.setState(1);
//                    payment.setState(1);
//                    db_acsess.updateTask(DisplayReportActivity.this,task,payment);
//                    // Toast.makeText(show_Order_Info.this, "True = 1", Toast.LENGTH_LONG).show();
//                    finish();
//                }else {
//                    task.setState(0);
//                    payment.setState(0);
//                    db_acsess.updateTask(DisplayReportActivity.this,task,payment);
//                    finish();
//                    //  Toast.makeText(show_Order_Info.this,"false = 0", Toast.LENGTH_LONG).show();
//                }
//
//            }
//        });



    }

    private void setUpViews(){
        customerName = findViewById(R.id.task_customer_name_display_report_top);
        customerJob = findViewById(R.id.job_display_report);
        deliveryDate = findViewById(R.id.deliver_date_display_report);
        tvColor = findViewById(R.id.color_display_report);
        tvCount = findViewById(R.id.count_display_report);
        tvPrice = findViewById(R.id.price_state_display_report);
        tvPayment = findViewById(R.id.payment_display_report);
        tvRemainder = findViewById(R.id.remainder_display_report);
        tvRegDate = findViewById(R.id.reg_date_display_report);
        checkState =  findViewById(R.id.check_state_display_report);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_task, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(this,DashboradReportActivity.class));
            finish();


        } else if (item.getItemId() == R.id.action_pay_task) {

            customerPayment();

            // Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.action_Delete_task) {
            showAlertDialog();
            //  Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();

        }

        return super.onOptionsItemSelected(item);
    }


    private void showAlertDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("آیا میخواهید گزارش را پاک کنید ؟");
        builder.setPositiveButton("بلی", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DB_Acsess db_acsess = DB_Acsess.getInstans(DisplayReportActivity.this);
                db_acsess.open();
                int idPayment = getIntent().getIntExtra("id", 1);
                //Toast.makeText(show_Order_Info.this, "ID : "+taskId, Toast.LENGTH_SHORT).show();


                if(db_acsess.deletePayment(idPayment) == true){
                    Toast.makeText(DisplayReportActivity.this, "گزارش موفقانه حذف شد", Toast.LENGTH_SHORT).show();

                   // startActivity(new Intent(DisplayReportActivity.this,ReportActivity.class));
                    finish();
                }else{
                    Toast.makeText(DisplayReportActivity.this, "گزارش حذف نشد", Toast.LENGTH_SHORT).show();
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
                DB_Acsess db_acsess = DB_Acsess.getInstans(DisplayReportActivity.this);
                db_acsess.open();

                // String cuName = getIntent().getStringExtra("date");

                float remainder = Float.parseFloat(tvRemainder.getText().toString());
                float price = Float.parseFloat(tvPrice.getText().toString());
                float payment = Float.parseFloat(tvPayment.getText().toString());
                float valuePayment = price;

                float value = Float.parseFloat(edPayment.getText().toString());


                //Toast.makeText(getApplicationContext(), "Price : "+price+" , Payment : "+payment +" , Value : "+value, Toast.LENGTH_SHORT).show();
                if (remainder == value) {

                    int payId = getIntent().getIntExtra("id", 1);
                    int taskId = getIntent().getIntExtra("task_id", 1);
                    Order order = new Order();
                    Payment payment_ = new Payment();
                   // Customer customer = new Customer();
                    order.setId(taskId);


                    payment_.setId(payId);


                    db_acsess.updateTaskPaymentReport(DisplayReportActivity.this, order,payment_);

                    //  Toast.makeText(getApplicationContext(), "Hi "+regDate, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();

                   //  startActivity(new Intent(DisplayReportActivity.this,ReportActivity.class));
                    finish();


                }else if(remainder == 0 ){
                    Toast.makeText(getApplicationContext(), "پرداخت از قبل تکمیل شده است "+"\n   دکمه لغو را فشار دهید   ", Toast.LENGTH_SHORT).show();
                }

                else {

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

