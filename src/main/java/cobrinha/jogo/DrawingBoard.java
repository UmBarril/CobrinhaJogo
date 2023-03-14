package cobrinha.jogo;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

class DrawingBoard extends JPanel {
    private Consumer<Graphics2D> drawFunc;
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawFunc.accept(g2d);
        this.repaint();
    }
    public void registerDrawFunc(Consumer<Graphics2D> drawFunc) {
        this.drawFunc = drawFunc;
    }
}
