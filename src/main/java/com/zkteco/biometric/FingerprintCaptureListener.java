package com.zkteco.biometric;


/**
 * Created by admin on 8/7/18.
 */

/**
 * Fredrick Oluoch
 * http://www.blaqueyard.com
 * 0720106420 | 0722508906
 * email: menty44@gmail.com
 */


public interface FingerprintCaptureListener {
    void captureOK(byte[] var1);

    void captureError(int var1);

    void extractOK(byte[] var1);
}
