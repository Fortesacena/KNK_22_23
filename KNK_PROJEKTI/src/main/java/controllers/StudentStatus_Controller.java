package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class StudentStatus_Controller implements Initializable {

    public Button roomDetails_btn1;

    @FXML
    private Button Studentstatus_btn;

    @FXML
    private Label dormitoryField;

    @FXML
    private Button logout_btn;

    @FXML
    private Label roomField;

    @FXML
    private TextField statusLabel;

    @FXML
    private Button studentApply_btn;

    @FXML
    private Label Dormitory_status;

    @FXML
    private Label Room_status;

    @FXML
    private Label Status_of_std;

    @FXML
    private Label wlcstudent_status;

    @FXML
    private Label signout_status;

    @FXML
    private ImageView albanianFlag;

    @FXML
    private ImageView americanFlag;


    public java.awt.Label getStatusLabel() {
        return null;
    }



    void translateEnglish() {
        Locale currentLocale = new Locale("en");

        ResourceBundle translate = ResourceBundle.getBundle("translation.content", currentLocale);
        studentApply_btn.setText(translate.getString("applyInDormitory_btn"));
        Studentstatus_btn.setText(translate.getString("statusi_roomdetails"));
        Status_of_std.setText(translate.getString("Status_of_std"));
        Dormitory_status.setText(translate.getString("Dormitory_status"));
        Room_status.setText(translate.getString("Room_status"));
        wlcstudent_status.setText(translate.getString("wlc_student_rd"));
        signout_status.setText(translate.getString("Signout_dashboard"));
        roomDetails_btn1.setText(translate.getString("RoomDetails"));




    }

    void translateAlbanian() {
        Locale currentLocale = new Locale("sq");

        ResourceBundle translate = ResourceBundle.getBundle("translation.content", currentLocale);
        studentApply_btn.setText(translate.getString("applyInDormitory_btn"));
        Studentstatus_btn.setText(translate.getString("statusi_roomdetails"));
        Status_of_std.setText(translate.getString("Status_of_std"));
        Dormitory_status.setText(translate.getString("Dormitory_status"));
        Room_status.setText(translate.getString("Room_status"));
        wlcstudent_status.setText(translate.getString("wlc_student_rd"));
        signout_status.setText(translate.getString("Signout_dashboard"));
        roomDetails_btn1.setText(translate.getString("RoomDetails"));



    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Locale.setDefault(new Locale("sq"));
        albanianFlag.setOnMouseClicked(e -> {
            translateAlbanian();
        });
        americanFlag.setOnMouseClicked(e -> {
            translateEnglish();
        });

    }

    public void updateStatusLabel(String message) {
            statusLabel.setText(message);
    }

    public void switchToLogIn(javafx.event.ActionEvent actionEvent) throws IOException {
        logout_btn.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/controllers/Login.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToStudentApply(javafx.event.ActionEvent actionEvent) throws IOException {
        studentApply_btn.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/controllers/Student_Dashboard.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    void initialize() {
        assert Studentstatus_btn != null : "fx:id=\"Studentstatus_btn\" was not injected: check your FXML file 'StudentStatus.fxml'.";
        assert logout_btn != null : "fx:id=\"logout_btn\" was not injected: check your FXML file 'StudentStatus.fxml'.";
        assert statusLabel != null : "fx:id=\"status_field\" was not injected: check your FXML file 'StudentStatus.fxml'.";
        assert studentApply_btn != null : "fx:id=\"studentApply_btn\" was not injected: check your FXML file 'StudentStatus.fxml'.";

    }

    public void switchToRoomDetails(ActionEvent event) throws IOException{
        roomDetails_btn1.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/controllers/Room_Details.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}
