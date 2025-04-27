package project_system.org;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;

import javafx.scene.control.Label;

import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Callback;




public class AdminController1  extends Method{

    @FXML
    private TableColumn<Task, String> F_AssignedTo;

    @FXML
    private TableColumn<Task, Integer> F_Due;

    @FXML
    private TableColumn<Task, String> F_File;

    @FXML
    private TableColumn<Task, String> F_For;

    @FXML
    private TableColumn<Task, Integer> F_ID;

    @FXML
    private TableColumn<Task, String> F_Instruction;

    @FXML
    private TableColumn<Task, String> F_Status;

    @FXML
    private TableColumn<Task, String> F_Title;

    @FXML
    private TableColumn<Task, Void> F_Delete;

  

    @FXML
    private ImageView LOGOUT;

    @FXML
    private TableColumn<Task, Integer> P_No;

    @FXML
    private TableColumn<Task, String> M_AssignedTo;

    @FXML
    private TableColumn<Task, Integer> M_Due;

    @FXML
    private TableColumn<Task, String> M_File;

    @FXML
    private TableColumn<Task, String> M_For;

    @FXML
    private TableColumn<Task, String> M_ID;

    @FXML
    private TableColumn<Task, String> M_Instruction;

    @FXML
    private TableColumn<Task, String> M_Status;

    @FXML
    private TableColumn<Task, String> M_Title;

    @FXML
    private TableColumn<Task, String> P_AssignedTo;

    @FXML
    private TableColumn<Task, Integer> P_Due;

    @FXML
    private TableColumn<Task, String> P_File;

    @FXML
    private TableColumn<Task, String> P_For;

    @FXML
    private TableColumn<Task, Integer> P_ID;

    @FXML
    private TableColumn<Task, String> P_Instruction;

    @FXML
    private TableColumn<Task, String> P_Status;

    @FXML
    private TableColumn<Task, Void> P_Delete;


    @FXML
    private TableView<Task> pendingTable;

    @FXML
    private TableView<Task> finishedTable;

    @FXML
    private AnchorPane EMPLOYEE_TAB;

    @FXML
    private TableView<Project> TABLE_PROJECT;

    @FXML
    private Button btnRetrieveFile;


    @FXML
    private TableView<Project> projectTable;


    @FXML
    private TableColumn<Task,Void> M_Delete;

    @FXML
    private TableView<Task> MiissingTable;

    @FXML
    private Button ADD_EMPLOYEE;

    @FXML
    private TableView<Employee> EMPLOYEE_TABLE;

    @FXML
    private TableColumn<Employee, Void> E_DELETE;

    @FXML
    private TableColumn<Employee, String> E_DEPARTMENT;

    @FXML
    private TableColumn<Employee, String> E_EMAIL;

    @FXML
    private TableColumn<Employee, String> E_FIRSTNAME;

    @FXML
    private TableColumn<Employee, Integer> E_ID;

    @FXML
    private TableColumn<Employee, String> E_LASTNAME;

    @FXML
    private TableColumn<Employee, String> E_POSITION;

    @FXML
    private TableColumn<Employee, String> E_STATUS;


    
    @FXML
    private Button BTN_SAVE;

    @FXML
    private TextField TEXT_D;

    @FXML
    private TextField TEXT_E;

    @FXML
    private TextField TEXT_L;

    @FXML
    private TextField TEXT_N;

    @FXML
    private TextField TEXT_P;

    @FXML
    private TextField TEXT_S;

    @FXML
    private WebView webview;

    @FXML
    private  WebView webview1;
     
    @FXML
    private ComboBox<String> DEPARTMENT_COMBO;

    @FXML
    private TableColumn<Employee, String> EDIT;

    @FXML
    private TableColumn<Employee, String> PROGRESS;

    @FXML
    private TableColumn<Task, String> P_Title;

    @FXML
    private TableColumn<Task, String> PR_NO;

    @FXML
    private TableColumn<Project, Double> PR_PROGRESS;

    @FXML
    private TableColumn<Project, Double> PR_TABLE;

    @FXML
    private TableColumn<Project, String> PRaction;

    @FXML
    private StackedBarChart<Task, String> P_STACKEDBAR;

    @FXML
    private TableColumn<Task, Integer> No;

    @FXML
    private TextField SEARCH_BAR;

    @FXML
    private ImageView SETTINGS;
   
    @FXML
    private TableColumn<Task, Number> rowNumberColumn;
  
    private final String DB_URL = "jdbc:mysql://127.0.0.1:3306/pomsdb?serverTimezone=UTC";
    private final String USER = "root";
    private final String PASSWORD = "luese_192003";

    private ObservableList<Task> pendingList = FXCollections.observableArrayList();
    private ObservableList<Task> finishedList = FXCollections.observableArrayList();
    private ObservableList<Task> MissingList = FXCollections.observableArrayList();
    private ObservableList<Employee> EmployeeList = FXCollections.observableArrayList();
    private ObservableList<Project> ProjectList = FXCollections.observableArrayList();
   
