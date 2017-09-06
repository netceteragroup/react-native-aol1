//
//  ATVASTNonLinearAds.h
//  ADTECHMobileSDK
//
//  Created by ADTECH GmbH on 4/20/12.
//  Copyright (c) 2012 ADTECH GmbH. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "ATVASTEnums.h"
#import "ATVASTTrackable.h"

@class ATVASTTracking;

@interface ATVASTNonLinearAds : ATVASTTrackable

@property (nonatomic, readonly) NSMutableArray *nonLinears; // array of ATVASTNonLinear objects



@end
