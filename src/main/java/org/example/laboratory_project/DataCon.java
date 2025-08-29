package org.example.laboratory_project;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.stage.Window;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.Parent;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.*;
import java.sql.DriverManager;



public class DataCon {
    String url  = "jdbc:mysql://localhost:8889/LABORATORY";
    String username = "pk31";
    String password = "123456";
    String Password = "3443";

    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;

    PreparedStatement preparedStatement = null;
    ObservableList<String> results = FXCollections.observableArrayList();

    public void datacon(){



    }
}
