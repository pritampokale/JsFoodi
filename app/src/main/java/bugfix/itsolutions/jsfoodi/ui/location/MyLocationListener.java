package bugfix.itsolutions.jsfoodi.ui.location;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.core.content.ContextCompat;

import com.google.firebase.analytics.FirebaseAnalytics;

/* loaded from: classes4.dex */
public class MyLocationListener implements LocationListener {
    public static double latitude;
    public static double longitude;
    Context ctx;
    boolean isGPSEnabled;
    boolean isNetworkEnabled;
    Location location;
    LocationManager locationManager;

    @Override // android.location.LocationListener
    public void onProviderDisabled(String str) {
    }

    @Override // android.location.LocationListener
    public void onProviderEnabled(String str) {
    }

    @Override // android.location.LocationListener
    public void onStatusChanged(String str, int i, Bundle bundle) {
    }

    public MyLocationListener(Context context) {
        this.isGPSEnabled = false;
        this.isNetworkEnabled = false;
        this.ctx = context;
        try {
            LocationManager locationManager = (LocationManager) context.getSystemService(FirebaseAnalytics.Param.LOCATION);
            this.locationManager = locationManager;
            this.isGPSEnabled = locationManager.isProviderEnabled("gps");
            this.isNetworkEnabled = this.locationManager.isProviderEnabled("network");
            if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(context, "android.permission.ACCESS_FINE_LOCATION") != 0) {
                ContextCompat.checkSelfPermission(context, "android.permission.ACCESS_COARSE_LOCATION");
            }
            if (this.isGPSEnabled) {
                this.locationManager.requestLocationUpdates("gps", 0L, 0.0f, this);
                this.location = this.locationManager.getLastKnownLocation("gps");
            }
            if (this.isNetworkEnabled) {
                this.locationManager.requestLocationUpdates("network", 0L, 0.0f, this);
                this.location = this.locationManager.getLastKnownLocation("network");
            }
            latitude = this.location.getLatitude();
            longitude = this.location.getLongitude();
        } catch (Exception unused) {
        }
    }

    @Override // android.location.LocationListener
    public void onLocationChanged(Location location) {
        location.getLatitude();
        location.getLongitude();
        latitude = location.getLatitude();
        longitude = location.getLongitude();
    }
}
