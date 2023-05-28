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
import models.roomData;
import repository.Interface.RoomRepositoryInterface;
import repository.RoomRepository;
import services.ConnectionUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import static java.lang.String.*;

public class ManageRooms_Controller implements Initializable {

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
    private DatePicker addStudents_Data;

    @FXML
    private TextField addStudents_Emri;

    @FXML
    private ComboBox<?> addStudents_Fakulteti;

    @FXML
    private ComboBox<?> addStudents_Gjinia;

    @FXML
    private TextField addStudents_Mbiemri;

    @FXML
    private ComboBox<?> addStudents_Viti;

    @FXML
    private TableColumn<?, ?> addStudents_col_Datelindja;

    @FXML
    private TableColumn<?, ?> addStudents_col_Emri;

    @FXML
    private TableColumn<?, ?> addStudents_col_Fakulteti;

    @FXML
    private TableColumn<?, ?> addStudents_col_Gjinia;

    @FXML
    private TableColumn<?, ?> addStudents_col_Mbiemri;

    @FXML
    private TableColumn<?, ?> addStudents_col_studentNum;

    @FXML
    private TableColumn<?, ?> addStudents_col_viti;

    @FXML
    private ImageView addStudents_imageView;

    @FXML
    private Button addStudents_insertBtn;

    @FXML
    private TextField addStudents_search;

    @FXML
    private TextField addStudents_studentNum;

    @FXML
    private TableView<?> addStudents_tableView;

    @FXML
    private Button home_btn;

    @FXML
    private Button manageRooms_addBtn;

    @FXML
    private Button manageRooms_clearBtn;

    @FXML
    private Label Object_label_mang;

    @FXML
    private Label Room_label_mang;

    @FXML
    private Label Status_label_mang;

    @FXML
    private TableColumn<roomData, String> manageRooms_col_object;

    @FXML
    private TableColumn<roomData, String> manageRooms_col_room;

    @FXML
    private TableColumn<roomData, String> manageRooms_col_type;

    @FXML
    private TableColumn<roomData, Integer> manageRooms_col_id;

    @FXML
    private Button manageRooms_deleteBtn;

    @FXML
    private ChoiceBox<String> manageRooms_object;
    private final String[] konvikti = {"1", "2", "3", "4", "5", "6", "7", "8"};

    @FXML
    private TextField manageRooms_room;

    @FXML
    private TableView<roomData> manageRooms_tableView;

    @FXML
    private ChoiceBox<String> manageRooms_status;
    private String[] status = {"Available", "Occupied"};

    @FXML
    private Button manageRooms_updateBtn;

    @FXML
    private TextField manageRooms_id;

    @FXML
    private Button logout_btn;

    @FXML
    private Label wlc_manage;


    @FXML
    private Label signout_manage;

    @FXML
    private ImageView albanianFlag;

    @FXML
    private ImageView americanFlag;



    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    private RoomRepositoryInterface roomRepository = new RoomRepository();
    private ObservableList<roomData> roomdataList;
    public void availableRoomShowData() throws SQLException {
        roomdataList = roomRepository.getAllRooms();

        manageRooms_col_object.setCellValueFactory(new PropertyValueFactory<>("object"));
        manageRooms_col_room.setCellValueFactory(new PropertyValueFactory<>("room"));
        manageRooms_col_type.setCellValueFactory(new PropertyValueFactory<>("status"));

        manageRooms_tableView.setItems(roomdataList);

    }
    public void availableRoomSelectData(){
        roomData roomD = manageRooms_tableView.getSelectionModel().getSelectedItem();
        int num = manageRooms_tableView.getSelectionModel().getSelectedIndex();


        if((num-1) < -1){
            return;
        }
        manageRooms_room.setText(roomD.getRoom());
        manageRooms_room.setText(roomD.getRoom());

    }

