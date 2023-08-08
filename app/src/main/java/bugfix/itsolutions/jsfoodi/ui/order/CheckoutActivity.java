package bugfix.itsolutions.jsfoodi.ui.order;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shreyaspatil.EasyUpiPayment.EasyUpiPayment;
import com.shreyaspatil.EasyUpiPayment.listener.PaymentStatusListener;
import com.shreyaspatil.EasyUpiPayment.model.PaymentApp;
import com.shreyaspatil.EasyUpiPayment.model.TransactionDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

import bugfix.itsolutions.jsfoodi.MainActivity;
import bugfix.itsolutions.jsfoodi.R;
import bugfix.itsolutions.jsfoodi.ui.auth.AddInfoActivity;
import bugfix.itsolutions.jsfoodi.utils.GenerateRandomNum;

/* loaded from: classes4.dex */
public class CheckoutActivity extends AppCompatActivity implements View.OnClickListener, PaymentStatusListener {
    private long customerID;
    private FirebaseFirestore db;
    private String extraInst;
    FirebaseUser firebaseUser;
    private LinearLayout mCODView;
    private LinearLayout mCardView;
    private String mDeliveryAmount;
    private String mFAmount;
    private LinearLayout mUpiView;
    private LinearLayout mUpiView1;
    private LinearLayout mUpiView2;
    private LinearLayout mUpiView3;
    private String mid;
    private long orderID;
    private String resDelTime;
    private String resName;
    private String resSpotImage;
    private String resUid;
    private String uid;
    private String userAddress;
    private String userName;
    private String userPhone;
    private String USER_LIST = "UserList";
    private String CART_ITEMS = "CartItems";
    private String USER_ORDERS = "UserOrders";
    private String RES_LIST = "RestaurantList";
    private String RES_ORDERS = "RestaurantOrders";
    ArrayList<String> getItemsArr = new ArrayList<>();
    ArrayList<String> getOrderedItemsArr = new ArrayList<>();
    HashMap<String, String> orderedItemsMap = new HashMap<>();
    ArrayList<JSONObject> list2 = new ArrayList<>();
    ArrayList<JSONObject> list1 = new ArrayList<>();
    ArrayList<JSONObject> list3 = new ArrayList<>();
    private String mTotalAmount;
    String amount = this.mTotalAmount;
    String upi = "Q649171673@ybl";
    String upiID = "Q649171673@ybl";
    String name = "JsFoodi";
    String desc = "Online Food Delivery Services";
    String ServerUploadPath1 = "https://jsfoodi.com/Jsfoodi_App/JsFoodi/addOrders.php";
    String ServerUploadPath = "https://jsfoodi.com/Jsfoodi_App/JsFoodi/deleteCartItemMain.php";

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$onClick$2(DialogInterface dialogInterface, int i) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_checkout);
        this.firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        init();
    }

    private void init() {
        ImageView imageView = (ImageView) findViewById(R.id.cartBackBtn);
        this.resUid = getIntent().getStringExtra("RES_UID");
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        Objects.requireNonNull(currentUser);
        this.uid = currentUser.getUid();
        Volley.newRequestQueue(getApplicationContext()).add(new JsonArrayRequest(0, "https://jsfoodi.com/Jsfoodi_App/JsFoodi/fetchUser.php?id=" + this.uid, null, new Response.Listener<JSONArray>() { // from class: ui.order.CheckoutActivity.1
            @Override // com.android.volley.Response.Listener
            public void onResponse(JSONArray jSONArray) {
                for (int i = 0; i < jSONArray.length(); i++) {
                    JSONObject jSONObject = null;
                    try {
                        jSONObject = jSONArray.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    CheckoutActivity.this.list2.add(jSONObject);
                    try {
                        CheckoutActivity checkoutActivity = CheckoutActivity.this;
                        checkoutActivity.userAddress = checkoutActivity.list2.get(i).getString("address");
                        CheckoutActivity checkoutActivity2 = CheckoutActivity.this;
                        checkoutActivity2.userName = checkoutActivity2.list2.get(i).getString("name");
                        CheckoutActivity checkoutActivity3 = CheckoutActivity.this;
                        checkoutActivity3.userPhone = checkoutActivity3.list2.get(i).getString("phone");
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() { // from class: ui.order.CheckoutActivity.2
            @Override // com.android.volley.Response.ErrorListener
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("Error.Response", volleyError.toString());
            }
        }));
        Volley.newRequestQueue(getApplicationContext()).add(new JsonArrayRequest(0, "https://jsfoodi.com/Jsfoodi_App/JsFoodi/fetchSpecificRestaurant.php?id=" + this.resUid, null, new Response.Listener<JSONArray>() { // from class: ui.order.CheckoutActivity.3
            @Override // com.android.volley.Response.Listener
            public void onResponse(JSONArray jSONArray) {
                for (int i = 0; i < jSONArray.length(); i++) {
                    JSONObject jSONObject = null;
                    try {
                        jSONObject = jSONArray.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    CheckoutActivity.this.list3.add(jSONObject);
                    try {
                        CheckoutActivity checkoutActivity = CheckoutActivity.this;
                        checkoutActivity.resName = checkoutActivity.list3.get(i).getString("name");
                        CheckoutActivity checkoutActivity2 = CheckoutActivity.this;
                        checkoutActivity2.resDelTime = checkoutActivity2.list3.get(i).getString("prepTime");
                        CheckoutActivity checkoutActivity3 = CheckoutActivity.this;
                        checkoutActivity3.resSpotImage = checkoutActivity3.list3.get(i).getString("image");
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() { // from class: ui.order.CheckoutActivity.4
            @Override // com.android.volley.Response.ErrorListener
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("Error.Response", volleyError.toString());
            }
        }));
        Volley.newRequestQueue(getApplicationContext()).add(new JsonArrayRequest(0, "https://jsfoodi.com/Jsfoodi_App/JsFoodi/fetchCart.php?id=" + this.uid, null, new Response.Listener<JSONArray>() { // from class: ui.order.CheckoutActivity.5
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
                    CheckoutActivity.this.list1.add(jSONObject);
                    try {
                        CheckoutActivity.this.getItemsArr.add(CheckoutActivity.this.list1.get(i).getString("name"));
                        ArrayList<String> arrayList = CheckoutActivity.this.getOrderedItemsArr;
                        arrayList.add(CheckoutActivity.this.list1.get(i).getString("count") + " x " + CheckoutActivity.this.list1.get(i).getString("name"));
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() { // from class: ui.order.CheckoutActivity.6
            @Override // com.android.volley.Response.ErrorListener
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("Error.Response", volleyError.toString());
            }
        }));
        this.extraInst = getIntent().getStringExtra("EXTRA_INS");
        this.mTotalAmount = getIntent().getStringExtra("TOTAL_AMOUNT");
        this.mDeliveryAmount = getIntent().getStringExtra("DELVERY_AMOUNT");
        try {
            this.mFAmount = String.valueOf(Integer.parseInt(this.mTotalAmount) - Integer.parseInt(this.mDeliveryAmount));
        } catch (NumberFormatException unused) {
        }
        this.db = FirebaseFirestore.getInstance();
        ((TextView) findViewById(R.id.totalAmountItems)).setText("Amount to be paid ₹" + this.mTotalAmount);
        this.mCODView = (LinearLayout) findViewById(R.id.cashMethodContainer);
        this.mCardView = (LinearLayout) findViewById(R.id.creditCardMethodContainer);
        this.mUpiView = (LinearLayout) findViewById(R.id.upiMethodContainer);
        this.mUpiView1 = (LinearLayout) findViewById(R.id.upiMethodContainer1);
        this.mUpiView2 = (LinearLayout) findViewById(R.id.upiMethodContainer2);
        this.mUpiView3 = (LinearLayout) findViewById(R.id.upiMethodContainer3);
        this.mCODView.setOnClickListener(this);
        this.mCardView.setOnClickListener(this);
        this.mUpiView.setOnClickListener(this);
        this.mUpiView1.setOnClickListener(this);
        this.mUpiView2.setOnClickListener(this);
        this.mUpiView3.setOnClickListener(this);
        this.mUpiView.setVisibility(0);
        this.mid = "YOUR_OWN_MID";
        this.customerID = Long.parseLong(GenerateRandomNum.Companion.generateRandNum());
        this.orderID = Long.parseLong(GenerateRandomNum.Companion.generateRandNum());
        showResPaymentMethods();
        imageView.setOnClickListener(new View.OnClickListener() { // from class: ui.order.CheckoutActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CheckoutActivity.this.m1963lambda$init$0$uiorderCheckoutActivity(view);
            }
        });
    }

    /* renamed from: lambda$init$0$ui-order-CheckoutActivity  reason: not valid java name */
    public /* synthetic */ void m1963lambda$init$0$uiorderCheckoutActivity(View view) {
        onBackPressed();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        String format = new SimpleDateFormat("ddMMyyyyHHmmss", Locale.getDefault()).format(Calendar.getInstance().getTime());
        int id = view.getId();
        if (id == R.id.cashMethodContainer) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("You are sure to Order Cash on Delivery").setMessage("You are sure to Place Cash On delivery 'Order'.\nCancellation Not allowed While COD 100% Food+Delivery Charges are taken on Delivery").setPositiveButton("Place Order on Cash on Delivery", new DialogInterface.OnClickListener() { // from class: ui.order.CheckoutActivity$$ExternalSyntheticLambda0
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    CheckoutActivity.this.m1964lambda$onClick$1$uiorderCheckoutActivity(dialogInterface, i);
                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() { // from class: ui.order.CheckoutActivity$$ExternalSyntheticLambda1
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    CheckoutActivity.lambda$onClick$2(dialogInterface, i);
                }
            });
            builder.show();
            return;
        }
        switch (id) {
            case R.id.upiMethodContainer /* 2131362851 */:
                makePayment(this.amount, this.upi, this.name, this.desc, format);
                return;
            case R.id.upiMethodContainer1 /* 2131362852 */:
                makePayment1(this.amount, this.upi, this.name, this.desc, format);
                return;
            case R.id.upiMethodContainer2 /* 2131362853 */:
                makePayment2(this.amount, this.upi, this.name, this.desc, format);
                return;
            case R.id.upiMethodContainer3 /* 2131362854 */:
                makePayment3(this.amount, this.upi, this.name, this.desc, format);
                return;
            default:
                return;
        }
    }

    /* renamed from: lambda$onClick$1$ui-order-CheckoutActivity  reason: not valid java name */
    public /* synthetic */ void m1964lambda$onClick$1$uiorderCheckoutActivity(DialogInterface dialogInterface, int i) {
        uploadOrderDetails("COD");
    }

    private void showResPaymentMethods() {
        this.mCardView.setVisibility(8);
        this.mCODView.setVisibility(0);
        this.mUpiView.setVisibility(0);
    }

    private void uploadOrderDetails(String str) {
        String format = new SimpleDateFormat("dd MMM yyyy").format(Calendar.getInstance().getTime());
        String format2 = new SimpleDateFormat("hh:mm a").format(Calendar.getInstance().getTime());
        String generateRandNum = GenerateRandomNum.Companion.generateRandNum();
        String format3 = new DecimalFormat("000000").format(new Random().nextInt(999999));
        this.orderedItemsMap.put("ordered_items", String.valueOf(this.getOrderedItemsArr));
        HashMap<String, String> hashMap = this.orderedItemsMap;
        hashMap.put("total_amount", "₹" + this.mFAmount);
        HashMap<String, String> hashMap2 = this.orderedItemsMap;
        hashMap2.put("delivery_amount", "₹" + this.mDeliveryAmount);
        HashMap<String, String> hashMap3 = this.orderedItemsMap;
        hashMap3.put("ordered_time", format + " " + format2);
        this.orderedItemsMap.put("ordered_restaurant_name", this.resName);
        this.orderedItemsMap.put("ruid", this.resUid);
        this.orderedItemsMap.put("ordered_restaurant_spotimage", this.resSpotImage);
        this.orderedItemsMap.put("ordered_restaurant_delivery_time", this.resDelTime);
        this.orderedItemsMap.put("ordered_id", generateRandNum);
        this.orderedItemsMap.put("payment_method", str);
        this.orderedItemsMap.put("otp", format3);
        this.orderedItemsMap.put(NotificationCompat.CATEGORY_STATUS, "Ordered");
        this.orderedItemsMap.put("extra_instructions", this.extraInst);
        this.orderedItemsMap.put("delivery_address", this.userAddress);
        UpdateCartItem();
        deleteItem(this.uid);
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [ui.order.CheckoutActivity$1AsyncTaskUploadClass] */
    public void UpdateCartItem() {
        new AsyncTask<Void, Void, String>() { // from class: ui.order.CheckoutActivity.1AsyncTaskUploadClass
            @Override // android.os.AsyncTask
            protected void onPreExecute() {
                super.onPreExecute();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public void onPostExecute(String str) {
                super.onPostExecute( str);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public String doInBackground(Void... voidArr) {
                AddInfoActivity.ImageProcessClass imageProcessClass = new AddInfoActivity.ImageProcessClass();
                CheckoutActivity.this.orderedItemsMap.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                return imageProcessClass.ImageHttpRequest(CheckoutActivity.this.ServerUploadPath1, CheckoutActivity.this.orderedItemsMap);
            }
        }.execute(new Void[0]);
    }

    /* JADX WARN: Type inference failed for: r2v1, types: [ui.order.CheckoutActivity$2AsyncTaskUploadClass] */
    public void deleteItem(String str) {
        new AsyncTask<Void, Void, String>() { // from class: ui.order.CheckoutActivity.2AsyncTaskUploadClass
            @Override // android.os.AsyncTask
            protected void onPreExecute() {
                super.onPreExecute();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public void onPostExecute(String str2) {
                super.onPostExecute( str2);
                Intent intent = new Intent(CheckoutActivity.this, OrderSuccessfulActivity.class);
                intent.putExtra("RES_UID", CheckoutActivity.this.resUid);
                intent.addFlags(67108864);
                intent.addFlags(67108864);
                CheckoutActivity.this.startActivity(intent);
                CheckoutActivity.this.finish();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public String doInBackground(Void... voidArr) {
                AddInfoActivity.ImageProcessClass imageProcessClass = new AddInfoActivity.ImageProcessClass();
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("update", FirebaseAuth.getInstance().getCurrentUser().getUid());
                return imageProcessClass.ImageHttpRequest(CheckoutActivity.this.ServerUploadPath, hashMap);
            }
        }.execute(new Void[0]);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    private void makePayment(String str, String str2, String str3, String str4, String str5) {
        EasyUpiPayment build = new EasyUpiPayment.Builder().with(this).setPayeeVpa(this.upiID).setPayeeName(this.upiID).setTransactionId(str5).setTransactionRefId(str5).setDescription(str4).setAmount(String.valueOf(Double.parseDouble(this.mTotalAmount))).build();
        build.setDefaultPaymentApp(PaymentApp.GOOGLE_PAY);
        build.startPayment();
        build.setPaymentStatusListener(this);
    }

    private void makePayment1(String str, String str2, String str3, String str4, String str5) {
        EasyUpiPayment build = new EasyUpiPayment.Builder().with(this).setPayeeVpa(this.upiID).setPayeeName(this.upiID).setTransactionId(str5).setTransactionRefId(str5).setDescription(str4).setAmount(String.valueOf(Double.parseDouble(this.mTotalAmount))).build();
        build.setDefaultPaymentApp(PaymentApp.PAYTM);
        build.startPayment();
        build.setPaymentStatusListener(this);
    }

    private void makePayment2(String str, String str2, String str3, String str4, String str5) {
        EasyUpiPayment build = new EasyUpiPayment.Builder().with(this).setPayeeVpa(this.upiID).setPayeeName(this.upiID).setTransactionId(str5).setTransactionRefId(str5).setDescription(str4).setAmount(String.valueOf(Double.parseDouble(this.mTotalAmount))).build();
        build.setDefaultPaymentApp(PaymentApp.AMAZON_PAY);
        build.startPayment();
        build.setPaymentStatusListener(this);
    }

    private void makePayment3(String str, String str2, String str3, String str4, String str5) {
        EasyUpiPayment build = new EasyUpiPayment.Builder().with(this).setPayeeVpa(this.upiID).setPayeeName(this.upiID).setTransactionId(str5).setTransactionRefId(str5).setDescription(str4).setAmount(String.valueOf(Double.parseDouble(this.mTotalAmount))).build();
        build.setDefaultPaymentApp(PaymentApp.BHIM_UPI);
        build.startPayment();
        build.setPaymentStatusListener(this);
    }

    @Override // com.shreyaspatil.EasyUpiPayment.listener.PaymentStatusListener
    public void onTransactionCompleted(TransactionDetails transactionDetails) {
        transactionDetails.getStatus().toString();
        transactionDetails.getApprovalRefNo();
        Toast.makeText(this, "RF : " + transactionDetails.getApprovalRefNo(), 1).show();
    }

    @Override // com.shreyaspatil.EasyUpiPayment.listener.PaymentStatusListener
    public void onTransactionSuccess() {
        Toast.makeText(this, "Transaction successfully completed..", 0).show();
        uploadOrderDetails("UPI");
    }

    @Override // com.shreyaspatil.EasyUpiPayment.listener.PaymentStatusListener
    public void onTransactionSubmitted() {
        Log.e("TAG", "TRANSACTION SUBMIT");
    }

    @Override // com.shreyaspatil.EasyUpiPayment.listener.PaymentStatusListener
    public void onTransactionFailed() {
        Toast.makeText(this, "Failed to complete transaction", 0).show();
    }

    @Override // com.shreyaspatil.EasyUpiPayment.listener.PaymentStatusListener
    public void onTransactionCancelled() {
        Toast.makeText(this, "Transaction cancelled..", 0).show();
    }

    @Override // com.shreyaspatil.EasyUpiPayment.listener.PaymentStatusListener
    public void onAppNotFound() {
        Toast.makeText(this, "No app found for making transaction..", 0).show();
    }
}
