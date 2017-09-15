
package com.netcetera.reactnative.adtech;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AdtechViewPackage implements ReactPackage {

    private String appName;
    private String domain;

    public AdtechViewPackage(String appName, String domain) {
        this.appName = appName;
        this.domain = domain;
    }

    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }

    @Override
    public List<ViewManager> createViewManagers(
            ReactApplicationContext reactContext) {
        return Arrays.<ViewManager>asList(
                new AdtechViewManager(appName, domain)
        );
    }
}