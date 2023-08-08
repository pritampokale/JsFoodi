package bugfix.itsolutions.jsfoodi.ui.order;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.airbnb.lottie.LottieAnimationView;

import bugfix.itsolutions.jsfoodi.R;

/* loaded from: classes4.dex */
public class OrderSuccessfulActivity extends AppCompatActivity {
    private static int TIME_OUT = 5000;
    private LottieAnimationView mSuccessAnimation;
    private MediaPlayer mp;
    private String resUid;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_order_successful);
        init();
        changestatusbarcolor();
    }

    private void init() {
        this.resUid = getIntent().getStringExtra("RES_UID");
        this.mp = MediaPlayer.create(this, (int) R.raw.order);
        LottieAnimationView lottieAnimationView = (LottieAnimationView) findViewById(R.id.successAnim);
        this.mSuccessAnimation = lottieAnimationView;
        lottieAnimationView.setSpeed(0.8f);
        this.mSuccessAnimation.playAnimation();
        if (!this.mp.isPlaying()) {
            this.mp.start();
        } else {
            this.mp.stop();
        }
        moveToOrdersScreen();
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

    private void moveToOrdersScreen() {
        new Handler().postDelayed(new Runnable() { // from class: ui.order.OrderSuccessfulActivity$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                OrderSuccessfulActivity.this.m1968lambda$moveToOrdersScreen$0$uiorderOrderSuccessfulActivity();
            }
        }, TIME_OUT);
    }

    /* renamed from: lambda$moveToOrdersScreen$0$ui-order-OrderSuccessfulActivity  reason: not valid java name */
    public /* synthetic */ void m1968lambda$moveToOrdersScreen$0$uiorderOrderSuccessfulActivity() {
        Intent intent = new Intent(this, CurrentOrderActivity.class);
        intent.putExtra("RES_UID", this.resUid);
        startActivity(intent);
        this.mp.reset();
        this.mp.release();
        intent.addFlags(67108864);
        intent.addFlags(32768);
        finish();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        this.mSuccessAnimation.cancelAnimation();
    }
}
