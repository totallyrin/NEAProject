package Main;

interface rePaint {
    void onRepaint();
}

class RP implements rePaint {
    @Override
    public void onRepaint() {
        new DepthFirst().repaint();
    }
}

/*class DepthFirstThread extends Thread {

    private rePaint rp;

    public void run() {
        rp = new RP();
        depthFirstGeneration(Maze.startX, Maze.startY);
    }

    public void depthFirstGeneration(int x, int y) { // up = 1, right = 2, down = 3, left = 4
        Maze.cell[x][y] = 1;
        //Maze.rpaint();
        rp.onRepaint();
        try {
            Thread.sleep(10);
        } catch (Exception e) {
            System.out.println(e);
        }
        int[] directions = getDirections();
        for (int i = 0; i < 4; i++) {

            switch (directions[i]) {
                case 1: // up
                    if (y - 2 <= 0) // check if going up would go outside of the maze
                        continue;
                    if (Maze.cell[x][y - 2] == 0) { // check that the space is available
                        Maze.cell[x][y - 1] = 1;
                        Maze.cell[x][y - 2] = 1;
                        depthFirstGeneration(x, y - 2);
                    }
                    break;
                case 2: // right
                    if (x + 2 >= 42)
                        continue;
                    if (Maze.cell[x + 2][y] == 0) {
                        Maze.cell[x + 1][y] = 1;
                        Maze.cell[x + 2][y] = 1;
                        depthFirstGeneration(x + 2, y);
                    }
                    break;
                case 3: // down
                    if (y + 2 >= 42)
                        continue;
                    if (Maze.cell[x][y + 2] == 0) {
                        Maze.cell[x][y + 1] = 1;
                        Maze.cell[x][y + 2] = 1;
                        depthFirstGeneration(x, y + 2);
                    }
                    break;
                case 4: // left
                    if (x - 2 <= 0)
                        continue;
                    if (Maze.cell[x - 2][y] == 0) {
                        Maze.cell[x - 1][y] = 1;
                        Maze.cell[x - 2][y] = 1;
                        depthFirstGeneration(x - 2, y);
                    }
                    break;
            }
        }
    }

    public int[] getDirections() {
        int[] directions = {1, 2, 3, 4};
        int[] shuffle = new int[4];
        for (int i = 0; i < directions.length; i++) {
            int rand = Maze.random.nextInt(directions.length);
            while (directions[rand] == 0) {
                rand = Maze.random.nextInt(directions.length);
            }
            int temp = directions[rand];
            directions[rand] = 0;
            shuffle[i] = temp;
        }
        return shuffle;
    }

}*/

public class DepthFirst extends Maze implements Runnable {

    DepthFirst() {
        super.initMaze();
    }

    public void run() {
        depthFirstGeneration(Maze.startX, Maze.startY);
    }

    public void startGen() {
        depthFirstGeneration(super.startX, super.startY);
    }

    public void depthFirstGeneration(int x, int y) { // up = 1, right = 2, down = 3, left = 4
        super.cell[x][y] = Mark.CURRENT;
        /*try {
            Thread.sleep(10);
        } catch (Exception e) {
            System.out.println(e);
        }*/
        Dir[] directions = getDirections();
        for (int i = 0; i < directions.length; i++) {

            switch (directions[i]) {
                case UP: // up
                    if (y - 2 <= 0) // check if going up would go outside of the maze
                        continue;
                    if (super.cell[x][y - 2] == Mark.WALL) { // check that the space is available
                        super.cell[x][y] = Mark.PATH; // sets 'current' cell to no longer be current
                        super.cell[x][y - 1] = Mark.PATH; // sets the spaces ahead to a path
                        super.cell[x][y - 2] = Mark.PATH;
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
                        depthFirstGeneration(x + 2, y);
                    }
                    break;
            }
        }
        this.repaint();
        this.revalidate();
    }

    public Dir[] getDirections() {
        Dir[] directions = {Dir.UP, Dir.DOWN, Dir.LEFT, Dir.RIGHT};
        Dir[] shuffle = new Dir[4];
        for (int i = 0; i < directions.length; i++) {
            int rand = super.random.nextInt(directions.length);
            while (directions[rand] == Dir.NULL) {
                rand = super.random.nextInt(directions.length);
            }
            Dir temp = directions[rand];
            directions[rand] = Dir.NULL;
            shuffle[i] = temp;
        }
        return shuffle;
    }

}
