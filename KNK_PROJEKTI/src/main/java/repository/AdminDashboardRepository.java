package repository;

import javafx.scene.chart.XYChart;
import repository.Interface.AdminDashboardRepositoryInterface;
import services.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDashboardRepository implements AdminDashboardRepositoryInterface {
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    @Override
    public int getTotalApplicants() throws SQLException {
        String sql = "SELECT COUNT(application_id) FROM application";

        connect = ConnectionUtil.getConnection();

        int countTotal = 0;

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                countTotal = result.getInt("COUNT(application_id)");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return countTotal;
    }

    @Override
    public int getFemaleApplicants() throws SQLException {
        String sql = "SELECT COUNT(application_id) FROM application WHERE gender = 'Female'";

        connect = ConnectionUtil.getConnection();

        int countFemale = 0;

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                countFemale = result.getInt("COUNT(application_id)");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return countFemale;
    }

    @Override
    public int getMaleApplicants() throws SQLException {
        String sql = "SELECT COUNT(application_id) FROM application WHERE gender = 'Male'";

        connect = ConnectionUtil.getConnection();

        int countMale = 0;

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                countMale = result.getInt("COUNT(application_id)");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return countMale;
    }

    @Override
    public XYChart.Series<String, Number> getTotalApplicantsChart() throws SQLException {
        String sql = "SELECT date, COUNT(application_id) FROM application GROUP BY date ORDER BY TIMESTAMP(date) ASC LIMIT 10";

        connect = ConnectionUtil.getConnection();

        XYChart.Series<String, Number> chart = new XYChart.Series<>();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                chart.getData().add(new XYChart.Data<>(result.getString(1), result.getInt(2)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return chart;
    }
}
