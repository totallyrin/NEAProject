package Main;

import java.util.*;

public class RandomisedKruskal extends GenMaze {

    public void run() {
        super.run();
        kruskalGen();
        if (!stop) {
            completedGen = true;
            Main.currentMaze = super.complete(super.maze);
            repaint();
        }
    }

    @Override
    public void animate() {
        super.animate(50);
    }

    /*
    Create a list of all walls, and make a set for each cell.
    For each wall, if the cells divided by the wall belong to different sets, merge the sets and remove the wall.
    Repeat until there is only one set/all other sets are empty.
     */

    private void kruskalGen() {
        // create a list of all walls
        ArrayList<int[]> walls = new ArrayList<>();
        for (int j = 2; j < mazeSize - 2; j += 2) {
            for (int i = 1; i < mazeSize - 1; i++) {
                walls.add(new int[]{i, j});
            }
        }
        for (int j = 1; j < mazeSize - 1; j++) {
            for (int i = 2; i < mazeSize - 2; i += 2) {
                walls.add(new int[]{i, j});
            }
        }
        // create a list containing sets for each cell
        ArrayList<HashSet<int[]>> cells = new ArrayList<>();
        for (int j = 1; j < mazeSize - 1; j += 2) {
            for (int i = 1; i < mazeSize - 1; i += 2) {
                cells.add(new HashSet<int[]>(Collections.singleton(new int[]{i, j})));
            }
        }
        while (cells.size() > 1) {
            HashSet<int[]> set1 = null, set2 = null;
            if (stop)
                return;
            // choose a random wall, get coords of that wall
            int rand = random.nextInt(walls.size());
            int[] wall = walls.get(rand);
            int x = wall[0], y = wall[1];
            if (!((x % 2 == 0) && (y % 2 == 0))) {
                // choose random direction
                switch (getDirections()[0]) {
                    case UP:
                    case DOWN:
                        if (y - 1 <= 0)
                            continue;
                        if (y + 1 >= mazeSize)
                            continue;
                        if (x % 2 == 0)
                            continue;
                        // find sets containing cells to be joined
                        for (HashSet<int[]> set : cells) {
                            for (int[] cell : set) {
                                if ((cell[0] == x) && (cell[1] == y - 1)) {
                                    set1 = set;
                                    break;
                                }
                            }
                            if (set1 != null)
                                break;
                        }
                        for (HashSet<int[]> set : cells) {
                            for (int[] cell : set) {
                                if ((cell[0] == x) && (cell[1] == y + 1)) {
                                    set2 = set;
                                    break;
                                }
                            }
                            if (set2 != null)
                                break;
                        }
                        // check if cells are in the same set
                        boolean same = false;
                        for (int[] j : set1) {
                            for (int[] k : set2) {
                                if ((j[0] == k[0]) && (j[1] == k[1])) {
                                    same = true;
                                    break;
                                }
                            }
                            if (same)
                                break;
                        }
                        if (same)
                            continue;
                        else {
                            // merge sets
                            cells.remove(set1);
                            cells.remove(set2);
                            set1.add(wall);
                            set1.addAll(set2);
                            cells.add(set1);
                            // set those cells to paths
                            for (int[] c : set1) {
                                maze[c[0]][c[1]] = Mark.PATH;
                                if (!this.hidden)
                                    animate();
                            }
                            walls.remove(wall);
                        }
                        break;
                    case LEFT:
                    case RIGHT:
                        if (x - 1 <= 0)
                            continue;
                        if (x + 1 >= mazeSize)
                            continue;
                        if (y % 2 == 0)
                            continue;
                        // find sets containing cells to be joined
                        for (HashSet<int[]> set : cells) {
                            for (int[] cell : set) {
                                if ((cell[0] == x - 1) && (cell[1] == y)) {
                                    set1 = set;
                                    break;
                                }
                            }
                            if (set1 != null)
                                break;
                        }
                        for (HashSet<int[]> set : cells) {
                            for (int[] cell : set) {
                                if ((cell[0] == x + 1) && (cell[1] == y)) {
                                    set2 = set;
                                    break;
                                }
                            }
                            if (set2 != null)
                                break;
                        }
                        // check if cells are in the same set
                        same = false;
                        for (int[] j : set1) {
                            for (int[] k : set2) {
                                if ((j[0] == k[0]) && (j[1] == k[1])) {
                                    same = true;
                                    break;
                                }
                            }
                            if (same)
                                break;
                        }
                        if (same)
                            continue;
                        else {
                            // merge sets
                            cells.remove(set1);
                            cells.remove(set2);
                            set1.add(wall);
                            set1.addAll(set2);
                            cells.add(set1);
                            // set those cells to paths
                            for (int[] c : set1) {
                                maze[c[0]][c[1]] = Mark.PATH;
                                if (!this.hidden)
                                    animate();
                            }
                            walls.remove(wall);
                        }
                        break;
                }
            }
        }
    }
}
