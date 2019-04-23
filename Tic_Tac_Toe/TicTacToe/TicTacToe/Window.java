package TicTacToe;

import AI.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Window extends JFrame {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;

    private Board board;
    private Panel panel;
    private BufferedImage imageBackground, imageX, imageO;

    private enum Mode {Player, AI}
    private Mode mode;

    private Point[] cells;

    private static final int DISTANCE = 100;

    private Window () {
        this(Mode.AI);
    }

    private Window (Mode mode) {

        this.mode = mode;
        board = new Board();
        loadCells();
        panel = createPanel();
        setWindowProperties();
        loadImages();
    }

    private void loadCells () {
        cells = new Point[9];

        cells[0] = new Point(109, 109);
        cells[1] = new Point(299, 109);
        cells[2] = new Point(489, 109);
        cells[3] = new Point(109, 299);
        cells[4] = new Point(299, 299);
        cells[5] = new Point(489, 299);
        cells[6] = new Point(109, 489);
        cells[7] = new Point(299, 489);
        cells[8] = new Point(489, 489);
    }

    private void setWindowProperties () {
        setResizable(false);
        pack();
        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private Panel createPanel () {
        Panel panel = new Panel();
        Container cp = getContentPane();
        cp.add(panel);
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        panel.addMouseListener(new MyMouseAdapter());
        return panel;
    }

    private void loadImages () {
        imageBackground = getImage("background_1");
        imageX = getImage("x1_1");
        imageO = getImage("o1_1");
    }

    private static BufferedImage getImage (String path) {

        BufferedImage image;

        try {
            path = ".." + File.separator + "Image" + File.separator + path + ".png";
            image = ImageIO.read(Window.class.getResource(path));
        } catch (IOException ex) {
            throw new RuntimeException("Image could not be loaded.");
        }

        return image;
    }

    private class Panel extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            paintTicTacToe((Graphics2D) g);
        }

        private void paintTicTacToe (Graphics2D g) {
            setProperties(g);
            paintBoard(g);
            paintWinner(g);
        }

        private void setProperties (Graphics2D g) {
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.drawImage(imageBackground, 0, 0, null);

            g.drawString("", 0, 0);
        }

        private void paintBoard (Graphics2D g) {
            Board.State[][] boardArray = board.toArray();

            int offset = 20;

            for (int y = 0; y < 3; y++) {
                for (int x = 0; x < 3; x++) {
                    if (boardArray[y][x] == Board.State.X) {
                        g.drawImage(imageX, offset + 200 * x, offset + 200 * y, null);
                    } else if (boardArray[y][x] == Board.State.O) {
                        g.drawImage(imageO, offset + 200 * x, offset + 190 * y, null);
                    }
                }
            }
        }

        private void paintWinner (Graphics2D g) {
            if (board.isGameOver()) {
                g.setColor(new Color(255, 255, 255));
                g.setFont(new Font("TimesRoman", Font.PLAIN, 50));

                String s;

                if (board.getWinner() == Board.State.Blank) {
                    s = "Draw";
                } else {
                    s = board.getWinner() + " Wins!";
                }

                g.drawString(s, 300 - getFontMetrics(g.getFont()).stringWidth(s)/2, 315);

            }
        }
    }

    private class MyMouseAdapter extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            super.mouseClicked(e);

            if (board.isGameOver()) {
                board.reset();
                panel.repaint();
            } else {
                playMove(e);
            }

        }

        private void playMove (MouseEvent e) {
            int move = getMove(e.getPoint());

            if (!board.isGameOver() && move != -1) {
                boolean validMove = board.move(move);
                if (mode == Mode.AI && validMove && !board.isGameOver()) {
                    Main.alphaBetaPruning(board);
                }
                panel.repaint();
            }
        }

        private int getMove (Point point) {
            for (int i = 0; i < cells.length; i++) {
                if (distance(cells[i], point) <= DISTANCE) {
                    return i;
                }
            }
            return -1;
        }

        private double distance (Point p1, Point p2) {
            double xDiff = p1.getX() - p2.getX();
            double yDiff = p1.getY() - p2.getY();

            double xDiffSquared = xDiff*xDiff;
            double yDiffSquared = yDiff*yDiff;

            return Math.sqrt(xDiffSquared+yDiffSquared);
        }
    }

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        System.out.println("Choose mode of the game, vs. AI - 1, vs. Player - 2: ");

        for(; ;){
            int mode = in.nextInt();
            if(mode == 2){
                System.out.println("Game Mode: Player vs. Player");
                SwingUtilities.invokeLater(() -> new Window(Mode.Player));
                break;
            }
            else if (mode == 1) {
                System.out.println("Game Mode: Player vs. AI");
                SwingUtilities.invokeLater(() -> new Window(Mode.AI));
                break;
            }
            else {
                System.out.println("Invalid. Choose 1 or 2: ");
            }
        }

    }

}
