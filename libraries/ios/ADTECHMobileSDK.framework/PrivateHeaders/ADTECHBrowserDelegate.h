//
//  ADTECHBrowserDelegate.h
//  ADTECHMobileSDK
//
//  Created by ADTECH GmbH on 10/18/13.
//  Copyright (c) 2013 ADTECH GmbH. All rights reserved.
//

#import <Foundation/Foundation.h>

@protocol ADTECHBrowserDelegate <NSObject>

- (void)willDismissBrowser;
- (void)didDismissBrowser;

@optional
- (void)openNativeBrowserWithURL:(NSURL*)url;

@end
