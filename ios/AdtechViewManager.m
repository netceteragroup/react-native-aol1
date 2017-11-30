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
RCT_EXPORT_VIEW_PROPERTY(autoload, BOOL)

RCT_EXPORT_VIEW_PROPERTY(onAdFetchSuccess, RCTDirectEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onAdFetchFail, RCTDirectEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onInterstitialHidden, RCTDirectEventBlock)

static BOOL isLoggingEnabled = NO;

+ (void)setLoggingEnabled:(BOOL)enabled
{
    isLoggingEnabled = enabled;
}

+ (BOOL)isLoggingEnabled
{
    return isLoggingEnabled;
}

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

    RCTUIManager *UIManager = [super.bridge uiManager];

    if (view.reactTag) {
        __block RCTShadowView *shadowView = nil;

        dispatch_sync(RCTGetUIManagerQueue(), ^{
            shadowView = [UIManager shadowViewForReactTag:view.reactTag];
        });
        UIView *reactView = [UIManager viewForReactTag:view.reactTag];

        if (shadowView && reactView) {
            [UIManager setSize:newAdSize forView:adTechView];
        }
    } else {
        RCTLogInfo(@"Ad banner size can't be updated. View is no longer in tree.");
    }
}

RCT_EXPORT_METHOD(pause:(nonnull NSNumber *)reactTag){
    [self.bridge.uiManager addUIBlock:^(__unused RCTUIManager *uiManager, NSDictionary<NSNumber *, UIView *> *viewRegistry) {
        id view = viewRegistry[reactTag];
        if ([view isKindOfClass:[RNAdtechView class]]) {
            RNAdtechView *adTechView = view;
            [adTechView pause];
        }
    }];
}

RCT_EXPORT_METHOD(resume:(nonnull NSNumber *)reactTag){
    [self.bridge.uiManager addUIBlock:^(__unused RCTUIManager *uiManager, NSDictionary<NSNumber *, UIView *> *viewRegistry) {
        id view = viewRegistry[reactTag];
        if ([view isKindOfClass:[RNAdtechView class]]) {
            RNAdtechView *adTechView = view;
            [adTechView resume];
        }
    }];
}

@end
