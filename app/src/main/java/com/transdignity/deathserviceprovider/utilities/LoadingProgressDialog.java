package com.transdignity.deathserviceprovider.utilities;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.transdignity.deathserviceprovider.R;

public class LoadingProgressDialog extends Dialog {
    TextView text;
    String message;
    ImageView image;
    Context context;

    public LoadingProgressDialog(Context context, String message) {
        super(context);
        this.context = context;
        this.message = message;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(R.color.transparent);
        setContentView(R.layout.loading_progress_dialog);
        image = (ImageView) findViewById(R.id.loading_icon);

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.progress_rotation);
        image.setAnimation(animation);
        animation.start();

        text = (TextView) findViewById(R.id.dialogmessage);
        text.setText(message);

    }

    public void setMessage(String message) {
        text.setText(message);
    }

    @Override
    protected void onStop() {
        super.onStop();
        image.clearAnimation();
    }

}
