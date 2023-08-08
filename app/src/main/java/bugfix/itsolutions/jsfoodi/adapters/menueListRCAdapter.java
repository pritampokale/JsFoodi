package bugfix.itsolutions.jsfoodi.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

import bugfix.itsolutions.jsfoodi.R;
import bugfix.itsolutions.jsfoodi.ui.auth.AddInfoActivity;
import bugfix.itsolutions.jsfoodi.ui.cart.CartItemActivity;

/* loaded from: classes.dex */
public class menueListRCAdapter extends RecyclerView.Adapter<menueListRCAdapter.ViewHolder> {
    String Ruid;
    String check;
    Context context;
    ArrayList<JSONObject> list;
    ArrayList<JSONObject> list1;
    String mResDeliveryTime;
    String mResImage;
    String mRestaurantUid;
    private RelativeLayout mRootView;
    ArrayList<JSONObject> listf = new ArrayList<>();
    HashMap<String, String> HashMapParams = new HashMap<>();
    HashMap<String, String> HashMapParams1 = new HashMap<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public menueListRCAdapter(Context context, ArrayList<JSONObject> arrayList, ArrayList<JSONObject> arrayList2) {
        this.context = context;
        this.list = arrayList;
        this.list1 = arrayList2;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.restaurant_menuitems_view, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ", Locale.ENGLISH);
        new SimpleDateFormat("hh:mm aaa");
        simpleDateFormat.format(new Date());
        try {
            TextView textView = viewHolder.menuItemName;
            textView.setText("" + this.list.get(i).getString("name"));
            TextView textView2 = viewHolder.menuItemCategory;
            textView2.setText("" + this.list.get(i).getString("category"));
            TextView textView3 = viewHolder.textView;
            textView3.setText("" + this.list.get(i).getString("description"));
            TextView textView4 = viewHolder.menuItemPrice;
            textView4.setText("₹ " + this.list.get(i).getString(FirebaseAnalytics.Param.PRICE));
            this.Ruid = this.list.get(i).getString("ruid");
            String string = this.list.get(i).getString("veg");
            String string2 = this.list.get(i).getString("img");
            String string3 = this.list.get(i).getString("imageCat");
            if (string.equals("Veg")) {
                Context context = this.context;
                Objects.requireNonNull(context);
                Context context2 = context;
                Glide.with(context).load(Integer.valueOf((int) R.drawable.veg_symbol)).into(viewHolder.foodMark);
            } else {
                Context context3 = this.context;
                Objects.requireNonNull(context3);
                Context context4 = context3;
                Glide.with(context3).load(Integer.valueOf((int) R.drawable.non_veg_symbol)).into(viewHolder.foodMark);
            }
            if (!string2.equals("empty")) {
                Context context5 = this.context;
                Objects.requireNonNull(context5);
                Context context6 = context5;
                Glide.with(context5).load(string2).placeholder((int) R.drawable.ic_baseline_restaurant_24).circleCrop().into(viewHolder.imageView);
            } else {
                Context context7 = this.context;
                Objects.requireNonNull(context7);
                Context context8 = context7;
                Glide.with(context7).load(string3).placeholder((int) R.drawable.ic_baseline_restaurant_24).circleCrop().into(viewHolder.imageView);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        FirebaseAuth.getInstance().getCurrentUser().getUid();
        for (int i2 = 0; i2 < this.list1.size(); i2++) {
            try {
                if (this.list1.get(i2).getString("name").equals(this.list.get(i).getString("name"))) {
                    viewHolder.addMenuItemBtn.setVisibility(8);
                    viewHolder.removeMenuItemBtn.setVisibility(0);
                }
            } catch (IndexOutOfBoundsException | JSONException e2) {
                e2.printStackTrace();
            }
        }
        viewHolder.addMenuItemBtn.setOnClickListener(new View.OnClickListener() { // from class: adapters.menueListRCAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                try {
                    viewHolder.addItemToCart(menueListRCAdapter.this.list.get(i).getString("name"), menueListRCAdapter.this.list.get(i).getString(FirebaseAnalytics.Param.PRICE), menueListRCAdapter.this.list.get(i).getString("ruid"), menueListRCAdapter.this.list.get(i).getString("veg"));
                } catch (JSONException e3) {
                    e3.printStackTrace();
                }
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                Volley.newRequestQueue(menueListRCAdapter.this.context).add(new JsonArrayRequest(0, "https://jsfoodi.com/Jsfoodi_App/JsFoodi/fetchCart.php?id=" + uid, null, new Response.Listener<JSONArray>() { // from class: adapters.menueListRCAdapter.1.1
                    @Override // com.android.volley.Response.Listener
                    public void onResponse(JSONArray jSONArray) {
                        for (int i3 = 0; i3 < jSONArray.length(); i3++) {
                            JSONObject jSONObject = null;
                            try {
                                jSONObject = jSONArray.getJSONObject(i3);
                            } catch (JSONException e4) {
                                e4.printStackTrace();
                            }
                            menueListRCAdapter.this.listf.add(jSONObject);
                            for (int i4 = 0; i4 < menueListRCAdapter.this.listf.size(); i4++) {
                                try {
                                    if (!menueListRCAdapter.this.listf.get(i4).getString("ruid").equals(menueListRCAdapter.this.Ruid)) {
                                        menueListRCAdapter.this.HashMapParams1.put("name", menueListRCAdapter.this.listf.get(i4).getString("name"));
                                        viewHolder.RemoveCartItem();
                                    }
                                } catch (JSONException e5) {
                                    e5.printStackTrace();
                                }
                            }
                        }
                    }
                }, new Response.ErrorListener() { // from class: adapters.menueListRCAdapter.1.2
                    @Override // com.android.volley.Response.ErrorListener
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d("Error.Response", volleyError.toString());
                    }
                }));
                viewHolder.addMenuItemBtn.setVisibility(8);
                viewHolder.removeMenuItemBtn.setVisibility(0);
            }
        });
        viewHolder.removeMenuItemBtn.setOnClickListener(new View.OnClickListener() { // from class: adapters.menueListRCAdapter.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                try {
                    viewHolder.removeItemFromCart(menueListRCAdapter.this.list.get(i).getString("name"));
                    viewHolder.removeMenuItemBtn.setVisibility(8);
                    viewHolder.addMenuItemBtn.setVisibility(0);
                } catch (JSONException e3) {
                    e3.printStackTrace();
                }
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.list.size();
    }

