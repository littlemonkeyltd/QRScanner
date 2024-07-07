package org.littlemonkey.qrscanner;


/**
 * 
 *  @author nick
 */
public interface NativeScanner {

	/**
	 *  Scans based on the settings in this class and returns the results
	 */
	public void scanQRCode();

	/**
	 *  Scans based on the settings in this class and returns the results
	 */
	public void scanBarCode();
}
