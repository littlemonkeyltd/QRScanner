package org.littlemonkey.qrscanner;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;
import android.text.TextUtils;
import com.codename1.impl.android.IntentResultListener;
import net.sourceforge.zbar.Symbol;
import com.dm.zbar.android.scanner.*;

public class NativeScannerImpl {

    public void scanQRCode() {
        final android.app.Activity ctx = com.codename1.impl.android.AndroidNativeUtil.getActivity();
        Intent intent = new Intent(ctx, ZBarScannerActivity.class);
        intent.putExtra(ZBarConstants.SCAN_MODES, new int[]{Symbol.QRCODE});
        com.codename1.impl.android.AndroidNativeUtil.startActivityForResult(intent,new IntentResultListener() {
            public void onActivityResult(int requestCode, int resultCode, Intent data) {
                if (requestCode == 0) {
                    if (resultCode == Activity.RESULT_OK) {
                        QRScanner.getCallback().scanCompleted(data.getStringExtra(ZBarConstants.SCAN_RESULT), "QRCODE", data.getStringExtra(ZBarConstants.SCAN_RESULT).getBytes());
                    } else if (resultCode == Activity.RESULT_CANCELED && data != null) {
                        String error = data.getStringExtra(ZBarConstants.ERROR_INFO);
                        if (!TextUtils.isEmpty(error)) {
                            QRScanner.getCallback().scanError(100, error);
                        } else {
                            QRScanner.getCallback().scanCanceled();
                        }
                    }
                }
            }
        });
    }

    public void scanBarCode() {
    }

    public boolean isSupported() {
        return true;
    }

}
