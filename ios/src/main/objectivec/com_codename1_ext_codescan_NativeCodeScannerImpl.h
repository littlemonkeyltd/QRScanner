#import <Foundation/Foundation.h>
#define CN1_QRSCANNER_AVFOUNDATION 1
#ifdef CN1_QRSCANNER_AVFOUNDATION
#import "CN1AVFoundationCodeScanner.h"
#endif

@interface com_codename1_ext_codescan_NativeCodeScannerImpl : NSObject {
}

-(void)scanQRCode;
-(void)scanBarCode;
-(BOOL)isSupported;
@end