    public void initialize() {
        
        updatePendingTaskStatus();
        FetchProjectData();
        fetchDataFromDatabase1();
        fetchDataFromDatabase2();
        addDeleteButtonToTable(F_Delete);

        Scene scene = new Scene(new VBox()); // Create a new Scene object
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        setupTableView();
        SearchBar();
        populateDepartmentComboBox();
        populateDepartmentComboBox();
        DEPARTMENT_COMBO.setOnAction(event -> filterEmployeesByDepartment());
        setProjectTableView();
        transferOverdueTasks();
        updateDatabaseForOverdueTasks(MissingList);
        updateDatabaseForOverdueTasks(MissingList);
        calculateEmployeeProgress(0);
      
   
   

        setEmployeeTableView();

        if (P_Delete != null) {
            addDeleteButtonToTable(P_Delete); // Add Delete button for Pending Table
        } else {
            System.err.println("P_Delete column is not initialized. Please check the FXML file.");
        }

        if (F_Delete != null) {
            addOkButtonToTable(F_Delete); // Add OK button for Finished Table
        } else {
            System.err.println("F_Delete column is not initialized. Please check the FXML file.");
        }

        if (M_Delete != null) {
            addOkButtonToTable(F_Delete); // Add OK button for Missing Table
        } else {
            System.err.println("M_Delete column is not initialized. Please check the FXML file.");
        }

        // Avoid redundant error messages for pendingTable
        if (pendingTable != null) {
            fetchDataFromDatabase();
        } else {
            System.err.println("pendingTable is not initialized. Please check the FXML file.");
        }

        if (MiissingTable != null) {
            fetchDataFromDatabase2();
        } else {
            System.err.println("MiissingTable is not initialized. Please check the FXML file.");
        }

        if (EMPLOYEE_TABLE != null) {
            FetchEmployeeData();
            setEmployeeTableView();
        } else {
            System.err.println("EMPLOYEE_TABLE is not initialized. Please check the FXML file.");
        }

        if (P_File != null) {
            addFileViewButtonToTable();
        } else {
            System.err.println("P_File column is not initialized. Please check the FXML file.");
        }

         WebEngine webEngine = webview.getEngine();
         webEngine.load("https://www.dilg.gov.ph");

         WebEngine webEngine1 = webview1.getEngine();
         String widgetHtml = "<html><body><h1><!--Dayspedia.com widget--><iframe width='252' height='197' style='padding:0!important;margin:0!important;border:none!important;background:none!important;background:transparent!important' marginheight='0' marginwidth='0' frameborder='0' scrolling='no' comment='/*defined*/' src='https://dayspedia.com/if/digit/?v=1&iframe=eyJ3LTEyIjp0cnVlLCJ3LTExIjp0cnVlLCJ3LTEzIjp0cnVlLCJ3LTE0IjpmYWxzZSwidy0xNSI6ZmFsc2UsInctMTEwIjp0cnVlLCJ3LXdpZHRoLTAiOnRydWUsInctd2lkdGgtMSI6ZmFsc2UsInctd2lkdGgtMiI6ZmFsc2UsInctMTYiOiIyNHB4Iiwidy0xOSI6IjQ4Iiwidy0xNyI6IjE2Iiwidy0yMSI6dHJ1ZSwiYmdpbWFnZSI6MTMsImJnaW1hZ2VTZXQiOnRydWUsInctMjFjMCI6IiMwMDNkNzUiLCJ3LTAiOnRydWUsInctMyI6dHJ1ZSwidy0zYzAiOiIjMzQzNDM0Iiwidy0zYjAiOiIxIiwidy02IjoiI2VkZWRlZCIsInctMjAiOnRydWUsInctNCI6IiMwMDdkYmYiLCJ3LTE4IjpmYWxzZSwidy13aWR0aC0yYy0wIjoiMzAwIiwidy0xMTUiOnRydWV9&lang=en&cityid=7614'></iframe><!--Dayspedia.com widget ENDS--></h1></body></html>";
         webEngine1.loadContent(widgetHtml);
    }

   

    private void addOkButtonToTable(TableColumn<Task, Void> f_Delete2) {
        f_Delete2.setCellFactory(new Callback<TableColumn<Task, Void>, TableCell<Task, Void>>() {
            @Override
            public TableCell<Task, Void> call(TableColumn<Task, Void> param) {
                return new TableCell<Task, Void>() {
                    private final Button okButton = new Button("OK");
                    private final HBox container = new HBox(okButton);

                    {
                        okButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
                        container.setAlignment(Pos.CENTER);
                        okButton.setOnAction(event -> {
                            Task task = getTableView().getItems().get(getIndex());
                            Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to confirm this task?");
                            alert.setHeaderText(null);
                            alert.showAndWait().ifPresent(response -> {
                                if (response == ButtonType.OK) {
                                    confirmTaskInDatabase(task.getTitle());
                                    getTableView().getItems().remove(task);
                                }
                            });
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(container);
                        }
                    }

                    private void confirmTaskInDatabase(String title) {
                        String query = "UPDATE finisheddb SET Status = 'Confirmed' WHERE Title = ?";

                        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
                             PreparedStatement stmt = conn.prepareStatement(query)) {

                            stmt.setString(1, title);
                            stmt.executeUpdate();
                            showAlert("Success", "Task confirmed successfully");

                        } catch (SQLException e) {
                            e.printStackTrace();
                            showAlert("Error", "Failed to confirm task in the database");
                        }
                    }
                };
            }
        });
    }



