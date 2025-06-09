package org.example.laboratory_project;
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

import java.io.IOException;
import java.sql.*;

public class AddController {

    @FXML
    private TextField TextSurname;
    @FXML
    private TextField TextName;
    @FXML
    private TextField TextPatronymic;
    @FXML
    private TextField TextGender;
    @FXML
    private TextField TextAge;
    @FXML
    private TextField TextMarital_status;
    @FXML
    private TextField TextThe_presence_of_children;
    @FXML
    private TextField TextPost;
    @FXML
    private TextField TextAcademic_degre;
    @FXML
    private Button Button_Add;
    @FXML
    private Label labelErorr;
    @FXML
    private Label labelEx;





    String url  = "jdbc:mysql://localhost:8889/LABORATORY";
    String username = "pk31";
    String password = "123456";


    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;






    @FXML
    protected void Button_Add(){

        String _surname = TextSurname.getText().trim();
        String _name = TextName.getText().trim();
        String _patronymic = TextPatronymic.getText().trim();
        String _gender = TextGender.getText().trim();
        int _age = 0;

        try {
            _age = Integer.parseInt(TextAge.getText()); // Преобразуем строку в целое число
        } catch (NumberFormatException e) {
            // Обрабатываем ситуацию, когда текст не является корректным целым числом

        }

        String 	_marital_status = TextMarital_status.getText().trim();
        String _the_presence_of_children = TextThe_presence_of_children.getText().trim();
        String 	_post = TextPost.getText().trim();
        String _academic_degre = TextAcademic_degre.getText().trim();

        String sqlText = "INSERT INTO `laboratory_staff`(surname, name, patronymic, gender, age, marital_status, the_presence_of_children, post, academic_degre) VALUES (?,?,?,?,?,?,?,?,?)";

         try {
             if (_surname.length() > 0 && _name.length() > 0 && _patronymic.length() > 0 && _gender.length() > 0  && _marital_status.length() > 0 && _the_presence_of_children.length() > 0 && _post.length() > 0 && _academic_degre.length() > 0) {


                connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement(sqlText);
                preparedStatement.setString(1, _surname);
                preparedStatement.setString(2, _name);
                preparedStatement.setString(3, _patronymic);
                preparedStatement.setString(4, _gender);
                preparedStatement.setInt(5, _age);
                preparedStatement.setString(6, _marital_status);
                preparedStatement.setString(7, _the_presence_of_children);
                preparedStatement.setString(8, _post);
                preparedStatement.setString(9, _academic_degre);
                preparedStatement.executeUpdate();
                Button_Add.getScene().getWindow().hide();
             }else {
                 labelErorr.setText("не все окна заполнены");
             }



            }catch(Exception e){
                System.out.println(e);
            }










    }



}
