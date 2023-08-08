package bugfix.itsolutions.jsfoodi.ui.review;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hsalf.smileyrating.SmileyRating;

import java.util.HashMap;
import java.util.Objects;

import bugfix.itsolutions.jsfoodi.MainActivity;
import bugfix.itsolutions.jsfoodi.R;

/* loaded from: classes4.dex */
public class NewReviewActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private RadioButton mNotRecommendBtn;
    private RadioButton mRecommendBtn;
    private EditText mReviewEditText;
    private SmileyRating ratingBar;
    private String recommendText;
    private String resUid;
    private String uid;
    private String userImage;
    private String userName;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_new_review);
        changestatusbarcolor();
        init();
        fetchUserDetails();
    }

    private void init() {
        this.uid = getIntent().getStringExtra("UID");
        this.resUid = getIntent().getStringExtra("RUID");
        String stringExtra = getIntent().getStringExtra("RES_NAME");
        this.db = FirebaseFirestore.getInstance();
        this.ratingBar = (SmileyRating) findViewById(R.id.smiley_rating);
        this.mReviewEditText = (EditText) findViewById(R.id.reviewEditText);
        ((ImageView) findViewById(R.id.cartBackBtn)).setOnClickListener(new View.OnClickListener() { // from class: ui.review.NewReviewActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                NewReviewActivity.this.m1971lambda$init$0$uireviewNewReviewActivity(view);
            }
        });
        ((TextView) findViewById(R.id.recommendLabel)).setText("Would you recommend " + stringExtra + " ?");
        this.mRecommendBtn = (RadioButton) findViewById(R.id.recommend);
        this.mNotRecommendBtn = (RadioButton) findViewById(R.id.notrecommend);
        ((Button) findViewById(R.id.saveReviewBtn)).setOnClickListener(new View.OnClickListener() { // from class: ui.review.NewReviewActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                NewReviewActivity.this.m1972lambda$init$1$uireviewNewReviewActivity(view);
            }
        });
    }

    /* renamed from: lambda$init$0$ui-review-NewReviewActivity  reason: not valid java name */
    public /* synthetic */ void m1971lambda$init$0$uireviewNewReviewActivity(View view) {
        onBackPressed();
    }

    /* renamed from: lambda$init$1$ui-review-NewReviewActivity  reason: not valid java name */
    public /* synthetic */ void m1972lambda$init$1$uireviewNewReviewActivity(View view) {
        uploadReviewDetails();
    }

    private void fetchUserDetails() {
        this.db.collection("UserList").document(this.uid).get().addOnCompleteListener(new OnCompleteListener() { // from class: ui.review.NewReviewActivity$$ExternalSyntheticLambda2
            @Override // com.google.android.gms.tasks.OnCompleteListener
            public final void onComplete(Task task) {
                NewReviewActivity.this.m1970lambda$fetchUserDetails$2$uireviewNewReviewActivity(task);
            }
        });
    }

    /* renamed from: lambda$fetchUserDetails$2$ui-review-NewReviewActivity  reason: not valid java name */
    public /* synthetic */ void m1970lambda$fetchUserDetails$2$uireviewNewReviewActivity(Task task) {
        if (task.isSuccessful()) {
            DocumentSnapshot documentSnapshot = (DocumentSnapshot) task.getResult();
            Objects.requireNonNull(documentSnapshot);
            this.userName = (String) documentSnapshot.get("name");
            this.userImage = (String) documentSnapshot.get("user_profile_image");
        }
    }

    private void uploadReviewDetails() {
        if (TextUtils.isEmpty(this.mReviewEditText.getText()) || this.ratingBar.getSelectedSmiley().getRating() == -1 || (!this.mRecommendBtn.isChecked() && !this.mNotRecommendBtn.isChecked())) {
            Toast.makeText(this, "Please fill the review properly", 0).show();
            return;
        }
        if (this.mRecommendBtn.isChecked()) {
            this.recommendText = "YES";
        } else if (this.mNotRecommendBtn.isChecked()) {
            this.recommendText = "NO";
        }
        int rating = this.ratingBar.getSelectedSmiley().getRating();
        String obj = this.mReviewEditText.getText().toString();
        HashMap hashMap = new HashMap();
        hashMap.put("user_name", this.userName);
        hashMap.put("user_image", this.userImage);
        hashMap.put("uid", this.uid);
        hashMap.put("rating", String.valueOf(rating));
        hashMap.put("recommended", this.recommendText);
        hashMap.put("review", obj);
        this.db.collection("RestaurantList").document(this.resUid).collection("Reviews").document().set(hashMap).addOnCompleteListener(new OnCompleteListener() { // from class: ui.review.NewReviewActivity$$ExternalSyntheticLambda3
            @Override // com.google.android.gms.tasks.OnCompleteListener
            public final void onComplete(Task task) {
                NewReviewActivity.this.m1973lambda$uploadReviewDetails$3$uireviewNewReviewActivity(task);
            }
        });
    }

    /* renamed from: lambda$uploadReviewDetails$3$ui-review-NewReviewActivity  reason: not valid java name */
    public /* synthetic */ void m1973lambda$uploadReviewDetails$3$uireviewNewReviewActivity(Task task) {
        if (task.isSuccessful()) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
            Toast.makeText(getApplicationContext(), "Review Uploaded", 0).show();
        }
    }

    private void changestatusbarcolor() {
        Window window = getWindow();
        window.clearFlags(67108864);
        window.addFlags(Integer.MIN_VALUE);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        if (Build.VERSION.SDK_INT >= 23) {
            getWindow().getDecorView().setSystemUiVisibility(8192);
        }
    }
}
