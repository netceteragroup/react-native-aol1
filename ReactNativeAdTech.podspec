require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))
version = package['version']

#
#  Be sure to run `pod spec lint ReactNativeAdTech.podspec' to ensure this is a
#  valid spec and to remove all comments including this before submitting the spec.
#
#  To learn more about Podspec attributes see http://docs.cocoapods.org/specification.html
#  To see working Podspecs in the CocoaPods repo see https://github.com/CocoaPods/Specs/
#

Pod::Spec.new do |s|
  s.name         = "ReactNativeAdTech"
  s.version      = version
  s.summary      = "A native Veeplay react native component."

  s.description  = <<-DESC
  Veeplay
                   DESC

  s.homepage     = "https://github.com/netceteragroup/react-native-aol1"
  s.license      = "COMMERCIAL"
  s.author       = { "Zdravko Nikolovski" => "zdravko.nikolovski@netcetera.com" }

  s.ios.deployment_target = "8.0"

  s.source       = { :git => "https://github.com/netceteragroup/react-native-aol1", :branch => "master" }


  s.source_files  = "ios/*.{m,h}"
  s.public_header_files = "ios/*.h"

  s.dependency 'React'

  # Force the static framework to be linked into this pod
  # See https://github.com/CocoaPods/CocoaPods/issues/2926
end