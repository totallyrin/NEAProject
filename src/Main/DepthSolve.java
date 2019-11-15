package Main;

public class DepthSolve extends SolveMaze {

    DepthSolve() {
        super.initMaze();
    }

    public void run() {
        super.clearSolution();
        super.run();
        depthSolve(startX, startY);
        super.maze = complete(super.maze);
        this.hidden = false;
        if (!stop)
            repaint();
    }

    private void depthSolve(int x, int y) {
        if (stop) {
            this.hidden = true;
            return;
        }
        if (x == endX - 1 && y == endY - 1) {
            super.complete = true;
            super.maze[x][y] = Mark.ROUTE;
        }
        if (super.complete)
            return;
        Direction[] directions = getDirections();
        for (Direction direction : directions) {
            switch (direction) {
                case UP: // up
                    if (y - 1 <= 0) // check if going up would go outside of the maze
                        continue;
                    if (super.complete)
                        return;
                    if (super.maze[x][y - 1] == Mark.PATH) { // check that the space is available
                        super.maze[x][y] = Mark.ROUTE;
                        super.maze[x][y - 1] = Mark.CURRENT;
                        if (!this.hidden)
                            super.animate();
                        depthSolve(x, y - 1);
                    }
                    break;
                case DOWN: // down
                    if (y + 1 >= mazeSize)
                        continue;
                    if (super.complete)
                        return;
                    if (super.maze[x][y + 1] == Mark.PATH) {
                        super.maze[x][y] = Mark.ROUTE;
                        super.maze[x][y + 1] = Mark.CURRENT;
                        if (!this.hidden)
                            super.animate();
                        depthSolve(x, y + 1);
                    }
                    break;
                case LEFT: // left
                    if (x - 1 <= 0)
                        continue;
                    if (super.complete)
                        return;
                    if (super.maze[x - 1][y] == Mark.PATH) {
                        super.maze[x][y] = Mark.ROUTE;
                        super.maze[x - 1][y] = Mark.CURRENT;
                        if (!this.hidden)
                            super.animate();
                        depthSolve(x - 1, y);
                    }
                    break;
                case RIGHT: // right
                    if (x + 1 >= mazeSize)
                        continue;
                    if (super.complete)
                        return;
                    if (super.maze[x + 1][y] == Mark.PATH) {
                        super.maze[x][y] = Mark.ROUTE;
                        super.maze[x + 1][y] = Mark.CURRENT;
                        if (!this.hidden)
                            super.animate();
                        depthSolve(x + 1, y);
                    }
                    break;
                default:
                    if (super.maze[x][y] == Mark.CURRENT) {
                        super.maze[x][y] = Mark.END;
                        if (!this.hidden)
                            super.animate();
                    }
                    break;
            }

        }
        if (super.complete)
            return;
        super.maze[x][y] = Mark.END;
        if (!this.hidden)
            super.animate();
    }

}
