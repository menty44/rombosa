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


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class FingerprintCaptureThread implements Runnable {
    private FingerprintSensor fingerprintSensor = null;
    private FingerprintCaptureListener fingerprintCaptureListener = null;
    private boolean isCancel = false;
    private CountDownLatch countdownLatch = new CountDownLatch(1);

    public FingerprintCaptureThread(FingerprintSensor fingerprintSensor, int index) {
        this.fingerprintSensor = fingerprintSensor;
        this.fingerprintCaptureListener = (FingerprintCaptureListener)fingerprintSensor.getFingerprintCaptureListenerList().get("key.working.listener." + index);
    }

    public void run() {
        while(!this.isCancel) {
            try {
                Thread.sleep(100L);
            } catch (InterruptedException var5) {
                var5.printStackTrace();
            }

            byte[] fpImage = new byte[this.fingerprintSensor.getImageWidth() * this.fingerprintSensor.getImageHeight()];
            byte[] fpTemplate = new byte[2048];
            boolean ret = false;
            int[] tempLen = new int[]{2048};
            int ret1 = this.fingerprintSensor.capture(fpImage, fpTemplate, tempLen);
            if(ret1 < 0) {
                this.fingerprintCaptureListener.captureError(ret1);
            } else {
                this.fingerprintSensor.setLastTempLen(tempLen[0]);
                this.fingerprintCaptureListener.captureOK(fpImage);
                this.fingerprintCaptureListener.extractOK(fpTemplate);
            }
        }

        this.countdownLatch.countDown();
    }

    public void cancel() {
        this.isCancel = true;

        try {
            this.countdownLatch.await(2L, TimeUnit.SECONDS);
        } catch (InterruptedException var2) {
            var2.printStackTrace();
        }

    }

    public boolean isCancel() {
        return this.isCancel();
    }
}

