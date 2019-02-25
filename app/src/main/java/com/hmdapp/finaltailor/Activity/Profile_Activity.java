package com.hmdapp.finaltailor.Activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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
import com.hmdapp.finaltailor.Models.Customer;
import com.hmdapp.finaltailor.Models.Payment;
import com.hmdapp.finaltailor.Models.Task;
import com.hmdapp.finaltailor.R;
import com.hmdapp.finaltailor.Utlity.SpacingItemDecoration;
import com.hmdapp.finaltailor.Utlity.Tools;
import com.hmdapp.finaltailor.database.DB_Acsess;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;


import java.util.Calendar;

public class Profile_Activity extends AppCompatActivity {

    private TextView tx_f_name, tx_job, tx_phone;

    private TextView tx_qad, tx_shana, tx_baqal, tx_daman, tx_shalwar,
            tx_pacha, tx_bar_shalwar, tx_model, tx_model_dam_astin,
            tx_model_qot_astin, tx_qad_paty, tx_astin, tx_yakhan,
            tx_model_yaqa, tx_dam_astin, tx_model_astin;
    String dliverDate;
    TextView txt_show_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_test);
        initToolbar();
        initComponent_t();


    }

    private void initComponent_t() {
        DB_Acsess db_acsess = DB_Acsess.getInstans(this);
        db_acsess.open();
        int id = getIntent().getIntExtra("id", 1);
        String name = getIntent().getStringExtra("name");
        String phon = getIntent().getStringExtra("phone");
        String job = getIntent().getStringExtra("job");
        tx_f_name = findViewById(R.id.tx_f_name);
        tx_job = findViewById(R.id.tx_job);
        tx_f_name.setText(name);
        tx_job.setText(job);


        Cloth cl = db_acsess.getCloth(id);

        String[] names = {"قد", "شانه", "بغل ", "شلوار", "برشلوار", "دم پاچه ", "آستین ", "دم آستین ",
                "مودل آستین", "یخن", "مودل دم آستین ", "قد پتی", "مودل", "دامن", "قوت آستین", "مودل یقه"};
        Object[] value = {cl.getQad(), cl.getShana(), cl.getBaqal(), cl.getShalwar(), cl.getBar_shalwar(),

                cl.getPacha(), cl.getAstin(), cl.getDam_astin(), cl.getModel_astin(),
                cl.getYakhan(), cl.getModel_dam_astin(), cl.getQad_paty(), cl.getModel(),
                cl.getDaman(), cl.getModel_qot_astin(), cl.getModel_yaqa()};

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.addItemDecoration(new SpacingItemDecoration(3, Tools.dpToPx(this, 3), true));
        recyclerView.setHasFixedSize(true);

        AdapterGridSingleLine mAdapter = new AdapterGridSingleLine(this, names, value);
        recyclerView.setAdapter(mAdapter);

        // on item list clicked


    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        String name = getIntent().getStringExtra("name");

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, R.color.purple_600);
    }

    private void initComponent() {
        DB_Acsess db_acsess = DB_Acsess.getInstans(this);
        db_acsess.open();
        int id = getIntent().getIntExtra("id", 1);
        String name = getIntent().getStringExtra("name");
        String phon = getIntent().getStringExtra("phone");
        String job = getIntent().getStringExtra("job");
        tx_f_name = findViewById(R.id.tx_f_name);
        tx_job = findViewById(R.id.tx_job);
        tx_f_name.setText(name);
        tx_job.setText(job);


        tx_qad = findViewById(R.id.tx_qad);
        tx_shana = findViewById(R.id.tx_shana);
        tx_baqal = findViewById(R.id.tx_baqal);
        tx_shalwar = findViewById(R.id.tx_shalwar);
        tx_bar_shalwar = findViewById(R.id.tx_bar_shalwar);
        tx_pacha = findViewById(R.id.tx_dam_pacha);
        tx_astin = findViewById(R.id.tx_astin);
        tx_dam_astin = findViewById(R.id.tx_dam_astin);
        tx_model_astin = findViewById(R.id.tx_astin_model);
        tx_model_dam_astin = findViewById(R.id.tx_model_dam_astin);
        tx_yakhan = findViewById(R.id.tx_yakhan);
        tx_qad_paty = findViewById(R.id.tx_qad_paty);
        tx_model = findViewById(R.id.tx_model);
        tx_daman = findViewById(R.id.tx_daman);
        tx_model_qot_astin = findViewById(R.id.tx_qot_astin);
        tx_model_yaqa = findViewById(R.id.tx_model_yaqa);


        Cloth cl = db_acsess.getCloth(id);
        tx_qad.setText(cl.getQad() + "  cm");
        tx_shana.setText(cl.getShana() + "  cm");
        tx_baqal.setText(cl.getBaqal() + "  cm");
        tx_shalwar.setText(cl.getShalwar() + "  cm");
        tx_bar_shalwar.setText(cl.getBar_shalwar() + "  cm");
        tx_pacha.setText(cl.getPacha() + "  cm");
        tx_astin.setText(cl.getAstin() + "  cm");
        tx_dam_astin.setText(cl.getDam_astin() + "  cm");
        tx_model_astin.setText(cl.getModel_astin());
        tx_model_dam_astin.setText(cl.getModel_dam_astin());
        tx_yakhan.setText(cl.getYakhan() + "  cm");
        tx_qad_paty.setText(cl.getQad_paty());
        tx_model.setText(cl.getModel());
        tx_daman.setText(cl.getDaman() + "  cm");
        tx_model_qot_astin.setText(cl.getModel_qot_astin());
        tx_model_yaqa.setText(cl.getModel_yaqa());


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
            int id = getIntent().getIntExtra("id", 1);
            int id_cu = getIntent().getIntExtra("id_cu", 1);
            Intent intent = new Intent(getApplicationContext(), Regester_Activity.class);
            intent.putExtra("id_cl", id);
            intent.putExtra("id_cu", id_cu);
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
        builder.setTitle("آیا میخواهید مشتری را پاک کنید ؟");
        builder.setPositiveButton("بلی", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                int id_cl = getIntent().getIntExtra("id", 1);
                int id_cu = getIntent().getIntExtra("id_cu", 1);
                delete(id_cl, id_cu);
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

    private void delete(int cl, int cu) {
        DB_Acsess db_acsess = DB_Acsess.getInstans(getApplicationContext());
        boolean t = db_acsess.delete_cloth(cl);
        boolean r = db_acsess.delete_Cutomer(cu);
        if (t && r) {
            Toast.makeText(this, "حذف شد", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(this, Person_Activity.class));
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
        final EditText ed_mony, ed_payment,edColor,edCount;

        ed_mony = dialog.findViewById(R.id.txt_mony);
        ed_payment = dialog.findViewById(R.id.txt_pay);
        txt_show_date = dialog.findViewById(R.id.txt_date);
        edColor = dialog.findViewById(R.id.txt_color);
        edCount = dialog.findViewById(R.id.txt_count);

        txt_show_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDatePickerDark();
                //Toast.makeText(Profile_Activity.this,"Instead of date picker",Toast.LENGTH_LONG).show();
            }
        });

        ((AppCompatButton) dialog.findViewById(R.id.bt_cancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        ((AppCompatButton) dialog.findViewById(R.id.bt_submit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DB_Acsess db_acsess = DB_Acsess.getInstans(Profile_Activity.this);
                db_acsess.open();

                String cuName = getIntent().getStringExtra("name");


                String review = txt_show_date.getText().toString().trim();
                float price = Float.parseFloat(ed_mony.getText().toString());
                float payment = Float.parseFloat(ed_payment.getText().toString());
                String color = edColor.getText().toString();
                int count = Integer.parseInt(edCount.getText().toString());


                if (review.isEmpty()) {

                    Toast.makeText(getApplicationContext(), "لطفا تاریخ  تحویل را وارد کنید", Toast.LENGTH_SHORT).show();
                } else if (price < 50) {
                    Toast.makeText(getApplicationContext(), "لطفا قیمت دوخت را وارد کنید", Toast.LENGTH_SHORT).show();

                } else if (payment < 10) {
                    Toast.makeText(getApplicationContext(), "لطفا رسید را وارد کنید", Toast.LENGTH_SHORT).show();

                } else if (color.trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "لطفا رنگ را وارد کنید", Toast.LENGTH_SHORT).show();

                }
                else if (count <= 0) {
                    Toast.makeText(getApplicationContext(), "لطفا تعداد جوره لباس را وارد کنید", Toast.LENGTH_SHORT).show();

                }

                else {

                    int id_cu = getIntent().getIntExtra("id_cu", 1);
                    Task task = new Task();
                    Payment payment_ = new Payment();
                    float totalPrice = price * count;

                    Customer customer = new Customer();
                    long time = System.currentTimeMillis();
                    String regDate = Tools.getFormattedDateSimple(time);
                    customer.setId(id_cu);
                    customer.setName(cuName);
                    task.setCustomer(customer);
                    task.setCount(count);
                    task.setColor(color);
                    task.setState(0);
                    task.setPrice(totalPrice);
                    task.setPayment(payment);
                    task.setRemainder(totalPrice - payment);
                    task.setRegDate(regDate);
                    task.setDeliverDate(dliverDate);


                    payment_.setState(0);
                    payment_.setCount(count);
                    payment_.setColor(color);
                    payment_.setPrice(totalPrice);
                    payment_.setPayment(payment);
                    payment_.setRemainder(totalPrice - payment);
                    payment_.setRegDate(regDate);
                    payment_.setDeliverDate(dliverDate);
                    payment_.setCustomer(customer);
                    payment_.setTaskID(db_acsess.getLastId_task()+1);
                    payment_.setTask(task);
                    //Toast.makeText(getApplicationContext(), "Hi "+regDate, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    addTask(task, payment_);
                }

            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    private void addTask(Task task, Payment payment_) {

        DB_Acsess db_acsess = DB_Acsess.getInstans(this);
        db_acsess.open();
        long s = db_acsess.add_task(task, payment_);

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

                        dliverDate = Tools.getFormattedDateSimple(date_ship_millis) + "";
                        txt_show_date.setText(dliverDate);

                       // Toast.makeText(Profile_Activity.this, date, Toast.LENGTH_SHORT).show();

                    }
                },
                cur_calender.get(Calendar.YEAR),
                cur_calender.get(Calendar.MONTH),
                cur_calender.get(Calendar.DAY_OF_MONTH)
        );


        //set dark theme
        datePicker.setThemeDark(true);
        datePicker.setAccentColor(getResources().getColor(R.color.colorPrimary));
        datePicker.setMinDate(cur_calender);
        datePicker.show(getFragmentManager(), "Datepickerdialog");
        // datePicker.show(getFragmentManager(),"Datepickerdialog");


    }
}
