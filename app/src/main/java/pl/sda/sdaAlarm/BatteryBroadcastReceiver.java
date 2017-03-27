package pl.sda.sdaAlarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BatteryBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent startMainActivity = new Intent(context, MainActivity.class);
        startMainActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(startMainActivity);
    }
}
