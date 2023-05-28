package repository.Interface;

import models.DTO.CreateUseriDTO;
import models.Users;

import java.sql.SQLException;

public interface UserRepositoryInterface {
    public void insert(CreateUseriDTO createUseriDTO) throws SQLException;
    public Users getStudentByUsername(String username) throws SQLException;
}


