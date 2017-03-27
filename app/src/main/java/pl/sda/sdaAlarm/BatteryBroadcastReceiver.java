package pl.sda.sdaAlarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import java.util.Calendar;

public class BatteryBroadcastReceiver extends BroadcastReceiver {

    public static final int HOUR_DEF_VALUE = 22;
    public static final int MINUTES_DEF_VALUE = 0;
    private SharedPreferences sharedPreferences;

    @Override
    public void onReceive(Context context, Intent intent) {
        startMainActivity(context);
        sharedPreferences = context.getSharedPreferences(SettingsDialogFragment.TAG_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        Calendar settingsTime = prepareCalendar();
        if (System.currentTimeMillis() > settingsTime.getTimeInMillis()) {
            startMainActivity(context);
        }
    }

    @NonNull
    private Calendar prepareCalendar() {
        int hour = getHourFromSharedPreferences();
        int minutes = getMinutesFromSharePreferences();
        return getCalendar(hour, minutes);
    }

    private int getMinutesFromSharePreferences() {
        return sharedPreferences.getInt(SettingsDialogFragment.MINUTES_KEY, MINUTES_DEF_VALUE);
    }

    private int getHourFromSharedPreferences() {
        return sharedPreferences.getInt(SettingsDialogFragment.HOUR_KEY, HOUR_DEF_VALUE);
    }

    private void startMainActivity(Context context) {
        Intent startMainActivity = new Intent(context, MainActivity.class);
        startMainActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(startMainActivity);
    }

    @NonNull
    private Calendar getCalendar(int hour, int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minutes);
        return calendar;
    }
}
