package org.researchsuite.rsextensionsrstb;

import org.researchsuite.rstb.DefaultStepGenerators.descriptors.RSTBStepDescriptor;

import java.util.List;

/**
 * Created by jameskizer on 9/26/17.
 */

public class PermissionRequestStepDescriptor extends RSTBStepDescriptor {
    public String buttonText;
    public List<String> permissions;

    PermissionRequestStepDescriptor() {

    }

}
