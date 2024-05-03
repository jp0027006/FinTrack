package com.example.moneymanager.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;


import com.example.moneymanager.Database.DBhelper;
import com.example.moneymanager.R;
import com.example.moneymanager.Utils.SharepreferenceUtils;

public class SplashActivity extends AppCompatActivity {

    DBhelper dBhelper;
    SharepreferenceUtils sharepreferenceUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        dBhelper=new DBhelper(this);
        sharepreferenceUtils=new SharepreferenceUtils(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(sharepreferenceUtils.getAutologin()){
                    Intent intent= new Intent(SplashActivity.this, HomeScreenActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent= new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                }

                finish();

            }
        },3000);



    }

}