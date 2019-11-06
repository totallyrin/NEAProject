package Main;

public class DepthFirst extends GenMaze {

    DepthFirst() {
        super.initMaze();
    }

    public void run() {
        super.run();
        startGen();
        super.complete = true;
        repaint();
        Common.currentMaze = super.complete(super.maze);
    }

    public void startGen() {
        depthFirstGeneration(Common.startX, Common.startY);
    }

    public void depthFirstGeneration(int x, int y) {
        super.maze[x][y] = Mark.CURRENT;
        Direction[] directions = Common.getDirections();
        for (int i = 0; i < directions.length; i++) {
            switch (directions[i]) {
                case UP: // up
                    if (y - 2 <= 0) // check if going up would go outside of the maze
                        continue;
                    if (super.maze[x][y - 2] == Mark.WALL) { // check that the space is available
                        super.maze[x][y] = Mark.PATH; // sets 'current' cell to no longer be current
                        super.maze[x][y - 1] = Mark.PATH; // sets the spaces ahead to a path
                        super.maze[x][y - 2] = Mark.CURRENT;
                        if (!this.hidden)
                            super.animate();
                        depthFirstGeneration(x, y - 2); // call the same subroutine using the new coordinates
                    }
                    break;
                case DOWN: // down
                    if (y + 2 >= Common.mazeSize)
                        continue;
                    if (super.maze[x][y + 2] == Mark.WALL) {
                        super.maze[x][y] = Mark.PATH;
                        super.maze[x][y + 1] = Mark.PATH;
                        super.maze[x][y + 2] = Mark.CURRENT;
                        if (!this.hidden)
                            super.animate();
                        depthFirstGeneration(x, y + 2);
                    }
                    break;
                case LEFT: // left
                    if (x - 2 <= 0)
                        continue;
                    if (super.maze[x - 2][y] == Mark.WALL) {
                        super.maze[x][y] = Mark.PATH;
                        super.maze[x - 1][y] = Mark.PATH;
                        super.maze[x - 2][y] = Mark.CURRENT;
                        if (!this.hidden)
                            super.animate();
                        depthFirstGeneration(x - 2, y);
                    }
                    break;
                case RIGHT: // right
                    if (x + 2 >= Common.mazeSize)
                        continue;
                    if (super.maze[x + 2][y] == Mark.WALL) {
                        super.maze[x][y] = Mark.PATH;
                        super.maze[x + 1][y] = Mark.PATH;
                        super.maze[x + 2][y] = Mark.CURRENT;
                        if (!this.hidden)
                            super.animate();
                        depthFirstGeneration(x + 2, y);
                    }
                    break;
            }
        }
        if (super.maze[x][y] == Mark.CURRENT) {
            super.maze[x][y] = Mark.END;
        }
    }

}
