package bugfix.itsolutions.jsfoodi.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.myhexaville.smartimagepicker.ImagePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import bugfix.itsolutions.jsfoodi.adapters.orderListRCAdapter;
import bugfix.itsolutions.jsfoodi.R;
import de.hdodenhof.circleimageview.CircleImageView;
import bugfix.itsolutions.jsfoodi.ui.auth.LoginActivity;
import bugfix.itsolutions.jsfoodi.ui.cart.CartItemActivity;
import bugfix.itsolutions.jsfoodi.ui.location.ChangeLocationActivity;
import bugfix.itsolutions.jsfoodi.ui.order.OrdersActivity;

/* loaded from: classes3.dex */
public class MyProfileFragment extends Fragment implements View.OnClickListener {
    private FirebaseFirestore db;
    orderListRCAdapter expencesListRCAdapter;
    private StorageReference filePath;
    private ImagePicker imagePicker;
    private FirebaseAuth mAuth;
    private ImageView mChangeAddressView;
    Uri mImageUri;
    private TextView mLogOutText;
    private ImageView mMyOrdersText;
    private ProgressDialog mProgressDialog;
    private StorageReference mUserImageRef;
    private TextView mUserProfileAddress;
    private CircleImageView mUserProfileImage;
    private TextView mUserProfileName;
    private TextView mUserProfileNum;
    private DocumentReference mUserRef;
    private ImageView meditProfile;
    private String uid;
    private View view;

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.view = layoutInflater.inflate(R.layout.fragment_my_profile, viewGroup, false);
        init();
        this.mLogOutText.setOnClickListener(this);
        this.mMyOrdersText.setOnClickListener(this);
        this.mUserProfileImage.setOnClickListener(this);
        this.mChangeAddressView.setOnClickListener(this);
        this.meditProfile.setOnClickListener(this);
        return this.view;
    }

    private void init() {
        this.mProgressDialog = new ProgressDialog(getContext());
        this.db = FirebaseFirestore.getInstance();
        this.mLogOutText = (TextView) this.view.findViewById(R.id.logOutText);
        this.mUserProfileName = (TextView) this.view.findViewById(R.id.userProfileName);
        this.mUserProfileNum = (TextView) this.view.findViewById(R.id.userProfileNumber);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        this.mAuth = firebaseAuth;
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        Objects.requireNonNull(currentUser);
        this.uid = currentUser.getUid();
        this.mMyOrdersText = (ImageView) this.view.findViewById(R.id.myOrdersImage);
        this.mChangeAddressView = (ImageView) this.view.findViewById(R.id.changeAddressImage);
        this.meditProfile = (ImageView) this.view.findViewById(R.id.editProfile);
        this.mUserProfileAddress = (TextView) this.view.findViewById(R.id.userProfileAddress);
        this.mUserProfileImage = (CircleImageView) this.view.findViewById(R.id.userProfileImage);
        this.mUserImageRef = FirebaseStorage.getInstance().getReference();
        this.mUserRef = this.db.collection("UserList").document(this.uid);
        final ArrayList arrayList = new ArrayList();
        final ArrayList arrayList2 = new ArrayList();
        Volley.newRequestQueue(this.view.getContext()).add(new JsonArrayRequest(0, "https://jsfoodi.com/Jsfoodi_App/JsFoodi/fetchOrders.php?id=" + this.uid, null, new Response.Listener<JSONArray>() { // from class: fragments.MyProfileFragment.1
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
                        if (((JSONObject) arrayList2.get(i)).getString(NotificationCompat.CATEGORY_STATUS).equals("Ordered") || ((JSONObject) arrayList2.get(i)).getString(NotificationCompat.CATEGORY_STATUS).equals("Accepted") || ((JSONObject) arrayList2.get(i)).getString(NotificationCompat.CATEGORY_STATUS).equals("Delivering")) {
                            arrayList.add((JSONObject) arrayList2.get(i));
                        }
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                    RecyclerView recyclerView = (RecyclerView) MyProfileFragment.this.view.findViewById(R.id.recv);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MyProfileFragment.this.getContext()));
                    MyProfileFragment.this.expencesListRCAdapter = new orderListRCAdapter(MyProfileFragment.this.getContext(), arrayList);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setItemViewCacheSize(20);
                    recyclerView.setDrawingCacheEnabled(true);
                    recyclerView.setDrawingCacheQuality(1048576);
                    recyclerView.setAdapter(MyProfileFragment.this.expencesListRCAdapter);
                }
            }
        }, new Response.ErrorListener() { // from class: fragments.MyProfileFragment.2
            @Override // com.android.volley.Response.ErrorListener
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("Error.Response", volleyError.toString());
            }
        }));
        final ArrayList arrayList3 = new ArrayList();
        Volley.newRequestQueue(requireContext()).add(new JsonArrayRequest(0, "https://jsfoodi.com/Jsfoodi_App/JsFoodi/fetchUser.php?id=" + this.uid, null, new Response.Listener<JSONArray>() { // from class: fragments.MyProfileFragment.3
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
                    arrayList3.add(jSONObject);
                    try {
                        ((TextView) MyProfileFragment.this.view.findViewById(R.id.userProfileName)).setText(((JSONObject) arrayList3.get(i)).getString("name"));
                        ((TextView) MyProfileFragment.this.view.findViewById(R.id.userProfileNumber)).setText(((JSONObject) arrayList3.get(i)).getString("phone"));
                        ((TextView) MyProfileFragment.this.view.findViewById(R.id.userProfileAddress)).setText(((JSONObject) arrayList3.get(i)).getString("address"));
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() { // from class: fragments.MyProfileFragment.4
            @Override // com.android.volley.Response.ErrorListener
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("Error.Response", volleyError.toString());
            }
        }));
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.changeAddressImage /* 2131362069 */:
                Intent intent = new Intent(getActivity(), ChangeLocationActivity.class);
                intent.putExtra("INT", "THREE");
                startActivity(intent);
                return;
            case R.id.editProfile /* 2131362187 */:
                Intent intent2 = new Intent(getActivity(), CartItemActivity.class);
                intent2.putExtra("INT", "THREE");
                startActivity(intent2);
                return;
            case R.id.logOutText /* 2131362377 */:
                new AlertDialog.Builder(requireContext()).setMessage("Are you sure you want to log out ?").setPositiveButton("Log Out", new DialogInterface.OnClickListener() { // from class: fragments.MyProfileFragment$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        MyProfileFragment.this.m374lambda$onClick$0$fragmentsMyProfileFragment(dialogInterface, i);
                    }
                }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).show();
                return;
            case R.id.myOrdersImage /* 2131362459 */:
                startActivity(new Intent(getActivity(), OrdersActivity.class));
                return;
            default:
                return;
        }
    }

    /* renamed from: lambda$onClick$0$fragments-MyProfileFragment  reason: not valid java name */
    public /* synthetic */ void m374lambda$onClick$0$fragmentsMyProfileFragment(DialogInterface dialogInterface, int i) {
        new HashMap().put("devicetoken", FieldValue.delete());
        this.mAuth.signOut();
        sendUserToLogin();
    }

    private void sendUserToLogin() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(67108864);
        intent.addFlags(32768);
        startActivity(intent);
        requireActivity().finish();
    }
}
