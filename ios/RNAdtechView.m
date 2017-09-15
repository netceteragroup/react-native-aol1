#import "RNAdtechView.h"
#import "ATBannerViewController.h"
#import <ADTECHMobileSDK/ADTECHMobileSDK.h>

@interface RNAdtechView() {
    ATBannerViewController *bannerVC;
}

@end

@implementation RNAdtechView

- (instancetype)init
{
    self = [super init];
    
    [self createAdTechView];
    
    return self;
}

- (void)createAdTechView
{
    bannerVC = [[ATBannerViewController alloc] init];

    bannerVC.alias = self.alias;
    bannerVC.type = self.type;
    bannerVC.networkId = self.networkId;
    bannerVC.subnetworkId = self.subnetworkId;
    bannerVC.keyValues = self.keyValues;

    bannerVC.bannerDelegate = self;
    bannerVC.interstitialDelegate = self;

    [self addSubview:bannerVC.view];

    [bannerVC.view.topAnchor constraintEqualToAnchor:self.topAnchor].active = YES;
    [bannerVC.view.bottomAnchor constraintEqualToAnchor:self.bottomAnchor].active = YES;
    [bannerVC.view.leftAnchor constraintEqualToAnchor:self.leftAnchor].active = YES;
    [bannerVC.view.rightAnchor constraintEqualToAnchor:self.rightAnchor].active = YES;
}

#pragma mark - Properties from JS

- (void)didSetProps:(NSArray<NSString *> *)changedProps
{
    if ([changedProps containsObject:@"alias"] ||
        [changedProps containsObject:@"type"] ||
        [changedProps containsObject:@"networkId"] ||
        [changedProps containsObject:@"subnetworkId"]) {

        bannerVC.alias = self.alias;
        bannerVC.type = self.type;
        bannerVC.networkId = self.networkId;
        bannerVC.subnetworkId = self.subnetworkId;
        bannerVC.keyValues = self.keyValues;

        [bannerVC setupController];
    }
}

#pragma mark - ATBannerViewDelegate

- (void)shouldSuspendForAd:(ATBannerView*)view
{
    // if needed, suspend the activity of the host application
}

- (void)shouldResumeForAd:(ATBannerView*)view
{
    // resume the activity of the host application
}

- (void)willLeaveApplicationForAd:(ATBannerView*)view
{
    // the ad triggered the app to enter background (e.g. the user clicked a URL in the ad)
    // you should save your apps state at this point
}
- (void)didFetchNextAd:(ATBannerView*)view signals:(NSArray *)signals
{
    // A new ad finished loading. You can decide you want to show the banner at this point, if this is the first ad shown.
    if (self.onAdFetchSuccess) {
        self.onAdFetchSuccess(@{});
    }
}

- (void)didFetchNextAd:(ATBannerView *)view
{
    // A new ad finished loading. You can decide you want to show the banner at this point, if this is the first ad shown.
    if (self.onAdFetchSuccess) {
        self.onAdFetchSuccess(@{});
    }
}

- (void)didFailFetchingAd:(ATBannerView*)view signals:(NSArray *)signals error:(NSError *)error
{
    // An ad failed to load. The banner will try to fetch another one after a waiting period.
    if (self.onAdFetchFail) {
        self.onAdFetchFail(@{});
    }
}

- (void)didFailFetchingAd:(ATBannerView *)view
{
    // An ad failed to load. The banner will try to fetch another one after a waiting period.
    if (self.onAdFetchFail) {
        self.onAdFetchFail(@{});
    }
}

- (void)didFailFetchingAd:(ATBannerView *)view signals:(NSArray *)signals
{
    // An ad failed to load. The banner will try to fetch another one after a waiting period.
    if (self.onAdFetchFail) {
        self.onAdFetchFail(@{});
    }
}

#pragma mark - ATInterstitialDelegate

- (void)didHideInterstitialAd:(ATInterstitial*)ad
{
    //User dismissed the ad or refresh timer was fired.
    //You should take down the interstitial ad from the screen at this time.
    if (self.onInterstitialHidden) {
        self.onInterstitialHidden(@{});
    }
}

- (void)didSuccessfullyFetchInterstitialAd:(ATInterstitial *)ad
{
    //Ad has been fetched successfully and is ready for display.
    //You should put up the ad on the screen at this time.
    [ad present];
}

- (void)didSuccessfullyFetchInterstitialAd:(ATInterstitial*)ad signals:(NSArray *)signals
{
    //Ad has been fetched successfully and is ready for display.
    //You should put up the ad on the screen at this time.
    [ad present];
}

- (void)didSuccessfullyFetchInterstitialAd:(ATInterstitial *)ad
{
    //Ad has been fetched successfully and is ready for display.
    //You should put up the ad on the screen at this time.
    [ad present];
}

- (void)didFailFetchingInterstitialAd:(ATInterstitial*)ad signals:(NSArray *)signals
{
    //Ad failed fetching.
    //You can call load to try again, if you think the conditions leading to the error have changed.
}

- (void)willLeaveApplicationForInterstitialAd:(ATInterstitial*)ad
{
    //User interaction triggers leaving the application such as opening an app store link.
    //You should save the state of the application when you get this call.
}

- (BOOL)shouldOpenLandingPageForAd:(ATInterstitial*)ad withURL:(NSURL*)URL useBrowser:(ATBrowserViewController *__autoreleasing *)browserViewController
{
    //The ad will try to show the landing page.
    //You can use a custom implementation for opening a landing page here.
    return YES;
}

@end
