
package org.littlemonkey.qrscanner;

import com.codename1.codescan.CodeScanner;
import com.codename1.codescan.ScanResult;
import com.codename1.system.NativeLookup;
import com.codename1.ui.Display;

/**
 * QRScanner uses built in ZBar on Android
 * Falls back to CodeScanner on other platforms where supported
 * 
 * Removes dependence on a third party scanner being installed.
 * 
 * Needs additional build hints:
 * 
 * android.xapplication = <activity android:name="com.dm.zbar.android.scanner.ZBarScannerActivity" android:screenOrientation="landscape" android:label="@string/app_name" />
 * android.xpermissions = <uses-permission android:name="android.permission.CAMERA"/><uses-feature android:name="android.hardware.camera" android:required="false"/>
 * 
 * And for release builds (only)
 * android.proguardKeep =  -keep class net.sourceforge.zbar.** {*;}  
 * 
 * NOTE THERE NEEDS TO BE A SPACE BEFORE AND AFTER THIS VALUE
 * 
 * @see CodeScanner
 * 
 * @author nick
 */
public class QRScanner {
    
    private static ScanResult callback;

    /**
     * Scan a QR Code with callback
     * 
     * @param callback 
     */
    public static void scanQRCode(ScanResult callback) {
        if (Display.getInstance().getPlatformName().equals("and")) {
            QRScanner.callback = callback;
            NativeScanner nativeScanner = (NativeScanner)NativeLookup.create(NativeScanner.class);
            if (nativeScanner != null && nativeScanner.isSupported()) {
                nativeScanner.scanQRCode();
            } else {
                callback.scanError(404, "Scanner not supported");
            }
                    
        } else {
            if (CodeScanner.getInstance() != null) {
                CodeScanner.getInstance().scanQRCode(callback);
            }
        }
    }

    /**
     * Scan a barcode with callback,
     * Currently scans for EAN13 codes on Android.
     * Change this in the native implementation.
     * 
     * @param callback 
     */
    public static void scanBarCode(ScanResult callback) {
         if (Display.getInstance().getPlatformName().equals("and")) {
            QRScanner.callback = callback;
            NativeScanner nativeScanner = (NativeScanner)NativeLookup.create(NativeScanner.class);
            if (nativeScanner != null && nativeScanner.isSupported()) {
                nativeScanner.scanBarCode();
            } else {
                callback.scanError(404, "Scanner not supported");
            }
                    
        } else {
            if (CodeScanner.getInstance() != null) {
                CodeScanner.getInstance().scanBarCode(callback);
            }
        }       
    }

    public static ScanResult getCallback() {
        return callback;
    }
    
    

}
