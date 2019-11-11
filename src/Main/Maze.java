package Main;

import javax.swing.*;
import java.awt.*;

public abstract class Maze extends JPanel implements Runnable {

    private Thread thread;
    Mark[][] maze = new Mark[Common.mazeSize][Common.mazeSize];
    public boolean complete = false;
    public boolean hidden = false;
    Mark[][] currentMaze = new Mark[Common.mazeSize][Common.mazeSize];
    public void resetAnimation() { // sets all cells in the maze to 'walls' and repaints the 'canvas'
        initMaze();
        repaint();
    }

    public void initMaze() { // initialises the maze, sets all the cells to 'walls'
        for (int i = 0; i < Common.mazeSize; i++) {
            for (int j = 0; j < Common.mazeSize; j++) {
                maze[i][j] = Mark.WALL;
            }
        }
        complete = false;
    }

    public void paintComponent(Graphics g, Mark[][] maze) {
        super.paintComponent(g);
        g.setColor(Common.bg);
        g.fillRect(20, 0, 10, 20); // start square
        g.fillRect(Common.mazeSize * 10 - 10, Common.mazeSize * 10 + 10, 10, 10); // end square
        maze[1][0] = Mark.PATH; // joins start square to maze
        maze[Common.mazeSize - 2][Common.mazeSize - 1] = Mark.PATH; // joins end square to maze
        for (int yy = 0; yy < Common.mazeSize; yy++) {
            for (int xx = 0; xx < Common.mazeSize; xx++) {
                switch (maze[xx][yy]) {
                    case WALL: // if the cell is a wall, paint dark grey
                        g.setColor(Color.DARK_GRAY);
                        g.fillRect(xx * 10 + 10, yy * 10 + 10, 10, 10);
                        break;
                    case PATH: // if the cell is a path, paint white
                        g.setColor(Common.bg);
                        g.fillRect(xx * 10 + 10, yy * 10 + 10, 10, 10);
                        break;
                    case SEARCH:
                        g.setColor(Common.yellow);
                        g.fillRect(xx * 10 + 10, yy * 10 + 10, 10, 10);
                        break;
                    case END:
                        if (!complete) {
                            g.setColor(Common.blue);
                        } else {
                            g.setColor(Common.bg);
                        }
                        g.fillRect(xx * 10 + 10, yy * 10 + 10, 10, 10);
                        break;
                    case CURRENT: // if the cell is the current cell and maze is not complete, paint red, otherwise paint white
                        if (!complete) {
                            g.setColor(Common.red);
                        } else {
                            g.setColor(Common.bg);
                        }
                        g.fillRect(xx * 10 + 10, yy * 10 + 10, 10, 10);
                        break;
                    case ROUTE:
                        g.setColor(Color.ORANGE);
                        g.fillRect(xx * 10 + 10, yy * 10 + 10, 10, 10);
                        break;
                }
            }
        }
    }


    public Mark[][] complete(Mark[][] maze){
        for (int x = 0; x < maze.length; x++) {
            for (int y = 0; y < maze[x].length; y++) {
                if (maze[x][y] == Mark.CURRENT || maze[x][y] == Mark.END)
                    maze[x][y] = Mark.PATH;
            }
        }
        return maze;
    }

    public void animate(int speed) {
        repaint();
        Common.sleep(speed);
    }

    public void animate() {
        animate(Common.speed);
    }

    @Override
    public void run() {
        resetAnimation();
    }

    public void rerun() {
        if (thread == null || thread.getState() == Thread.State.TERMINATED) {
            thread = new Thread(this);
            Common.speed = 100;
            thread.start();
        }
    }

    public Thread getThread() {
        return thread;
    }


}
