//
//  ATBannerViewController.h
//  RNAdtech
//
//  Created by Andi Anton on 21/08/2017.
//  Copyright © 2017 Facebook. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <ADTECHMobileSDK/ADTECHMobileSDK.h>

@interface ATBannerViewController : UIViewController<ATBannerViewDelegate, ATInterstitialDelegate>

@property (nonatomic) NSString *alias;
@property (nonatomic) NSString *type;
@property (nonatomic) NSString *networkid;
@property (nonatomic) NSString *subnetworkid;

@end
