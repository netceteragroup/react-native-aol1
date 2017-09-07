//
//  RNAdtechView.h
//  RNAdtech
//
//  Created by Andi Anton on 16/08/2017.
//  Copyright © 2017 Facebook. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <Foundation/Foundation.h>
#import <React/RCTView.h>
#import <React/RCTWebView.h>
#import <React/RCTEventDispatcher.h>
#import <React/RCTUtils.h>
#import <React/UIView+React.h>
#import <AdTechMobileSdk/ADTECHMobileSDK.h>

@interface RNAdtechView : RCTView<ATBannerViewDelegate, ATInterstitialDelegate>

@property (nonatomic) NSString *alias;
@property (nonatomic) NSString *type;
@property (nonatomic) NSNumber *networkId;
@property (nonatomic) NSNumber *subnetworkId;

@property (nonatomic, copy) RCTDirectEventBlock onAdFetchSuccess;
@property (nonatomic, copy) RCTDirectEventBlock onAdFetchFail;
@property (nonatomic, copy) RCTDirectEventBlock onInterstitialHidden;

@end
