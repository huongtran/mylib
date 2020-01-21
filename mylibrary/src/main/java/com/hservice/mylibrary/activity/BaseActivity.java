package com.hservice.mylibrary.activity;

import android.app.Activity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.hservice.mylibrary.application.MyApp;
import com.hservice.mylibrary.utils.android.utils.Utils;

public class BaseActivity extends Activity {

    public AdView mAdView = null;

    public void displayBannerAds() {
        try {
            mAdView.loadAd(new AdRequest.Builder().addTestDevice(MyApp.getDeviceID(this)).build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public InterstitialAd mInterstitial;

    public void loadInterads() {
        String adsId = Utils.getStringResourceByName(this, "ads_app_inter");
        mInterstitial = new InterstitialAd(this);
        mInterstitial.setAdUnitId(adsId);
        mInterstitial.loadAd(new AdRequest.Builder().addTestDevice(MyApp.getDeviceID(this)).build());
    }

    public void showInterAds() {
        if (mInterstitial != null && mInterstitial.isLoaded()) {
            mInterstitial.show();
            loadInterads();
        }
    }

    @Override
    public void onDestroy() {
        try {
            if (mAdView != null)
                mAdView.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onDestroy();
    }

    @Override
    public void onPause() {
        try {
            if (mAdView != null)
                mAdView.pause();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            if (mAdView != null)
                mAdView.resume();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
