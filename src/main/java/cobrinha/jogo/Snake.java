package cobrinha.jogo;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class Snake implements DrawableObject {
    private final List<SnakeBodyPart> bodyParts;
    public Direction currentDirection;

    public SnakeBodyPart getHead() { return bodyParts.get(0); }

    public Snake(int startX, int startY, int size, Direction startDirection) {
        this.bodyParts = new ArrayList<>();
        this.currentDirection = startDirection;

        for(int i = 0; i < size; i++)
            this.bodyParts.add(new SnakeBodyPart(startX, startY));
    }

    public void increaseBodySize() {
        SnakeBodyPart bodyPartToBeDuped = bodyParts.get(bodyParts.size() - 1);
        this.bodyParts.add(new SnakeBodyPart(bodyPartToBeDuped.x, bodyPartToBeDuped.y));
    }

    public void moveForward() {
        for(int i = bodyParts.size() - 1; i > 0; i--) {
            bodyParts.get(i).x = bodyParts.get(i-1).x;
            bodyParts.get(i).y = bodyParts.get(i-1).y;
        }
        switch (currentDirection) {
            case UP -> getHead().y--;
            case DOWN -> getHead().y++;
            case RIGHT -> getHead().x++;
            case LEFT -> getHead().x--;
        }
    }

    // true if it did collide / false if it didn't collide
    public boolean checkSelfCollision() {
        for(int i = 1; i < bodyParts.size(); i++) {
            SnakeBodyPart bodyPart = bodyParts.get(i);
            if(bodyPart.x == getHead().x && bodyPart.y == getHead().y) {
                return true;
            }
        }
        return false;
    }

    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.RED);
        g2d.fillRect(getHead().x * BLOCK_SIZE, getHead().y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
        g2d.setColor(Color.GREEN);
        for(int i = 1; i < bodyParts.size(); i++) {
            SnakeBodyPart bodyPart = bodyParts.get(i);
            g2d.fillRect(bodyPart.x * BLOCK_SIZE, bodyPart.y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
        }
    }

    static class SnakeBodyPart {
        public int x;
        public int y;
        public SnakeBodyPart(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    enum Direction {
        UP, DOWN, LEFT, RIGHT
    }
}

