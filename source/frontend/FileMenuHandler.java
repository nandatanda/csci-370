package frontend;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;

/**
 * frontend.FileMenuHandler is an ActionListener implementation that handles actions for the file menu.
 * It listens for events triggered by menu items and performs corresponding actions.
 */
public class FileMenuHandler implements ActionListener {
    JFrame jframe;

    /**
     * Constructs a frontend.FileMenuHandler object with the specified JFrame.
     *
     * @param jf the JFrame associated with the menu handler
     */
    public FileMenuHandler(JFrame jf) {
        jframe = jf;
    }

    /**
     * Handles the actionPerformed event triggered by menu items.
     *
     * @param event the ActionEvent object representing the action performed
     */
    public void actionPerformed(ActionEvent event) {
        // Store the latest action
        String menuName = event.getActionCommand();

        // Check if that action was to click the "Open" button
        if (menuName.equals("Open"))
            openFile();
            // Otherwise check if "Quit" was pressed
        else if (menuName.equals("Quit"))
            System.exit(0);
    }

    /**
     * Opens a file selected by the user using a file chooser dialog.
     * If a file is chosen, it calls the readSource method to read and process the selected file.
     */
    private void openFile() {
        // Display a window for the user to pick which file to open
        JFileChooser chooser;
        int status;
        chooser = new JFileChooser();
        status = chooser.showOpenDialog(null);
        if (status == JFileChooser.APPROVE_OPTION)
            readSource(chooser.getSelectedFile());
        else
            JOptionPane.showMessageDialog(null, "Open File dialog canceled");
    }

    /**
     * Reads the contents of the chosen file and updates the GUI with the file's contents.
     *
     * @param chosenFile the File object representing the chosen file
     */
    private void readSource(File chosenFile) {
        // Create a file reader object to parse the file
        String chosenFileName = chosenFile.getName();

    }
}
