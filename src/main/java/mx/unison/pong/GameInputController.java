package mx.unison.pong;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

public class GameInputController {
    private final GameModel model;
    private final GameLoop loop;
    private final Runnable repaint;

    public GameInputController(GameModel model, GameLoop loop, Runnable repaint) {
        this.model = model;
        this.loop = loop;
        this.repaint = repaint;
    }

    public void install(JComponent component) {
        bind(component, "LEFT", "move-left", this::moveLeft);
        bind(component, "RIGHT", "move-right", this::moveRight);
        bind(component, "SPACE", "pause", loop::pause);
        bind(component, "R", "resume", loop::resume);
    }

    private void bind(JComponent component, String keyStroke, String actionName, Runnable action) {
        component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("pressed " + keyStroke), actionName);
        component.getActionMap().put(actionName, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                action.run();
            }
        });
    }

    private void moveLeft() {
        if (model.getState() == GameState.RUNNING) {
            model.getPaddle().moveLeft(GameModel.WIDTH);
            repaint.run();
        }
    }

    private void moveRight() {
        if (model.getState() == GameState.RUNNING) {
            model.getPaddle().moveRight(GameModel.WIDTH);
            repaint.run();
        }
    }
}
