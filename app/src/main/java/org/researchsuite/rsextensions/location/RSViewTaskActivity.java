package org.researchsuite.rsextensions.location;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;

import org.researchstack.backbone.task.Task;
import org.researchstack.backbone.ui.ViewTaskActivity;
import org.researchsuite.rsuiteextensionscore.PermissionRequestDelegate;

/**
 * Created by jameskizer on 9/26/17.
 */

public class RSViewTaskActivity extends ViewTaskActivity implements PermissionRequestDelegate {

    final static int PERMISSION_REQUEST = 0x4567;
    private OnCompletion permisionCompletionHandler;

    public static Intent newIntent(Context context, Task task)
    {
        Intent intent = new Intent(context, RSViewTaskActivity.class);
        intent.putExtra(EXTRA_TASK, task);
        return intent;
    }

    @Override
    public void requestPermissions(String[] permissions, OnCompletion completion) {
        this.permisionCompletionHandler = completion;
        ActivityCompat.requestPermissions( this, permissions, PERMISSION_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == PERMISSION_REQUEST) {
            this.permisionCompletionHandler.onCompletion(permissions, grantResults);
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
