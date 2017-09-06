//
//  ADTECHViewMonitor.h
//  ADTECHMobileSDK
//
//  Created by ADTECH GmbH on 2/25/15.
//  Copyright (c) 2015 ADTECH GmbH. All rights reserved.
//

#import <UIKit/UIKit.h>

@class ADTECHViewVisibilityEvent;

@protocol ADTECHViewMonitorDelegate<NSObject>

- (void)visibilityChangedForView:(UIView*)view to:(float)visibility;

@end


@interface ADTECHViewMonitor : NSObject

@property (nonatomic, weak) id<ADTECHViewMonitorDelegate> delegate;
@property (nonatomic, assign) float updateFrequency;

- (instancetype)initWithTargetView:(UIView *)targetView
                   andBoundingRect:(CGRect)boundingRect;

- (void)start;
- (void)stop;
- (BOOL)isStarted;

- (void)registerEvent:(ADTECHViewVisibilityEvent*)event;

- (float)currentVisibility;

- (NSTimeInterval)totalDuration;
- (float)maxAreaForMinimumInterval:(NSTimeInterval)interval withAccuracy:(float*)accuracy;
- (NSTimeInterval)durationForMinimumArea:(float)area;

@end
