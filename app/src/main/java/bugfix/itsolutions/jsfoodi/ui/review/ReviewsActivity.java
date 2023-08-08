package bugfix.itsolutions.jsfoodi.ui.review;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.messaging.Constants;

import java.util.Objects;

import bugfix.itsolutions.jsfoodi.R;
import de.hdodenhof.circleimageview.CircleImageView;
import bugfix.itsolutions.jsfoodi.models.ReviewDetails;

/* loaded from: classes4.dex */
public class ReviewsActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    LinearLayoutManager linearLayoutManager;
    private RecyclerView mUserReviewRecyclerView;
    private String resName;
    private String resNum;
    private String resPrice;
    private String resUid;
    private String uid;

    /* loaded from: classes4.dex */
//    public class ReviewsHolder_ViewBinding implements Unbinder {
//        private ReviewsHolder target;
//
//        public ReviewsHolder_ViewBinding(ReviewsHolder reviewsHolder, View view) {
//            this.target = reviewsHolder;
//            reviewsHolder.mUserImage = (CircleImageView) Utils.findRequiredViewAsType(view, R.id.userReviewProfileImage, "field 'mUserImage'", CircleImageView.class);
//            reviewsHolder.mUserName = (TextView) Utils.findRequiredViewAsType(view, R.id.userReviewName, "field 'mUserName'", TextView.class);
//            reviewsHolder.mReview = (TextView) Utils.findRequiredViewAsType(view, R.id.userReviewText, "field 'mReview'", TextView.class);
//            reviewsHolder.mRecommendLabel = (TextView) Utils.findRequiredViewAsType(view, R.id.recommendTextLabel, "field 'mRecommendLabel'", TextView.class);
//        }
//
//        @Override // butterknife.Unbinder
//        public void unbind() {
//            ReviewsHolder reviewsHolder = this.target;
//            if (reviewsHolder == null) {
//                throw new IllegalStateException("Bindings already cleared.");
//            }
//            this.target = null;
//            reviewsHolder.mUserImage = null;
//            reviewsHolder.mUserName = null;
//            reviewsHolder.mReview = null;
//            reviewsHolder.mRecommendLabel = null;
//        }
//    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_reviews);
        init();
        fetchReviews();
    }

    private void init() {
        fetchResDetails();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        Objects.requireNonNull(currentUser);
        this.uid = currentUser.getUid();
        this.db = FirebaseFirestore.getInstance();
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.newReviewContainer);
        linearLayout.setVisibility(0);
        linearLayout.setOnClickListener(new View.OnClickListener() { // from class: ui.review.ReviewsActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ReviewsActivity.this.m1974lambda$init$0$uireviewReviewsActivity(view);
            }
        });
        ((ImageView) findViewById(R.id.cartBackBtn)).setOnClickListener(new View.OnClickListener() { // from class: ui.review.ReviewsActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ReviewsActivity.this.m1975lambda$init$1$uireviewReviewsActivity(view);
            }
        });
        this.mUserReviewRecyclerView = (RecyclerView) findViewById(R.id.userReviewRecyclerView);
        ((TextView) findViewById(R.id.resReviewText)).setText(this.resName);
        ((TextView) findViewById(R.id.resReviewPrice)).setText("Cost for one - ₹" + this.resPrice);
        ((ImageView) findViewById(R.id.callResBtn)).setOnClickListener(new View.OnClickListener() { // from class: ui.review.ReviewsActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ReviewsActivity.this.m1976lambda$init$2$uireviewReviewsActivity(view);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, 1, false);
        this.linearLayoutManager = linearLayoutManager;
        this.mUserReviewRecyclerView.setLayoutManager(linearLayoutManager);
    }

    /* renamed from: lambda$init$0$ui-review-ReviewsActivity  reason: not valid java name */
    public /* synthetic */ void m1974lambda$init$0$uireviewReviewsActivity(View view) {
        Intent intent = new Intent(this, NewReviewActivity.class);
        intent.putExtra("UID", this.uid);
        intent.putExtra("RES_NAME", this.resName);
        intent.putExtra("RUID", this.resUid);
        startActivity(intent);
    }

    /* renamed from: lambda$init$1$ui-review-ReviewsActivity  reason: not valid java name */
    public /* synthetic */ void m1975lambda$init$1$uireviewReviewsActivity(View view) {
        onBackPressed();
    }

    /* renamed from: lambda$init$2$ui-review-ReviewsActivity  reason: not valid java name */
    public /* synthetic */ void m1976lambda$init$2$uireviewReviewsActivity(View view) {
        Intent intent = new Intent("android.intent.action.DIAL");
        intent.setData(Uri.parse("tel:" + this.resNum));
        startActivity(intent);
    }

    private void fetchResDetails() {
        this.resUid = getIntent().getStringExtra("RUID");
        this.resName = getIntent().getStringExtra("NAME");
        this.resPrice = getIntent().getStringExtra("PRICE");
        this.resNum = getIntent().getStringExtra("NUM");
    }

    private void fetchReviews() {
        FirestoreRecyclerAdapter<ReviewDetails, ReviewsHolder> firestoreRecyclerAdapter = new FirestoreRecyclerAdapter<ReviewDetails, ReviewsHolder>(new FirestoreRecyclerOptions.Builder().setQuery(this.db.collection("RestaurantList").document(this.resUid).collection("Reviews"), ReviewDetails.class).build()) { // from class: ui.review.ReviewsActivity.1
            @Override // com.firebase.ui.firestore.FirestoreRecyclerAdapter
            public void onBindViewHolder(ReviewsHolder reviewsHolder, int i, ReviewDetails reviewDetails) {
                Glide.with(ReviewsActivity.this.getApplicationContext()).load(reviewDetails.getUser_image()).apply((BaseRequestOptions<?>) new RequestOptions().override(37, 37)).placeholder((int) R.drawable.user_placeholder).into(reviewsHolder.mUserImage);
                reviewsHolder.mUserName.setText(reviewDetails.getUser_name());
                reviewsHolder.mReview.setText(reviewDetails.getReview());
                String recommended = reviewDetails.getRecommended();
                if (recommended.equals("YES")) {
                    reviewsHolder.mRecommendLabel.setText("Recommended");
                    reviewsHolder.mRecommendLabel.setBackgroundDrawable(ContextCompat.getDrawable(ReviewsActivity.this.getApplicationContext(), R.drawable.recommended_background));
                } else if (recommended.equals("NO")) {
                    reviewsHolder.mRecommendLabel.setText("Not Recommended");
                    reviewsHolder.mRecommendLabel.setBackgroundDrawable(ContextCompat.getDrawable(ReviewsActivity.this.getApplicationContext(), R.drawable.not_recommended_background));
                }
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public ReviewsHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                return new ReviewsHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_review_layout, viewGroup, false));
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.firebase.ui.firestore.FirestoreRecyclerAdapter, com.firebase.ui.common.BaseChangeEventListener
            public void onError(FirebaseFirestoreException firebaseFirestoreException) {
                String message = firebaseFirestoreException.getMessage();
                Objects.requireNonNull(message);
                Log.e(Constants.IPC_BUNDLE_KEY_SEND_ERROR, message);
            }
        };
        firestoreRecyclerAdapter.startListening();
        firestoreRecyclerAdapter.notifyDataSetChanged();
        this.mUserReviewRecyclerView.setAdapter(firestoreRecyclerAdapter);
    }

    /* loaded from: classes4.dex */
    public static class ReviewsHolder extends RecyclerView.ViewHolder {
//        @BindView(R.id.recommendTextLabel)
        TextView mRecommendLabel;
//        @BindView(R.id.userReviewText)
        TextView mReview;
//        @BindView(R.id.userReviewProfileImage)
        CircleImageView mUserImage;
//        @BindView(R.id.userReviewName)
        TextView mUserName;

        public ReviewsHolder(View view) {
            super(view);
            mUserName = view.findViewById(R.id.userReviewName);
            mUserImage = view.findViewById(R.id.userReviewProfileImage);
            mReview = view.findViewById(R.id.userReviewText);
            mRecommendLabel = view.findViewById(R.id.recommendTextLabel);
//            ButterKnife.bind(this, view);
        }
    }
}
