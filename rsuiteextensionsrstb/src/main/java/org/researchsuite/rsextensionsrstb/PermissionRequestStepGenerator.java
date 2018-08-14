package org.researchsuite.rsextensionsrstb;

import com.google.gson.JsonObject;

import org.researchstack.backbone.step.Step;
import org.researchsuite.rstb.DefaultStepGenerators.RSTBBaseStepGenerator;
import org.researchsuite.rstb.RSTBTaskBuilderHelper;
import org.researchsuite.rsuiteextensionscore.PermissionRequestStep;
import org.researchsuite.rsuiteextensionscore.scale.ScaleAnswerFormat;
import org.researchsuite.rsuiteextensionscore.scale.ScaleQuestionStep;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by jameskizer on 9/26/17.
 */

public class PermissionRequestStepGenerator extends RSTBBaseStepGenerator {

    public PermissionRequestStepGenerator(){
        super();
        this.supportedTypes = Arrays.asList(
                "permissionRequest"
        );
    }

    @Override
    public List<Step> generateSteps(RSTBTaskBuilderHelper helper, String type, JsonObject jsonObject, String identifierPrefix) {

        PermissionRequestStepDescriptor stepDescriptor = helper.getGson().fromJson(jsonObject, PermissionRequestStepDescriptor.class);

        String identifier = this.combineIdentifiers(stepDescriptor.identifier, identifierPrefix);
        PermissionRequestStep step = new PermissionRequestStep(identifier);
        step.setTitle(stepDescriptor.title);
        step.setText(stepDescriptor.text);
        step.setOptional(stepDescriptor.optional);
        step.setButtonText(stepDescriptor.buttonText);

        String[] permissions = new String[stepDescriptor.permissions.size()];
        permissions = stepDescriptor.permissions.toArray(permissions);
        step.setPermissions(permissions);

        return Collections.singletonList((Step)step);

    }
}
