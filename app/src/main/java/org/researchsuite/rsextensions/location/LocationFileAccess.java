package org.researchsuite.rsextensions.location;

import android.content.Context;

import org.researchstack.backbone.StorageAccess;
import org.researchstack.backbone.storage.file.SimpleFileAccess;
import org.researchsuite.rstb.RSTBStateHelper;


/**
 * Created by Christina on 6/20/17.
 */

public class LocationFileAccess extends SimpleFileAccess implements RSTBStateHelper {

    private String pathName;

    public static LocationFileAccess getInstance()
    {
        return (LocationFileAccess) StorageAccess.getInstance().getFileAccess();
    }

    public LocationFileAccess(String pathName)
    {
        this.pathName = pathName;
    }

    private String pathForRSTBKey(String key) {
        StringBuilder pathBuilder = new StringBuilder(this.pathName);
        pathBuilder.append("/rstb/");
        pathBuilder.append(key);

        return pathBuilder.toString();
    }


    @Override
    public byte[] valueInState(Context context, String key) {
        if (this.dataExists(context, this.pathForRSTBKey(key))) {
            return this.readData(context, this.pathForRSTBKey(key));
        }
        else {
            return null;
        }
    }

    @Override
    public void setValueInState(Context context, String key, byte[] value) {
        this.writeData(
                context, this.pathForRSTBKey(key),
                value
        );


    }
}
