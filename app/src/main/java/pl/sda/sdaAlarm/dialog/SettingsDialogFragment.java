package pl.sda.sdaAlarm.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TimePicker;

import pl.sda.sdaAlarm.R;

public class SettingsDialogFragment extends DialogFragment {

    public static final String TAG = SettingsDialogFragment.class.getSimpleName();
    public static final String TAG_SHARED_PREFERENCES = "alarm_shared_preferences";
    public static final String HOUR_KEY = "hour";
    public static final String MINUTES_KEY = "minutes";
    private SharedPreferences sharedPreferences;
    private TimePicker timePicker;

    public SettingsDialogFragment() {
        // Required empty public constructor
    }

    public static SettingsDialogFragment newInstance() {
        return new SettingsDialogFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Activity activity = getActivity();
        sharedPreferences = activity.getSharedPreferences(TAG_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        View view = activity.getLayoutInflater().inflate(R.layout.fragment_settings_dialog, null);
        timePicker = (TimePicker) view.findViewById(R.id.settingsDialogTimePicker);
        timePicker.setIs24HourView(true);

        setSavedValuesOnTimePicker();

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.settings_dialog_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveSettingsToSharedPreferences();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                })
                .create();
    }

    private void setSavedValuesOnTimePicker() {
        setSavedHourTimePicker();
        setSavedMinutesTimePicker();
    }

    private void setSavedMinutesTimePicker() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePicker.setMinute(getSavedMinutes());
        } else {
            timePicker.setCurrentMinute(getSavedMinutes());
        }
    }

    private void setSavedHourTimePicker() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePicker.setHour(getSavedHour());
        } else {
            timePicker.setCurrentHour(getSavedHour());
        }
    }

    public void saveSettingsToSharedPreferences() {
        int hour = getTimepickerHour();
        int minutes = getTimepickerMinutes();
        saveDataToSharedPreferences(hour, minutes);
    }

    private int getSavedMinutes() {
        return sharedPreferences.getInt(MINUTES_KEY, 0);
    }

    private int getSavedHour() {
        return sharedPreferences.getInt(HOUR_KEY, 0);
    }

    private int getTimepickerMinutes() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? timePicker.getMinute() : timePicker.getCurrentMinute();
    }

    private int getTimepickerHour() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? timePicker.getHour() : timePicker.getCurrentHour();
    }

    private void saveDataToSharedPreferences(int hour, int minutes) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(HOUR_KEY, hour);
        editor.putInt(MINUTES_KEY, minutes);
        editor.apply();
    }
}
