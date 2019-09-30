package Main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.*;

public abstract class GenMaze extends JPanel implements Runnable {

    private Thread thread;
    volatile static Random random = new Random();
    public Timer tm = new Timer(500, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            repaint();
        }
    });

    public Color red = new Color(247, 73, 57);
    public Color blue = new Color(0, 149, 255);

    public static final int mazeSize = 35; // set the maze size
    volatile static Mark[][] cell = new Mark[mazeSize][mazeSize]; // creates the grid of cells used for the maze
    volatile static int startX = 1, startY = 1, endX = mazeSize - 1, endY = mazeSize - 1;
    public boolean complete = false;
    Color bg;

    GenMaze() {
        bg = this.getBackground();
        this.setBackground(Color.DARK_GRAY);
    }

    public void startTimer() {
        tm.restart();
    }

    public void resetAnimation() { // sets all cells in the maze to 'walls' and repaints the 'canvas'
        initMaze();
        repaint();
    }

    public void initMaze() { // initialises the maze, sets all the cells to 'walls'
        for (int i = 0; i < mazeSize; i++) {
            for (int j = 0; j < mazeSize; j++) {
                cell[i][j] = Mark.WALL;
            }
        }
        complete = false;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(bg);
        g.fillRect(20, 0, 10, 20); // start square
        g.fillRect(mazeSize * 10 - 10, mazeSize * 10 + 10, 10, 10); // end square
        cell[1][0] = Mark.PATH; // joins start square to maze
        cell[mazeSize - 2][mazeSize - 1] = Mark.PATH; // joins end square to maze
        for (int yy = 0; yy < mazeSize; yy++) {
            for (int xx = 0; xx < mazeSize; xx++) {
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
                        if (!complete){
                            g.setColor(blue);
                        } else {
                            g.setColor(bg);
                        }
                        g.fillRect(xx * 10 + 10, yy * 10 + 10, 10, 10);
                        break;
                    case CURRENT: // if the cell is the current cell and maze is not complete, paint red, otherwise paint white
                        if (!complete) {
                            g.setColor(red);
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
            Thread.sleep(100);
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
            thread.start();
        }
    }
}
