package softeng2.teamhortons.myxa.ui.rider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.maps.GeoApiContext;

import softeng2.teamhortons.myxa.R;
import softeng2.teamhortons.myxa.generic.ConstantVariable;
import softeng2.teamhortons.myxa.services.LocationService;

public class RiderActivity extends AppCompatActivity implements OnMapReadyCallback {
    private MapView mMapView;
    private GeoApiContext mGeoApiContext;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private RiderViewModel riderViewModel;
    private GoogleMap mGoogleMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider);
        riderViewModel = new ViewModelProvider(this, new RiderViewModelFactory()).get(RiderViewModel.class);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            initGoogleMap(savedInstanceState);
        } catch (Exception e) {

        }
        findViewById(R.id.showOrder).setOnClickListener(v ->  {
            AlertDialog.Builder builder = new AlertDialog.Builder(RiderActivity.this);
            builder.setMessage("Accept Order?\n some order details")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, which) -> {
                mFusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(RiderActivity.this, location -> {
                    riderViewModel.startUserLocationsRunnable(location, mGeoApiContext, mGoogleMap);
                });
            })
            .setNegativeButton("No", (dialog, which) -> {
                dialog.dismiss();
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });
        findViewById(R.id.finishDelivery).setOnClickListener(v -> {
            riderViewModel.stopLocationUpdates();
            mGoogleMap.clear();
        });
    }
    private void startLocationService(){
        if (!isLocationServiceRunning()){
            Intent serviceIntent = new Intent(this, LocationService.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                RiderActivity.this.startForegroundService(serviceIntent);
            } else {
                startService(serviceIntent);
            }
        }
    }
    private boolean isLocationServiceRunning(){
        boolean isRunning = false;
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)){
            if ("com.example.googlemapapi.services.LocationService".equals(service.service.getClassName())){
                Log.d("LocationService", "isLocationServiceRunning: location service is running");
                isRunning = true;
            }
        }
        return isRunning;
    }
    private void initGoogleMap(Bundle savedInstanceState){
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(ConstantVariable.MAP_VIEW_BUNDLE_KEY);
        }
        mMapView = findViewById(R.id.riderMapView);
        mMapView.onCreate(mapViewBundle);

        mMapView.getMapAsync(this);
        if (mGeoApiContext == null){
            mGeoApiContext = new GeoApiContext.Builder().apiKey(getString(R.string.google_maps_api_key)).build();
        }
    }
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setMyLocationEnabled(true);
        mFusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {
            LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
           mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 14));
        });
        startLocationService();
    }
    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        riderViewModel.stopLocationUpdates();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}
