package pl.sda.sdaAlarm;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AlarmActivity extends AppCompatActivity {

    private static final String TAG = AlarmActivity.class.getSimpleName();
    private Animation animation;
    private MediaPlayer ringtone;
    private Vibrator vibrator;

    @BindView(R.id.alarmActivityLayout)
    View mainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        ButterKnife.bind(this);
        setupAlarmComponents();
        startAlarm();
    }

    private void setupAlarmComponents() {
        setupRingtone();
        setupVibration();
        setupAnimation();
    }

    private void setupAnimation() {
        animation = new AlphaAnimation(1, 0);
        animation.setDuration(500);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.REVERSE);
    }

    private void setupVibration() {
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
    }

    private void startAlarm() {
        startSound();
        startVibration();
        startAnimation();
    }

    private void startAnimation() {
        mainView.setAnimation(animation);
    }

    private void startVibration() {
        long[] pattern = {0, 100, 1000};
        if (vibrator != null) {
            vibrator.vibrate(pattern, 0);
        }
    }

    private void stopVibration() {
        if (vibrator != null) {
            vibrator.cancel();
        }
    }

    private void setupRingtone() {
        Uri path = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.alarm);
        ringtone = MediaPlayer.create(getApplicationContext(), path);
        ringtone.setLooping(true);
        ringtone.setVolume(1.0f, 1.0f);
        setMaxVolume();

//        RingtoneManager.setActualDefaultRingtoneUri(getApplicationContext(),
//                RingtoneManager.TYPE_ALARM, path);
//        ringtone = RingtoneManager.getRingtone(this, path);
//        ringtone.setStreamType(AudioManager.STREAM_ALARM);
    }

    private void setMaxVolume() {
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
    }

    private void startSound() {
        if (ringtone != null) {
            ringtone.start();
        }
    }

    @OnClick(R.id.alarmActivityLayout)
    public void stopAlarm() {
        stopSound();
        stopVibration();
        onBackPressed(); // lub finish();
    }

    private void stopSound() {
        if (ringtone.isPlaying() && ringtone != null) {
            ringtone.stop();
        } else {
            Log.d(TAG, "Ringtone is null or not playing");
        }
    }

}
