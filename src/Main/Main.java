package Main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;

public class Main {

    public static void main(String[] args) {
        buildGUI(); // creates GUI
    }

    public static void buildGUI() {

        Border empty;
        empty = BorderFactory.createEmptyBorder(30, 30, 30, 30); //makes border
        Component buffer = Box.createRigidArea(new Dimension(0, 10)); // creates a rigid area, used for spacing components

        JFrame frame = new JFrame("MazeTool"); //creating main frame for gui, must have
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //set it to close on "x"
        frame.setSize(500, 750); //manually sets default size
        frame.getRootPane().setBorder(empty); //adds border
        frame.setLocationRelativeTo(null); // gets the window to open in the middle of the screen
        frame.setIconImage((new ImageIcon("img/icon.png").getImage())); // sets icon image of jframe

        BoxLayout bx = new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS); // creates box layout
        frame.setLayout(bx); // sets layout

        JPanel panel = new JPanel(); // creates main panel

        JPanel text = new JPanel(); // create text panel
        JLabel title = new JLabel();
        title.setText("MazeTool"); // create title label
        title.setFont(new Font(null, Font.PLAIN, 30)); // set size to 30, using default font
        JLabel version = new JLabel();
        version.setText("v. 0.3.2"); // version number
        text.add(title);
        text.add(version);

        JPanel genDrop = new JPanel(); // create panel for maze-generation algorithm choice
        String[] genAlgorithms = GenMaze.genAlgorithms; // gets list of algorithms from GenMaze class
        JComboBox genList = new JComboBox(genAlgorithms); // creates ComboBox containing algorithms
        genDrop.add(new JLabel("Generate maze using: "));
        genDrop.add(genList);

        JPanel hiddenButton = new JPanel(); // create panel for choosing to show/hide maze generation
        JRadioButton showGen = new JRadioButton("Show generation", true); // create button to show generation, default
        JRadioButton hideGen = new JRadioButton("Hide generation"); // create button to hide gen
        ButtonGroup hidden = new ButtonGroup(); // create button group
        hidden.add(showGen); // adds buttons to button group so only one can be selected at any one time
        hidden.add(hideGen);
        hiddenButton.add(showGen); // adds buttons to panel
        hiddenButton.add(hideGen);

        JPanel maze = new JPanel(); // create main panel to hold maze
        JPanel emptyMaze = new GenMaze(); // create panel for startup, where no maze is displayed
        emptyMaze.setPreferredSize(new Dimension(Common.mazeSize * 10 + 20, Common.mazeSize * 10 + 20)); // force size of panel
        maze.add(emptyMaze);
        JPanel df = new DepthFirst(); // create panel for depth-first
        df.setPreferredSize(new Dimension(Common.mazeSize * 10 + 20, Common.mazeSize * 10 + 20));
        df.setVisible(false); // set to invisible
        maze.add(df);
        JPanel hk = new HuntAndKill(); // create panel for hunt-and-kill
        hk.setPreferredSize(new Dimension(Common.mazeSize * 10 + 20, Common.mazeSize * 10 + 20));
        hk.setVisible(false); // set to invisible
        maze.add(hk);

        JPanel ds = new DepthSolve();
        ds.setPreferredSize(new Dimension(Common.mazeSize * 10 + 20, Common.mazeSize * 10 + 20));
        ds.setVisible(false); // set to invisible
        maze.add(ds);

        JPanel buttons = new JPanel(); // create a panel for buttons so they appear side by side
        JButton genMaze = new JButton("Generate new maze"); // create 'generate new maze' button
        genMaze.setAlignmentX(JButton.CENTER_ALIGNMENT); // center the button
        genMaze.addActionListener(new ActionListener() { // when pressed;
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!Common.isRunning(((DepthFirst) df).getThread()) && !Common.isRunning(((HuntAndKill) hk).getThread())) { // check that no mazes are being generated
                    if (!Common.isRunning(((DepthSolve) ds).getThread())) {
                        emptyMaze.setVisible(false); // hide empty maze panel
                        df.setVisible(false); // make sure all maze panels are hidden
                        hk.setVisible(false);
                        ((DepthSolve) ds).setVisible(false);
                        if (genList.getSelectedItem().equals(genAlgorithms[0])) { // if depth-first is selected
                            if (showGen.isSelected()) { // if show generation is selected
                                ((DepthFirst) df).hidden = false; // set 'hidden' to false, meaning the maze generation will be animated
                            } else {
                                ((DepthFirst) df).hidden = true; // set 'hidden' to true, meaning generation process will not be shown
                            }
                            ((DepthFirst) df).rerun(); // create maze
                            df.setVisible(true); // show maze panel
                        } else if (genList.getSelectedItem().equals(genAlgorithms[1])) { // if hunt-and-kill is selected
                            if (showGen.isSelected()) { // if show generation is selected
                                ((HuntAndKill) hk).hidden = false;
                            } else {
                                ((HuntAndKill) hk).hidden = true;
                            }
                            ((HuntAndKill) hk).rerun(); // create maze
                            hk.setVisible(true); // show maze panel
                        }
                    }
                }
            }
        });

        String[] solveAlgorithms = SolveMaze.solveAlgorithms;
        JComboBox solveList = new JComboBox(solveAlgorithms);
        JButton solveMaze = new JButton("Solve maze");
        solveMaze.setAlignmentX(JButton.CENTER_ALIGNMENT);
        solveMaze.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!Common.isRunning(((DepthFirst) df).getThread()) && !Common.isRunning(((HuntAndKill) hk).getThread())) { // check that no mazes are being generated
                    emptyMaze.setVisible(false); // hide empty maze panel
                    df.setVisible(false); // make sure all maze panels are hidden
                    hk.setVisible(false);
                    ((DepthSolve) ds).setVisible(false);
                    if (solveList.getSelectedItem().equals(solveAlgorithms[0])) {
                        ((DepthSolve) ds).setVisible(true);
                        ((DepthSolve) ds).rerun();
                    }
                }
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
        solveDrop.add(new JLabel("Solve maze using: "));
        solveDrop.add(solveList);

        frame.add(panel); // adds main panel to frame
        panel.add(text); // adding things to the panel
        panel.add(buffer);
        panel.add(genDrop);
        panel.add(hiddenButton);
        panel.add(buffer);
        panel.add(maze);
        panel.add(solveDrop);
        panel.add(buffer);
        panel.add(buttons);

        frame.setVisible(true); // make the window visible
        frame.setResizable(false); // makes the frame non-resizable

    }

}
