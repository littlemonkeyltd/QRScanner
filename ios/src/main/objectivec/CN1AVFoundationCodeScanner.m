#import "com_codename1_ext_codescan_CodeScanner.h"
#import "CN1AVFoundationCodeScanner.h"
#import "CodenameOne_GLViewController.h"

@implementation CN1AVFoundationCodeScanner

- (instancetype)initWithMetadataObjectTypes:(NSArray *)metadataObjectTypes {
    self = [super init];
    if (self) {
        self.metadataObjectTypes = metadataObjectTypes;
    }
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Additional setup if needed
}

- (void)viewDidAppear:(BOOL)animated {
    [super viewDidAppear:animated];
    [self scanBarCode];
}

- (void)scanBarCode {
    dispatch_async(dispatch_get_main_queue(), ^{
        POOL_BEGIN();

        self.session = [[AVCaptureSession alloc] init];
        AVCaptureDevice *device = [AVCaptureDevice defaultDeviceWithMediaType:AVMediaTypeVideo];

        NSError *error = nil;
        AVCaptureDeviceInput *input = [AVCaptureDeviceInput deviceInputWithDevice:device error:&error];
        if (!input) {
            NSLog(@"Error: %@", error);
            return;
        }
        [self.session addInput:input];

        AVCaptureMetadataOutput *output = [[AVCaptureMetadataOutput alloc] init];
        [output setMetadataObjectsDelegate:self queue:dispatch_get_main_queue()];
        [self.session addOutput:output];

        // Specify the types of metadata objects to recognize
        if (self.metadataObjectTypes && self.metadataObjectTypes.count > 0) {
            output.metadataObjectTypes = self.metadataObjectTypes;
        } else {
            output.metadataObjectTypes = output.availableMetadataObjectTypes;
        }

        self.previewLayer = [AVCaptureVideoPreviewLayer layerWithSession:self.session];
        self.previewLayer.videoGravity = AVLayerVideoGravityResizeAspectFill;
        self.previewLayer.frame = self.view.layer.bounds;
        [self.view.layer addSublayer:self.previewLayer];

        // Add tap gesture recognizer to cancel scanning
        UITapGestureRecognizer *tapGesture = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(cancelScanning)];
        [self.view addGestureRecognizer:tapGesture];

        [self.session startRunning];
        POOL_END();
    });
}

- (void)cancelScanning {
    [self cleanupScanning];
    com_codename1_ext_codescan_CodeScanner_scanCanceledCallback__(CN1_THREAD_GET_STATE_PASS_SINGLE_ARG);
}

-(void)cleanupScanning {
    [self.session stopRunning];
        [self.previewLayer removeFromSuperlayer];
        // Remove the tap gesture recognizer
        for (UIGestureRecognizer *recognizer in self.view.gestureRecognizers) {
            if ([recognizer isKindOfClass:[UITapGestureRecognizer class]]) {
                [self.view removeGestureRecognizer:recognizer];
            }
        }
        [self dismissModalViewControllerAnimated: YES];
}

#pragma mark - AVCaptureMetadataOutputObjectsDelegate

- (void)captureOutput:(AVCaptureOutput *)captureOutput didOutputMetadataObjects:(NSArray<__kindof AVMetadataObject *> *)metadataObjects fromConnection:(AVCaptureConnection *)connection {
    for (AVMetadataObject *metadataObject in metadataObjects) {
        if (self.metadataObjectTypes == nil || self.metadataObjectTypes.count == 0 || [self.metadataObjectTypes containsObject:metadataObject.type]) {
            AVMetadataMachineReadableCodeObject *readableObject = (AVMetadataMachineReadableCodeObject *)metadataObject;
            NSString *scannedResult = readableObject.stringValue;
            NSString *codeType = readableObject.type;
            // Handle the scanned result
            NSLog(@"Scanned code: %@", scannedResult);
            [self.session stopRunning]; // Stop the session once we get a result
            [self.previewLayer removeFromSuperlayer];
            [self cleanupScanning]; // Clean up gesture recognizers and dismiss view controller
            com_codename1_ext_codescan_CodeScanner_scanCompletedCallback___java_lang_String_java_lang_String_byte_1ARRAY(
                CN1_THREAD_GET_STATE_PASS_ARG
                fromNSString(
                    CN1_THREAD_GET_STATE_PASS_ARG
                    scannedResult
                ),
                fromNSString(
                    CN1_THREAD_GET_STATE_PASS_ARG
                    codeType
                ),
                JAVA_NULL
            );
            break;
        }
    }
}

@end
