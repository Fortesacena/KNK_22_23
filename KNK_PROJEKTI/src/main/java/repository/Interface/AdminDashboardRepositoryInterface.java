package repository.Interface;

import javafx.scene.chart.XYChart;

import java.sql.SQLException;

public interface AdminDashboardRepositoryInterface {

    int getTotalApplicants() throws SQLException;
    int getFemaleApplicants() throws SQLException;
    int getMaleApplicants() throws SQLException;
    XYChart.Series<String, Number> getTotalApplicantsChart() throws SQLException;
}
