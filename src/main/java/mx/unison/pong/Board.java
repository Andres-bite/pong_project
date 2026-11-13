package mx.unison.pong;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import javax.swing.BorderFactory;
import javax.swing.JComponent;

public class Board extends JComponent {
    private final GameModel model;
    private final Font stateFont = new Font("SansSerif", Font.BOLD, 24);

    public Board(GameModel model) {
        this.model = model;
        setOpaque(true);
        setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.BLACK));
        setPreferredSize(new Dimension((int) GameModel.WIDTH, (int) GameModel.HEIGHT));
    }

    @Override
    protected void paintComponent(Graphics graphicsContext) {
        super.paintComponent(graphicsContext);
        Graphics2D graphics = (Graphics2D) graphicsContext;
        Ball ball = model.getBall();
        Paddle paddle = model.getPaddle();

        graphics.setStroke(new BasicStroke(5.0f));
        graphics.setColor(Color.RED);
        graphics.fill(new Ellipse2D.Double(ball.getX(), ball.getY(), Ball.DIAMETER, Ball.DIAMETER));
        graphics.setColor(Color.BLACK);
        graphics.fill(new Rectangle2D.Double(paddle.getX(), paddle.getY(), Paddle.WIDTH, Paddle.HEIGHT));
        drawState(graphics);
    }

    private void drawState(Graphics2D graphics) {
        String stateLabel = switch (model.getState()) {
            case PAUSED -> "Paused";
            case GAME_OVER -> "Game over";
            default -> null;
        };

        if (stateLabel != null) {
            graphics.setFont(stateFont);
            graphics.setColor(Color.BLUE);
            graphics.drawString(stateLabel, 20, 30);
        }
    }
}
