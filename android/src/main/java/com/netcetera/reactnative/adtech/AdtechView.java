package com.netcetera.reactnative.adtech;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.adtech.mobilesdk.publisher.ErrorCause;
import com.adtech.mobilesdk.publisher.configuration.AdtechAdConfiguration;
import com.adtech.mobilesdk.publisher.view.AdtechBannerView;
import com.adtech.mobilesdk.publisher.view.AdtechBannerViewCallback;
import com.adtech.mobilesdk.publisher.view.BannerResizeBehavior;
import com.adtech.mobilesdk.publisher.view.BannerResizeProperties;
import com.adtech.mobilesdk.publisher.view.BannerResizeType;
import com.facebook.react.ReactActivity;
import com.netcetera.reactnative.utils.LogUtils;

class AdtechView extends FrameLayout {

    //This counter was added to see if there are sync issues and
    //only one instance per view should be created.
    //It was needed to test on TweetView approach that creates 2 instances of the
    //same view.
    private static int instancesCounter = 0;

    private static final String TAG = AdtechView.class.getCanonicalName();

    private View mainContainer;
    private RelativeLayout loadingContainer;
    private ProgressBar loadingProgressBar;
    private int reactTag;
    private ReactActivity activity;

    public AdtechView(Context context, ReactActivity activity) {
        super(context);
        this.activity = activity;
        instancesCounter++;
        LogUtils.d(TAG, "AdtechView(Context context) " + instancesCounter);
        initContent();

        adtechBannerView = (AdtechBannerView) findViewById(R.id.ad_banner);

    }

    private void initContent() {
        LogUtils.d(TAG, "initContent");
        LayoutInflater.from(getContext()).inflate(R.layout.add_container, this, true);
        mainContainer = this;

        findViews();
        setLoadingView();
    }

    private int countParameter = 0;

    /**
     * 5 parameters are needed
     * type,
     * alias,
     * networkid,
     * subnetworkid
     * height
     */
    private void checkIfAllParametersWereLoaded(){
        countParameter ++;
        if(countParameter == 5){
            loadAlias();
        }
    }

    private String mAlias;

    public void setAlias(String alias) {
        mAlias = alias;
        LogUtils.d(TAG, "setAlias, alias = " + alias);
        checkIfAllParametersWereLoaded();
    }

    private String mType;

    public void setType(String type) {
        mType = type;
        LogUtils.d(TAG, "setType" + type);
        checkIfAllParametersWereLoaded();
    }

    private int mNetworkId;

    public void setNetworkId(int networkId) {
        mNetworkId = networkId;
        LogUtils.d(TAG, "setNetworkId " + networkId);
        checkIfAllParametersWereLoaded();
    }

    private int mSubnetworkId;

    public void setSubnetworkId(int subnetworkId) {
        mSubnetworkId = subnetworkId;
        LogUtils.d(TAG, "setSubnetworkId " + subnetworkId);
        checkIfAllParametersWereLoaded();
    }

    private int mHeight;

    public void setHeight(int height){
        mHeight = height;
        LogUtils.d(TAG, "setHeight, height = " + height);
        checkIfAllParametersWereLoaded();
    }

    private void findViews() {
        loadingContainer = (RelativeLayout) mainContainer.findViewById(R.id.loading_container);
        loadingProgressBar = (ProgressBar) mainContainer.findViewById(R.id.loading_progress_bar);
    }

    private boolean isLoaded = false;

    private void setAdtechView() {
        loadingProgressBar.setVisibility(View.INVISIBLE);
        if(mType.equalsIgnoreCase("interstitial")) {
            setupInterstitialAd();
        } else {
            adtechBannerView.setVisibility(View.VISIBLE);
            adtechBannerView.bringToFront();
            adtechBannerView.requestLayout();
        }
    }


    private void setLoadingView() {
        LogUtils.d(TAG, "setLoadingView");
        loadingContainer.setVisibility(View.VISIBLE);
    }

