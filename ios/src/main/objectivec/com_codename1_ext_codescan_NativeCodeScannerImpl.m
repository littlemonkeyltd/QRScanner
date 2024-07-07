#import "com_codename1_ext_codescan_NativeCodeScannerImpl.h"
#import "ScanCodeImplExt.h"
#import "CodenameOne_GLViewController.h"
#import "cn1_globals.h"
#import "com_codename1_ui_Display.h"
@implementation com_codename1_ext_codescan_NativeCodeScannerImpl

-(void)scanQRCode{
#ifdef CN1_QRSCANNER_AVFOUNDATION
    dispatch_async(dispatch_get_main_queue(), ^{
        CN1AVFoundationCodeScanner *scannerViewController = [self isScanAllTypesEnabled]
            ? [[CN1AVFoundationCodeScanner alloc] init]
            : [[CN1AVFoundationCodeScanner alloc] initWithMetadataObjectTypes: [self getQRScanningTypes]];

        [[CodenameOne_GLViewController instance] presentModalViewController:scannerViewController animated:NO];
    });

    return;
#endif
    [self scanBarCode];
}

-(NSArray*) getBarCodeScanningTypes {
    return @[AVMetadataObjectTypeEAN13Code, AVMetadataObjectTypeEAN8Code, AVMetadataObjectTypeUPCECode];
}

-(NSArray*) getQRScanningTypes {
    return @[AVMetadataObjectTypeQRCode];
}

-(BOOL) isScanAllTypesEnabled {
    NSString *resultString = [self getStringDisplayProperty: @"scanAllCodeTypes" default: @"false"];
    return [resultString isEqualToString:@"true"];
}

-(NSString*) getStringDisplayProperty: (NSString*)key default:(NSString*)defaultVal {
    struct ThreadLocalData* threadStateData = getThreadLocalData();
    enteringNativeAllocations();
    JAVA_OBJECT d = com_codename1_ui_Display_getInstance__(CN1_THREAD_GET_STATE_PASS_SINGLE_ARG);
    JAVA_OBJECT jkey = fromNSString(CN1_THREAD_GET_STATE_PASS_ARG key);
    JAVA_OBJECT jdefaultVal = fromNSString(CN1_THREAD_GET_STATE_PASS_ARG defaultVal);
    JAVA_OBJECT res = com_codename1_ui_Display_getProperty___java_lang_String_java_lang_String_R_java_lang_String(
        CN1_THREAD_GET_STATE_PASS_ARG d,
        jkey,
        jdefaultVal
    );
    finishedNativeAllocations();

    return toNSString(CN1_THREAD_GET_STATE_PASS_ARG res);
}

-(void)scanBarCode{
#ifdef CN1_QRSCANNER_AVFOUNDATION
    dispatch_async(dispatch_get_main_queue(), ^{
        CN1AVFoundationCodeScanner *scannerViewController = [self isScanAllTypesEnabled]
                ? [[CN1AVFoundationCodeScanner alloc] init]
                : [[CN1AVFoundationCodeScanner alloc] initWithMetadataObjectTypes: [self getBarCodeScanningTypes]];

        [[CodenameOne_GLViewController instance] presentModalViewController:scannerViewController animated:NO];

    });
    return;
#endif
#if !TARGET_IPHONE_SIMULATOR
    dispatch_async(dispatch_get_main_queue(), ^{
        POOL_BEGIN();
        CVZBarReaderViewControllerExt *reader = [CVZBarReaderViewControllerExt new];
        ScanCodeImplExt* scanCall = [[ScanCodeImplExt alloc] init];
        reader.readerDelegate = scanCall;
        reader.supportedOrientationsMask = ZBarOrientationMaskAll;
        
        //ZBAR_CONFIGURATIONS
        
        ZBarImageScanner *scanner = reader.scanner;
        // TODO: (optional) additional reader configuration here
        
        // EXAMPLE: disable rarely used I2/5 to improve performance
        [scanner setSymbology: ZBAR_I25
                       config: ZBAR_CFG_ENABLE
                           to: 0];
        
        // present and release the controller
        [[CodenameOne_GLViewController instance] presentModalViewController:reader animated:NO];
#ifndef CN1_USE_ARC
        [reader release];
#endif
        POOL_END();
    });
#endif
}

-(BOOL)isSupported{
    return YES;
}

@end
