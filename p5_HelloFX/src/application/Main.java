package application;

import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
    // store any command-line arguments that were entered.
    // NOTE: this.getParameters().getRaw() will get these also
    private List<String> args;

    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 800;
    private static final String APP_TITLE = "HelloFX";

    @Override
    public void start(Stage primaryStage) throws Exception {
        Integer[] arr = {0,1,2,3,4,5,6,7,8,9};
        
        // save args example
        args = this.getParameters().getRaw();

        // Main layout is Border Pane example (top,left,center,right,bottom)
        BorderPane root = new BorderPane();
        VBox vbox = new VBox();
        //root.setPadding(new Insets(10, 10, 10, 10));

        
        //Top pane of the GUI
        Canvas canvas = new Canvas(1200, 100);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        gc.setFill(Color.BLUE);
        gc.setFont(new Font(30));
        gc.fillText("CS400 My First JavaFX Program", 400, 50);
        
        vbox.getChildren().add(canvas);
        // Add the vertical box to the center of the root pane
        root.setTop(vbox);
        
        
        
        //Left pane of the GUI    
        canvas = new Canvas(400, 600);
        
        // Weekdays 
        String mood[] = { "Sad", "Angry", "Happy", "Excited", "Bored", "Want the coronavirus quarentine to end" }; 
        Label label = new Label("How we feeling today?");
        
        // Create a combo box 
        ComboBox<String> combo_box = new ComboBox<String>(FXCollections.observableArrayList(mood)); 
        // Create action event 
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) { 
                label.setText(combo_box.getValue() + "? Yeah, me too"); 
            } 
        }; 
        // Set on action 
        combo_box.setOnAction(event);
      
        VBox vbox2 = new VBox();
        vbox2.getChildren().addAll(label, combo_box);
        root.setLeft(vbox2);
        
        
        
        //Center GUI frame
        //load the image
        Text txt = new Text("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\nbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb\n"
            + "ccccccccccccccccccccccccccccccccccccccccc\ndddddddddddddddddddddddddddddddddddddd\n");
        root.setCenter(txt);
        
        
        
        //Right GUI frame
        TextField usr = new TextField();
        usr.setPromptText("ID");
        usr.setMaxWidth(50);
        Label label3 = new Label("Farm: ");
        Label label4 = new Label("Showing data for farm N/A");
        EventHandler<ActionEvent> event3 = new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) { 
                label4.setText("Showing data for farm " + usr.getText());
            } 
        }; 
        usr.setOnAction(event3);//Hopefully that event is hitting enter
        VBox vb = new VBox();
        vb.getChildren().addAll(label3, usr, label4);
        root.setRight(vb);
        
        
        //Bottom GUI frame
        Button button = new Button("Done");
        Label label2 = new Label("Are you?");
        // add an EventHandler to the Button
        EventHandler<ActionEvent> event2 = new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) { 
                txt.setText("HOLY ASS HAT");
            } 
        }; 
        button.setOnAction(event2);
        VBox vbox3 = new VBox();
        vbox3.getChildren().addAll(button, label2);
        root.setBottom(vbox3);
        
        
        
        
        //Final set up of GUI window
        Scene mainScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        mainScene.setFill(Color.WHITE);
        // Add the stuff and set the primary stage
        primaryStage.setTitle(APP_TITLE);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }
    


    /**
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}

