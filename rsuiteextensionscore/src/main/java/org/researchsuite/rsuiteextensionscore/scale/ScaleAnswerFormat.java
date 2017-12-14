package org.researchsuite.rsuiteextensionscore.scale;

import org.researchstack.backbone.answerformat.AnswerFormat;

/**
 * Created by jameskizer on 10/10/17.
 */

public class ScaleAnswerFormat extends AnswerFormat {

    private Integer maximum;
    private Integer minimum;
    private Integer defaultValue;
    private Integer step;
    private Boolean vertical;
    private String maximumValueDescription;
    private String minimumValueDescription;
    private String neutralValueDescription;

    public ScaleAnswerFormat(Integer maximum, Integer minimum, Integer defaultValue, Integer step, Boolean vertical, String maximumValueDescription, String minimumValueDescription, String neutralValueDescription) {
        this.maximum = maximum;
        this.minimum = minimum;
        this.defaultValue = defaultValue;
        this.step = step;
        this.vertical = vertical;
        this.maximumValueDescription = maximumValueDescription;
        this.minimumValueDescription = minimumValueDescription;
        this.neutralValueDescription = neutralValueDescription;
    }

    public Integer getMaximum() {
        return maximum;
    }

    public Integer getMinimum() {
        return minimum;
    }

    public Integer getDefaultValue() {
        return defaultValue;
    }

    public Integer getStep() {
        return step;
    }

    public Boolean getVertical() {
        return vertical;
    }

    public String getMaximumValueDescription() {
        return maximumValueDescription;
    }

    public String getMinimumValueDescription() {
        return minimumValueDescription;
    }

    public String getNeutralValueDescription() {
        return neutralValueDescription;
    }
}
