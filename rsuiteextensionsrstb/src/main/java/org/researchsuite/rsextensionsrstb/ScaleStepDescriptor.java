package org.researchsuite.rsextensionsrstb;

import com.google.gson.annotations.SerializedName;

import edu.cornell.tech.foundry.researchsuitetaskbuilder.DefaultStepGenerators.descriptors.RSTBQuestionStepDescriptor;

/**
 * Created by jameskizer on 10/10/17.
 */

public class ScaleStepDescriptor extends RSTBQuestionStepDescriptor {

    public Integer maximumValue;
    public Integer minimumValue;
    public Integer defaultValue;
    public Integer step;

    public Boolean vertical = false;
    public String maximumDescription;
    public String minimumDescription;
    public String neutralDescription;

    public ScaleStepDescriptor() {

    }

}
