package com.hmdapp.finaltailor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.hmdapp.finaltailor.database.LocalData;


public class Seting_Activity extends AppCompatActivity {
    LocalData localData;
    CardView cardView_reply;
    CardView cardView_send;
  //  CheckBox ch_r_rep, ch_et_rep, ch_sa_rep, ch_mtn_rep, ch_awc_rep;
    CheckBox ch_r_con, ch_et_con, ch_sa_con, ch_mtn_con, ch_awc_con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seting);
        localData = new LocalData(this);
        initToolbar();
        initComponenet();
    }

    private void initComponenet() {


//        ch_r_rep = findViewById(R.id.ch_send_roshan);
//        ch_et_rep = findViewById(R.id.ch_send_etisalat);
//        ch_sa_rep = findViewById(R.id.ch_send_salam);
//        ch_mtn_rep = findViewById(R.id.ch_send_mtn);
//        ch_awc_rep = findViewById(R.id.ch_send_awc);

//
//        ch_r_rep.setChecked(localData.get_rosahn_reply());
//        ch_et_rep.setChecked(localData.get_etisalat_reply());
//        ch_sa_rep.setChecked(localData.get_salam_reply());
//        ch_mtn_rep.setChecked(localData.get_mtn_reply());
//        ch_awc_rep.setChecked(localData.get_awc_reply());


        ch_r_con = findViewById(R.id.ch_send_end_roshan);
        ch_et_con = findViewById(R.id.ch_send_end_etisalt);
        ch_sa_con = findViewById(R.id.ch_send_end_sala);
        ch_mtn_con = findViewById(R.id.ch_send_end_mtn);
        ch_awc_con = findViewById(R.id.ch_send_end_awc);


        ch_r_con.setChecked(localData.get_rosahn_end());
        ch_et_con.setChecked(localData.get_etisalat_end());
        ch_sa_con.setChecked(localData.get_salam_end());
        ch_mtn_con.setChecked(localData.get_mtn_end());
        ch_awc_con.setChecked(localData.get_awc_end());

//
//        cardView_reply = findViewById(R.id.card_reply);
        cardView_send = findViewById(R.id.card_send);

        if (!localData.get_customer_confirm()) {
            cardView_send.setVisibility(View.GONE);
        }
//
//        Switch sw_reply = findViewById(R.id.switch1);
//        sw_reply.setChecked(localData.get_customer_reply());
//        sw_reply.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//
//                localData.set_customer_reply(b);
//                Toast.makeText(Seting_Activity.this, "set Reply To Cutomer:" + localData.get_customer_reply(), Toast.LENGTH_SHORT).show();
//                if (!b) {
//                    cardView_reply.setVisibility(View.GONE);
//                } else {
//                    cardView_reply.setVisibility(View.VISIBLE);
//
//                }
//
//            }
//        });


        Switch sw_confirm = findViewById(R.id.switch2);

        sw_confirm.setChecked(localData.get_customer_confirm());

        sw_confirm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                localData.set_customer_confirm(b);

                Toast.makeText(Seting_Activity.this, "set Confirm To Cutomer:" + localData.get_customer_confirm(), Toast.LENGTH_SHORT).show();
                if (!b) {
                    cardView_send.setVisibility(View.GONE);
                } else {
                    cardView_send.setVisibility(View.VISIBLE);


                }

            }
        });


    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_s);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("تنظیمات");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // getMenuInflater().inflate(R.menu.menu_search_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {

            if (localData.get_customer_confirm()) {
                set_send_sim();
            }

            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

//    public void set_reply_sim() {
//        boolean r = ch_r_rep.isChecked();
//        boolean et = ch_et_rep.isChecked();
//        boolean sa = ch_sa_rep.isChecked();
//        boolean mtn = ch_mtn_rep.isChecked();
//        boolean awc = ch_awc_rep.isChecked();
//
//
////        Set<String> reply_sim=new HashSet<>();
////        reply_sim.add(r);
////        reply_sim.add(et);
////        reply_sim.add(sa);
////        reply_sim.add(awc);
////        reply_sim.add(mtn);
//
//        localData.set_roshan_reply(r);
//        localData.set_etisalat_reply(et);
//        localData.set_salam_reply(sa);
//        localData.set_mtn_reply(mtn);
//        localData.set_awc_reply(awc);
//        //localData.set_number_set_reply(reply_sim);
//
//        Toast.makeText(this, "sim" + localData.get_rosahn_reply(), Toast.LENGTH_SHORT).show();
//    }

    public void set_send_sim() {
        boolean r = ch_r_con.isChecked();
        boolean et = ch_et_con.isChecked();
        boolean sa = ch_sa_con.isChecked();
        boolean mtn = ch_mtn_con.isChecked();
        boolean awc = ch_awc_con.isChecked();


//        Set<String> reply_sim=new HashSet<>();
//        reply_sim.add(r);
//        reply_sim.add(et);
//        reply_sim.add(sa);
//        reply_sim.add(awc);
//        reply_sim.add(mtn);

        localData.set_roshan_end(r);
        localData.set_etisalat_end(et);
        localData.set_salam_end(sa);
        localData.set_mtn_end(mtn);
        localData.set_awc_end(awc);
        //localData.set_number_set_reply(reply_sim);

        Toast.makeText(this, "sim" + localData.get_rosahn_reply(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {

    }
}
