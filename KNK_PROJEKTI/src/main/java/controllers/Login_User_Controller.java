package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import services.UserService;
import services.exceptions.IncorrectPassword;
import services.exceptions.UserNotFound;
import services.exceptions.Validation;
import services.interfaces.UserInterface;
import services.interfaces.ValidatorInterface;
import services.validators.ValidatorService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Login_User_Controller implements Initializable {

    @FXML
    private Button a_loginbtn;

    @FXML
    private Button a_signupbtn;

    @FXML
    private Label username_admin_label1;

    @FXML
    private Label pass_admin_label;

    @FXML
    private PasswordField a_password;

    @FXML
    private TextField a_username;

    @FXML
    private Label messageLabel;

    @FXML
    private Label a_donthaveacc;

    @FXML
    private Label wlc_admin_Label;

    @FXML
    private ImageView albanianFlag;

    @FXML
    private ImageView americanFlag;


    private UserInterface userService = new UserService();

    private ValidatorInterface validatorService = new ValidatorService();

    @FXML
    public void login(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            try {
                HandleLogin(new ActionEvent(keyEvent.getSource(), null)); // Pass keyEvent.getSource() instead of keyEvent
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @FXML
    public void goToPassword(KeyEvent keyEvent){
        if(keyEvent.getCode() == KeyCode.TAB){
            a_password.requestFocus();
        }
    }

    @FXML
    void HandleLogin(ActionEvent event) throws IOException {
        this.validateInputs();
        String username = a_username.getText();
        String password = a_password.getText();

        try {
            if (userService.isAdmin(username, password)) {

                // Admin login
                Parent nextSceneParent = FXMLLoader.load(getClass().getResource("/controllers/Admin_Dashboard.fxml"));
                Scene nextScene = new Scene(nextSceneParent);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(nextScene);
                stage.show();
            } else if (userService.isStudent(username, password)) {
                // Student login
                Parent nextSceneParent = FXMLLoader.load(getClass().getResource("/controllers/Student_Dashboard.fxml"));
                Scene nextScene = new Scene(nextSceneParent);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(nextScene);
                stage.show();
            } else {
                messageLabel.setText("Invalid username or password!");
            }
        } catch (IncorrectPassword exception) {
            exception.printStackTrace();
            messageLabel.setText("Password is incorrect!");
        } catch (SQLException exception) {
            exception.printStackTrace();
            messageLabel.setText("Something went wrong!");
        } catch (UserNotFound e) {
            e.printStackTrace();
            messageLabel.setText("User not found!");
        }
    }


    private void validateInputs() {
        this.validatorService.validateTextField(a_username);
        this.validatorService.validateGeneralPasswordField(a_password);
        try {
            this.validatorService.throwIfInvalid();
        } catch (Validation exception) {
            exception.printStackTrace();
            this.messageLabel.setText("Invalid Inputs");
        }
    }

    //Duhet nje Override
    void translateEnglish() {
        Locale currentLocale = new Locale("en");

        ResourceBundle translate = ResourceBundle.getBundle("translation.content", currentLocale);
        a_loginbtn.setText(translate.getString("a_loginbtn"));
        a_donthaveacc.setText(translate.getString("a_donthaveacc"));
        wlc_admin_Label.setText(translate.getString("wlc_admin_Label"));
        a_username.setText(translate.getString("username_admin_label"));
        a_password.setText(translate.getString("pass_admin_label"));
        username_admin_label1.setText(translate.getString("username_admin_label"));
        pass_admin_label.setText(translate.getString("pass_admin_label"));
        a_signupbtn.setText(translate.getString("Student_Submitbtn"));
    }

    void translateAlbanian() {
        Locale currentLocale = new Locale("sq");

        ResourceBundle translate = ResourceBundle.getBundle("translation.content", currentLocale);
        a_loginbtn.setText(translate.getString("a_loginbtn"));
        a_donthaveacc.setText(translate.getString("a_donthaveacc"));
        wlc_admin_Label.setText(translate.getString("wlc_admin_Label"));
        a_username.setText(translate.getString("username_admin_label"));
        a_password.setText(translate.getString("pass_admin_label"));
        username_admin_label1.setText(translate.getString("username_admin_label"));
        pass_admin_label.setText(translate.getString("pass_admin_label"));
        a_signupbtn.setText(translate.getString("Student_Submitbtn"));



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

    public void HandleAdminSignUpButton() throws IOException {
        a_signupbtn.getScene().getWindow().hide();
        //LINK YOUR DASHBOARD
        Parent root = FXMLLoader.load(getClass().getResource("/controllers/Signup_Student.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



}
