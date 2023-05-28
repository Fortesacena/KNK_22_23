package services.interfaces;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import services.exceptions.DifferentPasswords;
import services.exceptions.Validation;

public interface ValidatorInterface {
    void validateTextField(TextField textField) ;
    void validateGeneralPasswordField(PasswordField passwordField);
    void validatePasswordField(PasswordField passwordField) ;
    void validatePhoneTextField(TextField textField) ;
    void validateEmailTextField(TextField textField) ;
    void validateUsernameTextField(TextField textField) ;
    void validateEmriTextField(TextField textField) ;
    void validateMbiemriTextField(TextField textField) ;

    void validateMatchingPasswords(PasswordField passwordField, PasswordField confirmPasswordField) throws DifferentPasswords;

    void throwIfInvalid() throws Validation;
    
 
}
