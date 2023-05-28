package controllers;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import models.Application;
import repository.addStudentRepository;
import services.ConnectionUtil;

public class AddStudents_Controller implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button Availability_btn;

    @FXML
    private Button ManageRooms_btn;

    @FXML
    private Button addStudent_btn;

    @FXML
    private TableColumn<Application, Double> addStudents_col_AverageGrade;

    @FXML
    private TableColumn<Application, Date> addStudents_col_BirthDate;

    @FXML
    private TableColumn<Application, String> addStudents_col_City;

    @FXML
    private TableColumn<Application, String> addStudents_col_University;

    @FXML
    private TableColumn<Application, String> addStudents_col_Gender;

    @FXML
    private TableColumn<Application, String> addStudents_col_Name;

    @FXML
    private TableColumn<Application, Integer> addStudents_col_PersonalNumber;

    @FXML
    private TableColumn<Application, String> addStudents_col_PhoneNo;

    @FXML
    private TableColumn<Application, String> addStudents_col_Surname;

    @FXML
    private TableColumn<Application, Integer> addStudents_col_studentNum;

    @FXML
    private TableColumn<Application, Integer> addStudents_col_year;

    @FXML
    private TextField addStudents_search;

    @FXML
    private TableView<Application> addStudents_tableView;

    @FXML
    private ComboBox<String> addStudent_dhoma;

    @FXML
    private ComboBox<String> addStudents_konvikti;

    @FXML
    private Button home_btn;

    @FXML
    private Button logout_btn;

    @FXML
    private TextField showSelectedStudentID;

    @FXML
    private ImageView showSelectedStudentImage;
    @FXML
    private Pagination pagination;

    @FXML
    private ImageView albanianFlag;
    @FXML
    private ImageView americanFlag;

    private Image image;
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    private addStudentRepository addstudentRepository = new addStudentRepository();

    public void roomNumberList() throws SQLException {
        String item = (String) addStudents_konvikti.getSelectionModel().getSelectedItem();

        String availableRoomNumber = "SELECT * FROM room WHERE OBJECT = '" + item + "'";

        connect = ConnectionUtil.getConnection();

        try {
            ObservableList listData = FXCollections.observableArrayList();
            prepare = connect.prepareStatement(availableRoomNumber);
            result = prepare.executeQuery();

            while (result.next()) {
                listData.add(result.getString("room"));
            }
            addStudent_dhoma.setItems(listData);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dormitoryNumberList() throws SQLException {
        String listType = "SELECT * FROM room WHERE status = 'Available'";

        connect = ConnectionUtil.getConnection();

        try {

            ObservableList listData = FXCollections.observableArrayList();
            prepare = connect.prepareStatement(listType);
            result = prepare.executeQuery();

            while (result.next()) {
                listData.add(result.getString("object"));
            }

            addStudents_konvikti.setItems(listData);

            roomNumberList();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void studentShowData() throws SQLException {
        ObservableList<Application> listStudentData = addstudentRepository.getStudentListData();

        // Create a new modifiable ObservableList and add all items from the database query
        ObservableList<Application> modifiableList = FXCollections.observableArrayList();
        modifiableList.addAll(listStudentData);

        // Set the items of the TableView to the modifiable list
        addStudents_tableView.setItems(modifiableList);

        // Set up the columns as before
        addStudents_col_studentNum.setCellValueFactory(new PropertyValueFactory<>("application_id"));
        addStudents_col_year.setCellValueFactory(new PropertyValueFactory<>("study_year"));
        addStudents_col_University.setCellValueFactory(new PropertyValueFactory<>("university"));
        addStudents_col_PhoneNo.setCellValueFactory(new PropertyValueFactory<>("phone_no"));
        addStudents_col_Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        addStudents_col_Surname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        addStudents_col_Gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        addStudents_col_BirthDate.setCellValueFactory(new PropertyValueFactory<>("birth_date"));
        addStudents_col_City.setCellValueFactory(new PropertyValueFactory<>("city"));
        addStudents_col_PersonalNumber.setCellValueFactory(new PropertyValueFactory<>("personal_number"));
        addStudents_col_AverageGrade.setCellValueFactory(new PropertyValueFactory<>("average_grade"));

        // Set font color, size, and style
        addStudents_tableView.setStyle("-fx-text-fill: black;");
        addStudents_tableView.setStyle("-fx-font-size: 12px;");
        addStudents_tableView.setStyle("-fx-font-family: Arial;");

        addStudents_tableView.refresh(); // Refresh the table view to display the data
    }

    private String toRgbString(Color color) {
        int r = (int) (color.getRed() * 255);
        int g = (int) (color.getGreen() * 255);
        int b = (int) (color.getBlue() * 255);
        return String.format("rgb(%d,%d,%d)", r, g, b);
    }

    public void addStudentsSelected() {
        Application stdData = addStudents_tableView.getSelectionModel().getSelectedItem();
        int num = addStudents_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        showSelectedStudentID.setText(String.valueOf(stdData.getApplication_id()));

        String url = "file:///" + stdData.getImage();

        image = new Image(url, 200, 200, false, true);
        showSelectedStudentImage.setFitWidth(200);
        showSelectedStudentImage.setFitHeight(200);
        showSelectedStudentImage.setImage(image);

    }

    public void addStudentsSearch() {
        FilteredList<Application> filter = new FilteredList<>(addStudents_tableView.getItems(), e -> true);

        addStudents_search.textProperty().addListener((observable, oldValue, newValue) -> {
            filter.setPredicate(predicateStudentData -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String searchKey = newValue.toLowerCase();

                return predicateStudentData.getApplication_id().toString().contains(searchKey) ||
                        predicateStudentData.getStudy_year().toString().contains(searchKey) ||
                        predicateStudentData.getUniversity().toLowerCase().contains(searchKey) ||
                        predicateStudentData.getPhone_no().toLowerCase().contains(searchKey) ||
                        predicateStudentData.getName().toLowerCase().contains(searchKey) ||
                        predicateStudentData.getLastname().toLowerCase().contains(searchKey) ||
                        predicateStudentData.getGender().toLowerCase().contains(searchKey) ||
                        predicateStudentData.getBirth_date().toString().contains(searchKey) ||
                        predicateStudentData.getCity().toLowerCase().contains(searchKey) ||
                        predicateStudentData.getPersonal_number().toString().contains(searchKey) ||
                        predicateStudentData.getAverage_grade().toString().contains(searchKey);
            });
        });

        SortedList<Application> sortList = new SortedList<>(filter);
        sortList.comparatorProperty().bind(addStudents_tableView.comparatorProperty());
        addStudents_tableView.setItems(sortList);
    }

    public void switchToAdminDashboard(ActionEvent actionEvent) throws IOException {
        home_btn.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/controllers/Admin_Dashboard.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToManageRooms(ActionEvent actionEvent) throws IOException {
        ManageRooms_btn.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/controllers/ManageRooms.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToLogIn(ActionEvent actionEvent) throws IOException {
        logout_btn.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/controllers/Login.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Duhet nje Override
    void translateEnglish() {
        Locale currentLocale = new Locale("en");

        ResourceBundle translate = ResourceBundle.getBundle("translation.content", currentLocale);
        addStudents_col_BirthDate.setText(translate.getString("addStudents_col_BirthDate"));
        addStudents_col_City.setText(translate.getString("addStudents_col_City"));
        addStudents_col_University.setText(translate.getString("addStudents_col_University"));
        addStudents_col_Name.setText(translate.getString("addStudents_col_studentNum"));
        addStudents_col_Gender.setText(translate.getString("addStudents_col_Gender"));
        home_btn.setText(translate.getString("home_btn1"));
        addStudent_btn.setText(translate.getString("addStudent_btn"));
        addStudents_col_year.setText(translate.getString("addStudents_col_year"));;
    }

    void translateAlbanian() {
        Locale currentLocale = new Locale("sq");

        ResourceBundle translate = ResourceBundle.getBundle("translation.content", currentLocale);
        addStudents_col_BirthDate.setText(translate.getString("addStudents_col_BirthDate"));
        addStudents_col_City.setText(translate.getString("addStudents_col_City"));
        addStudents_col_University.setText(translate.getString("addStudents_col_University"));
        addStudents_col_Name.setText(translate.getString("addStudents_col_Name"));
        addStudents_col_Gender.setText(translate.getString("addStudents_col_Gender"));
        home_btn.setText(translate.getString("home_btn1"));
        addStudent_btn.setText(translate.getString("addStudent_btn"));
        addStudents_col_year.setText(translate.getString("addStudents_col_year"));
        addStudents_col_studentNum.setText(translate.getString("addStudents_col_studentNum"));



    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
       ;
        try {
            studentShowData();
            addStudentsSearch();
            dormitoryNumberList();
            roomNumberList();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Locale.setDefault(new Locale("sq"));
        albanianFlag.setOnMouseClicked(e->{
            translateAlbanian();
        });
        americanFlag.setOnMouseClicked(e->{
            translateEnglish();
        });

        addStudents_tableView.setRowFactory(tv -> new TableRow<Application>() {
            @Override
            protected void updateItem(Application item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && item.getRowColor() != null) {
                    getStyleClass().add("highlighted-row");
                } else {
                    getStyleClass().remove("highlighted-row");
                }
            }
        });

    }

    public void addStudentToDormitory(ActionEvent event) {
        Application stdData = addStudents_tableView.getSelectionModel().getSelectedItem();
        String dormitory = addStudents_konvikti.getSelectionModel().getSelectedItem();
        String room = addStudent_dhoma.getSelectionModel().getSelectedItem();

        try {
            addStudentRepository.addStudentToDormitory(stdData, dormitory, room);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Successfully added Student to Dormitory");
            alert.showAndWait();

            // Highlight the selected row in green
            stdData.setRowColor(Color.GREEN);

            // Refresh the TableView to update the row appearance
            addStudents_tableView.refresh();

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Error occurred while adding student to Dormitory");
            alert.showAndWait();
        }
    }

    public void deleteStudentFromDormitory(ActionEvent event) {
        Application selectedStudent = addStudents_tableView.getSelectionModel().getSelectedItem();

        if (selectedStudent != null) {
            Integer applicationId = selectedStudent.getApplication_id();

            try {
                addStudentRepository.deleteStudentFromDormitory(applicationId);
                addStudentRepository.updateRoomStatus(selectedStudent.getRoom());

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure?");
                Optional<ButtonType> option = alert.showAndWait();

                // Remove the selected student from the list
                ObservableList<Application> students = FXCollections.observableArrayList(addStudents_tableView.getItems());
                students.remove(selectedStudent);

                // Update the table with the modified list
                addStudents_tableView.setItems(students);

                // Display success message
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully deleted Student from Dormitory");
                alert.showAndWait();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            // No student selected, show error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("No student selected");
            alert.showAndWait();
        }
    }


}
