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
import javax.swing.filechooser.FileNameExtensionFilter;

public class Main {

    static Mark[][] currentMaze;

    public static void main(String[] args) {
        buildGUI(); // creates GUI
    }

    private static void buildGUI() {
        // creates JFrame
        JFrame frame = new JFrame("MazeTool");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 760);
        frame.setLocationRelativeTo(null);
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
        version.setText("v. 0.4.3");
        text.add(title);
        text.add(version);

        // create panel for maze-generation algorithm choice, with comboBox to choose which algorithm to use
        JPanel genDrop = new JPanel();
        String[] genAlgorithms = GenMaze.genAlgorithms; // gets list of algorithms from GenMaze class
        JComboBox<String> genList = new JComboBox<>(genAlgorithms);
        genDrop.add(new JLabel("Generate maze using: "));
        genDrop.add(genList);

        // create panel for choosing to show/hide maze generation
        JPanel hiddenGen = new JPanel();
        JRadioButton showGen = new JRadioButton("Show generation", true); // create button to show generation, default
        JRadioButton hideGen = new JRadioButton("Hide generation");
        ButtonGroup hidden = new ButtonGroup();
        hidden.add(showGen); // adds buttons to button group so only one can be selected at any one time
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
        JRadioButton slow = new JRadioButton("Slow");
        JRadioButton normal = new JRadioButton("Normal", true);
        JRadioButton fast = new JRadioButton("Fast");
        ButtonGroup speeds = new ButtonGroup();
        speeds.add(slow);
        speeds.add(normal);
        speeds.add(fast);
        speed.add(slow);
        speed.add(normal);
        speed.add(fast);

        // create main panel to hold maze
        JPanel maze = new JPanel();
        JPanel emptyMaze = new DepthFirst(); // create panel for startup, where no maze is displayed
        emptyMaze.setPreferredSize(new Dimension(Maze.mazeSize * 10 + 20, Maze.mazeSize * 10 + 20)); // force size of panel
        maze.add(emptyMaze);
        DepthFirst df = new DepthFirst(); // create panel for depth-first
        df.setPreferredSize(new Dimension(Maze.mazeSize * 10 + 20, Maze.mazeSize * 10 + 20));
        df.setVisible(false);
        maze.add(df);
        HuntAndKill hk = new HuntAndKill(); // create panel for hunt-and-kill
        hk.setPreferredSize(new Dimension(Maze.mazeSize * 10 + 20, Maze.mazeSize * 10 + 20));
        hk.setVisible(false);
        maze.add(hk);
        RandomisedKruskal rk = new RandomisedKruskal(); // create panel for randomised kruskal's algorithm
        rk.setPreferredSize(new Dimension(Maze.mazeSize * 10 + 20, Maze.mazeSize * 10 + 20));
        rk.setVisible(false);
        maze.add(rk);

        DepthSolve ds = new DepthSolve(); // create panel for depth-first solving
        ds.setPreferredSize(new Dimension(Maze.mazeSize * 10 + 20, Maze.mazeSize * 10 + 20));
        ds.setVisible(false);
        maze.add(ds);
        DeadEnd de = new DeadEnd(); // create panel for dead-end filling
        de.setPreferredSize(new Dimension(Maze.mazeSize * 10 + 20, Maze.mazeSize * 10 + 20));
        de.setVisible(false);
        maze.add(de);
        ChainSolve cs = new ChainSolve();
        cs.setPreferredSize(new Dimension(Maze.mazeSize * 10 + 20, Maze.mazeSize * 10 + 20));
        cs.setVisible(false);
        maze.add(cs);

