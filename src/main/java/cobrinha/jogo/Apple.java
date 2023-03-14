package cobrinha.jogo;

import java.awt.*;
import java.util.Random;

public class Apple implements DrawableObject {
    public int x;
    public int y;
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.RED);
        g2d.fillOval(this.x*BLOCK_SIZE, this.y*BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
    }

    public void generateNewApple(int screenWidth, int screenHeight) {
        Random rand = new Random();
        this.x = rand.nextInt(screenWidth/BLOCK_SIZE);
        this.y = rand.nextInt(screenHeight/BLOCK_SIZE);
    }
}
