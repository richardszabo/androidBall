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

public class BallActivity extends Activity {

 	public static final String TAG = "Ball";	
	final float executionRate = 0.001f; // in sec
	Ball ball;
	BallSensor ballSensor;

	final Handler coordinatorChangeHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			((BallView) findViewById(R.id.ballview)).setCoord(ball.posX,ball.posY);
		}
	};

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		ballSensor = new BallSensor(this);

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
		ballSensor.registerListener();
		if( !ballSensor.hasAppropriateSensor() ) {
			Toast.makeText(this, "No Rotational Vector Sensors Available", Toast.LENGTH_SHORT).show();
			finish();
			return;
		}

	}

	@Override
	protected void onPause() {
		super.onPause();
		ballSensor.unregisterListener();
	}

	void setOrientationText(float orientation[]) {
		((TextView) findViewById(R.id.azimuthtext)).setText(String.format("Azimuth: %2.4f", orientation[0]));
		((TextView) findViewById(R.id.pitchtext)).setText(String.format("Pitch:   %2.4f", orientation[1]));
		((TextView) findViewById(R.id.rolltext)).setText(String.format("Roll:    %2.4f", orientation[2]));
		ball.calculateForce(-1 * orientation[1], orientation[2]);
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

