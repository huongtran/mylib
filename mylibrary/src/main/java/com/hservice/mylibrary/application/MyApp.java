
package com.hservice.mylibrary.application;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.provider.Settings;
import android.view.ViewConfiguration;

import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;

import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import ch.acra.acra.BuildConfig;

@ReportsCrashes(
        formUri = "http://landmark89.com/android_log/arca/acra.php",
        formUriBasicAuthLogin = "altplus",
        formUriBasicAuthPassword = "wcb4rtfwya8xmb6c",
        reportType = org.acra.sender.HttpSender.Type.JSON,
        buildConfigClass = BuildConfig.class,
        sendReportsAtShutdown = false)
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // force to sow the overflow menu icon
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception ex) {
            // Ignore
        }

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }

        try {
            Class.forName("android.os.AsyncTask");
        } catch (Throwable ignore) {
        }
        try {
            ACRA.DEV_LOGGING = true;
            ACRA.init(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final String md5(final String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
//            Logger.logStackTrace(TAG,e);
            e.printStackTrace();
        }
        return "";
    }

    public static String getDeviceID(Context context) {
        if (!BuildConfig.DEBUG) {
            return "";
        }
        String android_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return md5(android_id).toUpperCase();
    }
}
