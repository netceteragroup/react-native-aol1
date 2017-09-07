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

  s.author             = { "Zdravko Nikolovski" => "zdravko.nikolovski@netcetera.com" }

  s.platform     = :ios, "8.0"

  s.source       = { :http => "https://learn.oneadserver.aol.com/viewer/book-attachment/huTPsrZDmfWl1q7mMa9osw/ADTECH_SDK_iOS_3.8.4.zip" }

  s.ios.vendored_frameworks = "ADTECHMobileSDK.framework"

  s.public_header_files = "ADTECHMobileSDK.framework/Headers/*.h"

  s.resources = 'ADTECHMobileSDK.bundle', 'ADTECHLocalizable.strings', 'ADTECHAdConfiguration.plist'
  # ――― Project Linking ―――――――――――――――――――――――――――――――――――――――――――――――――――――――――― #
  #
  #  Link your library with frameworks, or libraries. Libraries do not include
  #  the lib prefix of their name.
  #

  # s.framework  = "SomeFramework"
  # s.frameworks = "SomeFramework", "AnotherFramework"

  # s.library   = "iconv"
  # s.libraries = "iconv", "xml2"


  # ――― Project Settings ――――――――――――――――――――――――――――――――――――――――――――――――――――――――― #
  #
  #  If your library depends on compiler flags you can set them in the xcconfig hash
  #  where they will only apply to your library. If you depend on other Podspecs
  #  you can include multiple dependencies to ensure it works.

  # s.requires_arc = true

  # s.xcconfig = { "HEADER_SEARCH_PATHS" => "$(SDKROOT)/usr/include/libxml2" }
  # s.dependency "JSONKit", "~> 1.4"

end
