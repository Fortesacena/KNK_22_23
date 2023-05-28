package services.validators;

import javafx.scene.control.Control;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import services.exceptions.DifferentPasswords;
import services.exceptions.Validation;
import services.interfaces.ValidatorInterface;

public class ValidatorService implements ValidatorInterface {
    private boolean isValid = true;

    @Override
    public void validateTextField(TextField textField) {
        if (textField.getText().trim().isEmpty()) {
            this.setErrorStyle(textField);
            textField.setPromptText("Nuk mund te jete e zbrazet");
            this.isValid = false;
            return;
        }
        this.setDefaultStyle(textField);
    }

    @Override
    public void validateGeneralPasswordField(PasswordField passwordField) {
        if (passwordField.getText().trim().isEmpty()) {
            this.setErrorStyle(passwordField);
            passwordField.setPromptText("Nuk mund te jete e zbrazet");
            this.isValid = false;
            return;
        }
        this.setDefaultStyle(passwordField);
    }


    @Override
    public void validatePasswordField(PasswordField passwordField) {
        if (passwordField.getText().trim().length() < 8
                || !passwordField.getText().trim().matches(".*[0-9].*")) {
            this.setErrorStyle(passwordField);
            passwordField.setPromptText("Sheno te paketen 8 karaktere dhe numra");
            this.isValid = false;
            return;
        }
        this.setDefaultStyle(passwordField);
    }

    @Override
    public void validatePhoneTextField(TextField textField) {
        if (!textField.getText().trim().matches("^\\d+$")) {
            this.setErrorStyle(textField);
            textField.setPromptText("Sheno numer telefoni valid");
            this.isValid = false;
            return;
        }
        this.setDefaultStyle(textField);
    }

    @Override
    public void validateEmailTextField(TextField textField) {
        if (!textField.getText().trim().matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})?$")) {
            this.setErrorStyle(textField);
            textField.setPromptText("Sheno email valide");
            this.isValid = false;
            return;
        }
        this.setDefaultStyle(textField);
    }

    @Override
    public void validateUsernameTextField(TextField textField) {
        if (!textField.getText().trim().matches("[a-zA-Z0-9]{3,}")) {
            this.setErrorStyle(textField);
            textField.setPromptText("Sheno (3 te pakten) karaktere alfanumerike");
            this.isValid = false;
            return;
        }
        this.setDefaultStyle(textField);
    }

    @Override
    public void validateEmriTextField(TextField textField) {
        if (!textField.getText().trim().matches("[a-zA-Z]{2,}")) {
            this.setErrorStyle(textField);
            textField.setPromptText("Sheno (2 te pakten) karaktere alfabetike");
            this.isValid = false;
            return;
        }
        this.setDefaultStyle(textField);
    }

    @Override
    public void validateMbiemriTextField(TextField textField) {
        if (!textField.getText().trim().matches("[a-zA-Z]{2,}")) {
            this.setErrorStyle(textField);
            textField.setPromptText("Sheno (2 te pakten) karaktere alfabetike");
            this.isValid = false;
            return;
        }
        this.setDefaultStyle(textField);
    }

    @Override
    public void validateMatchingPasswords(PasswordField passwordField, PasswordField confirmPasswordField)
            throws DifferentPasswords {
        System.out.println(passwordField.getText());
        System.out.println(confirmPasswordField.getText());
        if (!passwordField.getText().trim().equals(confirmPasswordField.getText().trim())) {
            this.setErrorStyle(passwordField);
            this.setErrorStyle(confirmPasswordField);
            throw new DifferentPasswords("Passwords must be different");
        }
    }

    @Override
    public void throwIfInvalid() throws Validation {
        if (!this.isValid) {
            throw new Validation("this is invalid");
        }
    }

    private void setErrorStyle(Control control) {
        control.setStyle("" +
                "-fx-border-color: red;" +
                " -fx-prompt-text-fill: red;" +
                " -fx-pref-width: 260;" +
                " -fx-pref-height:30;" +
                "-fx-border-radius:20;" +
                "-fx-background-radius:20");
    }

    private void setDefaultStyle(Control control) {
        control.setStyle("" +
                "-fx-border-color: gray;" +
                " -fx-prompt-text-fill: gray;" +
                " -fx-pref-width: 260;" +
                " -fx-pref-height:30;" +
                "-fx-border-radius:20;" +
                "-fx-background-radius:20");
    }
}
