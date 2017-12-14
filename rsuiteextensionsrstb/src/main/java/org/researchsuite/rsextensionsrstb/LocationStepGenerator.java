package org.researchsuite.rsextensionsrstb;

/**
 * Created by Christina on 6/20/17.
 */

import android.content.Context;
import android.support.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonObject;

import org.apache.commons.lang3.StringUtils;
import org.researchstack.backbone.answerformat.AnswerFormat;
import org.researchstack.backbone.step.Step;

import java.nio.ByteBuffer;
import java.util.Arrays;


import org.researchsuite.rstb.DefaultStepGenerators.RSTBQuestionStepGenerator;
import org.researchsuite.rstb.RSTBStateHelper;
import org.researchsuite.rstb.RSTBTaskBuilderHelper;
import org.researchsuite.rsuiteextensionscore.LocationAnswerFormat;
import org.researchsuite.rsuiteextensionscore.LocationStep;


public class LocationStepGenerator extends RSTBQuestionStepGenerator {

    private LocationStep step;

    public LocationStepGenerator(){
        super();
        this.supportedTypes = Arrays.asList
                ("location");
    }

    public static double toDouble(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getDouble();
    }

    @Nullable
    private LatLng getLatLngInState(Context context, RSTBStateHelper stateHelper, String key) {

        byte[] latBytes = stateHelper.valueInState(context, key+".lat");
        if (latBytes == null) {
            return null;
        }

        byte[] lngBytes = stateHelper.valueInState(context, key+".lng");
        if (lngBytes == null) {
            return null;
        }

        return new LatLng(
                toDouble(latBytes),
                toDouble(lngBytes)
        );
    }

    @Override
    public AnswerFormat generateAnswerFormat(RSTBTaskBuilderHelper helper, String type, JsonObject jsonObject) {
        LocationStepDescriptor locationStepDescriptor = helper.getGson().fromJson(jsonObject, LocationStepDescriptor.class);
        String defaultLocationKey = locationStepDescriptor.defaultLocationKey;

        if (StringUtils.isEmpty(defaultLocationKey)) {
            return new LocationAnswerFormat();
        }

        RSTBStateHelper stateHelper = helper.getStateHelper();
        if (stateHelper == null){
            return new LocationAnswerFormat();
        }

        LatLng defaultPosition = this.getLatLngInState(helper.getContext(), stateHelper, defaultLocationKey);
        if (defaultPosition == null) {
            return new LocationAnswerFormat();
        }
//
        return new LocationAnswerFormat(defaultPosition.latitude, defaultPosition.longitude);
    }

    @Override
    public Step generateStep(RSTBTaskBuilderHelper helper, String type, JsonObject jsonObject) {

        LocationStepDescriptor locationStepDescriptor = helper.getGson().fromJson(jsonObject,LocationStepDescriptor.class);

        if (locationStepDescriptor == null) {
            return null;
        }

        LocationAnswerFormat answerFormat = (LocationAnswerFormat) this.generateAnswerFormat(helper, type, jsonObject);

        this.step = new LocationStep(locationStepDescriptor.identifier,locationStepDescriptor.title,locationStepDescriptor.text, answerFormat);

        return step;

    }






}
