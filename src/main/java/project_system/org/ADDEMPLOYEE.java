package project_system.org;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ADDEMPLOYEE {

    @FXML
    private Button BTNE_CANCEL;

    @FXML
    private Button BTN_ADDPICTURE;

    @FXML
    private Button BTN_ADD_EMPLOYEE;

    @FXML
    private Label Goodmorninggreet;

    @FXML
    private ImageView IMAGE_VIEW;

    @FXML
    private ImageView LOGOUT;

    @FXML
    private TextField TXT_EMAIL;

    @FXML
    private TextField TXT_ID;

    @FXML
    private TextField TXT_NAME;

    @FXML
    private TextField TXT_PASS;

    @FXML
    private TextField TXT_SURENAME;

    @FXML
    private TextField TXT_DEPARTMENT;

    @FXML
    private TextField TXT_POSITION;

    @FXML
    private ComboBox<String> comboDepartment;

    @FXML
    private ImageView btnCREATETASK;

    @FXML
    private ImageView btnHOME;

    @FXML
    private ImageView btnPERFORAMNCE;

    @FXML
    private ImageView btnPRINT;

    @FXML
    private Label dategreet;

    @FXML
    private Label timegreet;

    private final String DB_URL = "jdbc:mysql://localhost:3306/pomsdb";
    private final String USER = "root";
    private final String PASSWORD = "luese_192003";

    @FXML
    public void initialize() {
        
    }
    @FXML
    void BTN_ADD_EMPLOYEE(MouseEvent event) {
        String name = TXT_NAME.getText();
        String surename = TXT_SURENAME.getText();
        String email = TXT_EMAIL.getText();
        String id = TXT_ID.getText();
        String pass = TXT_PASS.getText();
        String position = TXT_POSITION.getText();
        String department = TXT_DEPARTMENT.getText();

        if (name.isEmpty() || surename.isEmpty() || email.isEmpty() || id.isEmpty() || pass.isEmpty() || department.isEmpty() || position.isEmpty()) {
            System.out.println("Please fill all fields");
        } else {
            String insertQuery;
            clearFields();
            System.out.println("Employee added successfully");
            if (IMAGE_VIEW.getImage() == null) {
                insertQuery = "INSERT INTO `employeedb` (`EmployeeID` ,`FullName`, `LastName`, `Email`, `Password`, `Department`, `Position`, `Picture`, `Status`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            } else {
                insertQuery = "INSERT INTO `employeedb` (`EmployeeID` ,`FullName`, `LastName`, `Email`, `Password`, `Department`, `Position`, `Picture`, `Status`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            }
            if(position.equalsIgnoreCase("Admin")) {
                insertQuery = "INSERT INTO `employeedb` (`EmployeeID` ,`FullName`, `LastName`, `Email`, `Password`, `Department`, `Position`, `Picture`, `Status`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            } else {
                insertQuery = "INSERT INTO `employeedb` (`EmployeeID` ,`FullName`, `LastName`, `Email`, `Password`, `Department`, `Position`, `Picture`, `Status`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                System.out.println("You are not authorized to add employees");
            }

            try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, id);
                preparedStatement.setString(2, name);
                preparedStatement.setString(3, surename);
                preparedStatement.setString(4, email);
                preparedStatement.setString(5, pass);
                preparedStatement.setString(6, department);
                preparedStatement.setString(7, position);
                preparedStatement.setString(8, "picture");
                preparedStatement.setString(9, "Active");
                preparedStatement.executeUpdate();
                clearFields();
                showAlert("Success", "Employee added successfully");
               
            } catch (SQLException e) {
                e.printStackTrace();
               showAlert("Error", "Failed to add employee");
            }
        }

    }

    private void clearFields() {
        TXT_NAME.clear();
        TXT_SURENAME.clear();
        TXT_EMAIL.clear();
        TXT_ID.clear();
        TXT_PASS.clear();
        TXT_DEPARTMENT.clear();
        TXT_POSITION.clear();
        IMAGE_VIEW.setImage(null);
    }
    @FXML
    void BTN_CANCEL(MouseEvent event) {

    }

    @FXML
    void BTN_LOGOUT(MouseEvent event) {
    }
    @FXML
    void BTN_PICTURE(MouseEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            System.out.println(file);
            file.toURI().toString();
            IMAGE_VIEW.setImage(new javafx.scene.image.Image(file.toURI().toString()));
        }
    }

    @FXML
    void btnCREATETASK(MouseEvent event) throws IOException {

     App.setRoot("ASSIGNED_TASK", 1280, 800);

    }

    @FXML
    void btnHOME(MouseEvent event) throws IOException {

        App.setRoot("DASHBOARD", 1280, 800);

    }

    @FXML
    void btnPERFORAMANCE(MouseEvent event) throws IOException {

        App.setRoot("PERFORMANCE", 1280, 800);
    }

    @FXML
    void btnPRINT(MouseEvent event) {
    }
    private void showAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();

}
   
}
