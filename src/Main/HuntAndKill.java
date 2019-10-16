package Main;

public class HuntAndKill extends GenMaze {

    boolean deadend = false;

    HuntAndKill() {
        super.initMaze();
    }

    public void run() {
        super.run();
        deadend = false;
        startGen();
        super.complete = true;
        repaint();
    }

    public void startGen() {
        huntAndKill(Common.startX, Common.startY);
    }

    public void huntAndKill(int x, int y) {
        super.cell[x][y] = Mark.CURRENT;
        Direction[] directions = Common.getDirections();
        for (int i = 0; i < directions.length; i++) {
            if (this.deadend)
                hunt(); // this now works as intended
            switch (directions[i]) {
                case UP: // up
                    if (y - 2 <= 0) // check if going up would go outside of the maze
                        continue;
                    if (super.cell[x][y - 2] == Mark.WALL) { // check that the space is available
                        super.cell[x][y] = Mark.PATH; // sets 'current' cell to no longer be current
                        super.cell[x][y - 1] = Mark.PATH; // sets the spaces ahead to a path
                        super.cell[x][y - 2] = Mark.PATH;
                        super.cell[x][y - 2] = Mark.CURRENT;
                        super.animate();
                        huntAndKill(x, y - 2); // call the same subroutine using the new coordinates
                    }
                    break;
                case DOWN: // down
                    if (y + 2 >= Common.mazeSize)
                        continue;
                    if (super.cell[x][y + 2] == Mark.WALL) {
                        super.cell[x][y] = Mark.PATH;
                        super.cell[x][y + 1] = Mark.PATH;
                        super.cell[x][y + 2] = Mark.PATH;
                        super.cell[x][y + 2] = Mark.CURRENT;
                        super.animate();
                        huntAndKill(x, y + 2);
                    }
                    break;
                case LEFT: // left
                    if (x - 2 <= 0)
                        continue;
                    if (super.cell[x - 2][y] == Mark.WALL) {
                        super.cell[x][y] = Mark.PATH;
                        super.cell[x - 1][y] = Mark.PATH;
                        super.cell[x - 2][y] = Mark.PATH;
                        super.cell[x - 2][y] = Mark.CURRENT;
                        super.animate();
                        huntAndKill(x - 2, y);
                    }
                    break;
                case RIGHT: // right
                    if (x + 2 >= Common.mazeSize)
                        continue;
                    if (super.cell[x + 2][y] == Mark.WALL) {
                        super.cell[x][y] = Mark.PATH;
                        super.cell[x + 1][y] = Mark.PATH;
                        super.cell[x + 2][y] = Mark.PATH;
                        super.cell[x + 2][y] = Mark.CURRENT;
                        super.animate();
                        huntAndKill(x + 2, y);
                    }
                    break;
            }
        }
        if (super.cell[x][y] == Mark.CURRENT) {
            super.cell[x][y] = Mark.END;
            this.deadend = true;
            super.animate();
        }
    }

    public void hunt() {
        for (int y = 1; y < Common.mazeSize; y++) { // going down
            for (int x = 1; x < Common.mazeSize; x++) { // going across (right)
                if (super.cell[x][y] == Mark.WALL && Common.checkNeighbours(super.cell, x, y) == 1) { // checks that the current cell has only one neighbouring cell
                    switch (Common.neighbourDirection(super.cell, x, y)) {
                        case UP:
                            if (y + 1 < Common.mazeSize && Common.checkNeighbours(super.cell, x, y + 1) == 0) {
                                this.deadend = false;
                                super.cell[x][y] = Mark.PATH;
                                super.cell[x][y + 1] = Mark.PATH;
                                huntAndKill(x, y + 1);
                            }
                            break;
                        case DOWN:
                            if (y - 1 > 0 && Common.checkNeighbours(super.cell, x, y - 1) == 0) {
                                this.deadend = false;
                                super.cell[x][y] = Mark.PATH;
                                super.cell[x][y - 1] = Mark.PATH;
                                huntAndKill(x, y - 1);
                            }
                            break;
                        case LEFT:
                            if (x + 1 < Common.mazeSize && Common.checkNeighbours(super.cell, x + 1, y) == 0) {
                                this.deadend = false;
                                super.cell[x][y] = Mark.PATH;
                                super.cell[x + 1][y] = Mark.PATH;
                                huntAndKill(x + 1, y);
                            }
                            break;
                        case RIGHT:
                            if (x - 1 > 0 && Common.checkNeighbours(super.cell, x - 1, y) == 0) {
                                this.deadend = false;
                                super.cell[x][y] = Mark.PATH;
                                super.cell[x - 1][y] = Mark.PATH;
                                huntAndKill(x - 1, y);
                            }
                            break;
                    }
                }
            }
        }
    }
}
