package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../View_Controller/LoginScreen.fxml"));
        //primaryStage.setScene(new Scene(root, 626, 417));  //This seems to set the size of my window, ignores prefheight/prefwidth
        primaryStage.setScene(new Scene(root));  //Leaving h,w params unset allows them to be set in fxml by scenbuilder
        primaryStage.show();
    }

    public static void main(String[] args) throws Exception {
        launch(args);  // Launch GUI.
    }

}
