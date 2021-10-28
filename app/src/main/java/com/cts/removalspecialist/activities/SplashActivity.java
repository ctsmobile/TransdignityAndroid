package com.cts.removalspecialist.activities;

import android.content.Intent;

import androidx.databinding.DataBindingUtil;

import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cts.removalspecialist.MainActivity;
import com.cts.removalspecialist.R;
import com.cts.removalspecialist.databinding.ActivitySplashBinding;
import com.cts.removalspecialist.utilities.GlobalValues;

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
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                } else
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));

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
