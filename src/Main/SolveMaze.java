package Main;

import javax.swing.*;
import java.awt.*;

public abstract class SolveMaze extends JPanel implements Runnable {

    Thread thread;
    public static String[] solveAlgorithms = {"[NOT IMPLEMENTED]"}; //"Random Mouse algorithm", "Wall Follower", "Tremeaux's algorithm"};
    public static final int mazeSize = 35; // set the maze size
    Mark[][] maze = GenMaze.cell;
    volatile static Mark[][] cell = new Mark[mazeSize][mazeSize]; // creates the grid of cells used for the maze

    SolveMaze(){
        this.setOpaque(false);
    }

    public void resetAnimation() { // sets all cells in the maze to 'null' and repaints the 'canvas'
        initMaze();
        super.repaint();
    }

    public void initMaze() { // initialises the maze, sets all the cells to 'walls'
        for (int i = 0; i < mazeSize; i++) {
            for (int j = 0; j < mazeSize; j++) {
                cell[i][j] = Mark.NULL;
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Common.red);
        g.fillRect(20, 0, 10, 20); // start square
        g.fillRect(mazeSize * 10 - 10, mazeSize * 10 + 10, 10, 10); // end square
        cell[1][0] = Mark.PATH; // joins start square to maze
        cell[mazeSize - 2][mazeSize - 1] = Mark.PATH; // joins end square to maze
        for (int yy = 0; yy < mazeSize; yy++) {
            for (int xx = 0; xx < mazeSize; xx++) {
                switch (cell[xx][yy]) {
                    case PATH: // if the cell is a path, paint white
                        g.setColor(Color.ORANGE);
                        g.fillRect(xx * 10 + 10, yy * 10 + 10, 10, 10);
                        break;
                    case END:
                        g.setColor(Common.blue);
                        g.fillRect(xx * 10 + 10, yy * 10 + 10, 10, 10);
                        break;
                    case CURRENT: // if the cell is the current cell
                        g.setColor(Common.red);
                        g.fillRect(xx * 10 + 10, yy * 10 + 10, 10, 10);
                        break;
                }
            }
        }
    }

    public void animate() {
        repaint();
        try {
            Thread.sleep(100);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void run() {

    }

    public void rerun() {
        if (thread == null || thread.getState() == Thread.State.TERMINATED) {
            thread = new Thread(this);
            thread.start();
        }
    }

}
