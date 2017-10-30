#import "ATBannerViewController.h"
#import <ADTECHMobileSDK/ADTECHMobileSDK.h>
#import <React/RCTLog.h>
#import <React/RCTBridge.h>
#import "AdtechViewManager.h"

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

- (void)pause
{
    if (bannerView) {
        bannerView.visible = NO;
    }
}

- (void)resume
{
    if (bannerView) {
        [bannerView load];
        bannerView.visible = YES;
    }
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

- (BOOL)areParametersValid
{
    if(!self.alias || !self.networkId || !self.subnetworkId || !self.type) {
        if ([AdtechViewManager isLoggingEnabled]) {
            RCTLogError(@"You MUST provide: 'type', 'alias', 'networkId' and 'subnetworkId' parameters in order to see an ad!");
        }
        return NO;
    }

    return YES;
}

- (void)setupBannerView
{
    if (bannerView) {
        [bannerView removeFromSuperview];
    }

    ATAdtechAdConfiguration *configuration = [self adConfiguration];

    bannerView = [[ATBannerView alloc] initWithFrame:self.view.frame];
    bannerView.configuration = configuration;
    bannerView.viewController = self;
    bannerView.delegate = self.bannerDelegate;

    [self.view addSubview:bannerView];

    if (self.autoload) {
        [bannerView load];
    }
}

- (void)setupInterstitialView
{
    interstitial = [[ATInterstitial alloc] init];
    interstitial.delegate = self.interstitialDelegate;
    interstitial.viewController = [UIApplication sharedApplication].keyWindow.rootViewController;
    interstitial.backgroundColor = [UIColor colorWithRed:0 green:0 blue:0 alpha:0.8];
    
    ATAdtechAdConfiguration *configuration = [self adConfiguration];

    UIImage *normalStateImage = [UIImage imageNamed:@"close_box_red.png"];
    UIImage *highlightedStateImage = [UIImage imageNamed:@"close_box_black.png"];

    configuration.closeIndicator = [ATCloseIndicator closeIndicatorWithNormalStateImage:normalStateImage
                                                               andHighlightedStateImage:highlightedStateImage];

    interstitial.configuration = configuration;

    if (self.autoload) {
        [interstitial load];
    }
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
                    if ([AdtechViewManager isLoggingEnabled]) {
                        RCTLogInfo(@"Successfully registered key: '%@' with value '%@'"
                                   , processedKey
                                   , processedValues);
                    }
                } else {
                    if ([AdtechViewManager isLoggingEnabled]) {
                        RCTLogError(@"Failed to register registered key: '%@' with value '%@'. Reason: %@"
                                    , processedKey
                                    , processedValues
                                    , error.localizedDescription);
                    }
                }
            }
        }

    }

    if ([configuration isValid]) {
        if ([AdtechViewManager isLoggingEnabled]) {
            RCTLogInfo(@"Successfully configured an ad with:\nAlias: %@\nNetwork ID: %@\nSubnetwork ID: %@\nType: %@"
                       , self.alias
                       , self.networkId
                       , self.subnetworkId
                       , self.type);
        }
    } else {
        if ([AdtechViewManager isLoggingEnabled]) {
            RCTLogInfo(@"Failed to configure an ad with:\nAlias: %@\nNetwork ID: %@\nSubnetwork ID: %@\nType: %@"
                       , self.alias
                       , self.networkId
                       , self.subnetworkId
                       , self.type);
        }
    }

    return configuration;
}

@end
