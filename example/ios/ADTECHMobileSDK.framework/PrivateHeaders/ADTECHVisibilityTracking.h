//
//  ADTECHVisibilityTracking.h
//  ADTECHMobileSDK
//
//  Created by ADTECH GmbH on 4/3/15.
//  Copyright (c) 2015 ADTECH GmbH. All rights reserved.
//

#import <Foundation/Foundation.h>

@class ADTECHAdView;
@class ADTECHVisibilityConfiguration;
@class ADTECHViewMonitor;

@interface ADTECHVisibilityTracking : NSObject

@property (nonatomic, strong, readonly) ADTECHViewMonitor *viewMonitor;
@property (nonatomic, strong, readonly) ADTECHVisibilityConfiguration *configuration;

- (instancetype)initWithTargetView:(UIView *)targetView
                   andBoundingRect:(CGRect)boundingRect
                  andConfiguration:(ADTECHVisibilityConfiguration *)configuration;

- (void)startTracking;
- (void)stopTracking;

@end
