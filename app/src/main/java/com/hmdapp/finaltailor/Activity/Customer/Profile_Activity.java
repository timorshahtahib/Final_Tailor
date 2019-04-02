package com.hmdapp.finaltailor.Activity.Customer;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.hmdapp.finaltailor.Activity.Report_Dashbord.EveryRemainderActivity;
import com.hmdapp.finaltailor.Adapter.Adaptar_model;
import com.hmdapp.finaltailor.Models.Model;
import com.hmdapp.finaltailor.R;
import com.hmdapp.finaltailor.database.DB_Acsess;


import java.util.ArrayList;

public class Profile_Activity extends AppCompatActivity {

    private TextView tx_f_name, tx_job, tx_phone;

//    private TextView tx_qad, tx_shana, tx_baqal, tx_daman, tx_shalwar,
//            tx_pacha, tx_bar_shalwar, tx_model, tx_model_dam_astin,
//            tx_model_qot_astin, tx_qad_paty, tx_astin, tx_yakhan,
//            tx_model_yaqa, tx_dam_astin, tx_model_astin;
//    String dliverDate;
//    TextView txt_show_date;

    TextView reminder;
    View reminder_lyt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_test);
        initToolbar();
        initComponent_t();
        String phon = getIntent().getStringExtra("phone");

        Toast.makeText(this, phon, Toast.LENGTH_SHORT).show();

    }

    private void initComponent_t() {
        DB_Acsess db_acsess = DB_Acsess.getInstans(this);
        db_acsess.open();
        final int id_cu = getIntent().getIntExtra("id_cu", 0);
        String name = getIntent().getStringExtra("date");
        String job = getIntent().getStringExtra("job");
        tx_f_name = findViewById(R.id.tx_f_name);
        tx_job = findViewById(R.id.tx_job);
        tx_f_name.setText(name);
        tx_job.setText(job);
        FloatingActionButton fb = findViewById(R.id.floatingActionButton);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Regester_cloth_Activity.class);
                intent.putExtra("id_cu", id_cu);
                startActivity(intent);
                finish();
            }
        });
        int remind = db_acsess.getRemainderCuctomer(id_cu);


        reminder = findViewById(R.id.textView2);
        reminder_lyt = findViewById(R.id.reminder_lyt);
        if (remind > 0) {
            reminder.setText("" + remind);

            reminder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), EveryRemainderActivity.class);
                    intent.putExtra("id", id_cu); 
                    startActivity(intent);
                }
            });
        } else {
            reminder_lyt.setVisibility(View.INVISIBLE);
        }


        ////Cloth cl = db_acsess.getCloth(id_cu);

//        String[] names = {"قد", "شانه", "بغل ", "شلوار", "برشلوار", "دم پاچه ", "آستین ", "دم آستین ",
//                "مودل آستین", "یخن", "مودل دم آستین ", "قد پتی", "مودل", "دامن", "قوت آستین", "مودل یقه"};
//        Object[] value = {cl.getQad(), cl.getShana(), cl.getBaqal(), cl.getShalwar(), cl.getBar_shalwar(),
//
//                cl.getPacha(), cl.getAstin(), cl.getDam_astin(), cl.getModel_astin(),
//                cl.getYakhan(), cl.getModel_dam_astin(), cl.getQad_paty(), cl.getModel(),
//                cl.getDaman(), cl.getModel_qot_astin(), cl.getModel_yaqa()};
//
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //recyclerView.addItemDecoration(new SpacingItemDecoration(3, Tools.dpToPx(this, 3), true));
        recyclerView.setHasFixedSize(true);


        Log.d("customer_id", id_cu + "");
        ArrayList<Model> arrayList = db_acsess.get_Model_cloth(id_cu);
        Adaptar_model mAdapter = new Adaptar_model(this, arrayList);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new Adaptar_model.OnItemClickListener() {

            @Override
            public void onItemClick(View view, Model obj, int position) {
                Intent intent = new Intent(getApplicationContext(), Show_Model_Activity.class);

                intent.putExtra("id_cl", obj.getId());
                intent.putExtra("id_cu", id_cu);
                intent.putExtra("date", obj.getName());
                startActivity(intent);
                finish();
            }
        });

// on item list clicked


    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        String name = getIntent().getStringExtra("date");

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile_2, menu);
        //getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();

        } else if (item.getItemId() == R.id.action_edit) {

            int id_cu = getIntent().getIntExtra("id_cu", 1);
            Intent intent = new Intent(getApplicationContext(), Regester_Customer.class);

            intent.putExtra("id_cu", id_cu);
            intent.putExtra("state", 1);
            startActivity(intent);
            finish();

            // Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.action_Delete) {
            showAlertDialog();
            //  Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();

        } else {

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

        boolean r = db_acsess.delete_Cutomer(cu);
        if (r) {
            Toast.makeText(this, "حذف شد", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(this, Person_Activity.class));
            finish();
        } else {
            Toast.makeText(this, "حذف نشد", Toast.LENGTH_SHORT).show();
        }
    }


    public void call(View view) {
        String phon = getIntent().getStringExtra("phone");

        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + phon));//change the number

        try {
            startActivity(callIntent);
        } catch (android.content.ActivityNotFoundException ex) {

            ex.printStackTrace();
            Toast.makeText(getApplicationContext(), "yourActivity is not founded", Toast.LENGTH_SHORT).show();
        }
    }

    public void onsendsms(View view) {


        String phon = getIntent().getStringExtra("phone");


        Intent it = new Intent(Intent.ACTION_SEND);
        it.setType("text/plain");


        it.putExtra("address", phon);
        startActivity(it);


    }
}
