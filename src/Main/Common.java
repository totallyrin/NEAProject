package Main;

import java.awt.*;
import java.util.Random;

public class Common {

    public static final int mazeSize = 35; // set the maze size
    volatile static int startX = 1, startY = 1, endX = mazeSize - 1, endY = mazeSize - 1;
    public static Color red = new Color(247, 73, 57), blue = new Color(0, 149, 255);
    static Random random = new Random();

    public static Direction[] getDirections() {
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

    public static int checkNeighbours(Mark[][] grid, int x, int y) {
        int neighbours = 0;
        if ((x >= 0 && x < grid.length) && (y >= 0 && y < grid[x].length)) {
            if (x - 1 > 0 && grid[x - 1][y] != Mark.WALL)
                neighbours++;
            if (x + 1 < Common.mazeSize && grid[x + 1][y] != Mark.WALL)
                neighbours++;
            if (y - 1 > 0 && grid[x][y - 1] != Mark.WALL)
                neighbours++;
            if (y + 1 < Common.mazeSize && grid[x][y + 1] != Mark.WALL)
                neighbours++;
        }
        return neighbours;
    }

    public static Direction neighbourDirection(Mark[][] grid, int x, int y) {
        Direction neighbour = Direction.NULL;
        if (x - 1 > 0 && grid[x - 1][y] != Mark.WALL)
            neighbour = Direction.LEFT;
        if (x + 1 < Common.mazeSize && grid[x + 1][y] != Mark.WALL)
            neighbour = Direction.RIGHT;
        if (y - 1 > 0 && grid[x][y - 1] != Mark.WALL)
            neighbour = Direction.UP;
        if (y + 1 < Common.mazeSize && grid[x][y + 1] != Mark.WALL)
            neighbour = Direction.DOWN;
        return neighbour;

    }

    public static boolean isRunning(Thread thread) {
        if (thread == null || thread.getState() == Thread.State.TERMINATED)
            return false;
        else
            return true;
    }

}
