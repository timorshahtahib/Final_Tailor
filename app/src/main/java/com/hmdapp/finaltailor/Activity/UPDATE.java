package com.hmdapp.finaltailor.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hmdapp.finaltailor.R;

import java.net.URI;

public class UPDATE extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.update_activity);
    }

    public void onupdate(View view) {


        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.hmdapp.finaltailor"));

        startActivity(intent);
    }
}
