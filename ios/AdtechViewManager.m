#import <Foundation/Foundation.h>
#import "AdtechViewManager.h"
#import "RNAdtechView.h"


@implementation AdtechViewManager

RCT_EXPORT_MODULE();

RCT_EXPORT_VIEW_PROPERTY(alias, NSString *);
RCT_EXPORT_VIEW_PROPERTY(type, NSString *);
RCT_EXPORT_VIEW_PROPERTY(networkId, NSNumber *);
RCT_EXPORT_VIEW_PROPERTY(subnetworkId, NSNumber *);
RCT_EXPORT_VIEW_PROPERTY(keyValues, NSDictionary *)

RCT_EXPORT_VIEW_PROPERTY(onAdFetchSuccess, RCTDirectEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onAdFetchFail, RCTDirectEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onInterstitialHidden, RCTDirectEventBlock)

- (UIView *)view
{
    return [[RNAdtechView alloc] init];
}

@end
