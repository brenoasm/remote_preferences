#import "RemotePreferences.h"
#if __has_include(<remote_preferences/remote_preferences-Swift.h>)
#import <remote_preferences/remote_preferences-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "remote_preferences-Swift.h"
#endif

@implementation RemotePreferencesPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftRemotePreferencesPlugin registerWithRegistrar:registrar];
}
@end