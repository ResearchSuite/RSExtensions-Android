package org.researchsuite.rsextensions;

import android.util.Log;

import com.google.gson.JsonObject;

import org.researchstack.backbone.step.Step;
import org.researchsuite.rsextensionsrstb.RSRedirectStepDescriptor;
import org.researchsuite.rstb.DefaultStepGenerators.RSTBBaseStepGenerator;
import org.researchsuite.rstb.RSTBTaskBuilderHelper;
import org.researchsuite.rsuiteextensionscore.RSRedirectStep;

import java.util.Arrays;

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
