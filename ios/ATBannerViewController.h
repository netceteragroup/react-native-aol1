#import <UIKit/UIKit.h>
#import <AdTechMobileSdk/ADTECHMobileSDK.h>

@interface ATBannerViewController : UIViewController

@property (weak, nonatomic) id<ATBannerViewDelegate> bannerDelegate;
@property (weak, nonatomic) id<ATInterstitialDelegate> interstitialDelegate;

@property (nonatomic) NSString *alias;
@property (nonatomic) NSString *type;
@property (nonatomic) NSNumber *networkId;
@property (nonatomic) NSNumber *subnetworkId;
@property (nonatomic) NSDictionary *keyValues;

- (void)setupController;

@end
