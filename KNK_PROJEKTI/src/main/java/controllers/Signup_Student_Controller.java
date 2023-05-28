package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import models.DTO.CreateUseriDTO;
import services.PasswordHasher;
import services.UserService;
import services.exceptions.DifferentPasswords;
import services.exceptions.UserAlreadyExists;
import services.interfaces.UserInterface;
import services.interfaces.ValidatorInterface;
import services.validators.ValidatorService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;


public class Signup_Student_Controller implements Initializable {

    @FXML
    private TextField Student_Lastname;

    @FXML
    private TextField Student_Name;

    @FXML
    private Button Student_Submitbtn;

    @FXML
    private TextField Student_Username;

    @FXML
    private PasswordField Student_pw;

    @FXML
    private PasswordField Student_cpw;

    @FXML
    private Label messageLabel;

    @FXML
    private Label Std_name_signup_label;


    @FXML
    private Label Std_Lastname_signup_label;

    @FXML
    private Label std_username_signup_label;

    @FXML
    private Label pss_std_signup_label;

    @FXML
    private Label cpss_std_signup_label;

    @FXML
    private Label Student_acc;

    @FXML
    private Button Student_loginacc;

    @FXML
    private ImageView albanianFlag;

    @FXML
    private ImageView americanFlag;

    private ValidatorInterface validator = new ValidatorService();
    private UserInterface signUpStudentiService = new UserService();

    @FXML
    void handleSubmitStudentBtn(ActionEvent event) {
        if (validateInputs()) {
            CreateUseriDTO createUseriDTO = initializeCreateStudentiDto();
            try {
                this.validator.validateMatchingPasswords(Student_pw, Student_cpw);  // Validate passwords

                this.signUpStudentiService.signUp(createUseriDTO);
                this.messageLabel.setText("Successfully added user");

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully added user");
                alert.showAndWait();

                // Load the dashboardStudent.fxml file
                Parent nextSceneParent = FXMLLoader.load(getClass().getResource("/controllers/Login.fxml"));
                Scene nextScene = new Scene(nextSceneParent);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(nextScene);
                stage.show();
            } catch (DifferentPasswords e) {
                e.printStackTrace();
                this.messageLabel.setText("Passwords must match!");

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Passwords must match!");
                alert.showAndWait();
            } catch (UserAlreadyExists e) {
                e.printStackTrace();
                this.messageLabel.setText("User Already Exists!");

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("User Already Exists!");
                alert.showAndWait();
            } catch (SQLException e) {
                e.printStackTrace();
                this.messageLabel.setText("Something went wrong with the database!");
            } catch (IOException e) {
                e.printStackTrace();
                this.messageLabel.setText("Error loading dashboard!");
            }
        } else {
            this.messageLabel.setText("Please fill all the fields!");

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the fields!");
            alert.showAndWait();
        }
    }

    private boolean validateInputs() {
        return (
                !Student_Name.getText().trim().isEmpty() &&
                        !Student_Lastname.getText().trim().isEmpty() &&
                        !Student_Username.getText().trim().isEmpty() &&
                        !Student_pw.getText().trim().isEmpty() &&
                        !Student_cpw.getText().trim().isEmpty()
        );
    }

    private CreateUseriDTO initializeCreateStudentiDto() {
        String username = this.Student_Username.getText();
        String name = this.Student_Name.getText();
        String lastname = this.Student_Lastname.getText();
        String salt = PasswordHasher.generateSalt();
        String saltedPassword = PasswordHasher.generateSaltedHash(this.Student_pw.getText(), salt);

        return new CreateUseriDTO(username, name, lastname,salt, saltedPassword);
    }


    void translateEnglish() {
        Locale currentLocale = new Locale("en");

        ResourceBundle translate = ResourceBundle.getBundle("translation.content", currentLocale);
        Std_name_signup_label.setText(translate.getString("Std_name_signup_label"));
        Std_Lastname_signup_label.setText(translate.getString("Std_Lastname_signup_label"));
        std_username_signup_label.setText(translate.getString("std_username_signup_label"));
        pss_std_signup_label.setText(translate.getString("pss_std_signup_label"));
        cpss_std_signup_label.setText(translate.getString("cpss_std_signup_label"));
        Student_Submitbtn.setText(translate.getString("Student_Submitbtn"));
        Student_acc.setText(translate.getString("Student_acc"));
        Student_loginacc.setText(translate.getString("Student_loginacc"));

    }

    void translateAlbanian() {
        Locale currentLocale = new Locale("sq");

        ResourceBundle translate = ResourceBundle.getBundle("translation.content", currentLocale);
        Std_name_signup_label.setText(translate.getString("Std_name_signup_label"));
        Std_Lastname_signup_label.setText(translate.getString("Std_Lastname_signup_label"));
        std_username_signup_label.setText(translate.getString("std_username_signup_label"));
        pss_std_signup_label.setText(translate.getString("pss_std_signup_label"));
        cpss_std_signup_label.setText(translate.getString("cpss_std_signup_label"));
        Student_Submitbtn.setText(translate.getString("Student_Submitbtn"));
        Student_acc.setText(translate.getString("Student_acc"));
        Student_loginacc.setText(translate.getString("Student_loginacc"));

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Locale.setDefault(new Locale("sq"));
        albanianFlag.setOnMouseClicked(e->{
            translateAlbanian();
        });
        americanFlag.setOnMouseClicked(e->{
            translateEnglish();
        });

    }

    public void handleLoginAcc(ActionEvent actionEvent) {
    }
}
