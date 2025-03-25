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

public class ChangeController {

    @FXML
    private TextField text_surname;
    @FXML
    private TextField text_name;
    @FXML
    private TextField text_patronymic;
    @FXML
    private TextField text_gender;
    @FXML
    private TextField text_age;
    @FXML
    private TextField text_marital_status;
    @FXML
    private TextField text_the_presence_of_children;
    @FXML
    private TextField text_post;
    @FXML
    private TextField text_academic_degre;
    @FXML
    private ListView ListView_info;
    @FXML
    private TextField textfield_search;


    String url = "jdbc:mysql://localhost:3306/laboratory";
    String username = "pk31";
    String password = "123456";

    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    ObservableList<String> results = FXCollections.observableArrayList();
    //ListView<String> staffListView = new ListView<>(staffList);



    @FXML
    protected void Button_Create() {

        String selectedEmployee = ListView_info.getSelectionModel().getSelectedItem().toString();
        if (selectedEmployee != null) {
            String[] parts = selectedEmployee.split(" ");
            String surname = parts[0];
            String name = parts[1];
            String patronymic = parts[2];
            String sql =  "SELECT surname, name, patronymic, gender, age, marital_status, the_presence_of_children, post, academic_degre FROM laboratory_staff WHERE surname = ? AND name = ? AND patronymic = ?";

            try {
                // Загрузка данных выбранного сотрудника
                connection = DriverManager.getConnection(url,username,password);
                PreparedStatement selectPs = connection.prepareStatement(sql);
                selectPs.setString(1, surname);
                selectPs.setString(2, name);
                selectPs.setString(3, patronymic);
                ResultSet rs = selectPs.executeQuery();

                if (rs.next()) {
                    text_surname.setText(rs.getString("surname"));
                    text_name.setText(rs.getString("name"));
                    text_patronymic.setText(rs.getString("patronymic"));
                    text_gender.setText(rs.getString("gender"));
                    text_age.setText(String.valueOf(rs.getInt("age")));
                    text_marital_status.setText(rs.getString("marital_status"));
                    text_the_presence_of_children.setText(rs.getString("the_presence_of_children"));
                    text_post.setText(rs.getString("post"));
                    text_academic_degre.setText(rs.getString("academic_degre"));
                }

                // Обновление данных сотрудника
                /*PreparedStatement updatePs = connection.prepareStatement(
                        "UPDATE laboratory_staff SET surname = ?, name = ?, patronymic = ?, gender = ?, age = ?, " +
                                "marital_status = ?, the_presence_of_children = ?, post = ?, academic_degre = ? " +
                                "WHERE surname = ? AND name = ? AND patronymic = ?");
                updatePs.setString(1, text_surname.getText());
                updatePs.setString(2, text_name.getText());
                updatePs.setString(3, text_patronymic.getText());
                updatePs.setString(4, text_gender.getText());
                updatePs.setInt(5, Integer.parseInt(text_age.getText()));
                updatePs.setString(6, text_marital_status.getText());
                updatePs.setString(7, text_the_presence_of_children.getText());
                updatePs.setString(8, text_post.getText());
                updatePs.setString(9, text_academic_degre.getText());
                updatePs.setString(10, surname);
                updatePs.setString(11, name);
                updatePs.setString(12, patronymic);
                updatePs.executeUpdate();

                 */



            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }







    }
    @FXML
    protected void Button_update() {

        String selectedEmployee = ListView_info.getSelectionModel().getSelectedItem().toString();
        if (selectedEmployee != null) {
            String[] parts = selectedEmployee.split(" ");
            String surname = parts[0];
            String name = parts[1];
            String patronymic = parts[2];

            String sql = "UPDATE laboratory_staff SET surname = ?, name = ?, patronymic = ?, gender = ?, age = ?, " +
                    "marital_status = ?, the_presence_of_children = ?, post = ?, academic_degre = ? " +
                    "WHERE surname = ? AND name = ? AND patronymic = ?";


            try {
                // Загрузка данных выбранного сотрудника
                connection = DriverManager.getConnection(url, username, password);
                PreparedStatement updatePs = connection.prepareStatement(sql);
                updatePs.setString(1, surname);
                updatePs.setString(2, name);
                updatePs.setString(3, patronymic);

                updatePs.setString(1, text_surname.getText());
                updatePs.setString(2, text_name.getText());
                updatePs.setString(3, text_patronymic.getText());
                updatePs.setString(4, text_gender.getText());
                updatePs.setInt(5, Integer.parseInt(text_age.getText()));
                updatePs.setString(6, text_marital_status.getText());
                updatePs.setString(7, text_the_presence_of_children.getText());
                updatePs.setString(8, text_post.getText());
                updatePs.setString(9, text_academic_degre.getText());
                updatePs.setString(10, surname);
                updatePs.setString(11, name);
                updatePs.setString(12, patronymic);



                updatePs.executeUpdate();

                results.clear();
                PreparedStatement refreshPs = connection.prepareStatement("SELECT surname, name, post FROM laboratory_staff");
                ResultSet refreshRs = refreshPs.executeQuery();
                while (refreshRs.next()) {
                    results.add(refreshRs.getString("surname") + " - " + refreshRs.getString("name") + " - " + refreshRs.getString("post"));
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }




    }





        @FXML
        protected void Button_Update () {

            try {
                connection = DriverManager.getConnection(url, username, password);
                statement = connection.createStatement();
                resultSet = statement.executeQuery("SELECT surname, name, patronymic FROM laboratory_staff");
                ListView_info.getItems().clear();


                while (resultSet.next()) {
                    ListView_info.getItems().addAll(resultSet.getString(1) + " " + resultSet.getString(2) + " " + resultSet.getString(3));
                }
                resultSet.close();
                statement.close();
                connection.close();
            } catch (Exception e) {
                System.out.println(e);
            }


        }

        @FXML
        protected void Button_search () {

            String searchTerm = textfield_search.getText();
            if (searchTerm.length() > 0) {
                String query = "SELECT surname, name, patronymic, gender, age, marital_status, the_presence_of_children, post, academic_degre FROM laboratory_staff WHERE " +
                        "surname LIKE ? OR name LIKE ? OR patronymic LIKE ? OR gender LIKE ? OR age LIKE ? OR marital_status LIKE ? OR the_presence_of_children LIKE ? OR post LIKE ? OR academic_degre LIKE ?";


                try (Connection conn = DriverManager.getConnection(url, username, password);
                     PreparedStatement prepareStatement = conn.prepareStatement(query)) {

                    prepareStatement.setString(1, "%" + searchTerm + "%"); // Установка параметра для полей
                    prepareStatement.setString(2, "%" + searchTerm + "%");
                    prepareStatement.setString(3, "%" + searchTerm + "%");
                    prepareStatement.setString(4, "%" + searchTerm + "%");
                    prepareStatement.setString(5, "%" + searchTerm + "%");
                    prepareStatement.setString(6, "%" + searchTerm + "%");
                    prepareStatement.setString(7, "%" + searchTerm + "%");
                    prepareStatement.setString(8, "%" + searchTerm + "%");
                    prepareStatement.setString(9, "%" + searchTerm + "%");

                    ResultSet resultSet = prepareStatement.executeQuery();
                    textfield_search.clear();


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
            } else if (searchTerm.length() == 0) {
                System.out.println("ошибка");
            }

        }






}
