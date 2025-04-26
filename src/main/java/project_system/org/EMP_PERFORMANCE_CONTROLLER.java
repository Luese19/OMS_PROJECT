package project_system.org;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;



public class EMP_PERFORMANCE_CONTROLLER implements Initializable {

    @FXML
    private Button backButton;

    @FXML
    private TableColumn<PerformanceData, String> completionDateColumn;

    @FXML
    private Button exportButton;

    @FXML
    private TableView<PerformanceData> performanceTable;

    @FXML
    private TableColumn<PerformanceData, String> statusColumn; // Renamed from ratingColumn

    @FXML
    private TableColumn<PerformanceData, String> taskColumn;

    @FXML
    private StackedBarChart<String, Number> progressChart;

    private ObservableList<PerformanceData> performanceDataList;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (statusColumn == null) {
            System.err.println("Error: statusColumn is not initialized. Check your FXML file.");
            return;
        }
        // Initialize the table columns
        taskColumn.setCellValueFactory(new PropertyValueFactory<>("task"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status")); // Updated from rating

        completionDateColumn.setCellValueFactory(new PropertyValueFactory<>("completionDate"));

        // Load data from the database
        loadPerformanceDataFromDatabase();

        // Populate the progress chart
        populateProgressChart();
    }

    private void loadPerformanceDataFromDatabase() {
        performanceDataList = FXCollections.observableArrayList();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                 "SELECT Title, status, Due FROM finisheddb WHERE status = 'Done'")) { // Updated column name
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                performanceDataList.add(new PerformanceData(
                    resultSet.getString("Title"),
                    resultSet.getString("status"), // Updated column name
                    resultSet.getString("Due")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        performanceTable.setItems(performanceDataList);
    }

    private void populateProgressChart() {
        if (progressChart == null) {
            System.err.println("Error: progressChart is not initialized. Check your FXML file.");
            return;
        }

        XYChart.Series<String, Number> excellentSeries = new XYChart.Series<>();
        excellentSeries.setName("Excellent");
        XYChart.Series<String, Number> goodSeries = new XYChart.Series<>();
        goodSeries.setName("Good");
        XYChart.Series<String, Number> averageSeries = new XYChart.Series<>();
        averageSeries.setName("Average");

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                 "SELECT WEEK(Due) AS week, status, COUNT(*) AS count " +
                 "FROM finisheddb WHERE status = 'Done' GROUP BY week, status")) { // Updated column name
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String week = "Week " + resultSet.getInt("week");
                String status = resultSet.getString("status"); // Updated variable name
                int count = resultSet.getInt("count");

                switch (status) { // Updated variable name
                    case "Excellent":
                        excellentSeries.getData().add(new XYChart.Data<>(week, count));
                        break;
                    case "Good":
                        goodSeries.getData().add(new XYChart.Data<>(week, count));
                        break;
                    case "Average":
                        averageSeries.getData().add(new XYChart.Data<>(week, count));
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        progressChart.getData().addAll(excellentSeries, goodSeries, averageSeries);
    }

    // Model class for performance data
    public static class PerformanceData {
        private String task;
        private String status; // Renamed from rating
        private String completionDate;

        public PerformanceData(String task, String status, String completionDate) { // Updated parameter name
            this.task = task;
            this.status = status; // Updated assignment
            this.completionDate = completionDate;
        }

        public String getTask() {
            return task;
        }

        public void setTask(String task) {
            this.task = task;
        }

        public String getStatus() { // Renamed from getRating
            return status;
        }

        public void setStatus(String status) { // Renamed from setRating
            this.status = status; // Updated assignment
        }

        public String getCompletionDate() {
            return completionDate;
        }

        public void setCompletionDate(String completionDate) {
            this.completionDate = completionDate;
        }
    }

    public void handleBackButtonAction() throws IOException {
        // Ensure the correct FXML file path is passed
        App.setRoot("E_DASHBOARD1", 1300, 810); // Corrected navigation method
    }
    
    
}
