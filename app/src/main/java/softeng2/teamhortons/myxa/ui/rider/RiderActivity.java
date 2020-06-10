package softeng2.teamhortons.myxa.ui.rider;

import androidx.annotation.NonNull;
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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.maps.GeoApiContext;

import java.util.ArrayList;
import java.util.List;

import softeng2.teamhortons.myxa.R;
import softeng2.teamhortons.myxa.generic.ConstantVariable;
import softeng2.teamhortons.myxa.services.LocationService;
import softeng2.teamhortons.myxa.ui.customer_login.LoginActivity;

import static softeng2.teamhortons.myxa.generic.RequestCode.REQUEST_LOGIN;

public class RiderActivity extends AppCompatActivity implements OnMapReadyCallback {
    private MapView mMapView;
    private GeoApiContext mGeoApiContext;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private RiderViewModel riderViewModel;
    private GoogleMap mGoogleMap;

    AlertDialog dialog;
    String customerName;
    String totalPrice;
    ArrayList<String> recipeList;
    final ArrayList<Task<Object>> firebaseTasks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider);

        final Switch switchAcceptingOrders = findViewById(R.id.switch_acceptingOrders);
        final Button finishDelivery = findViewById(R.id.finishDelivery);

        customerName = "";
        totalPrice = "";
        recipeList = new ArrayList<>();

        riderViewModel = new ViewModelProvider(this, new RiderViewModelFactory()).get(RiderViewModel.class);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            initGoogleMap(savedInstanceState);
        } catch (Exception e) {

        }

        switchAcceptingOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("RiderActivity", FirebaseAuth.getInstance().getUid());

                DocumentReference riderDocPath = FirebaseFirestore.getInstance().collection("riders")
                        .document(FirebaseAuth.getInstance().getUid());

                Log.d("RiderActivity", riderDocPath.getPath());



                FirebaseFirestore.getInstance().collection("orders")
                        .whereEqualTo("dateCompleted", null)
                        .whereEqualTo("riderRef", riderDocPath)
                        .limit(1)
                        .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Log.d("RiderActivity", "# of Documents: " + String.valueOf(queryDocumentSnapshots.getDocuments().size()));
                        final DocumentReference orderRef = queryDocumentSnapshots.getDocuments().get(0).getReference();


                        for(DocumentSnapshot docSnap : queryDocumentSnapshots.getDocuments()) {
                            totalPrice = docSnap.get("totalPrice").toString();

                            DocumentReference userDocRef = (DocumentReference) docSnap.get("userRef");
                            Task customerNameTask = userDocRef
                                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            String customer_name = documentSnapshot.getString("firstName") + " " + documentSnapshot.getString("lastName");
                                            customerName = customer_name;
                                            Log.d("RiderActivity", customer_name);
                                        }
                                    });

                            Task recipeCartTask = docSnap.getReference().collection("cart")
                                    .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    recipeList.clear();
                                    for(DocumentSnapshot docSnap2 : queryDocumentSnapshots.getDocuments()) {
                                        String qty = docSnap2.getString("qty");

                                        DocumentReference recipe = (DocumentReference) docSnap2.get("recipeRef");
                                        Log.d("RiderActivity", recipe.getPath());
                                        recipeList.add(qty + "x " + recipe.getPath());
                                    }
                                }
                            });

                            firebaseTasks.add(customerNameTask);
                            firebaseTasks.add(recipeCartTask);
                        }

                        Tasks.whenAllComplete(firebaseTasks).addOnCompleteListener(new OnCompleteListener<List<Task<?>>>() {
                            @Override
                            public void onComplete(@NonNull Task<List<Task<?>>> task) {
                                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(RiderActivity.this);

                                LayoutInflater inflater = getLayoutInflater();

                                View dialogView = inflater.inflate(R.layout.dialog_order_request, null);
                                TextView textViewName = dialogView.findViewById(R.id.textView_customerName);
                                TextView textViewOrderPrice = dialogView.findViewById(R.id.textView_orderPrice);
                                TextView textViewRecipes = dialogView.findViewById(R.id.textView_recipeList);


                                textViewName.setText(customerName);
                                textViewOrderPrice.setText("PHP" + totalPrice);
                                for(String rec : recipeList) {
                                    textViewRecipes.setText(textViewRecipes.getText() + rec + "\n");
                                }

                                dialogBuilder.setView(dialogView);

                                dialog = dialogBuilder.create();
                                dialog.show();

                                Button buttonAccept = (Button) ((AlertDialog) dialog).findViewById(R.id.order_accept);
                                Button buttonDecline = (Button) ((AlertDialog) dialog).findViewById(R.id.order_decline);

                                buttonAccept.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mFusedLocationProviderClient.getLastLocation()
                                                .addOnSuccessListener(RiderActivity.this, location -> {
                                                    riderViewModel.startUserLocationsRunnable(location, mGeoApiContext, mGoogleMap);
                                                });
                                        dialog.dismiss();
                                        finishDelivery.setVisibility(View.VISIBLE);
                                        finishDelivery.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                orderRef.update("dateCompleted", FieldValue.serverTimestamp());
                                                riderViewModel.stopLocationUpdates();
                                                mGoogleMap.clear();
                                                switchAcceptingOrders.setChecked(false);
                                                finishDelivery.setVisibility(View.GONE);
                                            }
                                        });
                                    }
                                });

                                buttonDecline.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        switchAcceptingOrders.setChecked(false);
                                        dialog.dismiss();
                                    }
                                });
                            }
                        });
                    }
                });

//                AlertDialog.Builder builder = new AlertDialog.Builder(RiderActivity.this);
//                builder.setMessage("Accept Order?\n some order details")
//                        .setCancelable(false)
//                        .setPositiveButton("Yes", (dialog, which) -> {
//                            mFusedLocationProviderClient.getLastLocation()
//                                    .addOnSuccessListener(RiderActivity.this, location -> {
//                                        riderViewModel.startUserLocationsRunnable(location, mGeoApiContext, mGoogleMap);
//                                    });
//                        })
//                        .setNegativeButton("No", (dialog, which) -> {
//                            dialog.dismiss();
//                        });
//                AlertDialog dialog = builder.create();
//                dialog.show();
            }
        });

//        findViewById(R.id.showOrder).setOnClickListener(v ->  {
//            AlertDialog.Builder builder = new AlertDialog.Builder(RiderActivity.this);
//            builder.setMessage("Accept Order?\n some order details")
//                .setCancelable(false)
//                .setPositiveButton("Yes", (dialog, which) -> {
//                mFusedLocationProviderClient.getLastLocation()
//                    .addOnSuccessListener(RiderActivity.this, location -> {
//                    riderViewModel.startUserLocationsRunnable(location, mGeoApiContext, mGoogleMap);
//                });
//            })
//            .setNegativeButton("No", (dialog, which) -> {
//                dialog.dismiss();
//            });
//            AlertDialog dialog = builder.create();
//            dialog.show();
//        });


//        findViewById(R.id.finishDelivery).setOnClickListener(v -> {
//            riderViewModel.stopLocationUpdates();
//            mGoogleMap.clear();
//            switchAcceptingOrders.setChecked(false);
//        });
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FirebaseAuth.getInstance().signOut();
        //startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        startActivityForResult(
                new Intent(getApplicationContext(), LoginActivity.class),
                REQUEST_LOGIN);
        finish();
    }
}
