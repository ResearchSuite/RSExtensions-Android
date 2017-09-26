package org.researchsuite.rsuiteextensionscore;

import org.researchstack.backbone.result.Result;

/**
 * Created by jameskizer on 9/26/17.
 */

final public class PermissionRequestResult extends Result {

    final public String[] permissions;
    final public int[] grantResults;

    public PermissionRequestResult(String identifier, String[] permissions, int[] grantResults) {
        super(identifier);
        this.permissions = permissions;
        this.grantResults = grantResults;
    }
}
