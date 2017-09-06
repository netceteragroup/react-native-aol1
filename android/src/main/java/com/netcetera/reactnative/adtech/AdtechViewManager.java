package com.netcetera.reactnative.adtech;

import android.util.Log;

import com.facebook.react.ReactActivity;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

public class AdtechViewManager extends
        SimpleViewManager<AdtechView> {

    private static final String TAG = AdtechViewManager.class.getCanonicalName();


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
        AdtechView view = new AdtechView(context, (ReactActivity)context.getCurrentActivity());
        return view;
    }


}
