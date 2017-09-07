//
//  ATCloseIndicator.h
//  ADTECHMobileSDK
//
//  Created by ADTECH GmbH on 10/9/13.
//  Copyright (c) 2013 ADTECH GmbH. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>

/**
 * Allows overriding the default close indicator for expanded ads, interstitials and video ads.
 * If you want to use your own images as close indicators, provide them through this configuration object.
 *
 * @since 3.4.
 *
 * @see ATBaseConfiguration
 */
@interface ATCloseIndicator : NSObject<NSCoding>

/**
 * The button to be used for the close indicator button.
 * Defaults to nil, meaning the SDK should use its own resource for the indicator.
 *
 * @since 3.8
 */
@property (nonatomic,strong) UIButton *button;


/**
 * Call this to create a close indicator using images for the normal and the highlighted state. If you only provide an image
 * for one state it will be used for both states.
 *
 * @param normalStateImage Image for the buttons UIControlStateNormal.
 * @param highlightedStateImage Image for the buttons UIControlStateHighlighted.
 *
 * @since 3.8
 */
+ (ATCloseIndicator *)closeIndicatorWithNormalStateImage:(UIImage *)normalStateImage
                                andHighlightedStateImage:(UIImage *)highlightedStateImage;

/**
 * Call this to create a close indicator using a button of your choosing.
 *
 * @param button Button to be used for the close indicator. 
 *
 * @since 3.8
 */
+ (ATCloseIndicator *)closeIndicatorWithButton:(UIButton *)button;

@end
