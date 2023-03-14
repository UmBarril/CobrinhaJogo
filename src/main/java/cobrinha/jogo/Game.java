package cobrinha.jogo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

public class Game implements ActionListener, KeyListener {
    public static int WINDOW_WIDTH = 600;
    public static int WINDOW_HEIGHT = 600;
    public static int BLOCK_SIZE = 25;
    public static int DELAY = 200;
    public static final int SNAKE_INITIAL_SIZE = 2;
    private Snake snake;
    private Apple apple;
    private boolean running = false;
    private int points = 0;
    private GameWindow window;
    private Timer timer;
    public Game() {
        this.timer = new Timer(DELAY, this);

        // will fire the actionPerformed method after the specified delay
        this.timer = new Timer(DELAY, this);
    }
    public void start() {
        boolean jogar = false;
        while(!jogar) {
            String opcao = JOptionPane.showInputDialog(this.window, "O que deseja fazer?:\n 1- Jogar\n 2- Abrir leaderboard\n 3- Mudar tamanho da tela(padrão 600),\n 4- Mudar velocidade do jogo(padrão 200)\n 5- Mudar tamanho dos blocos(padrão 25)\n 6- Sair");
            switch (Integer.parseInt(opcao)) {
                case 1 -> jogar = true;
                case 2 -> this.showLeaderBoard();
                case 3 -> {
                    String tamanho = JOptionPane.showInputDialog(this.window, "Digite o novo tamanho da tela");
                    WINDOW_WIDTH = Integer.parseInt(tamanho);
                    WINDOW_HEIGHT = Integer.parseInt(tamanho);
                }
                case 4 -> {
                    String velocidade = JOptionPane.showInputDialog(this.window, "Digite a nova velocidade");
                    DELAY = Integer.parseInt(velocidade);
                }
                case 5 -> {
                    String tamanho = JOptionPane.showInputDialog(this.window, "Digite o novo tamanho dos blocos");
                    BLOCK_SIZE = Integer.parseInt(tamanho);
                }
                default -> { return; }
            }
        }
        this.window = new GameWindow(WINDOW_WIDTH, WINDOW_HEIGHT);
        DrawingBoard drawingBoard = new DrawingBoard();
        drawingBoard.registerDrawFunc(this::draw);
        this.window.add(drawingBoard);

        this.window.addKeyListener(this);
        this.window.setVisible(true);

        this.running = true;
        apple = new Apple();
        snake = new Snake((WINDOW_WIDTH/BLOCK_SIZE)/2, (WINDOW_HEIGHT/BLOCK_SIZE)/2, SNAKE_INITIAL_SIZE, Snake.Direction.RIGHT);
        apple.generateNewApple(Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT);
        this.timer.start();
    }
    public void draw(Graphics2D g2d) {
        if(running) {
            this.drawBoundaries(g2d);
            apple.draw(g2d);
            snake.draw(g2d);

            g2d.setColor(Color.red);
		    g2d.setFont(new Font("Comic Sans",Font.BOLD, 40));
            FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
            String scoreStr = "Score: " + points;
            g2d.drawString(scoreStr, (WINDOW_HEIGHT - metrics.stringWidth(scoreStr))/2, g2d.getFont().getSize());
        }
    }
    public void gameOver() {
        this.running = false;
        this.timer.stop();
        while(true) {
            try {
                this.saveGame();
                break;
            } catch (WrongNameFormatException e) {
                JOptionPane.showMessageDialog(this.window, "Formato errado! Apenas ensira letras em seu nome.");
            }
        }
        System.exit(0);
    }
    private void saveGame() throws WrongNameFormatException {
        String playerName = JOptionPane.showInputDialog("FIM DE JOGO. Digite o seu nome para o leaderboard: (apenas letras sem acentuação e sem espaços)");
        if(playerName == null) { // if null, not save
            return;
        }
        if(!playerName.matches("(?i)[a-z]\\w+")) {
            throw new WrongNameFormatException();
        }
        Player player = new Player(playerName, this.points);
        Persistance.updateLeaderboard(player);

    }
    public void showLeaderBoard() {
        List<Player> leaderboard = Persistance.readLeaderboard();
        if(leaderboard.size() == 0) {
            JOptionPane.showMessageDialog(this.window, "Ninguém jogou ainda! Chame mais amigos para conferir seus recordes!");
            return;
        }
        leaderboard.sort((p1, p2)  -> {
            if(p1.getPersonalRecord() == p2.getPersonalRecord()) {
                return 0;
            }
            return p1.getPersonalRecord() > p2.getPersonalRecord()? -1 : 1;
        });
        StringBuilder sb = new StringBuilder("Leaderboard:\n");
        for(Player leaderPlayer : leaderboard) {
            sb.append(leaderPlayer.toString());
            sb.append("\n");
        }
        JOptionPane.showMessageDialog(this.window, sb.toString());
    }
    private void drawBoundaries(Graphics2D g2d) {
        for(int i = 0; i < Game.WINDOW_HEIGHT/DrawableObject.BLOCK_SIZE; i++) {
            g2d.drawLine(i * DrawableObject.BLOCK_SIZE, 0, i * DrawableObject.BLOCK_SIZE, Game.WINDOW_HEIGHT);
            g2d.drawLine(0, i * DrawableObject.BLOCK_SIZE, Game.WINDOW_WIDTH, i * DrawableObject.BLOCK_SIZE);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(this.running) {
            snake.moveForward();
            if(snake.checkSelfCollision()) {
                gameOver();
            }
            if(this.checkBoundaries()) {
                gameOver();
            }
            if(this.checkApple()) {
                this.points++;
                apple.generateNewApple(Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT);
                snake.increaseBodySize();
            }
        }
    }
    private boolean checkBoundaries() {
        Snake.SnakeBodyPart head = snake.getHead();
        return head.x < 0 ||
                head.y < 0 ||
                head.x > WINDOW_WIDTH / BLOCK_SIZE ||
                head.y > WINDOW_HEIGHT / BLOCK_SIZE;
    }
    private boolean checkApple() {
        Snake.SnakeBodyPart head = snake.getHead();
        return head.x == apple.x && head.y == apple.y;
    }
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT -> {
                if(snake.currentDirection != Snake.Direction.RIGHT) {
                    snake.currentDirection = Snake.Direction.LEFT;
                }
            }
            case KeyEvent.VK_RIGHT -> {
                if(snake.currentDirection != Snake.Direction.LEFT) {
                    snake.currentDirection = Snake.Direction.RIGHT;
                }
            }
            case KeyEvent.VK_UP -> {
                if(snake.currentDirection != Snake.Direction.DOWN) {
                    snake.currentDirection = Snake.Direction.UP;
                }
            }
            case KeyEvent.VK_DOWN -> {
                if(snake.currentDirection != Snake.Direction.UP) {
                    snake.currentDirection = Snake.Direction.DOWN;
                }
            }
        }
    }
    @Override
    public void keyReleased(KeyEvent e) { }
    @Override
    public void keyTyped(KeyEvent e) { }
}
