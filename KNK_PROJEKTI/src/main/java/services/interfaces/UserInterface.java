package services.interfaces;


import models.DTO.CreateUseriDTO;
import services.exceptions.IncorrectPassword;
import services.exceptions.UserAlreadyExists;
import services.exceptions.UserNotFound;

import java.sql.SQLException;

public interface UserInterface {
    void signUp(CreateUseriDTO createUseriDTO)
            throws SQLException, UserAlreadyExists;
    void logIn(String username, String password) throws
            UserNotFound, IncorrectPassword,SQLException;

    boolean isAdmin(String username, String password) throws  SQLException, IncorrectPassword, UserNotFound;

    boolean isStudent(String username, String password) throws  SQLException, IncorrectPassword, UserNotFound;
}
