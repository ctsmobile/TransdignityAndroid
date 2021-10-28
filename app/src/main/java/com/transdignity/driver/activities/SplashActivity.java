package com.transdignity.driver.activities;

import android.content.Intent;

import androidx.databinding.DataBindingUtil;

import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.transdignity.driver.MainActivity;
import com.transdignity.driver.R;
import com.transdignity.driver.databinding.ActivitySplashBinding;
import com.transdignity.driver.utilities.GlobalValues;
import com.transdignity.driver.utilities.MyConstants;
import com.transdignity.driver.utilities.PreferenceManager;

public class SplashActivity extends AppCompatActivity {
    ActivitySplashBinding binding;
    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (GlobalValues.getPreferenceManager().isloginPref()) {
                    if (getIntent().getExtras() != null) {
                        Object value = getIntent().getExtras().get("status");
                        String status = String.valueOf(value);

                        if (status.equalsIgnoreCase("new_request")) {
                            GlobalValues.getPreferenceManager().setString(PreferenceManager.REQUESTID, getIntent().getStringExtra("request_id"));
                            GlobalValues.getPreferenceManager().setString(PreferenceManager.PICKUPLOCATION, getIntent().getStringExtra("pickup_location"));
                            GlobalValues.getInstance().setRequestStatus(status);
                            MyConstants.isNewTask = true;
                        }
                        //Toast.makeText(getApplicationContext(),getIntent().getExtras().toString(),Toast.LENGTH_SHORT).show();
                    }
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }

                finish();
            }
        };
        handler.postDelayed(runnable, 3000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
}
