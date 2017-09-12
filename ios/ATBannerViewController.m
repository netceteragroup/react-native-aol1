//
//  ATBannerViewController.m
//  RNAdtech
//
//  Created by Andi Anton on 21/08/2017.
//  Copyright © 2017 Facebook. All rights reserved.
//

#import "ATBannerViewController.h"
#import <ADTECHMobileSDK/ADTECHMobileSDK.h>

@interface ATBannerViewController () {
    ATBannerView *bannerView;
    ATInterstitial *interstitial;
}

@end

@implementation ATBannerViewController

- (void)viewDidLoad {
    [super viewDidLoad];
}

- (BOOL)areParametersValid {
    if(self.alias == nil || self.networkid == nil || self.subnetworkid == nil){
        NSLog(@"\nERROR: you MUST provide alias, networkid and subnetworkid values in order to see the ad!\n");
        return NO;
    }
    
    return YES;
}

- (void)setupController
{
    if([[self.type lowercaseString] isEqualToString:@"banner"]){
        [self setupBannerView];
    } else if([[self.type lowercaseString] isEqualToString:@"interstitial"]) {
        [self setupInterstitialView];
    }
}

- (void)setupBannerView {

    if(![self areParametersValid]){
        return;
    }
    if (bannerView) {
        [bannerView removeFromSuperview];
    }

    ATAdtechAdConfiguration *configuration = [ATAdtechAdConfiguration configuration];
    
    configuration.alias = self.alias;
    configuration.domain = @"a.adtech.de";
    configuration.networkID = [self.networkid integerValue];
    configuration.subNetworkID = [self.subnetworkid integerValue];
    configuration.openLandingPagesThroughBrowser = NO;

    bannerView = [[ATBannerView alloc] initWithFrame:self.view.frame];
    bannerView.configuration = configuration;
    bannerView.viewController = self;
    bannerView.delegate = self.bannerDelegate;
    
    [self.view addSubview:bannerView];
    
    [bannerView load];
}

- (void)setupInterstitialView
{
    if(![self areParametersValid]){
        return;
    }
    
    // create an interstitial
    interstitial = [[ATInterstitial alloc] init];
    interstitial.delegate = self.interstitialDelegate;
    interstitial.viewController = self;
    
    // configure it
    ATAdtechAdConfiguration *configuration = [ATAdtechAdConfiguration configuration];
    
    configuration.alias = self.alias;
    configuration.domain = @"a.adtech.de";
    configuration.networkID = [self.networkid integerValue];
    configuration.subNetworkID = [self.subnetworkid integerValue];
    configuration.openLandingPagesThroughBrowser = NO;
    
    /*
    configuration.alias = @"interstitial-top-5";
    configuration.networkID = 23;
    configuration.subNetworkID = 4;
    */
    
    // set image resources for close indicator when it's in DefaulState and PressedState.
    configuration.closeIndicator = [ATCloseIndicator closeIndicatorWithNormalStateImage:[UIImage imageNamed:@"close_box_red.png"] andHighlightedStateImage:[UIImage imageNamed:@"close_box_black.png"]];
    
    interstitial.configuration = configuration;
    
    [interstitial load];
}

@end
