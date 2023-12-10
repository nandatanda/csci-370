import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class GUI extends Application {

    FileChooser userFileUpload = new FileChooser();

    private String changed(CheckBox checkBox){
        String state;
        if(checkBox.isSelected()) {
            state = "Checked";
        } else
            state = "Unchecked";

        System.out.println(state);
        return state;
    }

    private void openFile(Stage stage) {
        userFileUpload.setTitle("Upload Data");
        userFileUpload.showOpenDialog(stage);
    }

    @Override
    public void start(Stage stage) throws Exception {

        // Create Alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "This is Where you enter game.");
        alert.setTitle("User Input");
        alert.setHeaderText("Game Entry");
        //Menu



        MenuItem gameEntry = new MenuItem("Upload Data");
        gameEntry.setOnAction(e -> openFile(stage));
        MenuItem about = new MenuItem("The Crew");
        MenuItem contact = new MenuItem("Contact");
        MenuItem rate = new MenuItem("Rate Prediction");

        Menu menu = new Menu("File");
        menu.getItems().add(gameEntry);
        Menu menu1 = new Menu("About");
        menu1.getItems().add(about);
        Menu menu2 = new Menu("Contact");
        menu2.getItems().add(contact);
        Menu menu3 = new Menu("Rate");
        menu3.getItems().add(rate);
        //Game Entry Form

        GridPane grid = new GridPane();

        //Need to get game title into string
        TextField gameTitle = new TextField();
        CheckBox console = new CheckBox();
        console.setAllowIndeterminate(false); //
        // Check box event listener methods
        console.setOnAction(e -> changed(console));



        CheckBox alcoholReference = new CheckBox();
        CheckBox animatedBlood = new CheckBox();
        CheckBox blood = new CheckBox();
        CheckBox bloodAndGore = new CheckBox();
        CheckBox cartoonViolence = new CheckBox();
        CheckBox crudeHumor = new CheckBox();
        CheckBox drugReference = new CheckBox();
        CheckBox fantasyViolence = new CheckBox();
        CheckBox intenseViolence = new CheckBox();
        CheckBox language = new CheckBox();
        CheckBox lyrics = new CheckBox();
        CheckBox matureHumor = new CheckBox();
        CheckBox mildBlood = new CheckBox();
        CheckBox mildCartoonViolence = new CheckBox();
        CheckBox mildFantasyViolence = new CheckBox();
        CheckBox mildLanguage = new CheckBox();
        CheckBox mildLyrics = new CheckBox();
        CheckBox mildSuggestiveThemes = new CheckBox();
        CheckBox mildViolence = new CheckBox();
        CheckBox noDescriptors = new CheckBox();
        CheckBox nudity = new CheckBox();
        CheckBox partialNudity = new CheckBox();
        CheckBox sexualContent = new CheckBox();
        CheckBox sexualThemes = new CheckBox();
        CheckBox simulatedGambling = new CheckBox();
        CheckBox strongLanguage = new CheckBox();
        CheckBox strongSexualContent = new CheckBox();
        CheckBox suggestiveThemes = new CheckBox();
        CheckBox useOfAlcohol = new CheckBox();
        CheckBox useOfDrugsAndAlcohol = new CheckBox();
        CheckBox violence = new CheckBox();

        Button form = new Button("Submit");

        grid.addRow(0,new Label("Game Title: "), gameTitle);
        grid.addRow(1,new Label(""));
        grid.addRow(2,new Label("On Console:\t"), console);
        grid.addRow(3,new Label(""));
        grid.addRow(4,new Label("Alcohol Reference:\t"), alcoholReference);
        grid.addRow(5,new Label(""));
        grid.addRow(6,new Label("Animated Blood:\t"), animatedBlood);
        grid.addRow(7,new Label(""));
        grid.addRow(8,new Label("Blood:\t"), blood);
        grid.addRow(9,new Label(""));
        grid.addRow(10,new Label("Blood and Gore:\t"), bloodAndGore);
        grid.addRow(11,new Label(""));
        grid.addRow(12,new Label("Cartoon Violence:\t"), cartoonViolence);
        grid.addRow(13,new Label(""));
        grid.addRow(14,new Label("Crude Humor:\t"), crudeHumor);
        grid.addRow(15,new Label(""));
        grid.addRow(16,new Label("Drug Reference:\t"), drugReference);
        grid.addRow(17,new Label(""));
        grid.addRow(18,new Label("Fantasy Violence:\t"), fantasyViolence);
        grid.addRow(19,new Label(""));
        grid.addRow(20,new Label("Intense Violence:\t"), intenseViolence);
        grid.addRow(21,new Label(""));
        grid.addRow(22,new Label("Language:\t"), language);
        grid.addRow(23,new Label(""));
        grid.addRow(24,new Label("Lyrics:\t"), lyrics);
        grid.addRow(25,new Label(""));
        grid.addRow(26,new Label("Mature Humor:\t"), matureHumor);
        grid.addRow(27,new Label(""));
        grid.addRow(28,new Label("Mild Blood:\t"), mildBlood);
        grid.addRow(29,new Label(""));
        grid.addRow(30,new Label("Mild Cartoon Violence:\t"), mildCartoonViolence);
        grid.addRow(31,new Label(""));
        grid.addRow(32,new Label("Mild Fantasy Violence:\t"), mildFantasyViolence);
        grid.addRow(33,new Label(""));
        grid.addRow(34,new Label("Mild Language:\t"), mildLanguage);
        grid.addRow(35,new Label(""));
        grid.addRow(36,new Label("Mild Lyrics:\t"), mildLyrics);
        grid.addRow(37,new Label(""));
        grid.addRow(38,new Label("Mild Suggestive Themes:\t"), mildSuggestiveThemes);
        grid.addRow(39,new Label(""));
        grid.addRow(40,new Label("Mild Violence:\t"), mildViolence);
        grid.addRow(41,new Label(""));
        grid.addRow(42,new Label("No Descriptors:\t"), noDescriptors);
        grid.addRow(43,new Label(""));
        grid.addRow(44,new Label("Nudity:\t"), nudity);
        grid.addRow(45,new Label(""));
        grid.addRow(46,new Label("Partial Nudity:\t"), partialNudity);
        grid.addRow(47,new Label(""));
        grid.addRow(48,new Label("Sexual Content:\t"), sexualContent);
        grid.addRow(49,new Label(""));
        grid.addRow(50,new Label("Sexual Themes:\t"), sexualThemes);
        grid.addRow(51,new Label(""));
        grid.addRow(52,new Label("Simulated Gambling:\t"), simulatedGambling);
        grid.addRow(53,new Label(""));
        grid.addRow(54,new Label("Strong Language:\t"), strongLanguage);
        grid.addRow(55,new Label(""));
        grid.addRow(56,new Label("Strong Sexual Content:\t"), strongSexualContent);
        grid.addRow(57,new Label(""));
        grid.addRow(58,new Label("Suggestive Themes:\t"), suggestiveThemes);
        grid.addRow(59,new Label(""));
        grid.addRow(60,new Label("Use of Alcohol:\t"), useOfAlcohol);
        grid.addRow(61,new Label(""));
        grid.addRow(62,new Label("Use of Drugs and Alcohol:\t"), useOfDrugsAndAlcohol);
        grid.addRow(63,new Label(""));
        grid.addRow(64, new Label("Violence\t"), violence);
        grid.addRow(65,new Label(""));
        grid.addRow(66, form); // Submit




        // Form -->>>>>>>>>

        MenuBar menuBar = new MenuBar(menu, menu1, menu2, menu3);


        Label nameLb1 = new Label("Enter Your Game");
        TextField nameFld = new TextField();
        nameFld.setMaxWidth(100);

        Label msg = new Label();
        msg.setStyle("-fx-text-fill: blue");

        Button sayHelloBtn = new Button("Say hello");
        Button exitBtn = new Button("Exit");

        sayHelloBtn.setOnAction(e -> {
            String game = nameFld.getText();
            if(!game.trim().isEmpty()) {
                msg.setText("The game you entered is: " + game);
            } else {
                msg.setText("No game entered");
            }
        });

        exitBtn.setOnAction(e -> Platform.exit());

        VBox root = new VBox();
        root.getChildren().addAll(menuBar, nameLb1, nameFld, msg, sayHelloBtn, exitBtn, grid, form);

        Scene scene = new Scene(root, 500, 250, Color.BLUE);
        stage.setScene(scene);
        stage.setTitle("Next Gen JFX");
        stage.setWidth(750);
        stage.setHeight(450);
        stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("enter"));
        stage.show();

    }
}
