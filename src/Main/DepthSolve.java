package Main;

public class DepthSolve extends SolveMaze {

    public void run() {
        clearSolution();
        super.run();
        depthSolve(startX, startY);
        maze = complete(maze);
        hidden = false;
        if (!stop)
            repaint();
    }

    private void depthSolve(int x, int y) {
        if (stop) {
            hidden = true;
            return;
        }
        if (x == endX - 1 && y == endY - 1) {
            completedSolve = true;
            maze[x][y] = Mark.ROUTE;
        }
        if (completedSolve)
            return;
        Direction[] directions = getDirections();
        for (Direction direction : directions) {
            switch (direction) {
                case UP: // up
                    if (y - 1 <= 0) // check if going up would go outside of the maze
                        continue;
                    if (completedSolve)
                        return;
                    if (maze[x][y - 1] == Mark.PATH) { // check that the space is available
                        maze[x][y] = Mark.ROUTE;
                        maze[x][y - 1] = Mark.CURRENT;
                        if (!hidden)
                            animate();
                        depthSolve(x, y - 1);
                    }
                    break;
                case DOWN: // down
                    if (y + 1 >= mazeSize)
                        continue;
                    if (completedSolve)
                        return;
                    if (maze[x][y + 1] == Mark.PATH) {
                        maze[x][y] = Mark.ROUTE;
                        maze[x][y + 1] = Mark.CURRENT;
                        if (!hidden)
                            animate();
                        depthSolve(x, y + 1);
                    }
                    break;
                case LEFT: // left
                    if (x - 1 <= 0)
                        continue;
                    if (completedSolve)
                        return;
                    if (maze[x - 1][y] == Mark.PATH) {
                        maze[x][y] = Mark.ROUTE;
                        maze[x - 1][y] = Mark.CURRENT;
                        if (!hidden)
                            animate();
                        depthSolve(x - 1, y);
                    }
                    break;
                case RIGHT: // right
                    if (x + 1 >= mazeSize)
                        continue;
                    if (completedSolve)
                        return;
                    if (maze[x + 1][y] == Mark.PATH) {
                        maze[x][y] = Mark.ROUTE;
                        maze[x + 1][y] = Mark.CURRENT;
                        if (!hidden)
                            animate();
                        depthSolve(x + 1, y);
                    }
                    break;
                default:
                    if (maze[x][y] == Mark.CURRENT) {
                        maze[x][y] = Mark.END;
                        if (!hidden)
                            animate();
                    }
                    break;
            }

        }
        if (completedSolve)
            return;
        maze[x][y] = Mark.END;
        if (!hidden)
            animate();
    }

}
