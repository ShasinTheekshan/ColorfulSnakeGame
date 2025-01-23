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

class GamePanel extends JPanel {
    private final int TILE_SIZE = 30;
    private final int GAME_WIDTH = 600;
    private final int GAME_HEIGHT = 600;

    private final ArrayList<Point> snake = new ArrayList<>();
    private final ArrayList<Color> snakeColors = new ArrayList<>();
    private Point food;
    private char direction = 'R';
    private final Random random = new Random();

    public GamePanel() {
        setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);

    }

    private void initGame() {
        snake.add(new Point(5, 5));
        snakeColors.add(getRandomColor());
    }

    private void spawnFood() {
        int x = random.nextInt(GAME_WIDTH / TILE_SIZE);
        int y = random.nextInt(GAME_HEIGHT / TILE_SIZE);
        food = new Point(x, y);
    }

    private void checkFood() {
        if (snake.get(0).equals(food)) {
            snake.add(new Point(food));
            snakeColors.add(getRandomColor());
            spawnFood();
        }
    }

    private Color getRandomColor() {
        return new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < snake.size(); i++) {
            g.setColor(snakeColors.get(i));
            Point p = snake.get(i);
            g.fillRect(p.x * TILE_SIZE, p.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }
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
        snake.remove(snake.size() - 1);
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
            }
        }
    }

}
