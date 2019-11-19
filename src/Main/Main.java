package Main;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Main {

    static Mark[][] currentMaze;

    public static void main(String[] args) {
        buildGUI(); // creates GUI
    }

    private static void buildGUI() {
        Border empty;
        empty = BorderFactory.createEmptyBorder(10, 10, 10, 10); //makes border

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
        version.setText("v. 0.3.5"); // version number
        text.add(title);
        text.add(version);

        JPanel genDrop = new JPanel(); // create panel for maze-generation algorithm choice
        String[] genAlgorithms = GenMaze.genAlgorithms; // gets list of algorithms from GenMaze class
        JComboBox<String> genList = new JComboBox<>(genAlgorithms); // creates ComboBox containing algorithms
        genDrop.add(new JLabel("Generate maze using: "));
        genDrop.add(genList);

        JPanel hiddenGen = new JPanel(); // create panel for choosing to show/hide maze generation
        JRadioButton showGen = new JRadioButton("Show generation", true); // create button to show generation, default
        JRadioButton hideGen = new JRadioButton("Hide generation"); // create button to hide gen
        ButtonGroup hidden = new ButtonGroup(); // create button group
        hidden.add(showGen); // adds buttons to button group so only one can be selected at any one time
        hidden.add(hideGen);
        hiddenGen.add(showGen); // adds buttons to panel
        hiddenGen.add(hideGen);

        JPanel hiddenSolve = new JPanel(); // create panel for choosing to show/hide maze generation
        JRadioButton showSolve = new JRadioButton("Show solving", true); // create button to show generation, default
        JRadioButton hideSolve = new JRadioButton("Hide solving"); // create button to hide gen
        ButtonGroup hidden2 = new ButtonGroup(); // create button group
        hidden2.add(showSolve); // adds buttons to button group so only one can be selected at any one time
        hidden2.add(hideSolve);
        hiddenSolve.add(showSolve); // adds buttons to panel
        hiddenSolve.add(hideSolve);

        JPanel maze = new JPanel(); // create main panel to hold maze
        JPanel emptyMaze = new DepthFirst(); // create panel for startup, where no maze is displayed
        emptyMaze.setPreferredSize(new Dimension(Maze.mazeSize * 10 + 20, Maze.mazeSize * 10 + 20)); // force size of panel
        maze.add(emptyMaze);
        DepthFirst df = new DepthFirst(); // create panel for depth-first
        df.setPreferredSize(new Dimension(Maze.mazeSize * 10 + 20, Maze.mazeSize * 10 + 20));
        df.setVisible(false); // set to invisible
        maze.add(df);
        HuntAndKill hk = new HuntAndKill(); // create panel for hunt-and-kill
        hk.setPreferredSize(new Dimension(Maze.mazeSize * 10 + 20, Maze.mazeSize * 10 + 20));
        hk.setVisible(false); // set to invisible
        maze.add(hk);

        DepthSolve ds = new DepthSolve();
        ds.setPreferredSize(new Dimension(Maze.mazeSize * 10 + 20, Maze.mazeSize * 10 + 20));
        ds.setVisible(false); // set to invisible
        maze.add(ds);
        DeadEnd de = new DeadEnd();
        de.setPreferredSize(new Dimension(Maze.mazeSize * 10 + 20, Maze.mazeSize * 10 + 20));
        de.setVisible(false);
        maze.add(de);

        JPanel buttons = new JPanel(); // create a panel for buttons so they appear side by side
        JButton genMaze = new JButton("Generate new maze"); // create 'generate new maze' button
        genMaze.setAlignmentX(JButton.CENTER_ALIGNMENT); // center the button
        genMaze.addActionListener(new ActionListener() { // when pressed;
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!Maze.isActive()) { // check that no mazes are being generated
                    emptyMaze.setVisible(false); // hide empty maze panel
                    df.setVisible(false); // make sure all maze panels are hidden
                    hk.setVisible(false);
                    ds.setVisible(false);
                    de.setVisible(false);
                    if (Objects.equals(genList.getSelectedItem(), genAlgorithms[0])) { // if depth-first is selected
                        df.hidden = !showGen.isSelected(); // set 'hidden' to either show or hide generation
                        df.rerun(); // create maze
                        df.setVisible(true); // show maze panel
                    } else if (Objects.equals(genList.getSelectedItem(), genAlgorithms[1])) { // if hunt-and-kill is selected
                        hk.hidden = !showGen.isSelected();
                        hk.rerun(); // create maze
                        hk.setVisible(true); // show maze panel
                    }
                }
            }
        });

        String[] solveAlgorithms = SolveMaze.solveAlgorithms;
        JComboBox<String> solveList = new JComboBox<>(solveAlgorithms);
        JButton solveMaze = new JButton("Solve maze");
        solveMaze.setAlignmentX(JButton.CENTER_ALIGNMENT);
        solveMaze.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!Maze.isActive() && Maze.completedGen) { // check that no mazes are being generated
                    df.setVisible(false); // make sure all maze panels are hidden
                    hk.setVisible(false);
                    ds.setVisible(false);
                    de.setVisible(false);
                    if (Objects.equals(solveList.getSelectedItem(), solveAlgorithms[0])) {
                        ds.hidden = !showSolve.isSelected();
                        ds.rerun();
                        ds.setVisible(true);
                    } else if (Objects.equals(solveList.getSelectedItem(), solveAlgorithms[1])) {
                        de.hidden = !showSolve.isSelected();
                        de.rerun();
                        de.setVisible(true);
                    }
                }
            }
        });

        JButton clrMaze = new JButton("Clear solution");
        clrMaze.setAlignmentX(JButton.CENTER_ALIGNMENT);
        clrMaze.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!Maze.isActive() && Maze.completedGen) { // check that no mazes are being generated
                    df.setVisible(false); // make sure all maze panels are hidden
                    hk.setVisible(false);
                    de.setVisible(false);
                    ds.clearSolution();
                    ds.setVisible(true);
                }
            }
        });

        JButton stopAll = new JButton("Stop all");
        stopAll.setAlignmentX(JButton.CENTER_ALIGNMENT);
        stopAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Maze.stopAll();
            }
        });

        JButton export = new JButton("Save maze");
        export.setAlignmentX(JButton.CENTER_ALIGNMENT);
        export.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BufferedImage image = new BufferedImage(maze.getWidth(), maze.getHeight(), BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = image.createGraphics();
                maze.paintAll(g2d);
                g2d.dispose();
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png", "gif", "jpeg", "tiff"));
                fileChooser.setDialogTitle("Save maze");
                int userSelection = fileChooser.showSaveDialog(frame);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();
                    String file = fileToSave.getAbsolutePath(), type;
                    if (fileToSave.toString().endsWith(".tiff"))
                        type = "tiff";
                    else if (fileToSave.toString().endsWith(".png"))
                        type = "png";
                    else if (fileToSave.toString().endsWith(".gif"))
                        type = "gif";
                    else {
                        type = "png"; // force save file as png if not tiff or gif
                        file = (fileToSave.getAbsolutePath() + ".png");
                    }
                    try {
                        ImageIO.write(image, type, new File(file));
                        System.out.println("Saved as: " + file);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        buttons.add(genMaze);
        buttons.add(solveMaze);
        buttons.add(clrMaze);
        buttons.add(stopAll);
        buttons.setAlignmentY(JPanel.TOP_ALIGNMENT);

        JPanel buttons2 = new JPanel();
        buttons2.add(export);

        JPanel solveDrop = new JPanel();
        solveDrop.add(new JLabel("Solve maze using: "));
        solveDrop.add(solveList);

        frame.add(panel); // adds main panel to frame
        panel.add(text); // adding things to the panel
        panel.add(genDrop);
        panel.add(hiddenGen);
        panel.add(maze);
        panel.add(solveDrop);
        panel.add(hiddenSolve);
        panel.add(buttons);
        panel.add(buttons2);

        frame.setVisible(true); // make the window visible
        frame.setResizable(false); // makes the frame non-resizable
    }

}
