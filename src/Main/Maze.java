package Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

public abstract class Maze extends JPanel implements Runnable {

    private static Random random = new Random();

    private static Thread thread;

    final static int mazeSize = 35; // set the maze size
    final int startX = 1, startY = 1, endX = mazeSize - 1, endY = mazeSize - 1;

    static Color bg = new Color(238, 238, 238), red = new Color(247, 73, 57), blue = new Color(0, 149, 255), yellow = new Color(255, 187, 0);
    private static int speed = 100;

    volatile static boolean stop = false;
    static boolean completedGen = false, completedSolve = false;
    boolean hidden = false;

    Mark[][] maze = new Mark[mazeSize][mazeSize];

    Maze() {
        initMaze();
        this.setBackground(Color.DARK_GRAY);
    }

    private void resetAnimation() { // sets all cells in the maze to 'walls' and repaints the 'canvas'
        initMaze();
        repaint();
    }

    public void initMaze() { // initialises the maze, sets all the cells to 'walls'
        for (int y = 0; y < mazeSize; y++) {
            for (int x = 0; x < mazeSize; x++) {
                maze[x][y] = Mark.WALL;
            }
        }
        completedGen = false;
        stop = false;
    }

    public void paintComponent(Graphics g, Mark[][] maze) {
        super.paintComponent(g);
        g.setColor(bg);
        g.fillRect(20, 0, 10, 20); // start square
        g.fillRect(mazeSize * 10 - 10, mazeSize * 10 + 10, 10, 10); // end square
        maze[1][0] = Mark.PATH; // joins start square to maze
        maze[mazeSize - 2][mazeSize - 1] = Mark.PATH; // joins end square to maze
        for (int y = 0; y < mazeSize; y++) {
            for (int x = 0; x < mazeSize; x++) {
                switch (maze[x][y]) {
                    case WALL: // if the cell is a wall, paint dark grey
                        g.setColor(Color.DARK_GRAY);
                        g.fillRect(x * 10 + 10, y * 10 + 10, 10, 10);
                        break;
                    case PATH: // if the cell is a path, paint white
                        g.setColor(bg);
                        g.fillRect(x * 10 + 10, y * 10 + 10, 10, 10);
                        break;
                    case SEARCH:
                        g.setColor(yellow);
                        g.fillRect(x * 10 + 10, y * 10 + 10, 10, 10);
                        break;
                    case END:
                        if (!completedGen) {
                            g.setColor(blue);
                        } else {
                            g.setColor(bg);
                        }
                        g.fillRect(x * 10 + 10, y * 10 + 10, 10, 10);
                        break;
                    case CURRENT: // if the cell is the current cell and maze is not completedGen, paint red, otherwise paint white
                        if (!completedGen) {
                            g.setColor(red);
                        } else {
                            g.setColor(bg);
                        }
                        g.fillRect(x * 10 + 10, y * 10 + 10, 10, 10);
                        break;
                    case ROUTE:
                        g.setColor(Color.ORANGE);
                        g.fillRect(x * 10 + 10, y * 10 + 10, 10, 10);
                        break;
                }
            }
        }
    }

    Mark[][] complete(Mark[][] maze) {
        for (int x = 0; x < maze.length; x++) {
            for (int y = 0; y < maze[x].length; y++) {
                if (maze[x][y] == Mark.CURRENT || maze[x][y] == Mark.END)
                    maze[x][y] = Mark.PATH;
            }
        }
        return maze;
    }

    static Direction[] getDirections() {
        Direction[] directions = {Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT}; // create an array with the 4 valid directions
        Direction[] shuffle = new Direction[4]; // create an empty array for the shuffled set of directions
        for (int i = 0; i < directions.length; i++) {
            int rand = random.nextInt(directions.length); // choose a random number from 0-3
            while (directions[rand] == Direction.NULL) { // if the direction it chooses is not valid
                rand = random.nextInt(directions.length); // continue generating numbers until it can choose a valid direction
            }
            Direction temp = directions[rand]; // get the chosen direction
            directions[rand] = Direction.NULL; // set the space to null
            shuffle[i] = temp; // put the chosen direction in the correct space in the shuffled array
        }
        return shuffle;
    }

    static int checkNeighbours(Mark[][] grid, int x, int y, Mark type) {
        int neighbours = 0;
        if ((x >= 0 && x < grid.length) && (y >= 0 && y < grid[x].length)) {
            if (x - 1 > 0 && grid[x - 1][y] == type)
                neighbours++;
            if (x + 1 < mazeSize && grid[x + 1][y] == type)
                neighbours++;
            if (y - 1 > 0 && grid[x][y - 1] == type)
                neighbours++;
            if (y + 1 < mazeSize && grid[x][y + 1] == type)
                neighbours++;
        }
        return neighbours;
    }

    static Direction neighbourDirection(Mark[][] grid, int x, int y) {
        Direction neighbour = Direction.NULL;
        if (x - 1 > 0 && grid[x - 1][y] != Mark.WALL)
            neighbour = Direction.LEFT;
        if (x + 1 < mazeSize && grid[x + 1][y] != Mark.WALL)
            neighbour = Direction.RIGHT;
        if (y - 1 > 0 && grid[x][y - 1] != Mark.WALL)
            neighbour = Direction.UP;
        if (y + 1 < mazeSize && grid[x][y + 1] != Mark.WALL)
            neighbour = Direction.DOWN;
        return neighbour;

    }

    private static void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void animate(int speed) {
        repaint();
        sleep(speed);
    }

    public void animate() {
        animate(speed);
    }

    @Override
    public void run() {
        resetAnimation();
    }

    void rerun() {
        if (thread == null || thread.getState() == Thread.State.TERMINATED) {
            thread = new Thread(this);
            speed = 100;
            thread.start();
        }
    }

    Thread getThread() {
        return thread;
    }

    static boolean isRunning(Thread thread) {
        return thread != null && thread.getState() != Thread.State.TERMINATED;
    }

    static boolean isActive() {
        return isRunning(Maze.thread);
    }

    static void stopAll() {
        if (isRunning(thread))
            stop = true;
    }

}
