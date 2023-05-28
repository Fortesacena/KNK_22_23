package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import models.Application;
import services.ConnectionUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class StudentStatus_Controller implements Initializable {

    @FXML
    private TextField personalNumberField;

    @FXML
    private Button searchButton;

    @FXML
    private TableView<Application> tableView;

    @FXML
    private TableColumn<Application, String> prn_std;

    @FXML
    private TableColumn<Application, String> status_stds;

    @FXML
    private TableColumn<Application, String> dormitory_stds;

    @FXML
    private TableColumn<Application, String> room_stds;

    private ObservableList<Application> tableData;

    @FXML
    private Label Status_of_std;

    @FXML
    private Label Dormitory_status;

    @FXML
    private Label Room_status;

    @FXML
    private Label statusLabel;

    @FXML
    private Button roomDetails_btn1;

    @FXML
    private Button Studentstatus_btn;

    @FXML
    private Button logout_btn;

    @FXML
    private Button studentApply_btn;

    @FXML
    private Label wlcstudent_status;

    @FXML
    private Label signout_status;

    @FXML
    private ImageView albanianFlag;

    @FXML
    private ImageView americanFlag;

    void translateEnglish() {
        Locale currentLocale = new Locale("en");

        ResourceBundle translate = ResourceBundle.getBundle("translation.content", currentLocale);
        studentApply_btn.setText(translate.getString("applyInDormitory_btn"));
        Status_of_std.setText(translate.getString("statusi_roomdetails"));
        dormitory_stds.setText(translate.getString("Dormitory_status"));
        room_stds.setText(translate.getString("Room_status"));
        wlcstudent_status.setText(translate.getString("wlc_student_rd"));
        signout_status.setText(translate.getString("Signout_dashboard"));
        roomDetails_btn1.setText(translate.getString("RoomDetails"));
        prn_std.setText(translate.getString("personalNumber1"));
    }

    void translateAlbanian() {
        Locale currentLocale = new Locale("sq");

        ResourceBundle translate = ResourceBundle.getBundle("translation.content", currentLocale);
        studentApply_btn.setText(translate.getString("applyInDormitory_btn"));
        Status_of_std.setText(translate.getString("statusi_roomdetails"));
        dormitory_stds.setText(translate.getString("Dormitory_status"));
        room_stds.setText(translate.getString("Room_status"));
        wlcstudent_status.setText(translate.getString("wlc_student_rd"));
        signout_status.setText(translate.getString("Signout_dashboard"));
        roomDetails_btn1.setText(translate.getString("RoomDetails"));
        prn_std.setText(translate.getString("personalNumber1"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize the table and set up its columns
        prn_std.setCellValueFactory(new PropertyValueFactory<>("personal_number"));
        status_stds.setCellValueFactory(new PropertyValueFactory<>("statusS"));
        dormitory_stds.setCellValueFactory(new PropertyValueFactory<>("dormitory"));
        room_stds.setCellValueFactory(new PropertyValueFactory<>("room"));

        // Initialize the table data
        tableData = FXCollections.observableArrayList();
        tableView.setItems(tableData);

        Locale.setDefault(new Locale("sq"));
        albanianFlag.setOnMouseClicked(e -> {
            translateAlbanian();
        });
        americanFlag.setOnMouseClicked(e -> {
            translateEnglish();
        });
    }

    @FXML
    public void searchpn(ActionEvent event) {
        String personalNumber = personalNumberField.getText();

        // Clear the previous table data
        tableData.clear();

        // Fetch the data from the database for the entered personal number
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "SELECT * FROM Application WHERE personal_number = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, personalNumber);
            ResultSet resultSet = statement.executeQuery();

            // Check if a row exists with the entered personal number
            if (resultSet.next()) {
                Application application = new Application(
                        resultSet.getInt("application_id"),
                        resultSet.getInt("study_year"),
                        resultSet.getString("university"),
                        resultSet.getString("phone_no"),
                        resultSet.getString("name"),
                        resultSet.getString("lastname"),
                        resultSet.getString("gender"),
                        resultSet.getString("birth_date"),
                        resultSet.getString("city"),
                        resultSet.getString("personal_number"),
                        resultSet.getDouble("average_grade"),
                        resultSet.getString("image"),
                        resultSet.getString("room"),
                        resultSet.getInt("dormitory"),
                        resultSet.getString("statusS")
                );

                tableData.add(application);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Personal Number Not Found");
                alert.setHeaderText(null);
                alert.setContentText("The personal number you entered was not found.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any potential exceptions or show an error message
        }

        // Refresh the table view to display the data
        tableView.refresh();
    }

    public void updateStatusLabel(String message) {
        statusLabel.setText(message);
    }

    public void switchToLogIn(ActionEvent actionEvent) throws IOException {
        logout_btn.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/controllers/Login.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToStudentApply(ActionEvent actionEvent) throws IOException {
        studentApply_btn.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/controllers/Student_Dashboard.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToRoomDetails(ActionEvent event) throws IOException {
        roomDetails_btn1.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/controllers/Room_Details.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
