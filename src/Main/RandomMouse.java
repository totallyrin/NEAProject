package Main;

public class RandomMouse extends SolveMaze {

    RandomMouse(){
        super.initMaze();
    }

    public void run() {
        super.run();
        startSolve();
        repaint();
    }

    public void startSolve(){
        mazeSolving();
    }

    public void mazeSolving(){
        int x = 0, y = 0;

        while (x != Common.endX && y != Common.endY){

        }

    }

}
