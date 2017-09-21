package org.researchsuite.rsextensions;

import android.content.Context;
import android.util.AttributeSet;

import org.researchsuite.rsuiteextensionscore.RSRedirectStepDelegate;
import org.researchsuite.rsuiteextensionscore.RSRedirectStepLayout;

/**
 * Created by jameskizer on 8/16/17.
 */

public class RSTestRedirectStepLayout extends RSRedirectStepLayout {

    public RSTestRedirectStepLayout(Context context) {
        super(context);
    }

    public RSTestRedirectStepLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RSTestRedirectStepLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected RSRedirectStepDelegate getDelegate() {
        return RSTestDelegate.getInstance();
    }
}
