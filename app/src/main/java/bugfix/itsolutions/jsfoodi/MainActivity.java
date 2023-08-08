package bugfix.itsolutions.jsfoodi;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Objects;

import bugfix.itsolutions.jsfoodi.fragments.FavouriteFragment;
import bugfix.itsolutions.jsfoodi.fragments.MyProfileFragment;
import bugfix.itsolutions.jsfoodi.fragments.RestaurantFragment;
import bugfix.itsolutions.jsfoodi.ui.auth.AddInfoActivity;
import bugfix.itsolutions.jsfoodi.ui.auth.LoginActivity;

/* loaded from: classes.dex */
public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNav;
    String deviceToken;
    private FirebaseUser mCurrentUser;
    String uid;
    String ServerUploadPath1 = "https://jsfoodi.com/Jsfoodi_App/JsFoodi/updateDeviceTokenUser.php";
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() { // from class: bugfix.itsolutions.jsfoodi.MainActivity$$ExternalSyntheticLambda5
        @Override // com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener
        public final boolean onNavigationItemSelected(MenuItem menuItem) {
            return MainActivity.this.m41lambda$new$5$bugfixitsolutionsjsfoodiMainActivity(menuItem);
        }
    };

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$init$2(Exception exc) {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$init$3() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
//        final AppUpdateManager create = AppUpdateManagerFactory.create(getApplicationContext());
//        create.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener() { // from class: bugfix.itsolutions.jsfoodi.MainActivity$$ExternalSyntheticLambda4
//            @Override // com.google.android.gms.tasks.OnSuccessListener
//            public final void onSuccess(Object obj) {
//                MainActivity.this.m42lambda$onCreate$0$bugfixitsolutionsjsfoodiMainActivity(create, (AppUpdateInfo) obj);
//            }
//        });
        if (Build.VERSION.SDK_INT >= 33 && ActivityCompat.checkSelfPermission(this, "android.permission.POST_NOTIFICATIONS") != 0) {
            requestPermissions(new String[]{"android.permission.POST_NOTIFICATIONS"}, 1);
        }
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel notificationChannel = new NotificationChannel("myapp_notification", "MyApp", 4);
            notificationChannel.setShowBadge(true);
            notificationChannel.setDescription("");
            AudioAttributes build = new AudioAttributes.Builder().setUsage(5).setContentType(1).build();
            notificationChannel.setSound(Uri.parse("android.resource://" + getPackageName() + "/raw/order"), build);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{400, 1000, 400});
            notificationChannel.setLockscreenVisibility(1);
            ((NotificationManager) getSystemService(NotificationManager.class)).createNotificationChannel(notificationChannel);
        }
        changestatusbarcolor();
        init();
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        this.bottomNav = bottomNavigationView;
        bottomNavigationView.setOnNavigationItemSelectedListener(this.navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new RestaurantFragment()).commit();
    }

    /* renamed from: lambda$onCreate$0$bugfix-itsolutions-jsfoodi-MainActivity  reason: not valid java name */
    public /* synthetic */ void m42lambda$onCreate$0$bugfixitsolutionsjsfoodiMainActivity(AppUpdateManager appUpdateManager, AppUpdateInfo appUpdateInfo) {
        if (appUpdateInfo.updateAvailability() == 2 && appUpdateInfo.isUpdateTypeAllowed(1)) {
            try {
                appUpdateManager.startUpdateFlowForResult(appUpdateInfo, 1, this, 100);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        }
    }

    private void init() {
        this.mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        try {
            this.uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            FirebaseMessaging.getInstance().getToken().addOnSuccessListener(new OnSuccessListener() { // from class: bugfix.itsolutions.jsfoodi.MainActivity$$ExternalSyntheticLambda3
                @Override // com.google.android.gms.tasks.OnSuccessListener
                public final void onSuccess(Object obj) {
                    MainActivity.this.m40lambda$init$1$bugfixitsolutionsjsfoodiMainActivity((String) obj);
                }
            }).addOnFailureListener(new OnFailureListener() { // from class: bugfix.itsolutions.jsfoodi.MainActivity$$ExternalSyntheticLambda2
                @Override // com.google.android.gms.tasks.OnFailureListener
                public final void onFailure(Exception exc) {
                    MainActivity.lambda$init$2(exc);
                }
            }).addOnCanceledListener(new OnCanceledListener() { // from class: bugfix.itsolutions.jsfoodi.MainActivity$$ExternalSyntheticLambda0
                @Override // com.google.android.gms.tasks.OnCanceledListener
                public final void onCanceled() {
                    MainActivity.lambda$init$3();
                }
            }).addOnCompleteListener(new OnCompleteListener() { // from class: bugfix.itsolutions.jsfoodi.MainActivity$$ExternalSyntheticLambda1
                @Override // com.google.android.gms.tasks.OnCompleteListener
                public final void onComplete(Task task) {
                    Log.v("TAG", "This is the token : " + ((String) task.getResult()));
                }
            });
        } catch (NullPointerException unused) {
        }
    }

    /* renamed from: lambda$init$1$bugfix-itsolutions-jsfoodi-MainActivity  reason: not valid java name */
    public /* synthetic */ void m40lambda$init$1$bugfixitsolutionsjsfoodiMainActivity(String str) {
        if (!TextUtils.isEmpty(str)) {
            Log.d("TAG", "retrieve token successful : " + str);
            this.deviceToken = str;
            addUser();
            return;
        }
        Log.w("TAG", "token should not be null...");
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [bugfix.itsolutions.jsfoodi.MainActivity$1AsyncTaskUploadClass] */
    public void addUser() {
        new AsyncTask<Void, Void, String>() { // from class: bugfix.itsolutions.jsfoodi.MainActivity.1AsyncTaskUploadClass
            @Override // android.os.AsyncTask
            protected void onPreExecute() {
                super.onPreExecute();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public void onPostExecute(String str) {
                super.onPostExecute(str);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public String doInBackground(Void... voidArr) {
                AddInfoActivity.ImageProcessClass imageProcessClass = new AddInfoActivity.ImageProcessClass();
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("Id", FirebaseAuth.getInstance().getCurrentUser().getUid());
                hashMap.put("DeviceId", MainActivity.this.deviceToken);
                return imageProcessClass.ImageHttpRequest(MainActivity.this.ServerUploadPath1, hashMap);
            }
        }.execute(new Void[0]);
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

    /* renamed from: lambda$new$5$bugfix-itsolutions-jsfoodi-MainActivity  reason: not valid java name */
    public /* synthetic */ boolean m41lambda$new$5$bugfixitsolutionsjsfoodiMainActivity(MenuItem menuItem) {
        Fragment fragment = null;
        int itemId = menuItem.getItemId();
        try {
            if (itemId != R.id.imageBadgeView) {
                switch (itemId) {
                    case R.id.nav_favourites /* 2131362463 */:
                        fragment = new FavouriteFragment();
                        break;
                    case R.id.nav_profile /* 2131362464 */:
                        fragment = new MyProfileFragment();
                        break;
                    case R.id.nav_restaurants /* 2131362465 */:
                        fragment = new RestaurantFragment();
                        break;
                    case R.id.nav_search /* 2131362466 */:
                        sendUserToSearch();
                    default:
                        fragment = null;
                        break;
                }
                FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
                Objects.requireNonNull(fragment);
                Fragment fragment2 = fragment;
                beginTransaction.replace(R.id.fragmentContainer, fragment).commit();
                return true;
            }
            this.bottomNav.setVisibility(8);
            FragmentTransaction beginTransaction2 = getSupportFragmentManager().beginTransaction();
            Fragment fragment22 = fragment;
            beginTransaction2.replace(R.id.fragmentContainer, fragment).commit();
            return true;
        } catch (NullPointerException unused) {
            return true;
        }
    }

    private void sendUserToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(67108864);
        intent.addFlags(32768);
        startActivity(intent);
        finish();
    }

    private void sendUserToSearch() {
        Intent intent = new Intent(this, search_service.class);
        intent.addFlags(67108864);
        intent.addFlags(32768);
        startActivity(intent);
        finish();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment instanceof MyProfileFragment) {
                fragment.onActivityResult(i, i2, intent);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStart() {
        super.onStart();
        if (this.mCurrentUser == null) {
            sendUserToLogin();
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Do you really want to exit?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        finishAffinity();
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
    }
}
