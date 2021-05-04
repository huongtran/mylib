package com.hservice.mylibrary.utils.android.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.Settings.Secure;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

public class Utils {

//    public static String URL_ANTI_LIMIT_ADS = "http://landmark89.com/snake_st/PUBGB_doanvanhau.json";
    public static String URL_ANTI_LIMIT_ADS = "http://landmark89.com/snake_st/PUBGB_maihuong.json";

    public static String getStringResourceByName(Context context, String aString) {
        String packageName = context.getPackageName();
        int resId = context.getResources().getIdentifier(aString, "string", packageName);
        return context.getString(resId);
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px      A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

    public static void setDynamicHeight(ListView mListView) {
        ListAdapter mListAdapter = mListView.getAdapter();
        if (mListAdapter == null) {
            // when adapter is null
            return;
        }
        int height = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(mListView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        for (int i = 0; i < mListAdapter.getCount(); i++) {
            View listItem = mListAdapter.getView(i, null, mListView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            height += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = mListView.getLayoutParams();
        params.height = height + (mListView.getDividerHeight() * (mListAdapter.getCount() - 1));
        mListView.setLayoutParams(params);
        mListView.requestLayout();
    }

    private static Toast toast;

    public static void makeToast(Context context, String str) {
        if (toast == null) {
            toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
            toast.show();
        } else {
            toast.cancel();
            toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public static boolean isEmpty(String value) {
        if (value == null)
            return true;
        return value.trim().equals("");
    }

    public static String getDeviceID(Context context) {
        return Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
    }

    public static void toast(Context context, String ms) {
        Toast toast = Toast.makeText(context, ms, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void disabledStrictMode() {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    public static String donwload(String strUrl) throws Exception {
        URL url = new URL(strUrl);
        URLConnection tc = url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(tc.getInputStream()));
        String line = "";
        StringBuilder rs = new StringBuilder();
        while ((line = in.readLine()) != null) {
            rs.append(line);
            rs.append("\n");
            break;
        }
        in.close();
        return rs.toString();
    }

    public static String readFile(File fileDir, Context context) throws IOException {
        StringBuilder rs = new StringBuilder();
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileDir), "UTF8"));
        String str = "";
        while ((str = in.readLine()) != null) {
            rs.append(str);
        }
        in.close();
        return rs.toString();
    }

    public static void writeFile(File fileDir, String content, Context context) throws IOException {
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileDir), "UTF8"));
        out.append(content);
        out.flush();
        out.close();
    }

    public static Typeface getSkranjiFonts(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/Skranji-Regular.ttf");
    }

    public static Typeface getCarterOneFonts(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/CarterOne.ttf");
    }

    public static void playSound(Context context, int res) {
        // Sound settings
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        boolean sound = prefs.getBoolean("sound_onoff", true);
        if (!sound)
            return;
        MediaPlayer mp = MediaPlayer.create(context, res);
        if (sound) {
            mp.setVolume(1.0f, 1.0f);
        } else {
            mp.setVolume(0, 0);
        }
        try {
            mp.prepare();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        mp.start();
    }

}
