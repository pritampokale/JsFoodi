package bugfix.itsolutions.jsfoodi.ui.order;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
//import com.mapbox.api.directions.v5.MapboxDirections;
//import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

import bugfix.itsolutions.jsfoodi.MainActivity;
import bugfix.itsolutions.jsfoodi.R;
import ir.samanjafari.easycountdowntimer.EasyCountDownTextview;

/* loaded from: classes4.dex */
public class CurrentOrderActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String ICON_LAYER_ID = "icon-layer-id";
    private static final String ICON_SOURCE_ID = "icon-source-id";
    private static final String RED_PIN_ICON_ID = "red-pin-icon-id";
    private static final String ROUTE_LAYER_ID = "route-layer-id";
    private static final String ROUTE_SOURCE_ID = "route-source-id";
//    private MapboxDirections client;
//    private DirectionsRoute currentRoute;
    private FirebaseFirestore db;
    ImageView img;
    private double latitude;
    private double longitude;
    Button mCallResBtn;
    private EasyCountDownTextview mCountDownTimer;
    private LottieAnimationView mDeliveryAnimation;
    private ImageView mGoBackBtn;
    private double mResLatitude;
    private double mResLongitude;
    private TextView mTimeFormatText;
    private double mUserLatitude;
    private double mUserLongitude;
    private MapView mapView;
    private String order_id;
    SharedPreferences pref;
    private String prep_Time;
    ProgressDialog progressDialog;
    int resDeliveryTime;
    private String resName;
    private String resPhoneNum;
    private String resUid;
    TextView txt;
    private String uid;
    private String RES_LIST = "RestaurantList";
    private String deliveryUid = "";
    private String USER_LIST = "UserList";
    int conr = 1;
    ArrayList<JSONObject> list2 = new ArrayList<>();
    ArrayList<JSONObject> list4 = new ArrayList<>();
    ArrayList<JSONObject> list1 = new ArrayList<>();
    ArrayList<JSONObject> list3 = new ArrayList<>();

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_current_order);
        changestatusbarcolor();
        fetchInfo();
        this.mCallResBtn = (Button) findViewById(R.id.callResBtn);
        this.img = (ImageView) findViewById(R.id.imageView6);
        this.txt = (TextView) findViewById(R.id.textView5);
        this.pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps)).getMapAsync(this);
        ImageView imageView = (ImageView) findViewById(R.id.cartBackBtn);
        this.mGoBackBtn = imageView;
        imageView.setOnClickListener(new View.OnClickListener() { // from class: ui.order.CurrentOrderActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CurrentOrderActivity.this.m1965lambda$onCreate$0$uiorderCurrentOrderActivity(view);
            }
        });
    }

    /* renamed from: lambda$onCreate$0$ui-order-CurrentOrderActivity  reason: not valid java name */
    public /* synthetic */ void m1965lambda$onCreate$0$uiorderCurrentOrderActivity(View view) {
        onBackPressed();
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

    private void fetchInfo() {
        this.resUid = getIntent().getStringExtra("RES_UID");
        this.resName = getIntent().getStringExtra("RES_NAME");
        this.order_id = getIntent().getStringExtra("ORDER_UID");
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        Objects.requireNonNull(currentUser);
        this.uid = currentUser.getUid();
        Volley.newRequestQueue(getApplicationContext()).add(new JsonArrayRequest(0, "https://jsfoodi.com/Jsfoodi_App/JsFoodi/fetchSpecificRestaurant.php?id=" + this.resUid, null, new AnonymousClass1(), new Response.ErrorListener() { // from class: ui.order.CurrentOrderActivity.2
            @Override // com.android.volley.Response.ErrorListener
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("Error.Response", volleyError.toString());
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: ui.order.CurrentOrderActivity$1  reason: invalid class name */
    /* loaded from: classes4.dex */
    public class AnonymousClass1 implements Response.Listener<JSONArray> {
        AnonymousClass1() {
        }

        @Override // com.android.volley.Response.Listener
        public void onResponse(JSONArray jSONArray) {
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObject = null;
                try {
                    jSONObject = jSONArray.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                CurrentOrderActivity.this.list3.add(jSONObject);
                try {
                    CurrentOrderActivity currentOrderActivity = CurrentOrderActivity.this;
                    currentOrderActivity.resPhoneNum = currentOrderActivity.list3.get(i).getString("phoneNumber");
                    CurrentOrderActivity currentOrderActivity2 = CurrentOrderActivity.this;
                    currentOrderActivity2.resName = currentOrderActivity2.list3.get(i).getString("name");
                    ((TextView) CurrentOrderActivity.this.findViewById(R.id.currentOrderResLoc)).setText(CurrentOrderActivity.this.list3.get(i).getString("address"));
                    CurrentOrderActivity currentOrderActivity3 = CurrentOrderActivity.this;
                    currentOrderActivity3.prep_Time = currentOrderActivity3.list3.get(i).getString("prepTime");
                    CurrentOrderActivity currentOrderActivity4 = CurrentOrderActivity.this;
                    currentOrderActivity4.mResLatitude = Double.parseDouble(currentOrderActivity4.list3.get(i).getString("latitude"));
                    CurrentOrderActivity currentOrderActivity5 = CurrentOrderActivity.this;
                    currentOrderActivity5.mResLongitude = Double.parseDouble(currentOrderActivity5.list3.get(i).getString("longitude"));
                    String replace = CurrentOrderActivity.this.prep_Time.replace(" Mins", "");
                    try {
                        CurrentOrderActivity.this.resDeliveryTime = Integer.parseInt(replace);
                    } catch (NumberFormatException unused) {
                    }
                    ArrayList arrayList = new ArrayList();
                    ArrayList arrayList2 = new ArrayList();
                    Volley.newRequestQueue(CurrentOrderActivity.this.getApplicationContext()).add(new JsonArrayRequest(0, "https://jsfoodi.com/Jsfoodi_App/JsFoodi/fetchOrders.php?id=" + CurrentOrderActivity.this.uid, null, new C00661(arrayList2, arrayList), new Response.ErrorListener() { // from class: ui.order.CurrentOrderActivity.1.2
                        @Override // com.android.volley.Response.ErrorListener
                        public void onErrorResponse(VolleyError volleyError) {
                            Log.d("Error.Response", volleyError.toString());
                        }
                    }));
                    Volley.newRequestQueue(CurrentOrderActivity.this.getApplicationContext()).add(new JsonArrayRequest(0, "https://jsfoodi.com/Jsfoodi_App/JsFoodi/fetchUser.php?id=" + CurrentOrderActivity.this.uid, null, new Response.Listener<JSONArray>() { // from class: ui.order.CurrentOrderActivity.1.3
                        @Override // com.android.volley.Response.Listener
                        public void onResponse(JSONArray jSONArray2) {
                            for (int i2 = 0; i2 < jSONArray2.length(); i2++) {
                                JSONObject jSONObject2 = null;
                                try {
                                    jSONObject2 = jSONArray2.getJSONObject(i2);
                                } catch (JSONException e2) {
                                    e2.printStackTrace();
                                }
                                CurrentOrderActivity.this.list2.add(jSONObject2);
                                try {
                                    CurrentOrderActivity.this.mUserLatitude = Double.parseDouble(CurrentOrderActivity.this.list2.get(i2).getString("latitude"));
                                    CurrentOrderActivity.this.mUserLongitude = Double.parseDouble(CurrentOrderActivity.this.list2.get(i2).getString("longitude"));
                                    CurrentOrderActivity.this.init(CurrentOrderActivity.this.mResLongitude, CurrentOrderActivity.this.mResLatitude, CurrentOrderActivity.this.mUserLongitude, CurrentOrderActivity.this.mUserLatitude, CurrentOrderActivity.this.resName, CurrentOrderActivity.this.resPhoneNum);
                                    ((SupportMapFragment) CurrentOrderActivity.this.getSupportFragmentManager().findFragmentById(R.id.maps)).getMapAsync(CurrentOrderActivity.this);
                                } catch (JSONException e3) {
                                    e3.printStackTrace();
                                }
                            }
                        }
                    }, new Response.ErrorListener() { // from class: ui.order.CurrentOrderActivity.1.4
                        @Override // com.android.volley.Response.ErrorListener
                        public void onErrorResponse(VolleyError volleyError) {
                            Log.d("Error.Response", volleyError.toString());
                        }
                    }));
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: ui.order.CurrentOrderActivity$1$1  reason: invalid class name and collision with other inner class name */
        /* loaded from: classes4.dex */
        public class C00661 implements Response.Listener<JSONArray> {
            final /* synthetic */ ArrayList val$list54;
            final /* synthetic */ ArrayList val$list55;

            C00661(ArrayList arrayList, ArrayList arrayList2) {
                this.val$list55 = arrayList;
                this.val$list54 = arrayList2;
            }

            @RequiresApi(api = Build.VERSION_CODES.O)
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
                    this.val$list55.add(jSONObject);
                    try {
                        if (((JSONObject) this.val$list55.get(i)).getString("ordered_id").equals(CurrentOrderActivity.this.order_id)) {
                            this.val$list54.add((JSONObject) this.val$list55.get(i));
                        }
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                }
                for (int i2 = 0; i2 < this.val$list54.size(); i2++) {
                    try {
                        String string = ((JSONObject) this.val$list54.get(i2)).getString("dateTime");
                        CurrentOrderActivity.this.deliveryUid = ((JSONObject) this.val$list54.get(i2)).getString("deliveryBoy");
                        DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        DateTimeFormatter ofPattern2 = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a");
                        LocalDateTime parse = LocalDateTime.parse(string, ofPattern);
                        CurrentOrderActivity.this.mCountDownTimer = (EasyCountDownTextview) CurrentOrderActivity.this.findViewById(R.id.easyCountDownTextview);
                        CurrentOrderActivity.this.mTimeFormatText = (TextView) CurrentOrderActivity.this.findViewById(R.id.timeLeftText);
                        CurrentOrderActivity.this.mCountDownTimer.setVisibility(4);
                        TextView textView = CurrentOrderActivity.this.mTimeFormatText;
                        textView.setText(parse.plusMinutes(CurrentOrderActivity.this.resDeliveryTime).format(ofPattern2) + "");
                    } catch (JSONException e3) {
                        e3.printStackTrace();
                    }
                    if (CurrentOrderActivity.this.deliveryUid.isEmpty() || CurrentOrderActivity.this.deliveryUid == "") {
                        CurrentOrderActivity.this.img.setVisibility(0);
                        CurrentOrderActivity.this.img.setImageResource(R.drawable.waitingdel_1);
                        CurrentOrderActivity.this.txt.setVisibility(0);
                        CurrentOrderActivity.this.txt.setText("Waiting For Delivery Boy");
                        try {
                            if (((JSONObject) this.val$list54.get(i2)).getString(NotificationCompat.CATEGORY_STATUS).equals("Ordered")) {
                                CurrentOrderActivity.this.img.setImageResource(R.drawable.person_waiting);
                                CurrentOrderActivity.this.txt.setText("Your Food is Preparing");
                                CurrentOrderActivity.this.mCallResBtn.setOnClickListener(new View.OnClickListener() { // from class: ui.order.CurrentOrderActivity$1$1$$ExternalSyntheticLambda0
                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view) {
                                        C00661.this.m1966lambda$onResponse$0$uiorderCurrentOrderActivity$1$1(view);
                                    }
                                });
                            }
                        } catch (JSONException e4) {
                            e4.printStackTrace();
                        }
                    } else {
                        CurrentOrderActivity.this.img.setVisibility(8);
                        CurrentOrderActivity.this.txt.setVisibility(8);
                        Volley.newRequestQueue(CurrentOrderActivity.this.getApplicationContext()).add(new JsonArrayRequest(0, "https://jsfoodi.com/Jsfoodi_App/Delivery/fetchDeliveryBoyMob.php?id=" + CurrentOrderActivity.this.deliveryUid, null, new C00671(), new Response.ErrorListener() { // from class: ui.order.CurrentOrderActivity.1.1.2
                            @Override // com.android.volley.Response.ErrorListener
                            public void onErrorResponse(VolleyError volleyError) {
                                Log.d("Error.Response", volleyError.toString());
                            }
                        }));
                    }
                }
            }

            /* renamed from: lambda$onResponse$0$ui-order-CurrentOrderActivity$1$1  reason: not valid java name */
            public /* synthetic */ void m1966lambda$onResponse$0$uiorderCurrentOrderActivity$1$1(View view) {
                Intent intent = new Intent("android.intent.action.DIAL");
                intent.setData(Uri.parse("tel:+917499818928"));
                CurrentOrderActivity.this.startActivity(intent);
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            /* renamed from: ui.order.CurrentOrderActivity$1$1$1  reason: invalid class name and collision with other inner class name */
            /* loaded from: classes4.dex */
            public class C00671 implements Response.Listener<JSONArray> {
                C00671() {
                }

                @Override // com.android.volley.Response.Listener
                public void onResponse(JSONArray jSONArray) {
                    for (int i = 0; i < jSONArray.length(); i++) {
                        JSONObject jSONObject = null;
                        try {
                            jSONObject = jSONArray.getJSONObject(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        CurrentOrderActivity.this.list4.add(jSONObject);
                        try {
                            CurrentOrderActivity.this.mResLatitude = Double.parseDouble(CurrentOrderActivity.this.list4.get(i).getString("latitude"));
                            CurrentOrderActivity.this.mResLongitude = Double.parseDouble(CurrentOrderActivity.this.list4.get(i).getString("longitude"));
                            CurrentOrderActivity.this.mCallResBtn.setOnClickListener(new View.OnClickListener() { // from class: ui.order.CurrentOrderActivity$1$1$1$$ExternalSyntheticLambda0
                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view) {
                                    C00671.this.m1967lambda$onResponse$0$uiorderCurrentOrderActivity$1$1$1(view);
                                }
                            });
                        } catch (JSONException e2) {
                            e2.printStackTrace();
                        }
                        CurrentOrderActivity.this.pref = PreferenceManager.getDefaultSharedPreferences(CurrentOrderActivity.this.getApplicationContext());
                        Double valueOf = Double.valueOf(CurrentOrderActivity.this.pref.getString("latitude", ""));
                        Double valueOf2 = Double.valueOf(CurrentOrderActivity.this.pref.getString("longitude", ""));
                        WebView webView = (WebView) CurrentOrderActivity.this.findViewById(R.id.web);
                        webView.setWebViewClient(new WebViewClient());
                        webView.getSettings().setJavaScriptEnabled(true);
                        if (CurrentOrderActivity.this.conr == 1) {
                            CurrentOrderActivity.this.conr++;
                            CurrentOrderActivity.this.progressDialog = new ProgressDialog(CurrentOrderActivity.this);
                            CurrentOrderActivity.this.progressDialog.setMessage("Loading...");
                            CurrentOrderActivity.this.progressDialog.show();
                        }
                        webView.setWebViewClient(new WebViewClient() { // from class: ui.order.CurrentOrderActivity.1.1.1.1
                            @Override // android.webkit.WebViewClient
                            public void onReceivedError(WebView webView2, int i2, String str, String str2) {
                            }

                            @Override // android.webkit.WebViewClient
                            public boolean shouldOverrideUrlLoading(WebView webView2, String str) {
                                if (str.contains("intent://")) {
                                    Toast.makeText(CurrentOrderActivity.this, "Navigation Feature is not for you", 0).show();
                                    return true;
                                }
                                webView2.loadUrl(str);
                                return true;
                            }

                            @Override // android.webkit.WebViewClient
                            public void onPageFinished(WebView webView2, String str) {
                                if (CurrentOrderActivity.this.progressDialog.isShowing()) {
                                    new Handler().postDelayed(new Runnable() { // from class: ui.order.CurrentOrderActivity.1.1.1.1.1
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            CurrentOrderActivity.this.progressDialog.dismiss();
                                        }
                                    }, 2000L);
                                }
                            }
                        });
                        webView.loadUrl("http://maps.google.com/maps?saddr=" + valueOf + "," + valueOf2 + "&daddr=" + CurrentOrderActivity.this.mResLatitude + "," + CurrentOrderActivity.this.mResLongitude);
                    }
                }

                /* renamed from: lambda$onResponse$0$ui-order-CurrentOrderActivity$1$1$1  reason: not valid java name */
                public /* synthetic */ void m1967lambda$onResponse$0$uiorderCurrentOrderActivity$1$1$1(View view) {
                    Intent intent = new Intent("android.intent.action.DIAL");
                    intent.setData(Uri.parse("tel:" + CurrentOrderActivity.this.deliveryUid));
                    CurrentOrderActivity.this.startActivity(intent);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void init(double d, double d2, double d3, double d4, String str, String str2) {
        ((TextView) findViewById(R.id.currentOrderResName)).setText(str);
        ((TextView) findViewById(R.id.orderedRestaurantName)).setText(str);
        this.mCallResBtn = (Button) findViewById(R.id.callResBtn);
        Point.fromLngLat(d, d2);
        Point.fromLngLat(d3, d4);
        new LatLng(d4, d3);
        new LatLng(d2, d);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    @Override // com.google.android.gms.maps.OnMapReadyCallback
    public void onMapReady(GoogleMap googleMap) {
        com.google.android.gms.maps.model.LatLng latLng = new com.google.android.gms.maps.model.LatLng(Double.valueOf(this.pref.getString("latitude", "")).doubleValue(), Double.valueOf(this.pref.getString("longitude", "")).doubleValue());
        com.google.android.gms.maps.model.LatLng latLng2 = new com.google.android.gms.maps.model.LatLng(this.mResLatitude, this.mResLongitude);
        googleMap.addMarker(new MarkerOptions().position(latLng).title("Current Location"));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18.0f));
        googleMap.addMarker(new MarkerOptions().position(latLng2).title("Restaurant Location").icon(BitmapDescriptorFactory.defaultMarker(30.0f)));
    }

    private boolean appInstalledOrNot(String str) {
        try {
            getPackageManager().getPackageInfo(str, 1);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    @Override // android.view.Window.Callback
    public void onPointerCaptureChanged(boolean z) {
        super.onPointerCaptureChanged(z);
    }
}
