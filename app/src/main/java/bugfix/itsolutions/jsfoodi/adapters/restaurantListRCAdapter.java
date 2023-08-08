package bugfix.itsolutions.jsfoodi.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import bugfix.itsolutions.jsfoodi.R;
import bugfix.itsolutions.jsfoodi.ui.main.MainRestaurantPageActivity;

/* loaded from: classes.dex */
public class restaurantListRCAdapter extends RecyclerView.Adapter<restaurantListRCAdapter.ViewHolder> {
    String Ruid;
    Context context;
    ArrayList<JSONObject> list;
    int ok = 0;
    String search;

    public restaurantListRCAdapter(Context context, ArrayList<JSONObject> arrayList, String str) {
        this.context = context;
        this.list = arrayList;
        this.search = str;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.restaurant_main_detail1, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        try {
            if (this.list.get(i).getString("open").equals("yes") && this.list.get(i).getString("verification").equals("yes")) {
                TextView textView = viewHolder.resName;
                textView.setText("" + this.list.get(i).getString("name"));
                TextView textView2 = viewHolder.resCuisine;
                textView2.setText("" + this.list.get(i).getString("cuisine"));
                TextView textView3 = viewHolder.average_time;
                textView3.setText("     Time : " + this.list.get(i).getString("prepTime") + " Mins");
                TextView textView4 = viewHolder.average_price;
                textView4.setText("₹ " + this.list.get(i).getString("average_price"));
                this.Ruid = this.list.get(i).getString("uid");
                String string = this.list.get(i).getString("image");
                if (string.equals("empty")) {
                    Glide.with(this.context).load(Integer.valueOf((int) R.drawable.restaurant_image_placeholder)).into(viewHolder.resImage);
                } else {
                    Glide.with(this.context).load(string).placeholder((int) R.drawable.restaurant_image_placeholder).into(viewHolder.resImage);
                }
                viewHolder.card.setOnClickListener(new View.OnClickListener() { // from class: adapters.restaurantListRCAdapter.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        Intent intent = new Intent(restaurantListRCAdapter.this.context, MainRestaurantPageActivity.class);
                        try {
                            intent.putExtra("RUID", restaurantListRCAdapter.this.list.get(i).getString("uid"));
                            intent.putExtra("NAME", restaurantListRCAdapter.this.list.get(i).getString("name"));
                            intent.putExtra("RES_IMAGE", restaurantListRCAdapter.this.list.get(i).getString("image"));
                            intent.putExtra("PRICE", restaurantListRCAdapter.this.list.get(i).getString("average_price"));
                            intent.putExtra("ADDRESS", restaurantListRCAdapter.this.list.get(i).getString("address"));
                            intent.putExtra("CUISINE", restaurantListRCAdapter.this.list.get(i).getString("cuisine"));
                            intent.putExtra("TIME", restaurantListRCAdapter.this.list.get(i).getString("prepTime"));
                            intent.putExtra("RES_NUM", restaurantListRCAdapter.this.list.get(i).getString("phoneNumber"));
                            intent.putExtra("RES_SEARCH", restaurantListRCAdapter.this.search);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            restaurantListRCAdapter.this.context.startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                viewHolder.close.setVisibility(8);
                viewHolder.resImage3.setVisibility(8);
                return;
            }
            TextView textView5 = viewHolder.resName;
            textView5.setText("" + this.list.get(i).getString("name"));
            TextView textView6 = viewHolder.resCuisine;
            textView6.setText("" + this.list.get(i).getString("cuisine"));
            TextView textView7 = viewHolder.average_time;
            textView7.setText("     Time : " + this.list.get(i).getString("prepTime") + "");
            TextView textView8 = viewHolder.average_price;
            textView8.setText("₹ " + this.list.get(i).getString("average_price"));
            this.Ruid = this.list.get(i).getString("uid");
            String string2 = this.list.get(i).getString("image");
            if (string2.equals("empty")) {
                Glide.with(this.context).load(Integer.valueOf((int) R.drawable.restaurant_image_placeholder)).into(viewHolder.resImage);
            } else {
                Glide.with(this.context).load(string2).placeholder((int) R.drawable.restaurant_image_placeholder).into(viewHolder.resImage);
                Glide.with(this.context).load(string2).placeholder((int) R.drawable.restaurant_image_placeholder).into(viewHolder.resImage3);
            }
            viewHolder.close.setVisibility(0);
            viewHolder.resImage.animate().alpha(1.0f);
            viewHolder.resImage.setVisibility(4);
            viewHolder.resImage3.setVisibility(0);
            viewHolder.card.setCardBackgroundColor(-3355444);
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
        TextView average_price;
        TextView average_time;
        CardView card;
        ImageView close;
        TextView ratingAggregate;
        RatingBar ratingBar;
        TextView resCuisine;
        ImageView resImage;
        ImageView resImage3;
        TextView resName;

        public ViewHolder(View view) {
            super(view);
            this.resName = (TextView) view.findViewById(R.id.resName);
            this.resCuisine = (TextView) view.findViewById(R.id.resCuisine);
            this.ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
            this.ratingAggregate = (TextView) view.findViewById(R.id.ratingAggregate);
            this.average_price = (TextView) view.findViewById(R.id.average_price);
            this.average_time = (TextView) view.findViewById(R.id.average_time);
            this.resImage = (ImageView) view.findViewById(R.id.resImage);
            this.resImage3 = (ImageView) view.findViewById(R.id.resImage3);
            this.close = (ImageView) view.findViewById(R.id.imageView4);
            this.card = (CardView) view.findViewById(R.id.card);
        }
    }
}
