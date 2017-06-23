package org.researchsuite.rsextensionsrstb;

/**
 * Created by Christina on 6/20/17.
 */

import com.google.gson.JsonObject;

import org.researchstack.backbone.answerformat.AnswerFormat;
import org.researchstack.backbone.answerformat.TextAnswerFormat;
import org.researchstack.backbone.step.Step;
import org.researchsuite.rsuiteextensionscore.LocationStep;

import java.util.Arrays;

import edu.cornell.tech.foundry.researchsuitetaskbuilder.DefaultStepGenerators.RSTBQuestionStepGenerator;
import edu.cornell.tech.foundry.researchsuitetaskbuilder.RSTBTaskBuilderHelper;


public class LocationStepGenerator extends RSTBQuestionStepGenerator {

    private LocationStep step;

    public LocationStepGenerator(RSTBTaskBuilderHelper helper, String type, JsonObject jsonObject){
        super();
        this.supportedTypes = Arrays.asList(type);
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

        TextAnswerFormat answerFormat = new TextAnswerFormat(locationStepDescriptor.maximumLength);
        answerFormat.setIsMultipleLines(locationStepDescriptor.multipleLines);



        this.step = new LocationStep(locationStepDescriptor.identifier,locationStepDescriptor.title, answerFormat);

        return step;



    }






}
