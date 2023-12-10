import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GUI extends Application {
    public String changed(CheckBox checkBox){
        String state = null;
        if(checkBox.isIndeterminate()) {
            state = "Undefined";
        } else if (checkBox.isSelected()) {
            state = "Checked";
        } else {
            state = "Unchecked";
        }
        System.out.println(state);
        return state;
    }

    @Override
    public void start(Stage stage) throws Exception {

        // Create Alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "This is Where you enter game.");
        alert.setTitle("User Input");
        alert.setHeaderText("Game Entry");
        // Create Game form menu item
        MenuItem gameEntry = new MenuItem("Open Game Form");
        gameEntry.setOnAction(e -> alert.show());
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
        // Form -->>>>>>>>>
        Button form = new Button("Submit");
        GridPane grid = new GridPane();
        TextField gameTitle = new TextField();
        //Console checkbox
        CheckBox console = new CheckBox();
        console.setAllowIndeterminate(false);
        // Check box event listener methods
        console.setOnAction(e -> changed(console));



        // ----

        CheckBox violence = new CheckBox();
        CheckBox nudity = new CheckBox();
        grid.addRow(0,new Label("Game Title: "), gameTitle);

        grid.addRow(1,new Label(""));
        grid.addRow(2,new Label("On Console:\t"), console);
        grid.addRow(3,new Label(""));

        grid.addRow(4, new Label("Violence\t"), violence);
        grid.addRow(5, new Label(""));
        grid.addRow(6, new Label("Nudity\t"), nudity);
        grid.addRow(9,new Label(""));
        grid.addRow(10, form);




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
