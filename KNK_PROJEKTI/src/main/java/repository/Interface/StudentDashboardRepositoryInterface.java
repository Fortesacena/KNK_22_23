package repository.Interface;


import models.Application;
import java.sql.SQLException;

public interface StudentDashboardRepositoryInterface {
    void insertApplication(Application applicationData) throws SQLException;
}
