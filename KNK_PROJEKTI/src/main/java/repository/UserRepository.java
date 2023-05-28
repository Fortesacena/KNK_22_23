package repository;

import models.DTO.CreateUseriDTO;
import models.Users;
import repository.Interface.UserRepositoryInterface;
import services.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository implements UserRepositoryInterface {

    @Override
    public void insert(CreateUseriDTO createUseriDto) throws SQLException {
        String sql = "INSERT INTO users(username, salt, salted_password, name, lastname) VALUES (?, ?, ?, ?, ?);";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, createUseriDto.getUsername());
        statement.setString(2, createUseriDto.getSalt());
        statement.setString(3, createUseriDto.getSaltedPassword());
        statement.setString(4, createUseriDto.getName());
        statement.setString(5, createUseriDto.getLastname());

        statement.executeUpdate();
    }

    @Override
    public Users getStudentByUsername(String usernameValue) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, usernameValue);

        ResultSet resultSet = statement.executeQuery();
        if (!resultSet.next()) {
            return null;
        }

        int student_id = resultSet.getInt("student_id");
        String username = resultSet.getString("username");
        String salt = resultSet.getString("salt");
        String saltedPassword = resultSet.getString("salted_password");
        String name = resultSet.getString("name");
        String lastname = resultSet.getString("lastname");
        String role = resultSet.getString("role");

        return new Users(
                student_id,
                username,
                salt,
                saltedPassword,
                name,
                lastname,
                role
        );
    }
}
