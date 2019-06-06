package ua.edu.npu;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Application extends javafx.application.Application {

    Model model = Model.getInstance();

    public void start(Stage primaryStage) throws Exception {
        new FXMLLoader();
        Parent root = FXMLLoader.load(getClass().getResource(model.getApplicationFileName()));
        primaryStage.setTitle(model.getTitle());
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
