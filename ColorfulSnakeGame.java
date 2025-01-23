package ColorfulSnakeGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class ColorfulSnakeGame extends JFrame {
    public ColorfulSnakeGame() {
        add(new GamePanel());
        setTitle("Colorful Snake Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ColorfulSnakeGame::new);
    }
}

class GamePanel extends JPanel implements ActionListener {
    private final int TILE_SIZE = 30;
    private final int GAME_WIDTH = 600;
    private final int GAME_HEIGHT = 600;

    private final ArrayList<Point> snake = new ArrayList<>();
    private final ArrayList<Color> snakeColors = new ArrayList<>();
    private Point food;
    private char direction = 'R';
    private boolean running = true;

    private int score = 0;
    private int highScore = 0;

    private final Timer timer;
    private final Random random = new Random();

    public GamePanel() {
        setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(new SnakeKeyListener());

        initGame();
        timer = new Timer(120, this);
        timer.start();
    }

    private void initGame() {
        snake.clear();
        snakeColors.clear();
        snake.add(new Point(5, 5));
        snakeColors.add(getRandomColor());
        spawnFood();
        score = 0;
    }

    private void spawnFood() {
        int x = random.nextInt(GAME_WIDTH / TILE_SIZE);
        int y = random.nextInt(GAME_HEIGHT / TILE_SIZE);
        food = new Point(x, y);
    }

    private Color getRandomColor() {
        return new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (running) {
            // Draw food
            g.setColor(Color.RED);
            g.fillRect(food.x * TILE_SIZE, food.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);

            // Draw snake
            for (int i = 0; i < snake.size(); i++) {
                g.setColor(snakeColors.get(i));
                Point p = snake.get(i);
                g.fillRect(p.x * TILE_SIZE, p.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }

            // Display score and high score
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 18));
            g.drawString("Score: " + score, 10, 20);
            g.drawString("High Score: " + highScore, 10, 40);
        } else {
            showGameOver(g);
        }
    }

    private void showGameOver(Graphics g) {
        String message = "Game Over! Press R to Restart";
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString(message, (GAME_WIDTH - metrics.stringWidth(message)) / 2, GAME_HEIGHT / 2);

        // Display final score and high score
        String scoreMessage = "Final Score: " + score;
        String highScoreMessage = "High Score: " + highScore;
        g.drawString(scoreMessage, (GAME_WIDTH - metrics.stringWidth(scoreMessage)) / 2, GAME_HEIGHT / 2 + 30);
        g.drawString(highScoreMessage, (GAME_WIDTH - metrics.stringWidth(highScoreMessage)) / 2, GAME_HEIGHT / 2 + 60);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            moveSnake();
            checkCollision();
            checkFood();
        }
        repaint();
    }

    private void moveSnake() {
        Point head = snake.get(0);
        Point newHead = new Point(head);

        switch (direction) {
            case 'U' -> newHead.y--;
            case 'D' -> newHead.y++;
            case 'L' -> newHead.x--;
            case 'R' -> newHead.x++;
        }

        snake.add(0, newHead);
        snakeColors.add(0, getRandomColor());
        snake.remove(snake.size() - 1);
        snakeColors.remove(snakeColors.size() - 1);
    }

    private void checkCollision() {
        Point head = snake.get(0);

        if (head.x < 0 || head.x >= GAME_WIDTH / TILE_SIZE || head.y < 0 || head.y >= GAME_HEIGHT / TILE_SIZE) {
            running = false;
        }

        for (int i = 1; i < snake.size(); i++) {
            if (head.equals(snake.get(i))) {
                running = false;
                break;
            }
        }

        if (!running) {
            timer.stop();
            if (score > highScore) {
                highScore = score;
            }
        }
    }

    private void checkFood() {
        if (snake.get(0).equals(food)) {
            snake.add(new Point(food));
            snakeColors.add(getRandomColor());
            spawnFood();
            score++;
        }
    }

    private class SnakeKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP -> {
                    if (direction != 'D')
                        direction = 'U';
                }
                case KeyEvent.VK_DOWN -> {
                    if (direction != 'U')
                        direction = 'D';
                }
                case KeyEvent.VK_LEFT -> {
                    if (direction != 'R')
                        direction = 'L';
                }
                case KeyEvent.VK_RIGHT -> {
                    if (direction != 'L')
                        direction = 'R';
                }
                case KeyEvent.VK_R -> {
                    if (!running) {
                        running = true;
                        initGame();
                        timer.start();
                    }
                }
            }
        }
    }
}