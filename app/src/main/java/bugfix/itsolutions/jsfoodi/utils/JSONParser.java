package bugfix.itsolutions.jsfoodi.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.browser.trusted.sharing.ShareTarget;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/* loaded from: classes4.dex */
public class JSONParser {
    static JSONObject jObj;
    private Context context;
    HttpURLConnection urlConnection = null;

    public JSONParser(Context context) {
        this.context = context;
    }

    public JSONObject makeHttpRequest(String str, String str2, String str3) {
        try {
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
                if (str2 == "POST") {
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setFixedLengthStreamingMode(str3.getBytes().length);
                    httpURLConnection.setRequestProperty("Content-Type", ShareTarget.ENCODING_TYPE_URL_ENCODED);
                    PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
                    printWriter.print(str3);
                    printWriter.close();
                }
                InputStream inputStream = httpURLConnection.getInputStream();
                byte[] bArr = new byte[10000];
                StringBuilder sb = new StringBuilder();
                while (true) {
                    int read = inputStream.read(bArr);
                    if (read < 0) {
                        break;
                    }
                    sb.append(new String(bArr, 0, read));
                }
                jObj = new JSONObject(sb.toString());
                HttpURLConnection httpURLConnection2 = this.urlConnection;
                if (httpURLConnection2 != null) {
                    httpURLConnection2.disconnect();
                }
                return jObj;
            } catch (Exception e) {
                e.printStackTrace();
                new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: utils.JSONParser$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        JSONParser.this.m1977lambda$makeHttpRequest$0$utilsJSONParser();
                    }
                });
                HttpURLConnection httpURLConnection3 = this.urlConnection;
                if (httpURLConnection3 != null) {
                    httpURLConnection3.disconnect();
                }
                return null;
            }
        } catch (Throwable th) {
            HttpURLConnection httpURLConnection4 = this.urlConnection;
            if (httpURLConnection4 != null) {
                httpURLConnection4.disconnect();
            }
            throw th;
        }
    }

    /* renamed from: lambda$makeHttpRequest$0$utils-JSONParser  reason: not valid java name */
    public /* synthetic */ void m1977lambda$makeHttpRequest$0$utilsJSONParser() {
        Toast.makeText(this.context, "Connectivity issue. Please try again later.", 1).show();
    }
}
