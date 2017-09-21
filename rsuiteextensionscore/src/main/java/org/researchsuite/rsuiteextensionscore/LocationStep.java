package org.researchsuite.rsuiteextensionscore;

import org.researchstack.backbone.step.Step;

/**
 * Created by Christina on 6/20/17.
 */

public class LocationStep extends Step {

    @Override
    public Class getStepLayoutClass() {
        return LocationStepLayout.class;
    }

    /** Returns a new location step that includes the specified identifier
     * @param identifier The identifier of the step
     */
    public LocationStep(String identifier) {
        super(identifier);
    }

}
