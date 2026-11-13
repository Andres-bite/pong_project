package mx.unison.pong;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndShowGui);
    }

    private static void createAndShowGui() {
        JFrame frame = new JFrame("Pong");
        GameModel model = new GameModel();
        Board board = new Board(model);
        GameLoop loop = new GameLoop(model, new GameEngine(), board::repaint);
        GameInputController inputController = new GameInputController(model, loop, board::repaint);
        JPanel panel = new JPanel(new BorderLayout());

        frame.setResizable(false);
        panel.add(board, BorderLayout.CENTER);
        inputController.install(board);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                loop.stop();
            }
        });
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
        loop.start();
    }
}
