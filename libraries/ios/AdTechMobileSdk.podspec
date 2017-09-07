Pod::Spec.new do |s|

  # ―――  Spec Metadata  ―――――――――――――――――――――――――――――――――――――――――――――――――――――――――― #
  #
  #  These will help people to find your library, and whilst it
  #  can feel like a chore to fill in it's definitely to your advantage. The
  #  summary should be tweet-length, and the description more in depth.
  #

  s.name         = "AdTechMobileSdk"
  s.version      = "3.8.4"
  s.summary      = "A podspec for AdTechMobileSdk."

  s.description  = <<-DESC
                    Podspec for easier integration of AdTech SDK
                   DESC

  s.homepage     = "https://github.com/netceteragroup/react-native-aol1"
  s.license      = "MIT"

  s.author       = { "Zdravko Nikolovski" => "zdravko.nikolovski@netcetera.com" }

  s.platform     = :ios, "8.0"

  s.source       = { :http => "https://learn.oneadserver.aol.com/viewer/book-attachment/huTPsrZDmfWl1q7mMa9osw/ADTECH_SDK_iOS_3.8.4.zip" }

  s.ios.vendored_frameworks = "ADTECHMobileSDK.framework"

  s.public_header_files = "ADTECHMobileSDK.framework/Headers/*.h"

  s.resources = 'ADTECHMobileSDK.bundle', 'ADTECHLocalizable.strings', 'ADTECHAdConfiguration.plist'

  s.frameworks = "AdSupport", "AudioToolbox", "AVFoundation", "CFNetwork", "CoreData", "CoreGraphics", "CoreLocation", "CoreMedia", "CoreMotion", "CoreVideo", "EventKit", "EventKitUI", "MediaPlayer", "MessageUI", "MobileCoreServices", "QuartzCore", "SafariServices", "Security", "StoreKit", "SystemConfiguration"
  s.libraries = "z", "xml2"

end
