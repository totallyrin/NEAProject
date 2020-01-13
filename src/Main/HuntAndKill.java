package Main;

public class HuntAndKill extends GenMaze {

    private int row = 1, column = 1;

    public void run() {
        super.run();
        row = 1;
        column = 1;
        huntAndKill(startX, startY);
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
                case UP: // up
                    if (y - 2 <= 0) // check if going up would go outside of the maze
                        continue;
                    if (maze[x][y - 2] == Mark.WALL) { // check that the space is available
                        maze[x][y] = Mark.PATH; // sets 'current' maze to no longer be current
                        maze[x][y - 1] = Mark.PATH; // sets the spaces ahead to a path
                        maze[x][y - 2] = Mark.CURRENT;
                        if (!hidden)
                            animate();
                        huntAndKill(x, y - 2); // call the same subroutine using the new coordinates
                    }
                    break;
                case DOWN: // down
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
                case LEFT: // left
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
                case RIGHT: // right
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
        for (int y = row; y < mazeSize - 1; y++) { // going down
            for (int x = column; x < mazeSize - 1; x++) { // going across (right)
                if (stop) {
                    hidden = true;
                    return;
                }
                temp = maze[x][y];
                maze[x][y] = Mark.SEARCH;
                if (!hidden)
                    animate(25);
                maze[x][y] = temp;
                if (maze[x][y] == Mark.WALL && checkNeighbours(maze, x, y) == 1) { // checks that the current cell has only one neighbouring wall
                    switch (neighbourDirection(maze, x, y)) {
                        case UP:
                            if (checkNeighbours(maze, x, y + 1) == 0) {
                                maze[x][y] = Mark.PATH;
                                maze[x][y + 1] = Mark.PATH;
                                row = y;
                                column = x;
                                huntAndKill(x, y + 1);
                                return;
                            }
                            break;
                        case DOWN:
                            if (y - 1 > 0 && checkNeighbours(maze, x, y - 1) == 0) {
                                maze[x][y] = Mark.PATH;
                                maze[x][y - 1] = Mark.PATH;
                                row = y;
                                column = x;
                                huntAndKill(x, y - 1);
                                return;
                            }
                            break;
                        case LEFT:
                            if (checkNeighbours(maze, x + 1, y) == 0) {
                                maze[x][y] = Mark.PATH;
                                maze[x + 1][y] = Mark.PATH;
                                row = y;
                                column = x;
                                huntAndKill(x + 1, y);
                                return;
                            }
                            break;
                        case RIGHT:
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
