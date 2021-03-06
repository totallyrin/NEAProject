package Main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Main {

    static Mark[][] currentMaze;

    public static void main(String[] args) {
        buildGUI(); // creates GUI on start-up
    }

    // method to build the GUI
    private static void buildGUI() {
        // creates JFrame
        JFrame frame = new JFrame("MazeTool");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 760);
        frame.setLocationRelativeTo(null);                                      // makes frame appear in center of screen
        frame.setIconImage((new ImageIcon("img/icon.png").getImage()));

        // creates border around JFrame
        Border empty;
        empty = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        frame.getRootPane().setBorder(empty);

        // creates box layout
        BoxLayout bx = new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS);
        frame.setLayout(bx);

        // creates main panel
        JPanel panel = new JPanel();

        // create text panel, including title and version number, using default font
        JPanel text = new JPanel();
        JLabel title = new JLabel();
        title.setText("MazeTool");
        title.setFont(new Font(null, Font.PLAIN, 30));
        JLabel version = new JLabel();
        version.setText("v. 1.0.0");
        text.add(title);
        text.add(version);

        // create panel for maze-generation algorithm choice, with comboBox to choose which algorithm to use
        JPanel genDrop = new JPanel();
        String[] genAlgorithms = GenMaze.genAlgorithms;             // gets array of algorithms from GenMaze class
        JComboBox<String> genList = new JComboBox<>(genAlgorithms);
        genDrop.add(new JLabel("Generate maze using: "));
        genDrop.add(genList);

        // create panel for choosing to show/hide maze generation
        JPanel hiddenGen = new JPanel();
        JRadioButton showGen = new JRadioButton("Show generation", true); // create button to show generation, default
        JRadioButton hideGen = new JRadioButton("Hide generation");
        ButtonGroup hidden = new ButtonGroup();
        hidden.add(showGen);        // adds buttons to button group so only one can be selected at any one time
        hidden.add(hideGen);
        hiddenGen.add(showGen);
        hiddenGen.add(hideGen);

        // create panel for choosing to show/hide maze generation
        JPanel hiddenSolve = new JPanel();
        JRadioButton showSolve = new JRadioButton("Show solving", true); // create button to show generation, default
        JRadioButton hideSolve = new JRadioButton("Hide solving");
        ButtonGroup hidden2 = new ButtonGroup();
        hidden2.add(showSolve); // adds buttons to button group so only one can be selected at any one time
        hidden2.add(hideSolve);
        hiddenSolve.add(showSolve);
        hiddenSolve.add(hideSolve);

        // create panel to control animation speed
        JPanel speed = new JPanel();
        // create buttons to control speed
        JRadioButton slow = new JRadioButton("Slow");
        JRadioButton normal = new JRadioButton("Normal", true);
        JRadioButton fast = new JRadioButton("Fast");
        // create buttonGroup so that only one speed can be selected at a time
        ButtonGroup speedSelection = new ButtonGroup();
        // add speeds to button group
        speedSelection.add(slow);
        speedSelection.add(normal);
        speedSelection.add(fast);
        // add
        speed.add(slow);
        speed.add(normal);
        speed.add(fast);

        // create main panel to hold maze
        JPanel maze = new JPanel();

        // creating panels for algorithms
        JPanel emptyMaze = new DepthFirst();                            // create panel for startup, where no maze is displayed
        DepthFirst depthFirst = new DepthFirst();                       // create panel for depth-first
        HuntAndKill huntAndKill = new HuntAndKill();                    // create panel for hunt-and-kill
        RandomisedKruskal randomisedKruskal = new RandomisedKruskal();  // create panel for randomised kruskal's algorithm
        DepthSolve depthSolve = new DepthSolve();                       // create panel for depth-first solving
        DeadEnd deadEnd = new DeadEnd();                                // create panel for dead-end filling
        ChainSolve chainSolve = new ChainSolve();                       // create panel for chain solving

        // add all generation/solving panels to a list for ease
        JPanel[] panels = {emptyMaze, depthFirst, huntAndKill, randomisedKruskal, depthSolve, deadEnd, chainSolve};

        setDimensions(panels);      // set all generation/solving panels to the same dimensions
        addAll(panels, maze);       // add all generation/solving panels to the maze panel
        hideAll(panels);            // hide all generation/solving panels
        emptyMaze.setVisible(true); // show empty maze panel for startup

        // create a panel for buttons so they appear side by side
        JPanel buttons = new JPanel();
        JButton genMaze = new JButton("Generate new maze");
        genMaze.setAlignmentX(JButton.CENTER_ALIGNMENT);
        genMaze.addActionListener(e -> {
            // check that no mazes are being generated/solved
            if (Maze.notActive()) {
                // change animation speed based on selected option
                if (slow.isSelected())
                    GenMaze.speed = 200;
                else if (fast.isSelected())
                    GenMaze.speed = 50;
                else
                    GenMaze.speed = 100;

                Maze.hidden = !showGen.isSelected(); // set 'hidden' to either show or hide generation
                hideAll(panels);                     // make sure all maze panels are hidden

                if (Objects.equals(genList.getSelectedItem(), genAlgorithms[0])) {          // if depth-first is selected
                    depthFirst.rerun();                                                     // start generation process
                    depthFirst.setVisible(true);                                            // show maze panel
                } else if (Objects.equals(genList.getSelectedItem(), genAlgorithms[1])) {   // if hunt-and-kill is selected
                    huntAndKill.rerun();
                    huntAndKill.setVisible(true);
                } else if (Objects.equals(genList.getSelectedItem(), genAlgorithms[2])) {   // if randomised kruskal is selected
                    randomisedKruskal.rerun();
                    randomisedKruskal.setVisible(true);
                }
            }
        });

        // creates comboBox for choosing maze-solving algorithms
        String[] solveAlgorithms = SolveMaze.solveAlgorithms;
        JComboBox<String> solveList = new JComboBox<>(solveAlgorithms);

        // creates button for starting solving
        JButton solveMaze = new JButton("Solve maze");
        solveMaze.setAlignmentX(JButton.CENTER_ALIGNMENT);
        solveMaze.addActionListener(e -> {
            // check that no mazes are being generated/solved
            if (Maze.notActive() && Maze.completedGen) {
                // change animation speed based on selected option
                if (slow.isSelected())
                    SolveMaze.speed = 100;
                else if (fast.isSelected())
                    SolveMaze.speed = 25;
                else
                    SolveMaze.speed = 50;

                Maze.hidden = !showSolve.isSelected();  // set 'hidden' to either show or hide generation
                hideAll(panels);                        // make sure all maze panels are hidden

                if (Objects.equals(solveList.getSelectedItem(), solveAlgorithms[0])) {          // if depth-first is selected
                    depthSolve.rerun();                                                         // start solving process
                    depthSolve.setVisible(true);                                                // show panel
                } else if (Objects.equals(solveList.getSelectedItem(), solveAlgorithms[1])) {   // if dead-end filling is selected
                    deadEnd.rerun();                                                            // start solving process
                    deadEnd.setVisible(true);
                } else if (Objects.equals(solveList.getSelectedItem(), solveAlgorithms[2])) {   // if chain solving is selected
                    chainSolve.rerun();
                    chainSolve.setVisible(true);
                }
            }
        });

        // creates button to clear current solution
        JButton clrMaze = new JButton("Clear solution");
        clrMaze.setAlignmentX(JButton.CENTER_ALIGNMENT);
        clrMaze.addActionListener(e -> {
            // check that no mazes are being generated
            if (Maze.notActive() && Maze.completedGen) {
                hideAll(panels);                            // make sure all maze panels are hidden
                depthSolve.clearSolution();                 // clear the current solution
                depthSolve.setVisible(true);                // shows default panel for maze-solving
            }
        });

        // added button to stop all current generation/solving
        JButton stopAll = new JButton("Stop all");
        stopAll.setAlignmentX(JButton.CENTER_ALIGNMENT);
        stopAll.addActionListener(e -> Maze.stopAll());

        // added button to export current maze to image
        JButton export = new JButton("Save maze");
        export.setAlignmentX(JButton.CENTER_ALIGNMENT);
        export.addActionListener(e -> {
            // create image from maze panel
            BufferedImage image = new BufferedImage(maze.getWidth(), maze.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = image.createGraphics();
            maze.paintAll(g2d);
            g2d.dispose();
            // create fileChooser
            JFileChooser fileChooser = new JFileChooser();
            // create a file filter of supported image files
            fileChooser.setFileFilter(new FileNameExtensionFilter("PNG, GIF, TIFF", "png", "gif", "tiff"));
            fileChooser.setDialogTitle("Save maze");
            int userSelection = fileChooser.showSaveDialog(frame);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                // get file name
                File fileToSave = fileChooser.getSelectedFile();
                // get file path
                String file = fileToSave.getAbsolutePath(), type;
                // set type of file based on ending
                if (fileToSave.toString().endsWith(".png"))
                    type = "png";
                else if (fileToSave.toString().endsWith(".gif"))
                    type = "gif";
                else if (fileToSave.toString().endsWith(".tiff"))
                    type = "tiff";
                else {
                    // force save file as png if not tiff or gif
                    type = "png";
                    file = (fileToSave.getAbsolutePath() + ".png");
                }
                // attempt to save file
                try {
                    ImageIO.write(image, type, new File(file));
                    System.out.println("Saved as: " + file);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // adds button which brings up information about the included algorithms
        JButton info = new JButton("About Algorithms");
        info.setAlignmentX(JButton.CENTER_ALIGNMENT);
        info.addActionListener(e -> JOptionPane.showMessageDialog(null,
                "Here is a brief description of the algorithms used in MazeTool:\n\n" +
                        "Generation:\n" +
                        "Depth First (Recursive Backtracker) - Randomly chooses a direction to travel in until it is no longer possible to travel in that direction.\n" +
                        "The algorithm returns to previously visited cells until it is possible to travel in a new direction.\n" +
                        "This repeats until all spaces are filled.\n" +
                        "\n" +
                        "Hunt-and-Kill algorithm - Similar to depth-first, randomly carve a path until you cannot go any further.\n" +
                        "Then, iterate through every cell in the grid until you get to an unvisited cell adjacent to a visited cell,\n" +
                        "and continue randomly carving a path from there. Repeat until all cells have been visited.\n" +
                        "\n" +
                        "Randomised Kruskal’s algorithm - Create a list of all walls, and make a set for each cell.\n" +
                        "For each wall, if the cells divided by the wall belong to different sets, merge the sets and remove the wall.\n" +
                        "Repeat until there is only one set/all other sets are empty.\n\n" +
                        "Solving:\n" +
                        "Depth-First (Recursive backtracker) - Randomly chooses a direction to travel in until it is no longer possible to travel in that direction.\n" +
                        "The algorithm returns to previously visited cells until it is possible to travel in a new direction.\n" +
                        "This repeats until the solution is found.\n" +
                        "\n" +
                        "Dead-end Filling - Fill in all dead ends until a junction is reached.\n" +
                        "This should fill in all dead ends and leave only the correct way unfilled.\n" +
                        "\n" +
                        "Chain Algorithm - Start by drawing a straight line (or at least a line that doesn't double back on itself) from start to end,\n" +
                        "letting it cross walls if needed, then just follow the line from start to end.\n" +
                        "If you bump into a wall, you can't go through it, so you have to go around.\n" +
                        "Send two wall following \"robots\" in both directions along the wall you hit.\n" +
                        "If a robot runs into the guiding line again, and at a point which is closer to the exit, then stop,\n" +
                        "and follow that wall yourself until you get there too. Keep following the line and repeating the process until the end is reached.\n"
                , "About Algorithms", JOptionPane.INFORMATION_MESSAGE));


        // add buttons to button panel
        buttons.add(genMaze);
        buttons.add(solveMaze);
        buttons.add(clrMaze);
        buttons.add(stopAll);
        buttons.setAlignmentY(JPanel.TOP_ALIGNMENT);

        // add extra buttons to second panel, which will appear under the first
        JPanel buttons2 = new JPanel();
        buttons2.add(export);
        buttons2.add(info);

        // add comboBox to new panel, with label
        JPanel solveDrop = new JPanel();
        solveDrop.add(new JLabel("Solve maze using: "));
        solveDrop.add(solveList);

        // sets certain panels to a specific size
        text.setPreferredSize(new Dimension(300, 45));
        genDrop.setPreferredSize(new Dimension(500, 30));
        solveDrop.setPreferredSize(new Dimension(500, 30));

        // add all panels that are not the main panel to an array so that the addAll subroutine can be used to add them all to the main panel
        JPanel[] subPanels = {text, genDrop, hiddenGen, maze, speed, solveDrop, hiddenSolve, buttons, buttons2};

        // adds main panel to frame
        frame.add(panel);
        // adding other panels to the main panel
        addAll(subPanels, panel);

        frame.setVisible(true);     // make the window visible
        frame.setResizable(false);  // makes the frame non-resizable
    }

    // method to hide all JPanels in an array
    private static void hideAll(JPanel[] panels) {
        for (JPanel panel : panels)
            panel.setVisible(false);
    }

    // method to set all JPanels in an array to the same dimensions
    private static void setDimensions(JPanel[] panels) {
        for (JPanel panel : panels)
            panel.setPreferredSize(new Dimension(Maze.mazeSize * 10 + 20, Maze.mazeSize * 10 + 20));
    }

    // method to add all JPanels in a list to a 'host' panel
    private static void addAll(JPanel[] panels, JPanel host) {
        for (JPanel panel : panels)
            host.add(panel);
    }

}