    /* loaded from: classes.dex */
    public class ViewHolder extends RecyclerView.ViewHolder {
        String ServerUploadPath;
        String ServerUploadPath1;
        Button addMenuItemBtn;
        ImageView foodMark;
        ImageView imageView;
        TextView menuItemCategory;
        TextView menuItemName;
        TextView menuItemPrice;
        Button removeMenuItemBtn;
        TextView textView;

        public ViewHolder(View view) {
            super(view);
            this.ServerUploadPath = "https://jsfoodi.com/Jsfoodi_App/JsFoodi/addCart.php";
            this.ServerUploadPath1 = "https://jsfoodi.com/Jsfoodi_App/JsFoodi/removeCartItem.php";
            this.menuItemName = (TextView) view.findViewById(R.id.menuItemName);
            this.menuItemCategory = (TextView) view.findViewById(R.id.menuItemCategory);
            this.menuItemPrice = (TextView) view.findViewById(R.id.menuItemPrice);
            this.addMenuItemBtn = (Button) view.findViewById(R.id.addMenuItemBtn);
            this.removeMenuItemBtn = (Button) view.findViewById(R.id.removeMenuItemBtn);
            this.foodMark = (ImageView) view.findViewById(R.id.foodMark);
            menueListRCAdapter.this.mRootView = (RelativeLayout) view.findViewById(R.id.con);
            this.imageView = (ImageView) view.findViewById(R.id.imageView);
            this.textView = (TextView) view.findViewById(R.id.textView);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void removeItemFromCart(String str) {
            menueListRCAdapter.this.HashMapParams1.put("name", str);
            RemoveCartItem();
        }

        public void addItemToCart(String str, String str2, String str3, String str4) {
            menueListRCAdapter.this.HashMapParams.put("name", str);
            menueListRCAdapter.this.HashMapParams.put(FirebaseAnalytics.Param.PRICE, str2);
            menueListRCAdapter.this.HashMapParams.put("veg", str4);
            menueListRCAdapter.this.HashMapParams.put("count", "1");
            addItem();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: adapters.menueListRCAdapter$ViewHolder$1AsyncTaskUploadClass  reason: invalid class name */
        /* loaded from: classes.dex */
        public class C1AsyncTaskUploadClass extends AsyncTask<Void, Void, String> {
            C1AsyncTaskUploadClass() {
            }

            @Override // android.os.AsyncTask
            protected void onPreExecute() {
                super.onPreExecute();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public void onPostExecute(String str) {
                super.onPostExecute( str);
                if (str.equals("Added Succesfull.")) {
                    Snackbar action = Snackbar.make(menueListRCAdapter.this.mRootView, "Added items in  your cart", -2).setAction("Cart", new View.OnClickListener() { // from class: adapters.menueListRCAdapter$ViewHolder$1AsyncTaskUploadClass$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            C1AsyncTaskUploadClass.this.m2x87029974(view);
                        }
                    });
                    action.setActionTextColor(ContextCompat.getColor(menueListRCAdapter.this.context, R.color.white));
                    action.getView().setBackgroundColor(ContextCompat.getColor(menueListRCAdapter.this.context, R.color.colorPrimary));
                    action.show();
                }
            }

            /* renamed from: lambda$onPostExecute$0$adapters-menueListRCAdapter$ViewHolder$1AsyncTaskUploadClass  reason: not valid java name */
            public /* synthetic */ void m2x87029974(View view) {
                Snackbar make = Snackbar.make(menueListRCAdapter.this.mRootView, "Message is restored!", 0);
                Intent intent = new Intent(menueListRCAdapter.this.context, CartItemActivity.class);
                intent.setFlags(268435456);
                menueListRCAdapter.this.context.startActivity(intent);
                make.dismiss();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public String doInBackground(Void... voidArr) {
                AddInfoActivity.ImageProcessClass imageProcessClass = new AddInfoActivity.ImageProcessClass();
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                menueListRCAdapter.this.HashMapParams.put("ruid", menueListRCAdapter.this.Ruid);
                menueListRCAdapter.this.HashMapParams.put("uid", uid);
                return imageProcessClass.ImageHttpRequest(ViewHolder.this.ServerUploadPath, menueListRCAdapter.this.HashMapParams);
            }
        }

        public void addItem() {
            new C1AsyncTaskUploadClass().execute(new Void[0]);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: adapters.menueListRCAdapter$ViewHolder$2AsyncTaskUploadClass  reason: invalid class name */
        /* loaded from: classes.dex */
        public class C2AsyncTaskUploadClass extends AsyncTask<Void, Void, String> {
            C2AsyncTaskUploadClass() {
            }

            @Override // android.os.AsyncTask
            protected void onPreExecute() {
                super.onPreExecute();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public void onPostExecute(String str) {
                super.onPostExecute( str);
                if (str.equals("Added Succesfull.")) {
                    Snackbar action = Snackbar.make(menueListRCAdapter.this.mRootView, "Removed items in your cart", -2).setAction("Cart", new View.OnClickListener() { // from class: adapters.menueListRCAdapter$ViewHolder$2AsyncTaskUploadClass$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            C2AsyncTaskUploadClass.this.m3x939b8ef5(view);
                        }
                    });
                    action.setActionTextColor(ContextCompat.getColor(menueListRCAdapter.this.context, R.color.white));
                    action.getView().setBackgroundColor(ContextCompat.getColor(menueListRCAdapter.this.context, R.color.colorPrimary));
                    action.show();
                }
            }

            /* renamed from: lambda$onPostExecute$0$adapters-menueListRCAdapter$ViewHolder$2AsyncTaskUploadClass  reason: not valid java name */
            public /* synthetic */ void m3x939b8ef5(View view) {
                Snackbar make = Snackbar.make(menueListRCAdapter.this.mRootView, "Message is restored!", 0);
                menueListRCAdapter.this.context.startActivity(new Intent(menueListRCAdapter.this.context, CartItemActivity.class));
                make.dismiss();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public String doInBackground(Void... voidArr) {
                AddInfoActivity.ImageProcessClass imageProcessClass = new AddInfoActivity.ImageProcessClass();
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                menueListRCAdapter.this.HashMapParams1.put("ruid", menueListRCAdapter.this.Ruid);
                menueListRCAdapter.this.HashMapParams1.put("uid", uid);
                return imageProcessClass.ImageHttpRequest(ViewHolder.this.ServerUploadPath1, menueListRCAdapter.this.HashMapParams1);
            }
        }

        public void RemoveCartItem() {
            new C2AsyncTaskUploadClass().execute(new Void[0]);
        }
    }
}
