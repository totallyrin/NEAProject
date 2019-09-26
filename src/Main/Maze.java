package Main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.*;

public abstract class Maze extends JPanel implements ActionListener {

    public static final int mazeSize = 35;
    volatile static Random random = new Random();
    public Timer tm = new Timer(50, this);
    volatile static Mark[][] cell = new Mark[mazeSize][mazeSize];
    volatile int x = 0, y = 0;
    volatile static int startX = 1, startY = 1, endX = mazeSize - 1, endY = mazeSize - 1;
    Color bg;

    Maze() {
        bg = this.getBackground();
        this.setBackground(Color.DARK_GRAY);
    }

    public void startTimer() {
        tm.restart();
    }

    public void resetAnimation() {
        for (int i = 0; i < mazeSize; i++){
            for (int j = 0; j < mazeSize; j++){
                cell[i][j] = Mark.WALL;
            }
        }
        repaint();
        x = 0; y = 0;
    }

    public void initMaze(){
        for(int i = 0; i < mazeSize; i++){
            for(int j = 0; j < mazeSize; j++){
                cell[i][j] = Mark.WALL;
            }
        }

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(bg);
        g.fillRect(20, 0, 10, 20); // start square
        g.fillRect(mazeSize * 10 - 10, mazeSize * 10 + 10, 10, 10); //
        cell[1][0] = Mark.PATH;
        cell[mazeSize - 2][mazeSize - 1] = Mark.PATH;
        for (int yy = 0; yy < mazeSize; yy++) {
            for (int xx = 0; xx < mazeSize; xx++) {
                if (cell[xx][yy] == Mark.WALL){
                    g.setColor(Color.DARK_GRAY);
                    g.fillRect(xx * 10 + 10, yy * 10 + 10, 10, 10);
                } else if (cell[xx][yy] == Mark.PATH) {
                    g.setColor(bg);
                    g.fillRect(xx * 10 + 10, yy * 10 + 10, 10, 10);
                } else if (cell[xx][yy] == Mark.CURRENT) {
                    g.setColor(Color.RED);
                    g.fillRect(xx * 10 + 10, yy * 10 + 10, 10, 10);
                }
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        repaint();
    }

}
