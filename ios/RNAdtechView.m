#import "RNAdtechView.h"
#import "ATBannerViewController.h"
#import <ADTECHMobileSDK/ADTECHMobileSDK.h>
#import <React/RCTLog.h>
#import "AdtechViewManager.h"

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

- (void)pause
{
    [bannerVC pause];
}

- (void)resume
{
    [bannerVC resume];
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
        bannerVC.autoload = self.autoload;

        [bannerVC setupController];
    }
}

#pragma mark - ATBannerViewDelegate

- (void)shouldSuspendForAd:(ATBannerView *)view
{
}

- (void)shouldResumeForAd:(ATBannerView *)view
{
}

- (void)willLeaveApplicationForAd:(ATBannerView *)view
{
}

- (void)didFetchNextAd:(ATBannerView *)adTechView signals:(NSArray *)signals
{
    [self bannerFetchedSuccessfully:adTechView];
}

- (void)didFetchNextAd:(ATBannerView *)adTechView
{
    [self bannerFetchedSuccessfully:adTechView];
}

- (void)didFailFetchingAd:(ATBannerView *)view signals:(NSArray *)signals error:(NSError *)error
{
    [self bannerFetchFailed:view];
}

- (void)didFailFetchingAd:(ATBannerView *)view
{
    [self bannerFetchFailed:view];
}

- (void)didFailFetchingAd:(ATBannerView *)view signals:(NSArray *)signals
{
    [self bannerFetchFailed:view];
}

#pragma mark - ATInterstitialDelegate

- (void)didHideInterstitialAd:(ATInterstitial *)ad
{
    if (self.onInterstitialHidden) {
        self.onInterstitialHidden(@{});
    }
}

- (void)didSuccessfullyFetchInterstitialAd:(ATInterstitial *)ad
{
    if (self.onAdFetchSuccess) {
        self.onAdFetchSuccess(@{});
    }

    [ad present];
}

- (void)didSuccessfullyFetchInterstitialAd:(ATInterstitial *)ad signals:(NSArray *)signals
{
    if (self.onAdFetchSuccess) {
        self.onAdFetchSuccess(@{});
    }
    [ad present];
}


- (void)didFailFetchingInterstitialAd:(ATInterstitial *)ad signals:(NSArray *)signals
{
    if (self.onAdFetchFail) {
        self.onAdFetchFail(@{});
    }
}

- (void)willLeaveApplicationForInterstitialAd:(ATInterstitial *)ad
{
}

- (BOOL)shouldOpenLandingPageForAd:(ATInterstitial *)ad withURL:(NSURL*)URL useBrowser:(ATBrowserViewController *__autoreleasing *)browserViewController
{
    if ([URL.scheme compare:@"http" options:NSCaseInsensitiveSearch] == NSOrderedSame ||
        [URL.scheme compare:@"https" options:NSCaseInsensitiveSearch] == NSOrderedSame ) {

        if ([[UIApplication sharedApplication] canOpenURL:URL]) {
            [[UIApplication sharedApplication] openURL:URL];
            return NO;
        }

        return YES;
    }
    return YES;
}

#pragma mark - Helpers

- (void)bannerFetchedSuccessfully:(ATBannerView *)bannerView
{
    CGSize desiredSize = [bannerView sizeThatFits:CGSizeMake(self.bounds.size.width, self.maxHeight.floatValue)];
    desiredSize = CGSizeMake(desiredSize.width, self.maxHeight.floatValue);
    if ([AdtechViewManager isLoggingEnabled]) {
        RCTLogInfo(@"DESIRED SIZE = %f   %f", desiredSize.width, desiredSize.height);
    }

    [self.delegate adTechView:self requestsResize:desiredSize];

    // A new ad finished loading. You can decide you want to show the banner at this point, if this is the first ad shown.
    if (self.onAdFetchSuccess) {
        self.onAdFetchSuccess(@{});
    }
}

- (void)bannerFetchFailed:(ATBannerView *)bannerView
{
    CGSize desiredSize = CGSizeZero;
    if ([AdtechViewManager isLoggingEnabled]) {
        RCTLogInfo(@"DESIRED SIZE = %f   %f", desiredSize.width, desiredSize.height);
    }
    [self.delegate adTechView:self requestsResize:desiredSize];

    // A new ad finished loading. You can decide you want to show the banner at this point, if this is the first ad shown.
    if (self.onAdFetchFail) {
        self.onAdFetchFail(@{});
    }
}

@end
