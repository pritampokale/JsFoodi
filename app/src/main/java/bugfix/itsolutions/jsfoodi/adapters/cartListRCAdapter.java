package bugfix.itsolutions.jsfoodi.adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import bugfix.itsolutions.jsfoodi.R;
import bugfix.itsolutions.jsfoodi.ui.auth.AddInfoActivity;

/* loaded from: classes.dex */
public class cartListRCAdapter extends RecyclerView.Adapter<cartListRCAdapter.ViewHolder> {
    HashMap<String, String> HashMapParams = new HashMap<>();
    HashMap<String, String> HashMapParams1 = new HashMap<>();
    String Ruid;
    Context context;
    ArrayList<JSONObject> list;

    public cartListRCAdapter(Context context, ArrayList<JSONObject> arrayList) {
        this.context = context;
        this.list = arrayList;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_items_layout, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        try {
            TextView textView = viewHolder.menuItemName;
            textView.setText("" + this.list.get(i).getString("name"));
            TextView textView2 = viewHolder.menuItemPrice;
            textView2.setText("₹ " + this.list.get(i).getString(FirebaseAnalytics.Param.PRICE));
            this.Ruid = this.list.get(i).getString("ruid");
            if (this.list.get(i).getString("veg").equals("Veg")) {
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
            viewHolder.mQtyPicker.setNumber(this.list.get(i).getString("count"));
            try {
                int parseInt = Integer.parseInt(this.list.get(i).getString(FirebaseAnalytics.Param.PRICE)) * Integer.parseInt(this.list.get(i).getString("count"));
                TextView textView3 = viewHolder.menuItemPrice;
                textView3.setText("₹ " + parseInt);
            } catch (NumberFormatException unused) {
            }
            viewHolder.mQtyPicker.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() { // from class: adapters.cartListRCAdapter$$ExternalSyntheticLambda0
                @Override // com.cepheuen.elegantnumberbutton.view.ElegantNumberButton.OnValueChangeListener
                public final void onValueChange(ElegantNumberButton elegantNumberButton, int i2, int i3) {
                    cartListRCAdapter.this.m0lambda$onBindViewHolder$0$adapterscartListRCAdapter(i, viewHolder, elegantNumberButton, i2, i3);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /* renamed from: lambda$onBindViewHolder$0$adapters-cartListRCAdapter  reason: not valid java name */
    public /* synthetic */ void m0lambda$onBindViewHolder$0$adapterscartListRCAdapter(int i, ViewHolder viewHolder, ElegantNumberButton elegantNumberButton, int i2, int i3) {
        String str;
        try {
            str = String.valueOf(Integer.parseInt(this.list.get(i).getString(FirebaseAnalytics.Param.PRICE)) * i3);
        } catch (NumberFormatException | JSONException e) {
            e.printStackTrace();
            str = null;
        }
        TextView textView = viewHolder.menuItemPrice;
        textView.setText("₹ " + str);
        try {
            viewHolder.UpdateCartItem(this.list.get(i).getString("uid"), this.list.get(i).getString("name"), String.valueOf(i3));
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        if (i3 == 0) {
            try {
                viewHolder.deleteItem(this.list.get(i).getString("id"));
                viewHolder.card.setVisibility(8);
            } catch (JSONException e3) {
                e3.printStackTrace();
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.list.size();
    }

    /* loaded from: classes.dex */
    public class ViewHolder extends RecyclerView.ViewHolder {
        String ServerUploadPath;
        String ServerUploadPath1;
        RelativeLayout card;
        ImageView foodMark;
        ElegantNumberButton mQtyPicker;
        TextView menuItemName;
        TextView menuItemPrice;

        public ViewHolder(View view) {
            super(view);
            this.ServerUploadPath = "https://jsfoodi.com/Jsfoodi_App/JsFoodi/deleteCartItem.php";
            this.ServerUploadPath1 = "https://jsfoodi.com/Jsfoodi_App/JsFoodi/updateCartItem.php";
            this.menuItemName = (TextView) view.findViewById(R.id.itemNameCart);
            this.menuItemPrice = (TextView) view.findViewById(R.id.itemPriceCart);
            this.mQtyPicker = (ElegantNumberButton) view.findViewById(R.id.quantityPicker);
            this.foodMark = (ImageView) view.findViewById(R.id.foodMarkCart);
            this.card = (RelativeLayout) view.findViewById(R.id.card);
        }

        /* JADX WARN: Type inference failed for: r0v0, types: [adapters.cartListRCAdapter$ViewHolder$1AsyncTaskUploadClass] */
        public void deleteItem(final String str) {
            new AsyncTask<Void, Void, String>() { // from class: adapters.cartListRCAdapter.ViewHolder.1AsyncTaskUploadClass
                @Override // android.os.AsyncTask
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                /* JADX INFO: Access modifiers changed from: protected */
                @Override // android.os.AsyncTask
                public void onPostExecute(String str2) {
                    super.onPostExecute( str2);
                }

                /* JADX INFO: Access modifiers changed from: protected */
                @Override // android.os.AsyncTask
                public String doInBackground(Void... voidArr) {
                    AddInfoActivity.ImageProcessClass imageProcessClass = new AddInfoActivity.ImageProcessClass();
                    cartListRCAdapter.this.HashMapParams.put("update", str);
                    return imageProcessClass.ImageHttpRequest(ViewHolder.this.ServerUploadPath, cartListRCAdapter.this.HashMapParams);
                }
            }.execute(new Void[0]);
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [adapters.cartListRCAdapter$ViewHolder$2AsyncTaskUploadClass] */
        public void UpdateCartItem(String str, final String str2, final String str3) {
            new AsyncTask<Void, Void, String>() { // from class: adapters.cartListRCAdapter.ViewHolder.2AsyncTaskUploadClass
                @Override // android.os.AsyncTask
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                /* JADX INFO: Access modifiers changed from: protected */
                @Override // android.os.AsyncTask
                public void onPostExecute(String str4) {
                    super.onPostExecute( str4);
                    ViewHolder.this.getTotalPrice();
                }

                /* JADX INFO: Access modifiers changed from: protected */
                @Override // android.os.AsyncTask
                public String doInBackground(Void... voidArr) {
                    AddInfoActivity.ImageProcessClass imageProcessClass = new AddInfoActivity.ImageProcessClass();
                    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    cartListRCAdapter.this.HashMapParams1.put("cond", str2);
                    cartListRCAdapter.this.HashMapParams1.put("cond1", uid);
                    cartListRCAdapter.this.HashMapParams1.put("update", str3);
                    return imageProcessClass.ImageHttpRequest(ViewHolder.this.ServerUploadPath1, cartListRCAdapter.this.HashMapParams1);
                }
            }.execute(new Void[0]);
        }

        public void getTotalPrice() {
            final ArrayList arrayList = new ArrayList();
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            Volley.newRequestQueue(cartListRCAdapter.this.context).add(new JsonArrayRequest(0, "https://jsfoodi.com/Jsfoodi_App/JsFoodi/fetchCart.php?id=" + uid, null, new Response.Listener<JSONArray>() { // from class: adapters.cartListRCAdapter.ViewHolder.1
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
                        if (arrayList.size() != 0) {
                            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                                try {
                                    String string = ((JSONObject) arrayList.get(i2)).getString(FirebaseAnalytics.Param.PRICE);
                                    String string2 = ((JSONObject) arrayList.get(i2)).getString("count");
                                    Integer.parseInt(string);
                                    Integer.parseInt(string2);
                                } catch (NumberFormatException | JSONException e2) {
                                    e2.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }, new Response.ErrorListener() { // from class: adapters.cartListRCAdapter.ViewHolder.2
                @Override // com.android.volley.Response.ErrorListener
                public void onErrorResponse(VolleyError volleyError) {
                    Log.d("Error.Response", volleyError.toString());
                }
            }));
        }
    }
}
