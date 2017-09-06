//
//  ATInterstitial.h
//  ADTECHMobileSDK
//
//  Created by ADTECH GmbH on 11/24/11.
//  Copyright (c) 2011 ADTECH GmbH. All rights reserved.
//
//  www.adtech.com 
//

#import <UIKit/UIKit.h>

@class ATAdConfiguration;
@protocol ATInterstitialDelegate;

/**
 * Represents one interstitial ad that you place in your application at various times and places.
 * For each interstitial ad you want to display, create one instance of this class.
 * 
 * The interstitial will call the delegate when different events occur in the ad's lifecycle.
 *
 * @note The interstitial class was renamed in NEXT_VERSION from ATInterstitialView to ATInterstitial to
 *       reflect that the class itself is not a view.
 *
 * @since 1.0
 *
 * @see ATInterstitialDelegate
 */
@interface ATInterstitial : NSObject

/** 
 * The delegate gets notified of different events in the lifecycle of the ad.
 * Your presenting view controller should register itself as delegate of the ad view.
 *
 * @since 1.0
 *
 * @see ATInterstitialDelegate
 */
@property (nonatomic, weak) id<ATInterstitialDelegate> delegate;

/**
 * The view controller that is the owner of the ad view being shown.
 *
 * @since 1.0
 *
 * @warning *You must set this property before calling load. Ads won't get loaded without this property being set.*
 */
@property (nonatomic, weak) UIViewController *viewController;

/** Allows configuring the ad and specifying different parameters for better targeting.
 *
 * @since 1.0
 *
 * @see ATAdConfiguration
 */
@property (nonatomic, strong) ATAdConfiguration *configuration;

/**
 * Allows changing the background color for the interstitial.
 * The default color is clear on iOS 8 and above and white on iOS 6 and 7.
 *
 * @since 3.6
 * @see modalPresentationStyle
 */
@property (strong, nonatomic) UIColor *backgroundColor;

/**
 * Allows changing the presentation style for the interstitial.
 *
 * @note If the backgroundColor set is partially transparent, i.e. alpha is below 1, the SDK changes the presentation style
 *       to UIModalPresentationOverFullScreen on iOS 8 and above. Transparent interstitials don't work with all
 *       presentation styles. You can still change the modalPresentationStyle to something else after that.
 *
 * @since 3.6
 * @see backgroundColor
 */
@property (assign, nonatomic) UIModalPresentationStyle modalPresentationStyle;

/** 
 * Returns YES when an interstitial ad is ready to be presented.
 * The delegate's [ATInterstitialDelegate didSuccessfullyFetchInterstitialAd:] is called when the value switches from NO to YES.
 * Once an interstitial is presented, the value changes to NO. You need to load again in order
 * to be able to present another interstitial.
 *
 * @since 2.0
 */
@property (nonatomic, readonly) BOOL isReady;

/**
 * Begins loading the ad. The ad should be configured before calling this method.
 * The delegate will be called on events only after this call.
 *
 * @since 1.0
 *
 * @warning *Important:* If a request fails the SDK stops fetching ads until you call ATInterstitial::load again.
 *
 * @warning *Important:* Before calling *load* you should always check the validity of the ad configuration by checking the ads configuration isValid property.
 * Failing to ensure a valid configuration will result in the ad not being correctly loaded and displayed.
 */
- (void)load;

/**
 * Presents the interstitial modally on top of the view controller.
 * Call this method after receiving the [ATInterstitialDelegate didSuccessfullyFetchInterstitialAd:] callback in order
 * to present the interstitial. It will be automatically dismissed after the refresh interval or when the user chooses to close it.
 * Calling this method before receiving the above callbacks will have no effect.
 *
 * @since 2.0
 */
- (void)present;

/**
 * Call this to programmatically dismiss the interstitial that is being presented modally on top of the view controller.
 * Calling this method before actually presenting the interstitial will have no effect.
 * If you don't call it, the interstitial will still be automatically dismissed after the refresh interval or when the user chooses to close it.
 *
 * @since 3.3
 */
- (void)dismiss;


/**
 * Call this if you want to present an interstitial before your app opens to its content. You must call this method from
 * your app delegate in the application:didFinishLaunchingWithOptions: function.
 *
 * If you're not using storyboards, then please make sure your rootViewController on the window instance is set before
 * you call this method.
 *
 * This method will extend the splash screen that is presented by the OS during the launching process.
 * You can subscribe to the interstitial events via the delegate parameter.
 * It is important to only use this for information about the life cycle of the ad and not calling any
 * methods such as present on the interstitial. Everything from loading, presenting and deallocating
 * the interstitial is handled internally. A time out is also used to make sure the user of the application
 * does not wait a long time for an ad to appear. This time out can be set via the timeOut parameter
 * and a suggested time out of 3 seconds can be used.
 *
 * @param configuration The configuration to use to create the interstitial.
 * @param delegate The delegate to call for events reported on the interstitial.
 * @param timeout Time allowed for the interstitial to load otherwise app content is displayed.
 *
 * @note You must have at least  one of the following in your app: a launch screen, and/or an asset catalog with launch images.
 * @note You have the chance to customize the modalPresentationStyle in the didSuccessfullyFetchInterstitialAd: flavor of delegate methods.
 *
 * @warning Only call this from your app delegate, as it is meant to display exactly one interstitial when your app is launched.
 *
 * @since 3.8
 */
+ (void)presentPrestitialWithConfiguration:(ATAdConfiguration *)configuration
                               andDelegate:(id<ATInterstitialDelegate>)delegate
                                andTimeout:(NSTimeInterval)timeout;

@end
