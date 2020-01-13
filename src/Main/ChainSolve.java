package Main;

public class ChainSolve extends SolveMaze {

    public void run() {
        clearSolution();
        super.run();
        chainSolve(startX, startY);
        for (int i = 1; i < mazeSize - 1; i++) {
            for (int j = 1; j < mazeSize - 1; j++) {
                if (maze[i][j] != Mark.WALL && checkNeighbours(maze, i, j, Mark.ROUTE) == 2)
                    maze[i][j] = Mark.ROUTE;
            }
        }
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
        if (x == endX - 1 && y == endY - 1) {
            completedSolve = true;
            maze[x][y] = Mark.ROUTE;
        }
        if (completedSolve)
            return;
        if (x == startX && y == startY) {
            fromStart = false;
            for (int i = 1; i < mazeSize - 1; i++) {
                if (maze[i][i] != Mark.WALL)
                    maze[i][i] = Mark.LINE;
                animate();
            }
            fromStart = true;
        }
        findRoute(x, y);
    }

    private void findRoute(int x, int y) {
        if (stop) { // return if stopped
            hidden = true;
            return;
        }
        if (x == endX - 1 && y == endY - 1) // complete if finished
            completedSolve = true;
        if (completedSolve) // return if complete
            return;
        if (random.nextInt(1) == 0)
            followerRight(x, y);
        else
            followerLeft(x, y);
    }

    private void followerRight(int x, int y) {
        if (stop) { // return if stopped
            hidden = true;
            return;
        }
        if (completedSolve) // return if complete
            return;
        if (x == endX - 1 && y == endY - 1) { // complete if finished
            completedSolve = true;
            return;
        }
        // right
        if (x + 1 < mazeSize) {
            if (maze[x + 1][y] == Mark.LINE) {
                maze[x][y] = Mark.ROUTE;
                chainSolve(x + 1, y);
            }
            else if (maze[x + 1][y] == Mark.PATH) {
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
            if (maze[x][y + 1] == Mark.LINE) {
                maze[x][y] = Mark.ROUTE;
                chainSolve(x, y + 1);
            }
            else if (maze[x][y + 1] == Mark.PATH) {
                maze[x][y] = Mark.ROUTE;
                maze[x][y + 1] = Mark.CURRENT;
                if (!hidden)
                    animate();
                followerRight(x, y + 1);
                if (completedSolve)
                    return;
            }
        }
        // up
        if (y - 1 > 0) {// check if going up would go outside of the maze
            if (maze[x][y - 1] == Mark.LINE) {
                maze[x][y] = Mark.ROUTE;
                chainSolve(x, y - 1);
            }
            else if (maze[x][y - 1] == Mark.PATH) { // check that the space is available
                maze[x][y] = Mark.ROUTE;
                maze[x][y - 1] = Mark.CURRENT;
                if (!hidden)
                    animate();
                followerRight(x, y - 1);
                if (completedSolve)
                    return;
            }
        }
        // left
        if (x - 1 > 0) {
            if (maze[x - 1][y] == Mark.LINE) {
                maze[x][y] = Mark.ROUTE;
                chainSolve(x - 1, y);
            }
            else if (maze[x - 1][y] == Mark.PATH) {
                maze[x][y] = Mark.ROUTE;
                maze[x - 1][y] = Mark.CURRENT;
                if (!hidden)
                    animate();
                followerRight(x - 1, y);
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

    private void followerLeft(int x, int y) {
        if (stop) { // return if stopped
            hidden = true;
            return;
        }
        if (completedSolve) // return if complete
            return;
        if (x == endX - 1 && y == endY - 1) { // complete if finished
            completedSolve = true;
            return;
        }
        // left
        if (x - 1 > 0) {
            if (maze[x - 1][y] == Mark.LINE) {
                maze[x][y] = Mark.ROUTE;
                chainSolve(x - 1, y);
            }
            else if (maze[x - 1][y] == Mark.PATH) {
                maze[x][y] = Mark.ROUTE;
                maze[x - 1][y] = Mark.CURRENT;
                if (!hidden)
                    animate();
                followerLeft(x - 1, y);
                if (completedSolve)
                    return;
            }
        }
        // up
        if (y - 1 > 0) {// check if going up would go outside of the maze
            if (maze[x][y - 1] == Mark.LINE) {
                maze[x][y] = Mark.ROUTE;
                chainSolve(x, y - 1);
            }
            else if (maze[x][y - 1] == Mark.PATH) { // check that the space is available
                maze[x][y] = Mark.ROUTE;
                maze[x][y - 1] = Mark.CURRENT;
                if (!hidden)
                    animate();
                followerLeft(x, y - 1);
                if (completedSolve)
                    return;
            }
        }
        // right
        if (x + 1 < mazeSize) {
            if (maze[x + 1][y] == Mark.LINE) {
                maze[x][y] = Mark.ROUTE;
                chainSolve(x + 1, y);
            }
            else if (maze[x + 1][y] == Mark.PATH) {
                maze[x][y] = Mark.ROUTE;
                maze[x + 1][y] = Mark.CURRENT;
                if (!hidden)
                    animate();
                followerLeft(x + 1, y);
                if (completedSolve)
                    return;
            }
        }
        // down
        if (y + 1 < mazeSize) {
            if (maze[x][y + 1] == Mark.LINE) {
                maze[x][y] = Mark.ROUTE;
                chainSolve(x, y - 1);
            }
            else if (maze[x][y + 1] == Mark.PATH) {
                maze[x][y] = Mark.ROUTE;
                maze[x][y + 1] = Mark.CURRENT;
                if (!hidden)
                    animate();
                followerLeft(x, y + 1);
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
