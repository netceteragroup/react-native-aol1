#import <Foundation/Foundation.h>
#import "AdtechViewManager.h"
#import "RNAdtechShadowView.h"
#import <React/RCTLog.h>
#import <React/RCTUIManager.h>

@implementation AdtechViewManager

RCT_EXPORT_MODULE();

RCT_EXPORT_VIEW_PROPERTY(alias, NSString *)
RCT_EXPORT_VIEW_PROPERTY(type, NSString *)
RCT_EXPORT_VIEW_PROPERTY(networkId, NSNumber *)
RCT_EXPORT_VIEW_PROPERTY(subnetworkId, NSNumber *)
RCT_EXPORT_VIEW_PROPERTY(keyValues, NSDictionary *)
RCT_EXPORT_VIEW_PROPERTY(maxHeight, NSNumber *)

RCT_EXPORT_VIEW_PROPERTY(onAdFetchSuccess, RCTDirectEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onAdFetchFail, RCTDirectEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onInterstitialHidden, RCTDirectEventBlock)

- (UIView *)view
{
    RNAdtechView *adTechView = [RNAdtechView new];
    adTechView.delegate = self;

    return adTechView;
}

- (RCTShadowView *)shadowView
{
    return [RNAdtechShadowView new];
}

- (void)adTechView:(RNAdtechView *)view requestsResize:(CGSize)newSize
{
    UIView *adTechView = view;
    CGSize newAdSize = newSize;

    RCTLogInfo(@"New Ad banner size: %f,%f", newAdSize.width, newAdSize.height);
    //getting the UI manager and set the intrinsic content size of the tweet view
    RCTUIManager *UIManager = [super.bridge uiManager];

    [UIManager setSize:newAdSize forView:adTechView];
}

@end
