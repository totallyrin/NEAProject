package Main;

public class DeadEnd extends SolveMaze {

    // method to start maze-solving process
    public void run() {
        clearSolution();
        super.run();
        fromStart = false;
        deadEnd();
        // set the unfilled route to 'route' cells after solving is complete for user to see correct route
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
        // cycle through every cell in the maze
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
                    // if the cell is a path
                } else if (maze[i][j] == Mark.PATH) {
                    // check if it is a dead end (has only one neighbouring path)
                    if (checkNeighbours(maze, i, j, Mark.PATH) == 1) {
                        if (!hidden)
                            animate();
                        // mark as dead end
                        maze[i][j] = Mark.END;
                        // start process again
                        deadEnd();
                        break;
                    }
                }
            }
        }
    }
}
