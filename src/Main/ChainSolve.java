package Main;

public class ChainSolve extends SolveMaze {

    // method to start solving process
    public void run() {
        clearSolution();
        super.run();
        chainSolve(startX, startY);
        maze = complete(maze);
        hidden = false;
        if (!stop)
            repaint();
    }

    /*
    Start by drawing a straight line (or at least a line that doesn't double back on itself) from start to end, letting it cross walls if needed,
    then just follow the line from start to end. If you bump into a wall, you can't go through it, so you have to go around.
    Send two wall following "robots" in both directions along the wall you hit.
    If a robot runs into the guiding line again, and at a point which is closer to the exit, then stop, and follow that wall yourself until you get there too.
    Keep following the line and repeating the process until the end is reached.
     */

    private void chainSolve(int x, int y) {
        if (stop) {
            hidden = true;
            return;
        }
        // if the end is reached, mark completedSolve as true and set that cell to a route cell
        if (x == endX - 1 && y == endY - 1) {
            completedSolve = true;
            maze[x][y] = Mark.ROUTE;
        }
        if (completedSolve)
            return;
        // if at the start of the maze
        if (x == startX && y == startY) {
            // set fromStart to false so the first cell is not set as a route cell
            fromStart = false;
            // for loop is used to draw the line
            for (int i = 1; i < mazeSize - 1; i++) {
                if (maze[i][i] != Mark.WALL)
                    maze[i][i] = Mark.LINE;
                if (!hidden)
                    animate();
            }
            // fromStart is set to true so that the first cell will be painted as a route cell
            fromStart = true;
        }
        findRoute(x, y);
    }

    private void findRoute(int x, int y) {
        if (stop) {
            hidden = true;
            return;
        }
        // if the end is reached, then set the maze as complete
        if (x == endX - 1 && y == endY - 1)
            completedSolve = true;
        if (completedSolve)
            return;
        // randomly choose next direction to search
        if (random.nextInt(1) == 0)
            followerRight(x, y);
        else
            followerLeft(x, y);
    }

    private void followerRight(int x, int y) {
        if (stop) {
            hidden = true;
            return;
        }
        if (completedSolve)
            return;
        // if end is reached, set the maze as complete
        if (x == endX - 1 && y == endY - 1) {
            completedSolve = true;
            return;
        }
        // right
        // check that going right does not go outside the maze
        if (x + 1 < mazeSize) {
            // if the cell to the right is part of the line, start process from there
            if (maze[x + 1][y] == Mark.LINE) {
                maze[x][y] = Mark.ROUTE;
                chainSolve(x + 1, y);
            } else if (maze[x + 1][y] == Mark.PATH) {
                maze[x][y] = Mark.ROUTE;
                maze[x + 1][y] = Mark.CURRENT;
                if (!hidden)
                    animate();
                followerRight(x + 1, y);
                if (completedSolve)
                    return;
            }
        }
        // down
        if (y + 1 < mazeSize) {
            // if the cell below is part of the line, start process from there
            if (maze[x][y + 1] == Mark.LINE) {
                maze[x][y] = Mark.ROUTE;
                chainSolve(x, y + 1);
            } else if (maze[x][y + 1] == Mark.PATH) {
                maze[x][y] = Mark.ROUTE;
                maze[x][y + 1] = Mark.CURRENT;
                if (!hidden)
                    animate();
                followerRight(x, y + 1);
                if (completedSolve)
                    return;
            }
        }
        // left
        if (x - 1 > 0) {
            // if the cell to the left is part of the line, start process from there
            if (maze[x - 1][y] == Mark.LINE) {
                maze[x][y] = Mark.ROUTE;
                chainSolve(x - 1, y);
            } else if (maze[x - 1][y] == Mark.PATH) {
                maze[x][y] = Mark.ROUTE;
                maze[x - 1][y] = Mark.CURRENT;
                if (!hidden)
                    animate();
                followerRight(x - 1, y);
                if (completedSolve)
                    return;
            }
        }
        // up
        if (y - 1 > 0) {
            // if the cell above is part of the line, start process from there
            if (maze[x][y - 1] == Mark.LINE) {
                maze[x][y] = Mark.ROUTE;
                chainSolve(x, y - 1);
            } else if (maze[x][y - 1] == Mark.PATH) {
                maze[x][y] = Mark.ROUTE;
                maze[x][y - 1] = Mark.CURRENT;
                if (!hidden)
                    animate();
                followerRight(x, y - 1);
                if (completedSolve)
                    return;
            }
        }
        if (completedSolve)
            return;
        // set the cell as a dead end if no direction can be travelled in
        maze[x][y] = Mark.END;
        if (!hidden)
            animate();
    }

    private void followerLeft(int x, int y) {
        if (stop) {
            hidden = true;
            return;
        }
        if (completedSolve)
            return;
        // if the end is reached, mark the maze as completed
        if (x == endX - 1 && y == endY - 1) {
            completedSolve = true;
            return;
        }
        // left
        // check that going left will not go outside the maze
        if (x - 1 > 0) {
            // if the cell to the left is part of the line, start process from there
            if (maze[x - 1][y] == Mark.LINE) {
                maze[x][y] = Mark.ROUTE;
                chainSolve(x - 1, y);
            } else if (maze[x - 1][y] == Mark.PATH) {
                maze[x][y] = Mark.ROUTE;
                maze[x - 1][y] = Mark.CURRENT;
                if (!hidden)
                    animate();
                followerLeft(x - 1, y);
                if (completedSolve)
                    return;
            }
        }
        // down
        if (y + 1 < mazeSize) {
            // if the cell below is part of the line, start process from there
            if (maze[x][y + 1] == Mark.LINE) {
                maze[x][y] = Mark.ROUTE;
                chainSolve(x, y - 1);
            } else if (maze[x][y + 1] == Mark.PATH) {
                maze[x][y] = Mark.ROUTE;
                maze[x][y + 1] = Mark.CURRENT;
                if (!hidden)
                    animate();
                followerLeft(x, y + 1);
                if (completedSolve)
                    return;
            }
        }
        // right
        if (x + 1 < mazeSize) {
            // if the cell to the right is part of the line, start process from there
            if (maze[x + 1][y] == Mark.LINE) {
                maze[x][y] = Mark.ROUTE;
                chainSolve(x + 1, y);
            } else if (maze[x + 1][y] == Mark.PATH) {
                maze[x][y] = Mark.ROUTE;
                maze[x + 1][y] = Mark.CURRENT;
                if (!hidden)
                    animate();
                followerLeft(x + 1, y);
                if (completedSolve)
                    return;
            }
        }
        // up
        if (y - 1 > 0) {
            // if the cell above is part of the line, start process from there
            if (maze[x][y - 1] == Mark.LINE) {
                maze[x][y] = Mark.ROUTE;
                chainSolve(x, y - 1);
            } else if (maze[x][y - 1] == Mark.PATH) {
                maze[x][y] = Mark.ROUTE;
                maze[x][y - 1] = Mark.CURRENT;
                if (!hidden)
                    animate();
                followerLeft(x, y - 1);
                if (completedSolve)
                    return;
            }
        }
        if (completedSolve)
            return;
        maze[x][y] = Mark.END;
        if (!hidden)
            animate();
    }

}
