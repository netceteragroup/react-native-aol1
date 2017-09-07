//
//  ATVASTCompanion.h
//  ADTECHMobileSDK
//
//  Created by ADTECH GmbH on 4/20/12.
//  Copyright (c) 2012 ADTECH GmbH. All rights reserved.
//

#import "ATVASTOverlay.h"
#import "ATVASTTracking.h"

@interface ATVASTCompanion : ATVASTOverlay

@property (nonatomic,readonly) NSMutableArray *creativeExtensions; // array of NSString objects containing any valid XML documents

@property (nonatomic, copy) NSString *altText;
@property (nonatomic, assign) NSInteger assetWidth;
@property (nonatomic, assign) NSInteger assetHeight;
@property (nonatomic, copy) NSString *adSlotId;
@property (nonatomic, assign) NSInteger expandedWidth;
@property (nonatomic, assign) NSInteger expandedHeight;
@property (nonatomic, copy) NSString *adParameters;


@end
