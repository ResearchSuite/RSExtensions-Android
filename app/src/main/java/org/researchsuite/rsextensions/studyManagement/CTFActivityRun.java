package org.researchsuite.rsextensions.studyManagement;

import com.google.gson.JsonElement;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by jameskizer on 3/7/17.
 */

public class CTFActivityRun implements Serializable {
    public final Integer requestId;
    public final String identifier;
    public final UUID taskRunUUID;
    public final JsonElement activity;
   // public final List<RSRPResultTransform> resultTransforms;

    public CTFActivityRun(String identifier, UUID taskRunUUID, Integer requestId, JsonElement activity) {

        this.identifier = identifier;
        this.taskRunUUID = taskRunUUID;
        this.requestId = requestId;
        this.activity = activity;
        //this.resultTransforms = resultTransforms;
    }
}
