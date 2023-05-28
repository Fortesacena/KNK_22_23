package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import jdk.internal.icu.util.CodePointMap;
import models.Application;
import models.getData;
import repository.StudentDashboardRepository;
import services.ConnectionUtil;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class StudentDashboard_Controller implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<String> City;
    private String[] city = {"Prishtina", "Prizren", "Ferizaj", "Peje", "Gjakove", "Gjilan", "Mitrovice", "Podujeve", "Vushtrri", "Suhareke",
            "Rahovec", "Drenas", "Lipjan", "Malisheve", "Kamenice", "Viti", "Decan", "Istog", "Kline", "Skenderaj", "Dragash", "Fushe Kosove", "Kacanik",
            "Shtime", "Obiliq", "Leposaviq"};

    @FXML
    private ChoiceBox<String> Gender;
    private String[] gender = {"Female", "Male"};

    @FXML
    private Button Studentstatus_btn;

    @FXML
    private ChoiceBox<Integer> StudyYear;
    private Integer[] studyYear = {1, 2, 3, 4, 5, 6};

    @FXML
    private ChoiceBox<String> Uni;
    private String[] uni = {"Fakulteti Filozofik", "Fakulteti i Shkencave Matematike Natyrore", "Fakulteti i Filologjise", "Fakulteti Juridik",
            "Fakulteti i Inxhinierise se Ndertimit", "Fakulteti Ekonomik", "Fakulteti i Inxhinierise Elektrike dhe Kompjuterike", "Fakulteti i Inxhinierise Mekanike",
            "Fakulteti i Mjekesise", "Fakulteti i Arteve", "Fakulteti i Bujqesise dhe Veterinarise", "Fakulteti i Edukimit Fizik dhe i Sportit",
            "Fakulteti i Eduktimi", "Fakulteti i Arkitektures"};

    @FXML
    private Button applyInDormitory_btn;

    @FXML
    private DatePicker birthDate_field;
    @FXML
    private Button insertImg_btn;

    @FXML
    private Button logout_btn;

    @FXML
    private TextField name_field;

    @FXML
    private TextField phoneNO;

    @FXML
    private Button studentApply_btn;

    @FXML
    private TextField studentID;

    @FXML
    private TextField surname_field;

    @FXML
    private TextField PersonalNumber;

    @FXML
    private TextField AverageGrade;

    @FXML
    private ImageView studentDashboard_imageView;

    @FXML
    private Button studentDashboard_insertbtn;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button roomDetails_btn;


    @FXML
    private Label personalNumber;

    @FXML
    private Label Year_std_dashboard;


    @FXML
    private Label uni_std_dashboard;

    @FXML
    private Label phonenr_std;

    @FXML
    private Label name_std_dashboard;

    @FXML
    private Label surname_std_dashboard;

    @FXML
    private Label gender_std_dashboard;

    @FXML
    private Label birthdate_stddashbooard;

    @FXML
    private Label City_dashboard;


    @FXML
    private Label averageGrade;

    @FXML
    private Label wlcstudent_std1;

    @FXML
    private Label signout_stf1;

    @FXML
    private ImageView albanianFlag;

    @FXML
    private ImageView americanFlag;


    private Image image;


    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    public void applyStudentToDormitory() {
        Integer studyYear = (Integer) StudyYear.getSelectionModel().getSelectedItem();
        String university = (String) Uni.getSelectionModel().getSelectedItem();
        String phoneNo = phoneNO.getText();
        String name = name_field.getText();
        String lastname = surname_field.getText();
        String gender = (String) Gender.getSelectionModel().getSelectedItem();
        String birthDate = String.valueOf(birthDate_field.getValue());
        String city = (String) City.getSelectionModel().getSelectedItem();
        String personalNumber = PersonalNumber.getText();
        Double averageGrade = Double.valueOf(AverageGrade.getText());
        String image = String.valueOf(studentDashboard_imageView.getImage());

        Alert alert;

        if (studyYear == null || university == null || phoneNo == null || name == null || lastname == null || gender == null
                || birthDate == null || city == null || personalNumber == null || averageGrade == null || getData.path == null
                || getData.path.isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } else {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.isPresent() && option.get() == ButtonType.OK) {
                try {
                    Application applicationData = new Application(null, studyYear, university, phoneNo, name, lastname, gender,
                            birthDate, city, personalNumber, averageGrade, image, null, null);
                    StudentDashboardRepository studentDashboardRepository = new StudentDashboardRepository();
                    studentDashboardRepository.insertApplication(applicationData);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Applied");
                    alert.showAndWait();
                } catch (SQLException e) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Failed to apply student: " + e.getMessage());
                    alert.showAndWait();
                }
            }
        }
    }

    public void applyStudentInsertImage(){

        FileChooser open = new FileChooser();
        open.setTitle("Open Image File");
        open.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image File", "jpg", "*png"));

        File file = open.showOpenDialog(main_form.getScene().getWindow());

        if(file != null){


            image = new Image(file.toURI().toString(), 222, 242, false, true);
            studentDashboard_imageView.setImage(image);

            getData.path = file.getAbsolutePath();


        }

    }

    @FXML
    public void switchToStudentStatus(javafx.event.ActionEvent actionEvent) throws IOException {
        Studentstatus_btn.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/controllers/Student_Status.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToLogIn(javafx.event.ActionEvent actionEvent) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Message");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure?");
        Optional<ButtonType> option = alert.showAndWait();
        logout_btn.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/controllers/Login.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToRoomDetails(javafx.event.ActionEvent actionEvent) throws IOException {
        roomDetails_btn.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/controllers/Room_Details.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    void translateEnglish() {
        Locale currentLocale = new Locale("en");

        ResourceBundle translate = ResourceBundle.getBundle("translation.content", currentLocale);
        studentApply_btn.setText(translate.getString("applyInDormitory_btn"));
        Studentstatus_btn.setText(translate.getString("statusi_roomdetails"));
        roomDetails_btn.setText(translate.getString("RoomDetails"));
        personalNumber.setText(translate.getString("personalNumber"));
        Year_std_dashboard.setText(translate.getString("Year_std_dashboard"));
        uni_std_dashboard.setText(translate.getString("uni_std_dashboard"));
        phonenr_std.setText(translate.getString("phonenr_std"));
        name_std_dashboard.setText(translate.getString("name_std_dashboard"));
        surname_std_dashboard.setText(translate.getString("surname_std_dashboard"));
        gender_std_dashboard.setText(translate.getString("gender_std_dashboard"));
        birthdate_stddashbooard.setText(translate.getString("birthdate_stddashbooard"));
        City_dashboard.setText(translate.getString("City_dashboard"));
        averageGrade.setText(translate.getString("averageGrade"));
        applyInDormitory_btn.setText(translate.getString("applyInDormitory_btn"));
        wlcstudent_std1.setText(translate.getString("wlc_student_rd"));
        signout_stf1.setText(translate.getString("Signout_dashboard"));



    }

    void translateAlbanian() {
        Locale currentLocale = new Locale("sq");

        ResourceBundle translate = ResourceBundle.getBundle("translation.content", currentLocale);
        studentApply_btn.setText(translate.getString("applyInDormitory_btn"));
        Studentstatus_btn.setText(translate.getString("statusi_roomdetails"));
        roomDetails_btn.setText(translate.getString("RoomDetails"));
        personalNumber.setText(translate.getString("personalNumber"));
        Year_std_dashboard.setText(translate.getString("Year_std_dashboard"));
        uni_std_dashboard.setText(translate.getString("uni_std_dashboard"));
        phonenr_std.setText(translate.getString("phonenr_std"));
        name_std_dashboard.setText(translate.getString("name_std_dashboard"));
        surname_std_dashboard.setText(translate.getString("surname_std_dashboard"));
        gender_std_dashboard.setText(translate.getString("gender_std_dashboard"));
        birthdate_stddashbooard.setText(translate.getString("birthdate_stddashbooard"));
        City_dashboard.setText(translate.getString("City_dashboard"));
        averageGrade.setText(translate.getString("averageGrade"));
        applyInDormitory_btn.setText(translate.getString("applyInDormitory_btn"));
        wlcstudent_std1.setText(translate.getString("wlc_student_rd"));
        signout_stf1.setText(translate.getString("Signout_dashboard"));

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Gender.getItems().addAll(gender);
        StudyYear.getItems().addAll(studyYear);
        Uni.getItems().addAll(uni);
        City.getItems().addAll(city);

        Locale.setDefault(new Locale("sq"));
        albanianFlag.setOnMouseClicked(e -> {
            translateAlbanian();
        });
        americanFlag.setOnMouseClicked(e -> {
            translateEnglish();
        });
    }
}

