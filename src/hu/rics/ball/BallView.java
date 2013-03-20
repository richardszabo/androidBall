package hu.rics.ball;

import android.content.*;
import android.graphics.*;
import android.graphics.Paint.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.widget.*;

/**
 * I see spots!
 *
 * @author <a href="mailto:android@callmeike.net">Blake Meike</a>
 */
public class BallView extends View {

 	public static final String TAG = "Ball";
	
 	BallActivity parent;
	
    /**
     * @param context the rest of the application
     */
    public BallView(Context context) {
        super(context);
        setFocusableInTouchMode(true);
    }

	void setParent(BallActivity parent) {
		this.parent = parent;	
	}
	
    /**
     * @param context
     * @param attrs
     */
    public BallView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusableInTouchMode(true);
    }

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public BallView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFocusableInTouchMode(true);
    }

	float x;
	float y;
	
    /**
     * @param dots
     */
    public void setCoord(float x, float y) {  
		this.x = x;
		this.y = y;
		Log.i(TAG, "setCoord:" + x + ":"+ y +":");
		Bundle data = new Bundle();
		data.putFloat("xpos", 2.3f);
		data.putFloat("ypos", 3.4f);
		data.putString("alma", "jonatan");
		Message msg = Message.obtain();
		msg.setData(data);
		parent.rotvectEventHandler.sendMessage(msg);
		invalidate();
	}

    /**
     * @see android.view.View#onDraw(android.graphics.Canvas)
     */
    @Override protected void onDraw(Canvas canvas) {
        //canvas.drawColor(Color.WHITE);

        Paint paint = new Paint();
		paint.setColor(Color.GRAY);
		canvas.drawRect(0,0,canvas.getWidth(),canvas.getHeight(),paint);
		paint.setColor(Color.BLACK);
		canvas.drawRect(10,10,canvas.getWidth()-10,canvas.getHeight()-10,paint);
		paint.setColor(Color.BLUE);
        paint.setStyle(Style.FILL);
		int ypos = (int)(canvas.getWidth() * (1.0f + x)/2.0f);
		int xpos = (int)(canvas.getHeight() * (1.0f + y)/2.0f);
		Log.i(TAG, "onDraw:" + xpos + ":"+ ypos +":");
		//((TextView) findViewById(R.id.disxtext)).setText(Float.valueOf(4.5f).toString());
		if( xpos >= 0 && xpos <= canvas.getWidth() &&
		    ypos >= 0 && ypos <= canvas.getHeight()) {
			 canvas.drawCircle(
				 xpos,
				 ypos,
				 5,
				 paint);			 
		}
    }
}
