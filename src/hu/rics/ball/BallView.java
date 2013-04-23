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
	
	void setParent(BallActivity parent) {
		this.parent = parent;	
	}

	boolean isAdditive = true;
	float x;
	float y;
	int xpos;
	int ypos;
	
    /**
     * @param dots
     */
    public void setCoord(float x, float y) {  
		this.x = x;
		this.y = y;
		Log.i(TAG, "setCoord:" + x + ":"+ y +":");
		invalidate();
	}

    int width;
    int height;
    
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        this.width = w;
        this.height = h;
		xpos = width / 2;
		ypos = height / 2;
        super.onSizeChanged(w, h, oldw, oldh);
	}
    
    /**
     * @see android.view.View#onDraw(android.graphics.Canvas)
     */
    @Override protected void onDraw(Canvas canvas) {
        //canvas.drawColor(Color.WHITE);

        Paint paint = new Paint();
		paint.setColor(Color.GREEN);
		canvas.drawRect(0,0,width,height,paint);
		paint.setColor(Color.BLACK);
		canvas.drawRect(10,10,width-10,height-10,paint);
		paint.setColor(Color.BLUE);
        paint.setStyle(Style.FILL);
		if( isAdditive ) {
			ypos += (int)(0.02 * height * x);
			xpos += (int)(0.02 * width * y);
		} else {
			ypos = (int)(height * (1.0f + x)/2.0f);
			xpos = (int)(width * (1.0f + y)/2.0f);
		}
		xpos = Math.min(width,Math.max(0,xpos));
		ypos = Math.min(height,Math.max(0,ypos));
		Log.i(TAG, "onDraw:" + xpos + "/"+ width +":" + ypos +"/" + height +":");
		/*Bundle data = new Bundle();
		data.putFloat("disx", xpos);
		data.putFloat("disy", ypos);
		Message msg = Message.obtain();
		msg.setData(data);
		parent.rotvectEventHandler.sendMessage(msg);*/
		if( xpos >= 0 && xpos <= width &&
		    ypos >= 0 && ypos <= height) {
			 canvas.drawCircle(
				 xpos,
				 ypos,
				 5,
				 paint);			 
		}
    }
}
