package org.researchsuite.rsuiteextensionscore;

/**
 * Created by jameskizer on 9/26/17.
 */

public interface PermissionRequestDelegate {

    interface OnCompletion {
        void onCompletion(String[] permissions, int[] grantResults);
    }

    int checkSelfPermission(String permission);
    void requestPermissions(String[] permissions, OnCompletion completion);

}
