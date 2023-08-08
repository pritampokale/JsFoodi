package bugfix.itsolutions.jsfoodi.ui.location;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.maps.MapView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import bugfix.itsolutions.jsfoodi.MainActivity;
import bugfix.itsolutions.jsfoodi.R;
import bugfix.itsolutions.jsfoodi.ui.auth.AddInfoActivity;
import bugfix.itsolutions.jsfoodi.ui.cart.CartItemActivity;

/* loaded from: classes4.dex */
public class ChangeLocationActivity extends AppCompatActivity implements OnMapReadyCallback, PermissionsListener {
    private static final String ICON_LAYER_ID = "icon-layer-id";
    private static final String ICON_SOURCE_ID = "icon-source-id";
    private static final String RED_PIN_ICON_ID = "red-pin-icon-id";
    private static final String ROUTE_LAYER_ID = "route-layer-id";
    private static final String ROUTE_SOURCE_ID = "route-source-id";
    private List<Address> addresses;
    private String city;
    private FirebaseFirestore db;
    private Geocoder geocoder;
    private String knownName;
    private LatLng latLng;
    double latitude;
    private LocationEngine locationEngine;
    private EditText locationPincode;
    double longitude;
    private String mIntentKey;
    private EditText mLocationText;
    private GoogleMap mMap;
    private ProgressDialog mProgressDialog;
    private Button mSaveLocationBtn;
    SupportMapFragment mapFragment;
    private MapView mapView;
    private Marker marker;
    GoogleMap myMap;
    private PermissionsManager permissionsManager;
    private String postalCode;
    SharedPreferences pref;
    SearchView searchView;
    private String subLocality;
    private String uid;
    private long DEFAULT_INTERVAL_IN_MILLISECONDS = 8000;
    private long DEFAULT_MAX_WAIT_TIME = 8000 * 5;
    HashMap<String, String> updateLocMap = new HashMap<>();
    String ServerUploadPath1 = "https://jsfoodi.com/Jsfoodi_App/JsFoodi/updateLocation.php";

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_change_location);
        this.pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        init();
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps);
        this.mapFragment = supportMapFragment;
        supportMapFragment.getMapAsync(this);
        SearchView searchView = (SearchView) findViewById(R.id.editTextTextPersonName);
        this.searchView = searchView;


        findViewById(R.id.saveLocationBtn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLocationPermission();
                getLocation();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() { // from class: ui.location.ChangeLocationActivity.1
            @Override // android.widget.SearchView.OnQueryTextListener
            public boolean onQueryTextChange(String str) {
                return false;
            }

            @Override // android.widget.SearchView.OnQueryTextListener
            public boolean onQueryTextSubmit(String str) {
                String charSequence = ChangeLocationActivity.this.searchView.getQuery().toString();
                if (charSequence != null) {
                    try {
                        Address address = new Geocoder(ChangeLocationActivity.this).getFromLocationName(charSequence, 1).get(0);
                        ChangeLocationActivity.this.myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(address.getLatitude(), address.getLongitude()), 15.0f));
                    } catch (IOException | IndexOutOfBoundsException e) {
                        e.printStackTrace();
                        Toast.makeText(ChangeLocationActivity.this, "This City Not Find", 0).show();
                    }
                }
                return false;
            }
        });
        this.mapFragment.getMapAsync(this);


    }

    private void init() {
        this.mIntentKey = getIntent().getStringExtra("INT");
        this.db = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        Objects.requireNonNull(currentUser);
        this.uid = currentUser.getUid();
        this.mProgressDialog = new ProgressDialog(this);
        this.mLocationText = (EditText) findViewById(R.id.locationActualText);
        this.locationPincode = (EditText) findViewById(R.id.locationPincode);
        this.geocoder = new Geocoder(this, Locale.getDefault());
        this.mSaveLocationBtn = (Button) findViewById(R.id.saveLocationBtn);
        this.mSaveLocationBtn.setOnClickListener(new View.OnClickListener() { // from class: ui.location.ChangeLocationActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ChangeLocationActivity.this.m1959lambda$init$0$uilocationChangeLocationActivity(view);
            }
        });
    }

    /* renamed from: lambda$init$0$ui-location-ChangeLocationActivity  reason: not valid java name */
    public /* synthetic */ void m1959lambda$init$0$uilocationChangeLocationActivity(View view) {
        this.mProgressDialog.setMessage("Updating Address, Please wait...");
        this.mProgressDialog.show();
        setLocation(this.latitude, this.longitude);
        this.postalCode = this.locationPincode.getText().toString();
        updateLocation();
        this.mProgressDialog.dismiss();
        String str = this.mIntentKey;
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case 78406:
                if (str.equals("ONE")) {
                    c = 0;
                    break;
                }
                break;
            case 83500:
                if (str.equals("TWO")) {
                    c = 1;
                    break;
                }
                break;
            case 79801726:
                if (str.equals("THREE")) {
                    c = 2;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                return;
            case 1:
                startActivity(new Intent(getApplicationContext(), CartItemActivity.class));
                finish();
                return;
            case 2:
                onBackPressed();
                finish();
                return;
            default:
                return;
        }
    }


    @Override // com.mapbox.android.core.permissions.PermissionsListener
    public void onExplanationNeeded(List<String> list) {
        Toast.makeText(this, "Please Give Location Permission", 1).show();
    }

    @Override // com.mapbox.android.core.permissions.PermissionsListener
    public void onPermissionResult(boolean z) {
        if (z) {
            return;
        }
        Toast.makeText(this, "Location Permission Accessed", 1).show();
        finish();
    }

    @Override // com.google.android.gms.maps.OnMapReadyCallback
    public void onMapReady(final GoogleMap googleMap) {

        this.myMap = googleMap;

        Double valueOf;
        Double valueOf2;
        this.myMap = googleMap;
        try {
            valueOf = Double.valueOf(this.pref.getString("latitude", ""));
            valueOf2 = Double.valueOf(this.pref.getString("longitude", ""));

            LatLng latLng = new LatLng(valueOf.doubleValue(), valueOf2.doubleValue());
            googleMap.addMarker(new MarkerOptions().position(latLng).title("Current Location"));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18.0f));

        } catch (NumberFormatException unused) {
            valueOf = Double.valueOf(19.7515);
            valueOf2 = Double.valueOf(75.7139);
            LatLng latLng = new LatLng(valueOf.doubleValue(), valueOf2.doubleValue());
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 7.0f));

        }


        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() { // from class: ui.location.ChangeLocationActivity.2
            @Override // com.google.android.gms.maps.GoogleMap.OnMapClickListener
            public void onMapClick(LatLng latLng2) {
                ChangeLocationActivity.this.latLng = latLng2;
                List<Address> arrayList = new ArrayList<>();
                try {
                    arrayList = ChangeLocationActivity.this.geocoder.getFromLocation(latLng2.latitude, latLng2.longitude, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Address address = arrayList.get(0);
                ChangeLocationActivity.this.latitude = address.getLatitude();
                ChangeLocationActivity.this.longitude = address.getLongitude();
                ChangeLocationActivity.this.mLocationText.setText(address.getAddressLine(0));
                ChangeLocationActivity.this.city = address.getLocality();
                ChangeLocationActivity.this.locationPincode.setText(address.getPostalCode());
                if (address != null) {
                    for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                        address.getAddressLine(i);
                    }
                }
                if (ChangeLocationActivity.this.marker != null) {
                    ChangeLocationActivity.this.marker.remove();
                }
                ChangeLocationActivity.this.marker = googleMap.addMarker(new MarkerOptions().position(latLng2).title("Updated Location").icon(BitmapDescriptorFactory.defaultMarker(30.0f)));
            }
        });
    }

    private void setLocation(double d, double d2) {
        try {
            this.addresses = this.geocoder.getFromLocation(d, d2, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Address> list = this.addresses;
        if (list == null || list.size() <= 0) {
            return;
        }
        this.postalCode = this.addresses.get(0).getPostalCode();
        this.knownName = this.addresses.get(0).getFeatureName();
        this.subLocality = this.addresses.get(0).getSubLocality();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onLowMemory() {
        super.onLowMemory();
    }

    public void getLocation() {

        LocationManager locationManager = (LocationManager) getSystemService(FirebaseAnalytics.Param.LOCATION);
        MyLocationListener myLocationListener = new MyLocationListener(this);
        if (ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0 || ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") == 0) {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0.0f, myLocationListener);


            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {


                this.latitude = MyLocationListener.latitude;
                this.longitude = MyLocationListener.longitude;
                if (this.latitude == 0.0) {
                    Toast.makeText(getApplicationContext(), "Currently gps has not found your location....", 1).show();

                    return;
                }else{
                    LatLng latLng = new LatLng(latitude, longitude);

                    ChangeLocationActivity.this.latLng = latLng;
                    List<Address> arrayList = new ArrayList<>();
                    try {
                        arrayList = ChangeLocationActivity.this.geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address address = arrayList.get(0);
                    ChangeLocationActivity.this.latitude = address.getLatitude();
                    ChangeLocationActivity.this.longitude = address.getLongitude();
                    ChangeLocationActivity.this.mLocationText.setText(address.getAddressLine(0));
                    ChangeLocationActivity.this.city = address.getLocality();
                    ChangeLocationActivity.this.locationPincode.setText(address.getPostalCode());

                    if (address != null) {
                        for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                            address.getAddressLine(i);
                        }
                    }

                    if(myMap != null) {
                        myMap.addMarker(new MarkerOptions().position(latLng).title("Current Location"));
                        myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18.0f));
                    }

                }
                return;
            }
            Toast.makeText(getApplicationContext(), "GPS is currently off...", 1).show();
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [ui.location.ChangeLocationActivity$1AsyncTaskUploadClass] */
    public void updateLocation() {
        new AsyncTask<Void, Void, String>() { // from class: ui.location.ChangeLocationActivity.1AsyncTaskUploadClass
            @Override // android.os.AsyncTask
            protected void onPreExecute() {
                super.onPreExecute();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public void onPostExecute(String str) {
                super.onPostExecute( str);
                str.equals("Added Succesfull");
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public String doInBackground(Void... voidArr) {
                AddInfoActivity.ImageProcessClass imageProcessClass = new AddInfoActivity.ImageProcessClass();
                ChangeLocationActivity.this.updateLocMap.put("address", ChangeLocationActivity.this.mLocationText.getText().toString());
                ChangeLocationActivity.this.updateLocMap.put("city", ChangeLocationActivity.this.city);
                ChangeLocationActivity.this.updateLocMap.put("latitude", String.valueOf(ChangeLocationActivity.this.latitude));
                ChangeLocationActivity.this.updateLocMap.put("longitude", String.valueOf(ChangeLocationActivity.this.longitude));
                ChangeLocationActivity.this.updateLocMap.put("postalcode", ChangeLocationActivity.this.postalCode);
                ChangeLocationActivity.this.updateLocMap.put("uid", ChangeLocationActivity.this.uid);
                return imageProcessClass.ImageHttpRequest(ChangeLocationActivity.this.ServerUploadPath1, ChangeLocationActivity.this.updateLocMap);
            }
        }.execute(new Void[0]);
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Denied")
                        .setMessage(getString(R.string.app_name)+" uses this permission to detect your current location and shows you great restaurants around you. please allow the permission.")
                        .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(ChangeLocationActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                    //Request location updates:
                          getLocation();

                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }
}
