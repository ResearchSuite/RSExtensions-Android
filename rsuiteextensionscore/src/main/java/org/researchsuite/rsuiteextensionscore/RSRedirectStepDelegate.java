package org.researchsuite.rsuiteextensionscore;

import android.content.Context;
import android.os.Handler;

/**
 * Created by jameskizer on 8/14/17.
 */

public interface RSRedirectStepDelegate extends RSOpenURLDelegate {

    public void beginRedirect(Context context);
    public Boolean isCompleted();
    public Throwable getError();
    public void setHandler(Handler handler);

}
