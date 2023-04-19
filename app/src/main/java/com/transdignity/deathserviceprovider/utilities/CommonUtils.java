package com.transdignity.deathserviceprovider.utilities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.Settings;
import android.text.TextPaint;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.transdignity.deathserviceprovider.R;
import com.transdignity.deathserviceprovider.activities.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.Calendar;

import static androidx.constraintlayout.widget.Constraints.TAG;

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
    public static void getDatepickerTextView(Context context, final TextView editText) {
        // Get Current Date
        int mYear, mMonth, mDay;

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        //  String curDate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        String curDate = year + "-"+ (monthOfYear + 1) + "-" +dayOfMonth;
                        editText.setText(curDate);
                        Log.e(TAG, "onDateSet:date >>>>> " + curDate);
                        // txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();

    }

    public static void getDatepicker(Context context, final TextView editText) {
        // Get Current Date
        int mYear, mMonth, mDay;

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        String day= String.valueOf(Integer.valueOf(dayOfMonth));
                        String month= String.valueOf(Integer.valueOf(monthOfYear));
                        if(day.length()==1){
                            String curDate = "0"+dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                            editText.setText(curDate);
                        }else if(month.length()==1){
                            String curDate = dayOfMonth + "/" + "0"+(monthOfYear + 1) + "/" + year;
                            editText.setText(curDate);
                        }else if((day.length()==1)&&(month.length()==1)){
                            String curDate = "0"+dayOfMonth + "/" + "0"+(monthOfYear + 1) + "/" + year;
                            editText.setText(curDate);
                        }else {
                            String curDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                            editText.setText(curDate);
                        }
                        if((day.length()==1)&&(month.length()==1)){
                            String curDate = "0"+dayOfMonth + "/" + "0"+(monthOfYear + 1) + "/" + year;
                            editText.setText(curDate);
                        }

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());

        datePickerDialog.show();

    }

    public static void getTime(Context context, final EditText editText) {
        int mHour, mMinute;
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        // String  strTime = hourOfDay + ":" + minute;
                        String hr= String.valueOf(Integer.valueOf(hourOfDay));
                        String mn= String.valueOf(Integer.valueOf(minute));
                        if(hr.length()==1){
                            editText.setText("0"+hourOfDay + ":" + minute);
                        }else if(mn.length()==1){
                            editText.setText(hourOfDay + ":" + "0"+minute);
                        }else if(hr.length()==1&&mn.length()==1){
                            editText.setText("0"+hourOfDay + ":" + "0"+minute);
                        }else {
                            editText.setText(hourOfDay + ":" + minute);

                        }
                        if(hr.length()==1&&mn.length()==1){
                            editText.setText("0"+hourOfDay + ":" + "0"+minute);
                        }
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    public static void getDatepickerDOB(Context context, final EditText editText) {
        // Get Current Date
        int mYear, mMonth, mDay;

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        String day= String.valueOf(Integer.valueOf(dayOfMonth));
                        String month= String.valueOf(Integer.valueOf(monthOfYear));
                        if(day.length()==1){
                            String curDate = "0"+dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                            editText.setText(curDate);
                        }else if(month.length()==1){
                            String curDate = dayOfMonth + "/" + "0"+(monthOfYear + 1) + "/" + year;
                            editText.setText(curDate);
                        }else if((day.length()==1)&&(month.length()==1)){
                            String curDate = "0"+dayOfMonth + "/" + "0"+(monthOfYear + 1) + "/" + year;
                            editText.setText(curDate);
                        }else {
                            String curDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                            editText.setText(curDate);
                        }
                        if((day.length()==1)&&(month.length()==1)){
                            String curDate = "0"+dayOfMonth + "/" + "0"+(monthOfYear + 1) + "/" + year;
                            editText.setText(curDate);
                        }

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());

        datePickerDialog.show();

    }


    public static void getDatepickerDOD(Context context, final EditText editText) {
        // Get Current Date
        int mYear, mMonth, mDay;

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        String day= String.valueOf(Integer.valueOf(dayOfMonth));
                        String month= String.valueOf(Integer.valueOf(monthOfYear));
                        if(day.length()==1){
                            String curDate = "0"+dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                            editText.setText(curDate);
                        }else if(month.length()==1){
                            String curDate = dayOfMonth + "/" + "0"+(monthOfYear + 1) + "/" + year;
                            editText.setText(curDate);
                        }else if((day.length()==1)&&(month.length()==1)){
                            String curDate = "0"+dayOfMonth + "/" + "0"+(monthOfYear + 1) + "/" + year;
                            editText.setText(curDate);
                        }else {
                            String curDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                            editText.setText(curDate);
                        }
                        if((day.length()==1)&&(month.length()==1)){
                            String curDate = "0"+dayOfMonth + "/" + "0"+(monthOfYear + 1) + "/" + year;
                            editText.setText(curDate);
                        }

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());

        datePickerDialog.show();

    }


    public static String getDeviceId(Context context) {

        String android_id = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return android_id;
    }
    public static  void logoutSession(Activity activity) {
        GlobalValues.getPreferenceManager().logout();
        Intent intentlogin = new Intent(activity, LoginActivity.class);
        intentlogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intentlogin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        CommonUtils.switchActivityWithIntent(activity, intentlogin);
    }

}
