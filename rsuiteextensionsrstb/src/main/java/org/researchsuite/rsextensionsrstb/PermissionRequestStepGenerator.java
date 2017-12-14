package org.researchsuite.rsextensionsrstb;

import com.google.gson.JsonObject;

import org.researchstack.backbone.step.Step;
import org.researchsuite.rstb.DefaultStepGenerators.RSTBBaseStepGenerator;
import org.researchsuite.rstb.RSTBTaskBuilderHelper;
import org.researchsuite.rsuiteextensionscore.PermissionRequestStep;

import java.util.Arrays;

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
    public Step generateStep(RSTBTaskBuilderHelper helper, String type, JsonObject jsonObject) {

        PermissionRequestStepDescriptor stepDescriptor = helper.getGson().fromJson(jsonObject, PermissionRequestStepDescriptor.class);

        PermissionRequestStep step = new PermissionRequestStep(stepDescriptor.identifier);
        step.setTitle(stepDescriptor.title);
        step.setText(stepDescriptor.text);
        step.setOptional(stepDescriptor.optional);
        step.setButtonText(stepDescriptor.buttonText);

        String[] permissions = new String[stepDescriptor.permissions.size()];
        permissions = stepDescriptor.permissions.toArray(permissions);
        step.setPermissions(permissions);

        return step;

    }
}
