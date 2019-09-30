package Main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;

public class Main {

    public static void main(String[] args) {
        buildGUI();
    }

    public static void buildGUI() {

        Border empty;
        empty = BorderFactory.createEmptyBorder(30, 30, 30, 30); //makes border
        Component buffer = Box.createRigidArea(new Dimension(0, 10));

        JFrame frame = new JFrame("MazeTool"); //creating main frame for gui, must have
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //set it to close on "x"
        frame.setSize(500, 750); //manually sets default size
        frame.getRootPane().setBorder(empty); //adds border
        frame.setLocationRelativeTo(null); // gets the window to open in the middle of the screen

        BoxLayout bx = new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS); // creates box layout
        frame.setLayout(bx); // sets layout

        JPanel panel = new JPanel(); // creates main panel

        JPanel text = new JPanel(); // create text panel
        JLabel title = new JLabel();
        title.setText("MazeTool"); // create title label
        title.setFont(new Font(null, Font.PLAIN, 30)); // set size to 30, using default font
        JLabel version = new JLabel();
        version.setText("v. 0.1.6"); // version number
        text.add(title);
        text.add(version);

        JPanel genDrop = new JPanel(); // create drop box for choosing generation algorithms
        String[] genAlgorithms = {"Depth-first search", "Randomised Kruskal's algorithm", "Randomized Prim's algorithm"};
        JComboBox genList = new JComboBox(genAlgorithms);
        genDrop.add(new JLabel("Generate maze using: "));
        genDrop.add(genList);

        JPanel animation = new DepthFirst();
        animation.setPreferredSize(new Dimension(GenMaze.mazeSize * 10 + 20, GenMaze.mazeSize * 10 + 20)); // force size of maze panel

        JPanel buttons = new JPanel();
        JButton genMaze = new JButton("Generate new maze");
        genMaze.setAlignmentX(JButton.CENTER_ALIGNMENT);
        genMaze.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((DepthFirst) animation).rerun();
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

        frame.add(panel); // adding stuff to the frame
        panel.add(text);
        panel.add(buffer);
        panel.add(genDrop);
        panel.add(buffer);
        panel.add(animation);
        panel.add(solveDrop);
        panel.add(buffer);
        panel.add(buttons);

        frame.setVisible(true);
        frame.setResizable(false); // makes the frame non-resizable

    }

}
