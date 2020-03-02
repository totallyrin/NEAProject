package Main;

public class HuntAndKill extends GenMaze {

    // store current row and column
    private int row = 1, column = 1;

    // method to run the maze generation process
    public void run() {
        super.run();
        // reset row and column to 1
        row = 1;
        column = 1;
        huntAndKill(startX, startY);
        // repaints the completed maze
        if (!stop) {
            completedGen = true;
            Main.currentMaze = complete(maze);
            repaint();
        }
    }

    private void huntAndKill(int x, int y) {
        if (stop) {
            hidden = true;
            return;
        }
        maze[x][y] = Mark.CURRENT;
        Direction[] directions = getDirections();
        for (Direction direction : directions) {
            if (stop) {
                hidden = true;
                return;
            }
            switch (direction) {
                case UP:                                // if direction chosen is up
                    if (y - 2 <= 0)                     // check if going up would go outside of the maze
                        continue;
                    if (maze[x][y - 2] == Mark.WALL) {  // check that the space is available
                        maze[x][y] = Mark.PATH;         // sets 'current' maze to no longer be current
                        maze[x][y - 1] = Mark.PATH;     // sets the spaces ahead to a path
                        maze[x][y - 2] = Mark.CURRENT;
                        if (!hidden)
                            animate();
                        huntAndKill(x, y - 2);       // call the same subroutine using the new coordinates
                    }
                    break;
                case DOWN:                              // if direction chosen is down
                    if (y + 2 >= mazeSize)
                        continue;
                    if (maze[x][y + 2] == Mark.WALL) {
                        maze[x][y] = Mark.PATH;
                        maze[x][y + 1] = Mark.PATH;
                        maze[x][y + 2] = Mark.CURRENT;
                        if (!hidden)
                            animate();
                        huntAndKill(x, y + 2);
                    }
                    break;
                case LEFT:                              // if direction chosen is left
                    if (x - 2 <= 0)
                        continue;
                    if (maze[x - 2][y] == Mark.WALL) {
                        maze[x][y] = Mark.PATH;
                        maze[x - 1][y] = Mark.PATH;
                        maze[x - 2][y] = Mark.CURRENT;
                        if (!hidden)
                            animate();
                        huntAndKill(x - 2, y);
                    }
                    break;
                case RIGHT:                             // if direction chosen is right
                    if (x + 2 >= mazeSize)
                        continue;
                    if (maze[x + 2][y] == Mark.WALL) {
                        maze[x][y] = Mark.PATH;
                        maze[x + 1][y] = Mark.PATH;
                        maze[x + 2][y] = Mark.CURRENT;
                        if (!hidden)
                            animate();
                        huntAndKill(x + 2, y);
                    }
                    break;
            }
        }
        if (maze[x][y] == Mark.CURRENT) {
            maze[x][y] = Mark.END;
            if (!hidden)
                animate();
            hunt();
        }
    }

    private void hunt() {
        Mark temp;
        for (int y = row; y < mazeSize - 1; y++) {           // going down
            for (int x = column; x < mazeSize - 1; x++) {    // going across (right)
                if (stop) {
                    hidden = true;
                    return;
                }
                temp = maze[x][y];
                maze[x][y] = Mark.SEARCH;
                if (!hidden)
                    animate(25);
                maze[x][y] = temp;
                // checks that the current cell has only one neighbouring wall
                if (maze[x][y] == Mark.WALL && checkNeighbours(maze, x, y) == 1) {
                    switch (neighbourDirection(maze, x, y)) {
                        case UP:                                                                // if direction chosen is up
                            if (checkNeighbours(maze, x, y + 1) == 0) {
                                maze[x][y] = Mark.PATH;
                                maze[x][y + 1] = Mark.PATH;
                                row = y;
                                column = x;
                                huntAndKill(x, y + 1);
                                return;
                            }
                            break;
                        case DOWN:                                                              // if direction chosen is down
                            if (y - 1 > 0 && checkNeighbours(maze, x, y - 1) == 0) {
                                maze[x][y] = Mark.PATH;
                                maze[x][y - 1] = Mark.PATH;
                                row = y;
                                column = x;
                                huntAndKill(x, y - 1);
                                return;
                            }
                            break;
                        case LEFT:                                                              // if direction chosen is left
                            if (checkNeighbours(maze, x + 1, y) == 0) {
                                maze[x][y] = Mark.PATH;
                                maze[x + 1][y] = Mark.PATH;
                                row = y;
                                column = x;
                                huntAndKill(x + 1, y);
                                return;
                            }
                            break;
                        case RIGHT:                                                             // if direction chosen is right
                            if (x - 1 > 0 && checkNeighbours(maze, x - 1, y) == 0) {
                                maze[x][y] = Mark.PATH;
                                maze[x - 1][y] = Mark.PATH;
                                row = y;
                                column = x;
                                huntAndKill(x - 1, y);
                                return;
                            }
                            break;
                    }
                }
                column = 1;
            }
        }
    }
}
