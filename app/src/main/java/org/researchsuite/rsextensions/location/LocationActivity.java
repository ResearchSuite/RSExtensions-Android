package org.researchsuite.rsextensions.location;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import org.researchstack.backbone.result.TaskResult;
import org.researchstack.backbone.task.Task;
import org.researchstack.backbone.ui.ViewTaskActivity;
import org.researchstack.backbone.utils.LogExt;
import org.researchsuite.rsextensions.studyManagement.CTFActivityRun;


/**
 * Created by jameskizer on 4/21/17.
 */

public class LocationActivity extends Activity implements RSActivityManagerDelegate {

 //   private boolean dataReady = false;



    private static int REQUEST_RS_ACTIVITY = 0xff30;

    private Object runningActivityLock = new Object();
    private CTFActivityRun runningActivity;
    private static String RUNNING_ACTIVITY_KEY = "RUNNING_ACTIVITY_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RSActivityManager.get().readyToLaunchActivity(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION  }, 0);
            return;
        }



    }
//    @Override
//    public void onDataReady() {
//        super.onDataReady();
//        this.dataReady = true;
//
//    }

    @Override
    public void onResume() {
        super.onResume();
        RSActivityManager.get().setDelegate(this, this);
    }

    @Override
    public void onPause() {
        super.onPause();
      //  this.dataReady = false;
    }

    @Override
    public boolean tryToLaunchRSActivity(RSActivityManager activityManager, CTFActivityRun activityRun) {

//        if (!this.dataReady) {
//            return false;
//        }

        synchronized (this.runningActivityLock) {
            //check to see if there is already a running activity
            if (this.runningActivity == null) {

                Task task = activityManager.loadTask(this, activityRun);

                if (task == null) {
                    Toast.makeText(this,
                            org.researchstack.skin.R.string.rss_local_error_load_task,
                            Toast.LENGTH_SHORT).show();
                    return true;
                }

                Intent intent = ViewTaskActivity.newIntent(this, task);

                this.runningActivity = activityRun;
                startActivityForResult(intent, REQUEST_RS_ACTIVITY);
                return true;
            }
        }

        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_RS_ACTIVITY) {
            CTFActivityRun activityRun = this.runningActivity;
            synchronized (this.runningActivityLock) {
                this.runningActivity = null;
            }

            if (resultCode == Activity.RESULT_OK) {
                assert (activityRun != null);
                LogExt.d(getClass(), "Received task result from task activity");
                TaskResult taskResult = (TaskResult) data.getSerializableExtra(ViewTaskActivity.EXTRA_TASK_RESULT);
                RSActivityManager.get().completeActivity(this, taskResult, activityRun);
            } else {

            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (permissions.length == 1 &&
                    permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // mMap.setMyLocationEnabled(true);
            } else {
                // Permission was denied. Display an error message.
            }
        }
    }

}
