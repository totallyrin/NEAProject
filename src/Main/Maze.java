package Main;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public abstract class Maze extends JPanel implements Runnable {

    static Random random = new Random();
    private static Thread thread;

    final static int mazeSize = 35; // set the maze size
    final int startX = 1, startY = 1, endX = mazeSize - 1, endY = mazeSize - 1;

    final static Color bg = new Color(238, 238, 238), red = new Color(247, 73, 57), blue = new Color(0, 149, 255), yellow = new Color(255, 187, 0), green = new Color(120, 200, 50);
    static int speed = 100;

    volatile static boolean stop = false;
    static boolean completedGen = false, completedSolve = false;
    static boolean hidden = false;

    Mark[][] maze = new Mark[mazeSize][mazeSize];

    // makes sure panel is blank, and that background colour is dark grey
    Maze() {
        initMaze();
        this.setBackground(Color.DARK_GRAY);
    }

    // sets all cells in the maze to 'walls' and repaints the 'canvas'
    private void resetAnimation() {
        initMaze();
        repaint();
    }

    // initialises the maze, sets all the cells to 'walls'
    public void initMaze() {
        for (int y = 0; y < mazeSize; y++) {
            for (int x = 0; x < mazeSize; x++) {
                maze[x][y] = Mark.WALL;
            }
        }
        completedGen = false;
        stop = false;
    }

    // 'completes' the maze by setting dead ends and current cell to path
    Mark[][] complete(Mark[][] maze) {
        for (int x = 0; x < maze.length; x++) {
            for (int y = 0; y < maze[x].length; y++) {
                if (maze[x][y] == Mark.CURRENT || maze[x][y] == Mark.END || maze[x][y] == Mark.LINE)
                    maze[x][y] = Mark.PATH;
            }
        }
        return maze;
    }

    // returns an array of directions in a random order
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

    // checks for number of neighbouring walls
    static int checkNeighbours(Mark[][] grid, int x, int y) {
        int neighbours = 0;
        if ((x >= 0 && x < grid.length) && (y >= 0 && y < grid[x].length)) {
            if (x - 1 > 0 && grid[x - 1][y] != Mark.WALL)
                neighbours++;
            if (x + 1 < mazeSize && grid[x + 1][y] != Mark.WALL)
                neighbours++;
            if (y - 1 > 0 && grid[x][y - 1] != Mark.WALL)
                neighbours++;
            if (y + 1 < mazeSize && grid[x][y + 1] != Mark.WALL)
                neighbours++;
        }
        return neighbours;
    }

    // checks for number of neighbouring cells of choice
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

    // returns the direction of the neighbouring wall
    static Direction neighbourDirection(Mark[][] grid, int x, int y) {
        Direction neighbour = Direction.NULL;
        if (x - 1 > 0 && grid[x - 1][y] != Mark.WALL)
            neighbour = Direction.LEFT;
        else if (x + 1 < mazeSize && grid[x + 1][y] != Mark.WALL)
            neighbour = Direction.RIGHT;
        else if (y - 1 > 0 && grid[x][y - 1] != Mark.WALL)
            neighbour = Direction.UP;
        else if (y + 1 < mazeSize && grid[x][y + 1] != Mark.WALL)
            neighbour = Direction.DOWN;
        return neighbour;

    }

    // causes current thread to sleep for x amount of time
    private static void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (Exception ignored) {
        }
    }

    // controls the animation
    void animate(int speed) {
        repaint();
        sleep(speed);
    }

    // controls the animation with default speed of 100
    public void animate() {
        animate(speed);
    }

    // resets animation when run
    @Override
    public void run() {
        resetAnimation();
    }

    // starts thread as long as no other threads are running
    void rerun() {
        if (thread == null || thread.getState() == Thread.State.TERMINATED) {
            thread = new Thread(this);
            speed = 100;
            thread.start();
        }
    }

    // checks if the specified thread is running
    private static boolean isRunning(Thread thread) {
        return thread != null && thread.getState() != Thread.State.TERMINATED;
    }

    // checks if any threads are running
    static boolean notActive() {
        return !isRunning(Maze.thread);
    }

    // stops current animation by setting stop to true
    static void stopAll() {
        if (isRunning(thread))
            stop = true;
    }

}
