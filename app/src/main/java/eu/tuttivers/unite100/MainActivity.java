package eu.tuttivers.unite100;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.SEND_SMS;

public class MainActivity extends AppCompatActivity {

    private static final int CALL_REQ_CODE = 101;
    private static final int SMS_REQ_CODE = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.fab).setOnClickListener(view -> {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1 && noPermission(CALL_PHONE)) {
                requestPermission(CALL_PHONE, CALL_REQ_CODE);
            } else {
                USSDRequest.askBalance(this);
            }
        });
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1 && noPermission(SEND_SMS)) {
            requestPermission(SEND_SMS, SMS_REQ_CODE);
        }
    }

    public void showConfirmDialog(View view) {
        DialogFragment fragment = ConfirmOptionActivationDialogFragment.newInstance(((TextView) view).getText().toString(), view.getTag().toString());
        fragment.show(getSupportFragmentManager(), null);
    }

    private boolean noPermission(String permission) {
        return ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED;
    }

    private void requestPermission(String permission, int requestCode) {
        ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CALL_REQ_CODE:
                USSDRequest.askBalance(this);
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


}
