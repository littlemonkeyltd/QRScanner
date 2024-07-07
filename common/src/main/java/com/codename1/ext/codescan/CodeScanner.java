/*
 * Copyright (c) 2012, Codename One and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Codename One designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *  
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 * 
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 * 
 * Please contact Codename One through http://www.codenameone.com/ if you 
 * need additional information or have any questions.
 */
package com.codename1.ext.codescan;

import com.codename1.io.Log;
import com.codename1.system.NativeLookup;
import com.codename1.ui.Display;
import java.util.Vector;

/**
 * A barcode/qrcode scanner API, this class is a singleton, notice that this
 * API might not be implemented for all platforms in which case the getInstance()
 * method will return null!
 *
 * @author Shai Almog
 */
public class CodeScanner {
    private ScanResult callback;
    private static NativeCodeScanner nativeInstance;
    private static CodeScanner instance;
    
    private CodeScanner() {
        try {
            nativeInstance = (NativeCodeScanner)NativeLookup.create(NativeCodeScanner.class);
        } catch (Throwable ex) {
            Log.p("Failed to load code scanner on this platform.");
        }
    }
    
    public static boolean isSupported() {
        getInstance();
        return nativeInstance != null && nativeInstance.isSupported();
    }
    
    /**
     * Returns the instance of the code scanner, notice that this method is equivalent 
     * to Display.getInstance().getCodeScanner().
     * 
     * @return instance of the code scanner
     */
    public static CodeScanner getInstance() {
        if (instance == null) {
            instance = new CodeScanner();
        }
        return instance;
    }
        
    /**
     * Scans based on the settings in this class and returns the results
     * 
     * @param callback scan results
     */
    public void scanQRCode(ScanResult callback) {
        this.callback = callback;
        nativeInstance.scanQRCode();
    }
        
    /**
     * Scans based on the settings in this class and returns the results
     * 
     * @param callback scan results
     */
    public void scanBarCode(ScanResult callback) {
        this.callback = callback;
        nativeInstance.scanBarCode();
    }
    
    
    /**
     * Called upon a successful scan operation
     * 
     * @param contents the contents of the data
     * @param formatName the format of the scan
     * @param rawBytes the bytes of data
     */
    static void scanCompletedCallback(final String contents, final String formatName, final byte[] rawBytes) {
        if (getInstance().callback != null) {
            Display.getInstance().callSerially(new Runnable() {
                public void run() {
                    getInstance().callback.scanCompleted(contents, formatName, rawBytes);
                    getInstance().callback = null;
                    Display.getInstance().getCurrent().revalidate();
                    Display.getInstance().getCurrent().repaint();
                }
            }); 
        }
    }
    
    /**
     * Invoked if the user canceled the scan
     */
    static void scanCanceledCallback() {
        if (getInstance().callback != null) {
            Display.getInstance().callSerially(new Runnable() {
                public void run() {
                    getInstance().callback.scanCanceled();
                    getInstance().callback = null;
                    Display.getInstance().getCurrent().revalidate();
                    Display.getInstance().getCurrent().repaint();
                }
            });
        }
    }
    
    /**
     * Invoked if an error occurred during the scanning process
     * 
     * @param errorCode code
     * @param message descriptive message
     */
    static void scanErrorCallback(final int errorCode, final String message) {
        if (getInstance().callback != null) {
            Display.getInstance().callSerially(new Runnable() {
                public void run() {
                    getInstance().callback.scanError(errorCode, message);
                    getInstance().callback = null;
                    Display.getInstance().getCurrent().revalidate();
                    Display.getInstance().getCurrent().repaint();
                }
            });
            
        }
    }
}
