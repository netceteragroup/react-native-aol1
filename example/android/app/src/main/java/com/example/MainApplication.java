package com.example;

import android.app.Application;

import com.adtech.mobilesdk.publisher.log.SDKLogLevel;
import com.adtech.mobilesdk.publisher.log.SDKLogger;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.soloader.SoLoader;
import com.netcetera.reactnative.adtech.AdtechViewPackage;

import java.util.Arrays;
import java.util.List;

public class MainApplication extends Application implements ReactApplication {

    private static final String TAG = MainApplication.class.getCanonicalName();

    private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
        @Override
        public boolean getUseDeveloperSupport() {
            return BuildConfig.DEBUG;
        }

        @Override
        protected List<ReactPackage> getPackages() {
            return Arrays.<ReactPackage>asList(
                    new MainReactPackage(),
                    new AdtechViewPackage("SampleApp", "a.adtech.de")
            );
        }
    };

    @Override
    public ReactNativeHost getReactNativeHost() {
        return mReactNativeHost;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SoLoader.init(this, /* native exopackage */ false);
        /**
         * From addtech documentation.
         * Set the logging level for the SDK to debug.
        */
        SDKLogger.setLogLevel(SDKLogLevel.D);
    }

}
