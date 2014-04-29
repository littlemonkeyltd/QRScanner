/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.littlemonkey.qrscanner;

import com.codename1.system.NativeInterface;

/**
 *
 * @author nick
 */
public interface NativeScanner extends NativeInterface {

    /**
     * Scans based on the settings in this class and returns the results
     *
     */
    public void scanQRCode();

    /**
     * Scans based on the settings in this class and returns the results
     *
     */
    public void scanBarCode();
    
}
