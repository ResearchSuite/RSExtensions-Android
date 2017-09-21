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

import edu.cornell.tech.foundry.researchsuitetaskbuilder.DefaultStepGenerators.RSTBBaseStepGenerator;
import edu.cornell.tech.foundry.researchsuitetaskbuilder.DefaultStepGenerators.descriptors.RSTBStepDescriptor;
import edu.cornell.tech.foundry.researchsuitetaskbuilder.RSTBTaskBuilderHelper;


public class LocationStepGenerator extends RSTBBaseStepGenerator {

    public LocationStepGenerator(){
        super();
        this.supportedTypes = Arrays.asList(
                "location"
        );
    }

    @Override
    public Step generateStep(RSTBTaskBuilderHelper helper, String type, JsonObject jsonObject) {

        RSTBStepDescriptor stepDescriptor = helper.getGson().fromJson(jsonObject, RSTBStepDescriptor.class);

        LocationStep step = new LocationStep(stepDescriptor.identifier);
        step.setTitle(stepDescriptor.title);
        step.setText(stepDescriptor.text);
        step.setOptional(stepDescriptor.optional);

        return step;

    }






}
