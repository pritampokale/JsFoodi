package bugfix.itsolutions.jsfoodi.ui.main;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

import bugfix.itsolutions.jsfoodi.adapters.menueListRCAdapter;
import bugfix.itsolutions.jsfoodi.MainActivity;
import bugfix.itsolutions.jsfoodi.R;
import bugfix.itsolutions.jsfoodi.ui.auth.AddInfoActivity;
import bugfix.itsolutions.jsfoodi.ui.review.ReviewsActivity;

/* loaded from: classes4.dex */
public class MainRestaurantPageActivity extends AppCompatActivity {
    private static NestedScrollView mRootView;
    private FirebaseFirestore db;
    EditText etSearch;
    menueListRCAdapter expencesListRCAdapter;
    LinearLayoutManager linearLayoutManager;
    private LottieAnimationView mFavoriteAnim;
    private RecyclerView mMenuItemRecyclerView;
    private String mResAddressString;
    private String mResCuisineString;
    private String mResDeliveryTime;
    private String mResDistance;
    private String mResImage;
    private String mResName;
    private String mResNum;
    private String mResPrice;
    private String mRestaurantUid;
    RecyclerView rc_hindi;
    private String search;
    private String uid;
    ArrayList<JSONObject> list = new ArrayList<>();
    ArrayList<JSONObject> listf = new ArrayList<>();
    ArrayList<JSONObject> list1 = new ArrayList<>();
    ArrayList<JSONObject> list2 = new ArrayList<>();
    HashMap<String, String> favResMap = new HashMap<>();
    HashMap<String, String> rfavResMap = new HashMap<>();
    ArrayList<JSONObject> filterdata = new ArrayList<>();
    String ServerUploadPath1 = "https://jsfoodi.com/Jsfoodi_App/JsFoodi/addFav.php";
    String ServerUploadPath = "https://jsfoodi.com/Jsfoodi_App/JsFoodi/removeFavRes.php";

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main_restaurant_page);
        this.rc_hindi = (RecyclerView) findViewById(R.id.menuItemRecylerView);
        this.etSearch = (EditText) findViewById(R.id.search_service_ac2);
        Intent intent = getIntent();
        if (intent != null) {
            this.mRestaurantUid = intent.getStringExtra("RUID");
            this.mResName = intent.getStringExtra("NAME");
            this.mResImage = intent.getStringExtra("RES_IMAGE");
            this.search = intent.getStringExtra("RES_SEARCH");
            this.etSearch.setText(" - ");
            this.mResDistance = "50.00";
            final ArrayList arrayList = new ArrayList();
            Volley.newRequestQueue(getApplicationContext()).add(new JsonArrayRequest(0, "https://jsfoodi.com/Jsfoodi_App/JsFoodi/fetchSpecificRestaurant.php?id=" + this.mRestaurantUid, null, new Response.Listener<JSONArray>() { // from class: ui.main.MainRestaurantPageActivity.1
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
                            MainRestaurantPageActivity.this.mResPrice = ((JSONObject) arrayList.get(i)).getString("average_price");
                            MainRestaurantPageActivity.this.mResAddressString = ((JSONObject) arrayList.get(i)).getString("address");
                            MainRestaurantPageActivity.this.mResCuisineString = ((JSONObject) arrayList.get(i)).getString("cuisine");
                            MainRestaurantPageActivity.this.mResDeliveryTime = ((JSONObject) arrayList.get(i)).getString("prepTime");
                            MainRestaurantPageActivity.this.mResNum = ((JSONObject) arrayList.get(i)).getString("phoneNumber");
                        } catch (JSONException e2) {
                            e2.printStackTrace();
                        }
                    }
                }
            }, new Response.ErrorListener() { // from class: ui.main.MainRestaurantPageActivity.2
                @Override // com.android.volley.Response.ErrorListener
                public void onErrorResponse(VolleyError volleyError) {
                    Log.d("Error.Response", volleyError.toString());
                }
            }));
        }
        init();
    }

    private void init() {
        mRootView = (NestedScrollView) findViewById(R.id.content1);
        this.db = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        Objects.requireNonNull(currentUser);
        this.uid = currentUser.getUid();
        final TextView textView = (TextView) findViewById(R.id.mainResName);
        final TextView textView2 = (TextView) findViewById(R.id.mainResCuisine);
        final TextView textView3 = (TextView) findViewById(R.id.mainResAddress);
        TextView textView4 = (TextView) findViewById(R.id.reviewText);
        textView4.setVisibility(8);
        ((ImageView) findViewById(R.id.reviewImage)).setVisibility(8);
        textView4.setOnClickListener(new View.OnClickListener() { // from class: ui.main.MainRestaurantPageActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainRestaurantPageActivity.this.m1960lambda$init$0$uimainMainRestaurantPageActivity(view);
            }
        });
        this.mFavoriteAnim = (LottieAnimationView) findViewById(R.id.favoriteAnim);
        checkFavRes();
        this.mFavoriteAnim.setOnClickListener(new View.OnClickListener() { // from class: ui.main.MainRestaurantPageActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainRestaurantPageActivity.this.m1961lambda$init$1$uimainMainRestaurantPageActivity(view);
            }
        });
        final TextView textView5 = (TextView) findViewById(R.id.restaurantAvgPrice);
        final TextView textView6 = (TextView) findViewById(R.id.restaurantDeliveryTime);
        ((TextView) findViewById(R.id.restaurantTitleToolbar)).setText(this.mResName);
        textView.setText(this.mResName);
        ((ImageView) findViewById(R.id.backBtn)).setOnClickListener(new View.OnClickListener() { // from class: ui.main.MainRestaurantPageActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainRestaurantPageActivity.this.m1962lambda$init$2$uimainMainRestaurantPageActivity(view);
            }
        });
        Volley.newRequestQueue(getApplicationContext()).add(new JsonArrayRequest(0, "https://jsfoodi.com/Jsfoodi_App/JsFoodi/fetchSpecificRestaurant.php?id=" + this.mRestaurantUid, null, new Response.Listener<JSONArray>() { // from class: ui.main.MainRestaurantPageActivity.3
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
                    MainRestaurantPageActivity.this.list2.add(jSONObject);
                    try {
                        textView.setText(MainRestaurantPageActivity.this.list2.get(i).getString("name"));
                        textView3.setText(MainRestaurantPageActivity.this.list2.get(i).getString("address"));
                        TextView textView7 = textView2;
                        textView7.setText("FSSAI Licence : " + MainRestaurantPageActivity.this.list2.get(i).getString("licence"));
                        TextView textView8 = textView5;
                        textView8.setText("₹" + MainRestaurantPageActivity.this.list2.get(i).getString("average_price"));
                        TextView textView9 = textView6;
                        textView9.setText(MainRestaurantPageActivity.this.list2.get(i).getString("prepTime") + "");
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() { // from class: ui.main.MainRestaurantPageActivity.4
            @Override // com.android.volley.Response.ErrorListener
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("Error.Response", volleyError.toString());
            }
        }));
        Volley.newRequestQueue(getApplicationContext()).add(new JsonArrayRequest(0, "https://jsfoodi.com/Jsfoodi_App/JsFoodi/fetchCart.php?id=" + this.uid, null, new Response.Listener<JSONArray>() { // from class: ui.main.MainRestaurantPageActivity.5
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
                    MainRestaurantPageActivity.this.list1.add(jSONObject);
                }
            }
        }, new Response.ErrorListener() { // from class: ui.main.MainRestaurantPageActivity.6
            @Override // com.android.volley.Response.ErrorListener
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("Error.Response", volleyError.toString());
            }
        }));
        Volley.newRequestQueue(getApplicationContext()).add(new JsonArrayRequest(0, "https://jsfoodi.com/Jsfoodi_App/JsFoodi/fetchMenue.php?id=" + this.mRestaurantUid, null, new Response.Listener<JSONArray>() { // from class: ui.main.MainRestaurantPageActivity.7
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
                    MainRestaurantPageActivity.this.list.add(jSONObject);
                    try {
                        if (MainRestaurantPageActivity.this.list.get(i).getString("is_active") == "yes") {
                            MainRestaurantPageActivity.this.listf.add(MainRestaurantPageActivity.this.list.get(i));
                        }
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                }
                final RecyclerView recyclerView = (RecyclerView) MainRestaurantPageActivity.this.findViewById(R.id.menuItemRecylerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainRestaurantPageActivity.this.getApplicationContext()));
                MainRestaurantPageActivity.this.expencesListRCAdapter = new menueListRCAdapter(MainRestaurantPageActivity.this.getApplicationContext(), MainRestaurantPageActivity.this.listf, MainRestaurantPageActivity.this.list1);
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(MainRestaurantPageActivity.this.expencesListRCAdapter);
                try {
                    MainRestaurantPageActivity.this.etSearch.setText(MainRestaurantPageActivity.this.search);
                    MainRestaurantPageActivity mainRestaurantPageActivity = MainRestaurantPageActivity.this;
                    mainRestaurantPageActivity.fialter(mainRestaurantPageActivity.search, MainRestaurantPageActivity.this.list1);
                } catch (NullPointerException unused) {
                }
                MainRestaurantPageActivity.this.etSearch.addTextChangedListener(new TextWatcher() { // from class: ui.main.MainRestaurantPageActivity.7.1
                    @Override // android.text.TextWatcher
                    public void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
                    }

                    @Override // android.text.TextWatcher
                    public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
                    }

                    @Override // android.text.TextWatcher
                    public void afterTextChanged(Editable editable) {
                        MainRestaurantPageActivity.this.filterdata.clear();
                        if (!editable.toString().isEmpty()) {
                            MainRestaurantPageActivity.this.fialter(editable.toString(), MainRestaurantPageActivity.this.list1);
                            return;
                        }
                        recyclerView.setAdapter(new menueListRCAdapter(MainRestaurantPageActivity.this.getApplicationContext(), MainRestaurantPageActivity.this.listf, MainRestaurantPageActivity.this.list1));
                        MainRestaurantPageActivity.this.expencesListRCAdapter.notifyDataSetChanged();
                    }
                });
            }
        }, new Response.ErrorListener() { // from class: ui.main.MainRestaurantPageActivity.8
            @Override // com.android.volley.Response.ErrorListener
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("Error.Response", volleyError.toString());
            }
        }));
    }

    /* renamed from: lambda$init$0$ui-main-MainRestaurantPageActivity  reason: not valid java name */
    public /* synthetic */ void m1960lambda$init$0$uimainMainRestaurantPageActivity(View view) {
        Intent intent = new Intent(this, ReviewsActivity.class);
        intent.putExtra("RUID", this.mRestaurantUid);
        intent.putExtra("NAME", this.mResName);
        intent.putExtra("PRICE", this.mResPrice);
        intent.putExtra("NUM", this.mResNum);
        startActivity(intent);
    }

    /* renamed from: lambda$init$1$ui-main-MainRestaurantPageActivity  reason: not valid java name */
    public /* synthetic */ void m1961lambda$init$1$uimainMainRestaurantPageActivity(View view) {
        if (this.mFavoriteAnim.getProgress() >= 0.1f) {
            this.mFavoriteAnim.setSpeed(-1.0f);
            this.mFavoriteAnim.playAnimation();
            delFavRes();
        } else if (this.mFavoriteAnim.getProgress() == 0.0f) {
            this.mFavoriteAnim.setSpeed(1.0f);
            this.mFavoriteAnim.playAnimation();
            favRes();
        }
    }

    /* renamed from: lambda$init$2$ui-main-MainRestaurantPageActivity  reason: not valid java name */
    public /* synthetic */ void m1962lambda$init$2$uimainMainRestaurantPageActivity(View view) {
        onBackPressed();
        overridePendingTransition(0, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fialter(String str, ArrayList<JSONObject> arrayList) {
        Iterator<JSONObject> it = this.list.iterator();
        while (it.hasNext()) {
            JSONObject next = it.next();
            try {
                if (next.getString("name").toLowerCase().contains(str.toLowerCase()) || next.getString("category").toLowerCase().contains(str.toLowerCase())) {
                    this.filterdata.add(next);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        this.filterdata.isEmpty();
        this.filterdata.isEmpty();
        this.rc_hindi.setAdapter(new menueListRCAdapter(getApplicationContext(), this.filterdata, arrayList));
        try {
            this.expencesListRCAdapter.notifyDataSetChanged();
        } catch (NullPointerException unused) {
        }
    }

    private void checkFavRes() {
        final ArrayList arrayList = new ArrayList();
        Volley.newRequestQueue(getApplicationContext()).add(new JsonArrayRequest(0, "https://jsfoodi.com/Jsfoodi_App/JsFoodi/fetchFavRestaurant.php?id=" + this.uid, null, new Response.Listener<JSONArray>() { // from class: ui.main.MainRestaurantPageActivity.9
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
                        if (!((JSONObject) arrayList.get(i)).getString("ruid").equals(MainRestaurantPageActivity.this.mRestaurantUid)) {
                            MainRestaurantPageActivity.this.mFavoriteAnim.setProgress(0.0f);
                        } else {
                            MainRestaurantPageActivity.this.mFavoriteAnim.setProgress(0.1f);
                            MainRestaurantPageActivity.this.mFavoriteAnim.resumeAnimation();
                        }
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() { // from class: ui.main.MainRestaurantPageActivity.10
            @Override // com.android.volley.Response.ErrorListener
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("Error.Response", volleyError.toString());
            }
        }));
    }

    private void favRes() {
        this.favResMap.put("ruid", this.mRestaurantUid);
        this.favResMap.put("name", this.mResName);
        this.favResMap.put("image", this.mResImage);
        this.favResMap.put("distance", this.mResDistance);
        this.favResMap.put(FirebaseAnalytics.Param.PRICE, this.mResPrice);
        this.favResMap.put("address", this.mResAddressString);
        this.favResMap.put("cuisine", this.mResCuisineString);
        this.favResMap.put("time", this.mResDeliveryTime);
        this.favResMap.put("phone", this.mResNum);
        AddFav();
    }

    private void delFavRes() {
        RemoveFav();
        Toast.makeText(this, "Removed successfully ", 0).show();
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [ui.main.MainRestaurantPageActivity$1AsyncTaskUploadClass] */
    public void AddFav() {
        new AsyncTask<Void, Void, String>() { // from class: ui.main.MainRestaurantPageActivity.1AsyncTaskUploadClass
            @Override // android.os.AsyncTask
            protected void onPreExecute() {
                super.onPreExecute();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public void onPostExecute(String str) {
                super.onPostExecute( str);
                str.equals("Added Succesfull.");
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public String doInBackground(Void... voidArr) {
                AddInfoActivity.ImageProcessClass imageProcessClass = new AddInfoActivity.ImageProcessClass();
                MainRestaurantPageActivity.this.favResMap.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                return imageProcessClass.ImageHttpRequest(MainRestaurantPageActivity.this.ServerUploadPath1, MainRestaurantPageActivity.this.favResMap);
            }
        }.execute(new Void[0]);
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [ui.main.MainRestaurantPageActivity$2AsyncTaskUploadClass] */
    public void RemoveFav() {
        new AsyncTask<Void, Void, String>() { // from class: ui.main.MainRestaurantPageActivity.2AsyncTaskUploadClass
            @Override // android.os.AsyncTask
            protected void onPreExecute() {
                super.onPreExecute();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public void onPostExecute(String str) {
                super.onPostExecute( str);
                str.equals("Added Succesfull.");
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public String doInBackground(Void... voidArr) {
                AddInfoActivity.ImageProcessClass imageProcessClass = new AddInfoActivity.ImageProcessClass();
                MainRestaurantPageActivity.this.rfavResMap.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                MainRestaurantPageActivity.this.rfavResMap.put("name", MainRestaurantPageActivity.this.mRestaurantUid);
                return imageProcessClass.ImageHttpRequest(MainRestaurantPageActivity.this.ServerUploadPath, MainRestaurantPageActivity.this.rfavResMap);
            }
        }.execute(new Void[0]);
    }
}
