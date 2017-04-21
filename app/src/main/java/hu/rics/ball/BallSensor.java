package hu.rics.ball;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

import java.util.List;

import static android.R.id.list;

/**
 * Created by rics on 2017.04.21..
 */

public class BallSensor implements SensorEventListener {
    BallActivity ballActivity;
    SensorManager mngr;
    List<Sensor> sensorList;

    BallSensor(BallActivity ballActivity) {
        this.ballActivity = ballActivity;
        initSensorManager();
    }

    private void initSensorManager() {
        mngr = (SensorManager) ballActivity.getSystemService(Context.SENSOR_SERVICE);
    }

    void registerListener() {
        getAppropriateSensors();
        if ( hasAppropriateSensor() ) {
            for (Sensor sensor : sensorList) {
                mngr.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI);
            }
        }
    }

    boolean hasAppropriateSensor() {
        return sensorList != null && !sensorList.isEmpty();
    }

    void unregisterListener() {
        if( mngr != null ) {
            mngr.unregisterListener(this);
        }
    }

    private void getAppropriateSensors() {
        if( mngr != null ) {
            sensorList = mngr.getSensorList(Sensor.TYPE_ROTATION_VECTOR);
            //List<Sensor> list = mngr.getSensorList(Sensor.TYPE_GAME_ROTATION_VECTOR); API level 18
            //https://developer.android.com/guide/topics/sensors/sensors_position.html#sensors-pos-geomrot API level 19
            //https://developer.android.com/guide/topics/sensors/sensors_position.html#sensors-pos-orient
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float angleX = sensorEvent.values[0];
        float angleY = sensorEvent.values.length > 1 ? sensorEvent.values[1] : 0;
        float angleZ = sensorEvent.values.length > 2 ? sensorEvent.values[2] : 0;
        float rotationMatrix[] = new float[9];
        SensorManager.getRotationMatrixFromVector(rotationMatrix, sensorEvent.values);
        float orientation[] = new float[3];
        SensorManager.getOrientation(rotationMatrix, orientation);
        ballActivity.setOrientationText(orientation);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
