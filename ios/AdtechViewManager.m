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

RCT_REMAP_VIEW_PROPERTY(alias, ALIAS, NSString);
RCT_REMAP_VIEW_PROPERTY(type, TYPE, NSString);
RCT_REMAP_VIEW_PROPERTY(networkid, NETWORKID, NSString);
RCT_REMAP_VIEW_PROPERTY(subnetworkid, SUBNETWORKID, NSString);



- (UIView *)view
{
    return [[RNAdtechView alloc] init];
}

@end
