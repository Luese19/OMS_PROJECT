package project_system.org;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import javafx.stage.FileChooser;
import javafx.stage.Stage;


import javafx.scene.control.TableView;

public class ASSIGNEDTASKCONTRLLER {
    @FXML
    private TableView<?> pendingTable;

    @FXML
    private Button BTN_ASSIGNED;

    
    @FXML
    private ImageView btnPERFORAMNCE;

    @FXML
    private ComboBox<String> COMBO_EMPLOYEE;

    @FXML
    private ComboBox<String> COMBO_PROJECT;

    @FXML
    private DatePicker DUE_CALENDAR;

    @FXML
    private Label Goodmorninggreet;

    @FXML
    private ImageView LOGOUT;

    @FXML
    private TextArea TXT_INSTRUCTION;

    @FXML
    private TextField TXT_SURNAME;

    @FXML
    private Button UPLOAD;

    @FXML
    private ImageView btnCREATETASK;

    @FXML
    private ImageView btnHOME;

    @FXML
    private ImageView btnPerformance;

    @FXML
    private ImageView btnPRINT;

    

    @FXML
    private Label TEXTFILE;

    
    @FXML
    private ImageView SHOWFILE;

    @FXML
    private ComboBox<String> AssignedEmployee; // Added
    @FXML
    private ComboBox<String> department_list; // Added
    private File selectedFile; // Added

    
    // Database credentials (replace with your own)
    private final String DB_URL = "jdbc:mysql://127.0.0.1:3306/pomsdb?serverTimezone=UTC";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "luese_192003";



    public void initialize() {

        populateDepartment();
        populateEmployee();
      
       
    }
    
    @FXML
    void btnAssignEmployee(MouseEvent event) {
        String query = "SELECT FullName FROM employeedb";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            ObservableList<String> employeeNames = FXCollections.observableArrayList();
            while (resultSet.next()) {
                employeeNames.add(resultSet.getString("FullName"));
            }
            AssignedEmployee.setItems(employeeNames);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void BTN_ASSIGNED(MouseEvent event) {
        if (selectedFile == null) {
            showAlert("Error", "No file selected. Please upload a file.");
            return;
        }

        if (!selectedFile.exists()) {
            showAlert("Error", "Selected file does not exist. Please upload a valid file.");
            return;
        }

        String employee = COMBO_EMPLOYEE.getValue();
        String project = COMBO_PROJECT.getValue();
        String due = DUE_CALENDAR.getValue().toString();
        String instruction = TXT_INSTRUCTION.getText();
        String title = TXT_SURNAME.getText();
        String status = "Pending";

        String insertquery = "INSERT INTO taskdb (Title, Instruction, AssignedTo, Due, `For`, File, FileData, FileType, Status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement insertStatement = connection.prepareStatement(insertquery);
             FileInputStream fis = new FileInputStream(selectedFile)) {

            insertStatement.setString(1, title);
            insertStatement.setString(2, instruction);
            insertStatement.setString(3, employee);
            insertStatement.setString(4, due);
            insertStatement.setString(5, project);
            insertStatement.setString(6, selectedFile.getName());
            insertStatement.setBinaryStream(7, fis, (int) selectedFile.length());
            insertStatement.setString(8, getFileType(selectedFile));
                        insertStatement.setString(9, status);
            
                        int rowsAffected = insertStatement.executeUpdate();
            
                        if (rowsAffected > 0) {
                            showAlert("Success", "Task assigned successfully.");
                            clearFields();
                        } else {
                            showAlert("Error", "An error occurred. Please try again.");
                        }
                    } catch (SQLIntegrityConstraintViolationException e) {
                        showAlert("Error", "Duplicate entry. The task already exists.");
                    } catch (SQLException | IOException e) {
                        e.printStackTrace();
                        showAlert("Error", "An error occurred. Please try again.");
                    }
                    // Handle assignment logic
                }
            
                private String getFileType(File selectedFile2) {
                    String fileName = selectedFile2.getName();
                    String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
                    return extension;
                }
            
                @FXML
    void BTN_LOGOUT(MouseEvent event) {
        clearUserSession();
        // Redirect to login screen
        try {
            App.setRoot("Login",  1300, 810);
        } catch (IOException e) {
            e.printStackTrace();
        }
    
      }
  
