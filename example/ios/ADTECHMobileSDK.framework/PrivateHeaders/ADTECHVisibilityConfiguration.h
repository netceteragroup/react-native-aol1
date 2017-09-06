//
//  ADTECHVisibilityConfiguration.h
//  ADTECHMobileSDK
//
//  Created by ADTECH GmbH on 19.03.2015.
//  Copyright (c) 2015 ADTECH GmbH. All rights reserved.
//

#import <Foundation/Foundation.h>

@class ADTECHAd;


@interface ADTECHVisibilityConfiguration : NSObject

@property(nonatomic, readonly) NSUInteger networkID;
@property(nonatomic, readonly) NSUInteger subNetworkID;

@property(nonatomic, readonly) NSString *adID;
@property(nonatomic, readonly) NSString *creativeID;
@property(nonatomic, readonly) NSString *bannerID;
@property(nonatomic, readonly) NSString *pageID;
@property(nonatomic, readonly) NSString *sizeTypeID;
@property(nonatomic, readonly) NSString *placementID;
@property(nonatomic, readonly) NSString *bannerUID;

@property(nonatomic, readonly) NSString *scheme;
@property(nonatomic, readonly) NSString *adServerHost;
@property(nonatomic, readonly) NSURL *viewEventURL;

@property(nonatomic, readonly) BOOL hasCustomEvent;
@property(nonatomic, readonly) NSTimeInterval customTime;
@property(nonatomic, readonly) float customArea;

@property(nonatomic, readonly) BOOL hasGoalEnabled;
@property(nonatomic, readonly) BOOL hasGoalCustomSettings;
@property(nonatomic, readonly) BOOL wasSelectedBySampling;

- (instancetype)initWithAd:(ADTECHAd*)ad;

- (BOOL)isValid;

@end
