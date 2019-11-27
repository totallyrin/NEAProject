package Main;

import java.awt.*;

public abstract class SolveMaze extends Maze {

    // list of maze-solving algorithms
    static String[] solveAlgorithms = {"Depth-first / Recursive Backtracker", "Dead-end Filling"}; //, "Tremeaux's algorithm"};
    boolean fromStart = true;

    // initialises the maze
    @Override
    public void initMaze() {
        super.maze = Main.currentMaze;
        completedSolve = false;
        stop = false;
    }

    // sets all 'route' cells to paths
    void clearSolution() {
        initMaze();
        for (int y = 0; y < mazeSize; y++) {
            for (int x = 0; x < mazeSize; x++) {
                if (super.maze[x][y] == Mark.ROUTE)
                    super.maze[x][y] = Mark.PATH;
            }
        }
        repaint();
    }

    // animate at double speed
    @Override
    public void animate() {
        super.animate(50);
    }

    // controls how to paint the panel
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        super.maze = Main.currentMaze;
        if (!completedSolve && !isActive()) {
            g.setColor(bg);
            super.maze[1][0] = Mark.PATH;
        } else {
            if (!fromStart)
                g.setColor(bg);
            else {
                g.setColor(Color.ORANGE);
                super.maze[1][0] = Mark.ROUTE; // joins start square to super.maze
            }
        }
        g.fillRect(20, 0, 10, 20); // start square
        if (completedSolve) {
            g.setColor(Color.ORANGE);
            super.maze[mazeSize - 2][mazeSize - 1] = Mark.ROUTE;
        } else {
            g.setColor(bg);
            super.maze[mazeSize - 2][mazeSize - 1] = Mark.PATH; // joins end square to super.maze
        }
        g.fillRect(mazeSize * 10 - 10, mazeSize * 10 + 10, 10, 10); // end square
        for (int y = 0; y < mazeSize; y++) {
            for (int x = 0; x < mazeSize; x++) {
                switch (super.maze[x][y]) {
                    case WALL:
                        g.setColor(Color.DARK_GRAY);
                        g.fillRect(x * 10 + 10, y * 10 + 10, 10, 10);
                        break;
                    case NULL:
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
