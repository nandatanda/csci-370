import javax.swing.*;
import java.awt.*;
import java.util.List;

public class UserInterface extends JFrame {

    private final JList<String> featureList = new JList<>();
    private final JTextArea inputTextArea = new JTextArea();
    private final JTextArea resultTextArea = new JTextArea();
    private final JButton submitButton = new JButton("Submit");
    private final JMenuBar menuBar = new JMenuBar();

    /**
     * Constructs a UserInterface object.
     * Initializes the window, menu bar, and content pane with text areas and feature list.
     */
    public UserInterface() {
        // Set default window behavior
        setTitle("ESRB Rating Classifier");

        // Create menu bar entries
        setJMenuBar(menuBar);
        createFileMenu();

        // Create and set the layout of the content pane
        JPanel contentPane = (JPanel) getContentPane();
        contentPane.setLayout(new BorderLayout());

        // Initialize the feature list on the left
        List<String> features = Main.trainingSet().features();
        featureList.setListData(features.toArray(new String[0]));

        JScrollPane featureScrollPane = new JScrollPane(featureList);
        featureScrollPane.setPreferredSize(new Dimension(300, 300)); // Set the preferred size

        // Create a split pane with the left side for feature list and the right side for input and results
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, featureScrollPane, createInputResultPanel());
        splitPane.setDividerLocation(300); // Set the initial divider location

        // Add the split pane to the content pane
        contentPane.add(splitPane, BorderLayout.CENTER);

        // Set default size and behavior
        setDefaultCloseOperation(UserInterface.EXIT_ON_CLOSE);
        setSize(1200, 675);
        setLocation(400, 200);
        setVisible(true);
    }

    /**
     * Creates a panel containing input and result text areas, instructions, and a submit button.
     *
     * @return the panel with input and result text areas, instructions, and a submit button
     */
    private JPanel createInputResultPanel() {
        JPanel inputResultPanel = new JPanel(new BorderLayout());

        // Instructions and submit button on the top-right
        JPanel instructionsPanel = new JPanel(new BorderLayout());
        JTextArea instructionsLabel = new JTextArea("Instructions:\n" +
                "\nClick on each content descriptor your game contains." +
                "\nWhen you have selected all the relevant features, click 'Submit.'\n" +
                "\nTo train using your own dataset, click 'File' -> 'Open' and choose a csv file." +
                "\nA window will appear to help you configure the program for your data.\n");
        instructionsLabel.setFont(new Font(instructionsLabel.getFont().getName(), Font.PLAIN, 16)); // Set the font size
        instructionsLabel.setLineWrap(true); // Enable line wrapping
        instructionsLabel.setWrapStyleWord(true); // Wrap at word boundaries
        instructionsLabel.setEditable(false); // Make it non-editable
        instructionsPanel.add(instructionsLabel, BorderLayout.CENTER);
        instructionsPanel.add(submitButton, BorderLayout.SOUTH);

        // Input text area on the top-left
        JScrollPane inputScrollPane = new JScrollPane(inputTextArea);
        inputScrollPane.setFont(new Font(inputScrollPane.getFont().getName(), Font.PLAIN, 16));
        inputResultPanel.add(instructionsPanel, BorderLayout.NORTH);
        inputResultPanel.add(inputScrollPane, BorderLayout.WEST);

        // Result text area on the bottom
        JScrollPane resultScrollPane = new JScrollPane(resultTextArea);
        inputResultPanel.add(resultScrollPane, BorderLayout.CENTER);

        // Add action listener to the submit button
        submitButton.addActionListener(e -> handleSubmission());

        return inputResultPanel;
    }

    /**
     * Handles the submission when the user clicks the submit button.
     */
    private void handleSubmission() {
        List<String> selectedFeatures = getSelectedFeatures();
        // Perform the prediction and update the resultTextArea accordingly
        // ...
    }

    /**
     * Gets the selected features from the feature list.
     *
     * @return a list of selected features
     */
    public List<String> getSelectedFeatures() {
        return featureList.getSelectedValuesList();
    }

    /**
     * Creates the file menu with submenu entries for file management.
     * Associates menu items with the FileMenuHandler.
     */
    private void createFileMenu() {
        // Create a submenu for file management
        JMenuItem item;
        JMenu fileMenu = new JMenu("File");
        FileMenuHandler fmh = new FileMenuHandler(this);

        // Add a submenu entry for opening files
        item = new JMenuItem("Open...");
        item.addActionListener(fmh);
        fileMenu.add(item);

        // Visually separate the submenu entries
        fileMenu.addSeparator();

        // Add a submenu entry for quitting the program
        item = new JMenuItem("Quit");
        item.addActionListener(fmh);
        fileMenu.add(item);

        // Add the submenu to the frame's menu bar
        menuBar.add(fileMenu);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(UserInterface::new);
    }
}
