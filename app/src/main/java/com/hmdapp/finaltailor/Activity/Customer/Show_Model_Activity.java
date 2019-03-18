package com.hmdapp.finaltailor.Activity.Customer;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hmdapp.finaltailor.Adapter.AdapterGridSingleLine;
import com.hmdapp.finaltailor.Models.Cloth;
import com.hmdapp.finaltailor.Models.Order;
import com.hmdapp.finaltailor.Models.Payment;
import com.hmdapp.finaltailor.R;
import com.hmdapp.finaltailor.Utlity.Tools;
import com.hmdapp.finaltailor.database.DB_Acsess;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class Show_Model_Activity extends AppCompatActivity {

    String dliverDate;
    TextView txt_show_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__model_);

        DB_Acsess db_acsess = DB_Acsess.getInstans(this);
        db_acsess.open();
        final int id_cl = getIntent().getIntExtra("id_cl", 0);
        final int id_cu = getIntent().getIntExtra("id_cu", 0);
        Cloth cl = db_acsess.getCloth(id_cu, id_cl);

        String[] names = {"قد", "شانه", "بغل ", "شلوار", "برشلوار", "دم پاچه ", "آستین ", "دم آستین ",
                "مودل آستین", "یخن", "مودل دم آستین ", "قد پتی", "مودل", "دامن", "قوت آستین", "مودل یقه"};
        Object[] value = {cl.getQad(), cl.getShana(), cl.getBaqal(), cl.getShalwar(), cl.getBar_shalwar(),

                cl.getPacha(), cl.getAstin(), cl.getDam_astin(), cl.getModel_astin(),
                cl.getYakhan(), cl.getModel_dam_astin(), cl.getQad_paty(), cl.getModel(),
                cl.getDaman(), cl.getModel_qot_astin(), cl.getModel_yaqa()};
