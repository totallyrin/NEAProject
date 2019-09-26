package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Animation extends JPanel implements ActionListener {

   /*public Timer tm = new Timer(50, this);
    int x = 0;
    int y = 0;
    int[][] grid = new int[40][40];
    Color bg;

    Animation() {
        bg = this.getBackground();
        this.setBackground(Color.DARK_GRAY);
    }

    public void startTimer() {
        tm.restart();
    }

    public void resetAnimation() {
        for (int i = 0; i < 40; i++){
            for (int j = 0; j < 40; j++){
                grid[i][j] = 0;
            }
        }
        repaint();
        x = 0; y = 0;
        //tm.restart();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(bg);
        g.fillRect(10, 0, 10, 10); // start square
        g.fillRect(400, 410, 10, 10); // end square
        for (int yy = 0; yy < 40; yy++) {
            for (int xx = 0; xx < 40; xx++) {
                if (grid[xx][yy] == 0){
                    g.setColor(Color.DARK_GRAY);
                    g.fillRect(xx * 10 + 10, yy * 10 + 10, 10, 10);
                } else if (grid[xx][yy] == 1) {
                    g.setColor(Color.RED);
                    g.fillRect(xx * 10 + 10, yy * 10 + 10, 10, 10);
                } else if (grid[xx][yy] == 2) {
                    g.setColor(bg);
                    g.fillRect(xx * 10 + 10, yy * 10 + 10, 10, 10);
                }
            }
        }
    }*/

    public void actionPerformed(ActionEvent e) {
        //Maze maze = new DepthFirst();

        /*if (x == 39 && y == 39){
            grid[x][y] = 2;
        }
        else if (x < 40) {
            grid[x][y] = 1;
            if (x > 0) {
                grid[x - 1][y] = 2;
            }
            x++;
        }
        else if (y < 40) {
            // note: at this point x > 39 !
            grid[x - 1][y] = 1;
            if (y > 0) {
                grid[x - 1][y - 1] = 2;
            }
            y++;
        }*/
        //repaint();
    }
}