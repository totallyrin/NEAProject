package Main;

public class DeadEnd extends SolveMaze {

    public void run() {
        clearSolution();
        super.run();
        fromStart = false;
        deadEnd();
        for (int j = 0; j < mazeSize; j++) {
            for (int i = 0; i < mazeSize; i++) {
                if (maze[i][j] == Mark.PATH)
                    maze[i][j] = Mark.ROUTE;
            }
        }
        fromStart = true;
        maze = complete(maze);
        if (!stop)
            repaint();
    }

    private void deadEnd() {
        if (stop) {
            hidden = true;
            return;
        }
        if (completedSolve)
            return;
        for (int j = 1; j < mazeSize; j++) {
            for (int i = 0; i < mazeSize; i++) {
                if (stop) {
                    hidden = true;
                    return;
                }
                if (completedSolve)
                    return;
                if ((i == startX) && (j == startY)) {
                    assert true; // do nothing
                } else if ((i == endX - 1) && (j == endY)) {
                    // if end is reached, maze is complete
                    completedSolve = true;
                    return;
                } else if (maze[i][j] == Mark.PATH) {
                    if (checkNeighbours(maze, i, j, Mark.PATH) == 1) {
                        if (!hidden)
                            animate();
                        maze[i][j] = Mark.END;
                        deadEnd();
                        break;
                    }
                }
            }
        }
    }
}
