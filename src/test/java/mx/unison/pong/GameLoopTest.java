package mx.unison.pong;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class GameLoopTest {
    @Test
    void startsPausesResumesAndStopsInExpectedStates() {
        GameModel model = new GameModel();
        GameLoop loop = new GameLoop(model, new GameEngine(), () -> {
        });

        loop.start();
        assertEquals(GameState.RUNNING, model.getState());

        loop.pause();
        assertEquals(GameState.PAUSED, model.getState());

        loop.resume();
        assertEquals(GameState.RUNNING, model.getState());

        loop.stop();
        assertEquals(GameState.STOPPED, model.getState());
    }

    @Test
    void repeatedPauseAndResumeKeepsSingleStateTransitionPath() {
        GameModel model = new GameModel();
        GameLoop loop = new GameLoop(model, new GameEngine(), () -> {
        });

        for (int index = 0; index < 10; index++) {
            loop.start();
            loop.pause();
        }

        assertEquals(GameState.PAUSED, model.getState());
        loop.stop();
    }
}
