package services;

import models.DTO.CreateUseriDTO;
import models.Users;
import repository.Interface.UserRepositoryInterface;
import repository.UserRepository;
import services.exceptions.IncorrectPassword;
import services.exceptions.UserAlreadyExists;
import services.exceptions.UserNotFound;
import services.interfaces.UserInterface;

import java.sql.SQLException;

public class UserService implements UserInterface {
    private UserRepositoryInterface userRepository = new UserRepository();

    private String SELECT_ROLE_QUERY = "SELECT role FROM users WHERE username = ? AND salted_password = ?";

    @Override
    public void signUp(CreateUseriDTO createUseriDto) throws SQLException, UserAlreadyExists {
        String username = createUseriDto.getUsername();
        if (userRepository.getStudentByUsername(username) != null) {
            throw new UserAlreadyExists("Username already taken!");
        }

        userRepository.insert(createUseriDto);
    }


    @Override
    public void logIn(String username, String password) throws UserNotFound, IncorrectPassword, SQLException {
        Users useri = userRepository.getStudentByUsername(username);
        if (useri == null) {
            throw new UserNotFound("User does not exist");
        }
        boolean isPasswordCorrect = PasswordHasher.compareSaltedHash(password, useri.getSalt(), useri.getSaltedPassword());
        if (!isPasswordCorrect) {
            throw new IncorrectPassword("Incorrect password");
        }
    }

    @Override
    public boolean isAdmin(String username, String password) throws SQLException, IncorrectPassword, UserNotFound {
        Users user = userRepository.getStudentByUsername(username);
        if (user == null) {
            throw new UserNotFound("User not found.");
        }
        boolean isPasswordCorrect = PasswordHasher.compareSaltedHash(password, user.getSalt(), user.getSaltedPassword());
        if (!isPasswordCorrect) {
            throw new IncorrectPassword("Incorrect password");
        }
        return user.getRole().equalsIgnoreCase("admin");
    }

    @Override
    public boolean isStudent(String username, String password) throws SQLException, IncorrectPassword, UserNotFound {
        Users user = userRepository.getStudentByUsername(username);
        if (user == null) {
            throw new UserNotFound("User not found.");
        }
        boolean isPasswordCorrect = PasswordHasher.compareSaltedHash(password, user.getSalt(), user.getSaltedPassword());
        if (!isPasswordCorrect) {
            throw new IncorrectPassword("Incorrect password");
        }
        return user.getRole().equalsIgnoreCase("student");
    }
}
