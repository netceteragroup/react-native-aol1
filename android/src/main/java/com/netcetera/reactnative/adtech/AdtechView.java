package com.netcetera.reactnative.adtech;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.adtech.mobilesdk.publisher.view.AdtechInterstitialView;
import com.adtech.mobilesdk.publisher.view.AdtechInterstitialViewCallback;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.RCTEventEmitter;

import com.adtech.mobilesdk.publisher.ErrorCause;
import com.adtech.mobilesdk.publisher.configuration.AdtechAdConfiguration;
import com.adtech.mobilesdk.publisher.view.AdtechBannerView;
import com.adtech.mobilesdk.publisher.view.AdtechBannerViewCallback;
import com.adtech.mobilesdk.publisher.view.BannerResizeBehavior;
import com.adtech.mobilesdk.publisher.view.BannerResizeProperties;
import com.adtech.mobilesdk.publisher.view.BannerResizeType;
import com.netcetera.reactnative.utils.LogUtils;
import com.facebook.react.bridge.UiThreadUtil;

class AdtechView extends RelativeLayout {

    public interface SizeChangeListener {
        void onSizeChanged(AdtechView view, int width, int height);
    }

    private static final String TAG = AdtechView.class.getCanonicalName();

    private View mainContainer;
    private RelativeLayout loadingContainer;
    private ProgressBar loadingProgressBar;

    private Activity activity;
    private String appName;
    private String domain;

    private int reactTag;

    private AdtechBannerView adtechBannerView;
    private AdtechInterstitialView adtechInterstitialView;

    private ArrayList<SizeChangeListener> sizeChangeListeners = new ArrayList<>();

    public AdtechView(Context context, Activity activity, String appName, String domain) {
        super(context);
        this.appName = appName;
        this.domain = domain;
        this.activity = activity;

        //activity.getLayoutInflater().inflate(R.layout.add_container, this, true);
        LayoutInflater.from(getContext()).inflate(R.layout.add_container, this, true);
        mainContainer = this;

        findViews();
        setLoadingView();
    }

    public void checkIfAllParametersWereLoadedAndLoad() {
        if (alias != null
                && type != null
                && networkId > 0
                && subnetworkId > 0
                && autoloadAd)
        {
            loadAd();
        }
    }

    private boolean autoloadAd = true;

    public void setAutoload(boolean autoloadAd) {
        this.autoloadAd = autoloadAd;
    }

    private String alias;

    public void setAlias(String alias) {
        this.alias = alias;
    }

    private String type;

    public void setType(String type) {
        this.type = type;
    }

    private int networkId;

    public void setNetworkId(int networkId) {
        this.networkId = networkId;
    }

    private int subnetworkId;

    public void setSubnetworkId(int subnetworkId) {
        this.subnetworkId = subnetworkId;
    }

    private ReadableMap keyValues;

    public void setKeyValues(ReadableMap keyValues) {
        this.keyValues = keyValues;
    }

    private int maxHeight;

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    private int height;

    public void setHeight(int height){
        this.height = height;
    }

    public void pauseAd() {
        if (adtechBannerView != null) {
            adtechBannerView.stop();
        }
    }

    public void resumeAd() {
        if (adtechBannerView != null) {
            adtechBannerView.load();
        }
    }

    public int getReactTag() {
        return reactTag;
    }

    public void setReactTag(int reactTag) {
        this.reactTag = reactTag;
    }

    public void respondToNewProps() {}

    private void updateSize() {
        requestLayout();
        measureAd();

        int measuredWidth = adtechBannerView.getMeasuredWidth();

        int measuredHeight = (int)(getResources().getDisplayMetrics().density * maxHeight);

        fireSizeChange(measuredWidth, measuredHeight);
    }

    private void updateSizeFail() {
        fireSizeChange(0, 0);
    }


    private void measureAd() {

        int w = View.MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.EXACTLY);
        int h = maxHeight;

        int measuredWidth = adtechBannerView.getMeasuredWidth();
        int measuredHeight = adtechBannerView.getMeasuredHeight();

        Log.d(TAG, "Measured " + measuredWidth + ", " + measuredHeight);

