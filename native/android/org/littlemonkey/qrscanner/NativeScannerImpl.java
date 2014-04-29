package org.littlemonkey.qrscanner;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import com.codename1.impl.android.IntentResultListener;
import com.codename1.io.Log;
import net.sourceforge.zbar.Symbol;
import com.dm.zbar.android.scanner.*;

public class NativeScannerImpl {

    public void scanQRCode() {
        scanCode(true);
    }

    public void scanBarCode() {
        scanCode(false);
    }

    private void scanCode(boolean qrCode) {
        final android.app.Activity ctx = com.codename1.impl.android.AndroidNativeUtil.getActivity();
        Intent intent = new Intent(ctx, ZBarScannerActivity.class);
        if (qrCode) {
            intent.putExtra(ZBarConstants.SCAN_MODES, new int[]{Symbol.QRCODE});
        } else {
            intent.putExtra(ZBarConstants.SCAN_MODES, new int[]{Symbol.EAN13});
        }
        com.codename1.impl.android.AndroidNativeUtil.startActivityForResult(intent, new IntentResultListener() {
            public void onActivityResult(int requestCode, int resultCode, Intent data) {
                if (resultCode == Activity.RESULT_OK) {
                    QRScanner.getCallback().scanCompleted(data.getStringExtra(ZBarConstants.SCAN_RESULT), "QRCODE", data.getStringExtra(ZBarConstants.SCAN_RESULT).getBytes());
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    if (data != null) {
                        String error = data.getStringExtra(ZBarConstants.ERROR_INFO);
                        if (!TextUtils.isEmpty(error)) {
                            QRScanner.getCallback().scanError(100, error);
                        } else {
                            QRScanner.getCallback().scanCanceled();
                        }
                    } else {
                        QRScanner.getCallback().scanCanceled();
                    }
                }
            }
        });
    }

    public boolean isSupported() {
        return true;
    }

}
