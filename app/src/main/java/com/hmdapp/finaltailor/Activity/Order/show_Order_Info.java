package com.hmdapp.finaltailor.Activity.Order;

import android.app.Dialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hmdapp.finaltailor.Models.Cloth;
import com.hmdapp.finaltailor.Models.Customer;
import com.hmdapp.finaltailor.Models.Order;
import com.hmdapp.finaltailor.Models.Payment;
import com.hmdapp.finaltailor.R;
import com.hmdapp.finaltailor.Utlity.Tools;
import com.hmdapp.finaltailor.database.DB_Acsess;


public class show_Order_Info extends AppCompatActivity {

    private TextView customerName, customerJob, deliveryDate, tvColor,
            tvCount, tvPrice, tvPayment, tvRemainder, tvRegDate;
    private CheckBox checkState, checkIsExist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_order_info);
        initToolbar();
        setUpViews();
        initViews();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_payment);


        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void initViews() {
        DB_Acsess db_acsess = DB_Acsess.getInstans(this);
        db_acsess.open();
        int id = getIntent().getIntExtra("id", 1);


        Order order = db_acsess.getOrder(id);
        Cloth cloth = order.getCloth();
        Customer customer = cloth.getCustomer();

        Log.d("order_id","id:  "+id);
        Payment payment = null;
        try {
            payment = db_acsess.getPayment(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d("Payment",payment.toString());

        getSupportActionBar().setTitle(customer.getName());

        customerName.setText(order.getCloth().getCustomer().getName());
        customerJob.setText(customer.getJob());
        //Toast.makeText(show_Order_Info.this,""+customer.getPhone(),Toast.LENGTH_LONG).show();

        deliveryDate.setText(order.getDeliverDate());
        tvColor.setText(order.getColor());
        tvCount.setText(order.getCount() + "");
        tvPrice.setText(order.getPrice() + "");
        try {
            tvPayment.setText(payment.getPish_pardakht() + "");
            tvRemainder.setText(payment.getReminder() + "");
        }catch (Exception e){
            e.printStackTrace();
        }

        tvRegDate.setText(order.getOrder_Date());
        int state = order.getCom_state();
        if (state == 0) {
            checkState.setChecked(false);
        } else {
            checkState.setChecked(true);
        }
        int stateIsExist = order.getIsExist();
        if (stateIsExist == 0) {
            checkIsExist.setChecked(false);
        } else {
            checkIsExist.setChecked(true);
        }
        checkState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DB_Acsess db_acsess = DB_Acsess.getInstans(show_Order_Info.this);
                db_acsess.open();
                int id = getIntent().getIntExtra("id", 1);
                Order order = new Order();
                Payment payment = new Payment();

                order.setId(id);
                payment.setOrder(order);
                if (isChecked) {
                    order.setCom_state(1);

                    db_acsess.updateTask( order);
                    onsend_sms();
                    // Toast.makeText(show_Order_Info.this, "True = 1", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(show_Order_Info.this, Tasks_Activity.class));
                    finish();
                } else {
                    order.setCom_state(0);

                    db_acsess.updateTask(order);
                    startActivity(new Intent(show_Order_Info.this, Tasks_Activity.class));
                    finish();
                    //  Toast.makeText(show_Order_Info.this,"false = 0", Toast.LENGTH_LONG).show();
                }

            }
        });

        checkIsExist.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DB_Acsess db_acsess = DB_Acsess.getInstans(show_Order_Info.this);
                db_acsess.open();
                int id = getIntent().getIntExtra("id", 1);
                Order order = new Order();
                order.setId(id);
                if (isChecked) {
                    order.setIsExist(1);
                    db_acsess.updateTaskClothState(order);
                    // Toast.makeText(show_Order_Info.this, "True = 1", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(show_Order_Info.this, Tasks_Activity.class));
                    finish();
                } else {
                    order.setIsExist(0);
                    db_acsess.updateTaskClothState( order);
                    startActivity(new Intent(show_Order_Info.this, Tasks_Activity.class));
                    finish();
                    //  Toast.makeText(show_Order_Info.this,"false = 0", Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    private void setUpViews() {
        customerName = findViewById(R.id.task_customer_name_payment_top);
        customerJob = findViewById(R.id.task_job_payment);
        deliveryDate = findViewById(R.id.task_date_payment);
        tvColor = findViewById(R.id.task_color_payment);
        tvCount = findViewById(R.id.task_count_payment);
        tvPrice = findViewById(R.id.task_price_state);
        tvPayment = findViewById(R.id.task_payment_payment);
        tvRemainder = findViewById(R.id.task_remainder_pay);
        tvRegDate = findViewById(R.id.task_reg_date_payment);
        checkState = findViewById(R.id.task_check_state);
        checkIsExist = findViewById(R.id.task_check_is_exist);
        tvPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x=Integer.parseInt(tvPayment.getText().toString());
                if(x==0){

                }else{

                    Intent intent=new Intent(getApplicationContext(),Show_Payment_of_order.class);
                    int remainder = Integer.parseInt(tvRemainder.getText().toString());
                    intent.putExtra("reminder",remainder);
                    int id = getIntent().getIntExtra("id", 0);
                    intent.putExtra("order_id",id);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_task, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {

            startActivity(new Intent(this,Tasks_Activity.class));
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
        builder.setTitle("آیا میخواهید تسک را پاک کنید ؟");
        builder.setPositiveButton("بلی", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DB_Acsess db_acsess = DB_Acsess.getInstans(show_Order_Info.this);
                db_acsess.open();
                int taskId = getIntent().getIntExtra("id", 1);
                //Toast.makeText(show_Order_Info.this, "ID : "+taskId, Toast.LENGTH_SHORT).show();


                if (db_acsess.deleteTask(taskId) == true) {
                    Toast.makeText(show_Order_Info.this, "تسک موفقانه حذف شد", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(show_Order_Info.this, Tasks_Activity.class));
                    finish();
                } else {
                    Toast.makeText(show_Order_Info.this, "تسک حذف نشد", Toast.LENGTH_SHORT).show();
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
                DB_Acsess db_acsess = DB_Acsess.getInstans(show_Order_Info.this);
                db_acsess.open();

                // String cuName = getIntent().getStringExtra("date");

                float remainder = Float.parseFloat(tvRemainder.getText().toString());



               int value = Integer.parseInt(edPayment.getText().toString());


                //Toast.makeText(getApplicationContext(), "Price : "+price+" , Payment : "+payment +" , Value : "+value, Toast.LENGTH_SHORT).show();

                if ( value-remainder<=0) {

                    int taskId = getIntent().getIntExtra("id", 1);
                    Order order = new Order();
                    Payment payment_ = new Payment();

                    order.setId(taskId);

                    payment_.setOrder(order);
                    payment_.setAmount(value);

                    long time = System.currentTimeMillis();
                    String regDate = Tools.getFormattedDateSimple(time);
                    payment_.setDate(regDate);
                    db_acsess.Add_Rasid( payment_);

                  // Toast.makeText(getApplicationContext(), "Hi "+regDate, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();

                    startActivity(new Intent(show_Order_Info.this, Tasks_Activity.class));
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

    public void caa(View view) {

        String phon = getIntent().getStringExtra("phone");

        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+phon));//change the number

        try{
            startActivity(callIntent);
        }

        catch (android.content.ActivityNotFoundException ex){

            ex.printStackTrace();
            Toast.makeText(getApplicationContext(),"yourActivity is not founded",Toast.LENGTH_SHORT).show();
        }
    }

    public void onsend_sms() {
        String phon = getIntent().getStringExtra("phone");



        //Get the SmsManager instance and call the sendTextMessage method to send message
        SmsManager sms=SmsManager.getDefault();
        sms.sendTextMessage(phon, null, "سلام مشتری عزیز وقت بخیر لباس شما دوخته شده است", null,null);

        Toast.makeText(getApplicationContext(), "Message Sent successfully!",
                Toast.LENGTH_LONG).show();
    }
}
