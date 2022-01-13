package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    boolean bolInsert = false, bolView = false, bolExit = false, bolUpdel = false;
    static int lvl;

    ToggleGroup tg = new ToggleGroup();
    VBox pane1;
    VBox pane2;
    VBox pane3;

    @FXML
    private Button btn_Exit;

    @FXML
    private Button btn_Insert;

    @FXML
    private Button btn_Updel;

    @FXML
    private Button btn_View;

    @FXML
    private Label lbl_Title;

    @FXML
    private VBox vBox_ParentPane;

    public void buttonPress() {
        btn_Exit.setStyle("-fx-background-color: #ffffff00; -fx-text-fill: #107A92; -fx-font-weight: bold; -fx-font-size: 14; -fx-background-radius: 0");
        if (bolInsert == true) {
            System.out.println("New Pressed");
            if (bolView == false || bolExit == false || bolUpdel == false) {
                btn_Exit.setStyle("-fx-background-color: #ffffff00; -fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-font-size: 14; -fx-background-radius: 0; -fx-border-width: 1; -fx-border-color: #FFFFFF");
                btn_Updel.setStyle("-fx-background-color: #ffffff00; -fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-font-size: 14; -fx-background-radius: 0; -fx-border-width: 1; -fx-border-color: #FFFFFF");
                btn_View.setStyle("-fx-background-color: #ffffff00; -fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-font-size: 14; -fx-background-radius: 0; -fx-border-width: 1; -fx-border-color: #FFFFFF");
            }
            btn_Insert.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #107A92; -fx-font-weight: bold; -fx-font-size: 14; -fx-background-radius: 0");
            bolInsert = false;
        }
        if (bolView == true) {
            System.out.println("Show Pressed");
            if (bolUpdel == false || bolExit == false || bolInsert == false) {
                btn_Insert.setStyle("-fx-background-color: #ffffff00; -fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-font-size: 14; -fx-background-radius: 0; -fx-border-width: 1; -fx-border-color: #FFFFFF");
                btn_Updel.setStyle("-fx-background-color: #ffffff00; -fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-font-size: 14; -fx-background-radius: 0; -fx-border-width: 1; -fx-border-color: #FFFFFF");
                btn_Exit.setStyle("-fx-background-color: #ffffff00; -fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-font-size: 14; -fx-background-radius: 0; -fx-border-width: 1; -fx-border-color: #FFFFFF");
            }
            btn_View.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #107A92; -fx-font-weight: bold; -fx-font-size: 14; -fx-background-radius: 0");
            bolView = false;
        }
        if (bolUpdel == true) {
            if (bolView == false || bolExit == false || bolInsert == false) {
                btn_View.setStyle("-fx-background-color: #ffffff00; -fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-font-size: 14; -fx-background-radius: 0; -fx-border-width: 1; -fx-border-color: #FFFFFF");
                btn_Exit.setStyle("-fx-background-color: #ffffff00; -fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-font-size: 14; -fx-background-radius: 0; -fx-border-width: 1; -fx-border-color: #FFFFFF");
                btn_Insert.setStyle("-fx-background-color: #ffffff00; -fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-font-size: 14; -fx-background-radius: 0; -fx-border-width: 1; -fx-border-color: #FFFFFF");
            }
            btn_Updel.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #107A92; -fx-font-weight: bold; -fx-font-size: 14; -fx-background-radius: 0");
            bolUpdel = false;
        }
        if (bolExit == true) {
            if (bolView == false || bolUpdel == false || bolInsert == false) {
                btn_View.setStyle("-fx-background-color: #ffffff00; -fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-font-size: 14; -fx-background-radius: 0; -fx-border-width: 1; -fx-border-color: #FFFFFF");
                btn_Insert.setStyle("-fx-background-color: #ffffff00; -fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-font-size: 14; -fx-background-radius: 0; -fx-border-width: 1; -fx-border-color: #FFFFFF");
                btn_Updel.setStyle("-fx-background-color: #ffffff00; -fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-font-size: 14; -fx-background-radius: 0; -fx-border-width: 1; -fx-border-color: #FFFFFF");
            }
            btn_Exit.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #107A92; -fx-font-weight: bold; -fx-font-size: 14; -fx-background-radius: 0");
            bolExit = false;
        }
    }

    @FXML
    private void btnExitPress(ActionEvent event) {
        bolExit = true;
        System.exit(0);
    }

    @FXML
    private void btnInsertPress(ActionEvent event) throws IOException {
        bolInsert = true;
        vBox_ParentPane.getChildren().clear();
        buttonPress();
        pane1 = FXMLLoader.load(getClass().getResource("/view/InsertView.fxml"));
        vBox_ParentPane.getChildren().addAll(pane1);
        vBox_ParentPane.setAlignment(Pos.CENTER);
    }

    @FXML
    private void btnViewPress(ActionEvent event) throws IOException {
        bolView = true;
        vBox_ParentPane.getChildren().clear();
        buttonPress();
        pane2 = FXMLLoader.load(getClass().getResource("/view/ShowView.fxml"));
        vBox_ParentPane.getChildren().addAll(pane2);
    }

    @FXML
    private void btnUpdelPress(ActionEvent event) throws IOException {
        bolUpdel = true;
        vBox_ParentPane.getChildren().clear();
        buttonPress();
        pane3 = FXMLLoader.load(getClass().getResource("/view/UpdelView.fxml"));
        vBox_ParentPane.getChildren().addAll(pane3);
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ActionEvent event = new ActionEvent();
        Font.loadFont(getClass().getResourceAsStream("src/font/Rockinsoda.ttf"), 14);
//        try {
//            bolInsert = true;
//            btnInsertPress(event);
//            buttonPress();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        btn_View.setFont(Font.font("src/font/Rockinsoda.ttf", 14));
//        btn_Updel.setFont(Font.font("src/font/Rockinsoda.ttf", 14));
//        btn_Exit.setFont(Font.font("src/font/Rockinsoda.ttf", 14));
//        btn_Insert.setFont(Font.font("src/font/Rockinsoda.ttf", 14));
        btn_Insert.setStyle("-fx-background-color: #ffffff00; -fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-font-size: 14; -fx-background-radius: 0; -fx-border-width: 1; -fx-border-color: #FFFFFF");
        btn_View.setStyle("-fx-background-color: #ffffff00; -fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-font-size: 14; -fx-background-radius: 0; -fx-border-width: 1; -fx-border-color: #FFFFFF");
        btn_Updel.setStyle("-fx-background-color: #ffffff00; -fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-font-size: 14; -fx-background-radius: 0; -fx-border-width: 1; -fx-border-color: #FFFFFF");
        btn_Exit.setStyle("-fx-background-color: #ffffff00; -fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-font-size: 14; -fx-background-radius: 0; -fx-border-width: 1; -fx-border-color: #FFFFFF");
    }
}