    private void fetchDataFromDatabase() {
        String query = "SELECT * FROM taskdb";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                pendingList.add(new Task(
                    rs.getString("Title"),
                    rs.getString("Instruction"),
                    rs.getString("AssignedTo"),
                    rs.getString("For"),
                    rs.getDate("Due"),
                    rs.getString("File"),
                    rs.getString("Status")
                   
                    
                    
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void setupTableView() {
        P_Title.setCellValueFactory(new PropertyValueFactory<>("Title"));
        P_Instruction.setCellValueFactory(new PropertyValueFactory<>("Instruction"));
        P_AssignedTo.setCellValueFactory(new PropertyValueFactory<>("AssignedTo"));
        P_For.setCellValueFactory(new PropertyValueFactory<>("forField"));
        P_Due.setCellValueFactory(new PropertyValueFactory<>("Due"));
        P_File.setCellValueFactory(new PropertyValueFactory<>("File"));
        P_Status.setCellValueFactory(new PropertyValueFactory<>("Status"));
        P_Delete.setCellValueFactory(new PropertyValueFactory<>("Delete"));
    
        pendingTable.setItems(pendingList);
    }
    public class Task {
        private String title;
        private String instruction;
        private String assignedTo;
        private String forField;
        private Date due;
        private String file;
        private String status;
        private String delete;
        private String id;
    

    public Task (String title, String instruction, String assignedTo, String forField, Date due, String file, String status) {
        this.title = title;
        this.instruction = instruction;
        this.assignedTo = assignedTo;
        this.forField = forField;
        this.due = due;
        this.file = file;
        this.status = status;
    }

  

        // Getters and setters for all fields
        
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public String getInstruction() { return instruction; }
        public void setInstruction(String instruction) { this.instruction = instruction; }

        public String getAssignedTo() { return assignedTo; }
        public void setAssignedTo(String assignedTo) { this.assignedTo = assignedTo; }

        public String getForField() { return forField; }
        public void setForField(String forField) { this.forField = forField; }

        public Date getDue() { return due; }
        public void setDue(Date due) { this.due = due; }

        public String getFile() { return file; }
        public void setFile(String file) { this.file = file; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public String getDelete() {
            return delete;
        }


        public void setDelete(String delete) {
            this.delete = delete;
        }
        
        
        
                public String getId() {
                    return id;
                }
            }
        
            public class Project {
                private String projectName;
                private String id;
        
                public Project(String projectName, String id) {
                    this.projectName = projectName;
                    this.id = id;
                }
        
                public String getId() {
                    return id;
                }
        
                public void setId(String id) {
                    this.id = id;
                }
                
                public String getProjectName() {
                    return projectName;
                }
        
                public void setProjectName(String projectName) {
                    this.projectName = projectName;
                }
            }
        //==============================================================================================================/
        private void fetchDataFromDatabase1() {
            String query = "SELECT * FROM finisheddb";
            finishedList.clear(); // Clear the list before adding new data
        
            try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
        
                while (rs.next()) {
                    finishedList.add(new Task(
                        rs.getString("Title"),
                        rs.getString("Instruction"),
                        rs.getString("AssignedTo"),
                        rs.getString("For"),
                        rs.getDate("Due"),
                        rs.getString("File"),
                        rs.getString("Status")
                   
                       // rs.getString("Delete")
                        
                    ));
                }
        
                F_Title.setCellValueFactory(new PropertyValueFactory<>("Title"));
                F_Instruction.setCellValueFactory(new PropertyValueFactory<>("Instruction"));
                F_AssignedTo.setCellValueFactory(new PropertyValueFactory<>("AssignedTo"));
                F_For.setCellValueFactory(new PropertyValueFactory<>("forField"));
                F_Due.setCellValueFactory(new PropertyValueFactory<>("Due"));
                F_File.setCellValueFactory(new PropertyValueFactory<>("File"));
                F_Status.setCellValueFactory(new PropertyValueFactory<>("Status"));
              
                F_Delete.setCellValueFactory(new PropertyValueFactory<>("Delete"));
                
                finishedTable.setItems(finishedList);
        
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
            private void fetchDataFromDatabase2() {
                String query = "SELECT * FROM missingdb";
        
                try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
                     Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery(query)) {
        
                    while (rs.next()) {
                        MissingList.add(new Task(
                            rs.getString("Title"),
                            rs.getString("Instruction"),
                            rs.getString("AssignedTo"),
                            rs.getString("For"),
                            rs.getDate("Due"),
                            rs.getString("File"),
                            rs.getString("Status")
                          //  rs.getString("Delete")
                        ));
        
                    }
                        M_Title.setCellValueFactory(new PropertyValueFactory<>("Title"));
                        M_Instruction.setCellValueFactory(new PropertyValueFactory<>("Instruction"));
                        M_AssignedTo.setCellValueFactory(new PropertyValueFactory<>("AssignedTo"));
                        M_For.setCellValueFactory(new PropertyValueFactory<>("forField"));
                        M_Due.setCellValueFactory(new PropertyValueFactory<>("Due"));
                        M_File.setCellValueFactory(new PropertyValueFactory<>("File"));
                        M_Status.setCellValueFactory(new PropertyValueFactory<>("Status"));
                        M_Delete.setCellValueFactory(new PropertyValueFactory<>("Delete"));
                
                        MiissingTable.setItems(MissingList);
        
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
              
            @FXML
            void BTN_LOGOUT(MouseEvent event) {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Logout Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to logout?");

                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        clearUserSession();

                        // Redirect to login screen
                        try {
                            App.setRoot("Login", 1300, 810);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
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
            void btnCREATETASK(MouseEvent event) throws IOException {
        
                App.setRoot("ASSIGNED_TASK",  1300, 810);
                
            }
                
            @FXML
            void btnHOME(MouseEvent event) throws IOException {
                App.setRoot("DASHBOARD",  1300, 810);
            }
        
            @FXML
            void btnPERFORAMANCE(MouseEvent event) throws IOException {
        
                App.setRoot("PERFORMANCE",  1300, 810);

        
            }
        
            @FXML
            void btnPRINT(MouseEvent event) {
        
            }
        
        ///======================================= BUTTON=======================================//
        
               @FXML
                void BTN_PROJECTADD(MouseEvent event) throws IOException {
                Stage stage = new Stage();
                stage.setTitle("Add Project");
            
                VBox vbox = new VBox(10);
                vbox.setPadding(new Insets(10));
            
                Label label1 = new Label("Project Name");
                TextField textfield = new TextField();
                Button button = new Button("Add");
            
                vbox.getChildren().addAll(label1, textfield, button);
            
                Scene scene = new Scene(vbox, 300, 200);
                stage.setScene(scene);
                stage.show();
            
                button.setOnAction(e -> {
                    String projectName = textfield.getText();
                    if (projectName.isEmpty()) {
                        showAlert("Project Name cannot be empty", projectName);
                        return;
                    }
                    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
                    PreparedStatement stmt = conn.prepareStatement("INSERT INTO projectname (ProjectTitle) VALUES (?)")) {
                                  
                                  
                     stmt.setString(1, projectName);
                     stmt.executeUpdate();
                     showAlert(" Project added successfully", projectName);
                     textfield.setText("");
                 } catch (SQLException ex) {
                     showAlert("Error: " + ex.getMessage(), projectName);
                 }
             });
     }
            
//=======================================DELETE BUTTO TO THE TABLE=======================================//

private void addDeleteButtonToTable(TableColumn<Task, Void> column) {
    if (column == null || column != P_Delete) { // Ensure it only applies to the pending table's delete column
            return;
    }
    column.setCellFactory(new Callback<TableColumn<Task, Void>, TableCell<Task, Void>>() {
        @Override
        public TableCell<Task, Void> call(TableColumn<Task, Void> param) {
            return new TableCell<Task, Void>() {
                private final Button deleteButton = new Button("Delete");
                private final Button okButton = new Button("OK");
                private final HBox container = new HBox(10, deleteButton, okButton); // Add spacing between buttons

                {
                    // Style and align buttons
                    deleteButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
                    okButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
                    container.setAlignment(Pos.CENTER);

                    // Set action for OK button
                    okButton.setOnAction(event -> {
                        Task task = getTableView().getItems().get(getIndex());
                        Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to confirm this task?");
                        alert.setHeaderText(null);
                        alert.showAndWait().ifPresent(response -> {
                            if (response == ButtonType.OK) {
                                confirmTaskInDatabase(task.getTitle());
                                getTableView().getItems().remove(task);
                            }
                        });
                    });

                    // Set action for Delete button
                    deleteButton.setOnAction(event -> {
                        Task task = getTableView().getItems().get(getIndex());
                        Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to delete this task?");
                        alert.setHeaderText(null);
                        alert.showAndWait().ifPresent(response -> {
                            if (response == ButtonType.OK) {
                                deleteTaskFromDatabase(task.getTitle());
                                getTableView().getItems().remove(task);
                            }
                        });
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(container);
                    }
                }
                private void confirmTaskInDatabase(String taskId) {
                    String query = "UPDATE taskdb SET Status = 'Confirmed' WHERE Title = ?";

                    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
                         PreparedStatement stmt = conn.prepareStatement(query)) {

                        stmt.setString(1, taskId);
                        stmt.executeUpdate();
                        showAlert("Success", "Task confirmed successfully");

                    } catch (SQLException e) {
                        e.printStackTrace();
                        showAlert("Error", "Failed to confirm task in the database");
                    }
                }
            };
        }
    });
}

private void deleteTaskFromDatabase(String taskId) {
    String query = "DELETE FROM taskdb WHERE Title = ?";

    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setString(1, taskId);
        stmt.executeUpdate();
        showAlert("Success", "Task deleted successfully");

    } catch (SQLException e) {
        e.printStackTrace();
       
    }
}

public void showAlert(String title, String message) {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}

/*****************************************EMPLOYEE TAB****************************************************/

private void deleteEmployeeFromDatabase(int employeeId) {
    String query = "DELETE FROM employeedb WHERE EmployeeID = ?";

    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setInt(1, employeeId);
        stmt.executeUpdate();
        showAlert("Success", "Employee deleted successfully");

    } catch (SQLException e) {
        e.printStackTrace();
       
    }
}

private void FetchEmployeeData() {
    String query = "SELECT * FROM employeedb";
    EmployeeList.clear(); // Clear the list before adding new data

    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(query)) {

        while (rs.next()) {
            EmployeeList.add(new Employee(
                rs.getInt("EmployeeID"),
                rs.getString("FullName"),
                rs.getString("LastName"),
                rs.getString("Email"),
                rs.getString("Department"),
                rs.getString("Position"),
                rs.getString("Status")
            ));
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

private String getEmployeeRemark(double progress) {
    if (progress >= 80) {
        return "Excellent";
    } else if (progress >= 60) {
        return "Good";
    } else if (progress >= 40) {
        return "Average";
    } else {
        return "Poor";
    }
}

private void setEmployeeTableView() {
    E_ID.setCellValueFactory(new PropertyValueFactory<>("id"));
    E_FIRSTNAME.setCellValueFactory(new PropertyValueFactory<>("fullName"));
    E_LASTNAME.setCellValueFactory(new PropertyValueFactory<>("lastName"));
    E_EMAIL.setCellValueFactory(new PropertyValueFactory<>("email"));
    E_DEPARTMENT.setCellValueFactory(new PropertyValueFactory<>("department"));
    E_POSITION.setCellValueFactory(new PropertyValueFactory<>("position"));
    E_STATUS.setCellValueFactory(new PropertyValueFactory<>("status"));

    E_DELETE.setCellFactory(new Callback<TableColumn<Employee, Void>, TableCell<Employee, Void>>() {
        @Override
        public TableCell<Employee, Void> call(TableColumn<Employee, Void> param) {
            return new TableCell<Employee, Void>() {
                private final Button deleteButton = new Button("Delete");
                private final HBox container = new HBox(deleteButton);

                {
                    deleteButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
                    container.setAlignment(Pos.CENTER);
                    deleteButton.setOnAction(event -> {
                        Employee employee = getTableView().getItems().get(getIndex());
                        deleteEmployeeFromDatabase(employee.getId());
                        getTableView().getItems().remove(employee);
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(container);
                    }
                }
            };
        }
    });

    PROGRESS.setCellFactory(new Callback<TableColumn<Employee, String>, TableCell<Employee, String>>() {
        @Override
        public TableCell<Employee, String> call(TableColumn<Employee, String> param) {
            return new TableCell<Employee, String>() {
                private final ProgressIndicator progressIndicator = new ProgressIndicator();
                private final HBox container = new HBox(progressIndicator);

                {
                    container.setAlignment(Pos.CENTER);
                    progressIndicator.setPrefSize(50, 50); // Set the size of the ProgressIndicator
                }

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        Employee employee = getTableView().getItems().get(getIndex());
                        double progress = calculateEmployeeProgress(employee.getId());
                        progressIndicator.setProgress(progress / 100);
                        setGraphic(container);
                    }
                }
            };
        }
    });

    EDIT.setCellFactory(new Callback<TableColumn<Employee, String>, TableCell<Employee, String>>() {
        @Override
        public TableCell<Employee, String> call(TableColumn<Employee, String> param) {
            return new TableCell<Employee, String>() {
                private final Button editButton = new Button("Edit");
                private final HBox container = new HBox(editButton);

                {
                    editButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
                    container.setAlignment(Pos.CENTER);
                    editButton.setOnAction(event -> {
                        Employee employee = getTableView().getItems().get(getIndex());
                        showEditEmployeeDialog(employee);
                    });
                }

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(container);
                    }
                }
            };
        }
    });

    EMPLOYEE_TABLE.setItems(EmployeeList);
}

private void showEditEmployeeDialog(Employee employee) {
    Stage stage = new Stage();
    stage.setTitle("Edit Employee");

    VBox vbox = new VBox(10);
    vbox.setPadding(new Insets(10));

    TextField firstNameField = new TextField(employee.getFirstName());
    TextField lastNameField = new TextField(employee.getLastName());
    TextField emailField = new TextField(employee.getEmail());
    TextField departmentField = new TextField(employee.getDepartment());
    TextField positionField = new TextField(employee.getPosition());
    TextField statusField = new TextField(employee.getStatus());
    Label remarkLabel = new Label("Remark: " + getEmployeeRemark(calculateEmployeeProgress(employee.getId())));
    Button saveButton = new Button("Save");

    vbox.getChildren().addAll(
        new Label("First Name"), firstNameField,
        new Label("Last Name"), lastNameField,
        new Label("Email"), emailField,
        new Label("Department"), departmentField,
        new Label("Position"), positionField,
        new Label("Status"), statusField,
        remarkLabel,
        saveButton
    );

    Scene scene = new Scene(vbox, 300, 500);
    stage.setScene(scene);
    stage.show();

    saveButton.setOnAction(e -> {
        employee.setFirstName(firstNameField.getText());
        employee.setLastName(lastNameField.getText());
        employee.setEmail(emailField.getText());
        employee.setDepartment(departmentField.getText());
        employee.setPosition(positionField.getText());
        employee.setStatus(statusField.getText());
        updateEmployeeInDatabase(employee);
        EMPLOYEE_TABLE.refresh();
        stage.close();
    });
}

private void updateEmployeeInDatabase(Employee employee) {
    String query = "UPDATE employeedb SET FullName = ?, LastName = ?, Email = ?, Department = ?, Position = ?, Status = ? WHERE EmployeeID = ?";

    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setString(1, employee.getFirstName());
        stmt.setString(2, employee.getLastName());
        stmt.setString(3, employee.getEmail());
        stmt.setString(4, employee.getDepartment());
        stmt.setString(5, employee.getPosition());
        stmt.setString(6, employee.getStatus());
        stmt.setInt(7, employee.getId());
        stmt.executeUpdate();
        showAlert("Success", "Employee updated successfully");

    } catch (SQLException e) {
        e.printStackTrace();
        showAlert("Error", "Failed to update employee in the database");
    }
}

private double calculateEmployeeProgress(int employeeId) {
    String totalTasksQuery = "SELECT COUNT(*) AS totalTasks FROM taskdb WHERE AssignedTo = ?";
    String finishedTasksQuery = "SELECT COUNT(*) AS finishedTasks FROM finisheddb WHERE AssignedTo = ?";
    int totalTasks = 4;
    int finishedTasks = 4;

    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
         PreparedStatement totalStmt = conn.prepareStatement(totalTasksQuery);
         PreparedStatement finishedStmt = conn.prepareStatement(finishedTasksQuery)) {

        totalStmt.setInt(1, employeeId);
        ResultSet totalRs = totalStmt.executeQuery();
        if (totalRs.next()) {
            totalTasks = totalRs.getInt("totalTasks");
        }

        finishedStmt.setInt(1, employeeId);
        ResultSet finishedRs = finishedStmt.executeQuery();
        if (finishedRs.next()) {
            finishedTasks = finishedRs.getInt("finishedTasks");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return totalTasks > 10 ? (double) finishedTasks / totalTasks * 100 : 0;   
}

@FXML
void BTN_ADDEMPLOYEE(MouseEvent event) throws IOException {
    // Add employee logic here

    App.setRoot("ADD_EMPLOYEE",  1300, 810);
}

public class Employee {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String department;
    private String position;
    private String status;

    public Employee(int id, String firstName, String lastName, String email, String department, String position, String status) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.department = department;
        this.position = position;
        this.status = status;
    }

    // Getters and setters for all fields
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
    
    // =========popoulate the department combo bo in employee tab/
    private void populateDepartmentComboBox() {
       String query = "SELECT Department FROM employeedb";
       try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query)) {
    
           while (rs.next()) {
               String department = rs.getString("Department");
               if (!DEPARTMENT_COMBO.getItems().contains(department)) {
                   DEPARTMENT_COMBO.getItems().add(department);
               }
           }
    
       } catch (SQLException e) {
           e.printStackTrace();
       }
       if (DEPARTMENT_COMBO.getItems().isEmpty()) {
           // Add sample data for departments
           DEPARTMENT_COMBO.getItems().addAll("HR", "Finance", "IT", "Marketing", "Operations");
       }
    }
    
private void filterEmployeesByDepartment() {
    String selectedDepartment = DEPARTMENT_COMBO.getValue();
    if (selectedDepartment != null) {
        ObservableList<Employee> filteredList = FXCollections.observableArrayList();
        for (Employee employee : EmployeeList) {
            if (selectedDepartment.equals(employee.getDepartment())) {
                filteredList.add(employee);
            }
        }
        EMPLOYEE_TABLE.setItems(filteredList);
    }
}

//============================================PROJECT TAB=======================================//

private void FetchProjectData() {
    String query = "SELECT * FROM projectname";

    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(query)) {

        while (rs.next()) {
            ProjectList.add(new Project(
        rs.getString("ProjectTitle"),
        rs.getString("id")
            ));
        }
        TABLE_PROJECT.setItems(ProjectList);

} catch (SQLException e) {
    e.printStackTrace();
}

PR_NO.setCellValueFactory(new PropertyValueFactory<>("id"));
PR_TABLE.setCellValueFactory(new PropertyValueFactory<>("projectName"));
}

private void setProjectTableView() {
    PR_NO.setCellValueFactory(new PropertyValueFactory<>("id"));
    PR_TABLE.setCellValueFactory(new PropertyValueFactory<>("projectName"));
    PR_PROGRESS.setCellFactory(new Callback<TableColumn<Project, Double>, TableCell<Project, Double>>() {
        @Override
        public TableCell<Project, Double> call(TableColumn<Project, Double> param) {
            return new TableCell<Project, Double>() {
                private final ProgressIndicator progressIndicator = new ProgressIndicator();
                private final HBox container = new HBox(progressIndicator);
                
                {
                    container.setAlignment(Pos.CENTER);
                    progressIndicator.setPrefSize(50, 50); // Set the size of the ProgressIndicator
                }

                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        Project project = getTableView().getItems().get(getIndex());
                        double progress = calculateProjectProgress(project.getId());
                        progressIndicator.setProgress(progress);
                        setGraphic(container);
                    }
                }
            };
        }
    });

