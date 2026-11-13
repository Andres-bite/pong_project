package mx.unison.pong;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import javax.swing.BorderFactory;
import javax.swing.JComponent;

public class Board extends JComponent implements KeyListener {
    private Dimension preferredSize;
    private final Ellipse2D.Double ball;
    private final Paddle paddle;
    private final BallRunner ballRunner;
    private final Font theFont = new Font("SansSerif", Font.BOLD, 24);

    public Board() {
        setOpaque(true);
        setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.BLACK));
        ball = new Ellipse2D.Double(20, 320, 20, 20);
        paddle = new Paddle();
        ballRunner = new BallRunner(ball, paddle, this::repaint);
        ballRunner.start();
    }

    @Override
    protected void paintComponent(Graphics graphicsContext) {
        super.paintComponent(graphicsContext);
        Graphics2D graphics = (Graphics2D) graphicsContext;
        if (isOpaque()) {
            graphics.setColor(getBackground());
            graphics.fillRect(0, 0, getWidth(), getHeight());
            graphics.setColor(getForeground());
        }

        if (ballRunner.getState() == GameState.PAUSED) {
            graphics.setFont(theFont);
            graphics.setColor(Color.BLUE);
            graphics.drawString("Paused", 20, 30);
        }

        graphics.setStroke(new BasicStroke(5.0f));
        graphics.setColor(Color.RED);
        graphics.fill(ball);
        graphics.setColor(Color.BLACK);
        graphics.fill(paddle.getPaddle());
    }

    @Override
    public Dimension getPreferredSize() {
        if (preferredSize == null) {
            return new Dimension(640, 480);
        }
        return super.getPreferredSize();
    }

    @Override
    public void setPreferredSize(Dimension newPreferredSize) {
        preferredSize = newPreferredSize;
        super.setPreferredSize(newPreferredSize);
    }

    public void stop() {
        ballRunner.stop();
    }

    @Override
    public void keyTyped(KeyEvent event) {
    }

    @Override
    public void keyPressed(KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if (ballRunner.getState() == GameState.RUNNING) {
                    paddle.moveLeft();
                    repaint();
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (ballRunner.getState() == GameState.RUNNING) {
                    paddle.moveRight();
                    repaint();
                }
                break;
            case KeyEvent.VK_SPACE:
                ballRunner.pause();
                repaint();
                break;
            case KeyEvent.VK_R:
                ballRunner.resume();
                repaint();
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent event) {
    }
}
