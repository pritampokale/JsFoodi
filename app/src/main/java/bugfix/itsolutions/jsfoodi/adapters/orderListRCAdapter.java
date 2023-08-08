package bugfix.itsolutions.jsfoodi.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import bugfix.itsolutions.jsfoodi.R;
import bugfix.itsolutions.jsfoodi.ui.auth.AddInfoActivity;
import bugfix.itsolutions.jsfoodi.ui.cart.CartItemActivity;
import bugfix.itsolutions.jsfoodi.ui.order.CurrentOrderActivity;

/* loaded from: classes.dex */
public class orderListRCAdapter extends RecyclerView.Adapter<orderListRCAdapter.ViewHolder> {
    String Ruid;
    String check;
    Context context;
    ArrayList<JSONObject> list;
    String mResDeliveryTime;
    String mResImage;
    String mRestaurantUid;
    private RelativeLayout mRootView;
    HashMap<String, String> HashMapParams = new HashMap<>();
    HashMap<String, String> HashMapParams1 = new HashMap<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public orderListRCAdapter(Context context, ArrayList<JSONObject> arrayList) {
        this.context = context;
        this.list = arrayList;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ordered_items_layout, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        try {
            TextView textView = viewHolder.orderedResName;
            textView.setText("" + this.list.get(i).getString("ordered_restaurant_name") + " - " + this.list.get(i).getString(NotificationCompat.CATEGORY_STATUS));
            TextView textView2 = viewHolder.orderedItemsText;
            StringBuilder sb = new StringBuilder();
            sb.append("");
            sb.append(this.list.get(i).getString("ordered_items"));
            textView2.setText(sb.toString());
            TextView textView3 = viewHolder.orderedTimeStamp;
            textView3.setText("" + this.list.get(i).getString("ordered_time"));
            TextView textView4 = viewHolder.OTP;
            textView4.setText("OTP : " + this.list.get(i).getString("otp"));
            viewHolder.orderedResAddress.setVisibility(8);
            Double valueOf = Double.valueOf(Double.valueOf(this.list.get(i).getString("total_amount").replace("₹", "")).doubleValue() + Double.valueOf(this.list.get(i).getString("deliveryAmount").replace("₹", "")).doubleValue());
            TextView textView5 = viewHolder.orderedAmount;
            textView5.setText("₹ " + valueOf);
            viewHolder.CardViewContainer.setOnClickListener(new View.OnClickListener() { // from class: adapters.orderListRCAdapter.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    try {
                        if (!orderListRCAdapter.this.list.get(i).getString(NotificationCompat.CATEGORY_STATUS).equals("Completed")) {
                            Intent intent = new Intent(orderListRCAdapter.this.context, CurrentOrderActivity.class);
                            intent.putExtra("RES_NAME", orderListRCAdapter.this.list.get(i).getString("ordered_restaurant_name"));
                            intent.putExtra("RES_UID", orderListRCAdapter.this.list.get(i).getString("ruid"));
                            intent.putExtra("ORDER_UID", orderListRCAdapter.this.list.get(i).getString("ordered_id"));
                            intent.addFlags(268435456);
                            orderListRCAdapter.this.context.startActivity(intent);
                        } else {
                            Toast.makeText(orderListRCAdapter.this.context, "Order Successfully Completed", 0).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.list.size();
    }

    /* loaded from: classes.dex */
    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView CardViewContainer;
        TextView OTP;
        String ServerUploadPath;
        String ServerUploadPath1;
        TextView orderedAmount;
        TextView orderedItemsText;
        TextView orderedResAddress;
        ImageView orderedResImage;
        TextView orderedResName;
        TextView orderedTimeStamp;

        public ViewHolder(View view) {
            super(view);
            this.ServerUploadPath = "https://jsfoodi.com/Jsfoodi_App/JsFoodi/addCart.php";
            this.ServerUploadPath1 = "https://jsfoodi.com/Jsfoodi_App/JsFoodi/removeCartItem.php";
            this.orderedResName = (TextView) view.findViewById(R.id.orderedResName);
            this.orderedResAddress = (TextView) view.findViewById(R.id.orderedResAddress);
            this.orderedItemsText = (TextView) view.findViewById(R.id.orderedItemsText);
            this.orderedTimeStamp = (TextView) view.findViewById(R.id.orderedTimeStamp);
            this.orderedAmount = (TextView) view.findViewById(R.id.orderedAmount);
            this.OTP = (TextView) view.findViewById(R.id.OTP);
            this.CardViewContainer = (CardView) view.findViewById(R.id.CardViewContainer);
            this.orderedResImage = (ImageView) view.findViewById(R.id.orderedResImage);
        }

        private void removeItemFromCart(String str) {
            orderListRCAdapter.this.HashMapParams1.put("name", str);
            RemoveCartItem();
        }

        public void addItemToCart(String str, String str2, String str3, String str4) {
            orderListRCAdapter.this.HashMapParams.put("name", str);
            orderListRCAdapter.this.HashMapParams.put(FirebaseAnalytics.Param.PRICE, str2);
            orderListRCAdapter.this.HashMapParams.put("veg", str4);
            orderListRCAdapter.this.HashMapParams.put("count", "1");
            addItem();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: adapters.orderListRCAdapter$ViewHolder$1AsyncTaskUploadClass  reason: invalid class name */
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
                    Snackbar action = Snackbar.make(orderListRCAdapter.this.mRootView, "Added items in  your cart", -2).setAction("Cart", new View.OnClickListener() { // from class: adapters.orderListRCAdapter$ViewHolder$1AsyncTaskUploadClass$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            C1AsyncTaskUploadClass.this.m4xb2e013dc(view);
                        }
                    });
                    action.setActionTextColor(ContextCompat.getColor(orderListRCAdapter.this.context, R.color.white));
                    action.getView().setBackgroundColor(ContextCompat.getColor(orderListRCAdapter.this.context, R.color.colorPrimary));
                    action.show();
                }
            }

            /* renamed from: lambda$onPostExecute$0$adapters-orderListRCAdapter$ViewHolder$1AsyncTaskUploadClass  reason: not valid java name */
            public /* synthetic */ void m4xb2e013dc(View view) {
                Snackbar make = Snackbar.make(orderListRCAdapter.this.mRootView, "Message is restored!", 0);
                Intent intent = new Intent(orderListRCAdapter.this.context, CartItemActivity.class);
                intent.setFlags(268435456);
                orderListRCAdapter.this.context.startActivity(intent);
                make.dismiss();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public String doInBackground(Void... voidArr) {
                AddInfoActivity.ImageProcessClass imageProcessClass = new AddInfoActivity.ImageProcessClass();
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                orderListRCAdapter.this.HashMapParams.put("ruid", orderListRCAdapter.this.Ruid);
                orderListRCAdapter.this.HashMapParams.put("uid", uid);
                return imageProcessClass.ImageHttpRequest(ViewHolder.this.ServerUploadPath, orderListRCAdapter.this.HashMapParams);
            }
        }

        public void addItem() {
            new C1AsyncTaskUploadClass().execute(new Void[0]);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: adapters.orderListRCAdapter$ViewHolder$2AsyncTaskUploadClass  reason: invalid class name */
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
                    Snackbar action = Snackbar.make(orderListRCAdapter.this.mRootView, "Removed items in your cart", -2).setAction("Cart", new View.OnClickListener() { // from class: adapters.orderListRCAdapter$ViewHolder$2AsyncTaskUploadClass$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            C2AsyncTaskUploadClass.this.m5xbf79095d(view);
                        }
                    });
                    action.setActionTextColor(ContextCompat.getColor(orderListRCAdapter.this.context, R.color.white));
                    action.getView().setBackgroundColor(ContextCompat.getColor(orderListRCAdapter.this.context, R.color.colorPrimary));
                    action.show();
                }
            }

            /* renamed from: lambda$onPostExecute$0$adapters-orderListRCAdapter$ViewHolder$2AsyncTaskUploadClass  reason: not valid java name */
            public /* synthetic */ void m5xbf79095d(View view) {
                Snackbar make = Snackbar.make(orderListRCAdapter.this.mRootView, "Message is restored!", 0);
                orderListRCAdapter.this.context.startActivity(new Intent(orderListRCAdapter.this.context, CartItemActivity.class));
                make.dismiss();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public String doInBackground(Void... voidArr) {
                AddInfoActivity.ImageProcessClass imageProcessClass = new AddInfoActivity.ImageProcessClass();
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                orderListRCAdapter.this.HashMapParams1.put("ruid", orderListRCAdapter.this.Ruid);
                orderListRCAdapter.this.HashMapParams1.put("uid", uid);
                return imageProcessClass.ImageHttpRequest(ViewHolder.this.ServerUploadPath1, orderListRCAdapter.this.HashMapParams1);
            }
        }

        public void RemoveCartItem() {
            new C2AsyncTaskUploadClass().execute(new Void[0]);
        }
    }
}
