package com.hmdapp.finaltailor.Activity.Report_Dashbord;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hmdapp.finaltailor.Activity.RemainderActivity;
import com.hmdapp.finaltailor.R;
import com.hmdapp.finaltailor.database.DB_Acsess;

public class DashboradReportActivity extends AppCompatActivity {
    private TextView tvDailyum,tvWeeklySum,tvMonthlySum,tvYearlySum,tvRemainderSum,tvCashMoney;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_report);
        initToolbar();
        setUpViews();
    }
    private void setUpViews(){

        tvDailyum = findViewById(R.id.count_daily);
        tvWeeklySum = findViewById(R.id.count_weekly);
        tvMonthlySum = findViewById(R.id.count_monthly);
        tvYearlySum = findViewById(R.id.count_yearly);
        tvRemainderSum = findViewById(R.id.count_remainder);
        tvCashMoney = findViewById(R.id.count_cash_money);

        DB_Acsess db_acsess = DB_Acsess.getInstans(this);
        db_acsess.open();

        tvDailyum.setText(""+ db_acsess.getDailyPayment());
        tvWeeklySum.setText(""+ db_acsess.getWeeklyPayment());
        tvMonthlySum.setText(""+ db_acsess.getMonthlyPayment());
        tvYearlySum.setText(""+ db_acsess.getYearlyPayment());
        tvCashMoney.setText(""+ db_acsess.getAllPayment());
        tvRemainderSum.setText(""+ db_acsess.getRemainderPayment());
    }

    public void onDailyClick(View view) {
        Intent intent = new Intent(this, ReportActivity.class);
        intent.putExtra("myKey","Daily");
       startActivity(intent);
    }

    public void onMonthlyClick(View view) {
        Intent intent = new Intent(this,ReportActivity.class);
        intent.putExtra("myKey","Monthly");
        startActivity(intent);
    }

    public void onWeeklyClick(View view) {
        Intent intent = new Intent(this,ReportActivity.class);
        intent.putExtra("myKey","Weekly");
        startActivity(intent);
    }

    public void onYearlyClick(View view) {
        Intent intent = new Intent(this,ReportActivity.class);
        intent.putExtra("myKey","Yearly");
        startActivity(intent);
    }
    public void onRemainderClick(View view) {
        Intent intent = new Intent(this, RemainderActivity.class);
        intent.putExtra("myKey","Yearly");
        startActivity(intent);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(" گزارش ها ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
}
