package Main;

public class DeadEnd extends SolveMaze {

    public void run() {
        super.clearSolution();
        super.run();
        super.fromStart = false;
        deadEnd();
        for (int j = 0; j < mazeSize; j++) {
            for (int i = 0; i < mazeSize; i++) {
                if (maze[i][j] == Mark.PATH)
                    maze[i][j] = Mark.ROUTE;
            }
        }
        super.fromStart = true;
        super.maze = complete(super.maze);
        if (!stop)
            repaint();
    }

    private void deadEnd() {
        if (stop) {
            this.hidden = true;
            return;
        }
        if (completedSolve)
            return;
        for (int j = 1; j < mazeSize; j++){
            for (int i = 0; i < mazeSize; i++){
                if (stop) {
                    this.hidden = true;
                    return;
                }
                if (completedSolve)
                    return;
                if ((i == startX) && (j == startY)) {
                    // do nothing
                }
                else if ((i == endX-1) && (j == endY)) {
                    // do nothing
                    completedSolve = true;
                    return;
                }
                else if (maze[i][j] == Mark.PATH) {
                    if (checkNeighbours(maze, i, j, Mark.PATH) == 1) {
                        //maze[i][j] = Mark.END;
                        //maze[i][j] = Mark.CURRENT;
                        if (!this.hidden)
                            animate();
                        maze[i][j] = Mark.END;
                        deadEnd();
                        break;
                    }
                }
            }
        }
        //completedSolve = true;
    }

    private void endFill(int x, int y) {
        if (stop) {
            this.hidden = true;
            return;
        }
        if (x == endX && y == endY) {
            maze[x][y] = Mark.PATH;
            completedSolve = true;
        }
        if (completedSolve)
            return;
        maze[x][y] = Mark.END;
        /*switch(neighbourDirection(maze, x, y, Mark.PATH)){
            case UP:
                animate();
                deadEnd(x, y - 1);
                break;
            case DOWN:
                animate();
                deadEnd(x, y + 1);
                break;
            case LEFT:
                animate();
                deadEnd(x - 1, y);
                break;
            case RIGHT:
                animate();
                deadEnd(x + 1, y);
                break;
        }

         */
    }
}
