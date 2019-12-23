package Main;

public class ChainSolve extends SolveMaze {

    public void run() {
        clearSolution();
        super.run();
        chainSolve();
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

    private void chainSolve() {
        for (int i = 1; i < mazeSize - 1; i++) {
            if (maze[i][i] != Mark.WALL)
                maze[i][i] = Mark.LINE;
            animate();
        }


    }
}
