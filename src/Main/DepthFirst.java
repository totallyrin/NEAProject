package Main;

public class DepthFirst extends Maze {

    DepthFirst() {
        super.initMaze();
    }

    public void run() {
        super.run();
        startGen();
        super.complete = true;
        repaint();
    }

    public void startGen() {
        depthFirstGeneration(super.startX, super.startY);
    }

    public void depthFirstGeneration(int x, int y) {
        super.cell[x][y] = Mark.CURRENT;
        Direction[] directions = getDirections();
        for (int i = 0; i < directions.length; i++) {

            switch (directions[i]) {
                case UP: // up
                    if (y - 2 <= 0) // check if going up would go outside of the maze
                        continue;
                    if (super.cell[x][y - 2] == Mark.WALL) { // check that the space is available
                        super.cell[x][y] = Mark.PATH; // sets 'current' cell to no longer be current
                        super.cell[x][y - 1] = Mark.PATH; // sets the spaces ahead to a path
                        super.cell[x][y - 2] = Mark.PATH;
                        animate();
                        depthFirstGeneration(x, y - 2); // call the same subroutine using the new coordinates
                    }
                    break;
                case DOWN: // down
                    if (y + 2 >= mazeSize)
                        continue;
                    if (super.cell[x][y + 2] == Mark.WALL) {
                        super.cell[x][y] = Mark.PATH;
                        super.cell[x][y + 1] = Mark.PATH;
                        super.cell[x][y + 2] = Mark.PATH;
                        animate();
                        depthFirstGeneration(x, y + 2);
                    }
                    break;
                case LEFT: // left
                    if (x - 2 <= 0)
                        continue;
                    if (super.cell[x - 2][y] == Mark.WALL) {
                        super.cell[x][y] = Mark.PATH;
                        super.cell[x - 1][y] = Mark.PATH;
                        super.cell[x - 2][y] = Mark.PATH;
                        animate();
                        depthFirstGeneration(x - 2, y);
                    }
                    break;
                case RIGHT: // right
                    if (x + 2 >= mazeSize)
                        continue;
                    if (super.cell[x + 2][y] == Mark.WALL) {
                        super.cell[x][y] = Mark.PATH;
                        super.cell[x + 1][y] = Mark.PATH;
                        super.cell[x + 2][y] = Mark.PATH;
                        animate();
                        depthFirstGeneration(x + 2, y);
                    }
                    break;
            }
        }
    }

    public Direction[] getDirections() {
        Direction[] directions = {Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT}; // create an array with the 4 valid directions
        Direction[] shuffle = new Direction[4]; // create an empty array for the shuffled set of directions
        for (int i = 0; i < directions.length; i++) {
            int rand = super.random.nextInt(directions.length); // choose a random number from 0-3
            while (directions[rand] == Direction.NULL) { // if the direction it chooses is not valid
                rand = super.random.nextInt(directions.length); // continue generating numbers until it can choose a valid direction
            }
            Direction temp = directions[rand]; // get the chosen direction
            directions[rand] = Direction.NULL; // set the space to null
            shuffle[i] = temp; // put the chosen direction in the correct space in the shuffled array
        }
        return shuffle;
    }

}
