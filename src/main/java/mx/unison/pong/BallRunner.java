package mx.unison.pong;

import java.awt.geom.Ellipse2D;
import javax.swing.Timer;

public class BallRunner {
    public static final int MAX_X = 640;
    public static final int MAX_Y = 480;
    public static final int SIGN = -1;
    public static final int DX = 20;
    public static final int DY = 20;

    private final Ellipse2D.Double ball;
    private final Paddle paddle;
    private final Timer timer;
    private int ballX;
    private int ballY;
    private int directionX = 1;
    private int directionY = 1;
    private GameState state = GameState.PAUSED;

    public BallRunner(Ellipse2D.Double shape, Paddle paddle, Runnable afterUpdate) {
        ball = shape;
        this.paddle = paddle;
        ballX = 320;
        ballY = 240;
        ball.x = ballX;
        ball.y = ballY;
        timer = new Timer(150, event -> {
            update();
            afterUpdate.run();
        });
        timer.setCoalesce(true);
    }

    public void start() {
        if (state == GameState.PAUSED) {
            state = GameState.RUNNING;
            timer.start();
        }
    }

    public void pause() {
        if (state == GameState.RUNNING) {
            state = GameState.PAUSED;
            timer.stop();
        }
    }

    public void resume() {
        start();
    }

    public void stop() {
        state = GameState.STOPPED;
        timer.stop();
    }

    public GameState getState() {
        return state;
    }

    private void update() {
        if (state != GameState.RUNNING) {
            return;
        }

        if (paddle.intersects(ball)) {
            directionY *= SIGN;
        }

        if (ball.getMinY() < 0 || ball.getMaxY() > MAX_Y) {
            directionY *= SIGN;
        }

        if (ball.getMinX() < 0 || ball.getMaxX() > MAX_X) {
            directionX *= SIGN;
        }

        ballX += DX * directionX;
        ballY += DY * directionY;
        ball.x = ballX;
        ball.y = ballY;
    }
}
