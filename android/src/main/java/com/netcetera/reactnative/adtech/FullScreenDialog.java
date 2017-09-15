package com.netcetera.reactnative.adtech;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.facebook.react.ReactActivity;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.RCTEventEmitter;

import com.adtech.mobilesdk.publisher.ErrorCause;
import com.adtech.mobilesdk.publisher.configuration.AdtechAdConfiguration;
import com.adtech.mobilesdk.publisher.view.AdtechInterstitialView;
import com.adtech.mobilesdk.publisher.view.AdtechInterstitialViewCallback;
import com.netcetera.reactnative.utils.LogUtils;

public class FullScreenDialog extends AlertDialog {

    private static final String TAG = FullScreenDialog.class.getCanonicalName();

    private AdtechInterstitialView adtechInterstitialView;


    protected FullScreenDialog(Context context) {
        super(context, R.style.DlgFullTheme);
        LogUtils.d(TAG, "FullScreenDialog");
    }

    private String title = "";

    public void setTitle(String title)
    {
        this.title = title;
    }

    private AdtechAdConfiguration adConfiguration;

    public void setAdConfiguration(AdtechAdConfiguration adConfiguration)
    {
        this.adConfiguration = adConfiguration;
    }

    private ReactActivity parentActivity;
    public void setParentActivity(ReactActivity parentActivity)
    {
        this.parentActivity = parentActivity;
    }

    public void showInterstitial()
    {
        LogUtils.d(TAG, "show");

        setupInterstititalAd();

        hide();
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

    private void setupInterstititalAd() {
        LogUtils.d(TAG, "setupInterstititalAd");

        adtechInterstitialView.setAdConfiguration(this.adConfiguration);
        adtechInterstitialView.setViewCallback(new AdtechInterstitialViewCallback() {
            @Override
            public void onAdLeave() {
                super.onAdLeave();
            }

            @Override
            public void onAdDismiss() {
                super.onAdDismiss();

                hide();
            }

            @Override
            public void onAdSuccess() {
                super.onAdSuccess();

                show();

                LogUtils.d(TAG, "onAdSuccess");
                adtechInterstitialView.setVisibility(View.VISIBLE);
                adtechInterstitialView.bringToFront();
                adtechInterstitialView.requestLayout();
            }

            @Override
            public void onAdSuccessWithSignal(int... signals) {
                super.onAdSuccessWithSignal(signals);

                show();

                adtechInterstitialView.setVisibility(View.VISIBLE);
                adtechInterstitialView.bringToFront();
                adtechInterstitialView.requestLayout();
            }

            @Override
            public void onAdFailure(ErrorCause cause) {
                super.onAdFailure(cause);

                hide();
            }

            @Override
            public void onAdFailureWithSignal(ErrorCause cause, int... signals) {
                super.onAdFailureWithSignal(cause, signals);

                hide();
            }

            @Override
            public void onCustomMediation() {
                super.onCustomMediation();
            }
        });
        adtechInterstitialView.load();
    }
}