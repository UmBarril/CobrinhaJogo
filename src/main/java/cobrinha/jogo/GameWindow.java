package cobrinha.jogo;

import javax.swing.*;

public class GameWindow extends JFrame {
    public GameWindow(int width, int height) {
        super();
        this.setFocusable(true);
        this.setResizable(false);
        this.requestFocus();
        this.setSize(width, height);
        this.setTitle("Game Window");
        this.setUndecorated(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
