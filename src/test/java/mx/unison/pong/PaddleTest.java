package mx.unison.pong;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PaddleTest {
    @Test
    void remainsWithinBoardLimits() {
        Paddle paddle = new Paddle(GameModel.WIDTH, GameModel.PADDLE_Y);

        for (int index = 0; index < 20; index++) {
            paddle.moveLeft(GameModel.WIDTH);
        }

        assertEquals(0.0, paddle.getX());

        for (int index = 0; index < 20; index++) {
            paddle.moveRight(GameModel.WIDTH);
        }

        assertEquals(GameModel.WIDTH - Paddle.WIDTH, paddle.getX());
    }
}
