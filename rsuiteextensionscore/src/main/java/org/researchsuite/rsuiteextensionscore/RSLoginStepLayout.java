package org.researchsuite.rsuiteextensionscore;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;

import org.researchstack.backbone.result.StepResult;
import org.researchstack.backbone.step.Step;
import org.researchstack.backbone.ui.callbacks.StepCallbacks;
import org.researchstack.backbone.ui.step.layout.StepLayout;
import org.researchstack.backbone.ui.views.SubmitBar;
import org.researchstack.backbone.utils.TextUtils;
import org.researchstack.skin.ui.adapter.TextWatcherAdapter;

/**
 * Created by jameskizer on 3/13/18.
 */

public class RSLoginStepLayout extends RelativeLayout implements StepLayout {

    public interface ActionCompletion {
        void onCompletion(boolean moveForward);
    }

    private View progress;

    private boolean isLoading = false;

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {

        boolean hasChanged = isLoading != loading;

        isLoading = loading;

        if (hasChanged) {
            final boolean localIsLoading = isLoading;
            final Activity activity = (Activity)this.context;

            activity.runOnUiThread(new Runnable() {
                public void run() {
                    if (localIsLoading) {
                        progress.animate().alpha(1).withStartAction(() -> {
                            progress.setVisibility(View.VISIBLE);
                            progress.setAlpha(0);
                        });
                    }
                    else {
                        progress.animate().alpha(0).withEndAction(() -> progress.setVisibility(View.GONE));
                    }
                }
            });
        }


    }

    protected AppCompatEditText identityField;
    protected AppCompatEditText  passwordField;
    private TextView forgotPassword;

    public RSLoginStep getStep() {
        return step;
    }

    private RSLoginStep step;

    private StepResult<Boolean> result;
    private StepCallbacks callbacks;

    protected Context context;

    protected void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    private boolean loggedIn = false;

    public RSLoginStepLayout(Context context)
    {
        super(context);
        this.context = context;
    }

    public RSLoginStepLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.context = context;
    }

    public RSLoginStepLayout(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @Override
    public void initialize(Step step, StepResult result) {

        this.step = (RSLoginStep) step;
        this.result = result == null ? new StepResult<>(step) : result;

        View layout = LayoutInflater.from(getContext()).inflate(R.layout.login_layout, this, true);

        //set title and text
        TextView titleView = (TextView) layout.findViewById(R.id.title);
        titleView.setText(this.step.getTitle());
        if(this.step.getText() != null) {
            TextView textView = (TextView) layout.findViewById(R.id.text);
            textView.setText(this.step.getText());
            textView.setVisibility(VISIBLE);
        }

        progress = layout.findViewById(R.id.progress);

        TextView identityLabel = (TextView) layout.findViewById(R.id.identityLabel);
        identityField = (AppCompatEditText) layout.findViewById(R.id.username);

        if (this.step.getShowIdentityField()) {


            identityLabel.setText(this.step.getIdentityFieldName());

            identityField.addTextChangedListener(new TextWatcherAdapter()
            {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {
                    if(! TextUtils.isEmpty(identityField.getError()))
                    {
                        identityField.setError(null);
                    }
                }
            });
            identityField.setHint(this.step.getIdentityFieldName());

            identityLabel.setVisibility(VISIBLE);
            identityField.setVisibility(VISIBLE);
        }
        else {
            identityLabel.setVisibility(GONE);
            identityField.setVisibility(GONE);
        }

        TextView passwordLabel = (TextView) layout.findViewById(R.id.passwordLabel);
        passwordField = (AppCompatEditText) layout.findViewById(R.id.password);

        if (this.step.getShowPasswordField()) {
            passwordField.addTextChangedListener(new TextWatcherAdapter()
            {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {
                    if(! TextUtils.isEmpty(passwordField.getError()))
                    {
                        passwordField.setError(null);
                    }
                }
            });
            passwordField.setOnEditorActionListener((v, actionId, event) -> {
                if((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) ||
                        (actionId == EditorInfo.IME_ACTION_DONE))
                {
                    return true;
                }
                return false;
            });

            passwordField.setHint(this.step.getPasswordFieldName());
            passwordLabel.setText(this.step.getPasswordFieldName());

            passwordField.setVisibility(VISIBLE);
            passwordLabel.setVisibility(VISIBLE);
        }
        else {
            passwordField.setVisibility(GONE);
            passwordLabel.setVisibility(GONE);
        }


        forgotPassword = (TextView) layout.findViewById(R.id.forgot_password);
        if (this.step.getForgotPasswordButtonTitle() != null && !this.step.getForgotPasswordButtonTitle().isEmpty()) {

            forgotPassword.setText(this.step.getForgotPasswordButtonTitle());
            RxView.clicks(forgotPassword).subscribe(v -> {

                loggedIn = false;
                forgotPasswordButtonAction(identityField.getText().toString(), new ActionCompletion() {
                    @Override
                    public void onCompletion(boolean moveForward) {
                        if (moveForward) {
                            callbacks.onSaveStep(StepCallbacks.ACTION_NEXT, step, getResult());
                        }
                    }
                });

//                if(! isEmailValid())
//                {
//                    Toast.makeText(getContext(), org.researchstack.skin.R.string.rss_error_invalid_email, Toast.LENGTH_SHORT)
//                            .show();
//                    return;
//                }
//
//                DataProvider.getInstance()
//                        .forgotPassword(getContext(), email.getText().toString())
//                        .compose(ObservableUtils.applyDefault())
//                        .subscribe(dataResponse -> {
//                            Toast.makeText(getContext(), dataResponse.getMessage(), Toast.LENGTH_SHORT)
//                                    .show();
//                        });
            });


        }
        else {
            forgotPassword.setVisibility(GONE);
        }

        SubmitBar submitBar = (SubmitBar) findViewById(R.id.submit_bar);
        submitBar.setPositiveAction(v -> handleLogInTap());
        submitBar.setPositiveTitle(this.step.getLoginButtonTitle());
        submitBar.getNegativeActionView().setVisibility(GONE);

    }

    public void handleLogInTap() {

        if (this.step.getShowIdentityField() && this.identityField.getText().toString().equals("")) {
            return;
        }

        if (this.step.getShowPasswordField() && this.passwordField.getText().toString().equals("")) {
            return;
        }

        this.loginButtonAction(this.identityField.getText().toString(), this.passwordField.getText().toString(), new ActionCompletion() {
            @Override
            public void onCompletion(boolean moveForward) {

                if (moveForward) {
                    callbacks.onSaveStep(StepCallbacks.ACTION_NEXT, step, getResult());
                }
            }
        });

    }

    public StepResult getResult() {
        result.setResult(new Boolean(this.loggedIn));
        return result;
    }

    @Override
    public View getLayout() {
        return this;
    }

    @Override
    public boolean isBackEventConsumed() {
        callbacks.onSaveStep(StepCallbacks.ACTION_PREV, step, this.getResult());
        return false;
    }

    @Override
    public void setCallbacks(StepCallbacks callbacks)
    {
        this.callbacks = callbacks;
    }

    protected void loginButtonAction(@Nullable String identity, @Nullable String password, final ActionCompletion completion) {

        final Activity activity = (Activity)this.context;
        activity.runOnUiThread(new Runnable() {
            public void run() {
                completion.onCompletion(true);
            }
        });

    }

    protected void forgotPasswordButtonAction(@Nullable String identity, final ActionCompletion completion) {

        final Activity activity = (Activity)this.context;
        activity.runOnUiThread(new Runnable() {
            public void run() {
                completion.onCompletion(false);
            }
        });

    }
}
