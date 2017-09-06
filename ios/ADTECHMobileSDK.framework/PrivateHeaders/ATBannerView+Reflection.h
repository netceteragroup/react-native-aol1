//
//  ATBannerView+Reflection.h
//  ADTECHMobileSDK
//
//  Created by ADTECH GmbH on 4/26/13.
//  Copyright (c) 2013 ADTECH GmbH. All rights reserved.
//

#import <ADTECHMobileSDK/ATBannerView.h>
@class ADTECHVisibilityTracking;

typedef NS_ENUM(NSInteger, ATAdType)
{
	kATAdTypeUnknown,
	kATAdTypeImage,
	kATAdTypeRich,
	kATAdTypeMRAID,
	kATAdTypeORMMA,
};

@interface ATBannerView (Reflection)

@property (nonatomic, strong, readonly) ADTECHVisibilityTracking *visibilityTracking;

- (ATAdType)currentAdType;

@end
