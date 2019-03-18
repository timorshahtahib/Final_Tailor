package com.hmdapp.finaltailor.Activity.Customer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hmdapp.finaltailor.Models.Customer;
import com.hmdapp.finaltailor.R;
import com.hmdapp.finaltailor.database.DB_Acsess;

public class Regester_Customer extends AppCompatActivity {

    private String full_name, job, phone;
    private EditText ed_f_name, ed_job, ed_phone;
    Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regester__customer);
        customer = new Customer();

        ed_f_name = findViewById(R.id.ed_f_name);
        ed_job = findViewById(R.id.ed_job);
        ed_phone = findViewById(R.id.ed_phone);


        final int state = getIntent().getIntExtra("state", 0);


        initToolbar();

        if (state == 1) {
            setToEdit();
        }
        Button btn = findViewById(R.id.btn_insert_customer);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Validate()) {
                    setproperty();

                    if (state == 1) {


                        int id_per = getIntent().getIntExtra("id_cu", 1);

                        customer.setId(id_per);
                        update(customer);
                        finish();
                    } else {
                        if (insert(customer)) {
                            Toast.makeText(Regester_Customer.this, "added ", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Person_Activity.class));
                            finish();
                        } else {
                            Toast.makeText(Regester_Customer.this, "not aded", Toast.LENGTH_SHORT).show();
                        }

                    }
//                    Toast.makeText(Regester_cloth_Activity.this, "id" + getRgText(rg_model), Toast.LENGTH_SHORT).show();


                }
            }
        });
    }


    public void setToEdit() {

        DB_Acsess db_acsess = DB_Acsess.getInstans(this);
        db_acsess.open();

        int id_per = getIntent().getIntExtra("id_cu", 1);

        Customer cu = db_acsess.getCustomer(id_per);
        ed_f_name.setText(cu.getName() + "");
        ed_phone.setText(cu.getPhone() + "");
        ed_job.setText(cu.getJob() + "");
    }

    public boolean Validate() {
        full_name = ed_f_name.getText().toString();
        job = ed_job.getText().toString();
        phone = ed_phone.getText().toString();


        boolean s = true;


        if (TextUtils.isEmpty(full_name.trim())) {
            Toast.makeText(this, " نام وتخلص را وارد کنید !", Toast.LENGTH_SHORT).show();
            s = false;
        }
        if (TextUtils.isEmpty(job.trim())) {
            Toast.makeText(this, " وظیفه  را وارد کنید !", Toast.LENGTH_SHORT).show();
            s = false;
        }
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, " شماره   را وارد کنید !", Toast.LENGTH_SHORT).show();
            s = false;
        }

        return s;
    }


    public void setproperty() {

        customer.setName(full_name);
        customer.setPhone(phone);
        customer.setJob(job);
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("اضافه کردن مشتری");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    private void update(Customer cu) {
        DB_Acsess db_acsess = DB_Acsess.getInstans(this);
        db_acsess.open();

        if (db_acsess.updatePerson(cu) > 0) {
            Toast.makeText(this, "person updated", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, Person_Activity.class));
            finish();
        } else {
            Toast.makeText(this, " not person updated", Toast.LENGTH_SHORT).show();
            finish();
        }

    }


    public boolean insert(Customer cu) {
        DB_Acsess db_acsess = DB_Acsess.getInstans(getApplicationContext());
        db_acsess.open();
        long t = db_acsess.insert_customer(cu);

        return t > 0;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.empety_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(this,Person_Activity.class));
            finish();
        }else{

        }

            // Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show()


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        startActivity(new Intent(this,Person_Activity.class));
        finish();

    }
}
