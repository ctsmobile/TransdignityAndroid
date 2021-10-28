package com.cts.removalspecialist.fcm;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;

import androidx.core.app.NotificationCompat;

import android.util.Log;
import android.widget.Toast;

import com.cts.removalspecialist.MainActivity;
import com.cts.removalspecialist.R;
import com.cts.removalspecialist.activities.LoginActivity;
import com.cts.removalspecialist.activities.SplashActivity;
import com.cts.removalspecialist.utilities.GlobalValues;
import com.cts.removalspecialist.utilities.MyConstants;
import com.cts.removalspecialist.utilities.PreferenceManager;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;


/**
 * Created by android1 on 7/12/16.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMessagingService";
    private SharedPreferences sharedpreferences;
    private String email_id = "";
    private Map<String, String> body;
    private static int value;

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        GlobalValues.getPreferenceManager().setFcmString(PreferenceManager.FCMKEY, s);
    }

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        body = remoteMessage.getData();
        if (remoteMessage.getNotification() != null) {
            Intent intent = new Intent(getApplicationContext(), SplashActivity.class);//remoteMessage.getData().get("badge_id")
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            //intent.putExtra("badge_id", remoteMessage.getData().get("badge_id"));

            // Log.e("Notification Body: " ,type+ remoteMessage.getData()+"");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                callNotificationMessage(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), intent);
                // showNotificationOrio(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), intent);
            } else {
                callNotificationMessage(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), intent);
            }
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            try {
                notificationManage(remoteMessage.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void notificationManage(String remoteMessage) {
        String notification_type = "title";
        try {

            MyConstants.isFromNotification = true;
            notification_type = body.get("status");
            if (notification_type.equalsIgnoreCase("new_request")) {
                GlobalValues.getPreferenceManager().setString(PreferenceManager.REQUESTID, body.get("request_id"));
                // GlobalValues.getPreferenceManager().setString(PreferenceManager.PICKUPLOCATION, body.get("pickup_location"));
                MyConstants.isNewTask = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //sendNotification(notification_type, remoteMessage);
        openAppViaNotification(notification_type, remoteMessage);
    }

    private void openAppViaNotification(String notification_type, String message) {
        if (GlobalValues.getPreferenceManager().isloginPref()) {
            Intent intent = new Intent(this, MainActivity.class);
            //intent.putExtra("NOTIFICATION_CONTENT", message);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(500);
            this.startActivity(intent);
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            // intent.putExtra("NOTIFICATION_CONTENT", message);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(500);
            this.startActivity(intent);
        }
    }

    private void sendNotification(String notification_type, String message) {
        sharedpreferences = getSharedPreferences(MyConstants.USER_LOGIN_PREFERENCES, Context.MODE_PRIVATE);
        email_id = sharedpreferences.getString(MyConstants.EMAIL, "");

        if (MyConstants.isMainActivityPageOpen) {

            if (!GlobalValues.getPreferenceManager().isloginPref()) {
                Intent intent = new Intent(this, MainActivity.class);
                //intent.putExtra("NOTIFICATION_CONTENT", message);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(500);
                this.startActivity(intent);
            } else {
                Intent intent = new Intent(this, LoginActivity.class);
                // intent.putExtra("NOTIFICATION_CONTENT", message);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(500);
                this.startActivity(intent);
            }
        } else {
            MyConstants.isFromNotification = true;

            if (GlobalValues.getPreferenceManager().isloginPref()) {
                Intent intent = new Intent(this, MainActivity.class);
                // intent.putExtra("NOTIFICATION_CONTENT", message);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(500);
                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                        .setSmallIcon(R.mipmap.ic_launcher).setTicker(getResources().getString(R.string.app_name)).setWhen(0)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(getResources().getString(R.string.app_name))

                        .setContentText(message)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(0, notificationBuilder.build());

            } else {
                Intent intent = new Intent(this, LoginActivity.class);
                //intent.putExtra("NOTIFICATION_CONTENT", message);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(500);
                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                        .setSmallIcon(R.mipmap.ic_launcher).setTicker(getResources().getString(R.string.app_name)).setWhen(0)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                        .setContentTitle(getResources().getString(R.string.app_name))
                        .setContentText(message)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(0, notificationBuilder.build());
            }
        }
    }

    private void handleNotification(String message) {
        if (isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            notificationManage(message);
            Toast.makeText(getApplicationContext(), "Background Call " + message, Toast.LENGTH_LONG).show();
            // Log.e(TAG, "LastFormFillData Background: " + message.toString());
        }
    }


    private boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }
        return isInBackground;
    }

    private void callNotificationMessage(String title, String message, Intent intent) {
        try {
            NotificationManager mNotificationManager = (NotificationManager)
                    getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), value  /* Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT);
            showNotification(title, message, mNotificationManager, pendingIntent);
        } catch (Exception e) {

        }
    }

    private void showNotification(String title, String message,
                                  NotificationManager notificationManager, PendingIntent pendingIntent) {

        // int icon = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ? R.drawable.koodoitlogo: R.drawable.koodoitlogo;
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getApplicationContext())
                        .setSmallIcon(R.drawable.ic_loading).
                        setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.ic_loading))
                        .setContentTitle(title)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(message)).setContentText(message)
                        .setPriority(Notification.PRIORITY_MAX);
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setAutoCancel(true);
        // if(*//*MaraamSharedPrefrence.getInstance(getApplicationContext()).getNotificationSound(true)*//*true) {
        //  mBuilder.setDefaults(Notification.DEFAULT_SOUND);
        // }
        notificationManager.notify(++value, mBuilder.build());
    }

   /* @SuppressLint("WrongConstant")
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showNotificationOrio(String title, String body, Intent intent) {
        NotificationChannel mChannel = null;
        NotificationManager notifManager = null;
        final int value = 4;
        if (notifManager == null) {
            notifManager = (NotificationManager) getSystemService
                    (Context.NOTIFICATION_SERVICE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationCompat.Builder builder;
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent;
            int importance = NotificationManager.IMPORTANCE_HIGH;
            if (mChannel == null) {
                mChannel = new NotificationChannel
                        (value+"", title, importance);
                mChannel.setDescription(body);
                mChannel.enableVibration(true);
                notifManager.createNotificationChannel(mChannel);
            }
            builder = new NotificationCompat.Builder(getApplicationContext());

            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(this, value, intent, PendingIntent.FLAG_ONE_SHOT);
            builder.setContentTitle(title)
                    .setSmallIcon(R.drawable.ic_menu_share) // required
                    .setContentText(body)  // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setLargeIcon(BitmapFactory.decodeResource
                            (getResources(), R.drawable.ic_menu_share))
                    .setContentIntent(pendingIntent)
                    .setSound(RingtoneManager.getDefaultUri
                            (RingtoneManager.TYPE_NOTIFICATION));
            Notification notification = builder.build();
            notifManager.notify(value, notification);
        }
    }*/
}