    PRaction.setCellFactory(new Callback<TableColumn<Project, String>, TableCell<Project, String>>() {
        @Override
        public TableCell<Project, String> call(TableColumn<Project, String> param) {
            return new TableCell<Project, String>() {
                private final Button deleteButton = new Button("Delete");
                private final HBox container = new HBox(deleteButton);

                {
                    deleteButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
                    container.setAlignment(Pos.CENTER);
                    deleteButton.setOnAction(event -> {
                        Project project = getTableView().getItems().get(getIndex());
                        deleteProjectFromDatabase(project.getId());
                        getTableView().getItems().remove(project);
                    });
                }

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(container);
                    }
                }
            };
        }
    });

    TABLE_PROJECT.setItems(ProjectList);
}

private void deleteProjectFromDatabase(String projectId) {
    String query = "DELETE FROM projectname WHERE id = ?";

    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setString(1, projectId);
        stmt.executeUpdate();
        showAlert("Success", "Project deleted successfully");

    } catch (SQLException e) {
        e.printStackTrace();
        showAlert("Error", "Failed to delete project from the database");
    }
}



private double calculateProjectProgress(String projectId) {
    String totalTasksQuery = "SELECT COUNT(*) AS totalTasks FROM finisheddb WHERE Title = ?";
    String finishedTasksQuery = "SELECT COUNT(*) AS finishedTasks FROM finisheddb WHERE Status = ?";
    int totalTasks = 0;
    int finishedTasks = 0;

    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
         PreparedStatement totalStmt = conn.prepareStatement(totalTasksQuery);
         PreparedStatement finishedStmt = conn.prepareStatement(finishedTasksQuery)) {
          
        totalStmt.setString(1, projectId);
        ResultSet totalRs = totalStmt.executeQuery();
        if (totalRs.next()) {
            totalTasks = totalRs.getInt("totalTasks");
        }

        finishedStmt.setString(1, projectId);
        ResultSet finishedRs = finishedStmt.executeQuery();
        if (finishedRs.next()) {
            finishedTasks = finishedRs.getInt("finishedTasks");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return totalTasks > 10 ? (double) finishedTasks / totalTasks : 10;
}

//============================================ADDING DUE DATE LESTINER=======================================/

private void transferOverdueTasks() {
    ObservableList<Task> overdueTasks = FXCollections.observableArrayList();
    Date now = new Date();

    for (Task task : pendingList) {
        if (task.getDue().before(now)) {
            overdueTasks.add(task);
        }
    }

    pendingList.removeAll(overdueTasks);
    MissingList.addAll(overdueTasks);
    updateDatabaseForOverdueTasks(overdueTasks);
}

private void updateDatabaseForOverdueTasks(ObservableList<Task> overdueTasks) {
    String deleteQuery = "DELETE FROM taskdb WHERE Title = ?";
    String insertQuery = "INSERT INTO missingdb (Title, Instruction, For, AssignedTo, Due, File, Status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
         PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery);
         PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {

        for (Task task : overdueTasks) {
            
            deleteStmt.executeUpdate();

            insertStmt.setString(1, task.getTitle());
            insertStmt.setString(2, task.getInstruction());
            insertStmt.setString(3, task.getForField());
            insertStmt.setString(4, task.getAssignedTo());
            insertStmt.setDate(5, new java.sql.Date(task.getDue().getTime()));
            insertStmt.setString(6, task.getFile());
            insertStmt.setString(7, task.getStatus());
            insertStmt.executeUpdate();
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
///////////////////////////////////////////////////////////////////////////////////////////////////////////
  
public void SearchBar(){
    SEARCH_BAR.textProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue.isEmpty()) {
            pendingTable.setItems(pendingList);
            finishedTable.setItems(finishedList);
            MiissingTable.setItems(MissingList);
        } else {
            ObservableList<Task> filteredPendingList = FXCollections.observableArrayList();
            ObservableList<Task> filteredFinishedList = FXCollections.observableArrayList();
            ObservableList<Task> filteredMissingList = FXCollections.observableArrayList();
            for (Task task : pendingList) {
                if (task.getTitle().toLowerCase().contains(newValue.toLowerCase())) {
                    filteredPendingList.add(task);
                }
            }
            for (Task task : finishedList) {
                if (task.getTitle().toLowerCase().contains(newValue.toLowerCase())) {
                    filteredFinishedList.add(task);
                }
            }
            for (Task task : MissingList) {
                if (task.getTitle().toLowerCase().contains(newValue.toLowerCase())) {
                    filteredMissingList.add(task);
                }
            }
            pendingTable.setItems(filteredPendingList);
            finishedTable.setItems(filteredFinishedList);
            MiissingTable.setItems(filteredMissingList);
        }
    });
}

