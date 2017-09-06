//
//  ATVASTAd.h
//  ADTECHMobileSDK
//
//  Created by ADTECH GmbH on 4/19/12.
//  Copyright (c) 2012 ADTECH GmbH. All rights reserved.
//

#import <Foundation/Foundation.h>

@class ATVASTInLine;
@class ATVASTWrapper;

@interface ATVASTAd : NSObject

@property (nonatomic, copy) NSString *ID;

@property (nonatomic) NSNumber *sequence;

@property (nonatomic, strong) ATVASTInLine *inLineAd;
@property (nonatomic, strong) ATVASTWrapper *wrapper;

@end
