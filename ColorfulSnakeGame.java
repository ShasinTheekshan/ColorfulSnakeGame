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

    public GamePanel() {
        setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);

    }

    private void initGame() {
        snake.add(new Point(5, 5));
        snakeColors.add(getRandomColor());
    }

}

