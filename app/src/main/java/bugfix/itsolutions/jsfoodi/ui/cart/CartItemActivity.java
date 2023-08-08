package bugfix.itsolutions.jsfoodi.ui.cart;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;

import bugfix.itsolutions.jsfoodi.adapters.cartListRCAdapter;
import bugfix.itsolutions.jsfoodi.MainActivity;
import bugfix.itsolutions.jsfoodi.R;
import bugfix.itsolutions.jsfoodi.ui.location.ChangeLocationActivity;
import bugfix.itsolutions.jsfoodi.ui.order.CheckoutActivity;

/* loaded from: classes4.dex */
public class CartItemActivity extends AppCompatActivity {
    private static final DecimalFormat decfor = new DecimalFormat("0.00");
    private FirebaseFirestore db;
    double distanceinKM;
    cartListRCAdapter expencesListRCAdapter;
    Double lat;
    LinearLayoutManager linearLayoutManager;
    Double lon;
    private RecyclerView mCartItemRecyclerView;
    private EditText mExtraInstructionsText;
    private TextView mRestaurantCartName;
    private TextView mTotalAmountText;
    private TextView mUserAddressText;
    private TextView mamountSaved;
    private RequestQueue queue;
    private String resDeliveryTime;
    Double resLat;
    Double resLon;
    private String resSpotImage;
    private String ruid;
    private String uid;
    private String userAddress;
    private String userName;
    private String userPhoneNum;
    private String extraIns = "none";
    private String USER_LIST = "UserList";
    private String CART_ITEMS = "CartItems";
    String checkf = "1";
    ArrayList<String> itemsArr = new ArrayList<>();
    ArrayList<String> orderedItemsArr = new ArrayList<>();

