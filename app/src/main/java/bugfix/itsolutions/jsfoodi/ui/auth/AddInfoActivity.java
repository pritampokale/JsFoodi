package bugfix.itsolutions.jsfoodi.ui.auth;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import bugfix.itsolutions.jsfoodi.MainActivity;
import bugfix.itsolutions.jsfoodi.R;
import bugfix.itsolutions.jsfoodi.ui.location.ChangeLocationActivity;

/* loaded from: classes4.dex */
public class AddInfoActivity extends AppCompatActivity implements LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private List<Address> addresses;
    private String city;
    private FirebaseFirestore db;
    private String devicetoken;
    private String finalAddress;
    GoogleApiClient gac;
    private String knownName;
    private String latitude;
    LocationRequest locationRequest;
    private String longitude;
    private Button mSaveInfoBtn;
    EditText mUserAddress;
    private EditText mUserEmail;
    private EditText mUserName;
    private String phoneNum;
    private String pinCode;
    private String postalCode;
    private String subLocality;
    private String uid;
    private EditText userCity;
    private EditText userPincode;
    ArrayList<JSONObject> list1 = new ArrayList<>();
    final String TAG = "GPS";
    int cont = 1;
    private long UPDATE_INTERVAL = 2000;
    private long FASTEST_INTERVAL = 10000;
    HashMap<String, String> HashMapParams = new HashMap<>();
    String ServerUploadPath = "https://jsfoodi.com/Jsfoodi_App/JsFoodi/addUser.php";

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$showAlert$2(DialogInterface dialogInterface, int i) {
    }

    @Override // com.google.android.gms.common.api.internal.ConnectionCallbacks
    public void onConnectionSuspended(int i) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_add_info);
        changestatusbarcolor();
        init();
        this.db = FirebaseFirestore.getInstance();
        this.phoneNum = getIntent().getStringExtra("PHONENUMBER");
        this.uid = getIntent().getStringExtra("UID");
        this.devicetoken = getIntent().getStringExtra("TOKEN");
        Volley.newRequestQueue(getApplicationContext()).add(new JsonArrayRequest(0, "https://jsfoodi.com/Jsfoodi_App/JsFoodi/fetchUser.php?id=" + this.uid, null, new Response.Listener<JSONArray>() { // from class: ui.auth.AddInfoActivity.1
            @Override // com.android.volley.Response.Listener
            public void onResponse(JSONArray jSONArray) {
                Log.d("Response", jSONArray.toString());
                for (int i = 0; i < jSONArray.length(); i++) {
                    JSONObject jSONObject = null;
                    try {
                        jSONObject = jSONArray.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    AddInfoActivity.this.list1.add(jSONObject);
                    try {
                        AddInfoActivity addInfoActivity = AddInfoActivity.this;
                        addInfoActivity.pinCode = addInfoActivity.list1.get(i).getString("postalcode");
                        AddInfoActivity addInfoActivity2 = AddInfoActivity.this;
                        addInfoActivity2.latitude = addInfoActivity2.list1.get(i).getString("latitude");
                        AddInfoActivity addInfoActivity3 = AddInfoActivity.this;
                        addInfoActivity3.longitude = addInfoActivity3.list1.get(i).getString("longitude");
                    } catch (NullPointerException | JSONException e2) {
                        e2.printStackTrace();
                    }
                    if (!AddInfoActivity.this.latitude.isEmpty() && !AddInfoActivity.this.latitude.equals("null") && !AddInfoActivity.this.latitude.equals("no")) {
                        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
                        edit.putString("pinCode", AddInfoActivity.this.pinCode);
                        edit.putString("latitude", AddInfoActivity.this.latitude);
                        edit.putString("longitude", AddInfoActivity.this.longitude);
                        edit.apply();
                        Intent intent = new Intent(AddInfoActivity.this, MainActivity.class);
                        intent.addFlags(67108864);
                        intent.addFlags(32768);
                        AddInfoActivity.this.startActivity(intent);
                        AddInfoActivity.this.finish();
                        Intent intent2 = new Intent(AddInfoActivity.this, MainActivity.class);
                        intent2.addFlags(67108864);
                        intent2.addFlags(32768);
                        AddInfoActivity.this.startActivity(intent2);
                        AddInfoActivity.this.finish();
                    }
                    Intent intent3 = new Intent(AddInfoActivity.this.getApplicationContext(), ChangeLocationActivity.class);
                    intent3.addFlags(67108864);
                    intent3.addFlags(32768);
                    intent3.putExtra("INT", "THREE");
                    AddInfoActivity.this.startActivity(intent3);
                    Intent intent22 = new Intent(AddInfoActivity.this, MainActivity.class);
                    intent22.addFlags(67108864);
                    intent22.addFlags(32768);
                    AddInfoActivity.this.startActivity(intent22);
                    AddInfoActivity.this.finish();
                }
            }
        }, new Response.ErrorListener() { // from class: ui.auth.AddInfoActivity.2
            @Override // com.android.volley.Response.ErrorListener
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("Error.Response", volleyError.toString());
            }
        }));
        this.mSaveInfoBtn.setOnClickListener(new View.OnClickListener() { // from class: ui.auth.AddInfoActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AddInfoActivity.this.m1948lambda$onCreate$0$uiauthAddInfoActivity(view);
            }
        });
    }

    /* renamed from: lambda$onCreate$0$ui-auth-AddInfoActivity  reason: not valid java name */
    public /* synthetic */ void m1948lambda$onCreate$0$uiauthAddInfoActivity(View view) {
        String obj = this.mUserName.getText().toString();
        String obj2 = this.mUserEmail.getText().toString();
        this.userPincode.getText().toString();
        if (obj.isEmpty() || obj2.isEmpty()) {
            Toast.makeText(this, "Please Enter Valid Info", 0).show();
            return;
        }
        this.HashMapParams.put("phone", this.phoneNum);
        this.HashMapParams.put("name", obj);
        this.HashMapParams.put("email", obj2);
        this.HashMapParams.put("latitude", String.valueOf(this.latitude));
        this.HashMapParams.put("longitude", String.valueOf(this.longitude));
        this.HashMapParams.put("address", this.mUserAddress.getText().toString());
        this.HashMapParams.put("city", this.userCity.getText().toString());
        this.HashMapParams.put("postalcode", this.userPincode.getText().toString());
        addUser();
    }

    private void init() {
        this.mUserName = (EditText) findViewById(R.id.userName);
        this.mUserEmail = (EditText) findViewById(R.id.userEmail);
        this.mSaveInfoBtn = (Button) findViewById(R.id.addInfo);
        this.mUserAddress = (EditText) findViewById(R.id.userAddress);
        this.userPincode = (EditText) findViewById(R.id.userPincode);
        this.userCity = (EditText) findViewById(R.id.userCity);
        isGooglePlayServicesAvailable();
        if (!isLocationEnabled()) {
            showAlert();
        }
        LocationRequest locationRequest = new LocationRequest();
        this.locationRequest = locationRequest;
        locationRequest.setInterval(this.UPDATE_INTERVAL);
        this.locationRequest.setFastestInterval(this.FASTEST_INTERVAL);
        this.locationRequest.setPriority(100);
        this.gac = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
    }

    public void changestatusbarcolor() {
        Window window = getWindow();
        window.clearFlags(67108864);
        window.addFlags(Integer.MIN_VALUE);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        if (Build.VERSION.SDK_INT >= 23) {
            getWindow().getDecorView().setSystemUiVisibility(8192);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStart() {
        this.gac.connect();
        super.onStart();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        this.gac.disconnect();
        super.onStop();
    }

    @Override // com.google.android.gms.location.LocationListener
    public void onLocationChanged(Location location) {
        if (location == null || this.cont != 1) {
            return;
        }
        updateUI(location);
        this.cont++;
    }

    @Override // com.google.android.gms.common.api.internal.ConnectionCallbacks
    public void onConnected(Bundle bundle) {
        try {
            if (ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") != 0 && ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") != 0) {
                ActivityCompat.requestPermissions(this, new String[]{"android.permission.ACCESS_FINE_LOCATION"}, 1);
                return;
            }
            Log.d("GPS", "onConnected");
            Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(this.gac);
            Log.d("GPS", "LastLocation: " + lastLocation.toString());
            LocationServices.FusedLocationApi.requestLocationUpdates(this.gac, this.locationRequest, this);
        } catch (NullPointerException unused) {
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i != 1) {
            return;
        }
        if (iArr.length > 0 && iArr[0] == 0) {
            try {
                LocationServices.FusedLocationApi.requestLocationUpdates(this.gac, this.locationRequest, this);
                return;
            } catch (SecurityException e) {
                Toast.makeText(this, "SecurityException:\n" + e.toString(), 1).show();
                return;
            }
        }
        Toast.makeText(this, "Permission denied!", 1).show();
    }

    @Override // com.google.android.gms.common.api.internal.OnConnectionFailedListener
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this, "onConnectionFailed: \n" + connectionResult.toString(), 1).show();
        Log.d("DDD", connectionResult.toString());
    }

    private void updateUI(Location location) {
        Log.d("GPS", "updateUI");
        try {
            this.addresses = new Geocoder(this, Locale.getDefault()).getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Address> list = this.addresses;
        if (list == null || list.size() <= 0) {
            return;
        }
        this.city = this.addresses.get(0).getLocality();
        this.postalCode = this.addresses.get(0).getPostalCode();
        this.knownName = this.addresses.get(0).getFeatureName();
        this.subLocality = this.addresses.get(0).getSubLocality();
        String str = this.knownName + ", " + this.subLocality + ", " + this.city + ", " + this.postalCode;
        this.finalAddress = str;
        this.mUserAddress.setText(str);
        this.userPincode.setText(this.postalCode);
        this.userCity.setText(this.city);
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(FirebaseAnalytics.Param.LOCATION);
        return locationManager.isProviderEnabled("gps") || locationManager.isProviderEnabled("network");
    }

    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int isGooglePlayServicesAvailable = googleApiAvailability.isGooglePlayServicesAvailable(this);
        if (isGooglePlayServicesAvailable != 0) {
            if (googleApiAvailability.isUserResolvableError(isGooglePlayServicesAvailable)) {
                googleApiAvailability.getErrorDialog(this, isGooglePlayServicesAvailable, 9000).show();
                return false;
            }
            Log.d("GPS", "This device is not supported.");
            finish();
            return false;
        }
        Log.d("GPS", "This device is supported.");
        return true;
    }

    private void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enable Location").setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to use this app").setPositiveButton("Location Settings", new DialogInterface.OnClickListener() { // from class: ui.auth.AddInfoActivity$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                AddInfoActivity.this.m1949lambda$showAlert$1$uiauthAddInfoActivity(dialogInterface, i);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() { // from class: ui.auth.AddInfoActivity$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                AddInfoActivity.lambda$showAlert$2(dialogInterface, i);
            }
        });
        builder.show();
    }

    /* renamed from: lambda$showAlert$1$ui-auth-AddInfoActivity  reason: not valid java name */
    public /* synthetic */ void m1949lambda$showAlert$1$uiauthAddInfoActivity(DialogInterface dialogInterface, int i) {
        startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [ui.auth.AddInfoActivity$1AsyncTaskUploadClass] */
    public void addUser() {
        new AsyncTask<Void, Void, String>() { // from class: ui.auth.AddInfoActivity.1AsyncTaskUploadClass
            @Override // android.os.AsyncTask
            protected void onPreExecute() {
                super.onPreExecute();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public void onPostExecute(String str) {
                super.onPostExecute( str);
                Intent intent = new Intent(AddInfoActivity.this, MainActivity.class);
                intent.addFlags(67108864);
                intent.addFlags(32768);
                AddInfoActivity.this.startActivity(intent);
                AddInfoActivity.this.finish();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public String doInBackground(Void... voidArr) {
                ImageProcessClass imageProcessClass = new ImageProcessClass();
                AddInfoActivity.this.HashMapParams.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                return imageProcessClass.ImageHttpRequest(AddInfoActivity.this.ServerUploadPath, AddInfoActivity.this.HashMapParams);
            }
        }.execute(new Void[0]);
    }

    /* loaded from: classes4.dex */
    public static class ImageProcessClass {
        private boolean check = true;

        public String ImageHttpRequest(String str, HashMap<String, String> hashMap) {
            StringBuilder sb = new StringBuilder();
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
                httpURLConnection.setReadTimeout(19000);
                httpURLConnection.setConnectTimeout(19000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                bufferedWriter.write(bufferedWriterDataFN(hashMap));
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                if (httpURLConnection.getResponseCode() == 200) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    StringBuilder sb2 = new StringBuilder();
                    while (true) {
                        try {
                            String readLine = bufferedReader.readLine();
                            if (readLine == null) {
                                break;
                            }
                            sb2.append(readLine);
                        } catch (Exception e) {
                            e = e;
                            sb = sb2;
                            e.printStackTrace();
                            return sb.toString();
                        }
                    }
                    sb = sb2;
                }
            } catch (Exception e2) {
//                e = e2;
            }
            return sb.toString();
        }

        private String bufferedWriterDataFN(HashMap<String, String> hashMap) throws UnsupportedEncodingException {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry : hashMap.entrySet()) {
                if (this.check) {
                    this.check = false;
                } else {
                    sb.append("&");
                }
                sb.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                sb.append("=");
                sb.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
            return sb.toString();
        }
    }
}
