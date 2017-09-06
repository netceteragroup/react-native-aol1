//
//  RNAdtechViewManager.m
//  RNAdtech
//
//  Created by Andi Anton on 16/08/2017.
//  Copyright © 2017 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "AdtechViewManager.h"
#import "RNAdtechView.h"


@implementation AdtechViewManager

RCT_EXPORT_MODULE();
//RCT_REMAP_VIEW_PROPERTY(sharingURL, SHARINGURL, NSString);

- (UIView *)view
{
    return [[RNAdtechView alloc] init];
}

@end
