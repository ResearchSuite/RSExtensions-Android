package org.researchsuite.rsextensions.location;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import org.researchsuite.rsextensions.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends LocationActivity {

    boolean fineLocationPermissions = false;
    boolean courseLocationPermissions = false;

    final static int LOCATION_ACCESS_PERMISSION_REQUEST = 0x4567;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RSActivityManager.get().queueActivity(this, "test", true);

//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION  }, LOCATION_ACCESS_PERMISSION_REQUEST);
//            return;
//        }
//        else {
//            fineLocationPermissions = true;
//            courseLocationPermissions = true;
//            RSActivityManager.get().queueActivity(this, "test", true);
//        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

//        switch(requestCode) {
//            case LOCATION_ACCESS_PERMISSION_REQUEST: {
//                if (grantResults.length > 0) {
//                    for(int i=0; i< grantResults.length; i++) {
//                        int result = grantResults[i];
//                        if (permissions[i].equals(android.Manifest.permission.ACCESS_FINE_LOCATION)) {
//                            fineLocationPermissions = (result == PackageManager.PERMISSION_GRANTED);
//                        }
//                        else if (permissions[i].equals(Manifest.permission.ACCESS_COARSE_LOCATION)) {
//                            courseLocationPermissions = (result == PackageManager.PERMISSION_GRANTED);
//                        }
//                    }
//                }
//            }
//        }

        RSActivityManager.get().queueActivity(this, "test", true);

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
