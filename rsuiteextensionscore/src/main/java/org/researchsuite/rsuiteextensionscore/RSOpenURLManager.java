package org.researchsuite.rsuiteextensionscore;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jameskizer on 8/14/17.
 */

public class RSOpenURLManager {

    private List<RSOpenURLDelegate> openURLDelegates;

    private static RSOpenURLManager manager = null;
    private static Object managerLock = new Object();

    @Nullable
    public static RSOpenURLManager getInstance() {
        synchronized (managerLock) {
            return manager;
        }
    }

    public static void config(List<RSOpenURLDelegate> openURLDelegates) {
        synchronized (managerLock) {
            if (manager == null) {
                manager = new RSOpenURLManager(openURLDelegates);
            }
        }
    }

    public RSOpenURLManager() {
        this.openURLDelegates = new ArrayList<>();
    }

    public RSOpenURLManager(List<RSOpenURLDelegate> openURLDelegates) {
        assert(openURLDelegates != null);

        this.openURLDelegates = openURLDelegates;
    }

    public Boolean handleURL(Uri url) {
        for(RSOpenURLDelegate delegate: this.openURLDelegates) {
            Boolean handled = delegate.handleURL(url);
            if(handled) {
                return true;
            }
        }
        return false;
    }
}
