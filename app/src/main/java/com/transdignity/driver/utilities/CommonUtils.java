package com.transdignity.driver.utilities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextPaint;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.transdignity.driver.R;
import com.transdignity.driver.activities.LoginActivity;

import java.text.DecimalFormat;

public class CommonUtils {
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarGradiant(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            Drawable background = activity.getResources().getDrawable(R.drawable.bg_primary_gradient);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,20);
            //   window.setGravity(Gravity.TOP);
            window.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
            //  window.setNavigationBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(background);
        }
    }

    public static void setInterpolation(Context context, View view) {
        final Animation myAnim = AnimationUtils.loadAnimation(context, R.anim.bounce);
        // Use bounce interpolator with amplitude 0.2 and frequency 20
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
        myAnim.setInterpolator(interpolator);
        view.startAnimation(myAnim);
    }

    public static void setGradientTextColor(TextView textView, String text) {
        textView.setText(text);
        TextPaint paint = textView.getPaint();
        float width = paint.measureText(text);
        Shader textShader = new LinearGradient(0, 0, width, textView.getTextSize(),
                new int[]{
                        Color.parseColor("#FF5252"),
                        Color.parseColor("#FD9018"),
                }, null, Shader.TileMode.MIRROR);
        textView.getPaint().setShader(textShader);
    }

    private static DecimalFormat decimalFormat = new DecimalFormat("#0.00");

    public static String decimalFormat(Double aDouble) {
        return decimalFormat.format(aDouble);
    }

    public static String decimalFormat(Float aFloat) {
        return decimalFormat.format(aFloat);
    }

    public static void switchActivity(Activity fromActivity, Class<?> toActivity) {
        Intent intent = new Intent(fromActivity, toActivity);
        fromActivity.startActivity(intent);
        fromActivity.overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);
    }

    public static void switchActivityWithIntent(Activity fromActivity, Intent intent) {
        fromActivity.startActivity(intent);
        fromActivity.overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);
    }

    public static void back(Activity fromActivity) {
        fromActivity.finish();
        fromActivity.overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);
    }

    public static boolean isNetwork(Context context) {
        NetworkInfo netInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected() && netInfo.isAvailable();
    }

    public static boolean isLocationEnabled(Activity mActivity) {
        LocationManager lm = (LocationManager) mActivity.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }
        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }


        if (!gps_enabled && !network_enabled) {
            enableLocationSettingDialog(mActivity, mActivity.getResources().getString(R.string.message_location_not_enable));
        } else {
            return true;
        }
        return false;
    }

    public static void enableLocationSettingDialog(final Activity mActivity, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle(mActivity.getResources().getString(R.string.app_name));
        builder.setMessage(message);
        builder.setPositiveButton(mActivity.getResources().getString(R.string.message_change_setting), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //   mActivity.startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), MyConstants.LOCATION_ENABLE_CODE);
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    public static boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static void hideKeyPad(Activity activity) {
        try {
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (activity.getCurrentFocus() != null && activity.getCurrentFocus().getWindowToken() != null) {
                inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadFragment(Context context, Fragment fragment, String tag) {
        // load fragment
        FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        //transaction.addToBackStack(null);
        transaction.commit();
    }

    public static void replaceFragment(Context context, Fragment fragment, int content_frame, Boolean addtoBackStack) {
        String backStateName = fragment.getClass().getName();
        String fragmentTag = backStateName;

        FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();

        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);

        if (!fragmentPopped && manager.findFragmentByTag(fragmentTag) == null) { //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(content_frame, fragment, fragmentTag);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            if (addtoBackStack) {
                ft.addToBackStack(backStateName);
            }
            ft.commit();
        } else {
//            Toast.makeText(context, "pop out", Toast.LENGTH_SHORT).show();
        }
    }

    public static String getCurrentFragmentTag(Context context) {
        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        String fragmentTag = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName();
        return fragmentTag;
    }

    public static Fragment getCurrentFragment(Context context) {
        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        String fragmentTag = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName();
        Fragment currentFragment = fragmentManager.findFragmentByTag(fragmentTag);
        return currentFragment;
    }

    public static Fragment getFragmentByTag(Context context, String Tag) {
        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        String fragmentTag = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName();
        Fragment currentFragment = fragmentManager.findFragmentByTag(Tag);
        return currentFragment;
    }

    public static String getDeviceId(Context context) {

        String android_id = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return android_id;
    }

    public static void navigateToGMap(Context context, String latitude, String longitude) {
        String strUri = "google.navigation:q= " + latitude + "," + longitude;
        // String strUri = "http://maps.google.com/maps?q=loc:" + latitude + "," + longitude + " (" + "Label which you want" + ")";
        Intent mapIntent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));
        mapIntent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(mapIntent);
        }
    }

    public static void logoutSession(Activity activity) {
        GlobalValues.getPreferenceManager().logout();
       /* Intent intentlogin = new Intent(activity, LoginActivity.class);
        intentlogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intentlogin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        CommonUtils.switchActivityWithIntent(activity, intentlogin);*/
    }

}
