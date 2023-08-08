package bugfix.itsolutions.jsfoodi.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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

import bugfix.itsolutions.jsfoodi.adapters.favListRCAdapter;
import bugfix.itsolutions.jsfoodi.R;

/* loaded from: classes3.dex */
public class FavouriteFragment extends Fragment {
    private FirebaseFirestore db;
    favListRCAdapter expencesListRCAdapter;
    LinearLayoutManager linearLayoutManager;
    private RecyclerView mFavoriteResRecyclerView;
    private String uid;
    private View view;

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.view = layoutInflater.inflate(R.layout.fragment_favourite, viewGroup, false);
        init();
        return this.view;
    }

    private void init() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        Objects.requireNonNull(currentUser);
        this.uid = currentUser.getUid();
        ((ImageView) this.view.findViewById(R.id.cartBackBtn)).setOnClickListener(new View.OnClickListener() { // from class: fragments.FavouriteFragment$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                FavouriteFragment.this.m373lambda$init$0$fragmentsFavouriteFragment(view);
            }
        });
        ((TextView) this.view.findViewById(R.id.confirmOrderText)).setText("Your Favorite Restaurants");
        this.db = FirebaseFirestore.getInstance();
        this.mFavoriteResRecyclerView = (RecyclerView) this.view.findViewById(R.id.favoriteResRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), 1, false);
        this.linearLayoutManager = linearLayoutManager;
        this.mFavoriteResRecyclerView.setLayoutManager(linearLayoutManager);
        final ArrayList arrayList = new ArrayList();
        Volley.newRequestQueue(getContext()).add(new JsonArrayRequest(0, "https://jsfoodi.com/Jsfoodi_App/JsFoodi/fetchFavRestaurant.php?id=" + this.uid, null, new Response.Listener<JSONArray>() { // from class: fragments.FavouriteFragment.1
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
                    RecyclerView recyclerView = (RecyclerView) FavouriteFragment.this.view.findViewById(R.id.favoriteResRecyclerView);
                    recyclerView.setLayoutManager(new LinearLayoutManager(FavouriteFragment.this.getContext()));
                    FavouriteFragment.this.expencesListRCAdapter = new favListRCAdapter(FavouriteFragment.this.getContext(), arrayList);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setItemViewCacheSize(20);
                    recyclerView.setDrawingCacheEnabled(true);
                    recyclerView.setDrawingCacheQuality(1048576);
                    recyclerView.setAdapter(FavouriteFragment.this.expencesListRCAdapter);
                }
            }
        }, new Response.ErrorListener() { // from class: fragments.FavouriteFragment.2
            @Override // com.android.volley.Response.ErrorListener
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("Error.Response", volleyError.toString());
            }
        }));
    }

    /* renamed from: lambda$init$0$fragments-FavouriteFragment  reason: not valid java name */
    public /* synthetic */ void m373lambda$init$0$fragmentsFavouriteFragment(View view) {
        RestaurantFragment restaurantFragment = new RestaurantFragment();
        FragmentTransaction beginTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
        beginTransaction.replace(R.id.fragmentContainer, restaurantFragment);
        beginTransaction.addToBackStack(null);
        beginTransaction.commit();
    }
}
