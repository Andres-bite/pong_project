package mx.unison.pong;

public class GameEngine {
    public void update(GameModel model) {
        Ball ball = model.getBall();
        Paddle paddle = model.getPaddle();
        double nextX = ball.getX() + ball.getVelocityX();
        double nextY = ball.getY() + ball.getVelocityY();
        double velocityX = ball.getVelocityX();
        double velocityY = ball.getVelocityY();

        if (nextX < 0.0) {
            nextX = 0.0;
            velocityX = Math.abs(velocityX);
        } else if (nextX + Ball.DIAMETER > GameModel.WIDTH) {
            nextX = GameModel.WIDTH - Ball.DIAMETER;
            velocityX = -Math.abs(velocityX);
        }

        if (nextY < 0.0) {
            nextY = 0.0;
            velocityY = Math.abs(velocityY);
        }

        if (collidesWithPaddle(ball, paddle, nextX, nextY)) {
            nextY = paddle.getY() - Ball.DIAMETER;
            velocityY = -Math.abs(velocityY);
        }

        if (nextY + Ball.DIAMETER > GameModel.HEIGHT) {
            ball.setPosition(nextX, GameModel.HEIGHT - Ball.DIAMETER);
            ball.setVelocity(velocityX, velocityY);
            model.setState(GameState.GAME_OVER);
            return;
        }

        ball.setPosition(nextX, nextY);
        ball.setVelocity(velocityX, velocityY);
    }

    private boolean collidesWithPaddle(Ball ball, Paddle paddle, double nextX, double nextY) {
        double currentBottom = ball.getY() + Ball.DIAMETER;
        double nextBottom = nextY + Ball.DIAMETER;
        boolean movingDown = ball.getVelocityY() > 0.0;
        boolean crossesPaddleTop = currentBottom <= paddle.getY() && nextBottom >= paddle.getY();
        double minimumBallX = Math.min(ball.getX(), nextX);
        double maximumBallRight = Math.max(ball.getX() + Ball.DIAMETER, nextX + Ball.DIAMETER);
        boolean overlapsPaddle = minimumBallX < paddle.getX() + Paddle.WIDTH
                && maximumBallRight > paddle.getX();
        return movingDown && crossesPaddleTop && overlapsPaddle;
    }
}
