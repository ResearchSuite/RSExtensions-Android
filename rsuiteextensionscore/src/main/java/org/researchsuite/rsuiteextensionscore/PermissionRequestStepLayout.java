package org.researchsuite.rsuiteextensionscore;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.PermissionRequest;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.MapView;

import junit.framework.Assert;

import org.researchstack.backbone.result.StepResult;
import org.researchstack.backbone.step.Step;
import org.researchstack.backbone.ui.callbacks.StepCallbacks;
import org.researchstack.backbone.ui.step.layout.StepLayout;
import org.researchstack.backbone.ui.views.SubmitBar;

import rx.functions.Action1;

/**
 * Created by jameskizer on 9/26/17.
 */

public class PermissionRequestStepLayout extends FrameLayout implements StepLayout {


    private PermissionRequestStep step;
    private StepResult stepResult;
    private StepCallbacks callbacks;

    private AppCompatButton mRequestButton;
    private AppCompatTextView mTitle;
    private AppCompatTextView mText;

    private PermissionRequestDelegate permissionRequestDelegate;
    private int[] mGrantResults;


    public PermissionRequestStepLayout(@NonNull Context context) {
        super(context);
    }

    public PermissionRequestStepLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public PermissionRequestStepLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void initialize(Step step, StepResult result) {

        this.step = (PermissionRequestStep) step;
        this.stepResult = result == null ? new StepResult<>(step) : result;

        View layout = LayoutInflater.from(getContext()).inflate(R.layout.permission_request_layout, this, true);

        this.mRequestButton = (AppCompatButton) findViewById(R.id.request_button);
        if (this.step.getButtonText() != null) {
            this.mRequestButton.setText(this.step.getButtonText());
        }

        this.mRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                beginRequest();
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

        SubmitBar submitBar = (SubmitBar) findViewById(R.id.rsb_submit_bar);
        submitBar.setPositiveAction(new Action1() {
            @Override
            public void call(Object o) {
                onNextClicked();
            }
        });

        if (step.isOptional()) {
            submitBar.setNegativeTitle(R.string.rsb_step_skip);
            submitBar.setNegativeAction(new Action1() {
                @Override
                public void call(Object o) {
                    onSkipClicked();
                }
            });
        }
        else {
            submitBar.getNegativeActionView().setVisibility(View.GONE);
        }

    }

    @Override
    public View getLayout() {

        return this;

    }

    @Override
    public boolean isBackEventConsumed() {
        //this will cancel the event
        callbacks.onSaveStep(StepCallbacks.ACTION_PREV,this.step,this.stepResult);
        return false;
    }

    //ViewTaskActivity invokes this with self
    @Override
    public void setCallbacks(StepCallbacks callbacks) {

        this.callbacks = callbacks;

        if (callbacks instanceof PermissionRequestDelegate) {
            this.permissionRequestDelegate = (PermissionRequestDelegate)callbacks;
        }
        else {
            String text = "Activity does not conform to PermissionRequestDelegate";
            assert(false);
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(getContext(), text, duration);
            toast.show();
        }

    }

    public Step getStep(){
        return this.step;
    }

    @Override
    public Parcelable onSaveInstanceState()
    {
        //don't clear  on rotation
        callbacks.onSaveStep(StepCallbacks.ACTION_NONE, getStep(), this.getStepResult(false));
        return super.onSaveInstanceState();
    }

    protected void onNextClicked()
    {
        callbacks.onSaveStep(StepCallbacks.ACTION_NEXT,
                this.getStep(),
                this.getStepResult(false));

    }

    public void onSkipClicked()
    {
        if(callbacks != null)
        {
            // empty step result when skipped
            callbacks.onSaveStep(StepCallbacks.ACTION_NEXT,
                    this.getStep(),
                    this.getStepResult(true));
        }
    }


    public StepResult getStepResult(boolean shouldClear) {

        if (shouldClear) {
            stepResult.setResult(null);
        }
        else {

            PermissionRequestResult result = new PermissionRequestResult(
                    step.getIdentifier(),
                    this.step.getPermissions(),
                    this.mGrantResults
            );

            result.setStartDate(stepResult.getStartDate());
            result.setEndDate(stepResult.getEndDate());

            stepResult.setResult(result);
        }

        return stepResult;
    }

    private void beginRequest() {

        assert(this.permissionRequestDelegate != null);

        this.permissionRequestDelegate.requestPermissions(this.step.getPermissions(), new PermissionRequestDelegate.OnCompletion() {
            @Override
            public void onCompletion(String[] permissions, int[] grantResults) {

                if (grantResults != null) {
                    mGrantResults = grantResults.clone();
                }

            }
        });

    }
}
