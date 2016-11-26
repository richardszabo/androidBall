package hu.rics.ball;

import android.util.Log;

import static android.R.attr.angle;
import static android.R.attr.radius;

/**
 * Created by rics on 2016.11.26..
 */

public class Ball {
    float mass = 1;
    final float g = 10; // gravity
    // TODO use renderscript for vector coordinates?
    double slopeForceX;
    double slopeForceY;
    double accelX;
    double accelY;
    double speedX;
    double speedY;
    // position is limited to [0,width], [0,height] properties
    int width;
    int height;
    double posX;
    double posY;
    final int pixelsToMove = 150; // how many pixels to move by force

    void calculateForce(float angleX, float angleY) {
        // slope: m*g*sin(slopeAngle), rolling: 2/7* m*g*sin(slopeAngle), given that torque for sphere (Theta) is 2/5*m*r^2
        //Log.i("Ball","angle:" + angleX + ":" + angleY);
        slopeForceX = mass * g * Math.sin(angleY) * 5 / 7;
        slopeForceY = mass * g * Math.sin(angleX) * 5 / 7;
    }

    void calculateAcceleration() {
        // acceleration
        accelX = slopeForceX/mass;
        accelY = slopeForceY/mass;
    }

    /**
     *
     * @param executionRate in sec
     */
    void updateVelocity(float executionRate) {
        speedX += accelX * executionRate;
        speedY += accelY * executionRate;
        //Log.i("Ball","speed:" + speedX + ":" + speedY);
    }

    void resetPosition(int w, int h) {
        width = w;
        height = h;
        posX = w/2;
        posY = h/2;
    }

    /**
     *
     * @param executionRate in sec
     */
    void updatePosition(float executionRate) {
        if( posX < 0 ) {
            posX = 0;
            speedX = 0;
        }
        if( posX > width ) {
            posX = width;
            speedX = 0;
        }
        if( posY < 0 ) {
            posY = 0;
            speedY = 0;
        }
        if( posY > height ) {
            posY = height;
            speedY = 0;
        }

        posX += pixelsToMove * speedX * executionRate;
        posY += pixelsToMove * speedY * executionRate;
    }

}
