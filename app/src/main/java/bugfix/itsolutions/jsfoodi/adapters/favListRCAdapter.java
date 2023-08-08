package bugfix.itsolutions.jsfoodi.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import bugfix.itsolutions.jsfoodi.R;
import bugfix.itsolutions.jsfoodi.ui.main.MainRestaurantPageActivity;

/* loaded from: classes.dex */
public class favListRCAdapter extends RecyclerView.Adapter<favListRCAdapter.ViewHolder> {
    String Ruid;
    Context context;
    ArrayList<JSONObject> list;

    public favListRCAdapter(Context context, ArrayList<JSONObject> arrayList) {
        this.context = context;
        this.list = arrayList;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_favorite_res_layout, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        try {
            this.Ruid = this.list.get(i).getString("uid");
            viewHolder.mFavResName.setText(this.list.get(i).getString("name"));
            viewHolder.mfavResLocation.setText(this.list.get(i).getString("address"));
            viewHolder.mfavResCategories.setText(this.list.get(i).getString("cuisine"));
            TextView textView = viewHolder.mFavResPrice;
            textView.setText("₹" + this.list.get(i).getString(FirebaseAnalytics.Param.PRICE) + " per person");
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: adapters.favListRCAdapter$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    favListRCAdapter.this.m1lambda$onBindViewHolder$0$adaptersfavListRCAdapter(i, view);
                }
            });
            String string = this.list.get(i).getString("image");
            if (string.equals("empty")) {
                Glide.with(this.context).load(Integer.valueOf((int) R.drawable.restaurant_image_placeholder)).into(viewHolder.mFavResImage);
            } else {
                Glide.with(this.context).load(string).placeholder((int) R.drawable.restaurant_image_placeholder).into(viewHolder.mFavResImage);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /* renamed from: lambda$onBindViewHolder$0$adapters-favListRCAdapter  reason: not valid java name */
    public /* synthetic */ void m1lambda$onBindViewHolder$0$adaptersfavListRCAdapter(int i, View view) {
        Intent intent = new Intent(this.context, MainRestaurantPageActivity.class);
        try {
            intent.putExtra("RUID", this.list.get(i).getString("ruid"));
            intent.putExtra("NAME", this.list.get(i).getString("name"));
            intent.putExtra("RES_SEARCH", "");
            intent.putExtra("ADDRESS", this.list.get(i).getString("address"));
            intent.putExtra("CUISINE", this.list.get(i).getString("cuisine"));
            intent.putExtra("DISTANCE", this.list.get(i).getString("distance"));
            intent.putExtra("TIME", this.list.get(i).getString("time"));
            intent.putExtra("PRICE", this.list.get(i).getString(FirebaseAnalytics.Param.PRICE));
            intent.putExtra("RES_IMAGE", this.list.get(i).getString("image"));
            intent.putExtra("RES_NUM", this.list.get(i).getString("phone"));
            this.context.startActivity(intent);
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
        ImageView mFavResImage;
        TextView mFavResName;
        TextView mFavResPrice;
        TextView mfavResCategories;
        TextView mfavResLocation;

        public ViewHolder(View view) {
            super(view);
            this.mFavResImage = (ImageView) view.findViewById(R.id.favResImage);
            this.mFavResName = (TextView) view.findViewById(R.id.favResName);
            this.mFavResPrice = (TextView) view.findViewById(R.id.favoriteResPrice);
            this.mfavResLocation = (TextView) view.findViewById(R.id.favResLocation);
            this.mfavResCategories = (TextView) view.findViewById(R.id.favResCategories);
        }
    }
}
