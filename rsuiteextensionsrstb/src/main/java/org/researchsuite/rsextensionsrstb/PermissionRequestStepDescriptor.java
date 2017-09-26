package org.researchsuite.rsextensionsrstb;

import java.util.List;

import edu.cornell.tech.foundry.researchsuitetaskbuilder.DefaultStepGenerators.descriptors.RSTBChoiceStepItemDescriptor;
import edu.cornell.tech.foundry.researchsuitetaskbuilder.DefaultStepGenerators.descriptors.RSTBStepDescriptor;

/**
 * Created by jameskizer on 9/26/17.
 */

public class PermissionRequestStepDescriptor extends RSTBStepDescriptor {
    public String buttonText;
    public List<String> permissions;

    PermissionRequestStepDescriptor() {

    }

}
