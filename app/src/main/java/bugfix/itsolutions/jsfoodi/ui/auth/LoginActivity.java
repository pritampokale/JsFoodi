package bugfix.itsolutions.jsfoodi.ui.auth;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

import bugfix.itsolutions.jsfoodi.MainActivity;
import bugfix.itsolutions.jsfoodi.R;

/* loaded from: classes4.dex */
public class LoginActivity extends AppCompatActivity {
    private CountryCodePicker countryCodePicker;
    private String finalPhoneNumber;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks;
    private FirebaseUser mCurrentUser;
    private EditText mNumberText;
    private Button mSendOtpBtn;
    private ProgressBar progressBar;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_login);
        initialiseViews();
        this.progressBar.setVisibility(8);
        changestatusbarcolor();
        this.mSendOtpBtn.setOnClickListener(new View.OnClickListener() { // from class: ui.auth.LoginActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {

//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                intent.addFlags(67108864);
//                intent.addFlags(32768);
//                startActivity(intent);
//                finish();
                LoginActivity.this.m1950lambda$onCreate$0$uiauthLoginActivity(view);
            }
        });
        this.mCallBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() { // from class: ui.auth.LoginActivity.1
            @Override // com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                LoginActivity.this.signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override // com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
            public void onVerificationFailed(FirebaseException firebaseException) {
                Log.e("verification","onVerificationFailed:-  " + firebaseException.getMessage());
//                Intent intent = new Intent(LoginActivity.this, OtpActivity.class);
//                intent.putExtra("AuthCredentials", str);
//                intent.putExtra("PhoneNumber", LoginActivity.this.finalPhoneNumber);
//                LoginActivity.this.startActivity(intent);
                Toast.makeText(LoginActivity.this, "Verification Failed, please try again!", 1).show();
            }

            @Override // com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
            public void onCodeSent(String str, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(str, forceResendingToken);
                Intent intent = new Intent(LoginActivity.this, OtpActivity.class);
                intent.putExtra("AuthCredentials", str);
                intent.putExtra("PhoneNumber", LoginActivity.this.finalPhoneNumber);
                LoginActivity.this.startActivity(intent);
            }
        };
    }

    /* renamed from: lambda$onCreate$0$ui-auth-LoginActivity  reason: not valid java name */
    public /* synthetic */ void m1950lambda$onCreate$0$uiauthLoginActivity(View view) {
        this.mSendOtpBtn.setVisibility(8);
        this.progressBar.setVisibility(0);
        String obj = this.mNumberText.getText().toString();
        String selectedCountryCodeWithPlus = this.countryCodePicker.getSelectedCountryCodeWithPlus();
        this.finalPhoneNumber = selectedCountryCodeWithPlus + obj;
        if (obj.isEmpty() || selectedCountryCodeWithPlus.isEmpty()) {
            Toast.makeText(this, "Please Enter Correct Credentials", 1).show();
        } else {
            PhoneAuthProvider.getInstance().verifyPhoneNumber(this.finalPhoneNumber, 60L, TimeUnit.SECONDS, this, this.mCallBacks);
        }
    }

    private void initialiseViews() {
        this.mSendOtpBtn = (Button) findViewById(R.id.sendOtpBtn);
        this.progressBar = (ProgressBar) findViewById(R.id.progressBar);
        this.mNumberText = (EditText) findViewById(R.id.loginInput);
        this.countryCodePicker = (CountryCodePicker) findViewById(R.id.countryCodeHolder);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        this.mAuth = firebaseAuth;
        this.mCurrentUser = firebaseAuth.getCurrentUser();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void signInWithPhoneAuthCredential(PhoneAuthCredential phoneAuthCredential) {
        this.mAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(this, new OnCompleteListener() { // from class: ui.auth.LoginActivity$$ExternalSyntheticLambda1
            @Override // com.google.android.gms.tasks.OnCompleteListener
            public final void onComplete(Task task) {
                LoginActivity.this.m1951lambda$signInWithPhoneAuthCredential$1$uiauthLoginActivity(task);
            }
        });
    }

    /* renamed from: lambda$signInWithPhoneAuthCredential$1$ui-auth-LoginActivity  reason: not valid java name */
    public /* synthetic */ void m1951lambda$signInWithPhoneAuthCredential$1$uiauthLoginActivity(Task task) {
        if (task.isSuccessful()) {
            sendUserToMain();
        } else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
            Toast.makeText(this, "Invalid OTP", 0).show();
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
