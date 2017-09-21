package org.researchsuite.rsuiteextensionscore;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import org.researchstack.backbone.result.StepResult;
import org.researchstack.backbone.step.Step;
import org.researchstack.backbone.ui.callbacks.StepCallbacks;
import org.researchstack.backbone.ui.step.layout.StepLayout;

/**
 * Created by jameskizer on 8/7/17.
 */

public abstract class RSRedirectStepLayout extends RelativeLayout implements StepLayout {

    private RSRedirectStep step;
    private StepResult   stepResult;
    private StepCallbacks callbacks;

    protected Context context;

    private AppCompatButton mLogInButton;
    private AppCompatTextView mTitle;
    private AppCompatTextView mText;

    private Handler mHandler;

    public RSRedirectStepLayout(Context context)
    {
        super(context);
        this.context = context;
    }

    public RSRedirectStepLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.context = context;
    }

    public RSRedirectStepLayout(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @Override
    public void initialize(Step step, StepResult result) {

        this.step = (RSRedirectStep) step;
        this.stepResult = result == null ? new StepResult<>(step) : result;

        View layout = LayoutInflater.from(getContext()).inflate(R.layout.layout_redirect, this, true);

        this.mLogInButton = (AppCompatButton) findViewById(R.id.log_in_button);
        if (this.step.getButtonText() != null) {
            this.mLogInButton.setText(this.step.getButtonText());
        }

        this.mLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logInTapped();
            }
        });

        this.mTitle = (AppCompatTextView) findViewById(R.id.title);
        if (this.step.getTitle() != null) {
            this.mTitle.setText(this.step.getTitle());
            this.mTitle.setVisibility(VISIBLE);
        }
        else {
            this.mTitle.setVisibility(GONE);
        }

        this.mText = (AppCompatTextView) findViewById(R.id.text);
        if (this.step.getText() != null) {
            this.mText.setText(this.step.getText());
            this.mText.setVisibility(VISIBLE);
        }
        else {
            this.mText.setVisibility(GONE);
        }

        this.mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                if (getDelegate().isCompleted() && getDelegate().getError() == null) {
                    moveForward();
                }
                return true;
            }
        });

        this.getDelegate().setHandler(this.mHandler);
    }

    abstract protected RSRedirectStepDelegate getDelegate();

    private void logInTapped() {

        //set up handler

        RSRedirectStepDelegate delegate = this.getDelegate();
        delegate.beginRedirect(this.context);
    }

//    @Override
//    protected void onVisibilityChanged(View changedView, int visibility) {
//
//        //if visible, check application state to see if we can move forward
//        RSRedirectStepDelegate delegate = this.getDelegate();
//        if (visibility == VISIBLE && delegate != null && delegate.isCompleted() != null) {
//
//            //may need to busy wait here...
//
//            if (delegate.getError() == null) {
//                this.moveForward();
//            }
//
//        }
//
//        super.onVisibilityChanged(changedView, visibility);
//    }



    @Override
    public Parcelable onSaveInstanceState()
    {
        callbacks.onSaveStep(StepCallbacks.ACTION_NONE, this.step, this.getStepResult(false));
        return super.onSaveInstanceState();
    }

    public void moveForward() {
        callbacks.onSaveStep(StepCallbacks.ACTION_NEXT, this.step, this.getStepResult(true));
    }

    @Override
    public View getLayout() {
        return this;
    }

    @Override
    public boolean isBackEventConsumed() {
        callbacks.onSaveStep(StepCallbacks.ACTION_PREV, step, this.getStepResult(false));
        return false;
    }

    @Override
    public void setCallbacks(StepCallbacks callbacks) {
        this.callbacks = callbacks;
    }

    public StepResult getStepResult(Boolean completed)
    {
        stepResult.setResult(completed);
        return stepResult;
    }
}
