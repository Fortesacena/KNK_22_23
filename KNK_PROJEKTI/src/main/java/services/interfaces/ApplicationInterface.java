package services.interfaces;

import models.DTO.CreateApplicationDTO;
import services.exceptions.UserAlreadyExists;

import java.sql.SQLException;

public interface ApplicationInterface {
    void addStudentToDormitory(CreateApplicationDTO application) throws SQLException;

    void deleteStudentFromDormitory(int applicationId) throws SQLException;

    public void apply(CreateApplicationDTO createApplicationDTO) throws SQLException, UserAlreadyExists;

}