        // create a panel for buttons so they appear side by side
        JPanel buttons = new JPanel();
        JButton genMaze = new JButton("Generate new maze");
        genMaze.setAlignmentX(JButton.CENTER_ALIGNMENT);
        genMaze.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!Maze.isActive()) { // check that no mazes are being generated
                    if (slow.isSelected())
                        GenMaze.speed = 200;
                    else if (fast.isSelected())
                        GenMaze.speed = 50;
                    else
                        GenMaze.speed = 100;
                    Maze.hidden = !showGen.isSelected(); // set 'hidden' to either show or hide generation
                    emptyMaze.setVisible(false); // hide empty maze panel
                    df.setVisible(false); // make sure all maze panels are hidden
                    hk.setVisible(false);
                    rk.setVisible(false);
                    ds.setVisible(false);
                    de.setVisible(false);
                    cs.setVisible(false);
                    if (Objects.equals(genList.getSelectedItem(), genAlgorithms[0])) { // if depth-first is selected
                        df.rerun(); // create maze
                        df.setVisible(true); // show maze panel
                    } else if (Objects.equals(genList.getSelectedItem(), genAlgorithms[1])) { // if hunt-and-kill is selected
                        hk.rerun(); // create maze
                        hk.setVisible(true); // show maze panel
                    } else if (Objects.equals(genList.getSelectedItem(), genAlgorithms[2])) {
                        rk.rerun();
                        rk.setVisible(true);
                    }
                }
            }
        });

        // creates comboBox for choosing maze-solving algorithms
        String[] solveAlgorithms = SolveMaze.solveAlgorithms;
        JComboBox<String> solveList = new JComboBox<>(solveAlgorithms);

        // creates button for starting solving
        JButton solveMaze = new JButton("Solve maze");
        solveMaze.setAlignmentX(JButton.CENTER_ALIGNMENT);
        solveMaze.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!Maze.isActive() && Maze.completedGen) { // check that no mazes are being generated
                    if (slow.isSelected())
                        SolveMaze.speed = 100;
                    else if (fast.isSelected())
                        SolveMaze.speed = 25;
                    else
                        SolveMaze.speed = 50;
                    Maze.hidden = !showSolve.isSelected();
                    df.setVisible(false); // make sure all maze panels are hidden
                    hk.setVisible(false);
                    rk.setVisible(false);
                    ds.setVisible(false);
                    de.setVisible(false);
                    cs.setVisible(false);
                    if (Objects.equals(solveList.getSelectedItem(), solveAlgorithms[0])) {
                        ds.rerun();
                        ds.setVisible(true);
                    } else if (Objects.equals(solveList.getSelectedItem(), solveAlgorithms[1])) {
                        de.rerun();
                        de.setVisible(true);
                    } else if (Objects.equals(solveList.getSelectedItem(), solveAlgorithms[2])) {
                        cs.rerun();
                        cs.setVisible(true);
                    }
                }
            }
        });

        // creates button to clear current solution
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
                    ds.setVisible(true); // shows default panel for maze-solving
                }
            }
        });

        // added button to stop all current generation/solving
        JButton stopAll = new JButton("Stop all");
        stopAll.setAlignmentX(JButton.CENTER_ALIGNMENT);
        stopAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Maze.stopAll();
            }
        });

        // added button to export current maze to image
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
                fileChooser.setFileFilter(new FileNameExtensionFilter("PNG, GIF, TIFF", "png", "gif", "tiff"));
                fileChooser.setDialogTitle("Save maze");
                int userSelection = fileChooser.showSaveDialog(frame);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();
                    String file = fileToSave.getAbsolutePath(), type;
                    if (fileToSave.toString().endsWith(".png"))
                        type = "png";
                    else if (fileToSave.toString().endsWith(".gif"))
                        type = "gif";
                    else if (fileToSave.toString().endsWith(".tiff"))
                        type = "tiff";
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

        // add buttons to button panel
        buttons.add(genMaze);
        buttons.add(solveMaze);
        buttons.add(clrMaze);
        buttons.add(stopAll);
        buttons.setAlignmentY(JPanel.TOP_ALIGNMENT);

        // add extra buttons to second panel, which will appear under the first
        JPanel buttons2 = new JPanel();
        buttons2.add(export);

        // add comboBox to new panel, with label
        JPanel solveDrop = new JPanel();
        solveDrop.add(new JLabel("Solve maze using: "));
        solveDrop.add(solveList);

        text.setPreferredSize(new Dimension(300, 45));
        genDrop.setPreferredSize(new Dimension(500, 30));
        solveDrop.setPreferredSize(new Dimension(500, 30));

        frame.add(panel); // adds main panel to frame
        panel.add(text); // adding things to the panel
        panel.add(genDrop);
        panel.add(hiddenGen);
        panel.add(maze);
        panel.add(speed);
        panel.add(solveDrop);
        panel.add(hiddenSolve);
        panel.add(buttons);
        panel.add(buttons2);

        frame.setVisible(true); // make the window visible
        frame.setResizable(false); // makes the frame non-resizable
    }

}