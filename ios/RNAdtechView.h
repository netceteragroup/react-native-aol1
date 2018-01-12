#import <UIKit/UIKit.h>
#import <Foundation/Foundation.h>
#import <React/RCTView.h>
#import <React/RCTWebView.h>
#import <React/RCTEventDispatcher.h>
#import <React/RCTUtils.h>
#import <React/UIView+React.h>
#import <AdTechMobileSdk/ADTECHMobileSDK.h>

@class RNAdtechView;

@protocol RNAdtechViewDelegate <NSObject>
@optional

- (void)adTechView:(RNAdtechView *)view requestsResize:(CGSize)newSize;
@end

@interface RNAdtechView : RCTView<ATBannerViewDelegate, ATInterstitialDelegate>

@property (nonatomic, weak) id<RNAdtechViewDelegate> delegate;

@property (nonatomic) NSString *alias;
@property (nonatomic) NSString *type;
@property (nonatomic) NSNumber *networkId;
@property (nonatomic) NSNumber *subnetworkId;
@property (nonatomic) NSDictionary *keyValues;
@property (nonatomic) NSNumber *maxHeight;
@property (nonatomic, assign) BOOL autoload;

@property (nonatomic, copy) RCTDirectEventBlock onAdFetchSuccess;
@property (nonatomic, copy) RCTDirectEventBlock onAdFetchFail;
@property (nonatomic, copy) RCTDirectEventBlock onInterstitialHidden;

@property (nonatomic, weak) NSArray *signalsForEmptyAds;

- (void)pause;
- (void)resume;

@end
