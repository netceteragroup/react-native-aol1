//
//  ATGenericAdConfiguration.h
//  ADTECHMobileSDK
//
//  Created by ADTECH GmbH on 1/23/13.
//  Copyright (c) 2013 ADTECH GmbH. All rights reserved.
//

#import "ATAdConfiguration.h"
#import "GenericConfigurable.h"

/**
 * Holds the configuration of ads served by ad.com. You can set here the placement identification details.
 *
 * Available in 3.1 and later.
 *
 * @see ATAdConfiguration
 * @see ATBaseConfiguration
 * @see GenericConfigurable
 * @see [ATBannerView configuration]
 * @see [ATInterstitial configuration]
 */
@interface ATGenericAdConfiguration : ATAdConfiguration<NSCopying, GenericConfigurable>
{
}
/**
 * Creates a new autoreleased instance of the configuration. This is a convenience method.
 *
 * Available in 3.1 and later.
 */
+ (ATGenericAdConfiguration*)configuration;

@end
