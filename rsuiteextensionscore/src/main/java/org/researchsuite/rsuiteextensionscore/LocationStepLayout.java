package org.researchsuite.rsuiteextensionscore;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.researchstack.backbone.result.StepResult;
import org.researchstack.backbone.step.Step;
import org.researchstack.backbone.ui.callbacks.StepCallbacks;
import org.researchstack.backbone.ui.step.layout.StepLayout;
import org.researchstack.backbone.ui.views.SubmitBar;

import rx.functions.Action1;

/**
 * Created by Christina on 6/21/17.
 */

public class LocationStepLayout extends FrameLayout implements StepLayout, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private LocationStep step;
    private StepResult stepResult;
    private TextView titleText;
    private TextView questionDetail;
    private EditText locationField;
    private GoogleMap mGoogleMap;
    private StepCallbacks callbacks;
    private MapView mMapView;
    private GoogleApiClient mGoogleApiClient;
    private FusedLocationProviderClient mFusedLocationClient;
    private String userInput;
    private boolean userPlaced;
    private String formattedAddress;
    private Marker mMarker;

    public LocationStepLayout(@NonNull Context context) {
        super(context);
    }

    public LocationStepLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public LocationStepLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public String getGetGoogleMapsKey() {
        return getContext().getString(R.string.google_maps_key);
    }


    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        mMapView.onPause();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }

    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        mMapView.onResume();
        mGoogleApiClient.connect();
    }


    @Override
    public void initialize(Step step, StepResult result) {

        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this.getContext());

        this.step = (LocationStep) step;

        this.stepResult = result == null ? new StepResult<>(step) : result;
        this.initializeStep(this.step, this.stepResult);

    }

    public void initializeStep(LocationStep step, StepResult result) {
        this.initStepLayout(step);
    }

    @Override
    public View getLayout() {

        return this;

    }

    private void initStepLayout(LocationStep step) {


        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.location_layout, this, true);

        mMapView = (MapView) findViewById(R.id.myMap);
        mMapView.onCreate(null);

        mMapView.getMapAsync(this);

        titleText = (TextView) findViewById(R.id.question_title);
        titleText.setText(step.getTitle());

        questionDetail = (TextView) findViewById(R.id.question_detail);
        questionDetail.setText(step.getText());

        SubmitBar submitBar = (SubmitBar) findViewById(R.id.rsb_submit_bar);
        submitBar.setPositiveAction(new Action1() {
            @Override
            public void call(Object o) {
                onNextClicked();
            }
        });

        if (step.isOptional()) {
            submitBar.setNegativeTitle(R.string.rsb_step_skip);
            submitBar.setNegativeAction(new Action1() {
                @Override
                public void call(Object o) {
                    onSkipClicked();
                }
            });
        }
        else {
            submitBar.getNegativeActionView().setVisibility(View.GONE);
        }

        locationField = (EditText) findViewById(R.id.location_result);
        locationField.setSingleLine(true);
        locationField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_DONE){
                    userInput = locationField.getText().toString();
                    if (userInput != "") {
                        handleNewAddressInput(userInput);
                        Log.d("geocode called","called");
                    }
                }
                return false;
            }
        });
    }

    @Override
    public boolean isBackEventConsumed() {
        //this will cancel the event
        callbacks.onSaveStep(StepCallbacks.ACTION_PREV,this.step,this.stepResult);
        return false;
    }

    @Override
    public void setCallbacks(StepCallbacks callbacks) {
        this.callbacks = callbacks;

    }

    public Step getStep(){
        return this.step;
    }

    @Override
    public Parcelable onSaveInstanceState()
    {
        //don't clear  on rotation
        callbacks.onSaveStep(StepCallbacks.ACTION_NONE, getStep(), this.getStepResult(false));
        return super.onSaveInstanceState();
    }

    protected void onNextClicked()
    {
        callbacks.onSaveStep(StepCallbacks.ACTION_NEXT,
                this.getStep(),
                this.getStepResult(false));

    }

    public void onSkipClicked()
    {
        if(callbacks != null)
        {
            // empty step result when skipped
            callbacks.onSaveStep(StepCallbacks.ACTION_NEXT,
                    this.getStep(),
                    this.getStepResult(true));
        }
    }


    public StepResult getStepResult(boolean shouldClear) {

        if (shouldClear) {
            stepResult.setResult(null);
        }
        else {
            LocationResult result = new LocationResult(step.getIdentifier());
            result.setStartDate(stepResult.getStartDate());
            result.setEndDate(stepResult.getEndDate());
            result.setLatitude(this.mMarker.getPosition().latitude);
            result.setLongitute(this.mMarker.getPosition().longitude);
            result.setUserPlaced(this.userPlaced);
            result.setUserInput(userInput);
            result.setAddress(formattedAddress);
            stepResult.setResult(result);
        }

        return stepResult;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        mGoogleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
                Log.d("onMarkerDragStart: ", marker.getPosition().toString());
            }

            @Override
            public void onMarkerDrag(Marker marker) {
                Log.d("onMarkerDrag: ", marker.getPosition().toString());
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                Log.d("onMarkerDragEnd: ", marker.getPosition().toString());
                userPlaced = true;
                handleNewLocation(marker.getPosition(), false, false);
            }
        });

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {


        if (stepResult.getResult() != null && stepResult.getResult() instanceof LocationResult ) {

            this.setUpFromResult((LocationResult) stepResult.getResult());

        }

        else {

            //chec for required permissions
            //add toast if not available!!
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // handled in main

                CharSequence text = "Location access not permitted";
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(getContext(), text, duration);
                toast.show();

                LocationAnswerFormat answerFormat = (LocationAnswerFormat) step.getAnswerFormat();
                LatLng position = new LatLng(answerFormat.getDefaultLocation().getLatitude(), answerFormat.getDefaultLocation().getLongitude());

                Log.d("position: ",String.valueOf(position));
                mMarker = mGoogleMap.addMarker(new MarkerOptions().position(position).title("Location").draggable(true));
                handleNewLocation(position, true, true);
            }
            else {
                this.mFusedLocationClient.getLastLocation()
                        .addOnSuccessListener(new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {

                                LatLng position;

                                if (location != null) {
                                    position = new LatLng(location.getLatitude(), location.getLongitude());
                                }
                                else {
                                    LocationAnswerFormat answerFormat = (LocationAnswerFormat) step.getAnswerFormat();
                                    position = new LatLng(answerFormat.getDefaultLocation().getLatitude(), answerFormat.getDefaultLocation().getLongitude());
                                }

                                Log.d("last location: ",String.valueOf(location));
                                mMarker = mGoogleMap.addMarker(new MarkerOptions().position(position).title("Location").draggable(true));
                                handleNewLocation(position, true, true);

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                e.printStackTrace();
                            }
                        });
            }


        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void handleNewAddressInput(String userSubmittedAddress) {

        GeocodeAsyncTask.getAddressFromUserInput(getGetGoogleMapsKey(),
                userSubmittedAddress,
                new GeocodeAsyncTask.GeocodeCompletion() {
                    @Override
                    public void onCompletion(final GeocodeLocationResponse response, Exception e) {
                        if (e != null) {
                            e.printStackTrace();
                        }
                        else {
                            Log.d("response: ", response.formattedAddress);

                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    // this will run in the main thread
                                    updateLocation(response, true, true);
                                }
                            });


                        }
                    }
                }
        );
    }

    private void handleNewLocation(LatLng position, final boolean moveCamera, final boolean updateInternalLocation) {

        GeocodeAsyncTask.getAddressFromLatLon(getGetGoogleMapsKey(),
                position.latitude,
                position.longitude,
                new GeocodeAsyncTask.GeocodeCompletion() {
                    @Override
                    public void onCompletion(final GeocodeLocationResponse response, Exception e) {
                        if (e != null) {
                            e.printStackTrace();
                        }
                        else {
                            Log.d("response: ", response.formattedAddress);

                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    // this will run in the main thread
                                    updateLocation(response, moveCamera, updateInternalLocation);
                                }
                            });


                        }
                    }
                }
        );
    }

    private void setUpFromResult(LocationResult locationResult) {

        LatLng position = new LatLng(locationResult.getLatitude(), locationResult.getLongitute());

        Log.d("Setting location to ",String.valueOf(position));
        mMarker = mGoogleMap.addMarker(new MarkerOptions().position(position).title("Location").draggable(true));
        this.formattedAddress = locationResult.getAddress();
        this.userInput = locationResult.getUserInput();

        locationField.setText(this.userInput);

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(this.mMarker.getPosition())
                .zoom(17)
                .build();
        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private void updateLocation(GeocodeLocationResponse locationResponse, boolean moveCamera, boolean updateInternalLocation) {

        this.formattedAddress = locationResponse.formattedAddress;

        if (updateInternalLocation) {
            LatLng newLocation = new LatLng(locationResponse.latitude, locationResponse.longitude);
            this.mMarker.setPosition(newLocation);
        }

        if (moveCamera) {
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(this.mMarker.getPosition())
                    .zoom(17)
                    .build();
            mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }

}



