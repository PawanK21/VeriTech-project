import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.Random;

public class SnakeGame extends JFrame implements ActionListener, KeyListener {

    private static final int TILE_SIZE = 20;
    private static final int GRID_SIZE = 20;

    private LinkedList<Point> snake;
    private Point fruit;
    private int direction;

    public SnakeGame() {
        setTitle("Snake Game");
        setSize(TILE_SIZE * GRID_SIZE, TILE_SIZE * GRID_SIZE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        snake = new LinkedList<>();
        snake.add(new Point(GRID_SIZE / 2, GRID_SIZE / 2));
        direction = KeyEvent.VK_RIGHT;

        spawnFruit();

        Timer timer = new Timer(100, this);
        timer.start();

        addKeyListener(this);
        setFocusable(true);
    }

    private void spawnFruit() {
        Random random = new Random();
        int x, y;

        do {
            x = random.nextInt(GRID_SIZE);
            y = random.nextInt(GRID_SIZE);
        } while (snake.contains(new Point(x, y)));

        fruit = new Point(x, y);
    }

    private void move() {
        Point head = snake.getFirst();

        switch (direction) {
            case KeyEvent.VK_UP:
                head = new Point(head.x, (head.y - 1 + GRID_SIZE) % GRID_SIZE);
                break;
            case KeyEvent.VK_DOWN:
                head = new Point(head.x, (head.y + 1) % GRID_SIZE);
                break;
            case KeyEvent.VK_LEFT:
                head = new Point((head.x - 1 + GRID_SIZE) % GRID_SIZE, head.y);
                break;
            case KeyEvent.VK_RIGHT:
                head = new Point((head.x + 1) % GRID_SIZE, head.y);
                break;
        }

        snake.addFirst(head);

        if (head.equals(fruit)) {
            spawnFruit();
        } else {
            snake.removeLast();
        }

        if (snake.size() > 1 && snake.subList(1, snake.size()).contains(head)) {
            gameOver();
        }
    }

    private void gameOver() {
        JOptionPane.showMessageDialog(this, "Game Over!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // Draw snake
        g.setColor(Color.GREEN);
        for (Point point : snake) {
            g.fillRect(point.x * TILE_SIZE, point.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }

        // Draw fruit
        g.setColor(Color.RED);
        g.fillRect(fruit.x * TILE_SIZE, fruit.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);

        // Draw grid
        g.setColor(Color.GRAY);
        for (int i = 0; i < GRID_SIZE; i++) {
            g.drawLine(i * TILE_SIZE, 0, i * TILE_SIZE, TILE_SIZE * GRID_SIZE);
            g.drawLine(0, i * TILE_SIZE, TILE_SIZE * GRID_SIZE, i * TILE_SIZE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if ((key == KeyEvent.VK_LEFT) && (direction != KeyEvent.VK_RIGHT)) {
            direction = KeyEvent.VK_LEFT;
        } else if ((key == KeyEvent.VK_RIGHT) && (direction != KeyEvent.VK_LEFT)) {
            direction = KeyEvent.VK_RIGHT;
        } else if ((key == KeyEvent.VK_UP) && (direction != KeyEvent.VK_DOWN)) {
            direction = KeyEvent.VK_UP;
        } else if ((key == KeyEvent.VK_DOWN) && (direction != KeyEvent.VK_UP)) {
            direction = KeyEvent.VK_DOWN;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SnakeGame snakeGame = new SnakeGame();
            snakeGame.setVisible(true);
        });
    }
}
