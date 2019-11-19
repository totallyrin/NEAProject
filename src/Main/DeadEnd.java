package Main;

public class DeadEnd extends SolveMaze {

    public void run() {
        super.clearSolution();
        super.run();
        deadEnd(startX, startY);
        super.maze = complete(super.maze);
        this.hidden = false;
        if (!stop)
            repaint();
    }

    private void deadEnd(int x, int y) {
        if (stop) {
            this.hidden = true;
            return;
        }
        if (completedSolve)
            return;
        for (int j = 1; j < mazeSize; j++) {
            for (int i = 0; i < mazeSize; i++) {
                completedSolve = false;
                endFill(i, j);
            }
        }
        completedSolve = true;
    }

    private void endFill(int x, int y) {
        if (stop) {
            this.hidden = true;
            return;
        }
        if (x == endX - 1 && y == endY - 1) {
            completedSolve = true;
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
                    if (super.maze[x][y - 1] == Mark.PATH) { // check that the space is available
                        super.maze[x][y] = Mark.NULL;
                        super.maze[x][y - 1] = Mark.NULL;
                        endFill(x, y - 1);
                    }
                    break;
                case DOWN: // down
                    if (y + 1 >= mazeSize)
                        continue;
                    if (completedSolve)
                        return;
                    if (super.maze[x][y + 1] == Mark.PATH) {
                        super.maze[x][y] = Mark.NULL;
                        super.maze[x][y + 1] = Mark.NULL;
                        endFill(x, y + 1);
                    }
                    break;
                case LEFT: // left
                    if (x - 1 <= 0)
                        continue;
                    if (completedSolve)
                        return;
                    if (super.maze[x - 1][y] == Mark.PATH) {
                        super.maze[x][y] = Mark.NULL;
                        super.maze[x - 1][y] = Mark.NULL;
                        endFill(x - 1, y);
                    }
                    break;
                case RIGHT: // right
                    if (x + 1 >= mazeSize)
                        continue;
                    if (completedSolve)
                        return;
                    if (super.maze[x + 1][y] == Mark.PATH) {
                        super.maze[x][y] = Mark.NULL;
                        super.maze[x + 1][y] = Mark.NULL;
                        if (!this.hidden)
                            super.animate();
                        endFill(x + 1, y);
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
        if (completedSolve)
            return;
        super.maze[x][y] = Mark.END;
        if (!this.hidden)
            super.animate();
    }
}
