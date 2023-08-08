package bugfix.itsolutions.jsfoodi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsMessage;

/* loaded from: classes.dex */
public class SmsListener extends BroadcastReceiver {
    private SharedPreferences preferences;

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        Bundle extras;
        if (!intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED") || (extras = intent.getExtras()) == null) {
            return;
        }
        try {
            Object[] objArr = (Object[]) extras.get("pdus");
            int length = objArr.length;
            SmsMessage[] smsMessageArr = new SmsMessage[length];
            for (int i = 0; i < length; i++) {
                smsMessageArr[i] = SmsMessage.createFromPdu((byte[]) objArr[i]);
                smsMessageArr[i].getOriginatingAddress();
                smsMessageArr[i].getMessageBody();
            }
        } catch (Exception unused) {
        }
    }
}