        adtechBannerView.measure(w, h);
    }

    public void addSizeChangeListener(@NonNull SizeChangeListener l) {
        sizeChangeListeners.add(l);
    }

    public void removeSizeChangeListener(@NonNull SizeChangeListener l) {
        sizeChangeListeners.remove(l);
    }

    protected void fireSizeChange(int width, int height) {
        for (SizeChangeListener l : sizeChangeListeners) {
            l.onSizeChanged(this, width, height);
        }
    }

    private void findViews() {
        loadingContainer = (RelativeLayout) mainContainer.findViewById(R.id.loading_container);
        loadingProgressBar = (ProgressBar) mainContainer.findViewById(R.id.loading_progress_bar);
    }

    private void setLoadingView() {
        LogUtils.d(TAG, "setLoadingView");
        loadingContainer.setVisibility(View.VISIBLE);
    }

    private void loadAd() {
        if(type.equalsIgnoreCase("banner")){
            setupBannerAd();
        } else if(type.equalsIgnoreCase("interstitial")){
            UiThreadUtil.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setupInterstitialAd();
                }
            });
        }
    }

    public void disposeAd() {
        if (adtechBannerView != null) {
            adtechBannerView.stop();
        }
        if (adtechInterstitialView != null) {
            adtechInterstitialView.stop();
        }
    }

    private void setupBannerAd() {
        if (adtechBannerView == null) {
            adtechBannerView = (AdtechBannerView) mainContainer.findViewById(R.id.ad_banner);
        }

        LogUtils.d(TAG, "setupBannerAd");
        //parameters setup bannerad start
        AdtechAdConfiguration adtechAdConfiguration = getAdConfiguration();

        adtechBannerView.setAdConfiguration(adtechAdConfiguration);

        //Optional: set a call-back to be notified of certain events in the life-cycle of the ad */
        adtechBannerView.setViewCallback(
                new AdtechBannerViewCallback() {

                    @Override
                    public void onAdSuspend() {
                        LogUtils.d(TAG, "onAdSuspend");
                    }

                    @Override
                    public void onAdSuccess() {
                        LogUtils.d(TAG, "onAdSuccess");
                        adSuccess();
                    }

                    @Override
                    public void onAdResume() {
                        LogUtils.d(TAG, "onAdResume");
                    }

                    @Override
                    public void onAdLeave() {
                        LogUtils.d(TAG, "onAdLeave");
                    }

                    @Override
                    public void onAdFailure(ErrorCause cause) {
                        LogUtils.d(TAG, "onAdFailure");

                        adtechBannerView.setVisibility(GONE);
                        adFail();
                        updateSizeFail();
                    }

                    @Override
                    public void onCustomMediation() {
                        LogUtils.d(TAG, "onCustomMediation");
                    }

                    @Override
                    public BannerResizeBehavior onAdWillResize(BannerResizeProperties resizeProperties) {
                        LogUtils.d(TAG, "onAdWillResize");
                        return new BannerResizeBehavior(BannerResizeType.INLINE, 0);
                    }

                    @Override
                    public void onAdDidResize(BannerResizeProperties resizeProperties) {
                        LogUtils.d(TAG, "onAdDidResize");
                        adSuccess();
                    }

                    @Override
                    public void onAdFailureWithSignal(ErrorCause cause, int... signals) {
                        LogUtils.d(TAG, "onAdSuccess");
                        adFail();
                    }

                    @Override
                    public void onAdSuccessWithSignal(int... signals) {
                        adSuccess();
                    }

                    void adSuccess() {
                        adtechBannerView.setHardwareAccelerationEnabledForCurrentAd(true);
                        adtechBannerView.setVisibility(VISIBLE);
                        adFecthedSuccessfully();
                        updateSize();
                    }

                    void adFail() {
                        adtechBannerView.setVisibility(GONE);
                        adFetchFailed();
                        updateSizeFail();
                    }
                });
        adtechBannerView.load();
    }

    private void setupInterstitialAd() {
        if (adtechInterstitialView == null) {
            LayoutParams params = new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT
            );

            adtechInterstitialView = new AdtechInterstitialView(activity.getBaseContext());
            adtechInterstitialView.setLayoutParams(params);

            activity.addContentView(adtechInterstitialView, params);
        }

        LogUtils.d(TAG, "setupInterstititalAd");

        AdtechAdConfiguration config = getAdConfiguration();
        config.setHideAfterRefreshInterval(false);

        adtechInterstitialView.setAdConfiguration(config);
        adtechInterstitialView.setViewCallback(new AdtechInterstitialViewCallback() {
            @Override
            public void onAdLeave() {
                super.onAdLeave();
            }

            @Override
            public void onAdDismiss() {
                super.onAdDismiss();
                adInterstitialHidden();

//                UiThreadUtil.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        adtechInterstitialView.setBackgroundColor(0x00000000);
//                    }
//                });
            }

            @Override
            public void onAdSuccess() {
                super.onAdSuccess();
                adFecthedSuccessfully();

                LogUtils.d(TAG, "onAdSuccess");
                adtechInterstitialView.setVisibility(View.VISIBLE);
                adtechInterstitialView.bringToFront();
                adtechInterstitialView.requestLayout();
                adtechInterstitialView.setBackgroundColor(0xd9000000);

            }

            @Override
            public void onAdSuccessWithSignal(int... signals) {
                super.onAdSuccessWithSignal(signals);
                adFecthedSuccessfully();
                adtechInterstitialView.setVisibility(View.VISIBLE);
                adtechInterstitialView.bringToFront();
                adtechInterstitialView.requestLayout();
                adtechInterstitialView.setBackgroundColor(0xd9000000);

            }

            @Override
            public void onAdFailure(ErrorCause cause) {
                super.onAdFailure(cause);
                adFetchFailed();
            }

            @Override
            public void onAdFailureWithSignal(ErrorCause cause, int... signals) {
                super.onAdFailureWithSignal(cause, signals);
                adFetchFailed();
            }

            @Override
            public void onCustomMediation() {
                super.onCustomMediation();
            }
        });
        adtechInterstitialView.load();
    }

    private final void triggerAnEvent(String eventName) {
        WritableMap evt = Arguments.createMap();

        ReactContext ctx = (ReactContext) getContext();
        ctx.getJSModule(RCTEventEmitter.class).receiveEvent(
                getId(),
                eventName,
                evt);
    }

    public void adFecthedSuccessfully()
    {
        loadingProgressBar.setVisibility(INVISIBLE);
        triggerAnEvent("onAdFetchSuccess");
    }

    public void adFetchFailed()
    {
        triggerAnEvent("onAdFetchFail");
    }

    public void adInterstitialHidden()
    {
        triggerAnEvent("onInterstitialHidden");
    }

    private AdtechAdConfiguration getAdConfiguration()
    {
        AdtechAdConfiguration configuration = new AdtechAdConfiguration(appName);
        configuration.setDomain(domain);

        configuration.setAlias(alias);
        configuration.setNetworkId(networkId);
        configuration.setSubnetworkId(subnetworkId);

        if (keyValues != null) {
            ReadableMapKeySetIterator iterator = keyValues.keySetIterator();

            while (iterator.hasNextKey()) {
                String key = iterator.nextKey();
                ReadableType type = keyValues.getType(key);
                String[] processedValues = {};

                switch (type) {
                    case Null:
                        break;
                    case Boolean:
                        Boolean boolValue = keyValues.getBoolean(key);
                        processedValues = new String[] { Boolean.toString(boolValue) };
                        break;
                    case Number:
                        Double doubleValue = keyValues.getDouble(key);
                        processedValues = new String[] { Double.toString(doubleValue) };
                        break;
                    case String:
                        processedValues = new String[] { keyValues.getString(key) };
                        break;
                    case Map:
                        break;
                    case Array:
                        ReadableArray arrayValue = keyValues.getArray(key);
                        processedValues = stringArrayFromReadableArray(arrayValue);
                        break;
                    default:
                        break;
                }

                String processedKey = key.startsWith("kv") ? key.substring("kv".length()) : key;

                if (processedValues.length == 1) {
                    configuration.addKeyValueParameter(processedKey, processedValues[0]);
                } else {
                    configuration.addKeyValueParameter(processedKey, processedValues);
                }
            }
        }

        if (configuration.isValid()) {
            LogUtils.d(TAG, "Configuration is valid");
        } else {
            LogUtils.e(TAG, "Configuration is not valid");
        }

        return configuration;
    }

    private String[] stringArrayFromReadableArray(ReadableArray readableArray)
    {
        ArrayList<String> result = new ArrayList<>();

        for (int idx = 0; idx < readableArray.size(); idx++) {
            ReadableType type = readableArray.getType(idx);

            switch (type) {
                case Null:
                    break;
                case Boolean:
                    Boolean boolValue = readableArray.getBoolean(idx);
                    result.add(Boolean.toString(boolValue));
                    break;
                case Number:
                    Double doubleValue = readableArray.getDouble(idx);
                    result.add(Double.toString(doubleValue));
                    break;
                case String:
                    result.add(readableArray.getString(idx));
                    break;
                case Map:
                    break;
                case Array:
                    break;
                default:
                    break;
            }
        }

        return result.toArray(new String[]{});
    }
}
