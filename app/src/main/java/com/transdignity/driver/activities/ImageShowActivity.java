package com.transdignity.driver.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.transdignity.driver.R;
import com.transdignity.driver.utilities.MyConstants;

public class ImageShowActivity extends AppCompatActivity {
    ImageView image;
    String str_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_show);
        image=findViewById(R.id.image);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            str_image = extras.getString("image");
            Glide.with(getApplicationContext()).load(MyConstants.BASE_URL + str_image).into(image);

        } else {
            // handle case
        }

    }
}