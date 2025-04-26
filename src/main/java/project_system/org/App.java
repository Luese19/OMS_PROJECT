package project_system.org;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.xml.stream.XMLStreamException;
import java.sql.DriverManager;

/**
 * JavaFX App
 */
public class App extends Application {
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/pomsdb?serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "luese_192003";

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        // Load the MySQL JDBC driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Establish the connection
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
    private static Scene scene;
    @Override
    public void start(Stage stage) throws IOException {
        try {
            Parent root = loadFXML("Login");
            scene = new Scene(root, 1300, 810);
            stage.setScene(scene);
            stage.show();
        } catch (XMLStreamException e) {
            System.err.println("Error loading FXML file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    static void setRoot(String fxml, double width, double height) throws IOException {
        try {
            Parent root = loadFXML(fxml);
            scene.setRoot(root);
            scene.getWindow().setWidth(width);
            scene.getWindow().setHeight(height);
        } catch (XMLStreamException e) {
            System.err.println("Error loading FXML file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static Parent loadFXML(String fxml) throws IOException, XMLStreamException {
        // Ensure the path to the FXML file is correct
        String fxmlPath = fxml + ".fxml";
        System.out.println("Loading FXML file: " + fxmlPath);
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxmlPath));
        return fxmlLoader.load();
    }
    







    public static void main(String[] args) {
        launch();

        try (Connection connection = App.getConnection()) {
            System.out.println("Connection successful!");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
       
    }
}