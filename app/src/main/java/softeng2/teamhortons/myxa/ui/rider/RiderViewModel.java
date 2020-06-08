package softeng2.teamhortons.myxa.ui.rider;

import android.location.Location;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.internal.PolylineEncoding;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;

import java.util.ArrayList;
import java.util.List;

import softeng2.teamhortons.myxa.data.repository.AuthRepository;
import softeng2.teamhortons.myxa.data.repository.UserRepository;
import softeng2.teamhortons.myxa.generic.ConstantVariable;

class RiderViewModel extends ViewModel {
    private Handler mHandler = new Handler();
    private Runnable mRunnable;

    private AuthRepository authRepository;
    private UserRepository userRepository;

    public RiderViewModel(AuthRepository instance, UserRepository instance1) {
        this.authRepository = authRepository;
        this.userRepository = userRepository;
    }
    boolean isLoggedIn() {
        return authRepository.isLoggedIn();
    }

    public void startUserLocationsRunnable(Location origin, GeoApiContext geoApiContext, GoogleMap googleMap){
        mHandler.postDelayed(mRunnable = new Runnable() {
            @Override
            public void run() {
                calculateRoute(origin, geoApiContext, googleMap);
                mHandler.postDelayed(mRunnable, ConstantVariable.LOCATION_UPDATE_INTERVAL);
            }
        }, ConstantVariable.LOCATION_UPDATE_INTERVAL);
    }
    public void stopLocationUpdates(){
        mHandler.removeCallbacks(mRunnable);
    }
    private void calculateRoute(/*Location destination ,*/Location origin , GeoApiContext geoApiContext, GoogleMap googleMap){
        LatLng pointB = new LatLng(14.561362, 121.019537);
        googleMap.clear();
        googleMap.addMarker(new MarkerOptions().position(pointB));
        LatLng pointA = new LatLng(origin.getLatitude(), origin.getLongitude());
        DirectionsApiRequest directions = new DirectionsApiRequest(geoApiContext);
        directions.origin(new com.google.maps.model.LatLng(pointA.latitude, pointA.longitude));
        directions.destination(new com.google.maps.model.LatLng(pointB.latitude, pointB.longitude)).setCallback(new PendingResult.Callback<DirectionsResult>() {
            @Override
            public void onResult(DirectionsResult result) {
                Log.d("SUCCESS", "onResult: routes " + result.routes[0].toString());
                Log.d("SUCCESS", "onResult: duration " + result.routes[0].legs[0].duration);
                Log.d("SUCCESS", "onResult: distance " + result.routes[0].legs[0].distance);
                Log.d("SUCCESS", "onResult: geoCodedWayPoint " + result.geocodedWaypoints[0].toString());
                addPolyLinesToMap(result, googleMap);
            }

            @Override
            public void onFailure(Throwable e) {
                Log.e("ERROR", "Failed to get Directions");
                e.printStackTrace();
            }
        });
    }
    private void addPolyLinesToMap(final DirectionsResult result, GoogleMap googleMap){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                for(DirectionsRoute route : result.routes){
                    List<com.google.maps.model.LatLng> decodedPath = PolylineEncoding.decode(route.overviewPolyline.getEncodedPath());
                    List<LatLng> newDecodedPath = new ArrayList<>();
                    for (com.google.maps.model.LatLng latLng : decodedPath){
                        newDecodedPath.add(new LatLng(latLng.lat, latLng.lng));
                    }
                    Polyline polyline = googleMap.addPolyline(new PolylineOptions().addAll(newDecodedPath));
                }
            }
        });
    }
}
