package mx.unison.pong;

public class GameModel {
    public static final double WIDTH = 640.0;
    public static final double HEIGHT = 480.0;
    public static final double PADDLE_Y = 450.0;
    public static final double INITIAL_BALL_X = (WIDTH - Ball.DIAMETER) / 2.0;
    public static final double INITIAL_BALL_Y = HEIGHT / 2.0;
    public static final double INITIAL_VELOCITY_X = 5.0;
    public static final double INITIAL_VELOCITY_Y = 5.0;

    private final Ball ball = new Ball(
            INITIAL_BALL_X,
            INITIAL_BALL_Y,
            INITIAL_VELOCITY_X,
            INITIAL_VELOCITY_Y
    );
    private final Paddle paddle = new Paddle(WIDTH, PADDLE_Y);
    private GameState state = GameState.PAUSED;

    public Ball getBall() {
        return ball;
    }

    public Paddle getPaddle() {
        return paddle;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }
}
