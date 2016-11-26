package hu.rics.ball;

import android.app.*;
import android.content.*;
import android.hardware.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import hu.rics.ball.*;
import java.util.*;

import static android.R.attr.angle;
import static android.R.attr.data;
import static android.R.attr.x;
import static android.R.attr.y;

public class BallActivity extends Activity implements SensorEventListener {

 	public static final String TAG = "Ball";	
	private boolean hasSensor;
	final float executionRate = 0.001f; // in sec
	Ball ball;

	final Handler coordinatorChangeHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			((BallView) findViewById(R.id.ballview)).setCoord(ball.posX,ball.posY);
		}
	};

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getRotVectorSensors() == null) {
			hasSensor = false;
			Toast.makeText(this, "No Rotational Vector Sensors Available", Toast.LENGTH_SHORT).show();
			finish();
			return;
		}

		hasSensor = true;
		setContentView(R.layout.ball);
		((BallView) findViewById(R.id.ballview)).setParent(this);

		setTitle("Rotational Vector");
		ball = new Ball();
		Timer timer = new Timer();
		TimerTask timertask = new TimerTask() {
			public void run() {
				ball.calculateAcceleration();
				ball.updateVelocity(executionRate);
				ball.updatePosition(executionRate);
				//Log.i(TAG, "run:" + ball.posX + ":"+ ball.posY +":");
				coordinatorChangeHandler.sendEmptyMessage(0);
			}
		};
		timer.schedule(timertask,0,(int)(executionRate*1000));
	}

	@Override
	protected void onResume() {
		super.onResume();
		if(hasSensor) registerListener();
	}

	@Override
	protected void onPause() {
		super.onPause();
		unregisterListener();
	}

	private List<Sensor> getRotVectorSensors() {
		SensorManager mngr = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		List<Sensor> list = mngr.getSensorList(Sensor.TYPE_ROTATION_VECTOR);
		return list != null && !list.isEmpty() ? list : null;
	}

	private void registerListener() {
		SensorManager mngr = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		List<Sensor> list = getRotVectorSensors();
		if(list != null) {
			for(Sensor sensor: list) {
				mngr.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI);
			}
		}
	}

	private void unregisterListener() {
		if(hasSensor) {
			SensorManager mngr = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
			mngr.unregisterListener(this);
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) { }

	@Override
	public void onSensorChanged(SensorEvent event) {
		float angleX = event.values[0];
		float angleY = event.values.length > 1 ? event.values[1] : 0;
		float angleZ = event.values.length > 2 ? event.values[2] : 0;
		float rotationMatrix[] = new float[9];
		SensorManager.getRotationMatrixFromVector(rotationMatrix,event.values);
		float orientation[] = new float[3];
		SensorManager.getOrientation(rotationMatrix,orientation);
		((TextView) findViewById(R.id.azimuthtext)).setText(String.format("Azimuth: %2.4f",orientation[0]));
		((TextView) findViewById(R.id.pitchtext)).setText(String.format(  "Pitch:   %2.4f",orientation[1]));
		((TextView) findViewById(R.id.rolltext)).setText(String.format(   "Roll:    %2.4f",orientation[2]));
		ball.calculateForce(-1 * orientation[1],orientation[2]);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.ball, menu);
		return true;
	}

    @Override
	// TODO delete menu
    public boolean onOptionsItemSelected(MenuItem item) {
		BallView bv = ((BallView) findViewById(R.id.ballview));
        switch (item.getItemId()) {
			case R.id.additive:
				return true;
			case R.id.nonadd:
				return true;
        }
        return false;
    }
	
}

