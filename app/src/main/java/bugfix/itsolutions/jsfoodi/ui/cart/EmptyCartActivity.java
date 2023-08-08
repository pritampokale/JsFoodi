package bugfix.itsolutions.jsfoodi.ui.cart;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import bugfix.itsolutions.jsfoodi.R;

/* loaded from: classes4.dex */
public class EmptyCartActivity extends AppCompatActivity {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_empty_cart);
        init();
    }

    private void init() {
        ((ImageView) findViewById(R.id.cartBackBtn)).setOnClickListener(new View.OnClickListener() { // from class: ui.cart.EmptyCartActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                EmptyCartActivity.this.m1958lambda$init$0$uicartEmptyCartActivity(view);
            }
        });
    }

    /* renamed from: lambda$init$0$ui-cart-EmptyCartActivity  reason: not valid java name */
    public /* synthetic */ void m1958lambda$init$0$uicartEmptyCartActivity(View view) {
        onBackPressed();
    }
}
