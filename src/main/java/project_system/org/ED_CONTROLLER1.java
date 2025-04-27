package project_system.org;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.time.LocalDate;



import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import java.awt.Desktop;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.print.PrinterJob;

public class ED_CONTROLLER1 extends Method {

    @FXML
    private TableColumn<Task, String> F_AssignedTo, F_Delete, F_File, F_For, F_ID, F_Instruction, F_Status, F_Title, M_Add, M_AssignedTo, M_File, M_For, M_ID, M_Instruction, M_Status, M_Title, M_done, P_Add, P_AssignedTo, P_Done, P_Filee, P_For, P_ID, P_Instruction, P_Status, P_Title;

    @FXML
    private TableColumn<Task, Integer> F_Due, M_Due, P_Due;

    @FXML
    private Label Goodmorninggreet, dategreet;

    @FXML
    private ImageView LOGOUT, btnCalendar, btnHOME, btnPERFORAMNCE;

    @FXML
    private Tab TP_FINISHEDTASK, TP_MISSINGTASK, TP_Pendings;

    @FXML
    private ImageView btnPrint;

    @FXML
    private WebView webview2;

    @FXML
    private WebView webview3;

    @FXML
    private TableView<Task> MiissingTable, finishedTable, pendingTable;

    private final String DB_URL = "jdbc:mysql://127.0.0.1:3306/pomsdb?serverTimezone=UTC";
    private final String USER = "root";
    private final String PASSWORD = "luese_192003";

    private ObservableList<Task> pendingList = FXCollections.observableArrayList();
    private ObservableList<Task> finishedList = FXCollections.observableArrayList();
    private ObservableList<Task> missingList = FXCollections.observableArrayList();

    public void initialize() {
        fetchPending();
        fetchFinished();
        fetchMssing();
        setupTableView();
       
        
        addFileViewButtonToTable(F_File);
        addFileViewButtonToTable(M_File);
        addFileViewButtonToTable(P_Filee);
        addMarkAsDoneTable(F_Delete);
        addMarkAsDoneTable(M_done);
        addSubmitButtonToTable(P_Add);
        addSubmitButtonToTable(M_Add);
        
        

        if (P_Title != null) {
            P_Title.setCellValueFactory(new PropertyValueFactory<>("Title"));
        }
        if (P_Instruction != null) {
            P_Instruction.setCellValueFactory(new PropertyValueFactory<>("Instruction"));
        }
        if (P_For != null) {
            P_For.setCellValueFactory(new PropertyValueFactory<>("For"));
        }
        if (P_AssignedTo != null) {
            P_AssignedTo.setCellValueFactory(new PropertyValueFactory<>("AssignedTo"));
        }
        if (P_Due != null) {
            P_Due.setCellValueFactory(new PropertyValueFactory<>("Due"));
        }
        if (P_Filee != null) {
            P_Filee.setCellValueFactory(new PropertyValueFactory<>("File"));
        }
        if (P_Status != null) {
            P_Status.setCellValueFactory(new PropertyValueFactory<>("Status"));
        }

        
        setupTableView();

       WebEngine webEngine2= webview2.getEngine();
        String widgetHtml = "<html><body><h1><!--Dayspedia.com widget--><iframe width='270' height='197' style='padding:0!important;margin:0!important;border:none!important;background:none!important;background:transparent!important' marginheight='0' marginwidth='0' frameborder='0' scrolling='no' comment='/*defined*/' src='https://dayspedia.com/if/digit/?v=1&iframe=eyJ3LTEyIjp0cnVlLCJ3LTExIjp0cnVlLCJ3LTEzIjp0cnVlLCJ3LTE0IjpmYWxzZSwidy0xNSI6ZmFsc2UsInctMTEwIjp0cnVlLCJ3LXdpZHRoLTAiOnRydWUsInctd2lkdGgtMSI6ZmFsc2UsInctd2lkdGgtMiI6ZmFsc2UsInctMTYiOiIyNHB4Iiwidy0xOSI6IjQ4Iiwidy0xNyI6IjE2Iiwidy0yMSI6dHJ1ZSwiYmdpbWFnZSI6LTEsImJnaW1hZ2VTZXQiOmZhbHNlLCJ3LTIyYzAiOiIjMDAzNjcwIiwidy0wIjp0cnVlLCJ3LTMiOnRydWUsInctM2MwIjoiIzM0MzQzNCIsInctM2IwIjoiMSIsInctNiI6IiNmZmZmZmYiLCJ3LTIwIjp0cnVlLCJ3LTQiOiIjMDA3ZGJmIiwidy0xOCI6ZmFsc2UsInctd2lkdGgtMmMtMCI6IjMwMCIsInctMTE1Ijp0cnVlfQ==&lang=en&cityid=7614'></iframe><!--Dayspedia.com widget ENDS--><iframe width='252' height='197' style='padding:0!important;margin:0!important;border:none!important;background:none!important;background:transparent!important' marginheight='0' marginwidth='0' frameborder='0' scrolling='no' comment='/*defined*/' src='https://dayspedia.com/if/digit/?v=1&iframe=eyJ3LTEyIjp0cnVlLCJ3LTExIjp0cnVlLCJ3LTEzIjp0cnVlLCJ3LTE0IjpmYWxzZSwidy0xNSI6ZmFsc2UsInctMTEwIjp0cnVlLCJ3LXdpZHRoLTAiOnRydWUsInctd2lkdGgtMSI6ZmFsc2UsInctd2lkdGgtMiI6ZmFsc2UsInctMTYiOiIyNHB4Iiwidy0xOSI6IjQ4Iiwidy0xNyI6IjE2Iiwidy0yMSI6dHJ1ZSwiYmdpbWFnZSI6MTMsImJnaW1hZ2VTZXQiOnRydWUsInctMjFjMCI6IiMwMDNkNzUiLCJ3LTAiOnRydWUsInctMyI6dHJ1ZSwidy0zYzAiOiIjMzQzNDM0Iiwidy0zYjAiOiIxIiwidy02IjoiI2VkZWRlZCIsInctMjAiOnRydWUsInctNCI6IiMwMDdkYmYiLCJ3LTE4IjpmYWxzZSwidy13aWR0aC0yYy0wIjoiMzAwIiwidy0xMTUiOnRydWV9&lang=en&cityid=7614'></iframe><!--Dayspedia.com widget ENDS--></h1><p>/p></body></html>";
        webEngine2.loadContent(widgetHtml);

        WebEngine webEngine1 = webview3.getEngine();
        webEngine1.load("https://www.dilg.gov.ph");
    }

