/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.ext.codescan;

import com.codename1.system.NativeInterface;

/**
 *
 * @author shannah
 */
public interface NativeCodeScanner extends NativeInterface {
    public void scanQRCode();
    public void scanBarCode();
}
