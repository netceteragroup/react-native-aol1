//
//  ATAdtechVideoAdConfiguration.h
//  ADTECHMobileSDK
//
//  Created by ADTECH GmbH on 1/22/13.
//  Copyright (c) 2013 ADTECH GmbH. All rights reserved.
//

#import "ATVideoAdConfiguration.h"
#import "ADTECHConfigurable.h"

/**
 * Holds the placement configuration for video ads shown by ATMoviePlayerController that are served by ADTECH.
 * You create an instance of this object and set on it the needed values in order to receive and show video ads from a placement you have configured server side.
 * Set the configuration object on the ATMoviePlayerController that you use, once it is configured.
 * If you change the configuration while already playing video content, the changes will take effect only when fetching new ads.
 *
 * Available in 3.1 and later.
 */
@interface ATAdtechVideoAdConfiguration : ATVideoAdConfiguration<NSCopying, ADTECHConfigurable>
{
}

/**
 * Creates a new autoreleased instance of the configuration. This is a convenience method.
 * The configuration default values will be loaded from the configuration file (ADTECHAdConfiguration.plist).
 *
 * Available in 3.1 and later.
 */
+ (ATAdtechVideoAdConfiguration*)configuration;

@end
