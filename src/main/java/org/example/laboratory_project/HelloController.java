package org.example.laboratory_project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Window;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.Parent;
import javafx.event.ActionEvent;


import java.io.IOException;


public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private TextField TextBox1;
    @FXML
    private Button Button1;
    int password = 3443;


    @FXML
    protected void Button1(){
        int passwordInput = Integer.parseInt(TextBox1.getText().trim());
        if (passwordInput == password) {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("window3.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
            Button1.getScene().getWindow().hide();

        } else{
            welcomeText.setText("пароль не верный");
        }










    }



}

