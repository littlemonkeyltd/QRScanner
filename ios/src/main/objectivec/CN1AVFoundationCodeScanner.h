#import <AVFoundation/AVFoundation.h>

@interface CN1AVFoundationCodeScanner : UIViewController <AVCaptureMetadataOutputObjectsDelegate>

@property (strong, nonatomic) AVCaptureSession *session;
@property (strong, nonatomic) AVCaptureVideoPreviewLayer *previewLayer;
@property (strong, nonatomic) NSArray *metadataObjectTypes;

- (instancetype)initWithMetadataObjectTypes:(NSArray *)metadataObjectTypes;

@end