//
        RecyclerView recyclerView = findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
     //   recyclerView.addItemDecoration(new SpacingItemDecoration(3, Tools.dpToPx(this, 3), true));
        recyclerView.setHasFixedSize(true);

        AdapterGridSingleLine adapterGridSingleLine = new AdapterGridSingleLine(this, names, value);

        recyclerView.setAdapter(adapterGridSingleLine);

        initToolbar();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.include);
        String name = getIntent().getStringExtra("date");

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.action_edit) {
            int id_cu = getIntent().getIntExtra("id_cu", 0);
            int id_cl = getIntent().getIntExtra("id_cl", 0);
            Intent intent = new Intent(getApplicationContext(), Regester_cloth_Activity.class);
            intent.putExtra("id_cu", id_cu);
            intent.putExtra("id_cl", id_cl);
            intent.putExtra("state", 1);
            startActivity(intent);
            finish();
            // Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.action_Delete) {
            showAlertDialog();
            //  Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.action_cart) {
            showCustomDialog_to_cart();
        }
        return super.onOptionsItemSelected(item);
    }


    private void showAlertDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("آیا میخواهید لباس را پاک کنید ؟");
        builder.setPositiveButton("بلی", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                int id_cl = getIntent().getIntExtra("id_cl", 0);

                delete(id_cl);
                //Toast.makeText(Profile_Activity.this, "بلی", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("نخیر", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Toast.makeText(Profile_Activity.this, "نخیر", Toast.LENGTH_SHORT).show();
                dialogInterface.dismiss();

            }
        });
        builder.show();
    }

    private void delete(int cl) {
        DB_Acsess db_acsess = DB_Acsess.getInstans(getApplicationContext());
        boolean t = db_acsess.delete_cloth(cl);

        if (t) {
            Toast.makeText(this, "حذف شد", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(this, Profile_Activity.class));
            finish();
        } else {
            Toast.makeText(this, "حذف نشد", Toast.LENGTH_SHORT).show();
        }
    }

    private void showCustomDialog_to_cart() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_add_review);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        final EditText ed_mony, ed_payment, edColor, edCount;

        ed_mony = dialog.findViewById(R.id.txt_mony);
        ed_payment = dialog.findViewById(R.id.txt_pay);
        txt_show_date = dialog.findViewById(R.id.deliver_date);
        edColor = dialog.findViewById(R.id.txt_color);
        edCount = dialog.findViewById(R.id.txt_count);

        txt_show_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDatePickerDark();
                //Toast.makeText(Profile_Activity.this,"Instead of date picker",Toast.LENGTH_LONG).show();
            }
        });

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

                String cuName = getIntent().getStringExtra("date");


                String review = txt_show_date.getText().toString().trim();
                float price = Float.parseFloat(ed_mony.getText().toString());
                float payment=0;
                try{
                   payment = Float.parseFloat(ed_payment.getText().toString());

                }catch (Exception e){
                  payment = 0;

                }
                String color = edColor.getText().toString();
                int count = Integer.parseInt(edCount.getText().toString());


                if (review.isEmpty()) {

                    Toast.makeText(getApplicationContext(), "لطفا تاریخ  تحویل را وارد کنید", Toast.LENGTH_SHORT).show();
                } else if (price < 50) {
                    Toast.makeText(getApplicationContext(), "لطفا قیمت دوخت را وارد کنید", Toast.LENGTH_SHORT).show();


                } else if (color.trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "لطفا رنگ را وارد کنید", Toast.LENGTH_SHORT).show();

                } else if (count <= 0) {
                    Toast.makeText(getApplicationContext(), "لطفا تعداد جوره لباس را وارد کنید", Toast.LENGTH_SHORT).show();

                } else {

                    Order order = new Order();


                    float totalPrice = price * count;
                    long time = System.currentTimeMillis();
                   String regDate = Tools.getFormattedDateSimple(time);
                   // String regDate = android.text.format.DateFormat.format("yyy-mm-dd", new Date(time)).toString();

                    order.setCount(count);
                    order.setColor(color);
                    order.setCom_state(0);
                    order.setPrice(totalPrice);

                    order.setOrder_Date(regDate);
                    order.setDeliverDate(dliverDate);

                    final int id_cl = getIntent().getIntExtra("id_cl", 0);
                    final int id_cu = getIntent().getIntExtra("id_cu", 0);
                    Cloth cl = db_acsess.getCloth(id_cu, id_cl);
                    order.setCloth(cl);
                    Payment payment_ = new Payment();

                        payment_.setAmount((int) payment);
                        payment_.setDate(regDate);
                        payment_.setOrder(order);
                        payment_.setDes("p");

                    //Toast.makeText(getApplicationContext(), "Hi "+regDate, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    addTask(order, payment_);
                }

            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    private void addTask(Order order, Payment payment_) {

        DB_Acsess db_acsess = DB_Acsess.getInstans(this);
        db_acsess.open();
        long s = db_acsess.add_task(order, payment_);

        if (s > 0) {
            Toast.makeText(this, "اضافه شد", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, " اضافه نشد", Toast.LENGTH_SHORT).show();

        }
    }

    private void dialogDatePickerDark() {
        Calendar cur_calender = Calendar.getInstance();

        DatePickerDialog datePicker = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        long date_ship_millis = calendar.getTimeInMillis();
                        // ((TextView) findViewById(R.id.result)).setText(Tools.getFormattedDateSimple(date_ship_millis));

                        // long date = new Date(date_ship_millis).getTime();
                      //  String dd = android.text.format.DateFormat.format("yyy-mm-dd", new Date(date_ship_millis)).toString();
                       // dliverDate = dd + "";
                         dliverDate=Tools.getFormattedDateSimple(date_ship_millis);
                        //Toast.makeText(Show_Model_Activity.this, "" + d, Toast.LENGTH_SHORT).show();
                        txt_show_date.setText("" + dliverDate);

                        // Toast.makeText(Profile_Activity.this, date, Toast.LENGTH_SHORT).show();

                    }
                },
                cur_calender.get(Calendar.YEAR),
                cur_calender.get(Calendar.MONTH),
                cur_calender.get(Calendar.DAY_OF_MONTH)
        );


        //set dark theme
       // datePicker.setThemeDark(true);
        datePicker.setAccentColor(getResources().getColor(R.color.colorPrimary));
        datePicker.setMinDate(cur_calender);
        datePicker.show(getFragmentManager(), "Datepickerdialog");
        // datePicker.show(getFragmentManager(),"Datepickerdialog");


    }

}