    private void loadAlias() {
        LogUtils.d(TAG, "loadAlias, alias = " + mAlias);
        if(mType.equalsIgnoreCase("banner")){
            setupBannerAd();
        } else if(mType.equalsIgnoreCase("interstitial")){
            setupInterstitialAd();
        }
    }

    private AdtechBannerView adtechBannerView;

    private FullScreenDialog dialog = null;

    private void setupInterstitialAd(){
        if(dialog == null){
            dialog = new FullScreenDialog(getContext(), mAlias);
        }
        if (false == dialog.isShowing()) {
            dialog.show();
        }
    }

    private void setupBannerAd() {
        LogUtils.d(TAG, "setupBannerAd");
        //parameters setup bannerad start
        AdtechAdConfiguration adtechAdConfiguration = new AdtechAdConfiguration("SampleApp");
        adtechAdConfiguration.setDomain("a.adtech.de");
        //official example configuration is not working
        adtechAdConfiguration.setAlias(mAlias);
        adtechAdConfiguration.setNetworkId(mNetworkId);
        adtechAdConfiguration.setSubnetworkId(mSubnetworkId);
        //parameters setup bannerad end
        //Mandatory: provide the banner view with the configuration object. This can be replaced at runtime with another
        //configuration object pointing to a different ad-placement.
        adtechBannerView.setAdConfiguration(adtechAdConfiguration);

        //Optional: set a call-back to be notified of certain events in the life-cycle of the ad */
        adtechBannerView.setViewCallback(
                new AdtechBannerViewCallback() {

                    @Override
                    public void onAdSuspend() {
                        // This method is called to inform that the application should suspend its job, when the advertisement has been
                        // resized and covers the whole screen. <p> This might be useful in applications like games, or applications
                        // having video play-back.
                        LogUtils.d(TAG, "onAdSuspend");
                    }

                    @Override
                    public void onAdSuccess() {
                        // This method is called when an ad was downloaded successfully.
                        LogUtils.d(TAG, "onAdSuccess");
                    }

                    @Override
                    public void onAdResume() {
                        // This method is called when the application has been resumed from the suspended state.
                        LogUtils.d(TAG, "onAdResume");
                    }

                    @Override
                    public void onAdLeave() {
                        // This method is called when the current application is left because the user clicked a banner which will be
                        // opened in a the external browser.
                        LogUtils.d(TAG, "onAdLeave");
                    }

                    @Override
                    public void onAdFailure(ErrorCause cause) {
                        // This method is called when an ad download failed. This could happen because of networking reasons or other
                        // server communication reasons.
                        LogUtils.d(TAG, "onAdFailure");
                    }

                    @Override
                    public void onCustomMediation() {
                        // This method is called when the server send a request to trigger a custom mediation event.
                        LogUtils.d(TAG, "onCustomMediation");
                    }

                    @Override
                    public BannerResizeBehavior onAdWillResize(BannerResizeProperties resizeProperties) {
                        // This method is called when the ad is going to be resized.
                        LogUtils.d(TAG, "onAdWillResize");
                        return new BannerResizeBehavior(BannerResizeType.INLINE, 3000);
                    }

                    @Override
                    public void onAdDidResize(BannerResizeProperties resizeProperties) {
                        // This method is called when the ad is resized.
                        LogUtils.d(TAG, "onAdDidResize");
                    }

                    @Override
                    public void onAdFailureWithSignal(ErrorCause cause, int... signals) {
                        // This method is called when an ad could not be fetched, but signal codes were provided by server.
                        LogUtils.d(TAG, "onAdSuccess");
                    }

                    @Override
                    public void onAdSuccessWithSignal(int... signals) {
                        // This method is called when an ad is successfully fetched and signal codes were provided by server.
                        LogUtils.d(TAG, "onAdSuccessWithSignal " + instancesCounter);
                        setAdtechView();
                        isLoaded = true;
                    }
                });
        adtechBannerView.load();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        adtechBannerView.stop();
    }

    //refresh start
    //Code from TweetView does not work ok. There are 2 instances of the view.
    //This solution with requestLayout works ok.
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
            adtechBannerView.requestLayout();
        }
    };
    //refresh end
}