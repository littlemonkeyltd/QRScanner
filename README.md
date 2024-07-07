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

### Maven Projects

Add the following dependency to your project's common/pom.xml file:

```xml
<dependency>
    <groupId>com.codenameone</groupId>
    <artifactId>qrscanner-lib</artifactId>
    <version>2.0.2</version>
    <type>pom</type>
</dependency>
```

If you want to hack on the sources, and use a modified version, you can just clone this repository and run `mvn install` to install it into your local Maven repository.

### Ant Projects

Use Codename One Preferences to install the QRScanner library.

or

Build this project from source, then find the `.cn1lib` file in the `common/target` directory, and copy it to the "libs" directory of your Codename One application project, and select "Refresh Libs".

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

## Configuring Types to Scan For

By default, the scanner will look for bar codes of type `EAN13` in barcode scanning mode, and `QR_CODE` in QR code scanning mode.  
If you want to detect ALL supported code types, you can call the following before making the call to scan the code:

```java
Display.getInstance().setProperty("scanAllCodeTypes", "true");
```

## Building from Source

1. Clone this repository
2. Set JAVA_HOME to a JDK 1.8 or JDK 11 installation 
3. mvn install
