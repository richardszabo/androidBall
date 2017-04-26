package hu.rics.ball;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BallUnitTest {
    Ball ball;
    double EPSILON = 0.01;
    float EXECUTION_RATE = 10.0f;
    int SAMPLE_WIDTH = 150;
    int SAMPLE_HEIGHT = 200;
    float SAMPLE_POS_X = 40.0f;
    float SAMPLE_POS_Y = 70.0f;
    float SAMPLE_ANGLE_X = 10.0f;
    float SAMPLE_ANGLE_Y = 20.0f;

    @Before
    public void setup() {
        ball = new Ball();
        ball.setArenaSize(SAMPLE_WIDTH,SAMPLE_HEIGHT);
    }

    @Test
    public void resetPosition_isCorrect() {
        ball.resetPosition();
        assertEquals(ball.posX, SAMPLE_WIDTH/2,EPSILON);
        assertEquals(ball.posY, SAMPLE_HEIGHT/2,EPSILON);
    }

    @Test
    public void updatePosition_posXBelowZero() {
        ball.posX = -1;
        ball.updatePosition(EXECUTION_RATE);
        assertEquals(ball.posX, 0,EPSILON);
    }

    @Test
    public void updatePosition_posYBelowZero() {
        ball.posY = -1;
        ball.updatePosition(EXECUTION_RATE);
        assertEquals(ball.posY, 0,EPSILON);
    }

    @Test
    public void updatePosition_posXOverLimit() {
        ball.posX = SAMPLE_WIDTH + 1;
        ball.updatePosition(EXECUTION_RATE);
        assertEquals(ball.posX, SAMPLE_WIDTH, EPSILON);
    }

    @Test
    public void updatePosition_posYOverLimit() {
        ball.posY = SAMPLE_HEIGHT + 1;
        ball.updatePosition(EXECUTION_RATE);
        assertEquals(ball.posY, SAMPLE_HEIGHT, EPSILON);
    }

    @Test
    public void calculateForce_doesNotChangePosition() {
        ball.posX = SAMPLE_POS_X;
        ball.posY = SAMPLE_POS_Y;
        ball.calculateForce(SAMPLE_ANGLE_X,SAMPLE_ANGLE_Y);
        assertEquals(ball.posX, SAMPLE_POS_X, EPSILON);
        assertEquals(ball.posY, SAMPLE_POS_Y, EPSILON);
    }

    @Test
    public void calculateAcceleration_doesNotChangePosition() {
        ball.posX = SAMPLE_POS_X;
        ball.posY = SAMPLE_POS_Y;
        ball.calculateAcceleration();
        assertEquals(ball.posX, SAMPLE_POS_X, EPSILON);
        assertEquals(ball.posY, SAMPLE_POS_Y, EPSILON);
    }

    @Test
    public void updateVelocity_doesNotChangePosition() {
        ball.posX = SAMPLE_POS_X;
        ball.posY = SAMPLE_POS_Y;
        ball.updateVelocity(EXECUTION_RATE);
        assertEquals(ball.posX, SAMPLE_POS_X, EPSILON);
        assertEquals(ball.posY, SAMPLE_POS_Y, EPSILON);
    }
}