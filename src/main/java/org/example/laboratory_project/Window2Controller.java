package org.example.laboratory_project;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
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
import java.util.ArrayList;
import java.util.List;


public class Window2Controller {

    @FXML
    private TextField TextField_search;



    @FXML
    public ListView ListView_info;


    // Label для документа
    @FXML
    private Label label_surname;

    @FXML
    private Label label_name;

    @FXML
    private Label Label_Erorr;





    String url  = "jdbc:mysql://localhost:3306/laboratory";
    String username = "pk31";
    String password = "123456";
    String Password = "3443";
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;



    @FXML
    protected void Button_search() {

        String searchTerm = TextField_search.getText();
        String query = "SELECT surname, name, patronymic, gender, age, marital_status, the_presence_of_children, post, academic_degre FROM laboratory_staff WHERE " +
                "surname LIKE ? OR name LIKE ? OR patronymic LIKE ? OR gender LIKE ? OR age LIKE ? OR marital_status LIKE ? OR the_presence_of_children LIKE ? OR post LIKE ? OR academic_degre LIKE ?";

        ObservableList<String> results = FXCollections.observableArrayList();

        try (Connection conn = DriverManager.getConnection(url,username,password);
             PreparedStatement prepareStatement = conn.prepareStatement(query)) {

            prepareStatement.setString(1,"%" +  searchTerm + "%"); // Установка параметра для полей
            prepareStatement.setString(2,"%" +  searchTerm + "%");
            prepareStatement.setString(3,"%" +  searchTerm + "%");
            prepareStatement.setString(4,"%" +  searchTerm + "%");
            prepareStatement.setString(5,"%" +  searchTerm + "%");
            prepareStatement.setString(6,"%" +  searchTerm + "%");
            prepareStatement.setString(7,"%" +  searchTerm + "%");
            prepareStatement.setString(8,"%" +  searchTerm + "%");
            prepareStatement.setString(9,"%" +  searchTerm + "%");




            ResultSet resultSet = prepareStatement.executeQuery();


            while (resultSet.next()) {
                String surname = resultSet.getString("surname");
                String name = resultSet.getString("name");
                String patronymic = resultSet.getString("patronymic");
                String gender = resultSet.getString("gender");
                int age = resultSet.getInt("age");
                String marital_status = resultSet.getString("marital_status");
                String the_presence_of_children = resultSet.getString("the_presence_of_children");
                String post = resultSet.getString("post");
                String academic_degre = resultSet.getString("academic_degre");

                results.add(surname + " " + name + " " + patronymic);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        // Добавление результатов в список
        ListView_info.setItems(results);

    }

    @FXML
    protected void Button_Doc() {








        try {

            String selectedItems = ListView_info.getSelectionModel().getSelectedItems().toString();
            Label_Erorr.setText("фамилия: " + selectedItems);

            /*connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT surname FROM laboratory_staff" );



            if (selectedItems != null) {
               // System.out.println(selectedItems);
            while (resultSet.next()){

                label_surname.setText(selectedItems);
            }
            resultSet.close();
            statement.close();
            connection.close();
            }
            else {
                System.out.println("не выбрал");
            }

             */
        }


        catch (Exception e){
            System.out.println(e);
        }



    }

    @FXML
    protected void Button_Delete() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("WindowDoc.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();

        System.out.println("ffffff");
        System.out.println("ffffff");

    }

    @FXML
    protected void Button_Change() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("WindowChange.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();

        System.out.println("ffffff");


    }

    @FXML
    protected void Button_Add() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("WindowAdd.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();

    }

    @FXML
    protected void Button_update() {
        try {
            connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT surname, name, patronymic FROM laboratory_staff" );
            ListView_info.getItems().clear();


            while (resultSet.next()){
                ListView_info.getItems().addAll( resultSet.getString(1) + " " + resultSet.getString(2 )+ " " +resultSet.getString(3));
            }
            resultSet.close();
            statement.close();
            connection.close();
        }
        catch (Exception e){
            System.out.println(e);
        }

    }









}
