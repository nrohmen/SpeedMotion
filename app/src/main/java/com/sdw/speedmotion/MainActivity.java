package com.sdw.speedmotion;

import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.github.anastr.speedviewlib.base.Gauge;
import com.samsung.android.sdk.SsdkUnsupportedException;
import com.samsung.android.sdk.motion.Smotion;
import com.samsung.android.sdk.motion.SmotionPedometer;

public class MainActivity extends AppCompatActivity {
    private Smotion mMotion;
    private SmotionPedometer mPedometer;
    private Gauge speedView;
    private TextView textCalorie;
    private TextView textDistance;
    private TextView textCount;
    private TextView textStatus;

    final SmotionPedometer.ChangeListener changeListener =
            new SmotionPedometer.ChangeListener() {
                @Override
                public void onChanged(SmotionPedometer.Info info) {
                    double speed = info.getSpeed() * 1.60934;
                    textCalorie.setText(String.valueOf(info.getCalorie()));
                    textDistance.setText(String.valueOf(info.getDistance()));
                    textCount.setText(String.valueOf(info.getCount(SmotionPedometer.Info.COUNT_TOTAL)));
                    textStatus.setText(getStatus(info.getStatus()));
                    speedView.speedTo((float) speed);
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        speedView = findViewById(R.id.speedometer);
        textCalorie = findViewById(R.id.calorie);
        textCount = findViewById(R.id.step_count);
        textStatus = findViewById(R.id.status);
        textDistance = findViewById(R.id.distance);

        mMotion = new Smotion();
        try {
            mMotion.initialize(this);
        } catch (SsdkUnsupportedException e) {
            e.printStackTrace();
        }

        mPedometer = new SmotionPedometer(Looper.getMainLooper(), mMotion);
        mPedometer.start(changeListener);

    }

    private String getStatus(int status) {
        String str = null;
        switch (status) {
            case SmotionPedometer.Info.STATUS_WALK_UP:
                str = "Walk Up";
                break;
            case SmotionPedometer.Info.STATUS_WALK_DOWN:
                str = "Walk Down";
                break;
            case SmotionPedometer.Info.STATUS_WALK_FLAT:
                str = "Walk";
                break;
            case SmotionPedometer.Info.STATUS_RUN_DOWN:
                str = "Run Down";
                break;
            case SmotionPedometer.Info.STATUS_RUN_UP:
                str = "Run Up";
                break;
            case SmotionPedometer.Info.STATUS_RUN_FLAT:
                str = "Run";
                break;
            case SmotionPedometer.Info.STATUS_STOP:
                str = "Stop";
                break;
            case SmotionPedometer.Info.STATUS_UNKNOWN:
                str = "Unknown";
                break;
            default:
                break;
        }
        return str;
    }

}
