package com.netcetera.reactnative.adtech;

import java.util.ArrayList;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.adtech.mobilesdk.publisher.view.AdtechInterstitialView;
import com.adtech.mobilesdk.publisher.view.AdtechInterstitialViewCallback;
import com.facebook.react.ReactActivity;
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

class AdtechView extends RelativeLayout {
    private static final String TAG = AdtechView.class.getCanonicalName();

    private View mainContainer;
    private RelativeLayout loadingContainer;
    private ProgressBar loadingProgressBar;

    private ReactActivity activity;
    private String appName;
    private String domain;

    private AdtechBannerView adtechBannerView;
    private AdtechInterstitialView adtechInterstitialView;

    public AdtechView(Context context, ReactActivity activity, String appName, String domain) {
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

    private void checkIfAllParametersWereLoaded() {
        if (alias != null && type != null && networkId > 0 && subnetworkId > 0)
        {
            loadAd();
        }
    }

    private String alias;

    public void setAlias(String alias) {
        this.alias = alias;
        checkIfAllParametersWereLoaded();
    }

    private String type;

    public void setType(String type) {
        this.type = type;
        checkIfAllParametersWereLoaded();
    }

    private int networkId;

    public void setNetworkId(int networkId) {
        this.networkId = networkId;
        checkIfAllParametersWereLoaded();
    }

    private int subnetworkId;

    public void setSubnetworkId(int subnetworkId) {
        this.subnetworkId = subnetworkId;
        checkIfAllParametersWereLoaded();
    }

    private ReadableMap keyValues;

    public void setKeyValues(ReadableMap keyValues) {
        this.keyValues = keyValues;
        checkIfAllParametersWereLoaded();
    }

    private int height;

    public void setHeight(int height){
        this.height = height;
        checkIfAllParametersWereLoaded();
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
            setupInterstitialAd();
        }
    }

    private void setupBannerAd() {
        if (adtechBannerView == null) {
            LayoutParams params = new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT
            );

            adtechBannerView = new AdtechBannerView(getContext());
            adtechBannerView.setLayoutParams(params);
            addView(adtechBannerView);

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
                        adFecthedSuccessfully();
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
                        adFetchFailed();
                    }

                    @Override
                    public void onCustomMediation() {
                        LogUtils.d(TAG, "onCustomMediation");
                    }

                    @Override
                    public BannerResizeBehavior onAdWillResize(BannerResizeProperties resizeProperties) {
                        LogUtils.d(TAG, "onAdWillResize");
                        return new BannerResizeBehavior(BannerResizeType.INLINE, 3000);
                    }

                    @Override
                    public void onAdDidResize(BannerResizeProperties resizeProperties) {
                        LogUtils.d(TAG, "onAdDidResize");
                    }

                    @Override
                    public void onAdFailureWithSignal(ErrorCause cause, int... signals) {
                        LogUtils.d(TAG, "onAdSuccess");
                        triggerAnEvent("onAdFetchFail");
                    }

                    @Override
                    public void onAdSuccessWithSignal(int... signals) {
                        adFecthedSuccessfully();
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

        adtechInterstitialView.setAdConfiguration(getAdConfiguration());
        adtechInterstitialView.setViewCallback(new AdtechInterstitialViewCallback() {
            @Override
            public void onAdLeave() {
                super.onAdLeave();
            }

            @Override
            public void onAdDismiss() {
                super.onAdDismiss();
                adInterstitialHidden();
            }

            @Override
            public void onAdSuccess() {
                super.onAdSuccess();
                adFecthedSuccessfully();

                LogUtils.d(TAG, "onAdSuccess");
                adtechInterstitialView.setVisibility(View.VISIBLE);
                adtechInterstitialView.bringToFront();
                adtechInterstitialView.requestLayout();
            }

            @Override
            public void onAdSuccessWithSignal(int... signals) {
                super.onAdSuccessWithSignal(signals);
                adFecthedSuccessfully();
                adtechInterstitialView.setVisibility(View.VISIBLE);
                adtechInterstitialView.bringToFront();
                adtechInterstitialView.requestLayout();
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

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        if (adtechBannerView != null) {
            adtechBannerView.stop();
        }
        if (adtechInterstitialView != null) {
            adtechInterstitialView.stop();
        }
    }

    @Override
    public void requestLayout() {
        super.requestLayout();
        post(measureAndLayout);
    }

    private final Runnable measureAndLayout = new Runnable() {
        @Override
        public void run() {
            measure(MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(getHeight(), MeasureSpec.EXACTLY));
            layout(getLeft(), getTop(), getRight(), getBottom());
            if (adtechBannerView != null) {
                adtechBannerView.requestLayout();
            }
        }
    };

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

                configuration.addKeyValueParameter(processedKey, processedValues);
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