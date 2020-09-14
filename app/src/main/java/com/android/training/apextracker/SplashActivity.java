package com.android.training.apextracker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.android.training.apextracker.data.ApexContract;

public class SplashActivity extends CatalogActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent openMainActivity =  new Intent(
                        SplashActivity.this, CatalogActivity.class);
                startActivity(openMainActivity);
                finish();

            }
        }, 3000);
    }}

