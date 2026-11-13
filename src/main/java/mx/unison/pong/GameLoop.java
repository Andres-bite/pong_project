package mx.unison.pong;

import javax.swing.Timer;

public class GameLoop {
    public static final int FRAME_DELAY = 16;

    private final GameModel model;
    private final GameEngine engine;
    private final Runnable afterUpdate;
    private final Timer timer;

    public GameLoop(GameModel model, GameEngine engine, Runnable afterUpdate) {
        this.model = model;
        this.engine = engine;
        this.afterUpdate = afterUpdate;
        timer = new Timer(FRAME_DELAY, event -> tick());
        timer.setCoalesce(true);
    }

    public void start() {
        if (model.getState() == GameState.PAUSED) {
            model.setState(GameState.RUNNING);
            timer.start();
        }
    }

    public void pause() {
        if (model.getState() == GameState.RUNNING) {
            model.setState(GameState.PAUSED);
            timer.stop();
            afterUpdate.run();
        }
    }

    public void resume() {
        start();
    }

    public void stop() {
        model.setState(GameState.STOPPED);
        timer.stop();
    }

    private void tick() {
        if (model.getState() != GameState.RUNNING) {
            timer.stop();
            return;
        }

        engine.update(model);
        afterUpdate.run();

        if (model.getState() != GameState.RUNNING) {
            timer.stop();
        }
    }
}
