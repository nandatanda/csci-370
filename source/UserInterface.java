import javax.swing.*;
import java.awt.*;

public class UserInterface extends JFrame {

    private final JTextArea romanTextArea = new JTextArea();
    private final JTextArea arabicTextArea = new JTextArea();
    private final JMenuBar menuBar = new JMenuBar();

    /**
     * Constructs a UserInterface object.
     * Initializes the window, menu bar, and content pane with text areas.
     */
    public UserInterface() {
        // Set default window behavior
        setTitle("ESRB Rating Classifier Tool");

        // Create menu bar entries
        setJMenuBar(menuBar);
        createFileMenu();

        // Create and set the layout of the content pane
        JPanel contentPane = (JPanel) getContentPane();
        contentPane.setLayout(new GridLayout(1, 2));

        // Add text areas to the content pane
        contentPane.add(romanTextArea);
        contentPane.add(arabicTextArea);

        // Set default size and behavior
        setDefaultCloseOperation(UserInterface.EXIT_ON_CLOSE);
        setSize(800, 450);
        setLocation(400, 200);
        setVisible(true);
    }

    /**
     * Sets the text of the Roman numeral text area.
     *
     * @param s the text to be displayed in the Roman numeral text area
     */
    public void setRomanText(String s) {
        romanTextArea.setText(s);
    }

    /**
     * Sets the text of the Arabic numeral text area.
     *
     * @param s the text to be displayed in the Arabic numeral text area
     */
    public void setArabicText(String s) {
        arabicTextArea.setText(s);
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
}