package Main;

public class HuntAndKill extends GenMaze {

    int row = 1, collumn = 1;

    HuntAndKill() {
        super.initMaze();
    }

    public void run() {
        super.run();
        row = 1;
        collumn = 1;
        startGen();
        super.complete = true;
        repaint();
        currentMaze = super.complete(super.maze);
    }

    public void startGen() {
        huntAndKill(startX, startY);
    }

    public void huntAndKill(int x, int y) {
        super.maze[x][y] = Mark.CURRENT;
        Direction[] directions = getDirections();
        for (int i = 0; i < directions.length; i++) {
            switch (directions[i]) {
                case UP: // up
                    if (y - 2 <= 0) // check if going up would go outside of the maze
                        continue;
                    if (super.maze[x][y - 2] == Mark.WALL) { // check that the space is available
                        super.maze[x][y] = Mark.PATH; // sets 'current' maze to no longer be current
                        super.maze[x][y - 1] = Mark.PATH; // sets the spaces ahead to a path
                        super.maze[x][y - 2] = Mark.CURRENT;
                        if (!this.hidden)
                            super.animate();
                        huntAndKill(x, y - 2); // call the same subroutine using the new coordinates
                    }
                    break;
                case DOWN: // down
                    if (y + 2 >= mazeSize)
                        continue;
                    if (super.maze[x][y + 2] == Mark.WALL) {
                        super.maze[x][y] = Mark.PATH;
                        super.maze[x][y + 1] = Mark.PATH;
                        super.maze[x][y + 2] = Mark.CURRENT;
                        if (!this.hidden)
                            super.animate();
                        huntAndKill(x, y + 2);
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
                        huntAndKill(x - 2, y);
                    }
                    break;
                case RIGHT: // right
                    if (x + 2 >= mazeSize)
                        continue;
                    if (super.maze[x + 2][y] == Mark.WALL) {
                        super.maze[x][y] = Mark.PATH;
                        super.maze[x + 1][y] = Mark.PATH;
                        super.maze[x + 2][y] = Mark.CURRENT;
                        if (!this.hidden)
                            super.animate();
                        huntAndKill(x + 2, y);
                    }
                    break;
            }
        }
        if (super.maze[x][y] == Mark.CURRENT) {
            super.maze[x][y] = Mark.END;
            if (!this.hidden)
                super.animate();
            hunt();
        }
    }

    public void hunt() {
        Mark temp;
        for (int y = row; y < mazeSize - 1; y++) { // going down
            for (int x = collumn; x < mazeSize - 1; x++) { // going across (right)
                temp = super.maze[x][y];
                super.maze[x][y] = Mark.SEARCH;
                if (!this.hidden)
                    super.animate(25);
                super.maze[x][y] = temp;
                if (super.maze[x][y] == Mark.WALL && checkNeighbours(super.maze, x, y) == 1) { // checks that the current maze has only one neighbouring maze
                    switch (neighbourDirection(super.maze, x, y)) {
                        case UP:
                            if (y + 1 < mazeSize && checkNeighbours(super.maze, x, y + 1) == 0) {
                                super.maze[x][y] = Mark.PATH;
                                super.maze[x][y + 1] = Mark.PATH;
                                row = y;
                                collumn = x;
                                huntAndKill(x, y + 1);
                                return;
                            }
                            break;
                        case DOWN:
                            if (y - 1 > 0 && checkNeighbours(super.maze, x, y - 1) == 0) {
                                super.maze[x][y] = Mark.PATH;
                                super.maze[x][y - 1] = Mark.PATH;
                                row = y;
                                collumn = x;
                                huntAndKill(x, y - 1);
                                return;
                            }
                            break;
                        case LEFT:
                            if (x + 1 < mazeSize && checkNeighbours(super.maze, x + 1, y) == 0) {
                                super.maze[x][y] = Mark.PATH;
                                super.maze[x + 1][y] = Mark.PATH;
                                row = y;
                                collumn = x;
                                huntAndKill(x + 1, y);
                                return;
                            }
                            break;
                        case RIGHT:
                            if (x - 1 > 0 && checkNeighbours(super.maze, x - 1, y) == 0) {
                                super.maze[x][y] = Mark.PATH;
                                super.maze[x - 1][y] = Mark.PATH;
                                row = y;
                                collumn = x;
                                huntAndKill(x - 1, y);
                                return;
                            }
                            break;
                    }
                }
                collumn = 1;
            }
        }
    }
}
