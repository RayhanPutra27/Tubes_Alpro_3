package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage mainStg;
    public static void main(String[] args) {
        launch(Main.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("MainView.fxml"));
        this.mainStg = primaryStage;
        Scene scene = new Scene(parent);
        primaryStage.setTitle("Hello World!!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
