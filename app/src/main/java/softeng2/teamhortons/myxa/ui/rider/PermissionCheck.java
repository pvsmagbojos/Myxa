package softeng2.teamhortons.myxa.ui.rider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import softeng2.teamhortons.myxa.R;
import softeng2.teamhortons.myxa.generic.RequestCode;

public class PermissionCheck extends AppCompatActivity {
    private boolean mLocationPermissionGranted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_check);
        if (checkMapServices()){
            if (mLocationPermissionGranted){
                getMap();
            } else {
                getLocationPermission();
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (checkMapServices()){
            if (mLocationPermissionGranted){
                getMap();
            } else {
                getLocationPermission();
            }
        }
    }
    private boolean checkMapServices(){
        boolean isAvailable = false;
        if (isGoogleServicesAvailable()){
            if (isMapsEnabled()){
                isAvailable = true;
            }
        }
        return isAvailable;
    }

    private boolean isGoogleServicesAvailable() {
        boolean isAvailable = false;
        int service = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(PermissionCheck.this);
        if (service == ConnectionResult.SUCCESS){
            //Everything is fine and the user can make map request
            isAvailable = true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(service)) {
            //An error occurred but we can resolve it
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(PermissionCheck.this, service, RequestCode.REQUEST_DIALOG_ERROR);
            dialog.show();
        } else {
            Toast.makeText(this, "You can't make map request", Toast.LENGTH_SHORT).show();
        }
        return isAvailable;
    }

    private boolean isMapsEnabled() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean isEnabled = true;
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessagesNoGPS();
            isEnabled = false;
        }
        return isEnabled;
    }

    private void buildAlertMessagesNoGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("This application needs GPS to work properly, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent enableGPSIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(enableGPSIntent, RequestCode.REQUEST_ENABLE_GPS);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
    private void getLocationPermission(){
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, RequestCode.REQUEST_ACCESS_FINE_LOCATION);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCode.REQUEST_ENABLE_GPS) {
            if (mLocationPermissionGranted) {
                getMap();
            } else {
                getLocationPermission();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        if (requestCode == RequestCode.REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
            }
        }
    }
    public void getMap(){
        startActivity(new Intent(PermissionCheck.this, RiderActivity.class));
    }
}
