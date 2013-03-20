package hu.rics.ball;

import android.app.*;
import android.content.*;
import android.hardware.*;
import android.os.*;
import android.util.*;
import android.widget.*;
import java.util.*;

public class BallActivity extends Activity implements SensorEventListener {

 	public static final String TAG = "Ball";	
	private boolean hassensor;

	final Handler rotvectEventHandler 				= new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Bundle data = msg.getData();
			((TextView) findViewById(R.id.rotxtext)).setText(data.getString("x"));
			((TextView) findViewById(R.id.rotytext)).setText(data.getString("y"));
			((TextView) findViewById(R.id.rotztext)).setText(data.getString("z"));
			((TextView) findViewById(R.id.disxtext)).setText(Float.valueOf(data.getFloat("disx")).toString());
			((TextView) findViewById(R.id.disytext)).setText(Float.valueOf(data.getFloat("disy")).toString());			
			Log.i(TAG, "handle:" + data.getString("alma") + ":"+ data.getFloat("disy") +":");
			//((BallView) findViewById(R.id.ballview)).
			//	setCoord(data.getFloat("disx"),
			//			 data.getFloat("disy"));		
		}
	};

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getRotVectorSensors() == null) {
			hassensor = false;
			Toast.makeText(this, "No Rotational Vector Sensors Available", Toast.LENGTH_SHORT).show();
			finish();
			return;
		}

		hassensor = true;
		setContentView(R.layout.ball);
		((BallView) findViewById(R.id.ballview)).setParent(this);
		
		setTitle("Rotational Vector");
	}

	@Override
	protected void onResume() {
		super.onResume();
		if(hassensor) registerListener();
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
		if(hassensor) {
			SensorManager mngr = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
			mngr.unregisterListener(this);
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) { }

	@Override
	public void onSensorChanged(SensorEvent event) {
		float rvx = event.values[0];
		float rvy = event.values.length > 1 ? event.values[1] : 0;
		float rvz = event.values.length > 2 ? event.values[2] : 0;
		float disx = rvx / 0.57f;
		float disy = rvy / 0.57f;
		
		Bundle data = new Bundle();
		data.putString("x", "Rotational Vector X: "+rvx);
		data.putString("y", "Rotational Vector Y: "+rvy);
		data.putString("z", "Rotational Vector Z: "+rvz);
		//data.putFloat("disx", disx);
		//data.putFloat("disy", disy);
		((BallView) findViewById(R.id.ballview)).
			setCoord(disx,disy);		
		//Message msg = Message.obtain();
		//msg.setData(data);
		//rotvectEventHandler.sendMessage(msg);
	}
}

