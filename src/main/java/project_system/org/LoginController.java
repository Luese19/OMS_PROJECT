package project_system.org;

import java.awt.Desktop;
import java.io.IOException;

import java.net.URI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TextFormatter;



public class LoginController {

    @FXML
    private Button btnR;


    @FXML
    private Button button1;

    
     @FXML
    private TextField txtemail;

    @FXML
    private TextField txtpass; // Changed from PasswordField to TextField
    @FXML
    private CheckBox showpass;

    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    private Hyperlink btnLINK;



    @FXML
    private void initialize() {
        // Add a listener to toggle password visibility
        showpass.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                txtpass.setPromptText(txtpass.getText()); // Show the actual password in the prompt text
                txtpass.setText(""); // Clear the text field
            } else {
                txtpass.setText(txtpass.getPromptText()); // Restore the password to the text field
                txtpass.setPromptText(null); // Clear the prompt text
            }
        });

        if (progressIndicator == null) {
            progressIndicator = new ProgressIndicator();
        }

    }
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/pomsdb?serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "luese_192003";

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    private static final int MAX_ATTEMPTS = 3;
    private static final long LOCK_DURATION = 30000; // 30 seconds
    private int failedAttempts = 0;
    private long lockTime = 0;

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Login Status");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    

    private boolean performLogin(Connection connection, String email, String password, String tableName)
            throws SQLException {
        String query = "SELECT COUNT(*) FROM " + tableName + " WHERE Email = ? AND Password = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    @FXML
    void btn1(MouseEvent event) throws IOException {

        String email = txtemail.getText();
        String password = txtpass.getText();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            if (performLogin(connection, email, password, "admindb")) {
                showAlert("Login Successful, Welcome, Admin " + "!");
                App.setRoot("DASHBOARD", 1300, 810);
                Node source = (Node) event.getSource();
                if (source.getScene() != null) {
                    source.getScene().getWindow().hide();
                }
                failedAttempts = 0; // Reset failed attempts on successful login
            } else if (performLogin(connection, email, password, "employeedb")) {
                showAlert("Login Successful, Welcome, "+ "!");
                App.setRoot("E_DASHBOARD1",  1300, 810);
               // loadEmployeeTasks(email); // Load tasks assigned to the employee
                failedAttempts = 0; // Reset failed attempts on successful login
            } else {
                failedAttempts++;
                if (failedAttempts >= MAX_ATTEMPTS) {
                    lockTime = System.currentTimeMillis() + LOCK_DURATION;
                    showAlert("Too many failed attempts. Account is locked for 30 seconds.");
                } else {
                    showAlert("Login Failed");
                }
            }
        } catch (SQLException e) {
            showAlert("Error: " + e.getMessage());
        } finally {
            progressIndicator.setVisible(false); // Hide loading indicator
        }
    }

    /*private void loadEmployeeTasks(String email) {
        String query = "SELECT Title FROM taskdb  WHERE AssignedTo = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                StringBuilder tasks = new StringBuilder("Tasks assigned to you:\n");
                while (resultSet.next()) {
                    tasks.append(resultSet.getString("task")).append("\n");
                }
                showAlert(tasks.toString());
            }
        } catch (SQLException e) {
            showAlert("Error loading tasks: " + e.getMessage());
        }*/

    

    @FXML
    void showpassword(MouseEvent event) {
        if (txtpass.isDisable()) {
            txtpass.setDisable(false);
        txtpass.setText(txtpass.getPromptText());
        txtpass.setPromptText(null);
        } else {
            txtpass.setDisable(true);
        }
    }
    
public void logout() throws IOException {  // logout method
    // Navigate back to the login screen
    App.setRoot("Login", 1300, 810);
}


@FXML
void btnLogin(MouseEvent event) throws IOException {
    progressIndicator.setVisible(true); // Show loading indicator


    String email = txtemail.getText();
    String password = txtpass.getText();

    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
        if (performLogin(connection, email, password, "admindb")) {
            showAlert("Login Successful, Welcome, Admin " + "!");
            App.setRoot("DASHBOARD",  1300, 810);
            Node source = (Node) event.getSource();
            if (source.getScene() != null) {
                source.getScene().getWindow().hide();
            }
            failedAttempts = 0; // Reset failed attempts on successful login
        } else if (performLogin(connection, email, password, "employeedb")) {
            showAlert("Login Successful, Welcome, " + "!");
            App.setRoot("E_DASHBOARD1", 1300, 810);
          //  loadEmployeeTasks(email); // Load tasks assigned to the employee
            failedAttempts = 0; // Reset failed attempts on successful login
        } else {
            failedAttempts++;
            if (failedAttempts >= MAX_ATTEMPTS) {
                lockTime = System.currentTimeMillis() + LOCK_DURATION;
                showAlert("Too many failed attempts. Account is locked for 30 seconds.");
            } else {
                showAlert("Login Failed");
            }
        }
    } catch (SQLException e) {
        showAlert("Error: " + e.getMessage());
    } finally {
        progressIndicator.setVisible(false); // Hide loading indicator
    }

}

@FXML
    void CLICKLINK(ActionEvent event) throws IOException {
   
        Desktop.getDesktop().browse(URI.create("https://www.facebook.com/rivas.lueseandrey?mibextid=ZbWKwL"));
}

@FXML
    void handleLogin(ActionEvent event) throws IOException {
        String email = txtemail.getText();
        String password = txtpass.getText();

        // Check if the account is locked
        if (System.currentTimeMillis() < lockTime) {
            showAlert("Account is locked. Please try again later.");
            return;
        }

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            if (performLogin(connection, email, password, "admindb")) {
                showAlert("Login Successful, Welcome, Admin " + "!");
                App.setRoot("DASHBOARD",  1300, 810);
                Node source = (Node) event.getSource();
                if (source.getScene() != null) {
                    source.getScene().getWindow().hide();
                }
                failedAttempts = 0; // Reset failed attempts on successful login
            } else if (performLogin(connection, email, password, "employeedb")) {
                showAlert("Login Successful, Welcome, " + "!");
                App.setRoot("E_DASHBOARD1",  1300, 810);
                failedAttempts = 0; // Reset failed attempts on successful login
            } else {
                failedAttempts++;
                if (failedAttempts >= MAX_ATTEMPTS) {
                    lockTime = System.currentTimeMillis() + LOCK_DURATION;
                    showAlert("Too many failed attempts. Account is locked for 30 seconds.");
                } else {
                    showAlert("Login Failed");
                }
            }
        } catch (SQLException e) {
            showAlert("Error: " + e.getMessage());
        } finally {
            progressIndicator.setVisible(false); // Hide loading indicator
        }
    }

}
