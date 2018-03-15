package org.researchsuite.rsuiteextensionscore;

import android.content.Context;

/**
 * Created by jameskizer on 3/13/18.
 */

public interface RSCredentialStore {
    byte[] get(Context context, String key);
    void set(Context context, String key, byte[] value);
    void remove(Context context, String key);
    boolean has(Context context, String key);
}
