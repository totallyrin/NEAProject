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
        version.setText("v. 0.2.2"); // version number
        text.add(title);
        text.add(version);

        JPanel genDrop = new JPanel(); // create drop box for choosing generation algorithms
        String[] genAlgorithms = GenMaze.genAlgorithms;
        JComboBox genList = new JComboBox(genAlgorithms);
        genDrop.add(new JLabel("Generate maze using: "));
        genDrop.add(genList);

        JPanel hiddenButton = new JPanel();
        JRadioButton showGen = new JRadioButton("Show generation", true);
        JRadioButton hideGen = new JRadioButton("Hide generation");
        ButtonGroup hidden = new ButtonGroup();
        hidden.add(showGen);
        hidden.add(hideGen);
        hiddenButton.add(showGen);
        hiddenButton.add(hideGen);

        JPanel maze = new JPanel();
        JPanel animation = new GenMaze();
        animation.setPreferredSize(new Dimension(Common.mazeSize * 10 + 20, Common.mazeSize * 10 + 20)); // force size of maze panel
        maze.add(animation);
        JPanel df = new DepthFirst();
        df.setPreferredSize(new Dimension(Common.mazeSize * 10 + 20, Common.mazeSize * 10 + 20));
        df.setVisible(false);
        maze.add(df);
        JPanel hk = new HuntAndKill();
        hk.setPreferredSize(new Dimension(Common.mazeSize * 10 + 20, Common.mazeSize * 10 + 20));
        hk.setVisible(false);
        maze.add(hk);

        JPanel buttons = new JPanel();
        JButton genMaze = new JButton("Generate new maze");
        genMaze.setAlignmentX(JButton.CENTER_ALIGNMENT);
        genMaze.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!Common.isRunning(((DepthFirst) df).getThread()) && !Common.isRunning(((HuntAndKill) hk).getThread())) {
                    animation.setVisible(false);
                    df.setVisible(false);
                    hk.setVisible(false);
                    if (genList.getSelectedItem().equals(genAlgorithms[0])) {
                        if (showGen.isSelected()) {
                            ((DepthFirst) df).hidden = false;
                        } else {
                            ((DepthFirst) df).hidden = true;
                        }
                        ((DepthFirst) df).rerun();
                        df.setVisible(true);
                    } else if (genList.getSelectedItem().equals(genAlgorithms[1])) {
                        if (showGen.isSelected()) {
                            ((HuntAndKill) hk).hidden = false;
                        } else {
                            ((HuntAndKill) hk).hidden = true;
                        }
                        ((HuntAndKill) hk).rerun();
                        hk.setVisible(true);
                    }
                }
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
        String[] solveAlgorithms = SolveMaze.solveAlgorithms;
        JComboBox solveList = new JComboBox(solveAlgorithms);
        solveDrop.add(new JLabel("Solve maze using: "));
        solveDrop.add(solveList);

        frame.add(panel); // adding stuff to the frame
        panel.add(text);
        panel.add(buffer);
        panel.add(genDrop);
        panel.add(hiddenButton);
        panel.add(buffer);
        panel.add(maze);
        panel.add(solveDrop);
        panel.add(buffer);
        panel.add(buttons);

        frame.setVisible(true);
        frame.setResizable(false); // makes the frame non-resizable

    }

}
