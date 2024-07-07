package org.littlemonkey.qrscanner;

public class NativeScannerImpl implements org.littlemonkey.qrscanner.NativeScanner{

    private static boolean cameraUsageDescriptionChecked;
    public void scanQRCode() {
    }

    public void scanBarCode() {
    }

    public boolean isSupported() {
        checkCameraUsageDescription();
        return false;
    }

    private static void checkCameraUsageDescription() {
        if (!cameraUsageDescriptionChecked) {
            cameraUsageDescriptionChecked = true;

            java.util.Map<String, String> m = com.codename1.ui.Display.getInstance().getProjectBuildHints();
            if(m != null) {
                if(!m.containsKey("ios.NSCameraUsageDescription")) {
                    com.codename1.ui.Display.getInstance().setProjectBuildHint("ios.NSCameraUsageDescription", "Some functionality of the application requires your camera");
                }
            }
        }
    }

}
