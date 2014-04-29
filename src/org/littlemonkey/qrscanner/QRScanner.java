/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.littlemonkey.qrscanner;

import com.codename1.codescan.CodeScanner;
import com.codename1.codescan.ScanResult;
import com.codename1.system.NativeLookup;
import com.codename1.ui.Display;

/**
 *
 * @author nick
 */
public class QRScanner {
    
    private static ScanResult callback;

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
