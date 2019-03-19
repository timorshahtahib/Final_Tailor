package com.hmdapp.finaltailor.Activity.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.hmdapp.finaltailor.Models.Cloth;
import com.hmdapp.finaltailor.Models.Customer;
import com.hmdapp.finaltailor.R;
import com.hmdapp.finaltailor.database.DB_Acsess;

public class Regester_cloth_Activity extends AppCompatActivity {


    Button fb_insert;
    Customer customer;
    Cloth cloth;
    /// for profile
    // private EditText ed_f_name, ed_job, ed_phone;
    // for cloth
    private EditText ed_qad, ed_shana, ed_baqal, ed_daman, ed_shalwar, ed_pacha, ed_bar_shalwar, ed_yakhan, ed_astin, ed_dam_astin;
    private RadioGroup rg_model, rg_model_astin, rg_qot_astin, rg_model_yaqa, rg_qad_paty, rg_model_dam_astin;
    //    private String full_name, job, phone;
    private int qad, shana, baghal, daman, shalwar, pacha, bar_shalwar, yakhan, astin, dam_astin;
    private String model, model_astin, qot_astin, model_yaqa, qad_paty, model_dam_astn;

    private EditText ed_desc;
    String des;
    int id_cl;
    int id_per;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regester_);
        customer = new Customer();
        cloth = new Cloth();
        //   int checkedRadioButtonId = rd_cl_moldel.getCheckedRadioButtonId();
        final int state = getIntent().getIntExtra("state", 0);

        ed_desc = findViewById(R.id.ed_dec);


        ed_qad = findViewById(R.id.ed_qad);
        ed_baqal = findViewById(R.id.ed_baqal);
        ed_bar_shalwar = findViewById(R.id.ed_bar_shalwar);
        ed_shana = findViewById(R.id.ed_shana);
        ed_daman = findViewById(R.id.ed_daman);
        ed_shalwar = findViewById(R.id.ed_shalwar);
        ed_pacha = findViewById(R.id.ed_pacha);
        ed_yakhan = findViewById(R.id.ed_yakhan);
        ed_astin = findViewById(R.id.ed_astin);
        ed_dam_astin = findViewById(R.id.ed_dm_astin);

        rg_model = findViewById(R.id.cloth_model_rg);
        rg_model_astin = findViewById(R.id.model_astin_rg);
        rg_model_dam_astin = findViewById(R.id.model_dam_astin_rg);
        rg_qot_astin = findViewById(R.id.qot_astin_rg);
        rg_model_yaqa = findViewById(R.id.model_yaqa_rg);
        rg_qad_paty = findViewById(R.id.qad_paty_rg);
        fb_insert = findViewById(R.id.fab_insert_cloth);


        id_cl = getIntent().getIntExtra("id_cl", 1);
        id_per = getIntent().getIntExtra("id_cu", 1);
        customer.setId(id_per);

        fb_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = rg_model.getCheckedRadioButtonId();

                if (Validate()) {
                    setproperty();

                    if (state == 1) {
                        cloth.setId(id_cl);

                        update(cloth);
                        finish();
                    } else {
                        if (insert(cloth)) {
                            Toast.makeText(Regester_cloth_Activity.this, "added ", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Person_Activity.class));
                            finish();
                        } else {
                            Toast.makeText(Regester_cloth_Activity.this, "not aded", Toast.LENGTH_SHORT).show();
                        }

                    }
