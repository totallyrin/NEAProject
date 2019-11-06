package Main;

public class DepthSolve extends SolveMaze {

    DepthSolve() {
        super.initMaze();
    }

    public void run() {
        super.run();
        super.maze = Common.currentMaze;
        startGen();
        super.maze = complete(super.maze);
        repaint();
    }

    public void startGen() {
        wallFollow(Common.startX, Common.startY);
    }

    public void wallFollow(int x, int y){
        if (x == Common.endX - 1 && y == Common.endY - 1) {
            super.complete = true;
            super.maze[x][y] = Mark.ROUTE;
        }
        if (super.complete)
            return;
        super.maze[x][y] = Mark.CURRENT;
        Direction[] directions = Common.getDirections();
        for (int i = 0; i < directions.length; i++) {
            switch (directions[i]) {
                case UP: // up
                    if (y - 1 <= 0) // check if going up would go outside of the maze
                        continue;
                    if (super.maze[x][y - 1] == Mark.PATH) { // check that the space is available
                        super.maze[x][y] = Mark.ROUTE;
                        super.maze[x][y - 1] = Mark.CURRENT;
                        if (!this.hidden)
                            super.animate();
                        wallFollow(x, y - 1);
                    }
                    break;
                case DOWN: // down
                    if (y + 1 >= Common.mazeSize)
                        continue;
                    if (super.maze[x][y + 1] == Mark.PATH) {
                        super.maze[x][y] = Mark.ROUTE;
                        super.maze[x][y + 1] = Mark.CURRENT;
                        if (!this.hidden)
                            super.animate();
                        wallFollow(x, y + 1);
                    }
                    break;
                case LEFT: // left
                    if (x - 1 <= 0)
                        continue;
                    if (super.maze[x - 1][y] == Mark.PATH) {
                        super.maze[x][y] = Mark.ROUTE;
                        super.maze[x - 1][y] = Mark.CURRENT;
                        if (!this.hidden)
                            super.animate();
                        wallFollow(x - 1, y);
                    }
                    break;
                case RIGHT: // right
                    if (x + 1 >= Common.mazeSize)
                        continue;
                    if (super.maze[x + 1][y] == Mark.PATH) {
                        super.maze[x][y] = Mark.ROUTE;
                        super.maze[x + 1][y] = Mark.CURRENT;
                        if (!this.hidden)
                            super.animate();
                        wallFollow(x + 1, y);
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
