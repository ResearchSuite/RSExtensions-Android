package org.researchsuite.rsextensionsrstb;

/**
 * Created by Christina on 6/20/17.
 */

import android.location.Location;

import com.google.gson.JsonObject;

import org.researchstack.backbone.answerformat.AnswerFormat;
import org.researchstack.backbone.answerformat.TextAnswerFormat;
import org.researchstack.backbone.step.Step;
import java.util.Arrays;

import edu.cornell.tech.foundry.researchsuitetaskbuilder.DefaultStepGenerators.RSTBQuestionStepGenerator;
import edu.cornell.tech.foundry.researchsuitetaskbuilder.RSTBTaskBuilderHelper;

import org.researchsuite.rsuiteextensionscore.LocationAnswerFormat;
import org.researchsuite.rsuiteextensionscore.LocationStep;


public class LocationStepGenerator extends RSTBQuestionStepGenerator {

    private LocationStep step;

    public LocationStepGenerator(){
        super();
        this.supportedTypes = Arrays.asList
                ("location");
    }

    @Override
    public AnswerFormat generateAnswerFormat(RSTBTaskBuilderHelper helper, String type, JsonObject jsonObject) {
        LocationStepDescriptor locationStepDescriptor = helper.getGson().fromJson(jsonObject, LocationStepDescriptor.class);

        TextAnswerFormat answerFormat = new TextAnswerFormat(locationStepDescriptor.maximumLength);
        answerFormat.setIsMultipleLines(locationStepDescriptor.multipleLines);

        return answerFormat;
    }

    @Override
    public Step generateStep(RSTBTaskBuilderHelper helper, String type, JsonObject jsonObject) {

        LocationStepDescriptor locationStepDescriptor = helper.getGson().fromJson(jsonObject,LocationStepDescriptor.class);

        if (locationStepDescriptor == null) {
            return null;
        }

//        TextAnswerFormat answerFormat = new TextAnswerFormat(locationStepDescriptor.maximumLength);
//        answerFormat.setIsMultipleLines(locationStepDescriptor.multipleLines);

        LocationAnswerFormat answerFormat = new LocationAnswerFormat();

        this.step = new LocationStep(locationStepDescriptor.identifier,locationStepDescriptor.title,locationStepDescriptor.text, answerFormat);

        return step;



    }






}
