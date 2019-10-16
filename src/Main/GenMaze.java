package Main;

import java.awt.*;
import javax.swing.*;

public class GenMaze extends JPanel implements Runnable {

    private Thread thread;
    public static String[] genAlgorithms = {"Depth-first / Recursive Backtracker", "Hunt-and-Kill algorithm"}; //, "Randomised Kruskal's algorithm", "Randomized Prim's algorithm"};
    volatile static Mark[][] cell = new Mark[Common.mazeSize][Common.mazeSize]; // creates the grid of cells used for the maze
    public boolean complete = false;
    public int speed = 100;
    Color bg;

    GenMaze() {
        initMaze();
        bg = this.getBackground();
        this.setBackground(Color.DARK_GRAY);
    }

    public void resetAnimation() { // sets all cells in the maze to 'walls' and repaints the 'canvas'
        initMaze();
        repaint();
    }

    public void initMaze() { // initialises the maze, sets all the cells to 'walls'
        for (int i = 0; i < Common.mazeSize; i++) {
            for (int j = 0; j < Common.mazeSize; j++) {
                cell[i][j] = Mark.WALL;
            }
        }
        complete = false;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(bg);
        g.fillRect(20, 0, 10, 20); // start square
        g.fillRect(Common.mazeSize * 10 - 10, Common.mazeSize * 10 + 10, 10, 10); // end square
        cell[1][0] = Mark.PATH; // joins start square to maze
        cell[Common.mazeSize - 2][Common.mazeSize - 1] = Mark.PATH; // joins end square to maze
        for (int yy = 0; yy < Common.mazeSize; yy++) {
            for (int xx = 0; xx < Common.mazeSize; xx++) {
                switch (cell[xx][yy]) {
                    case WALL: // if the cell is a wall, paint dark grey
                        g.setColor(Color.DARK_GRAY);
                        g.fillRect(xx * 10 + 10, yy * 10 + 10, 10, 10);
                        break;
                    case PATH: // if the cell is a path, paint white
                        g.setColor(bg);
                        g.fillRect(xx * 10 + 10, yy * 10 + 10, 10, 10);
                        break;
                    case END:
                        if (!complete) {
                            g.setColor(Common.blue);
                        } else {
                            g.setColor(bg);
                        }
                        g.fillRect(xx * 10 + 10, yy * 10 + 10, 10, 10);
                        break;
                    case CURRENT: // if the cell is the current cell and maze is not complete, paint red, otherwise paint white
                        if (!complete) {
                            g.setColor(Common.red);
                        } else {
                            g.setColor(bg);
                        }
                        g.fillRect(xx * 10 + 10, yy * 10 + 10, 10, 10);
                        break;
                }
            }
        }
    }

    public void animate() {
        repaint();
        try {
            Thread.sleep(speed);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void run() {
        resetAnimation();
    }

    public void rerun() {
        if (thread == null || thread.getState() == Thread.State.TERMINATED) {
            thread = new Thread(this);
            speed = 100;
            thread.start();
        }
    }

    public void stop() {
        thread.interrupt();
    }

    public Thread getThread() {
        return thread;
    }
}