private void addFileViewButtonToTable() {
    Callback<TableColumn<Task, String>, TableCell<Task, String>> cellFactory = new Callback<TableColumn<Task, String>, TableCell<Task, String>>() {
        @Override
        public TableCell<Task, String> call(TableColumn<Task, String> param) {
            return new TableCell<Task, String>() {
                private final Button viewButton = new Button("View File");
                private final HBox container = new HBox(viewButton);

                {
                    container.setAlignment(Pos.CENTER);
                    viewButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
                    viewButton.setOnAction(event -> {
                        Task task = getTableView().getItems().get(getIndex());
                        viewFileFromDatabase(task.getFile());
                    });
                }

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(container);
                    }
                }
            };
        }
    };

    P_File.setCellFactory(cellFactory);
    F_File.setCellFactory(cellFactory);
    M_File.setCellFactory(cellFactory);
}


//============================================RETRIEVE FILE FROM DATABASE=======================================//`


private void viewFileFromDatabase(String fileName) {
    String query = "SELECT FileData FROM taskdb WHERE File = ?";

    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setString(1, fileName);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            InputStream inputStream = rs.getBinaryStream("FileData");
            String fileExtension = getFileExtension(fileName);
            File tempFile = File.createTempFile("temp", fileExtension);
            try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
            openFileWithDefaultApplication(tempFile);
        } else {
            showAlert("Error", "File not found in the database");
        }

    } catch (SQLException | IOException e) {
        e.printStackTrace();
        showAlert("Error", "Failed to retrieve file from the database");
    }
}


