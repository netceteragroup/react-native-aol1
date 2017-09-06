//
//  ATVASTDocument.h
//  ADTECHMobileSDK
//
//  Created by ADTECH GmbH on 4/19/12.
//  Copyright (c) 2012 ADTECH GmbH. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface ATVASTDocument : NSObject

@property (nonatomic, copy) NSString *version;
@property (nonatomic, strong) NSMutableArray *ads; // array of ATVASTAd objects
@property (nonatomic, copy) NSURL *error;
@end
