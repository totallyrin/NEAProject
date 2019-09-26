package Main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;

import static Main.Maze.mazeSize;

public class Main {

    public static void main(String[] args) {
        buildGUI();
    }

    public static void buildGUI(){

        Border empty;
        empty = BorderFactory.createEmptyBorder(30, 30, 30, 30); //makes border

        JFrame frame = new JFrame("Maze Tool"); //creating main frame for gui, must have
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //set it to close on "x"
        frame.setSize(500, 750); //manually sets default size
        frame.getRootPane().setBorder(empty); //adds border

        BoxLayout bx = new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS);
        frame.setLayout(bx);

        JPanel panel = new JPanel();

        JPanel text = new JPanel();
        JLabel title = new JLabel();
        title.setText("MazeTool");
        title.setFont(new Font(null, Font.PLAIN, 30));
        JLabel version = new JLabel();
        version.setText("v. 0.1.0");
        text.add(title);
        text.add(version);

        JPanel genDrop = new JPanel();
        String[] genAlgorithms = {"Depth-first search", "Randomised Kruskal's algorithm", "Randomized Prim's algorithm"};
        JComboBox genList = new JComboBox(genAlgorithms);
        genDrop.add(new JLabel("Generate maze using: "));
        genDrop.add(genList);

        JPanel animation = new DepthFirst();
        animation.setPreferredSize(new Dimension(mazeSize * 10 + 20, mazeSize * 10 + 20));

        JPanel buttons = new JPanel();
        JButton genMaze = new JButton("Generate new maze");
        genMaze.setAlignmentX(JButton.CENTER_ALIGNMENT);
        genMaze.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((DepthFirst) animation).resetAnimation(); // resets the 'canvas'
                ((DepthFirst) animation).startTimer(); // starts the animation
                ((DepthFirst) animation).run();
                //String genType = genList.getSelectedItem().toString();
            }
        });

        JButton solveMaze = new JButton("Solve maze");
        solveMaze.setAlignmentX(JButton.CENTER_ALIGNMENT);
        solveMaze.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        JButton clrMaze = new JButton("Clear solution");
        clrMaze.setAlignmentX(JButton.CENTER_ALIGNMENT);
        clrMaze.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        buttons.add(genMaze);
        buttons.add(solveMaze);
        buttons.add(clrMaze);
        buttons.setAlignmentY(JPanel.TOP_ALIGNMENT);

        JPanel solveDrop = new JPanel();
        String[] solveAlgorithms = {"Random Mouse algorithm", "Wall Follower", "Tremeaux's algorithm"};
        JComboBox solveList = new JComboBox(solveAlgorithms);
        solveDrop.add(new JLabel("Solve maze using: "));
        solveDrop.add(solveList);

        frame.add(panel);
        panel.add(text);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(genDrop);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(animation);
        panel.add(solveDrop);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(buttons);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        frame.setVisible(true);
        frame.setResizable(false);

    }

}
