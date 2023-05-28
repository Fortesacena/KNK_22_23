module com.example.knk_projekti {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires fontawesomefx;
    requires javafx.swing;

    opens models to javafx.base;
    opens com.example.knk_projekti to javafx.fxml;
    exports com.example.knk_projekti;
    opens controllers;
    exports controllers;

}