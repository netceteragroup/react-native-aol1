//
//  ATVASTURL.h
//  ADTECHMobileSDK
//
//  Created by ADTECH GmbH on 4/20/12.
//  Copyright (c) 2012 ADTECH GmbH. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface ATVASTURL : NSURL

@property (nonatomic, copy) NSString *ID;

+ (instancetype)URLWithString:(NSString*)urlString andID:(NSString*)ID;
+ (instancetype)URLWithString:(NSString*)urlString;

@end
