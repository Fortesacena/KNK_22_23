package repository;

import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableRow;
import repository.Interface.addStudentRepositoryInterface;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Application;
import services.ConnectionUtil;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class addStudentRepository implements addStudentRepositoryInterface {
        private static Connection connect;
        private static PreparedStatement prepare;
        private static Statement statement;
        private ResultSet result;

    @Override
    public ObservableList<Application> getStudentListData() throws SQLException {
        ObservableList<Application> listStudents = FXCollections.observableArrayList();

        String sql = "SELECT * FROM application";

        connect = ConnectionUtil.getConnection();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            Application stdD;

            while (result.next()) {
                stdD = new Application(result.getInt("application_id"),
                        result.getInt("study_year"),
                        result.getString("university"),
                        result.getString("phone_no"),
                        result.getString("name"),
                        result.getString("lastname"),
                        result.getString("gender"),
                        result.getString("birth_date"),
                        result.getString("city"),
                        result.getString("personal_number"),
                        result.getDouble("average_grade"),
                        result.getString("image"),
                        result.getString("room"),
                        result.getInt("dormitory"));

                listStudents.add(stdD);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listStudents;
    }


    public static void addStudentToDormitory(Application stdData, String dormitory, String room) throws SQLException {
        Integer application_id = stdData.getApplication_id();

        String insertData = "UPDATE application SET room = '" + room + "', dormitory = '"
                + dormitory + "' WHERE application_id = '" + application_id + "'";

        connect = ConnectionUtil.getConnection();

        try {
            if (dormitory == null || room == null) {
                throw new IllegalArgumentException("Please select the data first");
            } else {
                prepare = connect.prepareStatement(insertData);
                prepare.executeUpdate();
            }

            if (room != null) {
                String sqlEditStatus = "UPDATE room SET status ='Occupied' WHERE room = '" + room + "'";
                statement = connect.createStatement();
                statement.executeUpdate(sqlEditStatus);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }



    public static void deleteStudentFromDormitory(Integer application_id) throws SQLException {
        String deleteData = "DELETE FROM application WHERE application_id = ?";

        connect = ConnectionUtil.getConnection();

        try {
            prepare = connect.prepareStatement(deleteData);
            prepare.setInt(1, application_id);
            prepare.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateRoomStatus(String room) throws SQLException {
        String sqlEditStatus = "UPDATE room SET status ='Available' WHERE room = ?";
        try (PreparedStatement updateStatement = connect.prepareStatement(sqlEditStatus)) {
            updateStatement.setString(1, room);
            updateStatement.executeUpdate();
        }
    }
}


