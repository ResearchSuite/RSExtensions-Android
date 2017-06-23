package org.researchsuite.rsextensions.location;

import org.researchsuite.rsextensions.studyManagement.CTFActivityRun;

/**
 * Created by jameskizer on 4/21/17.
 */

public interface RSActivityManagerDelegate {
    boolean tryToLaunchRSActivity(RSActivityManager activityManager, CTFActivityRun activityRun);
}
