package org.researchsuite.rsuiteextensionscore;

import org.researchstack.backbone.step.Step;

/**
 * Created by jameskizer on 8/7/17.
 */

public class RSRedirectStep extends Step {

    private String buttonText;
    public RSRedirectStep(String identifier, Class redirectStepLayoutClass) {
        super(identifier);
        this.setStepLayoutClass(redirectStepLayoutClass);
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }
}
