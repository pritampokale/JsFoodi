package bugfix.itsolutions.jsfoodi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TextView;

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

import bugfix.itsolutions.jsfoodi.adapters.restaurantListRCAdapter;

/* loaded from: classes.dex */
public class catagoryView extends AppCompatActivity {
    String RUID;
    private FirebaseFirestore db;
    String deliveryTime;
    int distanceInMeters;
    restaurantListRCAdapter expencesListRCAdapter;
    LinearLayoutManager linearLayoutManager;
    private FirebaseUser mCurrentUser;
    private RecyclerView mRestaurantRecyclerView;
    String mRestaurantUid;
    TextView mainResName;
    String pinCode;
    int kl = 0;
    ArrayList<JSONObject> list = new ArrayList<>();
    ArrayList<JSONObject> list1 = new ArrayList<>();

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_catagory_view);
        this.mainResName = (TextView) findViewById(R.id.mainResName);
        this.mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        this.db = FirebaseFirestore.getInstance();
        Intent intent = getIntent();
        if (intent != null) {
            this.mRestaurantUid = intent.getStringExtra("catagory");
        }
        this.mainResName.setText(this.mRestaurantUid);
        this.mRestaurantRecyclerView = (RecyclerView) findViewById(R.id.menuItemRecylerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, 0, false);
        this.linearLayoutManager = linearLayoutManager;
        this.mRestaurantRecyclerView.setLayoutManager(linearLayoutManager);
        this.mRestaurantRecyclerView.setHasFixedSize(true);
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        defaultSharedPreferences.edit();
        this.pinCode = defaultSharedPreferences.getString("pinCode", "");
        Volley.newRequestQueue(getApplicationContext()).add(new JsonArrayRequest(0, "https://jsfoodi.com/Jsfoodi_App/JsFoodi/fetchUserForS.php?id=" + this.pinCode, null, new Response.Listener<JSONArray>() { // from class: bugfix.itsolutions.jsfoodi.catagoryView.1
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
                    catagoryView.this.list.add(jSONObject);
                }
                for (int i2 = 0; i2 < catagoryView.this.list.size(); i2++) {
                    try {
                        if (catagoryView.this.list.get(i2).getString("nameCat").contains(catagoryView.this.mRestaurantUid) && catagoryView.this.list.get(i2).getString("open").equals("yes") && catagoryView.this.list.get(i2).getString("verification").equals("yes")) {
                            catagoryView.this.list1.add(catagoryView.this.list.get(i2));
                        }
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                }

                RecyclerView recyclerView = (RecyclerView) catagoryView.this.findViewById(R.id.menuItemRecylerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(catagoryView.this.getApplicationContext()));
                catagoryView.this.expencesListRCAdapter = new restaurantListRCAdapter(catagoryView.this.getApplicationContext(), catagoryView.this.list1, mRestaurantUid);
                recyclerView.setHasFixedSize(true);
                recyclerView.setItemViewCacheSize(20);
                recyclerView.setDrawingCacheEnabled(true);
                recyclerView.setDrawingCacheQuality(1048576);
                recyclerView.setAdapter(catagoryView.this.expencesListRCAdapter);
            }
        }, new Response.ErrorListener() { // from class: bugfix.itsolutions.jsfoodi.catagoryView.2
            @Override // com.android.volley.Response.ErrorListener
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("Error.Response", volleyError.toString());
            }
        }));
    }
}
