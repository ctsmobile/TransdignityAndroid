package com.transdignity.deathserviceprovider;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import android.view.MenuItem;
import android.widget.Toast;

import com.transdignity.deathserviceprovider.fragments.ContactUsFragment;
import com.transdignity.deathserviceprovider.fragments.HomeFragment;
import com.transdignity.deathserviceprovider.fragments.PaymentFragment;
import com.transdignity.deathserviceprovider.fragments.ProfileFragment;
import com.transdignity.deathserviceprovider.fragments.SettingsFragment;
import com.transdignity.deathserviceprovider.utilities.CommonUtils;
import com.transdignity.deathserviceprovider.utilities.GlobalValues;

public class MainActivity extends AppCompatActivity {
    boolean doubleBackToExitPressedOnce = false;
    String fragmtStatusTag = "";
    Fragment fragment;
    public BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.setItemIconTintList(null);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // takePictureButton.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE,Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 0);
        }
        fragment = new HomeFragment();
        fragmtStatusTag = fragment.getClass().getName();
        GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
       // CommonUtils.loadFragment(this, fragment, fragmtStatusTag);
        CommonUtils.replaceFragment(this,fragment,R.id.frame_container,false);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    fragment = new HomeFragment();
                    fragmtStatusTag = fragment.getClass().getName();
                    GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                   // CommonUtils.loadFragment(MainActivity.this, fragment, fragmtStatusTag);
                    CommonUtils.replaceFragment(MainActivity.this,fragment,R.id.frame_container,false);

                    return true;
                case R.id.navigation_payment:
                    fragment = new PaymentFragment();
                    fragmtStatusTag = fragment.getClass().getName();
                    GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                   // CommonUtils.loadFragment(MainActivity.this, fragment, fragmtStatusTag);
                    CommonUtils.replaceFragment(MainActivity.this,fragment,R.id.frame_container,false);

                    return true;
                case R.id.navigation_contactus:
                    fragment = new ContactUsFragment();
                    fragmtStatusTag = fragment.getClass().getName();
                    GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                  //  CommonUtils.loadFragment(MainActivity.this, fragment, fragmtStatusTag);
                    CommonUtils.replaceFragment(MainActivity.this,fragment,R.id.frame_container,false);

                    return true;
                case R.id.navigaton_settings:
                    fragment = new SettingsFragment();
                    fragmtStatusTag = fragment.getClass().getName();
                    GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                    ///CommonUtils.loadFragment(MainActivity.this, fragment, fragmtStatusTag);
                    CommonUtils.replaceFragment(MainActivity.this,fragment,R.id.frame_container,false);

                    return true;
            }
            return false;
        }
    };


    /*public void loadFragment(Fragment fragment, String tag) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);

        transaction.commit();
    }*/

    @Override
    public void onBackPressed() {
        fragment = new HomeFragment();
        Fragment fragmentProfile = new ProfileFragment();
        String currentTag= GlobalValues.getInstance().getFramgentTag();

         if (currentTag.equalsIgnoreCase(fragmentProfile.getClass().getName())){
            fragmtStatusTag = fragmentProfile.getClass().getName();
            GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
          //  CommonUtils.loadFragment(MainActivity.this, fragmentProfile, fragmtStatusTag);
             CommonUtils.replaceFragment(MainActivity.this,fragment,R.id.frame_container,false);

             selectedNavItem(R.id.navigaton_settings);
            return;
        }
         else  if (!currentTag.equalsIgnoreCase(fragment.getClass().getName())) {
             fragmtStatusTag = fragment.getClass().getName();
             GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
          //   CommonUtils.loadFragment(MainActivity.this, fragment, fragmtStatusTag);
             CommonUtils.replaceFragment(MainActivity.this,fragment,R.id.frame_container, false);

             selectedNavItem(R.id.navigation_home);
             return;
         }
        else if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK PRESS again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    public  void selectedNavItem(int item) {
        navView.setSelectedItemId(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
