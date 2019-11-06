package Main;

import java.awt.*;
import javax.swing.*;

public class GenMaze extends Maze {

    public static String[] genAlgorithms = {"Depth-first / Recursive Backtracker", "Hunt-and-Kill algorithm"}; //, "Randomised Kruskal's algorithm", "Randomized Prim's algorithm"};

    GenMaze() {
        super.initMaze();
        this.setBackground(Color.DARK_GRAY);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Common.bg);
        g.fillRect(20, 0, 10, 20); // start square
        g.fillRect(Common.mazeSize * 10 - 10, Common.mazeSize * 10 + 10, 10, 10); // end square
        super.maze[1][0] = Mark.PATH; // joins start square to maze
        super.maze[Common.mazeSize - 2][Common.mazeSize - 1] = Mark.PATH; // joins end square to maze
        for (int yy = 0; yy < Common.mazeSize; yy++) {
            for (int xx = 0; xx < Common.mazeSize; xx++) {
                switch (super.maze[xx][yy]) {
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
                }
            }
        }
    }

}
