package com.sdw.speedmotion;

import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.anastr.speedviewlib.base.Gauge;
import com.samsung.android.sdk.SsdkUnsupportedException;
import com.samsung.android.sdk.motion.Smotion;
import com.samsung.android.sdk.motion.SmotionPedometer;

public class MainActivity extends AppCompatActivity {
    private Smotion mMotion;
    private SmotionPedometer mPedometer;
    private Gauge speedView;

    final SmotionPedometer.ChangeListener changeListener =
            new SmotionPedometer.ChangeListener() {
                @Override
                public void onChanged(SmotionPedometer.Info info) {
                    double speed = info.getSpeed();
                    speedView.speedTo((float) speed);
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        speedView = (Gauge) findViewById(R.id.speedometer);
        mMotion = new Smotion();
        try {
            mMotion.initialize(this);
        } catch (SsdkUnsupportedException e) {
            e.printStackTrace();
        }

        mPedometer = new SmotionPedometer(Looper.getMainLooper(), mMotion);
        mPedometer.start(changeListener);

    }


}