      private void clearUserSession() {
          UserSession.clear();
      }
  
      public static class UserSession {
          private static String username;
      
          public static void setUsername(String username) {
              UserSession.username = username;
          }
      
          public static String getUsername() {
              return username;
          }
      
          public static void clear() {
              username = null;
          }
    }

    @FXML
    void BTN_PROJECT(MouseEvent event) {
        String query = "SELECT ProjectTitle FROM projectname";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            ObservableList<String> departmentNames = FXCollections.observableArrayList();
            while (resultSet.next()) {
                departmentNames.add(resultSet.getString("ProjectTitle"));
            }
            COMBO_PROJECT.setItems(departmentNames);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    

        @FXML
    void BTN_UPLOAD(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.pdf","*.png","*.docx", "*.jpg", "*.jpeg", "*.gif", "*.bmp", "*.tiff", "*.csv", "*.xls", "*.xlsx", "*.ppt", "*.pptx", "*.txt", "*.rtf", "*.html", "*.xml", "*.json", "*.zip", "*.rar", "*.tar", "*.gz", "*.7z", "*.mp3", "*.mp4", "*.avi", "*.flv", "*.mkv", "*.mov", "*.wmv", "*.webm", "*.ogg", "*.ogv", "*.3gp", "*.pdf", "*.doc", "*.docx", "*.ppt", "*.pptx", "*.xls", "*.xlsx", "*.txt", "*.rtf", "*.html", "*.xml", "*.json", "*.zip", "*.rar", "*.tar", "*.gz", "*.7z", "*.mp3", "*.mp4", "*.avi", "*.flv", "*.mkv", "*.mov", "*.wmv", "*.webm", "*.ogg", "*.ogv", "*.3gp"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            TEXTFILE.setText(selectedFile.getAbsolutePath());

            try {
                javafx.scene.image.Image image = new javafx.scene.image.Image(selectedFile.toURI().toString());
                SHOWFILE.setImage(image);
               // SHOWFILE.setText(selectedFile.getAbsolutePath());
               // Handle the file as an image or another appropriate type
            } catch (Exception e) {
                e.printStackTrace();
                // Handle the exception (e.g., show an alert to the user)
            }
        }
    }


    @FXML
    public void populateDepartment() {
        String query = "SELECT ProjectTitle FROM projectname";                                      //populate department list
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            ObservableList<String> departmentNames = FXCollections.observableArrayList();
            while (resultSet.next()) {
                departmentNames.add(resultSet.getString("ProjectTitle"));
            }
            COMBO_PROJECT.setItems(departmentNames);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML                                                           //populate employee list
    public void populateEmployee() {
        String query = "SELECT FullName FROM employeedb";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            ObservableList<String> employeeNames = FXCollections.observableArrayList();
            while (resultSet.next()) {
                employeeNames.add(resultSet.getString("FullName"));
            }
            COMBO_EMPLOYEE.setItems(employeeNames);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnCREATETASK(MouseEvent event) throws IOException {
       App.setRoot("ASSIGNED_TASK", 1300, 810);
    }

    @FXML
    void BTN_ASSEMP(MouseEvent event) throws IOException {
     
        App.setRoot("ASSIGNED_TASK", 1300, 810);
    }


    private void clearFields() {
        COMBO_EMPLOYEE.setValue(null);
        COMBO_PROJECT.setValue(null);
        DUE_CALENDAR.setValue(null);
        TXT_INSTRUCTION.clear();
        TXT_SURNAME.clear();
        TEXTFILE.setText("");
    }

    @FXML
    void btnHOME(MouseEvent event) throws IOException {
        // Handle home button logic

        App.setRoot("DASHBOARD",  1300, 810);
    }

    @FXML
    void btnPerformance(MouseEvent event) throws IOException {
        // Handle performance button logic

        App.setRoot("PERFORMANCE",  1300, 810);
    }

    @FXML
    void btnPRINT(MouseEvent event) {
       
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null && job.showPrintDialog(pendingTable.getScene().getWindow())) {
            boolean success = job.printPage(pendingTable);
            if (success) {
                job.endJob();
            } else {
                showAlert("Error", "Printing failed.");
            }
        }
  
    }
    
    @FXML
    void btnPERFORAMANCE(MouseEvent event) throws IOException {

        App.setRoot("PEFORMANCE",  1300, 810);

    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

