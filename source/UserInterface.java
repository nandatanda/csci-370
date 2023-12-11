import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UserInterface extends JFrame {

    private final JList<String> featureList = new JList<>();
    private final JMenuBar menuBar = new JMenuBar();

    /**
     * Constructs a UserInterface object.
     * Initializes the window, menu bar, and content pane with text areas and feature list.
     */
    public UserInterface() {
        // Set default window behavior
        setTitle("ESRB Rating Classifier Tool");

        // Create menu bar entries
        setJMenuBar(menuBar);
        createFileMenu();

        // Create and set the layout of the content pane
        JPanel contentPane = (JPanel) getContentPane();
        contentPane.setLayout(new BorderLayout());

        // Initialize and add the feature list
        List<String> features = Main.trainingSet().features();
        // Add more features as needed
        featureList.setListData(features.toArray(new String[0]));

        JScrollPane featureScrollPane = new JScrollPane(featureList);
        contentPane.add(featureScrollPane, BorderLayout.CENTER);

        // Set default size and behavior
        setDefaultCloseOperation(UserInterface.EXIT_ON_CLOSE);
        setSize(1200, 675);
        setLocation(400, 200);
        setVisible(true);
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
        item = new JMenuItem("Open");
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
