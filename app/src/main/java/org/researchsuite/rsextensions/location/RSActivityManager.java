package org.researchsuite.rsextensions.location;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import org.researchstack.backbone.ResourcePathManager;
import org.researchstack.backbone.result.TaskResult;
import org.researchstack.backbone.step.Step;
import org.researchstack.backbone.task.OrderedTask;
import org.researchstack.backbone.task.Task;
import org.researchstack.backbone.utils.LogExt;
import org.researchsuite.rsextensions.studyManagement.CTFActivityRun;
import org.researchsuite.rsextensions.studyManagement.CTFScheduleItem;
import org.researchsuite.rsextensionsrstb.LocationStepGenerator;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

//rsextensionsrstb/src/main/java/org/researchsuite/rsextensionsrstb/LocationStepGenerator.java

/**
 * Created by jameskizer on 4/21/17.
 */

public class RSActivityManager {

    private static final String TAG = "RSActivityManager";

   // public RSTBTaskBuilder taskBuilder;
    public ResourcePathManager resourcePathManager;

    private static RSActivityManager sActivityManager;

    @NonNull
    public static RSActivityManager get() {
        if (sActivityManager == null) {
            sActivityManager = new RSActivityManager();
        }
        return sActivityManager;
    }


    private Random rand;
    private ConcurrentLinkedQueue<CTFActivityRun> activityQueue;

    private Object delegateLock = new Object();
    private RSActivityManagerDelegate delegate = null;

    public RSActivityManager() {

        this.rand = new Random();
        this.activityQueue = new ConcurrentLinkedQueue<>();
    }

    public void setDelegate(Context context, RSActivityManagerDelegate delegate) {
        synchronized (this.delegateLock) {
            this.delegate = delegate;
        }
        this.tryToLaunchActivity(context);

    }

    public void clearDelegate(RSActivityManagerDelegate delegate) {
        synchronized (this.delegateLock) {
            if (this.delegate.equals(delegate)) {
                this.delegate = null;
            }
        }
    }

    @Nullable
    private CTFScheduleItem getScheduleItem(Context context, String filename) {
       // resourcePathManager.getResouceAsInputStream(ResourcePathManager.Resource.TYPE_JSON,"json",filename,CTFScheduleItem.class);

        ResourcePathManager.Resource resource = new ResourcePathManager.Resource(ResourcePathManager.Resource.TYPE_JSON,
               "json",
                filename,
                CTFScheduleItem.class);

        return resource.create(context);
    }

    public void readyToLaunchActivity(Context context) {
        this.tryToLaunchActivity(context);
    }

    private void tryToLaunchActivity(Context context) {

        RSActivityManagerDelegate delegate;
        synchronized (this.delegateLock) {
            if (this.delegate == null) {
                return;
            }
            delegate = this.delegate;
        }


        if (!this.activityQueue.isEmpty()) {

            CTFActivityRun activityRun = this.activityQueue.peek();

            boolean launched = delegate.tryToLaunchRSActivity(this, activityRun);
            if (launched) {
                this.activityQueue.poll();
            }

            Task task = loadTask(context, activityRun);

            if (task == null) {
                Toast.makeText(context,
                        "No task",
                        Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }

    public void queueActivity(Context context, String filename, boolean tryToLaunch) {

        CTFScheduleItem scheduleItem = this.getScheduleItem(context, filename);
        CTFActivityRun activityRun = activityRunForItem(scheduleItem);

        //queue activity run
        Log.d("here in queue", scheduleItem.title);
        this.activityQueue.add(activityRun);
        this.tryToLaunchActivity(context);
    }

    public CTFActivityRun activityRunForItem(CTFScheduleItem item) {


        CTFActivityRun activityRun = new CTFActivityRun(
                item.identifier,
                UUID.randomUUID(),
                0,
                item.activity
        );

        return activityRun;
    }

    @Nullable
    public Task loadTask(Context context, CTFActivityRun activityRun) {

        LocationStepGenerator stepGenerator = new LocationStepGenerator(RSTaskBuilderManager.getBuilder().getStepBuilderHelper(),"textfield",activityRun.activity.getAsJsonObject());

        Step step = stepGenerator.generateStep(RSTaskBuilderManager.getBuilder().getStepBuilderHelper(),"textfield",activityRun.activity.getAsJsonObject());

        if (step != null){
            return new OrderedTask(activityRun.identifier,step);
        }
        else {
            return null;
        }


//        List<Step> stepList = null;
//        try {
//            stepList = RSTaskBuilderManager.getBuilder().stepsForElement(activityRun.activity);
//        }
//        catch(Exception e) {
//            Log.w(this.TAG, "could not create steps from task json", e);
//            return null;
//        }
//        if (stepList != null && stepList.size() > 0) {
//            return new OrderedTask(activityRun.identifier, stepList);
//        }
//        else {
//            return null;
//        }
    }

    public void completeActivity(Context context, TaskResult taskResult, CTFActivityRun activityRun) {

        assert(activityRun != null);

        LogExt.d(getClass(), String.valueOf(taskResult));


        this.tryToLaunchActivity(context);

    }


}