    public void availableRoomAdd() throws SQLException {
        try {
            String object = (String) manageRooms_object.getSelectionModel().getSelectedItem();
            String room = manageRooms_room.getText();
            String status = (String) manageRooms_status.getSelectionModel().getSelectedItem();

            Alert alert;

            // Check if there are empty fields
            if (object == null || room.isEmpty() || status == null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                roomData RoomData = new roomData(object, room, status);
                boolean roomExists = roomRepository.checkRoomExists(object, room);

                if (roomExists) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Room " + room + " already exists!");
                    alert.showAndWait();
                } else {
                    roomRepository.addRoom(RoomData);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully added!");
                    alert.showAndWait();

                    // Update the data on the tableview
                    availableRoomShowData();

                    // Clear the fields
                    availableRoomClear();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void availableRoomUpdate() {
        String object1 = (String) manageRooms_object.getSelectionModel().getSelectedItem();
        String room1 = manageRooms_room.getText();
        String status1 = (String) manageRooms_status.getSelectionModel().getSelectedItem();;

        try {
            if (object1 == null || room1 == null || status1 == null) {
                showErrorMessage("Please select the data first");
            } else {
                roomData updatedRoom = new roomData(object1, room1, status1);
                roomRepository.updateRoom(updatedRoom);

                showInformationMessage("Successfully updated");
                availableRoomShowData();
                availableRoomClear();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorMessage("Failed to update the room");
        }
    }

    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInformationMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Message");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void availableRoomDelete() throws SQLException {
        String room1 = manageRooms_room.getText();

        if (room1==null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please select the data first");
            alert.showAndWait();
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirmation Message");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to delete?");

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                roomRepository.deleteRoom(room1);

                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Information Message");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Successfully Deleted!");
                successAlert.showAndWait();

                availableRoomShowData();
                availableRoomClear();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public void availableRoomClear(){
        manageRooms_object.getSelectionModel().clearSelection();
        manageRooms_room.setText("");
        manageRooms_status.getSelectionModel().clearSelection();
    }

    public void switchToLogIn(ActionEvent actionEvent) throws IOException {
        logout_btn.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/controllers/Login.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToAdminDashboard(ActionEvent actionEvent) throws IOException {
        home_btn.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/controllers/Admin_Dashboard.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToApplicationDetails(ActionEvent actionEvent) throws IOException {
        addStudent_btn.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/controllers/AddStudents.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    void translateEnglish() {
        Locale currentLocale = new Locale("en");

        ResourceBundle translate = ResourceBundle.getBundle("translation.content", currentLocale);
        manageRooms_col_room.setText(translate.getString("manageRooms_col_room"));
        manageRooms_updateBtn.setText(translate.getString("manageRooms_updateBtn"));
        manageRooms_deleteBtn.setText(translate.getString("manageRooms_deleteBtn"));
        manageRooms_clearBtn.setText(translate.getString("manageRooms_clearBtn"));
        manageRooms_addBtn.setText(translate.getString("manageRooms_addBtn"));
        manageRooms_col_type.setText(translate.getString("manageRooms_col_type"));
        manageRooms_col_object.setText(translate.getString("manageRooms_col_object"));
        Status_label_mang.setText(translate.getString("Status_label_mang"));
        Room_label_mang.setText(translate.getString("Room_label_mang"));
        Object_label_mang.setText(translate.getString("Object_label_mang"));
        home_btn.setText(translate.getString("home_btn"));
        addStudent_btn.setText(translate.getString("applicationDetails_btn"));
        ManageRooms_btn.setText(translate.getString("manageRooms_btn"));
        signout_manage.setText(translate.getString("Signout_dashboard"));
        wlc_manage.setText(translate.getString("wlc_admin_dashboard"));

    }

    void translateAlbanian() {
        Locale currentLocale = new Locale("sq");
        ResourceBundle translate = ResourceBundle.getBundle("translation.content", currentLocale);
        manageRooms_col_room.setText(translate.getString("manageRooms_col_room"));
        manageRooms_updateBtn.setText(translate.getString("manageRooms_updateBtn"));
        manageRooms_deleteBtn.setText(translate.getString("manageRooms_deleteBtn"));
        manageRooms_clearBtn.setText(translate.getString("manageRooms_clearBtn"));
        manageRooms_addBtn.setText(translate.getString("manageRooms_addBtn"));
        manageRooms_col_type.setText(translate.getString("manageRooms_col_type"));
        manageRooms_col_object.setText(translate.getString("manageRooms_col_object"));
        Status_label_mang.setText(translate.getString("Status_label_mang"));
        Room_label_mang.setText(translate.getString("Room_label_mang"));
        Object_label_mang.setText(translate.getString("Object_label_mang"));
        home_btn.setText(translate.getString("home_btn"));
        addStudent_btn.setText(translate.getString("applicationDetails_btn"));
        ManageRooms_btn.setText(translate.getString("manageRooms_btn"));
        signout_manage.setText(translate.getString("Signout_dashboard"));
        wlc_manage.setText(translate.getString("wlc_admin_dashboard"));


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        manageRooms_object.getItems().addAll(konvikti);
        manageRooms_status.getItems().addAll(status);

        //to show the data on tableview
        try {
            availableRoomShowData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Locale.setDefault(new Locale("sq"));
        albanianFlag.setOnMouseClicked(e->{
            translateAlbanian();
        });
        americanFlag.setOnMouseClicked(e->{
            translateEnglish();
        });


    }

}