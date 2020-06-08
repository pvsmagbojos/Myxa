package softeng2.teamhortons.myxa.services;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.firestore.GeoPoint;

import softeng2.teamhortons.myxa.generic.ConstantVariable;

public class LocationService extends Service {
    FusedLocationProviderClient mFusedLocationProviderClient;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        if (Build.VERSION.SDK_INT >= 26){
            String CHANNEL_ID = "my_channel_06";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "My_Channel", NotificationManager.IMPORTANCE_DEFAULT);

            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID).setContentTitle("").setContentText("").build();
            startForeground(1, notification);
        }
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Location Service", "onStartCommand: called.");
        getLocation();
        return START_NOT_STICKY;
    }
    private void getLocation() {
        LocationRequest mLocationRequestHighAccuracy = new LocationRequest();
        mLocationRequestHighAccuracy.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequestHighAccuracy.setInterval(ConstantVariable.UPDATE_INTERVAL);
        mLocationRequestHighAccuracy.setFastestInterval(ConstantVariable.FASTEST_INTERVAL);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            stopSelf();
            return;
        }
        mFusedLocationProviderClient.requestLocationUpdates(mLocationRequestHighAccuracy, new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Location location = locationResult.getLastLocation();
                if (location != null){
                    //add to db
                }
            }
        }, Looper.myLooper());
    }
}
