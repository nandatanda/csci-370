import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public abstract class GUI extends Application implements Serializable {

    FileChooser userFileUpload = new FileChooser();
    FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
    FeatureGrid checkBoxFeatures = new FeatureGrid();

    private String changed(CheckBox checkBox){
        String state;
        if(checkBox.isSelected()) {
            state = "Checked";
        } else
            state = "Unchecked";

        System.out.println(state);
        return state;
    }

    private void openFile(Stage stage) throws IOException {
        Alert success = new Alert(Alert.AlertType.INFORMATION, "User Data");
        success.setTitle("User File");
        userFileUpload.getExtensionFilters().add(extensionFilter);
        userFileUpload.setTitle("Upload Data");
        File file = userFileUpload.showOpenDialog(stage);

        if(file != null) {
            success.setHeaderText("File Successfully Loaded");
            success.show();
            saveFile(file);
        } else {
            success.setHeaderText("File Upload Unsuccessful");
            success.show();
        }

    }

    private void saveFile(File file) throws IOException {
       try {
           Path source = file.toPath();
           Path destination = Path.of("data/userdata.csv");
           Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
       } catch (IOException ex) {
           throw new RuntimeException(ex);
       }

    }

    @Override
    public void start(Stage stage) {
        VBox root = new VBox();
        Scene scene = new Scene(root, 1400, 1250);
        Button form = new Button("Submit");
        GridPane grid = new GridPane();
        //Need to get game title into string
        TextField gameTitle = new TextField();
        CheckBox console = new CheckBox();

        //Menu
        MenuItem gameEntry = new MenuItem("Upload Data");
        MenuItem about = new MenuItem("The Crew");
        MenuItem contact = new MenuItem("Contact");
        MenuItem rate = new MenuItem("Rate Prediction");
        Menu menu = new Menu("File");
        Menu menu1 = new Menu("About");
        Menu menu2 = new Menu("Contact");
        Menu menu3 = new Menu("Rate");
        menu.getItems().add(gameEntry);
        menu1.getItems().add(about);
        menu2.getItems().add(contact);
        menu3.getItems().add(rate);
        MenuBar menuBar = new MenuBar(menu, menu1, menu2, menu3);
        gameEntry.setOnAction(e -> {
            try {
                openFile(stage);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        // Menu End

        //Game Entry Form
        console.setAllowIndeterminate(false); // Only Checked and Unchecked
        // Check box event listener methods
        console.setOnAction(e -> changed(console));

        grid.addRow(0,new Label("Game Title: "), gameTitle);
        grid.addRow(1,new Label(""));
        grid.addRow(2,new Label("On Console:\t"), console);
        grid.addRow(3,new Label(""));
        grid.addRow(4,new Label("Alcohol Reference:\t"), checkBoxFeatures.alcoholReference);
        grid.addRow(5,new Label(""));
        grid.addRow(6,new Label("Animated Blood:\t"), checkBoxFeatures.animatedBlood);
        grid.addRow(7,new Label(""));
        grid.addRow(8,new Label("Blood:\t"), checkBoxFeatures.blood);
        grid.addRow(9,new Label(""));
        grid.addRow(10,new Label("Blood and Gore:\t"), checkBoxFeatures.bloodAndGore);
        grid.addRow(11,new Label(""));
        grid.addRow(12,new Label("Cartoon Violence:\t"), checkBoxFeatures.cartoonViolence);
        grid.addRow(13,new Label(""));
        grid.addRow(14,new Label("Crude Humor:\t"), checkBoxFeatures.crudeHumor);
        grid.addRow(15,new Label(""));
        grid.addRow(16,new Label("Drug Reference:\t"), checkBoxFeatures.drugReference);
        grid.addRow(17,new Label(""));
        grid.addRow(18,new Label("Fantasy Violence:\t"), checkBoxFeatures.fantasyViolence);
        grid.addRow(19,new Label(""));
        grid.addRow(20,new Label("Intense Violence:\t"), checkBoxFeatures.intenseViolence);
        grid.addRow(21,new Label(""));
        grid.addRow(22,new Label("Language:\t"), checkBoxFeatures.language);
        grid.addRow(23,new Label(""));
        grid.addRow(24,new Label("Lyrics:\t"), checkBoxFeatures.lyrics);
        grid.addRow(25,new Label(""));
        grid.addRow(26,new Label("Mature Humor:\t"), checkBoxFeatures.matureHumor);
        grid.addRow(27,new Label(""));
        grid.addRow(28,new Label("Mild Blood:\t"), checkBoxFeatures.mildBlood);
        grid.addRow(29,new Label(""));
        grid.addRow(30,new Label("Mild Cartoon Violence:\t"), checkBoxFeatures.mildCartoonViolence);
        grid.addRow(31,new Label(""));
        grid.addRow(32,new Label("Mild Fantasy Violence:\t"), checkBoxFeatures.mildFantasyViolence);
        grid.addRow(33,new Label(""));
        grid.addRow(34,new Label("Mild Language:\t"), checkBoxFeatures.mildLanguage);
        grid.addRow(35,new Label(""));
        grid.addRow(36,new Label("Mild Lyrics:\t"), checkBoxFeatures.mildLyrics);
        grid.addRow(37,new Label(""));
        grid.addRow(38,new Label("Mild Suggestive Themes:\t"), checkBoxFeatures.mildSuggestiveThemes);
        grid.addRow(39,new Label(""));
        grid.addRow(40,new Label("Mild Violence:\t"), checkBoxFeatures.mildViolence);
        grid.addRow(41,new Label(""));
        grid.addRow(42,new Label("No Descriptors:\t"), checkBoxFeatures.noDescriptors);
        grid.addRow(43,new Label(""));
        grid.addRow(44,new Label("Nudity:\t"), checkBoxFeatures.nudity);
        grid.addRow(45,new Label(""));
        grid.addRow(46,new Label("Partial Nudity:\t"), checkBoxFeatures.partialNudity);
        grid.addRow(47,new Label(""));
        grid.addRow(48,new Label("Sexual Content:\t"), checkBoxFeatures.sexualContent);
        grid.addRow(49,new Label(""));
        grid.addRow(50,new Label("Sexual Themes:\t"), checkBoxFeatures.sexualThemes);
        grid.addRow(51,new Label(""));
        grid.addRow(52,new Label("Simulated Gambling:\t"), checkBoxFeatures.simulatedGambling);
        grid.addRow(53,new Label(""));
        grid.addRow(54,new Label("Strong Language:\t"), checkBoxFeatures.strongLanguage);
        grid.addRow(55,new Label(""));
        grid.addRow(56,new Label("Strong Sexual Content:\t"), checkBoxFeatures.strongSexualContent);
        grid.addRow(57,new Label(""));
        grid.addRow(58,new Label("Suggestive Themes:\t"), checkBoxFeatures.suggestiveThemes);
        grid.addRow(59,new Label(""));
        grid.addRow(60,new Label("Use of Alcohol:\t"), checkBoxFeatures.useOfAlcohol);
        grid.addRow(61,new Label(""));
        grid.addRow(62,new Label("Use of Drugs and Alcohol:\t"), checkBoxFeatures.useOfDrugsAndAlcohol);
        grid.addRow(63,new Label(""));
        grid.addRow(64,new Label("Violence\t"), checkBoxFeatures.violence);
        grid.addRow(65,new Label(""));
        grid.addRow(66, form); // Submit

        //Add to the Scene Graph
        root.getChildren().addAll(menuBar, grid, form);
        stage.setScene(scene);
        stage.setTitle("CSCI 370 - Team 6");
        stage.setWidth(1400);
        stage.setHeight(1250);
        stage.show();

    }
}
