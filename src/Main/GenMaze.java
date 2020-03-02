package Main;

import java.awt.*;

public abstract class GenMaze extends Maze {

    // array containing different generation algorithms included in the program
    static String[] genAlgorithms = {"Depth-first / Recursive Backtracker", "Hunt-and-Kill algorithm", "Randomised Kruskal's algorithm"}; //, "Randomized Prim's algorithm"};
    // default speed
    static int speed = 100;

    // override animate subroutine, using generation speed
    @Override
    public void animate() {
        super.animate(speed);
    }

    // subroutine to control how the panel is painted
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(bg);
        g.fillRect(20, 0, 10, 20);                                  // paint start square
        g.fillRect(mazeSize * 10 - 10, mazeSize * 10 + 10, 10, 10); // paint end square
        super.maze[1][0] = Mark.PATH;                                                   // joins start square to maze
        super.maze[mazeSize - 2][mazeSize - 1] = Mark.PATH;                             // joins end square to maze
        for (int y = 0; y < mazeSize; y++) {
            for (int x = 0; x < mazeSize; x++) {
                switch (maze[x][y]) {
                    case NULL:
                        // if the cell is a wall, paint dark grey
                    case WALL:
                        g.setColor(Color.DARK_GRAY);
                        g.fillRect(x * 10 + 10, y * 10 + 10, 10, 10);
                        break;
                    // if the cell is a path, paint white
                    case PATH:
                        g.setColor(bg);
                        g.fillRect(x * 10 + 10, y * 10 + 10, 10, 10);
                        break;
                    // if the cell is being searched, paint yellow
                    case SEARCH:
                        g.setColor(yellow);
                        g.fillRect(x * 10 + 10, y * 10 + 10, 10, 10);
                        break;
                    // if the cell is a dead end, paint blue unless complete, in which case paint white
                    case END:
                        if (!completedGen) {
                            g.setColor(blue);
                        } else {
                            g.setColor(bg);
                        }
                        g.fillRect(x * 10 + 10, y * 10 + 10, 10, 10);
                        break;
                    // if the cell is the current cell and maze is not completedGen, paint red, otherwise paint white
                    case CURRENT:
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
