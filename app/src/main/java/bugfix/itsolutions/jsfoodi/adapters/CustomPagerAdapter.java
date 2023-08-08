package bugfix.itsolutions.jsfoodi.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import java.util.List;

import bugfix.itsolutions.jsfoodi.R;

/* loaded from: classes.dex */
public class CustomPagerAdapter extends PagerAdapter {
    private static final String TAG = "ImageViewPage";
    private List<String> Int_ent;
    private List<String> imagePaths;
    Context mContext;
    LayoutInflater mLayoutInflater;

    @Override // androidx.viewpager.widget.PagerAdapter
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    public CustomPagerAdapter(Context context, List<String> list) {
        this.mContext = context;
        this.imagePaths = list;
        this.mLayoutInflater = (LayoutInflater) context.getSystemService("layout_inflater");
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getCount() {
        return this.imagePaths.size();
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public Object instantiateItem(ViewGroup viewGroup, int i) {
        Log.d(TAG, "instantiateItem() called with: container = [" + viewGroup + "], position = [" + i + "]");
        View inflate = this.mLayoutInflater.inflate(R.layout.item_pager, viewGroup, false);
        Glide.with(this.mContext).load(this.imagePaths.get(i)).placeholder((int) R.drawable.amazon).error(R.drawable.amazon).into((ImageView) inflate.findViewById(R.id.iv_xphoto));
        viewGroup.addView(inflate);
        return inflate;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        Log.d(TAG, "destroyItem() called with: container = [" + viewGroup + "], position = [" + i + "], object = [" + obj + "]");
        viewGroup.removeView((ConstraintLayout) obj);
    }
}
