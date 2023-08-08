package bugfix.itsolutions.jsfoodi;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/* loaded from: classes.dex */
public class FCMService extends FirebaseMessagingService {
    @Override // com.google.firebase.messaging.FirebaseMessagingService
    public void onMessageReceived(RemoteMessage remoteMessage) {
        sendNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("body"));
    }

    private void sendNotification(String str, String str2) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(67108864);
        ((NotificationManager) getSystemService("notification")).notify(0, new NotificationCompat.Builder(this).setSmallIcon(R.drawable.jsfoodi).setContentTitle(str).setContentText(str2).setAutoCancel(true).setVibrate(new long[]{500, 500, 500, 500, 500}).setLights(-16776961, 1, 1).setSound(RingtoneManager.getDefaultUri(2)).setContentIntent(PendingIntent.getActivity(this, 0, intent, 67108864)).build());
    }

    @Override // com.google.firebase.messaging.FirebaseMessagingService
    public void onNewToken(String str) {
        Log.e("fafaafsffa","newtoken" + str);

        FirebaseMessaging.getInstance().subscribeToTopic("your topic name here").addOnSuccessListener(new OnSuccessListener<Void>() { // from class: bugfix.itsolutions.jsfoodi.FCMService.1
            @Override // com.google.android.gms.tasks.OnSuccessListener
            public void onSuccess(Void r1) {
            }
        });
        super.onNewToken(str);
    }
}
