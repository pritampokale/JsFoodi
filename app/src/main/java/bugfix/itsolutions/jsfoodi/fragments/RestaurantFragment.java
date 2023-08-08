package bugfix.itsolutions.jsfoodi.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

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
import java.util.HashSet;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import bugfix.itsolutions.jsfoodi.adapters.CustomPagerAdapter;
import bugfix.itsolutions.jsfoodi.adapters.cussineListRCAdapter;
import bugfix.itsolutions.jsfoodi.adapters.restaurantListRCAdapter;
import bugfix.itsolutions.jsfoodi.R;
import ru.nikartm.support.ImageBadgeView;
import bugfix.itsolutions.jsfoodi.ui.cart.CartItemActivity;
import bugfix.itsolutions.jsfoodi.ui.cart.EmptyCartActivity;
import bugfix.itsolutions.jsfoodi.ui.location.ChangeLocationActivity;

/* loaded from: classes3.dex */
public class RestaurantFragment extends Fragment {
    String RUID;
    private String Ruid;
    private String address;
    cussineListRCAdapter cussineListRCAdapter;
    private FirebaseFirestore db;
    String deliveryTime;
    int distanceInMeters;
    restaurantListRCAdapter expencesListRCAdapter;
    private String latitude;
    LinearLayoutManager linearLayoutManager;
    private String longitude;
    private FirebaseUser mCurrentUser;
    private ImageBadgeView mImageBadgeView;
    private RecyclerView mRestaurantRecyclerView;
    Double mUserLat;
    Double mUserLong;
    private String pinCode;
    Timer timer;
    ViewPager viewPager;
    final long DELAY_MS = 800;
    final long PERIOD_MS = 4000;
    ArrayList<JSONObject> list = new ArrayList<>();
    ArrayList<JSONObject> listf = new ArrayList<>();
    ArrayList<JSONObject> listd = new ArrayList<>();
    ArrayList<JSONObject> list1 = new ArrayList<>();
    ArrayList<JSONObject> list3 = new ArrayList<>();
    List<String> photoUrls = new ArrayList();
    int currentPage = 0;
    ArrayList<String> listFruitNames = new ArrayList<>();
    ArrayList<String> listFruitImages = new ArrayList<>();

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_restaurant, viewGroup, false);
        init(inflate);
        fetchLocation(inflate);
        getItemsInCartNo();
        return inflate;
    }

    private void init(final View view) {
        ((LinearLayout) view.findViewById(R.id.addressContainer)).setOnClickListener(new View.OnClickListener() { // from class: fragments.RestaurantFragment$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                RestaurantFragment.this.m375lambda$init$0$fragmentsRestaurantFragment(view2);
            }
        });
        try {
            final ArrayList arrayList = new ArrayList();
            Volley.newRequestQueue(view.getContext()).add(new JsonArrayRequest(0, "https://jsfoodi.com/Jsfoodi_App/JsFoodi/fetchBanner.php", null, new Response.Listener<JSONArray>() { // from class: fragments.RestaurantFragment.1
                @Override // com.android.volley.Response.Listener
                public void onResponse(JSONArray jSONArray) {
                    Log.d("Response", jSONArray.toString());
                    RestaurantFragment.this.photoUrls.clear();
                    for (int i = 0; i < jSONArray.length(); i++) {
                        JSONObject jSONObject = null;
                        try {
                            jSONObject = jSONArray.getJSONObject(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        arrayList.add(jSONObject);
                        try {
                            RestaurantFragment.this.photoUrls.add(((JSONObject) arrayList.get(i)).getString("image"));
                        } catch (JSONException e2) {
                            e2.printStackTrace();
                        }
                    }
                    RestaurantFragment.this.viewPager = (ViewPager) view.findViewById(R.id.vFliper);
                    if (RestaurantFragment.this.viewPager != null) {
                        RestaurantFragment.this.viewPager.setAdapter(new CustomPagerAdapter(view.getContext(), RestaurantFragment.this.photoUrls));
                        final Handler handler = new Handler();
                        final Runnable runnable = new Runnable() { // from class: fragments.RestaurantFragment.1.1
                            @Override // java.lang.Runnable
                            public void run() {
                                if (RestaurantFragment.this.currentPage == RestaurantFragment.this.photoUrls.size()) {
                                    RestaurantFragment.this.currentPage = 0;
                                }
                                ViewPager viewPager = RestaurantFragment.this.viewPager;
                                RestaurantFragment restaurantFragment = RestaurantFragment.this;
                                int i2 = restaurantFragment.currentPage;
                                restaurantFragment.currentPage = i2 + 1;
                                viewPager.setCurrentItem(i2, true);
                            }
                        };
                        RestaurantFragment.this.timer = new Timer();
                        RestaurantFragment.this.timer.schedule(new TimerTask() { // from class: fragments.RestaurantFragment.1.2
                            @Override // java.util.TimerTask, java.lang.Runnable
                            public void run() {
                                handler.post(runnable);
                            }
                        }, 800L, 4000L);
                    }
                }
            }, new Response.ErrorListener() { // from class: fragments.RestaurantFragment.2
                @Override // com.android.volley.Response.ErrorListener
                public void onErrorResponse(VolleyError volleyError) {
                    Log.d("Error.Response", volleyError.toString());
                }
            }));
        } catch (NullPointerException unused) {
        }
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        this.mCurrentUser = currentUser;
        this.Ruid = currentUser.getUid();

        Log.e("dsadas","dasad:-  " + Ruid);
        this.db = FirebaseFirestore.getInstance();
        ((AppCompatActivity) requireActivity()).setSupportActionBar((Toolbar) view.findViewById(R.id.customToolBar));
        try {
            Volley.newRequestQueue(view.getContext()).add(new JsonArrayRequest(0, "https://jsfoodi.com/Jsfoodi_App/JsFoodi/fetchCussinesList.php", null, new Response.Listener<JSONArray>() { // from class: fragments.RestaurantFragment.3
                @Override // com.android.volley.Response.Listener
                public void onResponse(JSONArray jSONArray) {
                    Log.d("Response", jSONArray.toString());
                    RestaurantFragment.this.listd.clear();
                    for (int i = 0; i < 20; i++) {
                        JSONObject jSONObject = null;
                        try {
                            jSONObject = jSONArray.getJSONObject(i);
                        } catch (NullPointerException | JSONException e) {
                            e.printStackTrace();
                        }
                        RestaurantFragment.this.listd.add(jSONObject);
                        try {
                            RestaurantFragment.this.listFruitNames.add(RestaurantFragment.this.listd.get(i).getString("name"));
                            RestaurantFragment.this.listFruitImages.add(RestaurantFragment.this.listd.get(i).getString("image"));
                        } catch (NullPointerException | JSONException e2) {
                            e2.printStackTrace();
                        }
                    }
                    String[] strArr = new String[RestaurantFragment.this.listFruitNames.size()];
                    RestaurantFragment.this.listFruitNames.toArray(strArr);
                    String[] strArr2 = new String[RestaurantFragment.this.listFruitImages.size()];
                    RestaurantFragment.this.listFruitImages.toArray(strArr2);
                    RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.cuisineGridView);
                    recyclerView.setLayoutManager(new GridLayoutManager(RestaurantFragment.this.getContext(), 2, 0, false));
                    RestaurantFragment.this.cussineListRCAdapter = new cussineListRCAdapter(RestaurantFragment.this.getActivity(), strArr, strArr2);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(RestaurantFragment.this.cussineListRCAdapter);
                }
            }, new Response.ErrorListener() { // from class: fragments.RestaurantFragment.4
                @Override // com.android.volley.Response.ErrorListener
                public void onErrorResponse(VolleyError volleyError) {
                    Log.d("Error.Response", volleyError.toString());
                }
            }));
        } catch (NullPointerException unused2) {
        }
        try {
            this.mRestaurantRecyclerView = (RecyclerView) view.findViewById(R.id.restaurant_recyclerView);
            Volley.newRequestQueue(requireContext()).add(new JsonArrayRequest(0, "https://jsfoodi.com/Jsfoodi_App/JsFoodi/fetchUser.php?id=" + this.Ruid, null, new Response.Listener<JSONArray>() { // from class: fragments.RestaurantFragment.5
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
                        RestaurantFragment.this.list1.add(jSONObject);
                        try {
                            RestaurantFragment restaurantFragment = RestaurantFragment.this;
                            restaurantFragment.pinCode = restaurantFragment.list1.get(i).getString("postalcode");
                            RestaurantFragment restaurantFragment2 = RestaurantFragment.this;
                            restaurantFragment2.latitude = restaurantFragment2.list1.get(i).getString("latitude");
                            RestaurantFragment restaurantFragment3 = RestaurantFragment.this;
                            restaurantFragment3.longitude = restaurantFragment3.list1.get(i).getString("longitude");
                            if (RestaurantFragment.this.latitude.isEmpty() || RestaurantFragment.this.latitude.equals("null") || RestaurantFragment.this.latitude.equals("no")) {
                                Intent intent = new Intent(RestaurantFragment.this.getContext(), ChangeLocationActivity.class);
                                intent.addFlags(67108864);
                                intent.addFlags(32768);
                                intent.putExtra("INT", "THREE");
                                RestaurantFragment.this.startActivity(intent);
                            }
                            SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(RestaurantFragment.this.getContext()).edit();
                            edit.putString("pinCode", RestaurantFragment.this.pinCode);
                            edit.putString("latitude", RestaurantFragment.this.latitude);
                            edit.putString("longitude", RestaurantFragment.this.longitude);
                            edit.apply();
                            RestaurantFragment restaurantFragment4 = RestaurantFragment.this;
                            restaurantFragment4.address = restaurantFragment4.list1.get(i).getString("address");
                            ((TextView) view.findViewById(R.id.userLocation)).setText(RestaurantFragment.this.address);
                            Volley.newRequestQueue(view.getContext()).add(new JsonArrayRequest(0, "https://jsfoodi.com/Jsfoodi_App/JsFoodi/fetchRestaurant.php?id=" + RestaurantFragment.this.pinCode, null, new Response.Listener<JSONArray>() { // from class: fragments.RestaurantFragment.5.1
                                @Override // com.android.volley.Response.Listener
                                public void onResponse(JSONArray jSONArray2) {
                                    Log.d("Response", jSONArray2.toString());
                                    RestaurantFragment.this.list.clear();
                                    for (int i2 = 0; i2 < jSONArray2.length(); i2++) {
                                        JSONObject jSONObject2 = null;
                                        try {
                                            jSONObject2 = jSONArray2.getJSONObject(i2);
                                        } catch (JSONException e2) {
                                            e2.printStackTrace();
                                        }
                                        RestaurantFragment.this.listf.add(jSONObject2);
                                        try {
                                            if (RestaurantFragment.this.listf.get(i2).getString("verification").equals("Yes") || RestaurantFragment.this.listf.get(i2).getString("verification").equals("yes")) {
                                                RestaurantFragment.this.list.add(RestaurantFragment.this.listf.get(i2));
                                            }
                                        } catch (JSONException e3) {
                                            e3.printStackTrace();
                                        }
                                    }
                                    HashSet hashSet = new HashSet(RestaurantFragment.this.list);
                                    RestaurantFragment.this.list.clear();
                                    RestaurantFragment.this.list.addAll(hashSet);
                                    RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.restaurant_recyclerView);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(RestaurantFragment.this.getActivity()));
                                    RestaurantFragment.this.expencesListRCAdapter = new restaurantListRCAdapter(RestaurantFragment.this.getActivity(), RestaurantFragment.this.list, "");
                                    recyclerView.setHasFixedSize(true);
                                    recyclerView.setAdapter(RestaurantFragment.this.expencesListRCAdapter);
                                }
                            }, new Response.ErrorListener() { // from class: fragments.RestaurantFragment.5.2
                                @Override // com.android.volley.Response.ErrorListener
                                public void onErrorResponse(VolleyError volleyError) {
                                    Log.d("Error.Response", volleyError.toString());
                                }
                            }));
                        } catch (NullPointerException | JSONException e2) {
                            e2.printStackTrace();
                        }
                    }
                }
            }, new Response.ErrorListener() { // from class: fragments.RestaurantFragment.6
                @Override // com.android.volley.Response.ErrorListener
                public void onErrorResponse(VolleyError volleyError) {
                    Log.d("Error.Response", volleyError.toString());
                }
            }));
        } catch (NullPointerException unused3) {
        }
        this.mImageBadgeView = (ImageBadgeView) view.findViewById(R.id.imageBadgeView);
    }

    /* renamed from: lambda$init$0$fragments-RestaurantFragment  reason: not valid java name */
    public /* synthetic */ void m375lambda$init$0$fragmentsRestaurantFragment(View view) {
        Intent intent = new Intent(getActivity(), ChangeLocationActivity.class);
        intent.putExtra("INT", "ONE");
        startActivity(intent);
    }

    private void fetchLocation(View view) {
        if (this.mCurrentUser != null) {
            ((TextView) view.findViewById(R.id.userLocation)).setText(this.address);
        }
    }

    private void getItemsInCartNo() {
        try {
            Volley.newRequestQueue(requireContext()).add(new JsonArrayRequest(0, "https://jsfoodi.com/Jsfoodi_App/JsFoodi/fetchCart.php?id=" + this.mCurrentUser.getUid(), null, new AnonymousClass7(), new Response.ErrorListener() { // from class: fragments.RestaurantFragment.8
                @Override // com.android.volley.Response.ErrorListener
                public void onErrorResponse(VolleyError volleyError) {
                    Log.d("Error.Response", volleyError.toString());
                }
            }));
        } catch (NullPointerException unused) {

        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: fragments.RestaurantFragment$7  reason: invalid class name */
    /* loaded from: classes3.dex */
    public class AnonymousClass7 implements Response.Listener<JSONArray> {
        AnonymousClass7() {
        }

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
                RestaurantFragment.this.list3.add(jSONObject);
            }
            RestaurantFragment.this.mImageBadgeView.setBadgeValue(RestaurantFragment.this.list3.size());
            RestaurantFragment.this.mImageBadgeView.setOnClickListener(new View.OnClickListener() { // from class: fragments.RestaurantFragment$7$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    AnonymousClass7.this.m376lambda$onResponse$0$fragmentsRestaurantFragment$7(view);
                }
            });
        }

        /* renamed from: lambda$onResponse$0$fragments-RestaurantFragment$7  reason: not valid java name */
        public /* synthetic */ void m376lambda$onResponse$0$fragmentsRestaurantFragment$7(View view) {
            if (RestaurantFragment.this.mImageBadgeView.getBadgeValue() != 0) {
                RestaurantFragment.this.sendUserToCheckOut();
            } else {
                RestaurantFragment.this.sendUserToEmptyCart();
            }
        }
    }

    public void sendUserToCheckOut() {
        startActivity(new Intent(getActivity(), CartItemActivity.class));
        requireActivity().overridePendingTransition(0, 0);
    }

    public void sendUserToEmptyCart() {
        startActivity(new Intent(getActivity(), EmptyCartActivity.class));
        requireActivity().overridePendingTransition(0, 0);
    }
}
