package com.netcetera.reactnative.adtech;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.uimanager.UIManagerModule;
import com.netcetera.reactnative.utils.LogUtils;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.BaseViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.common.MapBuilder;

import javax.annotation.Nullable;

public class AdtechViewManager
        extends BaseViewManager<AdtechView, AdtechShadowNode>
        implements AdtechView.SizeChangeListener {

    public static final int COMMAND_PAUSE = 0;
    public static final int COMMAND_RESUME = 1;

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

    @ReactProp(name = "maxHeight")
    public void setMaxHeight(AdtechView view, int maxHeight) {
        LogUtils.d(TAG, "maxHeight = " + maxHeight);
        view.setMaxHeight(maxHeight);
    }

    @ReactProp(name = "autoload")
    public void setAutoload(AdtechView view, boolean autoload) {
        LogUtils.d(TAG, "autoload = " + autoload);
        view.setAutoload(autoload);
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
    protected void onAfterUpdateTransaction(AdtechView view) {
        super.onAfterUpdateTransaction(view);
        view.checkIfAllParametersWereLoadedAndLoad();
    }

    @javax.annotation.Nullable
    @Override
    public Map<String, Integer> getCommandsMap() {
        Map<String, Integer> map = new HashMap<String, Integer>();
        /*0*/ map.put("pause", COMMAND_PAUSE);
        /*1*/ map.put("resume",COMMAND_RESUME);
        return map;
    }

    @Override
    public void receiveCommand(AdtechView root, int commandId, @javax.annotation.Nullable ReadableArray args) {
        Assertions.assertNotNull(root);
        Assertions.assertNotNull(args);

        switch (commandId) {
            case COMMAND_PAUSE:{
                root.pauseAd();
                return;
            }
            case COMMAND_RESUME: {
                root.resumeAd();
                return;
            }

            default:
                throw new IllegalArgumentException(String.format(
                        "Unsupported command %d received by %s.",
                        commandId,
                        getClass().getSimpleName()));
        }
        //super.receiveCommand(root, commandId, args);
    }

    @Override
    public AdtechView createViewInstance(ThemedReactContext context) {
        AdtechView view = new AdtechView(
                context
                , context.getCurrentActivity()
                , appName
                , domain);

        view.addSizeChangeListener(this);
        return view;
    }

    @Override
    public void onDropViewInstance(AdtechView view) {
        view.removeSizeChangeListener(this);
        view.disposeAd();
        super.onDropViewInstance(view);
    }


    @NonNull
    public static AdtechView createAdtechView(ThemedReactContext context) {
        return new AdtechView(context
                , context.getCurrentActivity()
                , ""
                , ""
        );
    }

    @Override
    public @Nullable Map getExportedCustomDirectEventTypeConstants() {
        return MapBuilder.of(
                "onAdFetchSuccess", MapBuilder.of("registrationName", "onAdFetchSuccess"),
                "onAdFetchFail", MapBuilder.of("registrationName", "onAdFetchFail"),
                "onInterstitialHidden", MapBuilder.of("registrationName", "onInterstitialHidden")
        );
    }

    //start TweetShadowNode methods
    @Override
    public void updateExtraData(AdtechView view, Object extraData) {
        view.setReactTag((Integer) extraData);
        view.respondToNewProps();
    }

    @Override
    public AdtechShadowNode createShadowNodeInstance() {
        return new AdtechShadowNode();
    }

    @Override
    public Class<AdtechShadowNode> getShadowNodeClass() {
        return AdtechShadowNode.class;
    }

    @Override
    public void onSizeChanged(AdtechView view, final int width, final int height) {
        LogUtils.d(TAG, "Adtech changed size: " + width + ", " + height);
        ReactContext ctx = (ReactContext) view.getContext();
        final UIManagerModule uiManager = ctx.getNativeModule(UIManagerModule.class);
        final int reactTag = view.getReactTag();

        ctx.runOnNativeModulesQueueThread(new Runnable() {
            @Override
            public void run() {
                uiManager.updateNodeSize(reactTag, width, height);
            }
        });
    }
}
