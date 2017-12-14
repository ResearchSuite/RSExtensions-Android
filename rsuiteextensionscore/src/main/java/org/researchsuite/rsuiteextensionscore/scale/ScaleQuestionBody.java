package org.researchsuite.rsuiteextensionscore.scale;

import android.content.res.Resources;
import android.support.v7.widget.AppCompatSeekBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import org.researchstack.backbone.result.StepResult;
import org.researchstack.backbone.step.Step;
import org.researchstack.backbone.ui.step.body.BodyAnswer;
import org.researchstack.backbone.ui.step.body.StepBody;
import org.researchsuite.rsuiteextensionscore.R;

/**
 * Created by jameskizer on 10/10/17.
 */

public class ScaleQuestionBody implements StepBody {

    private ScaleQuestionStep step;
    private StepResult<Integer> result;
    private ScaleAnswerFormat format;

    private int viewType;
    private int sliderValue;

    public ScaleQuestionBody(Step step, StepResult result)
    {
        this.step = (ScaleQuestionStep) step;
        this.result = result == null ? new StepResult<>(step) : result;

        this.format = (ScaleAnswerFormat) this.step.getAnswerFormat();
    }

    @Override
    public View getBodyView(int viewType, LayoutInflater inflater, ViewGroup parent) {

        this.viewType = viewType;

        View view = getViewForType(viewType, inflater, parent);

        return view;
    }

    protected View getViewForType(int viewType, LayoutInflater inflater, ViewGroup parent)
    {

        if(viewType == VIEW_TYPE_DEFAULT)
        {
            return initView(inflater, parent);
        }
        else if(viewType == VIEW_TYPE_COMPACT)
        {
            throw new UnsupportedOperationException("Scale is not available as part of a form");
        }
        else
        {
            throw new IllegalArgumentException("Invalid View Type");
        }

    }

    protected View initView(LayoutInflater inflater, ViewGroup parent) {

        View scaleView = inflater.inflate(R.layout.scale_view, parent, false);

        //set previous or default value
        Integer integerResult = result.getResult();
        if (integerResult != null) {
            this.sliderValue = integerResult - this.format.getMinimum();
        }
        else {
            this.sliderValue = this.format.getDefaultValue() - this.format.getMinimum();
        }

        // Value Indicator
        final TextView valueLabel = scaleView.findViewById(R.id.current_value_label);
        valueLabel.setText(""+(this.sliderValue + this.format.getMinimum()) );

        TextView questionLabel = (TextView) scaleView.findViewById(R.id.question_text);
        questionLabel.setVisibility(View.GONE);

        TextView minValueLabel = (TextView) scaleView.findViewById(R.id.min_value_label);
        minValueLabel.setText(""+this.format.getMinimum());

        TextView maxValueLabel = (TextView) scaleView.findViewById(R.id.max_value_label);
        maxValueLabel.setText(""+this.format.getMaximum());


        TextView minTextLabel = (TextView) scaleView.findViewById(R.id.min_label);
        minTextLabel.setText(this.format.getMinimumValueDescription());

        TextView neutralTextLabel = (TextView) scaleView.findViewById(R.id.neutral_label);
        if (this.format.getNeutralValueDescription() != null) {
            neutralTextLabel.setText(this.format.getNeutralValueDescription());
        }
        else {
            neutralTextLabel.setVisibility(View.GONE);
        }


        TextView maxTextLabel = (TextView) scaleView.findViewById(R.id.max_label);
        maxTextLabel.setText(this.format.getMaximumValueDescription());

        // SeekBar
        AppCompatSeekBar seekBar = (AppCompatSeekBar) scaleView.findViewById(R.id.value_slider);


        //internal max = max - min
        seekBar.setMax(this.format.getMaximum()-this.format.getMinimum());

//        seekBar.setMin(this.format.getMinimum());
//        seekBar.setMin(this.format.getMinimum().intValue());
//        seekBar.setMax(this.format.getMaximum().intValue());

        seekBar.setProgress(this.sliderValue);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                sliderValue = progress;
                valueLabel.setText(""+ (progress  + format.getMinimum()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        return scaleView;

    }

    @Override
    public StepResult getStepResult(boolean skipped)
    {
        if(skipped)
        {
            result.setResult(null);
        }
        else
        {
            result.setResult(this.sliderValue + this.format.getMinimum());
        }

        return result;
    }



    @Override
    public BodyAnswer getBodyAnswerState()
    {
        return BodyAnswer.VALID;
    }
}
