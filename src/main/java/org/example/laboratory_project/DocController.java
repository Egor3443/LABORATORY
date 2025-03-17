package org.example.laboratory_project;
import javafx.collections.FXCollections;
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

import java.sql.*;

public class DocController {
    @FXML
    private Label label_surname;
    @FXML
    private Label label_name;
    @FXML
    private Label label_patronymic;
    @FXML
    private Label label_gender;
    @FXML
    private Label label_age;
    @FXML
    private Label label_marital_status;
    @FXML
    private Label label_the_presence_of_children;
    @FXML
    private Label label_post;
    @FXML
    private Label label_academic_degre;
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

    @FXML
    protected void Button_create() {
        String selectedEmployee = ListView_info.getSelectionModel().getSelectedItem().toString();

        if (selectedEmployee != null) {
            // Получаем ID сотрудника из выбранной строки
            String[] selectedEmployeeId = selectedEmployee.split(" ");
            String surname = selectedEmployeeId[0];
            String name = selectedEmployeeId[1];
            String patronymic = selectedEmployeeId[2];

            // Загружаем информацию о сотруднике из базы данных
            try (Connection connection = DriverManager.getConnection(url,username,password)) {

                String sql = "SELECT surname, name, patronymic, gender, age, marital_status, the_presence_of_children, post, academic_degre FROM laboratory_staff WHERE surname = ? AND name = ? AND patronymic = ?";


                try (PreparedStatement statement = connection.prepareStatement(sql)) {

                    statement.setString(1,surname);
                    statement.setString(2,name);
                    statement.setString(3,patronymic);

                    ResultSet resultSet = statement.executeQuery();

                    if (resultSet.next()) {
                        // Обновляем Labels
                        label_surname.setText(resultSet.getString(1));
                        label_name.setText( resultSet.getString(2));
                        label_patronymic.setText( resultSet.getString(3));
                        label_gender.setText(resultSet.getString(4));
                        label_age.setText(String.valueOf(resultSet.getInt(5)));
                        label_marital_status.setText( resultSet.getString(6));
                        label_the_presence_of_children.setText(resultSet.getString(7));
                        label_post.setText( resultSet.getString(8));
                        label_academic_degre.setText(resultSet.getString(9));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

        @FXML
        protected void Button_update() {
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
        protected void Button_search() {
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
            }else if (searchTerm.length() == 0){
                System.out.println("ошибка");
            }
        }


    }

