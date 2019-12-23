package Main;

public class DepthFirst extends GenMaze {

    public void run() {
        super.run();
        depthFirstGeneration(startX, startY);
        if (!stop) {
            completedGen = true;
            Main.currentMaze = complete(maze);
            repaint();
        }
    }

    private void depthFirstGeneration(int x, int y) {
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
                        maze[x][y] = Mark.PATH; // sets 'current' cell to no longer be current
                        maze[x][y - 1] = Mark.PATH; // sets the spaces ahead to a path
                        maze[x][y - 2] = Mark.CURRENT;
                        if (!hidden)
                            animate();
                        depthFirstGeneration(x, y - 2); // call the same subroutine using the new coordinates
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
                        depthFirstGeneration(x, y + 2);
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
                        depthFirstGeneration(x - 2, y);
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
                        depthFirstGeneration(x + 2, y);
                    }
                    break;
            }
        }
        if (maze[x][y] == Mark.CURRENT) {
            maze[x][y] = Mark.END;
        }
    }

}
