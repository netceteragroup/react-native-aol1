package com.netcetera.reactnative.adtech;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.adtech.mobilesdk.publisher.ErrorCause;
import com.adtech.mobilesdk.publisher.configuration.AdtechAdConfiguration;
import com.adtech.mobilesdk.publisher.view.AdtechInterstitialView;
import com.adtech.mobilesdk.publisher.view.AdtechInterstitialViewCallback;
import com.netcetera.reactnative.utils.LogUtils;

public class FullScreenDialog extends AlertDialog {

    private static final String TAG = FullScreenDialog.class.getCanonicalName();

    private boolean isLoaded = false;

    private AdtechInterstitialView adtechInterstitialView;

    private String alias;

    protected FullScreenDialog(Context context, String alias) {
        super(context, R.style.DlgFullTheme);
        LogUtils.d(TAG, "FullScreenDialog");
        this.alias = alias;
    }

    private String title = "";

    public void setTitle(String title){
        this.title = title;
    }

    @Override
    public void show() {
        super.show();
        LogUtils.d(TAG, "show");
        setupInterstititalAd(alias);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.d(TAG, "onCreate");

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        setContentView(R.layout.fullscreen_dialog_view);

        findViews();
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        keepStatusBar();

        View view = findViewById(R.id.fake_topbar);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)view.getLayoutParams();
        layoutParams.height = getStatusBarHeight(getContext());
        view.setLayoutParams(layoutParams);
        LogUtils.d(TAG, "height = " + getToolBarHeight());
    }

    private void keepStatusBar(){
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(attrs);
    }

    public int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public int getToolBarHeight() {
        int[] attrs = new int[] {R.attr.actionBarSize};
        TypedArray ta = getContext().obtainStyledAttributes(attrs);
        int toolBarHeight = ta.getDimensionPixelSize(0, -1);
        ta.recycle();
        return toolBarHeight;
    }

    private void findViews() {
        adtechInterstitialView = (AdtechInterstitialView) findViewById(R.id.ad_interstitial);
    }

    private void setupInterstititalAd(String alias) {
        LogUtils.d(TAG, "setupInterstititalAd");
        //parameters setup interstitalad start
        AdtechAdConfiguration adtechAdConfiguration = new AdtechAdConfiguration("SampleApp");
        adtechAdConfiguration.setAlias(alias);
        adtechAdConfiguration.setDomain("a.adtech.de");
        adtechAdConfiguration.setNetworkId(23);
        adtechAdConfiguration.setSubnetworkId(4);
        //parameters setup interstitalad end
        adtechInterstitialView.setAdConfiguration(adtechAdConfiguration);
        adtechInterstitialView.setViewCallback(new AdtechInterstitialViewCallback() {
            @Override
            public void onAdLeave() {
                super.onAdLeave();
                LogUtils.d(TAG, "onAdLeave");
            }

            @Override
            public void onAdDismiss() {
                super.onAdDismiss();
                LogUtils.d(TAG, "onAdDismiss");
                onBackPressed();
            }

            @Override
            public void onAdSuccess() {
                super.onAdSuccess();
                LogUtils.d(TAG, "onAdSuccess");
            }

            @Override
            public void onAdSuccessWithSignal(int... signals) {
                super.onAdSuccessWithSignal(signals);
                //LogUtils.d(TAG, "onAdSuccessWithSignal " + instancesCounter);
                adtechInterstitialView.setVisibility(View.VISIBLE);
                adtechInterstitialView.bringToFront();
                adtechInterstitialView.requestLayout();
                isLoaded = true;
            }

            @Override
            public void onAdFailure(ErrorCause cause) {
                super.onAdFailure(cause);
                LogUtils.d(TAG, "onAdFailure");
            }

            @Override
            public void onAdFailureWithSignal(ErrorCause cause, int... signals) {
                super.onAdFailureWithSignal(cause, signals);
                LogUtils.d(TAG, "onAdFailureWithSignal");
            }

            @Override
            public void onCustomMediation() {
                super.onCustomMediation();
                LogUtils.d(TAG, "onCustomMediation");
            }
        });
        adtechInterstitialView.load();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }


}