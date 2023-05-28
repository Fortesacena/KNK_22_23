package controllers;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class Room_Details_Controller implements Initializable {

    @FXML
    private Pagination pagination;
    private File[] files;
    @FXML
    private Button studentApply_btn;
    @FXML
    private Button Studentstatus_btn;

    @FXML
    private Button logout_btn;

    @FXML
    private Button RoomDetails;

    @FXML
    private Button showphotos_rd;

    @FXML
    private Label wlc_student_rd;

    @FXML
    private Label signout_rd;

    @FXML
    private ImageView albanianFlag;

    @FXML
    private ImageView americanFlag;


    @FXML
    void getImages(ActionEvent event) {
        Stage stage = (Stage) pagination.getScene().getWindow();
        openImages(stage);
        pagination.setPageFactory(this::getPages);
    }

    public VBox getPages(int pageIndex){
        ImageView imageView = new ImageView();
        if (files != null && pageIndex >= 0 && pageIndex < files.length) {
            File pngs = files[pageIndex];
            try{
                BufferedImage bi = ImageIO.read(pngs);
                Image image = SwingFXUtils.toFXImage(bi,null);
                imageView.setFitWidth(500);
                imageView.setFitHeight(500);
                imageView.setImage(image);
                imageView.setSmooth(true);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        VBox layout = new VBox(imageView);
        return layout;
    }

    public void switchToStudentApply(javafx.event.ActionEvent actionEvent) throws IOException {
        studentApply_btn.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/controllers/Student_Dashboard.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToStudentStatus(javafx.event.ActionEvent actionEvent) throws IOException {
        Studentstatus_btn.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/controllers/Student_Status.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToLogIn(javafx.event.ActionEvent actionEvent) throws IOException {
        logout_btn.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/controllers/Login.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void openImages(Stage parentStage) {
        String folderPath = "/images/dormitory";
        URL resourceUrl = getClass().getResource(folderPath);
        if (resourceUrl != null) {
            File getFiles = new File(resourceUrl.getFile());
            if (getFiles != null && getFiles.isDirectory()) {
                FilenameFilter filter = new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        return name.toLowerCase().endsWith(".jpeg");
                    }
                };
                files = getFiles.listFiles(filter);
            }
        }
    }

    void translateEnglish() {
        Locale currentLocale = new Locale("en");

        ResourceBundle translate = ResourceBundle.getBundle("translation.content", currentLocale);
        studentApply_btn.setText(translate.getString("applyInDormitory_btn"));
        Studentstatus_btn.setText(translate.getString("statusi_roomdetails"));
        RoomDetails.setText(translate.getString("RoomDetails"));
        showphotos_rd.setText(translate.getString("showphotos_rd"));
        signout_rd.setText(translate.getString("Signout_dashboard"));
        wlc_student_rd.setText(translate.getString("wlc_student_rd"));

    }

    void translateAlbanian() {
        Locale currentLocale = new Locale("sq");

        ResourceBundle translate = ResourceBundle.getBundle("translation.content", currentLocale);
        studentApply_btn.setText(translate.getString("applyInDormitory_btn"));
        Studentstatus_btn.setText(translate.getString("statusi_roomdetails"));
        RoomDetails.setText(translate.getString("RoomDetails"));
        showphotos_rd.setText(translate.getString("showphotos_rd"));
        signout_rd.setText(translate.getString("Signout_dashboard"));
        wlc_student_rd.setText(translate.getString("wlc_student_rd"));

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pagination.setPageCount(8);
        Locale.setDefault(new Locale("sq"));
        albanianFlag.setOnMouseClicked(e->{
            translateAlbanian();
        });
        americanFlag.setOnMouseClicked(e->{
            translateEnglish();
        });
    }
}