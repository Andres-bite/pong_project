package mx.unison.pong;

import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;

public class Paddle {
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    private final int[] xPositions = {0, 80, 160, 240, 320, 400, 480, 560};
    private final Rectangle2D.Double paddle;
    private int position;

    public Paddle() {
        position = 4;
        paddle = new Rectangle2D.Double(xPositions[position], 450, WIDTH, HEIGHT);
    }

    public void moveRight() {
        if (position < xPositions.length - 1) {
            position++;
            paddle.x = xPositions[position];
        }
    }

    public void moveLeft() {
        if (position > 0) {
            position--;
            paddle.x = xPositions[position];
        }
    }

    public Rectangle2D.Double getPaddle() {
        return paddle;
    }

    public boolean check(int x, int y) {
        return paddle.contains(x, y);
    }

    public boolean intersects(RectangularShape shape) {
        return paddle.intersects(shape.getBounds2D());
    }
}
