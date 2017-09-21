package org.researchsuite.rsextensions;

import android.util.Log;

import com.google.gson.JsonObject;

import org.researchstack.backbone.answerformat.AnswerFormat;
import org.researchstack.backbone.step.QuestionStep;
import org.researchstack.backbone.step.Step;
import org.researchsuite.rsextensionsrstb.RSRedirectStepDescriptor;
import org.researchsuite.rsuiteextensionscore.RSRedirectStep;

import java.util.Arrays;

import edu.cornell.tech.foundry.researchsuitetaskbuilder.DefaultStepGenerators.RSTBBaseStepGenerator;
import edu.cornell.tech.foundry.researchsuitetaskbuilder.DefaultStepGenerators.descriptors.RSTBCustomStepDescriptor;
import edu.cornell.tech.foundry.researchsuitetaskbuilder.DefaultStepGenerators.descriptors.RSTBQuestionStepDescriptor;
import edu.cornell.tech.foundry.researchsuitetaskbuilder.RSTBTaskBuilderHelper;

/**
 * Created by jameskizer on 8/16/17.
 */

public class RSTestRedirectStepGenerator extends RSTBBaseStepGenerator {


    public RSTestRedirectStepGenerator()
    {
        super();
        this.supportedTypes = Arrays.asList(
                "testRedirect"
        );
    }

    @Override
    public Step generateStep(RSTBTaskBuilderHelper helper, String type, JsonObject jsonObject) {

        RSRedirectStepDescriptor stepDescriptor = helper.getGson().fromJson(jsonObject, RSRedirectStepDescriptor.class);

        RSRedirectStep step = new RSRedirectStep(stepDescriptor.identifier, RSTestRedirectStepLayout.class);
        step.setTitle(stepDescriptor.title);
        step.setText(stepDescriptor.text);
        step.setOptional(stepDescriptor.optional);

        step.setButtonText(stepDescriptor.buttonText);

        return step;
    }
}
