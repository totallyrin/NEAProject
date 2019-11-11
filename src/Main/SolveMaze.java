package Main;

import javax.swing.*;
import java.awt.*;

public abstract class SolveMaze extends Maze {

    public static String[] solveAlgorithms = {"Depth-first / Recursive Backtracker"}; //, "Tremeaux's algorithm"};

    SolveMaze() {
        initMaze();
        this.setBackground(Color.DARK_GRAY);
    }

    @Override
    public void initMaze() { // initialises the maze
        super.maze = Common.currentMaze;
        complete = false;
    }

    public void clearSolution(){
        for (int x = 0; x < Common.mazeSize; x++) {
            for (int y = 0; y < Common.mazeSize; y++) {
                if (super.maze[x][y] == Mark.ROUTE)
                    super.maze[x][y] = Mark.PATH;
            }
        }
    }


    @Override
    public void animate(){
        super.animate(50);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.ORANGE);
        g.fillRect(20, 0, 10, 20); // start square
        if (complete)
            g.setColor(Color.ORANGE);
        else
            g.setColor(Common.bg);
        g.fillRect(Common.mazeSize * 10 - 10, Common.mazeSize * 10 + 10, 10, 10); // end square
        maze[1][0] = Mark.ROUTE; // joins start square to maze
        maze[Common.mazeSize - 2][Common.mazeSize - 1] = Mark.PATH; // joins end square to maze
        if (complete)
            maze[Common.mazeSize - 2][Common.mazeSize - 1] = Mark.ROUTE;
        for (int yy = 0; yy < Common.mazeSize; yy++) {
            for (int xx = 0; xx < Common.mazeSize; xx++) {
                switch (maze[xx][yy]) {
                    case WALL:
                        g.setColor(Color.DARK_GRAY);
                        g.fillRect(xx * 10 + 10, yy * 10 + 10, 10, 10);
                        break;
                    case PATH:
                        g.setColor(Common.bg);
                        g.fillRect(xx * 10 + 10, yy * 10 + 10, 10, 10);
                        break;
                    case ROUTE:
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
}
