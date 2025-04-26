package project_system.org;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

public class PERFORMANCE_CONTROLLER {

    @FXML
    private Label Goodmorninggreet;
    @FXML
    private ImageView LOGOUT;
    @FXML
    private ComboBox<String> PE_BOX;
    @FXML
    private TableColumn<task, String> PO_DEPARTMENT;
    @FXML
    private TableColumn<task, String> PO_NAME;
    @FXML
    private TableColumn<task, String> PO_POSITION;
    @FXML
    private TableColumn<task, Double> PO_PROGRESS_MONTH;
    @FXML
    private TableColumn<task, Double> PO_PROGRESS_WEEK;
    @FXML
    private TableColumn<task, String> PO_REMARKS;
    @FXML
    private TableView<task> P_PROGRESTABLE;
    @FXML
    private StackedBarChart<String, Number> P_STACKEDBAR;
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

    private final ObservableList<task> DisplayEmployee = FXCollections.observableArrayList();
    private final String DB_URL = "jdbc:mysql://127.0.0.1:3306/pomsdb?serverTimezone=UTC";
    private final String USER = "root";
    private final String PASSWORD = "luese_192003";

    @FXML
    void BTN_LOGOUT(MouseEvent event) {
        // Add your event handling code here
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
    void btnPERFORAMANCE(MouseEvent event) {
        // Add your event handling code here
    }

    @FXML
    void btnPRINT(MouseEvent event) {
        // Add your event handling code here
    }

    public void initialize() {
        DisplayDepartments();
        setEmployeeTABLEVIEW();
        setEmployeeSTACKEDBARCHART();
        setEmployeeLISTVIEW();
        DisplayEmployeeprogress();

        PE_BOX.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                DisplayEmployeeprogress();
            }
        });
    }

    public void DisplayDepartments() {
        String query = "SELECT Department FROM employeedb";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String department = rs.getString("Department");
                PE_BOX.getItems().add(department);

                // Add random sample data for each department
                for (int i = 0; i < 5; i++) { // Add 5 sample employees per department
                    String name = department + "_Employee" + (i + 1);
                    String position = "Position" + (i + 1);
                    double progressWeek = Math.random(); // Random value between 0.0 and 1.0
                    double progressMonth = Math.random(); // Random value between 0.0 and 1.0
                    DisplayEmployee.add(new task(name, department, position, progressWeek, progressMonth, "Sample Remarks"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        PE_BOX.getSelectionModel().selectFirst();
    }

    public class task {
        private String name;
        private String department;
        private String position;
        private double progressWeek;
        private double progressMonth;
        private String remarks;

        public task(String name, String department, String position, double progressWeek, double progressMonth, String remarks) {
            this.name = name;
            this.department = department;
            this.position = position;
            this.progressWeek = progressWeek;
            this.progressMonth = progressMonth;
            this.remarks = remarks;
        }

        public task(String string, String string2, String string3) {
            // Constructor implementation
        }

        // Getters and setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getDepartment() { return department; }
        public void setDepartment(String department) { this.department = department; }
        public String getPosition() { return position; }
        public void setPosition(String position) { this.position = position; }
        public double getProgressWeek() { return progressWeek; }
        public void setProgressWeek(double progressWeek) { this.progressWeek = progressWeek; }
        public double getProgressMonth() { return progressMonth; }
        public void setProgressMonth(double progressMonth) { this.progressMonth = progressMonth; }
        public String getRemarks() { return remarks; }
        public void setRemarks(String remarks) { this.remarks = remarks; }
    }

    private void DisplayEmployeeprogress() {
        DisplayEmployee.clear(); // Clear the list before populating
        String query = "SELECT Fullname, Department, Position FROM employeedb";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String name = rs.getString("Fullname");
                String department = rs.getString("Department");
                String position = rs.getString("Position");
                // Sample data for progress
                double progressWeek = Math.random(); // Random value between 0.0 and 1.0
                double progressMonth = Math.random(); // Random value between 0.0 and 1.0
                DisplayEmployee.add(new task(name, department, position, progressWeek, progressMonth, "Sample Remarks"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        PO_NAME.setCellValueFactory(new PropertyValueFactory<>("name"));
        PO_DEPARTMENT.setCellValueFactory(new PropertyValueFactory<>("department"));
        PO_POSITION.setCellValueFactory(new PropertyValueFactory<>("position"));
        PO_PROGRESS_WEEK.setCellValueFactory(new PropertyValueFactory<>("progressWeek"));
        PO_PROGRESS_MONTH.setCellValueFactory(new PropertyValueFactory<>("progressMonth"));
        PO_REMARKS.setCellValueFactory(new PropertyValueFactory<>("remarks"));
        P_PROGRESTABLE.setItems(DisplayEmployee);

        P_STACKEDBAR.getData().clear();
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Week Progress");
        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series2.setName("Month Progress");

        for (task employee : DisplayEmployee) {
            series1.getData().add(new XYChart.Data<>(employee.getName(), employee.getProgressWeek()));
            series2.getData().add(new XYChart.Data<>(employee.getName(), employee.getProgressMonth()));
        }
        P_STACKEDBAR.getData().addAll(series1, series2);
    }

    private double calculateProgress(String employeeName, String period) {
        String query = "";
        if (period.equals("week")) {
            query = "SELECT COUNT(*) AS taskCount FROM finisheddb WHERE Status = 'week' AND AssignedTo = ?";
        } else if (period.equals("month")) {
            query = "SELECT COUNT(*) AS taskCount FROM finisheddb WHERE Status = 'month' AND AssignedTo = ?";
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, employeeName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("taskCount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void setEmployeeTABLEVIEW() {
        PO_NAME.setCellValueFactory(new PropertyValueFactory<>("name"));
        PO_DEPARTMENT.setCellValueFactory(new PropertyValueFactory<>("department"));
        PO_POSITION.setCellValueFactory(new PropertyValueFactory<>("position"));
        PO_PROGRESS_WEEK.setCellValueFactory(new PropertyValueFactory<>("progressWeek"));
        PO_PROGRESS_MONTH.setCellValueFactory(new PropertyValueFactory<>("progressMonth"));
        PO_REMARKS.setCellValueFactory(new PropertyValueFactory<>("remarks"));

        Callback<TableColumn<task, Double>, TableCell<task, Double>> progressCellFactory = new Callback<TableColumn<task, Double>, TableCell<task, Double>>() {
            @Override
            public TableCell<task, Double> call(TableColumn<task, Double> param) {
                return new TableCell<task, Double>() {
                    private final ProgressIndicator progressIndicator = new ProgressIndicator();
                    private final HBox container = new HBox(progressIndicator);
                    {
                        container.setAlignment(Pos.CENTER);
                        progressIndicator.setPrefSize(70, 70);
                    }
                    @Override
                    protected void updateItem(Double item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            progressIndicator.setProgress(item);
                            setGraphic(container);
                        }
                    }
                };
            }
        };

        PO_PROGRESS_WEEK.setCellFactory(progressCellFactory);
        PO_PROGRESS_MONTH.setCellFactory(progressCellFactory);

        P_PROGRESTABLE.setItems(DisplayEmployee);
    }

    private void setEmployeeSTACKEDBARCHART() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        P_STACKEDBAR.setTitle("Employee Progress");
        xAxis.setLabel("Employee");
        yAxis.setLabel("Progress");
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Week Progress");
        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series2.setName("Month Progress");
        for (task employee : DisplayEmployee) {
            series1.getData().add(new XYChart.Data<>(employee.getName(), employee.getProgressWeek()));
            series2.getData().add(new XYChart.Data<>(employee.getName(), employee.getProgressMonth()));
        }
        P_STACKEDBAR.getData().addAll(series1, series2);
    }

    private void setEmployeeLISTVIEW() {
        PE_BOX.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText(item);
                        }
                    }
                };
            }
        });
    }

    public static class Employee {
        private final String name;
        private final String department;
        private final String position;
        private final double progressWeek;
        private final double progressMonth;
        private final String remarks;

        public Employee(String name, String department, String position, double progressWeek, double progressMonth, String remarks) {
            this.name = name;
            this.department = department;
            this.position = position;
            this.progressWeek = progressWeek;
            this.progressMonth = progressMonth;
            this.remarks = remarks;
        }

        public String getName() { return name; }
        public String getDepartment() { return department; }
        public String getPosition() { return position; }
        public double getProgressWeek() { return progressWeek; }
        public double getProgressMonth() { return progressMonth; }
        public String getRemarks() { return remarks; }
    }
}