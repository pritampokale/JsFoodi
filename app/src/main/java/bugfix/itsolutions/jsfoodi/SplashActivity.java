package bugfix.itsolutions.jsfoodi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import bugfix.itsolutions.jsfoodi.ui.auth.LoginActivity;

/* loaded from: classes.dex */
public class SplashActivity extends AppCompatActivity {
    String str;
    private FirebaseAuth mAuth;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.splashscreen);

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
                try {
//                    mAuth = FirebaseAuth.getInstance();
//                    FirebaseUser mFirebaseUser = mAuth.getCurrentUser();
//                    if(mFirebaseUser != null) {
//                        str = mFirebaseUser.getUid(); //Do what you need to do with the id
//                    }

                    str = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    Log.e("dassad","str:-  " +str);

                } catch (NullPointerException unused) {
                    Log.e("dassad","dsasda:-  " + unused.getMessage());
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                    str = "empty";
                }
//            }
//        },3000);


        final ArrayList arrayList = new ArrayList();
        Volley.newRequestQueue(this).add(new JsonArrayRequest(0, "https://jsfoodi.com/Jsfoodi_App/JsFoodi/fetchUser.php?id=" + str, null, new Response.Listener<JSONArray>() { // from class: bugfix.itsolutions.jsfoodi.SplashActivity.1
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
                        ((JSONObject) arrayList.get(i)).getString("phone");
                        SplashActivity.this.startActivity(new Intent(SplashActivity.this.getApplicationContext(), MainActivity.class));
                        SplashActivity.this.finish();
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                        SplashActivity.this.startActivity(new Intent(SplashActivity.this.getApplicationContext(), LoginActivity.class));
                        SplashActivity.this.finish();
                    }
                }
            }
        }, new Response.ErrorListener() { // from class: bugfix.itsolutions.jsfoodi.SplashActivity.2
            @Override // com.android.volley.Response.ErrorListener
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("Error.Response", volleyError.toString());
                SplashActivity.this.startActivity(new Intent(SplashActivity.this.getApplicationContext(), LoginActivity.class));
                SplashActivity.this.finish();
            }
        }));
    }
}
