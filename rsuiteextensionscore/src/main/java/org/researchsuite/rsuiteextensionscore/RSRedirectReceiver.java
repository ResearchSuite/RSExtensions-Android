package org.researchsuite.rsuiteextensionscore;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;

import org.researchstack.backbone.ui.ViewTaskActivity;
import org.researchstack.backbone.utils.LogExt;

import static android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;

/**
 * Created by jameskizer on 8/7/17.
 */

public abstract class RSRedirectReceiver extends AppCompatActivity {

    public abstract Class getActivityClass();

    @Override
    public void onCreate(Bundle savedInstanceState) {

//        LogExt.d(getClass(), "Handled redirect");
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String action = intent.getAction();
        Uri data = intent.getData();

        LogExt.d(getClass(), "Handled redirect: " + data);

        RSOpenURLManager.getInstance().handleURL(data);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 2000);

        Class activityClass = this.getActivityClass();
        Intent startTask = new Intent(this, activityClass);
        startTask.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);

        this.startActivity(startTask);

//        finish();
//        super.onCreate(savedInstanceState);
    }


}
