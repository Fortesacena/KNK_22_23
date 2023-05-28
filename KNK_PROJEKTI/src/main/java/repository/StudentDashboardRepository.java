package repository;

import models.Application;
import models.getData;
import repository.Interface.StudentDashboardRepositoryInterface;
import services.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class StudentDashboardRepository implements StudentDashboardRepositoryInterface {

    public void insertApplication(Application applicationData) throws SQLException {
        String insertStudentData = "INSERT INTO application (study_year, university, phone_no, name, lastname, gender, birth_date, city, personal_number, average_grade, image, date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(insertStudentData)) {

            statement.setInt(1, applicationData.getStudy_year());
            statement.setString(2, applicationData.getUniversity());
            statement.setString(3, applicationData.getPhone_no());
            statement.setString(4, applicationData.getName());
            statement.setString(5, applicationData.getLastname());
            statement.setString(6, applicationData.getGender());
            statement.setString(7, String.valueOf(applicationData.getBirth_date()));
            statement.setString(8, applicationData.getCity());
            statement.setString(9, applicationData.getPersonal_number());
            statement.setDouble(10, applicationData.getAverage_grade());
            statement.setString(11, applicationData.getImage());


            String url = getData.path;
            url = url.replace("\"", "/");
            statement.setString(11, url);

            Date date = new Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            statement.setString(12, String.valueOf(sqlDate));

            statement.executeUpdate();
        }
    }

}
