package mx.unison.pong;

public class Paddle {
    public static final double WIDTH = 80.0;
    public static final double HEIGHT = 30.0;
    public static final double STEP = 80.0;

    private double x;
    private final double y;

    public Paddle(double boardWidth, double y) {
        x = (boardWidth - WIDTH) / 2.0;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void moveLeft(double boardWidth) {
        x = Math.max(0.0, x - STEP);
    }

    public void moveRight(double boardWidth) {
        x = Math.min(boardWidth - WIDTH, x + STEP);
    }
}
