package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import repository.AdminDashboardRepository;
import repository.Interface.AdminDashboardRepositoryInterface;


import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class Admin_Dashboard_Controller implements Initializable {

    @FXML
    private Button applicationDetails_btn;

    @FXML
    private Label wlc_admin_dashboard;

    @FXML
    private AreaChart<String, Number> dashboard_areaChart;

    @FXML
    private Label dashboard_femaleApplicants;


    @FXML
    private Label dashboard_maleApplicants;

    @FXML
    private Label dashboard_totalApplicants;

    @FXML
    private Button logout_btn;

    @FXML
    private Button manageRooms_btn;

    @FXML
    private Label female_app;

    @FXML
    private Label male_app;

    @FXML
    private Button home_btn;

    @FXML
    private Label Totalappl_dashboard;

    @FXML
    private Label Signout_dashboard;

    @FXML
    private ImageView albanianFlag;

    @FXML
    private ImageView americanFlag;


    private AdminDashboardRepositoryInterface repository = new AdminDashboardRepository();

    public void homeDisplayTotalApplicants() throws SQLException {
        dashboard_totalApplicants.setText(String.valueOf(repository.getTotalApplicants()));
    }

    public void homeDisplayFemaleApplicants() throws SQLException {
        dashboard_femaleApplicants.setText(String.valueOf(repository.getFemaleApplicants()));
    }

    public void homeDisplayMaleApplicants() throws SQLException {
        dashboard_maleApplicants.setText(String.valueOf(repository.getMaleApplicants()));
    }

    public void homeDisplayTotalApplicantsChart() throws SQLException {
        dashboard_areaChart.getData().clear();
        dashboard_areaChart.getData().add(repository.getTotalApplicantsChart());
    }

    public void switchToHome(javafx.event.ActionEvent actionEvent) throws IOException {
        home_btn.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/controllers/Admin_Dashboard.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToApplicationDetails(javafx.event.ActionEvent actionEvent) throws IOException {
        applicationDetails_btn.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/controllers/AddStudents.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToManageRooms(javafx.event.ActionEvent actionEvent) throws IOException {
        manageRooms_btn.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/controllers/ManageRooms.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void switchToLogIn(javafx.event.ActionEvent actionEvent) throws IOException {
        logout_btn.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/controllers/Login.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Duhet nje Override
    void translateEnglish() {
        Locale currentLocale = new Locale("en");

        ResourceBundle translate = ResourceBundle.getBundle("translation.content", currentLocale);
        female_app.setText(translate.getString("female_app"));
        Signout_dashboard.setText(translate.getString("Signout_dashboard"));
        wlc_admin_dashboard.setText(translate.getString("wlc_admin_dashboard"));
        male_app.setText(translate.getString("male_app"));
        Totalappl_dashboard.setText(translate.getString("Totalappl_dashboard"));
        home_btn.setText(translate.getString("home_btn"));
        applicationDetails_btn.setText(translate.getString("applicationDetails_btn"));
        manageRooms_btn.setText(translate.getString("manageRooms_btn"));
    }

    void translateAlbanian() {
        Locale currentLocale = new Locale("sq");

        ResourceBundle translate = ResourceBundle.getBundle("translation.content", currentLocale);
        female_app.setText(translate.getString("female_app"));
        Signout_dashboard.setText(translate.getString("Signout_dashboard"));
        wlc_admin_dashboard.setText(translate.getString("wlc_admin_dashboard"));
        male_app.setText(translate.getString("male_app"));
        Totalappl_dashboard.setText(translate.getString("Totalappl_dashboard"));
        home_btn.setText(translate.getString("home_btn"));
        applicationDetails_btn.setText(translate.getString("applicationDetails_btn"));
        manageRooms_btn.setText(translate.getString("manageRooms_btn"));

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            homeDisplayTotalApplicants();
            homeDisplayFemaleApplicants();
            homeDisplayMaleApplicants();
            homeDisplayTotalApplicantsChart();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Locale.setDefault(new Locale("sq"));
        albanianFlag.setOnMouseClicked(e->{
            translateAlbanian();
        });
        americanFlag.setOnMouseClicked(e->{
            translateEnglish();
        });
    }
}
