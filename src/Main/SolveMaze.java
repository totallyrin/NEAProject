package Main;

import javax.swing.*;
import java.awt.*;

public abstract class SolveMaze extends Maze {

    public static String[] solveAlgorithms = {"Depth-first / Recursive Backtracker"}; //, "Tremeaux's algorithm"};
    boolean clear = false;

    SolveMaze() {
        initMaze();
        this.setBackground(Color.DARK_GRAY);
    }

    @Override
    public void initMaze() { // initialises the maze
        super.maze = currentMaze;
        complete = false;
    }

    public void clearSolution() {
        for (int x = 0; x < mazeSize; x++) {
            for (int y = 0; y < mazeSize; y++) {
                if (super.maze[x][y] == Mark.ROUTE)
                    super.maze[x][y] = Mark.PATH;
            }
        }
        complete = false;
        clear = true;
        repaint();
        clear = false;
    }


    @Override
    public void animate() {
        super.animate(50);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (clear) {
            g.setColor(bg);
            maze[1][0] = Mark.PATH;
        } else {
            g.setColor(Color.ORANGE);
            maze[1][0] = Mark.ROUTE; // joins start square to maze
        }
        g.fillRect(20, 0, 10, 20); // start square
        if (complete) {
            g.setColor(Color.ORANGE);
            maze[mazeSize - 2][mazeSize - 1] = Mark.ROUTE;
        } else {
            g.setColor(bg);
            maze[mazeSize - 2][mazeSize - 1] = Mark.PATH; // joins end square to maze
        }
        g.fillRect(mazeSize * 10 - 10, mazeSize * 10 + 10, 10, 10); // end square
        for (int y = 0; y < mazeSize; y++) {
            for (int x = 0; x < mazeSize; x++) {
                switch (maze[x][y]) {
                    case WALL:
                        g.setColor(Color.DARK_GRAY);
                        g.fillRect(x * 10 + 10, y * 10 + 10, 10, 10);
                        break;
                    case PATH:
                        g.setColor(bg);
                        g.fillRect(x * 10 + 10, y * 10 + 10, 10, 10);
                        break;
                    case ROUTE:
                        g.setColor(Color.ORANGE);
                        g.fillRect(x * 10 + 10, y * 10 + 10, 10, 10);
                        break;
                    case END:
                        g.setColor(blue);
                        g.fillRect(x * 10 + 10, y * 10 + 10, 10, 10);
                        break;
                    case CURRENT: // if the cell is the current cell
                        g.setColor(red);
                        g.fillRect(x * 10 + 10, y * 10 + 10, 10, 10);
                        break;
                }
            }
        }
    }
}
