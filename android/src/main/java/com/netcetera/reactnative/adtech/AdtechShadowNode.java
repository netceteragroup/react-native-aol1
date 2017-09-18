package com.netcetera.reactnative.adtech;

import android.view.View;
import android.view.ViewGroup;

import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.uimanager.UIViewOperationQueue;
import com.facebook.yoga.YogaMeasureFunction;
import com.facebook.yoga.YogaMeasureMode;
import com.facebook.yoga.YogaMeasureOutput;
import com.facebook.yoga.YogaNode;

import com.adtech.mobilesdk.publisher.view.AdtechBannerView;

/**
 * Inspired from ProgressBarShadowNode
 */

public class AdtechShadowNode extends LayoutShadowNode implements YogaMeasureFunction {

    private AdtechView adTechView;

    public AdtechShadowNode() {
        setMeasureFunction(this);
    }


    @Override
    public long measure(
            YogaNode node,
            float width,
            YogaMeasureMode widthMode,
            float height,
            YogaMeasureMode heightMode) {
        return measure(null, node, width, widthMode, height, heightMode);
    }

    @Override
    public void onCollectExtraUpdates(UIViewOperationQueue uiViewOperationQueue) {
        super.onCollectExtraUpdates(uiViewOperationQueue);

        uiViewOperationQueue.enqueueUpdateExtraData(getReactTag(), getReactTag());
    }

    public synchronized long measure(
            AdtechBannerView bannerView,
            YogaNode node,
            float width,
            YogaMeasureMode widthMode,
            float height,
            YogaMeasureMode heightMode
    ) {
        if (adTechView == null) {
            adTechView = AdtechViewManager.createAdtechView(getThemedContext());
        }
        adTechView.measure(yogaToAndroid(widthMode, width), yogaToAndroid(heightMode, height));

        int measuredWidth = adTechView.getMeasuredWidth();
        return YogaMeasureOutput.make(measuredWidth, 0);
    }

    private static int yogaToAndroid(YogaMeasureMode mode, float value) {
        int m;
        switch (mode) {
            case AT_MOST: m = View.MeasureSpec.AT_MOST; break;
            case EXACTLY: m = View.MeasureSpec.EXACTLY; break;
            case UNDEFINED:
            default:
                m = View.MeasureSpec.UNSPECIFIED;
        }

        int v;
        if (value == Float.NaN) {
            v = ViewGroup.LayoutParams.WRAP_CONTENT;
        } else {
            v = (int) value;
        }

        return View.MeasureSpec.makeMeasureSpec(v, m);
    }
}
