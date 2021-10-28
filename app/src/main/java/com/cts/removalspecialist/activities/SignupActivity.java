package com.cts.removalspecialist.activities;

import android.app.Activity;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cts.removalspecialist.utilities.CommonUtils;
import com.cts.removalspecialist.R;
import com.cts.removalspecialist.databinding.ActivitySignupBinding;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {
    ActivitySignupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup);
        binding.tvLogin.setOnClickListener(this);
        binding.llSignup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_login:
                Intent intent2 = new Intent(this, LoginActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                CommonUtils.switchActivityWithIntent(this, intent2);
                break;
            case R.id.ll_signup:
                successDialog(R.string.signup_verification_req,this);
                break;
        }
    }


    public static boolean successDialog(int succesMsg, final Activity activity) {

        LayoutInflater inflater = LayoutInflater.from(activity);
        View viewCustom = inflater.inflate(R.layout.forget_verifylink_sent_dialog, null);
        final AlertDialog dialog = new AlertDialog.Builder(activity).create();
        dialog.setView(viewCustom);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView succesText = viewCustom.findViewById(R.id.msg);

        succesText.setText(succesMsg);
        LinearLayout done = viewCustom.findViewById(R.id.ok_forgot);
        dialog.setCancelable(false);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                CommonUtils.back(activity);
                //activity.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            }
        });
        dialog.show();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
