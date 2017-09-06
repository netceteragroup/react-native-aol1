//
//  RNAdtechView.m
//  RNAdtech
//
//  Created by Andi Anton on 16/08/2017.
//  Copyright © 2017 Facebook. All rights reserved.
//

#import "RNAdtechView.h"
#import "ATBannerViewController.h"
#import <ADTECHMobileSDK/ADTECHMobileSDK.h>

@interface RNAdtechView()

//module position and size
@property (nonatomic) CGPoint windowPos;
@property (nonatomic) CGSize windowSize;

//yoga frame info done timer
@property (nonatomic) NSTimer *frameInfoTimer;

@property (nonatomic) UIView *AdTechView;

@property (nonatomic) ATBannerView *bannerView;

@property (nonatomic) ATBannerViewController *bannerVC;



@property (nonatomic) NSString *alias;
@property (nonatomic) NSString *type;
@property (nonatomic) NSString *networkid;
@property (nonatomic) NSString *subnetworkid;

@end

@implementation RNAdtechView

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/

- (instancetype)init
{
    self = [super init];
    
    self.autoresizingMask = UIViewAutoresizingFlexibleWidth | UIViewAutoresizingFlexibleHeight;
    
    [self createFrameInfoTimer];
    return self;
}

//timer for yoga notifications
- (void)createFrameInfoTimer {
    self.frameInfoTimer = [NSTimer scheduledTimerWithTimeInterval:.01f
                                                           target:self
                                                         selector:@selector(checkFrameInfoDone:)
                                                         userInfo:nil
                                                          repeats:YES];
}


-(void)checkFrameInfoDone:(NSTimer*)timer {
    
    if(self.frame.size.width > 0 && self.frame.size.height > 0){
        if([self.frameInfoTimer isValid]){
            [self.frameInfoTimer invalidate];
            NSLog(@"X = %f  Y = %f  W = %f  H = %f", self.frame.origin.x, self.frame.origin.y, self.frame.size.width, self.frame.size.height);
            self.windowPos = CGPointMake(self.frame.origin.x, self.frame.origin.y);
            self.windowSize = CGSizeMake(self.frame.size.width, self.frame.size.height);
            [self adjustViewsLayout];
        }
    }
}



- (void)adjustViewsLayout {
    /*
    if(self.imageLinksArray.count > 0){
        self.imageGalleryView = [[UIView alloc] initWithFrame:self.bounds];
        self.imageGalleryView.backgroundColor = [UIColor blackColor];
        self.imageGalleryView.autoresizingMask = UIViewAutoresizingFlexibleWidth | UIViewAutoresizingFlexibleHeight;
        
        self.scrollViewScreenWidth = self.frame.size.width - (2*IMAGE_GALLERY_NAVIGATION_BARS_W);
        
        [self loadImages];
        [self createScrollView];
        [self createImageGalleryNavigationControls];
        [self addSubview:self.imageGalleryView];
    }*/
    
    self.AdTechView = [[UIView alloc] initWithFrame:self.bounds];
    self.AdTechView.backgroundColor = [UIColor greenColor];
    self.AdTechView.autoresizingMask = UIViewAutoresizingFlexibleWidth | UIViewAutoresizingFlexibleHeight;
    
    /*
    self.bannerView = [[ATBannerView alloc] initWithFrame:self.AdTechView.frame];
    
    ATAdtechAdConfiguration *configuration = [ATAdtechAdConfiguration configuration];
    configuration.alias = @"home-top-5";
    
    self.bannerView.configuration = configuration;
    
    //self.bannerView.delegate = self;
    [self.bannerView load];
    [self.AdTechView addSubview:self.bannerView];
    */
    
    self.bannerVC = [[ATBannerViewController alloc] init];
    
    [self.bannerVC setAlias:self.alias];
    [self.bannerVC setType:self.type];
    [self.bannerVC setNetworkid:self.networkid];
    [self.bannerVC setSubnetworkid:self.subnetworkid];
    
    self.bannerVC.view.frame = self.bounds;
    self.bannerVC.view.autoresizingMask = UIViewAutoresizingFlexibleWidth | UIViewAutoresizingFlexibleHeight;
    [self.AdTechView addSubview:self.bannerVC.view];

    
    /*
    adtechBanner = [[ATBannerView alloc] initWithFrame:CGRectMake(0,0,320,50)]; adtechBanner.configuration.alias = @"home-top-5"; adtechBanner.viewController = self;
    adtechBanner.delegate = self;
    // add the banner as a subview of your view controllers view [self.view addSubview:adtechBanner];
    [adtechBanner load];
    */
    
    [self addSubview:self.AdTechView];
}


//properties from JS

- (void)setALIAS:(NSString *)alias {
    NSLog(@"alias: %@", alias);
    self.alias = alias;
}

- (void)setTYPE:(NSString *)type {
    NSLog(@"type: %@", type);
    self.type = type;
}

- (void)setNETWORKID:(NSString *)networkid {
    NSLog(@"networkid: %@", networkid);
    self.networkid = networkid;
}

- (void)setSUBNETWORKID:(NSString *)subnetworkid {
    NSLog(@"subnetworkid: %@", subnetworkid);
    self.subnetworkid = subnetworkid;
}



@end
