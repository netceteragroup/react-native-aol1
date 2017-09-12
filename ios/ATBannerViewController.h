//
//  ATBannerViewController.h
//  RNAdtech
//
//  Created by Andi Anton on 21/08/2017.
//  Copyright © 2017 Facebook. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <AdTechMobileSdk/ADTECHMobileSDK.h>

@interface ATBannerViewController : UIViewController

@property (weak, nonatomic) id<ATBannerViewDelegate> bannerDelegate;
@property (weak, nonatomic) id<ATInterstitialDelegate> interstitialDelegate;

@property (nonatomic) NSString *alias;
@property (nonatomic) NSString *type;
@property (nonatomic) NSNumber *networkid;
@property (nonatomic) NSNumber *subnetworkid;

- (void)setupController;

@end
