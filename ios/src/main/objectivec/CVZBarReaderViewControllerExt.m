#import "CVZBarReaderViewControllerExt.h"
#if !TARGET_IPHONE_SIMULATOR

@implementation CVZBarReaderViewControllerExt
- (void) loadView
{
#ifdef CN1_USE_ARC
    self.view = [[UIView alloc] initWithFrame: CGRectMake(0, 0, 320, 480)];
#else
    self.view = [[[UIView alloc] initWithFrame: CGRectMake(0, 0, 320, 480)] autorelease];
#endif
}

// Hack to hide the info button because it would just crash when pressed.
// https://github.com/codenameone/CodenameOne/issues/3287
- (void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];

    // Accessing the toolbar
    UIToolbar *toolbar = [[controls subviews] firstObject];

    // Only keeping the first two items of the toolbar, thus deleting the info button
    if ([toolbar isKindOfClass:UIToolbar.class]) {
        toolbar.items = @[ toolbar.items[0], toolbar.items[1] ];
    }
}
@end
#endif
