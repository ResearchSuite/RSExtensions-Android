package org.researchsuite.rsextensions.location;

import android.os.Bundle;

import org.researchsuite.rsextensions.R;


public class MainActivity extends LocationActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RSActivityManager.get().queueActivity(this, "test", true);



    }

}
