package org.researchsuite.rsuiteextensionscore;

import org.researchstack.backbone.step.Step;

/**
 * Created by jameskizer on 9/26/17.
 */

public class PermissionRequestStep extends Step {

    @Override
    public Class getStepLayoutClass() {
        return PermissionRequestStepLayout.class;
    }




    private String buttonText;
    private String[] permissions;

    /** Returns a new question step that includes the specified identifier, title, question and
     * answer format
     * @param identifier The identifier of the step
     */
    public PermissionRequestStep(String identifier){
        super(identifier);
    }

    public String getButtonText() {
        return buttonText;
    }

    public String[] getPermissions() {
        return permissions;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public void setPermissions(String[] permissions) {
        this.permissions = permissions;
    }
}
