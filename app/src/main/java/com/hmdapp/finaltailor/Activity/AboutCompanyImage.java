package com.hmdapp.finaltailor.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hmdapp.finaltailor.R;


public class AboutCompanyImage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_company_image);
        initToolbar();
//        ImageView img_t,img_a,img_h,img_w;
//
//        img_t=findViewById(R.id.img_timor);
//        img_a=findViewById(R.id.img_aziz);
//        img_h=findViewById(R.id.img_hasib);
//        img_w=findViewById(R.id.img_wahid);


    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("در باره ما ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.empety_menu, menu);
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

    private void showDialogImageCenter(String name, String emal, int img) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_image_center);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.show();


        TextView txt_name = dialog.findViewById(R.id.txt_about_name);
        txt_name.setText(name);
        TextView txt_emal = dialog.findViewById(R.id.txt_about_email);
        txt_emal.setText(emal);

        ImageButton imgclose = dialog.findViewById(R.id.img_about_close);
        imgclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ImageView imgperson = dialog.findViewById(R.id.img_about_persom);


        imgperson.setImageResource(img);

    }

    public void OnShowInfo(View view) {

        int id = view.getId();


        switch (id) {
            case R.id.img_timor:

                showDialogImageCenter(getString(R.string.timor_na), getString(R.string.timor_email), R.drawable.tahb);
                break;
            case R.id.img_aziz:
                showDialogImageCenter(getString(R.string.younis), getString(R.string.younis), R.drawable.yonis);

                break;

            case R.id.img_wahid:
                showDialogImageCenter(getString(R.string.wasi), getString(R.string.wasimail), R.drawable.mortaza);

                break;
                case R.id.saraj:
                showDialogImageCenter(getString(R.string.saraj), getString(R.string.saraj_email), R.drawable.saraj);

                break;

        }
    }


    private void send_email(String review, String s) {

        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{s});
        email.putExtra(Intent.EXTRA_SUBJECT, "hi");
        email.putExtra(Intent.EXTRA_TEXT, review);

//need this to prompts email client only
        email.setType("message/rfc822");

        startActivity(Intent.createChooser(email, "Choose an Email client :"));
    }

    public void send_email(View view) {


        final String[] dev_name = {getString(R.string.younis), getString( R.string.timor_na), getString(R.string.wasi),getString(R.string.saraj)};
        final String[] dev_email = {getString(R.string.younisail), "tahib2020@gmail.com", getString(R.string.wasimail),getString(R.string.saraj_email)};


        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        //
        dialog.setContentView(R.layout.dialog_add_review_2);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        final EditText et_post = (EditText) dialog.findViewById(R.id.et_post);

        final Spinner spin = dialog.findViewById(R.id.spinner);


        final TextView txt_name = dialog.findViewById(R.id.txt_sms_name);


        txt_name.setText("Send Email To :  " + dev_name[0]);


        ArrayAdapter aa = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, dev_name);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                txt_name.setText("Send Email To :  " + dev_name[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // final AppCompatRatingBar rating_bar = (AppCompatRatingBar) dialog.findViewById(R.id.rating_bar);
        ((AppCompatButton) dialog.findViewById(R.id.bt_cancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ((AppCompatButton) dialog.findViewById(R.id.bt_submit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String review = et_post.getText().toString().trim();
                if (TextUtils.isEmpty(review)) {
                    Toast.makeText(getApplicationContext(), "متن خالی ارسال نمی شود!", Toast.LENGTH_SHORT).show();
                } else {
                    send_email(review, dev_email[spin.getSelectedItemPosition()]);
                    Toast.makeText(getApplicationContext(), "انجام شد!", Toast.LENGTH_SHORT).show();

                }

                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);

    }
}
