package javafxapplication2;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Stephen Jones CS 246 Contributers: Cameron Thomas Menu Class will build a GUI
 * that will accept a line of text of a runnable and display that in a listview
 * it will then be able to start that runnable and stop the runnable.
 *
 * @author Stephen
 *
 */
public class Gui2 extends Application {

    GridPane grid;
    Label startDateFinder;
    Label endDateFinder;
    ComboBox<String> people;
    ComboBox<String> topical;
    TextField startDate;
    TextField endDate;
    Button find;
    ProgressBar progress;
    Scene scene;

    /**
     * Main will start the Javafx GUI builder
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Start method is where it builds the GUI
     */
    public void start(final Stage primaryStage) throws InterruptedException {
        // set the title for the stage
        primaryStage.setTitle("Provincial Mining");

        // adds a grid layout
        grid = new GridPane();
        // centers the grid
        grid.setAlignment(Pos.BOTTOM_CENTER);
        grid.setHgap(10);
        grid.setVgap(5);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Label startDateFinder = new Label("Start Date");
        grid.add(startDateFinder, 0, 3);

        endDateFinder = new Label("End Date");
        grid.add(endDateFinder, 1, 3);
        // text field will recieve input from user to load a Runnable
        people = new ComboBox();
        people.setPromptText("Person");
        people.setMinWidth(225);
        people.setEditable(true);
        grid.add(people, 0, 0);

        ObservableList<String> test = FXCollections.observableArrayList();
        for (int i = 0; i < 10; i++) {
            test.add("basketball");
            test.add("gator");
            test.add("apples");
        }
        people.setItems(test);

        topical = new ComboBox();
        topical.setPromptText("Topic");
        topical.setMinWidth(225);
        grid.add(topical, 1, 0);

        startDate = new TextField();
        startDate.setPromptText("Start Date");
        grid.add(startDate, 0, 4);

        endDate = new TextField();
        endDate.setPromptText("End Date");
        grid.add(endDate, 1, 4);
        Font font = new Font(14);

        find = new Button("Search");
        find.setFont(font);
        find.setMinSize(60, 30);
        grid.add(find, 2, 4);

        progress = new ProgressBar();
        progress.setMaxWidth(65);
        grid.add(progress, 2, 5);
        progress.setVisible(false);

        find.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                progress.setVisible(true);
            }
        });
        // set the scene and display it
        scene = new Scene(grid, 580, 280, Color.BLACK);
        scene.getStylesheets().add("fxml.css");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

    }
}
