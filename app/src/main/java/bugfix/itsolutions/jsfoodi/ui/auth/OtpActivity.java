package bugfix.itsolutions.jsfoodi.ui.auth;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import bugfix.itsolutions.jsfoodi.MainActivity;
import bugfix.itsolutions.jsfoodi.R;
import in.aabhasjindal.otptextview.OTPListener;
import in.aabhasjindal.otptextview.OtpTextView;

/* loaded from: classes4.dex */
public class OtpActivity extends AppCompatActivity {
    private String deviceToken;
    private FirebaseAuth mAuth;
    private String mAuthVerificationId;
    private FirebaseUser mCurrentUser;
    private OtpTextView mOtpText;
    private Button mVerifyBtn;
    private String phoneNum;
    private String uid;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_otp);
        changestatusbarcolor();
        this.mAuthVerificationId = getIntent().getStringExtra("AuthCredentials");
        this.phoneNum = getIntent().getStringExtra("PhoneNumber");
        init();
        this.mOtpText.setOtpListener(new OTPListener() { // from class: ui.auth.OtpActivity.1
            @Override // in.aabhasjindal.otptextview.OTPListener
            public void onInteractionListener() {
            }

            @Override // in.aabhasjindal.otptextview.OTPListener
            public void onOTPComplete(String str) {
                OtpActivity.this.signInWithPhoneAuthCredential(PhoneAuthProvider.getCredential(OtpActivity.this.mAuthVerificationId, OtpActivity.this.mOtpText.getOTP()));
            }
        });
        this.mVerifyBtn.setOnClickListener(new View.OnClickListener() { // from class: ui.auth.OtpActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                OtpActivity.this.m1952lambda$onCreate$0$uiauthOtpActivity(view);
            }
        });
    }

    /* renamed from: lambda$onCreate$0$ui-auth-OtpActivity  reason: not valid java name */
    public /* synthetic */ void m1952lambda$onCreate$0$uiauthOtpActivity(View view) {
        String otp = this.mOtpText.getOTP();
        if (otp.isEmpty()) {
            Toast.makeText(this, "Please enter correct OTP", 1).show();
        } else {
            signInWithPhoneAuthCredential(PhoneAuthProvider.getCredential(this.mAuthVerificationId, otp));
        }
    }

    private void init() {
        final ArrayList arrayList = new ArrayList();
        Volley.newRequestQueue(this).add(new JsonArrayRequest(0, "https://jsfoodi.com/Jsfoodi_App/JsFoodi/fetchUser1.php", null, new Response.Listener<JSONArray>() { // from class: ui.auth.OtpActivity.2
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
                    try {
                        OtpActivity otpActivity = OtpActivity.this;
                        otpActivity.deviceToken = OtpActivity.this.deviceToken + ((JSONObject) arrayList.get(i)).getString("phone") + ", ";
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() { // from class: ui.auth.OtpActivity.3
            @Override // com.android.volley.Response.ErrorListener
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("Error.Response", volleyError.toString());
            }
        }));
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        this.mAuth = firebaseAuth;
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        this.mCurrentUser = currentUser;
        try {
            this.uid = currentUser.getUid();
        } catch (NullPointerException unused) {
        }
        this.mOtpText = (OtpTextView) findViewById(R.id.otpView);
        ((TextView) findViewById(R.id.userNum)).setText("We have sent you an OTP at " + this.phoneNum + " please enter the OTP.");
        this.mVerifyBtn = (Button) findViewById(R.id.verifyOTP);
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

    /* JADX INFO: Access modifiers changed from: private */
    public void signInWithPhoneAuthCredential(PhoneAuthCredential phoneAuthCredential) {
        this.mAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(this, new OnCompleteListener() { // from class: ui.auth.OtpActivity$$ExternalSyntheticLambda1
            @Override // com.google.android.gms.tasks.OnCompleteListener
            public final void onComplete(Task task) {
                OtpActivity.this.m1953lambda$signInWithPhoneAuthCredential$1$uiauthOtpActivity(task);
            }
        });
    }

    /* renamed from: lambda$signInWithPhoneAuthCredential$1$ui-auth-OtpActivity  reason: not valid java name */
    public /* synthetic */ void m1953lambda$signInWithPhoneAuthCredential$1$uiauthOtpActivity(Task task) {
        if (task.isSuccessful()) {
            try {
                Intent intent = new Intent(this, AddInfoActivity.class);
                intent.putExtra("PHONENUMBER", this.phoneNum);
                intent.putExtra("RUID", this.uid);
                intent.putExtra("TOKEN", this.deviceToken);
                intent.addFlags(67108864);
                intent.addFlags(32768);
                startActivity(intent);
                finish();
            } catch (NullPointerException unused) {
                Intent intent2 = new Intent(this, AddInfoActivity.class);
                intent2.putExtra("PHONENUMBER", this.phoneNum);
                intent2.putExtra("RUID", this.uid);
                intent2.putExtra("TOKEN", this.deviceToken);
                intent2.addFlags(67108864);
                intent2.addFlags(32768);
                startActivity(intent2);
                finish();
            }
            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                Toast.makeText(this, "Invalid OTP", 0).show();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStart() {
        super.onStart();
        if (this.mCurrentUser != null) {
            sendUserToMain();
        }
    }

    public void sendUserToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(67108864);
        intent.addFlags(32768);
        startActivity(intent);
        finish();
    }
}
