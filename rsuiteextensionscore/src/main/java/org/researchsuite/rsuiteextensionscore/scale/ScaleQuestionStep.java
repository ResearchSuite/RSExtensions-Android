package org.researchsuite.rsuiteextensionscore.scale;

import org.researchstack.backbone.answerformat.AnswerFormat;
import org.researchstack.backbone.step.QuestionStep;

/**
 * Created by jameskizer on 10/10/17.
 */

public class ScaleQuestionStep extends QuestionStep {

    public ScaleQuestionStep(String identifier, String title, AnswerFormat format) {
        super(identifier, title, format);
    }


    @Override
    public Class<?> getStepBodyClass()
    {
        return ScaleQuestionBody.class;
    }
}
