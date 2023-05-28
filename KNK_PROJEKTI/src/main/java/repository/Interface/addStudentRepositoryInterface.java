package repository.Interface;

import javafx.collections.ObservableList;
import models.Application;

import java.sql.SQLException;

public interface addStudentRepositoryInterface {


    public ObservableList<Application> getStudentListData() throws SQLException;

}
