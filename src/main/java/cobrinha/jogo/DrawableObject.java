package cobrinha.jogo;

import java.awt.*;

public interface DrawableObject {
    int BLOCK_SIZE = Game.BLOCK_SIZE;
    void draw(Graphics2D g2d);
}
