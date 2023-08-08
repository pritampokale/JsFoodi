package bugfix.itsolutions.jsfoodi.ui.order;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import bugfix.itsolutions.jsfoodi.adapters.orderListRCAdapter;
import bugfix.itsolutions.jsfoodi.R;

/* loaded from: classes4.dex */
public class OrdersActivity extends AppCompatActivity {
    private String USER_LIST = "UserList";
    private String USER_ORDERS = "UserOrders";
    private FirebaseFirestore db;
    orderListRCAdapter expencesListRCAdapter;
    LinearLayoutManager linearLayoutManager;
    private ImageView mGoBackBtn;
    private RecyclerView mOrderItemRecyclerView;
    String resUID;
    private String uid;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_orders);
        init();
    }

    private void init() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        Objects.requireNonNull(currentUser);
        this.uid = currentUser.getUid();
        this.db = FirebaseFirestore.getInstance();
        this.linearLayoutManager = new LinearLayoutManager(this, 1, false);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.orderedItemsRecyclerView);
        this.mOrderItemRecyclerView = recyclerView;
        recyclerView.setLayoutManager(this.linearLayoutManager);
        new ArrayList();
        final ArrayList arrayList = new ArrayList();
        Volley.newRequestQueue(getApplicationContext()).add(new JsonArrayRequest(0, "https://jsfoodi.com/Jsfoodi_App/JsFoodi/fetchOrders.php?id=" + this.uid, null, new Response.Listener<JSONArray>() { // from class: ui.order.OrdersActivity.1
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
                    arrayList.add(jSONObject);
                    RecyclerView recyclerView2 = (RecyclerView) OrdersActivity.this.findViewById(R.id.orderedItemsRecyclerView);
                    recyclerView2.setLayoutManager(new LinearLayoutManager(OrdersActivity.this.getApplicationContext()));
                    OrdersActivity.this.expencesListRCAdapter = new orderListRCAdapter(OrdersActivity.this.getApplicationContext(), arrayList);
                    recyclerView2.setHasFixedSize(true);
                    recyclerView2.setItemViewCacheSize(20);
                    recyclerView2.setDrawingCacheEnabled(true);
                    recyclerView2.setDrawingCacheQuality(1048576);
                    recyclerView2.setAdapter(OrdersActivity.this.expencesListRCAdapter);
                }
            }
        }, new Response.ErrorListener() { // from class: ui.order.OrdersActivity.2
            @Override // com.android.volley.Response.ErrorListener
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("Error.Response", volleyError.toString());
            }
        }));
        ImageView imageView = (ImageView) findViewById(R.id.cartBackBtn);
        this.mGoBackBtn = imageView;
        imageView.setOnClickListener(new View.OnClickListener() { // from class: ui.order.OrdersActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                OrdersActivity.this.m1969lambda$init$0$uiorderOrdersActivity(view);
            }
        });
    }

    /* renamed from: lambda$init$0$ui-order-OrdersActivity  reason: not valid java name */
    public /* synthetic */ void m1969lambda$init$0$uiorderOrdersActivity(View view) {
        onBackPressed();
    }
}
