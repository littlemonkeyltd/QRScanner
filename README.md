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

1. Install the [cn1-codescan](https://github.com/codenameone/cn1-codescan) library into your project.
1. Build or download the [QRScanner.cn1lib](bin/QRScanner.cn1lib) file.
2. Put the file the `libs` folder of your project.
3. Right-click on your project and choose `Refresh Libs`

Example Code
------------
Basically use `QRScanner` instead of `CodeScanner`.

```java
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

```java
if (CodeScanner.getInstance() != null) {
    QRScanner.scanQRCode(myScanResult);
} else {
    Dialog.show("Not Supported","QR Code Scanning is not available on this device","OK",null);
}
```
