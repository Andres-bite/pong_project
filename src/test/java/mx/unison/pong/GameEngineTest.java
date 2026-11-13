package mx.unison.pong;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class GameEngineTest {
    private final GameEngine engine = new GameEngine();

    @Test
    void reboundsAtLeftWallAndKeepsBallInsideBoard() {
        GameModel model = new GameModel();
        Ball ball = model.getBall();
        ball.setPosition(1.0, 100.0);
        ball.setVelocity(-5.0, 2.0);

        engine.update(model);

        assertEquals(0.0, ball.getX());
        assertEquals(5.0, ball.getVelocityX());
    }

    @Test
    void reboundsAtRightWallAndKeepsBallInsideBoard() {
        GameModel model = new GameModel();
        Ball ball = model.getBall();
        ball.setPosition(GameModel.WIDTH - Ball.DIAMETER - 1.0, 100.0);
        ball.setVelocity(5.0, 2.0);

        engine.update(model);

        assertEquals(GameModel.WIDTH - Ball.DIAMETER, ball.getX());
        assertEquals(-5.0, ball.getVelocityX());
    }

    @Test
    void reboundsAtTopWallAndKeepsBallInsideBoard() {
        GameModel model = new GameModel();
        Ball ball = model.getBall();
        ball.setPosition(100.0, 1.0);
        ball.setVelocity(2.0, -5.0);

        engine.update(model);

        assertEquals(0.0, ball.getY());
        assertEquals(5.0, ball.getVelocityY());
    }

    @Test
    void reboundsWhenCrossingPaddleTop() {
        GameModel model = new GameModel();
        Ball ball = model.getBall();
        Paddle paddle = model.getPaddle();
        ball.setPosition(paddle.getX() + 5.0, paddle.getY() - Ball.DIAMETER - 2.0);
        ball.setVelocity(0.0, 5.0);

        engine.update(model);

        assertEquals(paddle.getY() - Ball.DIAMETER, ball.getY());
        assertEquals(-5.0, ball.getVelocityY());
    }

    @Test
    void reboundsWhenReachingPaddleEdge() {
        GameModel model = new GameModel();
        Ball ball = model.getBall();
        Paddle paddle = model.getPaddle();
        ball.setPosition(paddle.getX() - Ball.DIAMETER + 1.0, paddle.getY() - Ball.DIAMETER - 2.0);
        ball.setVelocity(0.0, 5.0);

        engine.update(model);

        assertEquals(paddle.getY() - Ball.DIAMETER, ball.getY());
        assertEquals(-5.0, ball.getVelocityY());
    }

    @Test
    void changesToGameOverWhenBallPassesBottomLimit() {
        GameModel model = new GameModel();
        Ball ball = model.getBall();
        ball.setPosition(0.0, GameModel.HEIGHT - Ball.DIAMETER - 1.0);
        ball.setVelocity(0.0, 5.0);

        engine.update(model);

        assertEquals(GameState.GAME_OVER, model.getState());
        assertEquals(GameModel.HEIGHT - Ball.DIAMETER, ball.getY());
    }
}