    /* loaded from: classes4.dex */
//    public class CartItemHolder_ViewBinding implements Unbinder {
//        private CartItemHolder target;
//
//        public CartItemHolder_ViewBinding(CartItemHolder cartItemHolder, View view) {
//            this.target = cartItemHolder;
//            cartItemHolder.mFoodMarkImg = (ImageView) Utils.findRequiredViewAsType(view, R.id.foodMarkCart, "field 'mFoodMarkImg'", ImageView.class);
//            cartItemHolder.mItemCartName = (TextView) Utils.findRequiredViewAsType(view, R.id.itemNameCart, "field 'mItemCartName'", TextView.class);
//            cartItemHolder.mItemCartPrice = (TextView) Utils.findRequiredViewAsType(view, R.id.itemPriceCart, "field 'mItemCartPrice'", TextView.class);
//            cartItemHolder.mQtyPicker = (ElegantNumberButton) Utils.findRequiredViewAsType(view, R.id.quantityPicker, "field 'mQtyPicker'", ElegantNumberButton.class);
//        }
//
////        @Override // butterknife.Unbinder
////        public void unbind() {
////            CartItemHolder cartItemHolder = this.target;
////            if (cartItemHolder == null) {
////                throw new IllegalStateException("Bindings already cleared.");
////            }
////            this.target = null;
////            cartItemHolder.mFoodMarkImg = null;
////            cartItemHolder.mItemCartName = null;
////            cartItemHolder.mItemCartPrice = null;
////            cartItemHolder.mQtyPicker = null;
////        }
//    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_cart_item);
        ((ImageView) findViewById(R.id.imageView3)).setVisibility(8);
        init();
    }

    private void init() {
        this.db = FirebaseFirestore.getInstance();
        ((TextView) findViewById(R.id.confirmOrderText)).setText("Confirm Order");
        this.mRestaurantCartName = (TextView) findViewById(R.id.restaurantCartName);
        this.mamountSaved = (TextView) findViewById(R.id.amountSaved);
        ((TextView) findViewById(R.id.changeAddressText)).setOnClickListener(new View.OnClickListener() { // from class: ui.cart.CartItemActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CartItemActivity.this.m1955lambda$init$0$uicartCartItemActivity(view);
            }
        });
        ((ImageView) findViewById(R.id.cartBackBtn)).setOnClickListener(new View.OnClickListener() { // from class: ui.cart.CartItemActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CartItemActivity.this.m1956lambda$init$1$uicartCartItemActivity(view);
            }
        });
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        Objects.requireNonNull(currentUser);
        this.uid = currentUser.getUid();
        this.mCartItemRecyclerView = (RecyclerView) findViewById(R.id.cartItemRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, 1, false);
        this.linearLayoutManager = linearLayoutManager;
        this.mCartItemRecyclerView.setLayoutManager(linearLayoutManager);
        final ArrayList arrayList = new ArrayList();
        final ArrayList arrayList2 = new ArrayList();
        final ArrayList arrayList3 = new ArrayList();
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        this.lat = Double.valueOf(defaultSharedPreferences.getString("latitude", ""));
        this.lon = Double.valueOf(defaultSharedPreferences.getString("longitude", ""));
        Volley.newRequestQueue(getApplicationContext()).add(new JsonArrayRequest(0, "https://jsfoodi.com/Jsfoodi_App/JsFoodi/fetchCart.php?id=" + this.uid, null, new Response.Listener<JSONArray>() { // from class: ui.cart.CartItemActivity.1
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
                    arrayList2.add(jSONObject);
                    try {
                        CartItemActivity.this.ruid = ((JSONObject) arrayList2.get(i)).getString("ruid");
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                    Volley.newRequestQueue(CartItemActivity.this.getApplicationContext()).add(new JsonArrayRequest(0, "https://jsfoodi.com/Jsfoodi_App/JsFoodi/fetchSpecificRestaurant.php?id=" + CartItemActivity.this.ruid, null, new Response.Listener<JSONArray>() { // from class: ui.cart.CartItemActivity.1.1
                        @Override // com.android.volley.Response.Listener
                        public void onResponse(JSONArray jSONArray2) {
                            for (int i2 = 0; i2 < jSONArray2.length(); i2++) {
                                JSONObject jSONObject2 = null;
                                try {
                                    jSONObject2 = jSONArray2.getJSONObject(i2);
                                } catch (JSONException e3) {
                                    e3.printStackTrace();
                                }
                                arrayList3.add(jSONObject2);
                                try {
                                    CartItemActivity.this.mRestaurantCartName.setText(((JSONObject) arrayList3.get(i2)).getString("name"));
                                    CartItemActivity.this.resDeliveryTime = ((JSONObject) arrayList3.get(i2)).getString("prepTime");
                                    CartItemActivity.this.resSpotImage = ((JSONObject) arrayList3.get(i2)).getString("image");
                                    CartItemActivity.this.resLat = Double.valueOf(((JSONObject) arrayList3.get(i2)).getString("latitude"));
                                    CartItemActivity.this.resLon = Double.valueOf(((JSONObject) arrayList3.get(i2)).getString("longitude"));
                                    Location location = new Location("locationA");
                                    location.setLatitude(CartItemActivity.this.resLat.doubleValue());
                                    location.setLongitude(CartItemActivity.this.resLon.doubleValue());
                                    Location location2 = new Location("locationA");
                                    location2.setLatitude(CartItemActivity.this.lat.doubleValue());
                                    location2.setLongitude(CartItemActivity.this.lon.doubleValue());
                                    CartItemActivity.this.distanceinKM = location.distanceTo(location2) * 0.001d;

                                    if(distanceinKM > 4.5){
                                        Toast.makeText(CartItemActivity.this, "Hotel is not available for your location", Toast.LENGTH_LONG).show();
                                        Button button = (Button) CartItemActivity.this.findViewById(R.id.payAmountBtn);
                                        button.setEnabled(false);
                                    }

                                } catch (JSONException e4) {
                                    e4.printStackTrace();
                                }
                            }
                        }
                    }, new Response.ErrorListener() { // from class: ui.cart.CartItemActivity.1.2
                        @Override // com.android.volley.Response.ErrorListener
                        public void onErrorResponse(VolleyError volleyError) {
                            Log.d("Error.Response", volleyError.toString());
                        }
                    }));
                    if (arrayList2.size() == 0) {
                        CartItemActivity.this.moveIfCartEmpty();
                    } else {
                        int i2 = 0;
                        for (int i3 = 0; i3 < arrayList2.size(); i3++) {
                            try {
                                i2 += Integer.parseInt(((JSONObject) arrayList2.get(i3)).getString(FirebaseAnalytics.Param.PRICE)) * Integer.parseInt(((JSONObject) arrayList2.get(i3)).getString("count"));
                            } catch (NumberFormatException | JSONException e3) {
                                e3.printStackTrace();
                            }
                        }
                        TextView textView = CartItemActivity.this.mTotalAmountText;
                        textView.setText("Amount Payable: ₹" + i2);
                    }
                    RecyclerView recyclerView = (RecyclerView) CartItemActivity.this.findViewById(R.id.cartItemRecyclerView);
                    recyclerView.setLayoutManager(new LinearLayoutManager(CartItemActivity.this.getApplicationContext()));
                    CartItemActivity.this.expencesListRCAdapter = new cartListRCAdapter(CartItemActivity.this.getApplicationContext(), arrayList2);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setItemViewCacheSize(20);
                    recyclerView.setDrawingCacheEnabled(true);
                    recyclerView.setDrawingCacheQuality(1048576);
                    recyclerView.setAdapter(CartItemActivity.this.expencesListRCAdapter);
                }
            }
        }, new Response.ErrorListener() { // from class: ui.cart.CartItemActivity.2
            @Override // com.android.volley.Response.ErrorListener
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("Error.Response", volleyError.toString());
            }
        }));
        Volley.newRequestQueue(getApplicationContext()).add(new JsonArrayRequest(0, "https://jsfoodi.com/Jsfoodi_App/JsFoodi/fetchUser.php?id=" + this.uid, null, new Response.Listener<JSONArray>() { // from class: ui.cart.CartItemActivity.3
            @Override // com.android.volley.Response.Listener
            public void onResponse(JSONArray jSONArray) {
                for (int i = 0; i < jSONArray.length(); i++) {
                    JSONObject jSONObject = null;
                    try {
                        jSONObject = jSONArray.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    arrayList.add(jSONObject);
                    try {
                        CartItemActivity.this.mUserAddressText.setText(((JSONObject) arrayList.get(i)).getString("address"));
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() { // from class: ui.cart.CartItemActivity.4
            @Override // com.android.volley.Response.ErrorListener
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("Error.Response", volleyError.toString());
            }
        }));
        final Handler handler = new Handler();
        handler.post(new Runnable() { // from class: ui.cart.CartItemActivity.5
            @Override // java.lang.Runnable
            public void run() {
                try {
                    CartItemActivity.this.getTotalPrice();
                } catch (NullPointerException unused) {
                }
                handler.postDelayed(this, 1000L);
            }
        });
        this.mExtraInstructionsText = (EditText) findViewById(R.id.extraInstructionEdiText);
        this.mUserAddressText = (TextView) findViewById(R.id.userDeliveryAddress);
        this.mTotalAmountText = (TextView) findViewById(R.id.totAmount);
        ((Button) findViewById(R.id.payAmountBtn)).setOnClickListener(new View.OnClickListener() { // from class: ui.cart.CartItemActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CartItemActivity.this.m1957lambda$init$2$uicartCartItemActivity(view);
            }
        });
    }

    /* renamed from: lambda$init$0$ui-cart-CartItemActivity  reason: not valid java name */
    public /* synthetic */ void m1955lambda$init$0$uicartCartItemActivity(View view) {
        Intent intent = new Intent(getApplicationContext(), ChangeLocationActivity.class);
        intent.putExtra("INT", "TWO");
        startActivity(intent);
        finish();
    }

    /* renamed from: lambda$init$1$ui-cart-CartItemActivity  reason: not valid java name */
    public /* synthetic */ void m1956lambda$init$1$uicartCartItemActivity(View view) {
        onBackPressed();
    }

    /* renamed from: lambda$init$2$ui-cart-CartItemActivity  reason: not valid java name */
    public /* synthetic */ void m1957lambda$init$2$uicartCartItemActivity(View view) {
        calculateTotalPriceAndMove();
    }

    /* loaded from: classes4.dex */
    public static class CartItemHolder extends RecyclerView.ViewHolder {
//        @BindView(R.id.foodMarkCart)
        ImageView mFoodMarkImg;
//        @BindView(R.id.itemNameCart)
        TextView mItemCartName;
//        @BindView(R.id.itemPriceCart)
        TextView mItemCartPrice;
//        @BindView(R.id.quantityPicker)
        ElegantNumberButton mQtyPicker;

        public CartItemHolder(View view) {
            super(view);
//            ButterKnife.bind(this, view);
            mQtyPicker = view.findViewById(R.id.quantityPicker);
            mItemCartPrice = view.findViewById(R.id.itemPriceCart);
            mItemCartName = view.findViewById(R.id.itemNameCart);
            mFoodMarkImg = view.findViewById(R.id.foodMarkCart);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void moveIfCartEmpty() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    public void getTotalPrice() {
        final ArrayList arrayList = new ArrayList();
        this.queue = Volley.newRequestQueue(this);
        this.queue.add(new JsonArrayRequest(0, "https://jsfoodi.com/Jsfoodi_App/JsFoodi/fetchCart.php?id=" + this.uid, null, new Response.Listener<JSONArray>() { // from class: ui.cart.CartItemActivity.6
            @Override // com.android.volley.Response.Listener
            public void onResponse(JSONArray jSONArray) {
                double d;
                double d2;
                Log.d("Response", jSONArray.toString());
                if (jSONArray.length() <= 0) {
                    CartItemActivity.this.mTotalAmountText.setText("Amount Payable: ₹ 0");
                    CartItemActivity.this.mamountSaved.setText("GST : ₹0 Delivery : ₹0");
                    Button button = (Button) CartItemActivity.this.findViewById(R.id.payAmountBtn);
                    button.setText("Pay ₹0");
                    button.setEnabled(false);
                    ((ImageView) CartItemActivity.this.findViewById(R.id.imageView3)).setVisibility(0);
                }
                for (int i = 0; i < jSONArray.length(); i++) {
                    JSONObject jSONObject = null;
                    try {
                        jSONObject = jSONArray.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    arrayList.add(jSONObject);
                    if (arrayList.size() == 0) {
                        CartItemActivity.this.moveIfCartEmpty();
                    } else {
                        int i2 = 0;
                        for (int i3 = 0; i3 < arrayList.size(); i3++) {
                            try {
                                String string = ((JSONObject) arrayList.get(i3)).getString(FirebaseAnalytics.Param.PRICE);
                                String string2 = ((JSONObject) arrayList.get(i3)).getString("count");
                                CartItemActivity.this.itemsArr.add(((JSONObject) arrayList.get(i3)).getString("name"));
                                CartItemActivity.this.orderedItemsArr.add(((JSONObject) arrayList.get(i3)).getString("count") + " x " + ((JSONObject) arrayList.get(i3)).getString("name"));
                                i2 += Integer.parseInt(string) * Integer.parseInt(string2);
                            } catch (NumberFormatException | JSONException e2) {
                                e2.printStackTrace();
                            }
                        }
                        CartItemActivity.this.mTotalAmountText.setText("Amount Payable: ₹" + i2);
                        if (i2 < 90) {
                            d = (i2 * 5) / 100;
                            d2 = CartItemActivity.this.distanceinKM * 19.0d;
                            CartItemActivity.this.mamountSaved.setText("GST : ₹" + d + " Delivery : ₹" + CartItemActivity.decfor.format(d2));
                        } else {
                            d = (i2 * 5) / 100;
                            d2 = CartItemActivity.this.distanceinKM > 2.2d ? CartItemActivity.this.distanceinKM * 16.0d : 0.0d;
                            CartItemActivity.this.mamountSaved.setText("GST : ₹" + d + " Delivery : ₹" + CartItemActivity.decfor.format(d2));
                        }
                        ((Button) CartItemActivity.this.findViewById(R.id.payAmountBtn)).setText("Pay ₹" + (((int) (d2 + d)) + i2));
                    }
                }
            }
        }, new Response.ErrorListener() { // from class: ui.cart.CartItemActivity.7
            @Override // com.android.volley.Response.ErrorListener
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("Error.Response", volleyError.toString());
                CartItemActivity.this.startActivity(new Intent(CartItemActivity.this.getApplicationContext(), EmptyCartActivity.class));
            }
        }));
    }

    private void calculateTotalPriceAndMove() {
        try {
            this.mCartItemRecyclerView.postDelayed(new Runnable() { // from class: ui.cart.CartItemActivity$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    CartItemActivity.this.m1954lambda$calculateTotalPriceAndMove$3$uicartCartItemActivity();
                }
            }, 5L);
        } catch (NullPointerException unused) {
        }
    }

    /* renamed from: lambda$calculateTotalPriceAndMove$3$ui-cart-CartItemActivity  reason: not valid java name */
    public /* synthetic */ void m1954lambda$calculateTotalPriceAndMove$3$uicartCartItemActivity() {
        double d;
        double d2;
        if (this.mCartItemRecyclerView.getAdapter().getItemCount() == 0) {
            moveIfCartEmpty();
            return;
        }
        RecyclerView.ViewHolder findViewHolderForAdapterPosition = this.mCartItemRecyclerView.findViewHolderForAdapterPosition(0);
        Objects.requireNonNull(findViewHolderForAdapterPosition);
        if (findViewHolderForAdapterPosition.itemView.findViewById(R.id.itemPriceCart) != null) {
            int i = 0;
            int i2 = 0;
            int i3 = 0;
            while (true) {
                RecyclerView.Adapter adapter = this.mCartItemRecyclerView.getAdapter();
                Objects.requireNonNull(adapter);
                if (i >= adapter.getItemCount()) {
                    break;
                }
                RecyclerView.ViewHolder findViewHolderForAdapterPosition2 = this.mCartItemRecyclerView.findViewHolderForAdapterPosition(i);
                Objects.requireNonNull(findViewHolderForAdapterPosition2);
                i2 += Integer.parseInt(((TextView) findViewHolderForAdapterPosition2.itemView.findViewById(R.id.itemPriceCart)).getText().toString().replace("₹ ", ""));
                if (i2 < 90) {
                    d = (i2 * 5) / 100;
                    this.mamountSaved.setText("GST : ₹" + d + " Delivery : ₹19");
                    d2 = 20.0d;
                } else {
                    d = (i2 * 5) / 100;
                    this.mamountSaved.setText("GST : ₹" + d + " Delivery : ₹0");
                    d2 = 0.0d;
                }
                i3 = (int) (d + d2);
                ((Button) findViewById(R.id.payAmountBtn)).setText("Pay ₹" + (i3 + i2));
                i++;

                if(distanceinKM > 4.5){
                    Toast.makeText(CartItemActivity.this, "Hotel is not available for your location", Toast.LENGTH_LONG).show();
                    Button button = (Button) CartItemActivity.this.findViewById(R.id.payAmountBtn);
                    button.setEnabled(false);
                }

            }
            if (!TextUtils.isEmpty(this.mExtraInstructionsText.getText())) {
                this.extraIns = this.mExtraInstructionsText.getText().toString();
            }
            if (this.resSpotImage != null) {
                Intent intent = new Intent(this, CheckoutActivity.class);
                intent.putExtra("TOTAL_AMOUNT", String.valueOf(i2 + i3));
                intent.putExtra("DELVERY_AMOUNT", String.valueOf(i3));
                intent.putExtra("RES_NAME", this.mRestaurantCartName.getText().toString());
                intent.putExtra("RES_UID", this.ruid);
                intent.putExtra("USER_ADDRESS", this.userAddress);
                intent.putExtra("USER_NAME", this.userName);
                intent.putExtra("USER_UID", this.uid);
                intent.putExtra("EXTRA_INS", this.extraIns);
                intent.putExtra("USER_PHONE", this.userPhoneNum);
                intent.putExtra("DELIVERY_TIME", this.resDeliveryTime);
                intent.putExtra("RES_IMAGE", this.resSpotImage);
                intent.addFlags(32768);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        }
    }
}