    public class Task {
        private String Title, Instruction, Due, AssignedTo, Status, File, For;

        public Task(String Title, String Instruction, String For, String AssignedTo, Date Due, String File, String Status) {
            this.Title = Title;
            this.For = For;
            this.Instruction = Instruction;
            this.AssignedTo = AssignedTo;
            this.Due = Due.toString();
            this.File = File;
            this.Status = Status;
           
        }

        public String getTitle() { return Title; }
        public void setTitle(String Title) { this.Title = Title; }
        public String getInstruction() { return Instruction; }
        public void setInstruction(String Instruction) { this.Instruction = Instruction; }
        public String getDue() { return Due; }
        public void setDue(String Due) { this.Due = Due; }
        public String getAssignedTo() { return AssignedTo; }
        public void setAssignedTo(String AssignedTo) { this.AssignedTo = AssignedTo; }
        public String getStatus() { return Status; }
        public void setStatus(String Status) { this.Status = Status; }
        public String getFile() { return File; }
        public void setFile(String File) { this.File = File; }
        public String getFor() { return For; }
        public void setFor(String For) { this.For = For; }
     
    } 

    public void fetchPending() {
        String query = "SELECT * FROM taskdb WHERE Status = 'Pending'";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                pendingList.add(new Task(
                        rs.getString("Title"),
                        rs.getString("Instruction"),
                        rs.getString("For"),
                        rs.getString("AssignedTo"),
                        rs.getDate("Due"),
                        rs.getString("File"),
                        rs.getString("Status")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        P_Title.setCellValueFactory(new PropertyValueFactory<>("Title"));
        P_Instruction.setCellValueFactory(new PropertyValueFactory<>("Instruction"));
        P_AssignedTo.setCellValueFactory(new PropertyValueFactory<>("AssignedTo"));
        P_For.setCellValueFactory(new PropertyValueFactory<>("For"));
        P_Due.setCellValueFactory(new PropertyValueFactory<>("Due"));
        P_Filee.setCellValueFactory(new PropertyValueFactory<>("File"));
        P_Status.setCellValueFactory(new PropertyValueFactory<>("Status"));
        pendingTable.setItems(pendingList);
    }

    public void fetchFinished() {
        String query = "SELECT * FROM finisheddb";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                finishedList.add(new Task(
                        rs.getString("Title"),
                        rs.getString("Instruction"),
                        rs.getString("For"),
                        rs.getString("AssignedTo"),
                        rs.getDate("Due"),
                        rs.getString("File"),
                        rs.getString("Status")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        F_Title.setCellValueFactory(new PropertyValueFactory<>("Title"));
        F_Instruction.setCellValueFactory(new PropertyValueFactory<>("Instruction"));
        F_For.setCellValueFactory(new PropertyValueFactory<>("For"));
        F_AssignedTo.setCellValueFactory(new PropertyValueFactory<>("AssignedTo"));
        F_Due.setCellValueFactory(new PropertyValueFactory<>("Due"));
        F_File.setCellValueFactory(new PropertyValueFactory<>("File"));
        F_Status.setCellValueFactory(new PropertyValueFactory<>("Status"));
        finishedTable.setItems(finishedList);
    }

    public void fetchMssing() {
        String query = "SELECT * FROM missingdb";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                missingList.add(new Task(
                        rs.getString("Title"),
                        rs.getString("Instruction"),
                        rs.getString("For"),
                        rs.getString("AssignedTo"),
                        rs.getDate("Due"),
                        rs.getString("File"),
                        rs.getString("Status")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        M_Title.setCellValueFactory(new PropertyValueFactory<>("Title"));
        M_Instruction.setCellValueFactory(new PropertyValueFactory<>("Instruction"));
        M_AssignedTo.setCellValueFactory(new PropertyValueFactory<>("AssignedTo"));
        M_For.setCellValueFactory(new PropertyValueFactory<>("For"));
        M_Due.setCellValueFactory(new PropertyValueFactory<>("Due"));
        M_File.setCellValueFactory(new PropertyValueFactory<>("File"));
        M_Status.setCellValueFactory(new PropertyValueFactory<>("Status"));
        MiissingTable.setItems(missingList);
    }

    private void setupTableView() {
        F_Title.setCellValueFactory(new PropertyValueFactory<>("Title"));
        F_Instruction.setCellValueFactory(new PropertyValueFactory<>("Instruction"));
        F_For.setCellValueFactory(new PropertyValueFactory<>("For"));
        F_AssignedTo.setCellValueFactory(new PropertyValueFactory<>("AssignedTo"));
        F_Due.setCellValueFactory(new PropertyValueFactory<>("Due"));
        F_File.setCellValueFactory(new PropertyValueFactory<>("File"));
        F_Status.setCellValueFactory(new PropertyValueFactory<>("Status"));
        finishedTable.setItems(finishedList);
    }

    @FXML
    private File retrieveFileFromDatabase(String fileName) {
        String query = "SELECT File, FileData FROM taskdb WHERE File = ?";
        File file = null;

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, fileName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                InputStream inputStream = rs.getBinaryStream("FileData");

                file = new File("retrieved_" + fileName);
                try (FileOutputStream outputStream = new FileOutputStream(file)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            showAlert("Error", "Error retrieving file: " + e.getMessage());
        }

        return file;
    }

    public void openFile(String filePath) {
            File file = new File(filePath);
            if (file.exists()) {
                try {
                    Desktop.getDesktop().open(file);
                } catch (IOException e) {
                    e.printStackTrace();
                    showAlert("Error", "Error opening file: " + e.getMessage());
                }
            } else {
                showAlert("Error", "File does not exist");
            }
        }

    private void addFileViewButtonToTable(TableColumn<Task, String> column) {
        if (column == null) return;
        column.setCellFactory(new Callback<TableColumn<Task, String>, TableCell<Task, String>>() {
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
                            String fileName = task.getFile();
                            File file = retrieveFileFromDatabase(fileName);
                            if (file != null) {
                                openFile(file.getAbsolutePath());
                            }
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
    }

    private void addMarkAsDoneTable(TableColumn<Task, String> column) {
        if (column == null) return;
        column.setCellFactory(new Callback<TableColumn<Task, String>, TableCell<Task, String>>() {
            @Override
            public TableCell<Task, String> call(TableColumn<Task, String> param) {
                return new TableCell<Task, String>() {
                    private final Button uploadButton = new Button("Mark as Done");
                    private final HBox container = new HBox(uploadButton);

                    {
                        container.setAlignment(Pos.CENTER);
                        uploadButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
                        uploadButton.setOnAction(event -> {
                            Task task = getTableView().getItems().get(getIndex());
                            markTaskAsDone(task);
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
    }

    private void markTaskAsDone(Task task) {
        String deleteQuery = "DELETE FROM taskdb WHERE Title = ? AND AssignedTo = ? AND Due = ? AND `For` = ?";
        String insertQuery = "INSERT INTO finisheddb (Title, Instruction, `For`, AssignedTo, Due, File, Status) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
             PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {

            // Delete from taskdb
            deleteStatement.setString(1, task.getTitle());
            deleteStatement.setString(2, task.getAssignedTo());
            deleteStatement.setString(3, task.getDue());
            deleteStatement.setString(4, task.getFor());
            
            int rowsDeleted = deleteStatement.executeUpdate();

            if (rowsDeleted > 0) {
                // Insert into finisheddb
                insertStatement.setString(1, task.getTitle());
                insertStatement.setString(2, task.getInstruction());
                insertStatement.setString(3, task.getFor());
                insertStatement.setString(4, task.getAssignedTo());
                insertStatement.setString(5, task.getDue());
                insertStatement.setString(6, task.getFile());
                insertStatement.setString(7, task.getStatus()); 
               
                

                int rowsInserted = insertStatement.executeUpdate();

                if (rowsInserted > 0) {
                    pendingTable.getItems().remove(task);
                    finishedTable.getItems().add(task);

                    showAlert("Success", "Task marked as done.");
                } else {
                    showAlert("Error", "An error occurred while marking the task as done.");
                }
            } else {
                showAlert("Error", "An error occurred while marking the task as done.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred while processing the task.");
        }
    }

    private void addSubmitButtonToTable(TableColumn<Task, String> column) {
        if (column == null) return;
        column.setCellFactory(new Callback<TableColumn<Task, String>, TableCell<Task, String>>() {
            @Override
            public TableCell<Task, String> call(TableColumn<Task, String> param) {
                return new TableCell<Task, String>() {
                    private final Button uploadButton = new Button("Upload File");
                    private final HBox container = new HBox(uploadButton);

                    {
                        container.setAlignment(Pos.CENTER);
                        uploadButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
                        uploadButton.setOnAction(event -> {
                            Task task = getTableView().getItems().get(getIndex());
                            FileChooser fileChooser = new FileChooser();
                            File selectedFile = fileChooser.showOpenDialog(null);
                            if (selectedFile != null) {
                                Submit(task, selectedFile);
                            } else {
                                showAlert("Error", "No file selected. Please upload a file.");
                            }
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
    }

    private void Submit(Task task, File selectedFile) {
        if (selectedFile == null) {
            showAlert("Error", "No file selected. Please upload a file.");
            return;
        }

        if (!selectedFile.exists()) {
            showAlert("Error", "Selected file does not exist. Please upload a valid file.");
            return;
        }

        String title = task.getTitle();
        String instruction = task.getInstruction();
        String project = task.getFor();
        String employee = task.getAssignedTo();
        String due = task.getDue();
        String status = "Done";

        String insertquery = "INSERT INTO finisheddb (Title, Instruction, `For`, AssignedTo, Due,  File, FileData, FileType, status, FinishedDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement insertStatement = connection.prepareStatement(insertquery);
             FileInputStream fis = new FileInputStream(selectedFile)) {

            insertStatement.setString(1, title);
            insertStatement.setString(2, instruction);
            insertStatement.setString(3, project);
            insertStatement.setString(4, employee);
            insertStatement.setString(5, due);
            insertStatement.setString(6, selectedFile.getName());
            insertStatement.setBinaryStream(7, fis, (int) selectedFile.length());
            insertStatement.setString(8, getFileType(selectedFile));
            insertStatement.setString(9, status);
            insertStatement.setDate(10, Date.valueOf(LocalDate.now()));
            int rowsAffected = insertStatement.executeUpdate();

            if (rowsAffected > 0) {
                pendingTable.getItems().remove(task); // Remove task from pending table
                showAlert("Success", "Task assigned successfully.");
                //add if the remove the pendinf table from the pending table if the task is successfully marked as done
                finishedTable.getItems().add(task); // Add task to finished table
            } else {
                showAlert("Error", "An error occurred. Please try again.");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            showAlert("Error", "Duplicate entry. The task already exists.");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred. Please try again.");
        }
    }

    private String getFileType(File file) {
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return "application/" + fileName.substring(dotIndex + 1);
        }
        return "application/octet-stream";
    }

    public void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void BTN_LOGOUT(MouseEvent event) {
        Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Logout Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to logout?");
        
        confirmationAlert.showAndWait().ifPresent(response -> {
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
    void btnCalendar(MouseEvent event) throws IOException {
    
            Calendar calendarFrame = new Calendar();
            calendarFrame.setVisible(true); 
 
    }

    @FXML
    void btnHOME(MouseEvent event) throws IOException {
        App.setRoot("E_DASHBOARD",  1300, 810);
    }

    @FXML
    private void btnPERFORMANCE(MouseEvent event) throws IOException {
        // Add your logic here
        System.out.println("Performance button clicked!");
        App.setRoot("employeePerformance", 1300, 810);
    }

    @FXML
    void btn_priint(MouseEvent event) {

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

}