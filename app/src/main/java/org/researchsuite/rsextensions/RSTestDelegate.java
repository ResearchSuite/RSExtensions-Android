package org.researchsuite.rsextensions;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.Toast;

import org.researchstack.backbone.utils.LogExt;
import org.researchsuite.rsuiteextensionscore.RSRedirectStepDelegate;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jameskizer on 8/14/17.
 */

public class RSTestDelegate implements RSRedirectStepDelegate {

    private Pattern urlPattern;
    private Boolean completed;
    private Throwable error;

    private static RSTestDelegate delegate = null;
    private static Object delegateLock = new Object();

    @Nullable
    public static RSTestDelegate getInstance() {
        synchronized (delegateLock) {
            return delegate;
        }
    }

    public static void config(String urlScheme) {
        synchronized (delegateLock) {
            if (delegate == null) {
                delegate = new RSTestDelegate(urlScheme);
            }
        }
    }

    public RSTestDelegate(String urlScheme) {

        StringBuilder patternStringBuilder = new StringBuilder("^");
        patternStringBuilder.append(urlScheme);
        patternStringBuilder.append("://auth/ancile/callback.*");

        Pattern urlPattern = Pattern.compile(patternStringBuilder.toString());
        this.urlPattern = urlPattern;
        this.completed = false;
        this.error = null;
    }

    public void openWebPage(Context context, String url) {
        try {

            LogExt.d(getClass(), "Beginning redirect");

            Uri webpage = Uri.parse(url);
            Intent myIntent = new Intent(Intent.ACTION_VIEW, webpage);
            context.startActivity(myIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "No application can handle this request. Please install a web browser or check your URL.",  Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    public Boolean handleURL(Uri uri) {
        Matcher m = urlPattern.matcher(uri.toString());
        if (m.matches()) {
            String code = uri.getQueryParameter("code");
            if(code != null) {
                this.completed = true;
                this.error = null;
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    @Override
    public void beginRedirect(Context context) {

        String url = "https://ancile.cornelltech.io/accounts/google/login";
        this.openWebPage(context, url);

    }

    @Override
    public Boolean isCompleted() {
        return this.completed;
    }

    @Override
    public Throwable getError() {
        return this.error;
    }

    @Override
    public void setHandler(Handler handler) {

    }
}
