package org.researchsuite.rsextensionsrstb;

import android.support.annotation.NonNull;

import com.google.gson.JsonObject;

import org.researchstack.backbone.answerformat.AnswerFormat;
import org.researchstack.backbone.step.Step;
import org.researchsuite.rstb.DefaultStepGenerators.RSTBQuestionStepGenerator;
import org.researchsuite.rstb.RSTBTaskBuilderHelper;
import org.researchsuite.rsuiteextensionscore.scale.ScaleAnswerFormat;
import org.researchsuite.rsuiteextensionscore.scale.ScaleQuestionStep;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by jameskizer on 10/10/17.
 */

public class ScaleStepGenerator extends RSTBQuestionStepGenerator {

    public ScaleStepGenerator(){
        super();
        this.supportedTypes = Collections.singletonList("scale");
    }


    @Override
    public AnswerFormat generateAnswerFormat(@NonNull RSTBTaskBuilderHelper helper, @NonNull String type, @NonNull JsonObject jsonObject) {

        ScaleStepDescriptor descriptor = helper.getGson().fromJson(jsonObject,ScaleStepDescriptor.class);
        if (descriptor == null) {
            return null;
        }

//        public ScaleAnswerFormat(Integer maximum, Integer minimum, Integer defaultValue, Integer step, Boolean vertical, String maximumValueDescription, String minimumValueDescription, String neutralValueDescription)
        return new ScaleAnswerFormat(
                descriptor.maximumValue,
                descriptor.minimumValue,
                descriptor.defaultValue,
                descriptor.step,
                descriptor.vertical,
                descriptor.maximumDescription,
                descriptor.minimumDescription,
                descriptor.neutralDescription
        );

    }

    @Override
    public List<Step> generateSteps(RSTBTaskBuilderHelper helper, String type, JsonObject jsonObject, String identifierPrefix) {
        ScaleStepDescriptor descriptor = helper.getGson().fromJson(jsonObject,ScaleStepDescriptor.class);
        if (descriptor == null) {
            return null;
        }

        ScaleAnswerFormat answerFormat = (ScaleAnswerFormat) this.generateAnswerFormat(helper, type, jsonObject);
        if (answerFormat == null) {
            return null;
        }

        String identifier = this.combineIdentifiers(descriptor.identifier, identifierPrefix);
        Step step =  new ScaleQuestionStep(identifier, descriptor.title, answerFormat);
        step.setText(descriptor.text);
        step.setOptional(descriptor.optional);

        return Collections.singletonList(step);
    }



}
