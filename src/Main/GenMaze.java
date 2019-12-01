package Main;

import java.awt.*;
import javax.swing.*;

public abstract class GenMaze extends Maze {

    static String[] genAlgorithms = {"Depth-first / Recursive Backtracker", "Hunt-and-Kill algorithm", "Randomised Kruskal's algorithm"}; //, "Randomized Prim's algorithm"};

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(bg);
        g.fillRect(20, 0, 10, 20); // start square
        g.fillRect(mazeSize * 10 - 10, mazeSize * 10 + 10, 10, 10); // end square
        super.maze[1][0] = Mark.PATH; // joins start square to maze
        super.maze[mazeSize - 2][mazeSize - 1] = Mark.PATH; // joins end square to maze
        for (int y = 0; y < mazeSize; y++) {
            for (int x = 0; x < mazeSize; x++) {
                switch (super.maze[x][y]) {
                    case NULL:
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
                }
            }
        }
    }

}
