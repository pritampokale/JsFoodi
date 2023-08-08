package bugfix.itsolutions.jsfoodi;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;

import bugfix.itsolutions.jsfoodi.adapters.restaurantListRCAdapter;

/* loaded from: classes.dex */
public class search_service extends AppCompatActivity {
    EditText etSearch;
    restaurantListRCAdapter patientListRCAdapter;
    RecyclerView rc_adapt;
    ArrayList<JSONObject> filterdata = new ArrayList<>();
    private String senttext = "";
    ArrayList<JSONObject> list = new ArrayList<>();
    ArrayList<JSONObject> listd = new ArrayList<>();
    ArrayList<JSONObject> list1 = new ArrayList<>();

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_search_service);
        EditText editText = (EditText) findViewById(R.id.search_service_ac);
        this.etSearch = editText;
        editText.requestFocus();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Volley.newRequestQueue(getApplicationContext()).add(new JsonArrayRequest(0, "https://jsfoodi.com/Jsfoodi_App/JsFoodi/fetchUser.php?id=" + uid, null, new Response.Listener<JSONArray>() { // from class: bugfix.itsolutions.jsfoodi.search_service.1
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
                    search_service.this.list1.add(jSONObject);
                    try {
                        String string = search_service.this.list1.get(i).getString("postalcode");
                        Volley.newRequestQueue(search_service.this.getApplicationContext()).add(new JsonArrayRequest(0, "https://jsfoodi.com/Jsfoodi_App/JsFoodi/fetchUserForS.php?id=" + string, null, new Response.Listener<JSONArray>() { // from class: bugfix.itsolutions.jsfoodi.search_service.1.1
                            @Override // com.android.volley.Response.Listener
                            public void onResponse(JSONArray jSONArray2) {
                                Log.d("Response", jSONArray2.toString());
                                for (int i2 = 0; i2 < jSONArray2.length(); i2++) {
                                    JSONObject jSONObject2 = null;
                                    try {
                                        jSONObject2 = jSONArray2.getJSONObject(i2);
                                    } catch (JSONException e2) {
                                        e2.printStackTrace();
                                    }
                                    search_service.this.listd.add(jSONObject2);
                                    try {
                                        if (search_service.this.listd.get(i2).getString("open").equals("yes") && search_service.this.listd.get(i2).getString("verification").equals("yes")) {
                                            search_service.this.list.add(search_service.this.listd.get(i2));
                                        }
                                    } catch (JSONException e3) {
                                        e3.printStackTrace();
                                    }
                                }
                                HashSet hashSet = new HashSet(search_service.this.list);
                                search_service.this.list.clear();
                                search_service.this.list.addAll(hashSet);
                                Collections.reverse(search_service.this.list);
                                search_service.this.rc_adapt = (RecyclerView) search_service.this.findViewById(R.id.all_services);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(search_service.this);
                                search_service.this.rc_adapt.setLayoutManager(linearLayoutManager);
                                search_service.this.rc_adapt.setHasFixedSize(true);
                                search_service.this.rc_adapt.setItemViewCacheSize(200000000);
                                search_service.this.rc_adapt.setDrawingCacheEnabled(true);
                                search_service.this.rc_adapt.setDrawingCacheQuality(1048576);
                                search_service.this.patientListRCAdapter = new restaurantListRCAdapter(search_service.this, search_service.this.list, "");
                                search_service.this.rc_adapt.addItemDecoration(new DividerItemDecoration(search_service.this.rc_adapt.getContext(), linearLayoutManager.getOrientation()));
                                search_service.this.rc_adapt.setAdapter(search_service.this.patientListRCAdapter);
                            }
                        }, new Response.ErrorListener() { // from class: bugfix.itsolutions.jsfoodi.search_service.1.2
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
        }, new Response.ErrorListener() { // from class: bugfix.itsolutions.jsfoodi.search_service.2
            @Override // com.android.volley.Response.ErrorListener
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("Error.Response", volleyError.toString());
            }
        }));
        this.etSearch.addTextChangedListener(new TextWatcher() { // from class: bugfix.itsolutions.jsfoodi.search_service.3
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                try {
                    search_service.this.filterdata.clear();
                    if (!editable.toString().isEmpty()) {
                        search_service.this.fialter(editable.toString());
                    } else {
                        search_service.this.rc_adapt.setAdapter(new restaurantListRCAdapter(search_service.this.getApplicationContext(), search_service.this.list, ""));
                        search_service.this.patientListRCAdapter.notifyDataSetChanged();
                    }
                } catch (NullPointerException unused) {
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fialter(String str) {
        try {
            Iterator<JSONObject> it = this.list.iterator();
            while (it.hasNext()) {
                JSONObject next = it.next();
                try {
                    if (next.getString("name").toLowerCase().contains(str.toLowerCase()) || next.getString("nameMenu").toLowerCase().contains(str.toLowerCase()) || next.getString("cuisine").toLowerCase().contains(str.toLowerCase()) || next.getString("cuisine").toLowerCase().equals(str.toLowerCase()) || next.getString("name").toLowerCase().equals(str.toLowerCase()) || next.getString("nameMenu").toLowerCase().equals(str.toLowerCase())) {
                        this.filterdata.add(next);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            this.filterdata.isEmpty();
            if (this.filterdata.isEmpty()) {
                this.senttext = str;
            }
            this.rc_adapt.setAdapter(new restaurantListRCAdapter(getApplicationContext(), this.filterdata, str));
            this.patientListRCAdapter.notifyDataSetChanged();
        } catch (NullPointerException unused) {
        }
    }

    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
}
