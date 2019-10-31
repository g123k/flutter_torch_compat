#import "TorchCompatPlugin.h"
#import <torch_compat/torch_compat-Swift.h>

@implementation TorchCompatPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftTorchCompatPlugin registerWithRegistrar:registrar];
}
@end
