#import "ATBannerViewController.h"
#import <ADTECHMobileSDK/ADTECHMobileSDK.h>
#import <React/RCTLog.h>
#import <React/RCTBridge.h>

@interface ATBannerViewController ()
{
    ATBannerView *bannerView;
    ATInterstitial *interstitial;
}

@end

@implementation ATBannerViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
}

- (BOOL)areParametersValid
{
    if(!self.alias || !self.networkId || !self.subnetworkId || !self.type) {
        RCTLogError(@"You MUST provide: 'type', 'alias', 'networkId' and 'subnetworkId' parameters in order to see an ad!");
        return NO;
    }

    return YES;
}

- (void)setupController
{
    if(![self areParametersValid]) {
        return;
    }

    if ([self isType:@"banner"]) {
        [self setupBannerView];
    } else if ([self isType:@"interstitial"]) {
        [self setupInterstitialView];
    }
}

- (void)setupBannerView
{
    if (bannerView) {
        [bannerView removeFromSuperview];
    }

    ATAdtechAdConfiguration *configuration = [ATAdtechAdConfiguration configuration];

    configuration.alias = self.alias;
    configuration.networkID = self.networkid.unsignedIntegerValue;
    configuration.subNetworkID = self.subnetworkid.unsignedIntegerValue;

    bannerView = [[ATBannerView alloc] initWithFrame:self.view.frame];
    bannerView.configuration = configuration;
    bannerView.viewController = self;
    bannerView.delegate = self.bannerDelegate;

    [self.view addSubview:bannerView];

    [bannerView load];
}

- (void)setupInterstitialView
{
    // create an interstitial
    interstitial = [[ATInterstitial alloc] init];
    interstitial.delegate = self.interstitialDelegate;
    interstitial.viewController = self;

    // configure it
    ATAdtechAdConfiguration *configuration = [ATAdtechAdConfiguration configuration];

    configuration.alias = self.alias;
    configuration.networkID = self.networkid.unsignedIntegerValue;
    configuration.subNetworkID = self.subnetworkid.unsignedIntegerValue;

    /*
    configuration.alias = @"interstitial-top-5";
    configuration.networkID = 23;
    configuration.subNetworkID = 4;
    */

    // set image resources for close indicator when it's in DefaulState and PressedState.
    configuration.closeIndicator = [ATCloseIndicator closeIndicatorWithNormalStateImage:[UIImage imageNamed:@"close_box_red.png"]
                                                               andHighlightedStateImage:[UIImage imageNamed:@"close_box_black.png"]];

    interstitial.configuration = configuration;

    [interstitial load];
}

#pragma mark - Helpers

- (BOOL)isType:(NSString *)type
{
    return [self.type compare:type options:NSCaseInsensitiveSearch] == NSOrderedSame;
}

- (ATAdtechAdConfiguration *)adConfiguration
{
    ATAdtechAdConfiguration *configuration = [ATAdtechAdConfiguration configuration];

    configuration.alias = self.alias;
    configuration.networkID = [self.networkId unsignedIntegerValue];
    configuration.subNetworkID = [self.subnetworkId unsignedIntegerValue];

    if (self.keyValues.count > 0) {
        for (NSString *key in self.keyValues) {
            NSArray *values = self.keyValues[key];

            NSString *processedKey = [key hasPrefix:@"kv"]
                                     ? [key substringFromIndex:[@"kv" length]]
                                     : key;

            NSArray *processedValues = [values isKindOfClass:[NSArray class]]
            ? values
            : @[values];

            if (processedKey.length > 0) {
                NSError *error;

                if ([configuration addUserKey:processedKey values:processedValues error:&error]) {
                    RCTLogInfo(@"Successfully registered key: '%@' with value '%@'"
                               , processedKey
                               , processedValues);
                } else {
                    RCTLogError(@"Failed to register registered key: '%@' with value '%@'. Reason: %@"
                               , processedKey
                               , processedValues
                               , error.localizedDescription);
                }
            }
        }

    }

    if ([configuration isValid]) {
        RCTLogInfo(@"Successfully configured an ad with:\nAlias: %@\nNetwork ID: %@\nSubnetwork ID: %@\nType: %@"
                   , self.alias
                   , self.networkId
                   , self.subnetworkId
                   , self.type);
    } else {
        RCTLogInfo(@"Failed to configure an ad with:\nAlias: %@\nNetwork ID: %@\nSubnetwork ID: %@\nType: %@"
                   , self.alias
                   , self.networkId
                   , self.subnetworkId
                   , self.type);
    }

    return configuration;
}

@end
