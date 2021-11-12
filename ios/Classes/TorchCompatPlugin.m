#import "TorchCompatPlugin.h"
#if __has_include(<torch_compat/torch_compat-Swift.h>)
#import <torch_compat/torch_compat-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "torch_compat-Swift.h"
#endif

@implementation TorchCompatPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftTorchCompatPlugin registerWithRegistrar:registrar];
}
@end