private void openFileWithDefaultApplication(File file) {
    try {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.OPEN)) {
                desktop.open(file);
            } else {
                showAlert("Error", "Open action is not supported on your system");
            }
        } else {
            showAlert("Error", "Desktop is not supported on your system");
        }
    } catch (IOException e) {
        e.printStackTrace();
        showAlert("Error", "No application is associated with the specified file for this operation");
    }
}
public class FileUtils {
    public void deleteFilesInDirectory(String directoryPath) {
            File directory = new File(directoryPath);
            if (directory.exists() && directory.isDirectory()) {
                File[] files = directory.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.isFile()) {
                            file.delete();
                        }
                    }
                }
            }
        }
    }
    
    @FXML
    private Button BTN_CHANGE_WEBVIEW;

@FXML
void BTN_SETTINGS(MouseEvent event) {
    Stage stage = new Stage();
    stage.setTitle("Settings");

    VBox vbox = new VBox(10);
    vbox.setPadding(new Insets(10));

    Label dbLabel = new Label("Database URL:");
    TextField dbField = new TextField(DB_URL);

    Label userLabel = new Label("Database User:");
    TextField userField = new TextField(USER);

    Label passwordLabel = new Label("Database Password:");
    PasswordField passwordField = new PasswordField();
    passwordField.setText(PASSWORD);

    Label webviewLabel = new Label("WebView URL:");
    TextField webviewField = new TextField(webview.getEngine().getLocation());

    Button saveButton = new Button("Save");
    saveButton.setOnAction(e -> {
        String newDbUrl = dbField.getText();
        String newUser = userField.getText();
        String newPassword = passwordField.getText();
        String newWebviewUrl = webviewField.getText();

        // Update WebView URL
        webview.getEngine().load(newWebviewUrl);

        System.out.println("Settings updated:");
        System.out.println("DB URL: " + newDbUrl);
        System.out.println("User: " + newUser);
        System.out.println("Password: " + newPassword);
        System.out.println("WebView URL: " + newWebviewUrl);

        stage.close();
    });

    vbox.getChildren().addAll(dbLabel, dbField, userLabel, userField, passwordLabel, passwordField, webviewLabel, webviewField, saveButton);

    Scene scene = new Scene(vbox, 400, 400);
    stage.setScene(scene);
    stage.show();
}
   //https://www.dilg.gov.ph/
//============================== update the pending task status Column in the admin  when the task is already in the finisheddb=====================================================//
   
public void updatePendingTaskStatus() {
    String query = "UPDATE taskdb SET Status = 'Completed' WHERE Title IN (SELECT Title FROM finisheddb)";

    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.executeUpdate();
       

    } catch (SQLException e) {
        e.printStackTrace();
        showAlert("Error", "Failed to update pending tasks in the database");
    }
}
    /*private void updateFinishedTaskStatus() {
        String query = "UPDATE finisheddb SET Status = 'Completed' WHERE Title IN (SELECT Title FROM taskdb)";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.executeUpdate();
            showAlert("Success", "Finished tasks updated successfully");

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to update finished tasks in the database");
        }
    }*/
private String getFileExtension(String fileName) {
    int lastIndexOfDot = fileName.lastIndexOf('.');
    if (lastIndexOfDot == -1) {
        return ""; // No extension found
    }
    return fileName.substring(lastIndexOfDot);
}
}

