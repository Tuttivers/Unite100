package eu.tuttivers.unite100;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class USSDRequest {

    private static final String BALANCE_NUMBER = "*500*1#";

    @SuppressLint("MissingPermission")
    public static void askBalance(Context context) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(ussdToCallableUri(BALANCE_NUMBER));
        context.startActivity(intent);
    }

    private static Uri ussdToCallableUri(String ussd) {
        String uriString = "";
        if (!ussd.startsWith("tel:")) {
            uriString += "tel:";
        }
        for (char c : ussd.toCharArray()) {
            if (c == '#')
                uriString += Uri.encode("#");
            else
                uriString += c;
        }
        return Uri.parse(uriString);
    }
}
