package hu.rics.ball;

import org.junit.Test;

import static org.junit.Assert.*;

public class BallUnitTest {
    double EPSILON = 0.01;
    float EXECUTION_RATE = 10.0f;

    @Test
    public void resetPosition_isCorrect() {
        int SAMPLE_WIDTH = 150;
        int SAMPLE_HEIGHT = 200;
        Ball ball = new Ball();
        ball.resetPosition(SAMPLE_WIDTH,SAMPLE_HEIGHT);
        assertEquals(ball.posX, SAMPLE_WIDTH/2,EPSILON);
        assertEquals(ball.posY, SAMPLE_HEIGHT/2,EPSILON);
    }

    @Test
    public void updatePosition_posXBelowZero() {
        Ball ball = new Ball();
        ball.posX = -1;
        ball.updatePosition(EXECUTION_RATE);
        assertEquals(ball.posX, 0,EPSILON);
    }

    @Test
    public void updatePosition_posYBelowZero() {
        Ball ball = new Ball();
        ball.posY = -1;
        ball.updatePosition(EXECUTION_RATE);
        assertEquals(ball.posY, 0,EPSILON);
    }

}