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
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;

import bugfix.itsolutions.jsfoodi.R;
import bugfix.itsolutions.jsfoodi.catagoryView;

/* loaded from: classes.dex */
public class cussineListRCAdapter extends RecyclerView.Adapter<cussineListRCAdapter.ViewHolder> {
    Context context;
    String[] fruitImages;
    String[] fruitNames;

    public cussineListRCAdapter(Context context, String[] strArr, String[] strArr2) {
        this.context = context;
        this.fruitNames = strArr;
        this.fruitImages = strArr2;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_cuisine_layout, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        try {
            viewHolder.name.setText(this.fruitNames[i]);
            Glide.with(this.context).load(this.fruitImages[i]).apply((BaseRequestOptions<?>) new RequestOptions().override(200, 200)).centerCrop().into(viewHolder.image);
        } catch (ArrayIndexOutOfBoundsException unused) {
        }
        viewHolder.image.setOnClickListener(new View.OnClickListener() { // from class: adapters.cussineListRCAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Intent intent = new Intent(cussineListRCAdapter.this.context, catagoryView.class);
                intent.putExtra("catagory", cussineListRCAdapter.this.fruitNames[i]);
                cussineListRCAdapter.this.context.startActivity(intent);
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.fruitNames.length;
    }

    /* loaded from: classes.dex */
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;

        public ViewHolder(View view) {
            super(view);
            this.name = (TextView) view.findViewById(R.id.cuisineName);
            this.image = (ImageView) view.findViewById(R.id.cuisineImage);
        }
    }
}
