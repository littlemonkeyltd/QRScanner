QRScanner
=========

CN1Lib for using ZBar scanning in Android apps.

The built in Codename One implementation of CodeScanner works well on iOS but has some issues on Android.  
This module works around those issues by embedding ZBar into the Android build.

This removes the reliance on an external scanning app to be installed and seems to scan faster.

It does add several megabytes to the final .apk file size.

How to use
==========

Installation
------------

1. Build or download the QRScanner.cn1lib file.
2. Put the file the `libs` folder of your project.
3. Right-click on your project and choose `Refresh Libs`
4. Add the following build hints"

|Key                  |Value                                                                                                                                             |
|---------------------|--------------------------------------------------------------------------------------------------------------------------------------------------|
|android.xapplication |`<activity android:name="com.dm.zbar.android.scanner.ZBarScannerActivity" android:screenOrientation="landscape" android:label="@string/app_name" />`|
|android.xpermissions |`<uses-permission android:name="android.permission.CAMERA"/><uses-feature android:name="android.hardware.camera" android:required="false"/>`        |
|android.proguard.Keep|` -keep class net.sourceforge.zbar.** {*;} `*NOTE THERE NEEDS TO BE A SPACE BEFORE AND AFTER THIS VALUE*                                           | 

Example Code
------------
Basically use `QRScanner` instead of `CodeScanner`.

```
QRScanner.scanQRCode(new ScanResult() {
    public void scanCompleted(String contents, String formatName, byte[] rawBytes) {
        Dialog.show("Completed", contents, "OK", null);
    }
    public void scanCanceled() {
        Dialog.show("Cancelled", "Scan Cancelled", "OK", null);
    }
    public void scanError(int errorCode, String message) {
        Dialog.show("Error", message, "OK", null);
    }
});
```

Converting an existing app
--------------------------

It should pretty much be a drop in replacement for CodeScanner.  If you need to detect if code scanning is supported on the current platform then you need to keep the original check
do not change this line to QRScanner:

```
if (CodeScanner.getInstance() != null) {
    QRScanner.scanQRCode(myScanResult);
} else {
    Dialog.show("Not Supported","QR Code Scanning is not available on this device","OK",null);
}
```
