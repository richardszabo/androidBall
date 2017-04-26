package hu.rics.ball;

import org.junit.Test;

import static org.junit.Assert.*;

public class BallUnitTest {
    double EPSILON = 0.01;

    @Test
    public void resetPosition_isCorrect() throws Exception {
        int SAMPLE_WIDTH = 150;
        int SAMPLE_HEIGHT = 200;
        Ball ball = new Ball();
        ball.resetPosition(SAMPLE_WIDTH,SAMPLE_HEIGHT);
        assertEquals(ball.posX, SAMPLE_WIDTH/2,EPSILON);
        assertEquals(ball.posY, SAMPLE_HEIGHT/2,EPSILON);
    }
}