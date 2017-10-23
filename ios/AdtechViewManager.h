#import <React/RCTViewManager.h>
#import "RNAdtechView.h"

@interface AdtechViewManager : RCTViewManager<RNAdtechViewDelegate>

+ (void)setLoggingEnabled:(BOOL)enabled;
+ (BOOL)isLoggingEnabled;

@end
