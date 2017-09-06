//
//  ATBannerViewController.m
//  RNAdtech
//
//  Created by Andi Anton on 21/08/2017.
//  Copyright © 2017 Facebook. All rights reserved.
//

#import "ATBannerViewController.h"
#import <ADTECHMobileSDK/ADTECHMobileSDK.h>

@interface ATBannerViewController ()

@property (nonatomic, strong) ATBannerView *bannerView;
@property (nonatomic, strong) ATInterstitial *interstitial;

@end

@implementation ATBannerViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    /*self.view.autoresizingMask = UIViewAutoresizingFlexibleWidth | UIViewAutoresizingFlexibleHeight;*/
    self.view.backgroundColor = [UIColor whiteColor];
    
    
    [self setupBannerView];

    
    
    //[self setupInterstitialView];
    
}


- (void) setupBannerView {
    ATAdtechAdConfiguration *configuration = [ATAdtechAdConfiguration configuration];
    //configuration.alias = @"home-top-5";
    configuration.alias = @"nzz-app-top-5";
    configuration.domain = @"a.adtech.de";
    configuration.networkID = 1135;
    configuration.subNetworkID = 1;
    
    self.bannerView = [[ATBannerView alloc] initWithFrame:self.view.frame];
    self.bannerView.configuration = configuration;
    self.bannerView.viewController = self;
    self.bannerView.delegate = self;
    
    [self.view addSubview:self.bannerView];
    
    [self.bannerView load];
}



- (void)setupInterstitialView
{
    // create an interstitial
    self.interstitial = [[ATInterstitial alloc] init];
    self.interstitial.delegate = self;
    self.interstitial.viewController = self;
    
    // configure it
    ATAdtechAdConfiguration *configuration = [ATAdtechAdConfiguration configuration];
    configuration.alias = @"interstitial-top-5";
    configuration.networkID = 23;
    configuration.subNetworkID = 4;
    
    // set image resources for close indicator when it's in DefaulState and PressedState.
    configuration.closeIndicator = [ATCloseIndicator closeIndicatorWithNormalStateImage:[UIImage imageNamed:@"close_box_red.png"] andHighlightedStateImage:[UIImage imageNamed:@"close_box_black.png"]];
    
    self.interstitial.configuration = configuration;
    
    [self.interstitial load];
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
}
- (void)didFailFetchingAd:(ATBannerView*)view signals:(NSArray *)signals error:(NSError *)error
{
    // An ad failed to load. The banner will try to fetch another one after a waiting period.
}

#pragma mark -

#pragma mark - ATInterstitialDelegate

- (void)didHideInterstitialAd:(ATInterstitial*)ad
{
    //User dismissed the ad or refresh timer was fired.
    //You should take down the interstitial ad from the screen at this time.
}

- (void)didSuccessfullyFetchInterstitialAd:(ATInterstitial*)ad signals:(NSArray *)signals
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

#pragma mark -


- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
