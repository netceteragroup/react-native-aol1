package com.netcetera.reactnative.adtech;

import java.util.Map;

import com.netcetera.reactnative.utils.LogUtils;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.ReactActivity;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.common.MapBuilder;

import javax.annotation.Nullable;

public class AdtechViewManager extends SimpleViewManager<AdtechView> {

    private static final String TAG = AdtechViewManager.class.getCanonicalName();
    private String appName;
    private String domain;

    public AdtechViewManager(String appName, String domain) {
        this.domain = domain;
        this.appName = appName;
    }

    @Override
    public String getName() {
        return "AdtechView";
    }

    @ReactProp(name = "alias")
    public void setAlias(AdtechView view, String alias) {
        LogUtils.d(TAG, "alias =" + alias);
        view.setAlias(alias);
    }

    @ReactProp(name = "type")
    public void setType(AdtechView view, String type) {
        LogUtils.d(TAG, "type = " + type);
        view.setType(type);
    }

    @ReactProp(name = "networkId")
    public void setNetworkId(AdtechView view, int networkId) {
        LogUtils.d(TAG, "newtorkId = " + networkId);

        view.setNetworkId(networkId);
    }

    @ReactProp(name = "subnetworkId")
    public void setSubnetworkId(AdtechView view, int subnetworkId) {
        LogUtils.d(TAG, "subnetworkId = " + subnetworkId);
        view.setSubnetworkId(subnetworkId);
    }

    @ReactProp(name = "keyValues")
    public void setKeyValues(AdtechView view, ReadableMap keyValues) {
        LogUtils.d(TAG, "keyValues = " + keyValues);
        view.setKeyValues(keyValues);
    }

    /**
     * to_be_investigated
     * To investigate if this setHeight method can be removed because I think there is already a
     * setHeight method from React Native framework.
     */
    @ReactProp(name = "height")
    public void setHeight(AdtechView view, int height) {
        LogUtils.d(TAG, "height = " + height);
        view.setHeight(height);
    }

    @Override
    public AdtechView createViewInstance(ThemedReactContext context) {
        AdtechView view = new AdtechView(
                context
                , (ReactActivity)context.getCurrentActivity()
                , appName
                , domain);
        return view;
    }

    @Override
    public @Nullable Map getExportedCustomDirectEventTypeConstants() {
        return MapBuilder.of(
                "onAdFetchSuccess", MapBuilder.of("registrationName", "onAdFetchSuccess"),
                "onAdFetchFail", MapBuilder.of("registrationName", "onAdFetchFail"),
                "onInterstitialHidden", MapBuilder.of("registrationName", "onInterstitialHidden")
        );
    }
}