//                    Toast.makeText(Regester_cloth_Activity.this, "id" + getRgText(rg_model), Toast.LENGTH_SHORT).show();


                }
            }
        });
        initToolbar();


        if (state == 1) {
            setToEdit();
        }

    }

    private void setToEdit() {

        DB_Acsess db_acsess = DB_Acsess.getInstans(this);
        db_acsess.open();
        int id_cl = getIntent().getIntExtra("id_cl", 1);
        int id_per = getIntent().getIntExtra("id_cu", 1);


        Cloth cl = db_acsess.getCloth(id_per, id_cl);


        ed_qad.setText(cl.getQad() + "");
        ed_shana.setText(cl.getShana() + "");
        ed_shalwar.setText(cl.getShalwar() + "");
        ed_bar_shalwar.setText(cl.getBar_shalwar() + "");
        ed_baqal.setText(cl.getBaqal() + "");
        ed_astin.setText(cl.getAstin() + "");
        ed_dam_astin.setText(cl.getDam_astin() + "");
        ed_yakhan.setText(cl.getYakhan() + "");
        ed_daman.setText(cl.getDaman() + "");
        ed_pacha.setText(cl.getPacha() + "");

        ed_desc.setText(cl.getDes() + "");
        setChekedRG(cl);


    }

    private void setChekedRG(Cloth cl) {
        String model = cl.getModel();
        String dam_astin = cl.getModel_dam_astin();
        String model_astin = cl.getModel_astin();
        String qot_astin = cl.getModel_qot_astin();
        String model_yaqa = cl.getModel_yaqa();
        String qad_paty = cl.getQad_paty();


        if(model.trim().equals("ساده")){

            rg_model.check(R.id.rb_model_sada);
        }else if(model.equals("شهبازی")){

            rg_model.check(R.id.rb_model_shabazi);

        }else if(model.equals("پاکستانی")){

            rg_model.check(R.id.rb_model_pakistani);

        }else if(model.equals("چپیه بخن")){
            rg_model.check(R.id.rb_model_chapayakhan);


        }else if(model.trim().equals("خامک")){
            rg_model.check(R.id.rb_model_khamak);


        }else if(model.trim().equals("دیگر ...")){
            rg_model.check(R.id.rb_model_another);


        }



        if(dam_astin.trim().equalsIgnoreCase("ساده ")){
            rg_model_dam_astin.check(R.id.rb_model_dam_astin_sada);
        }else if(dam_astin.trim().equalsIgnoreCase("مرابی ")){
            rg_model_dam_astin.check(R.id.rb_model_dam_astin_mehrabi);

        }else if(dam_astin.trim().equalsIgnoreCase("کفک دار")){
            rg_model_dam_astin.check(R.id.rb_model_dam_astin_kaphak);

        }else if(dam_astin.trim().equalsIgnoreCase("دیگر ...")){
            rg_model_dam_astin.check(R.id.rb_model_dam_astin_another);

        }


        if(model_astin.trim().equals("ساده")){
            rg_model_astin.check(R.id.rb_model_astin_sada);
        }else if(model_astin.trim().equals("کف")){
            rg_model_astin.check(R.id.rb_model_astin_kaf);
        }


        if (model_yaqa.trim().equals("یق خامک")){
            rg_model_yaqa.check(R.id.rb_model_yaqa_khamak);

        }else if(model_yaqa.trim().equals("یقه پاکستانی")){
            rg_model_yaqa.check(R.id.rb_model_yaqa_pak);



        }else if(model.trim().equals("دیگر ...")){
            rg_model_yaqa.check(R.id.rb_model_yaqa_another);

        }


//        String []property={cl.getModel(),cl.getModel_dam_astin(),cl.getModel_astin(),cl.getModel_qot_astin(),cl.getModel_yaqa(),cl.getQad_paty()};
//        for (String tx:property) {
//            switch (tx){
//                case "":
//            }
//        }


    }

    public boolean setproperty() {


        cloth.setQad(qad);
        cloth.setBaqal(baghal);
        cloth.setAstin(astin);
        cloth.setDam_astin(dam_astin);
        cloth.setDaman(daman);
        cloth.setShalwar(shalwar);
        cloth.setShana(shana);
        cloth.setBar_shalwar(bar_shalwar);
        cloth.setPacha(pacha);
        cloth.setYakhan(yakhan);

        cloth.setModel(model);
        cloth.setModel_astin(model_astin);
        cloth.setQad_paty(qad_paty);
        cloth.setModel_dam_astin(model_dam_astn);
        cloth.setModel_qot_astin(qot_astin);
        cloth.setModel_yaqa(model_yaqa);
        cloth.setDes(des);
        cloth.setCustomer(customer);


        return true;
    }

    public boolean Validate() {

        des = ed_desc.getText().toString();

        qad = getText(ed_qad);
        shana = getText(ed_shana);
        baghal = getText(ed_baqal);
        daman = getText(ed_daman);
        shalwar = getText(ed_shalwar);
        pacha = getText(ed_pacha);
        bar_shalwar = getText(ed_bar_shalwar);
        yakhan = getText(ed_yakhan);
        astin = getText(ed_astin);
        dam_astin = getText(ed_dam_astin);

        model = getRgText(rg_model);
        model_astin = getRgText(rg_model_astin);
        qot_astin = getRgText(rg_qot_astin);
        model_yaqa = getRgText(rg_model_yaqa);
        qad_paty = getRgText(rg_qad_paty);
        model_dam_astn = getRgText(rg_model_dam_astin);


        boolean s = true;

        if (TextUtils.isEmpty(des)) {
            Toast.makeText(this, " نام مدل را وارد کنید !", Toast.LENGTH_SHORT).show();

            s = false;
        }
        if (qad == -1) {
            Toast.makeText(this, " اندازه قد  را وارد کنید !", Toast.LENGTH_SHORT).show();

            s = false;
        }
        if (shana == -1) {
            Toast.makeText(this, " اندازه شانه   را وارد کنید !", Toast.LENGTH_SHORT).show();

            s = false;
        }
        if (baghal == -1) {
            Toast.makeText(this, "اندازه بغل  را وارد کنید !", Toast.LENGTH_SHORT).show();

            s = false;
        }
        if (daman == -1) {
            Toast.makeText(this, " اندازه دامن   را وارد کنید !", Toast.LENGTH_SHORT).show();

            s = false;
        }
        if (shalwar == -1) {
            Toast.makeText(this, " اندازه شلوار را وارد کنید !", Toast.LENGTH_SHORT).show();

            s = false;
        }
        if (pacha == -1) {
            Toast.makeText(this, "اندازه پاچه   را وارد کنید !", Toast.LENGTH_SHORT).show();

            s = false;
        }
        if (bar_shalwar == -1) {
            Toast.makeText(this, "اندازه بر شلوار   را وارد کنید !", Toast.LENGTH_SHORT).show();

            s = false;
        }
        if (yakhan == -1) {
            Toast.makeText(this, "اندازه یخن   را وارد کنید !", Toast.LENGTH_SHORT).show();

            s = false;
        }
        if (astin == -1) {
            Toast.makeText(this, " اندازه آستین  را وارد کنید !", Toast.LENGTH_SHORT).show();

            s = false;
        }
        if (dam_astin == -1) {
            Toast.makeText(this, "اندازه دم آستین  را وارد کنید !", Toast.LENGTH_SHORT).show();

            s = false;
        }
        if (TextUtils.isEmpty(model)) {
            Toast.makeText(this, "مودل را وارد کنید !", Toast.LENGTH_SHORT).show();
            s = false;
        }
        if (TextUtils.isEmpty(model_astin)) {
            Toast.makeText(this, "مودل  آستین را وارد کنید !", Toast.LENGTH_SHORT).show();
            s = false;
        }
        if (TextUtils.isEmpty(model_dam_astn)) {
            Toast.makeText(this, "مودل دم آستین  را وارد کنید !", Toast.LENGTH_SHORT).show();
            s = false;
        }
        if (TextUtils.isEmpty(qot_astin)) {
            Toast.makeText(this, "قوت آستین  را وارد کنید !", Toast.LENGTH_SHORT).show();
            s = false;
        }
        if (TextUtils.isEmpty(model_yaqa)) {
            Toast.makeText(this, "مودل  یقه را وارد کنید !", Toast.LENGTH_SHORT).show();
            s = false;
        }
        if (TextUtils.isEmpty(qad_paty)) {
            Toast.makeText(this, "قد پتی  را وارد کنید !", Toast.LENGTH_SHORT).show();
            s = false;
        }


        return s;
    }


    private String getRgText(RadioGroup rg) {
        String tx = "";

        int id = rg.getCheckedRadioButtonId();
        switch (id) {
            case R.id.rb_model_chapayakhan:
                tx = "چپه یحن";
                break;
            case R.id.rb_model_khamak:
                tx = "خامک";
                break;

            case R.id.rb_model_pakistani:
                tx = "پاکستانی";
                break;
            case R.id.rb_model_sada:
                tx = "ساده";
                break;

            case R.id.rb_model_shabazi:
                tx = "شهبازی";
                break;
            case R.id.rb_model_another:
                tx = "دیگر مودل بخن";
                break;
            case R.id.rb_model_dam_astin_sada:
                tx = "ساده";
                break;
            case R.id.rb_model_dam_astin_kaphak:
                tx = "کفک دار";
                break;
            case R.id.rb_model_dam_astin_mehrabi:
                tx = "مرابی";
                break;
            case R.id.rb_model_dam_astin_another:
                tx = "دیگر مودل دم آستین ";
                break;
            case R.id.rb_model_astin_kaf:
                tx = "کف";
                break;
            case R.id.rb_model_astin_sada:
                tx = "ساده";
                break;
            case R.id.rb_qot_astin_andami:
                tx = "اندامی";
                break;
            case R.id.rb_qot_astin_midim:
                tx = "متوسط";
                break;
            case R.id.rb_model_yaqa_khamak:
                tx = "یقه خامک";
                break;
            case R.id.rb_model_yaqa_pak:
                tx = "یقه پاکستانی";
                break;
            case R.id.rb_model_yaqa_another:
                tx = "دیگر مودل یقه";
                break;
            case R.id.rb_qad_paty_baland:
                tx = "قد پتی بلند";
                break;
            case R.id.rb_qad_paty_kota:
                tx = "قد پتی کوتا";
                break;
            case R.id.rb_qad_paty_another:
                tx = "قد پتی متوسط";
                break;

        }

        return tx;
    }


    private int getText(EditText ed) {

        String tx = ed.getText().toString().trim();

        if (TextUtils.isEmpty(tx)) {
            return -1;
        }
        return Integer.parseInt(tx);

    }

    public boolean insert(Cloth cl) {
        DB_Acsess db_acsess = DB_Acsess.getInstans(getApplicationContext());
        db_acsess.open();
        long t = db_acsess.insert_cloth(cl);

        return t > 0;
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("اضافه کردن مدل لباس");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
      //  getMenuInflater().inflate(R.menu.menu_search_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void update(Cloth cl) {
        DB_Acsess db_acsess = DB_Acsess.getInstans(this);
        db_acsess.open();
        if (db_acsess.updateCloth(cl) > 0)
            Toast.makeText(this, "ویرایش شد", Toast.LENGTH_SHORT).show();
        else {
            Toast.makeText(this, "ویرایش نشد", Toast.LENGTH_SHORT).show();
        }


    }

}
