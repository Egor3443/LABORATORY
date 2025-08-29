package org.example.laboratory_project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Window;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.Parent;
import javafx.event.ActionEvent;

import java.sql.*;

public class DocController {
    @FXML
    private ListView ListView_info;
    @FXML
    private ListView List_Doc;
    @FXML
    private ImageView imageView_Doc;
    @FXML
    private TextField textfield_search;
    String url = "jdbc:mysql://localhost:8889/LABORATORY";
    String username = "pk31";
    String password = "123456";

    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    ObservableList<String> results = FXCollections.observableArrayList();

    Alert alertNs = new Alert(Alert.AlertType.INFORMATION);
    Alert alertCon = new Alert(Alert.AlertType.CONFIRMATION);

    @FXML
    protected void Button_create() {
        String selectedEmployee = ListView_info.getSelectionModel().getSelectedItem().toString();

        if (selectedEmployee != null) {

            String[] selectedEmployeeId = selectedEmployee.split(" ");
            String surname = selectedEmployeeId[0];
            String name = selectedEmployeeId[1];
            String patronymic = selectedEmployeeId[2];

            try (Connection connection = DriverManager.getConnection(url,username,password)) {

                String sql = "SELECT surname, name, patronymic, gender, age, marital_status, the_presence_of_children, post, academic_degre FROM laboratory_staff WHERE surname = ? AND name = ? AND patronymic = ? " ;

                try (PreparedStatement statement = connection.prepareStatement(sql)) {

                    statement.setString(1,surname);
                    statement.setString(2,name);
                    statement.setString(3,patronymic);
                    ResultSet resultSet = statement.executeQuery();

                    if (resultSet.next()) {
                        // Обновляем Labels
                        alertCon.setTitle("отчет о сотруднике");
                        alertCon.setContentText("Фамилия: " + resultSet.getString(1) + "\n"
                                + "Имя: " + resultSet.getString(2) + "\n"
                                + "Отчество: " + resultSet.getString(3) + "\n"
                                + "Пол: " + resultSet.getString(4) + "\n"
                                + "Возраст: " + resultSet.getString(5) + "\n"
                                + "Семейное положение: " + resultSet.getString(6) + "\n"
                                + "Наличие детей: " + resultSet.getString(7) + "\n"
                                + "Должность: " + resultSet.getString(8) + "\n"
                                + "Ученая степень: " + resultSet.getString(9) + "\n");
                        alertCon.showAndWait();
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
                        "surname LIKE ? OR name LIKE ? OR patronymic LIKE ? OR gender LIKE ? OR age LIKE ? OR marital_status LIKE ?  OR the_presence_of_children LIKE ? OR post LIKE ? OR academic_degre LIKE ?";

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
                    ListView_info.getItems().clear();

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

    @FXML
    protected void button_DocLab(){

        String searchTerm = textfield_search.getText();
        if (searchTerm.length() > 0) {
            String query = "SELECT surname, name, patronymic, gender, age, marital_status, the_presence_of_children, post, academic_degre FROM laboratory_staff WHERE " +
                    "surname LIKE ? OR name LIKE ? OR patronymic LIKE ? OR gender LIKE ? OR age LIKE ? OR marital_status LIKE ?  OR the_presence_of_children LIKE ? OR post LIKE ? OR academic_degre LIKE ?";

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
                ListView_info.getItems().clear();

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
                    results.add("ФИО: " + surname + " "  + name + " " + patronymic + "\n" + "Личная информация: \n" +"Пол: "+ gender + "\n" + "Возраст: " +age+ "\n" + "Семейное положение: " + marital_status+ "\n" + "Наличие детей: " + the_presence_of_children+ "\n" + "Должность: " + post + "\n" + "Ученая степень: "+academic_degre + "\n");
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            List_Doc.setItems(results);
        }else if (searchTerm.length() == 0){
            System.out.println("ошибка");
        }
    }
}

