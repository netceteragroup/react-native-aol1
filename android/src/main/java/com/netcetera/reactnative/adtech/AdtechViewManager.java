package com.netcetera.reactnative.adtech;

import android.util.Log;
import java.util.Map;

import com.facebook.react.ReactActivity;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.common.MapBuilder;

import javax.annotation.Nullable;

public class AdtechViewManager extends
        SimpleViewManager<AdtechView> {

    private static final String TAG = AdtechViewManager.class.getCanonicalName();
    private String appName;

    @Override
    public String getName() {
        return "AdtechView";
    }

    @ReactProp(name = "alias")
    public void setAlias(AdtechView view, String alias) {
        Log.d(TAG, "setAlias");
        view.setAlias(alias);
    }

    @ReactProp(name = "type")
    public void setType(AdtechView view, String type) {
        Log.d(TAG, "setType");
        view.setType(type);
    }

    @ReactProp(name = "networkId")
    public void setNetworkId(AdtechView view, int networkId) {
        Log.d(TAG, "setNetworkId");

        view.setNetworkId(networkId);
    }

    @ReactProp(name = "subnetworkId")
    public void setSubnetworkId(AdtechView view, int subnetworkId) {
        Log.d(TAG, "setSubnetworkId");
        view.setSubnetworkId(subnetworkId);
    }

    /**
     * to_be_investigated
     * To investigate if this setHeight method can be removed because I think there is already a
     * setHeight method from React Native framework.
     */
    @ReactProp(name = "height")
    public void setHeight(AdtechView view, int height) {
        Log.d(TAG, "setHeight");
        view.setHeight(height);
    }

    @Override
    public AdtechView createViewInstance(ThemedReactContext context) {
        AdtechView view = new AdtechView(context, (ReactActivity)context.getCurrentActivity(),
                appName);
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

    public AdtechViewManager(String appName) {
        this.appName = appName;
    }
}
